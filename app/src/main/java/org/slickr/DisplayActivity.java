package org.slickr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marlog on 9/11/14.
 */
public class DisplayActivity extends Activity {
    String shareUrl = "";
    TextView titleTextView;
    TextView tagsTextView;
    TextView locationTextView;
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_display);

        ImageView imageView = (ImageView) findViewById(R.id.full_image);

        String fullImageUrl = this.getIntent().getExtras().get(FlickrUtils.FULL_IMG_URL).toString();

        Picasso.with(this).load(fullImageUrl).placeholder(R.drawable.placeholder).into(imageView);

        titleTextView = (TextView) findViewById(R.id.image_title);
        tagsTextView = (TextView) findViewById(R.id.image_tags);
        tagsTextView = (TextView) findViewById(R.id.image_geolocation);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        final String jsonString = this.getIntent().getExtras().get(FlickrUtils.JSON_STRING).toString();
        try {
            final JSONObject jsonObject = new JSONObject(jsonString);
            titleTextView.setText(jsonObject.optString("title"));
            // Get info
            final String infoUrl = FlickrUtils.getInstance().constructInfoUrl(jsonObject);

            AsyncHttpClient client = new AsyncHttpClient();

            client.get(infoUrl,
                    new BaseJsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                            //Log.d(getString(R.string.app_name), rawJsonResponse);
                            try {
                                JSONObject jsonObject = new JSONObject(rawJsonResponse);
                                //Log.d("slickr",jsonObject.getJSONObject("photos").getJSONArray("photo").toString());

                                // Photo page
                                String shareUrl= jsonObject.optJSONObject("photo").optJSONObject("urls").optJSONArray("url").toString();
                                // Location


                                // Manipulate tags
                                JSONArray tagsJSONArray = jsonObject.optJSONObject("photo").optJSONObject("tags").optJSONArray("tag");
                                final StringBuilder tagsBuilder = new StringBuilder();

                                if (tagsJSONArray.length() > 0) {
                                    for (int i = 0; i < tagsJSONArray.length(); i++) {
                                        final JSONObject tagJsonObject = (JSONObject) tagsJSONArray.get(i);
                                        tagsBuilder.append(tagJsonObject.opt("raw"));
                                        tagsBuilder.append(" ");
                                    }
                                    tagsTextView.setText(tagsBuilder.toString());
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {


                            Log.e(getString(R.string.app_name), statusCode + "\n" + errorResponse);
                        }

                        @Override
                        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return null;
                        }

                    });

//            tagsTextView.setText(jsonObject.optString("tags"));
//            locationTextView.setText(jsonObject.optString("geolocation"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        if (shareItem != null) {
            mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        }
        setShareIntent();
        return true;
    }

    private void setShareIntent() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Take a look!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);

        mShareActionProvider.setShareIntent(shareIntent);
    }


}
