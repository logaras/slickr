package org.slickr.flickr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Representation of the results page.
 */
public class FlickrSearchResults {

    /**
     * Total pages for the query submitted.
     */
    private int pageCount;

    /**
     * Current page number.
     */
    private int currentPage;

    /**
     * Actual results.
     */
    private ArrayList<FlickrPhoto> resultsArray;

    /**
     * Constructs a FlickrSearchResults.
     *
     * @param jsonString the
     * @throws JSONException
     */
    public FlickrSearchResults(final String jsonString) throws JSONException {

        // get result page
        pageCount = FlickrUtils.getInstance().extractPageCount(jsonString);

        // get current page
        currentPage =  FlickrUtils.getInstance().extractCurrentPage(jsonString);

        resultsArray = FlickrUtils.getInstance().extractPhotos(jsonString);

    }


    public int getPageCount() {

        return pageCount;
    }

    public void setTotalPages(int pageCount) {

        this.pageCount= pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {

        this.currentPage = currentPage;
    }


    public void setPages(int pageCount) {
        this.pageCount = pageCount;
    }



    public ArrayList<FlickrPhoto> getResultsArray() {

        return resultsArray;
    }
}
