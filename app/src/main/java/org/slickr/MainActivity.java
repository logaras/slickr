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

    ProgressBar progressBarView;
    JSONAdapter mJSONAdapter;
    ListView resultsListView;
    boolean isGeoEnabled = false;
    Location mLocation;
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

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
            case R.id.action_settings:
                return true;
            case R.id.action_location:
                toggleLocation(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        /*if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/
    }

    private void toggleLocation(MenuItem item) {
        Log.d(getString(R.string.app_name), "!!!Should toggle locations");
        isGeoEnabled = !isGeoEnabled;

        if (isGeoEnabled) {
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                item.setIcon(R.drawable.ic_action_location_searching);
            } else if (mLocationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER))) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                item.setIcon(R.drawable.ic_action_location_searching);
            } else {
                Toast.makeText(this, "Enable GPS or Network location.", Toast.LENGTH_LONG).show();
            }
        } else {
            // disable updates
            mLocationManager.removeUpdates(this);
            item.setIcon(R.drawable.ic_action_location_off);
        }
    }


    /**
     * @param intent
     */
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(getString(R.string.app_name), "Searching for " + query);
            String urlTextQuery = "";
            try {
                urlTextQuery = URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(FlickrUtils.FLICK_SEARCH_URL);
            urlBuilder.append(urlTextQuery);

            if (isGeoEnabled) {

                urlBuilder.append("&lat=");
                urlBuilder.append(mLocation.getLatitude());
                urlBuilder.append("&lon=");
                urlBuilder.append(mLocation.getLongitude());
            }
            urlBuilder.toString();
            Log.d(getString(R.string.app_name), urlBuilder.toString());


            AsyncHttpClient client = new AsyncHttpClient();
            progressBarView.setVisibility(View.VISIBLE);
            client.get(urlBuilder.toString(),
                    new BaseJsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                            progressBarView.setVisibility(View.INVISIBLE);

                            //Log.d(getString(R.string.app_name), rawJsonResponse);
                            try {
                                JSONObject jsonObject = new JSONObject(rawJsonResponse);
                                //Log.d("slickr",jsonObject.getJSONObject("photos").getJSONArray("photo").toString());
                                mJSONAdapter.updateData(jsonObject.getJSONObject("photos").getJSONArray("photo"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                            progressBarView.setVisibility(View.INVISIBLE);

                            Log.e(getString(R.string.app_name), statusCode + "\n" + errorResponse);
                        }

                        @Override
                        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return null;
                        }

                    });

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JSONObject item = (JSONObject) mJSONAdapter.getItem(position);

        Intent displayIntent = new Intent(this, DisplayActivity.class);
        displayIntent.putExtra(FlickrUtils.JSON_STRING, item.toString());
        displayIntent.putExtra(FlickrUtils.FULL_IMG_URL, FlickrUtils.getInstance().constructSourceUrl(item, FlickrUtils.SIZE_LARGE));

        startActivity(displayIntent);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mLocationManager.removeUpdates(this);
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
