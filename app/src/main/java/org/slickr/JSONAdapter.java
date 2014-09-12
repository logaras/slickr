package org.slickr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by marlog on 9/10/14.
 */
public class JSONAdapter extends BaseAdapter {

    /**
     * The application context.
     */
    Context mContext;

    /**
     * The Layout inflater.
     */
    LayoutInflater mInflater;

    /**
     * The JSON Array to be displayed.
     */
    JSONArray mJsonArray;

    /**
     * JSONAdapter Constructor.
     * @param context the application context.
     * @param inflater the layout inflater
     */
    public JSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();

    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // This is the first time the item is displayed.
        // Inflate from scratch.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_result, null);


            // Create the ViewHolder for future use.
            viewHolder = new ViewHolder();
            viewHolder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.thumbnail_title);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.img_title);
            viewHolder.infoTextView = (TextView) convertView.findViewById(R.id.img_info);

            // Store the ViewHolder
            convertView.setTag(viewHolder);

        // Retrieve and use stored ViewHolder.
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        // Fill in the row with corresponding data.
        JSONObject jsonObject = (JSONObject) getItem(position);

        // Construct the thumnbnail URL
        final String thumbnailUrl = FlickrUtils.getInstance().constructSourceUrl(jsonObject, FlickrUtils.SIZE_LARGE_SQUARE);
        Log.d("slickr", "Getting thumbnail from " + thumbnailUrl);

        // Asynchronously fetch thumbnail from flickr
        Picasso.with(mContext).load(thumbnailUrl).placeholder(R.drawable.placeholder).into(viewHolder.thumbnailImageView);
        viewHolder.titleTextView.setText(jsonObject.optString("title"));

        return convertView;
    }

    /**
     * Updates the displayed data.
     *
     * @param jsonArray JSONArray with the new data.
     */
    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for storing the various Views used in each row.
     */
    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView infoTextView;
    }


}
