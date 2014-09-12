package org.slickr;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener, LocationListener {

    /**
     * Progress bar indicating background activity.
     */
    ProgressBar progressBarView;

    /**
     * The adapter for displaying search results.
     */
    JSONAdapter mJSONAdapter;

    /**
     * ListView containing the search results.
     */
    ListView resultsListView;

    /**
     * Indicating if the location services for the app are enabled.
     */
    boolean isGeoEnabled = false;

    /**
     * Last known location.
     */
    Location mLocation;

    /**
     * The Location Manager.
     */
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Disabling the title on the action bar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        // Initializing Views and display elements
        progressBarView = (ProgressBar) findViewById(R.id.progressBar);
        progressBarView.setVisibility(View.INVISIBLE);

        resultsListView = (ListView) findViewById(R.id.results_listview);
        mJSONAdapter = new JSONAdapter(this, getLayoutInflater());

        resultsListView.setAdapter(mJSONAdapter);
        resultsListView = (ListView) findViewById(R.id.results_listview);
        resultsListView.setOnItemClickListener(this);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            // Search widget selected
            case R.id.action_settings:
                return true;

            // Location togglig selected
            case R.id.action_location:
                toggleLocation(item);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Enabling or Disabling the location services for the app.
     *
     * @param item the MenuItem corresponding to the location settings.
     */
    private void toggleLocation(MenuItem item) {
        Log.d(getString(R.string.app_name), "Should toggle locations");

        // Revert previous state
        isGeoEnabled = !isGeoEnabled;

        // Is now enabled
        if (isGeoEnabled) {

            // Use best enabled location provider
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                item.setIcon(R.drawable.ic_action_location_searching);

            } else if (mLocationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER))) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                item.setIcon(R.drawable.ic_action_location_searching);

            // Cannot get fresh location.
            } else {
                Toast.makeText(this, "Enable GPS or Network Location for better results.", Toast.LENGTH_LONG).show();
            }

        // Is now disabled
        } else {

            // Unsubscribe from location updates.
            mLocationManager.removeUpdates(this);
            item.setIcon(R.drawable.ic_action_location_off);
        }
    }


    /**
     * Handles the actual search request.
     *
     * @param intent the Intent that initialized the process.
     */
    private void handleIntent(Intent intent) {

        // This is an intent for us indeed.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            // Get the query
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(getString(R.string.app_name), "Searching for " + query);

            // Encode the query to UTF-8
            String urlTextQuery = "";
            try {
                urlTextQuery = URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Start building the complete URL
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(FlickrUtils.FLICK_SEARCH_URL);
            urlBuilder.append(urlTextQuery);

            // Include location if enabled.
            if (isGeoEnabled) {

                urlBuilder.append("&lat=");
                urlBuilder.append(mLocation.getLatitude());
                urlBuilder.append("&lon=");
                urlBuilder.append(mLocation.getLongitude());
            }
            urlBuilder.toString();

            Log.d(getString(R.string.app_name), urlBuilder.toString());


            // Use the AsyncHttpClient to contact the Flickr api.
            AsyncHttpClient client = new AsyncHttpClient();

            progressBarView.setVisibility(View.VISIBLE);

            client.get(urlBuilder.toString(),
                    new BaseJsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                            // The results are in.
                            progressBarView.setVisibility(View.INVISIBLE);

                            // Try parsing the response.
                            try {
                                JSONObject jsonObject = new JSONObject(rawJsonResponse);
                                // Update the adapter to display the results.
                                mJSONAdapter.updateData(jsonObject.getJSONObject("photos").getJSONArray("photo"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                            progressBarView.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "An error occured while connecting the server.",Toast.LENGTH_LONG).show();
                            Log.e(getString(R.string.app_name), statusCode + "\n" + errorResponse);
                        }

                        @Override
                        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return null;
                        }

                    });

        }
    }



    /**
     * Show the Activity for displaying the photo.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JSONObject item = (JSONObject) mJSONAdapter.getItem(position);

        Intent displayIntent = new Intent(this, DisplayActivity.class);

        // Pass the photo json object as extra.
        displayIntent.putExtra(FlickrUtils.JSON_STRING, item.toString());

        // Pass the full photo URL as extra
        displayIntent.putExtra(FlickrUtils.FULL_IMG_URL, FlickrUtils.getInstance().constructSourceUrl(item, FlickrUtils.SIZE_LARGE));

        startActivity(displayIntent);
    }


    /**
     * Get any updates on current location.
     */
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
