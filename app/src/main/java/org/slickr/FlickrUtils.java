package org.slickr;

import org.json.JSONObject;

/**
 * Created by marlog on 9/11/14.
 */
public class FlickrUtils {
    private static FlickrUtils ourInstance;
    public static char SIZE_LARGE_SQUARE = 'q';
    public static char SIZE_LARGE ='b';

    public static FlickrUtils getInstance() {
        if (ourInstance == null) {
            ourInstance = new FlickrUtils();
        }
        return ourInstance;
    }

    private FlickrUtils() {

    }

    public String constructSourceUrl(final JSONObject jsonObject,final char size){
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
        urlBuilder.append(size);
        urlBuilder.append(".jpg");

        return urlBuilder.toString();
    }
}
