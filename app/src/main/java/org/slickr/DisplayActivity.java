package org.slickr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.squareup.picasso.Picasso;

/**
 * Created by marlog on 9/11/14.
 */
public class DisplayActivity extends Activity {
    String mImageUrl = "";
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_display);

        ImageView imageView = (ImageView) findViewById(R.id.full_image);

        String fullImageUrl = this.getIntent().getExtras().get(Statics.FULL_IMG_URL).toString();

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this).load(fullImageUrl).placeholder(R.drawable.placeholder).into(imageView);
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

        // create an Intent with the contents of the TextView
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Take a look!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mImageUrl);

        mShareActionProvider.setShareIntent(shareIntent);
    }


}
