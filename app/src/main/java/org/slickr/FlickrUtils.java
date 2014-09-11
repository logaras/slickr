package org.slickr;

import org.json.JSONObject;

/**
 * Created by marlog on 9/11/14.
 */
public class FlickrUtils {
    public static final String FLICKR_KEY = "a3e5933dec792ea59fd64d636716f4bc";
    public static final String FLICKR_SECRET = "b2ddfb9f662f2f1b";
    public static final String FLICK_BASE_URL = "https://api.flickr.com/services/rest/?format=json&nojsoncallback=1&method=";
    public static final String FLICKR_INFO_URL = FLICK_BASE_URL + "flickr.photos.getInfo&api_key="+FLICKR_KEY+"&photo_id=";
    public static final String FLICK_SEARCH_URL = FLICK_BASE_URL+ "flickr.photos.search&per_page=10&api_key="+FLICKR_KEY+"&text=";
    public static final String FULL_IMG_URL = "fullImageURL";
    public static final String JSON_STRING = "jsonString";
    public static final String IMAGE_ID = "imageId";

    public static char SIZE_LARGE_SQUARE = 'q';
    public static char SIZE_LARGE ='b';

    private static FlickrUtils ourInstance;
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

    public String constructInfoUrl(final JSONObject jsonObject){


        final StringBuilder urlBuilder= new StringBuilder();
        urlBuilder.append(FLICKR_INFO_URL);
        urlBuilder.append(jsonObject.opt("id"));
        urlBuilder.append("&secret=");
        urlBuilder.append(jsonObject.opt("secret"));

        return urlBuilder.toString();
    }

}
