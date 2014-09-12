package org.slickr;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marlog on 9/11/14.
 */
public class FlickrUtils {
    /**
     * The API Key for the slickr app.
     */
    public static final String FLICKR_KEY = "a3e5933dec792ea59fd64d636716f4bc";

    /**
     * The secret key for the slickr app.
     */
    public static final String FLICKR_SECRET = "b2ddfb9f662f2f1b";


    /**
     * The base url for the REST API calls.
     */
    public static final String FLICK_BASE_URL = "https://api.flickr.com/services/rest/?format=json&nojsoncallback=1&method=";

    /**
     * REST call for getting info for a specific photo.
     */
    public static final String FLICKR_INFO_URL = FLICK_BASE_URL + "flickr.photos.getInfo&api_key=" + FLICKR_KEY + "&photo_id=";

    public static final int RESULTS_PER_PAGE = 10;

    /**
     * REST call for searching a text query.
     */
    public static final String FLICK_SEARCH_URL = FLICK_BASE_URL + "flickr.photos.search&per_page="+RESULTS_PER_PAGE+"&api_key=" + FLICKR_KEY + "&text=";

    /**
     * Key for the full image url.
     */
    public static final String FULL_IMG_URL = "fullImageURL";

    /**
     * Key for the json string.
     */
    public static final String JSON_STRING = "jsonString";

    /*
s	small square 75x75
q
t	thumbnail, 100 on longest side
m	small, 240 on longest side
n	small, 320 on longest side
-	medium, 500 on longest side
z	medium 640, 640 on longest side
c	medium 800, 800 on longest side†
b	large, 1024 on longest side*
o	original image, either a jpg, gif or png, depending on source format
 */

    /**
     * Large Square Image size Identifier. square 150x150
     */
    public static char SIZE_LARGE_SQUARE = 'q';

    /**
     * Large Image size Identifier. 1024 on longest side
     */
    public static char SIZE_LARGE = 'b';

    /**
     * Static instance of the class.
     */
    private static FlickrUtils ourInstance;

    /**
     * Provides access to the FlickrUtils methods.
     *
     * @return the static instance of the class.
     */
    public static FlickrUtils getInstance() {
        if (ourInstance == null) {
            ourInstance = new FlickrUtils();
        }
        return ourInstance;
    }

    /**
     * Constucts the URL of a photo.
     * <p/>
     * Having the format: https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
     *
     * @param jsonObject the JSON Object corresponding to the photo.
     * @param size       desired size of the image retrieved.
     * @return the full URL of the photo
     */
    public String constructSourceUrl(final JSONObject jsonObject, final char size) {

        final StringBuilder urlBuilder = new StringBuilder();
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


    /**
     * Constructs the URL for the REST call returning info for a specific photo.
     *
     * @param jsonObject the JSON Object corresponding to the photo.
     * @return the info UR.
     */
    public String constructInfoUrl(final JSONObject jsonObject) {

        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(FLICKR_INFO_URL);
        urlBuilder.append(jsonObject.opt("id"));
        urlBuilder.append("&secret=");
        urlBuilder.append(jsonObject.opt("secret"));

        return urlBuilder.toString();
    }

    /**
     * Extracts the Location of a photo.
     *
     * @param jsonObject the JSON Object corresponding to the photo.
     * @return string with county,region,country or Unknown Location
     * @throws JSONException
     */
    public String extractLocation(JSONObject jsonObject) throws JSONException {
        String returnString = "(Unknown Location)";
        final JSONObject locationJsonObject = jsonObject.optJSONObject("photo").optJSONObject("location");
        if (locationJsonObject != null) {
            final StringBuilder locationBuilder = new StringBuilder();
            locationBuilder.append(((JSONObject) locationJsonObject.opt("county")).opt("_content").toString());
            locationBuilder.append(", ");
            locationBuilder.append(((JSONObject) locationJsonObject.opt("region")).opt("_content").toString());
            locationBuilder.append(", ");
            locationBuilder.append(((JSONObject) locationJsonObject.opt("country")).opt("_content").toString());
            Log.d("slickr", "Location is " + locationBuilder.toString());
            returnString = locationBuilder.toString();
        }
        return returnString;
    }

    /**
     * Extracts a friendly URL for media sharing.
     *
     * @param jsonObject the JSON Object corresponding to the photo.
     * @return URL as String
     * @throws JSONException
     */
    public String extractShareUrl(JSONObject jsonObject) throws JSONException {
        final JSONObject urlJsonObject = (JSONObject) jsonObject.optJSONObject("photo").optJSONObject("urls").optJSONArray("url").get(0);
        String returnString = urlJsonObject.opt("_content").toString();

        Log.d("slickr", "Share url is" + returnString);
        return returnString;
    }

    /**
     * Extracts the tags of a specific photo.
     *
     * @param jsonObject the JSON Object corresponding to the photo.'
     * @return all tags as String or (none)
     * @throws JSONException
     */
    public String extractTags(JSONObject jsonObject) throws JSONException {

        String returnString = "(none)";
        JSONArray tagsJSONArray = jsonObject.optJSONObject("photo").optJSONObject("tags").optJSONArray("tag");
        final StringBuilder tagsBuilder = new StringBuilder();

        if (tagsJSONArray.length() > 0) {
            for (int i = 0; i < tagsJSONArray.length(); i++) {
                final JSONObject tagJsonObject = (JSONObject) tagsJSONArray.get(i);
                tagsBuilder.append(tagJsonObject.opt("raw"));
                tagsBuilder.append(" ");
            }
            returnString = tagsBuilder.toString();
        }
        return returnString;
    }
}
