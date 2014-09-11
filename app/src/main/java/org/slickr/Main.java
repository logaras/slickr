package org.slickr;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Main extends Activity {

    ProgressBar progressBarView;
    JSONAdapter mJSONAdapter;
    ListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        progressBarView = (ProgressBar) findViewById(R.id.progressBar);
        progressBarView.setVisibility(View.INVISIBLE);

        resultsListView  = (ListView) findViewById(R.id.results_listview);
        mJSONAdapter = new JSONAdapter(this, getLayoutInflater());
        resultsListView.setAdapter(mJSONAdapter);

        resultsListView = (ListView) findViewById(R.id.results_listview);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * @param intent
     */
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(getString(R.string.app_name), "Searching for " + query);
            String urlString = "";
            try {
                urlString = URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d(getString(R.string.app_name), Statics.FLICK_SEARCH_URL + urlString);


            AsyncHttpClient client = new AsyncHttpClient();
            progressBarView.setVisibility(View.VISIBLE);
            client.get(Statics.FLICK_SEARCH_URL + urlString,
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
}
