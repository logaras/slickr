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
public class JSONAdapter extends BaseAdapter{

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter (Context context, LayoutInflater inflater){
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

        // Create a new view from scratch.
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_result,null);

            viewHolder = new ViewHolder();
            viewHolder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.img_title);
            viewHolder.infoTextView = (TextView) convertView.findViewById(R.id.img_info);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        // Fill in the row with corresponding data.
        JSONObject jsonObject = (JSONObject) getItem(position);

        // Get actual image from flickr using picasso
        final String thumbnailUrl = constructSourceUrl(jsonObject);
        Log.d("slickr","Getting thumbnail from " + thumbnailUrl);
        Picasso.with(mContext).load(thumbnailUrl).placeholder(R.drawable.placeholder).into(viewHolder.thumbnailImageView);
        viewHolder.thumbnailImageView.setImageResource(R.drawable.placeholder);
        viewHolder.titleTextView.setText(jsonObject.optString("title"));

        return convertView;
    }

    public void updateDate(JSONArray jsonArray){
        Log.d("slickr","Array has " + jsonArray.length() + " elements ");
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView infoTextView;
    }

    private String constructSourceUrl(final JSONObject jsonObject){
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
        -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
        o	original image, either a jpg, gif or png, depending on source format
         */
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg


        final StringBuilder urlBuilder= new StringBuilder();
        urlBuilder.append("https://farm");
        urlBuilder.append(jsonObject.opt("farm"));
        urlBuilder.append(".staticflickr.com/");
        urlBuilder.append(jsonObject.opt("server"));
        urlBuilder.append("/");
        urlBuilder.append(jsonObject.opt("id"));
        urlBuilder.append("_");
        urlBuilder.append(jsonObject.opt("secret"));
        urlBuilder.append("_");
        urlBuilder.append("t");

        return urlBuilder.toString();
    }
}
