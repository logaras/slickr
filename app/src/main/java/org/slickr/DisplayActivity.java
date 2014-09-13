package org.slickr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.slickr.flickr.Photo;


/**
 * Created by marlog on 9/11/14.
 */
public class DisplayActivity extends Activity {
    /**
     * TextView for the title of the photo.
     */
    TextView titleTextView;

    /**
     * TextView for the tags of the photo.
     */
    TextView tagsTextView;

    /**
     * TextView for the location of the photo.
     */
    TextView locationTextView;

    /**
     * ShareActionProvider for media sharing.
     */
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        // Hide title from actionbar.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_display);


        ImageView imageView = (ImageView) findViewById(R.id.full_image);

        // Get the photo URL from extras.
        String fullImageUrl = this.getIntent().getExtras().get(FlickrUtils.FULL_IMG_URL).toString();

        // Asynchronously start loading the photo.
        Picasso.with(this).load(fullImageUrl).placeholder(R.drawable.placeholder).into(imageView);



        titleTextView = (TextView) findViewById(R.id.image_title);
        tagsTextView = (TextView) findViewById(R.id.image_tags);
        locationTextView = (TextView) findViewById(R.id.image_geolocation);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        // Retrieve info url from extras.
        final String infoUrl = this.getIntent().getExtras().get(FlickrUtils.FLICKR_INFO_URL).toString();

        // Asynchronously get the Info Json
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(infoUrl,
                new BaseJsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                        Photo photo = null;
                        try {
                            photo = FlickrUtils.getInstance().convertPhotoFromInfo(rawJsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        titleTextView.setText(photo.getTitle());
                        tagsTextView.setText(photo.getTags());
                        locationTextView.setText(photo.getGeoLocation());
                        setShareIntent(photo.getShareUrl());
                        // Set the share url on the Share Intent

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                        Toast.makeText(getApplicationContext(), "An error occured while connecting the server.", Toast.LENGTH_LONG).show();
                        Log.e(getString(R.string.app_name), statusCode + "\n" + errorResponse);
                    }

                    @Override
                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return null;
                    }

                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        if (shareItem != null) {
            mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Prepares the Share Intent for the specific photo.
     *
     * @param shareUrl The full URL as String
     */
    private void setShareIntent(final String shareUrl) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);

        mShareActionProvider.setShareIntent(shareIntent);
    }

}
