package org.slickr.flickr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slickr.FlickrUtils;

import java.util.ArrayList;

/**
 * Created by marlog on 9/13/14.
 */
public class SearchResults {

    private int pages;
    private int currentPage;
    private ArrayList<Photo> resultsArray;

    public SearchResults(final String jsonString) throws JSONException {


        JSONObject jsonObject = new JSONObject(jsonString);

        // get result page
        pages = jsonObject.getJSONObject("photos").getInt("pages");

        // get current page
        currentPage = jsonObject.getJSONObject("photos").getInt("page");

        // get JSON array
        final JSONArray jsonArrayResults = jsonObject.getJSONObject("photos").getJSONArray("photo");

        resultsArray = convertToPhotosArrayList(jsonArrayResults);

    }

    private ArrayList<Photo> convertToPhotosArrayList(JSONArray jsonArrayResults) throws JSONException {
        ArrayList<Photo> photoList = new ArrayList<Photo>();

        for (int i = 0; i < jsonArrayResults.length(); i++) {
            JSONObject jsonPhoto = (JSONObject) jsonArrayResults.get(i);
            Photo photo = FlickrUtils.getInstance().convertPhotoFromResult(jsonPhoto);
            photoList.add(photo);
        }


        return photoList;
    }

    public int getTotalPages() {
        return pages;
    }

    public void setTotalPages(int pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setResultsArray(ArrayList<Photo> resultsArray) {
        this.resultsArray = resultsArray;
    }

    public ArrayList<Photo> getResultsArray() {

        return resultsArray;
    }
}
