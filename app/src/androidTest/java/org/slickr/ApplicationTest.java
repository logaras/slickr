package org.slickr;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.slickr.flickr.FlickrPhoto;
import org.slickr.flickr.FlickrUtils;

public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);

        try {
            JSONObject photoObject = new JSONObject(JSONData.VALID_RESULT_PHOTO);
            JSONObject photoInfo = new JSONObject(JSONData.VALID_INFO);


            // construct thumbnail image url
            testConstructThumbnailUrl(photoObject);

            // construct medium image url
            testConstructMediumImageUrl(photoObject);

            // extractLocation
            testExtractLocation(photoInfo);

            // constructImageUrl

            // extractShareUrl
            testExtractShareUrl(photoInfo);

            // extractPageCount
            testExtractPageCount();

            // extractCurrentPage
            testExtractCurrentPage();

            // extractPhotos
            testExtractPhotos();


            // extractTags
            testExtractTags(photoInfo);

            // appendPageToQueryUrl
            testAppendPageToQuery();

            // convertPhotoFromResult
           testConvertPhotoFromResult(photoObject);

            // convertPhotoFromInfo
            //testConvertPhotoFromInfo(photoObject);

        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }
    public void testConstructThumbnailUrl(JSONObject photoObject){
        String output = FlickrUtils.getInstance().constructImageUrl(photoObject,FlickrUtils.SIZE_LARGE_SQUARE);
        assertEquals(JSONData.THUMBNAIL_IMAGE_URL,output);
    }

    public void testConstructMediumImageUrl(JSONObject photoObject){
        String output = FlickrUtils.getInstance().constructImageUrl(photoObject,FlickrUtils.SIZE_MEDIUM);
        assertEquals(JSONData.MEDIUM_IMAGE_URL,output);
    }

    public void testExtractLocation(JSONObject photoObject) throws JSONException {
        String output = FlickrUtils.getInstance().extractLocation(photoObject);
        assertEquals(JSONData.LOCATION,output);
    }

    public void testExtractShareUrl(JSONObject photoObject) throws JSONException {
        String output = FlickrUtils.getInstance().extractShareUrl(photoObject);
        assertEquals(JSONData.SHARE_URL,output);
    }

    public void testExtractTags(JSONObject photoObject) throws JSONException {
        String output = FlickrUtils.getInstance().extractTags(photoObject);
        assertEquals(true, JSONData.TAGS.equalsIgnoreCase(output));
    }

    public void testExtractCurrentPage() throws JSONException {
        int output = FlickrUtils.getInstance().extractCurrentPage(JSONData.VALID_RESULTS);
        assertEquals(JSONData.CURRENT_PAGE,output);
    }

    public void testExtractPageCount() throws JSONException {
        int output = FlickrUtils.getInstance().extractPageCount(JSONData.VALID_RESULTS);
        assertEquals(JSONData.PAGE_COUNT,output);
    }

    public void testExtractPhotos() throws JSONException {
        int output = FlickrUtils.getInstance().extractPhotos(JSONData.VALID_RESULTS).size();
        assertEquals(JSONData.SEARCH_RESULTS,output);
    }

    public void testAppendPageToQuery(){
        String output = FlickrUtils.getInstance().appendPageToQueryUrl(JSONData.SEARCH_URL,2);
        assertEquals(JSONData.SEARCH_URL_PAGE_2,output);
    }

    public void testConvertPhotoFromResult(JSONObject photoObject){

        FlickrPhoto photo = FlickrUtils.getInstance().convertPhotoFromResult(photoObject);
        assertEquals(JSONData.TITLE,photo.getTitle());
        assertEquals(JSONData.INFO_URL,photo.getInfoUrl());
        assertEquals(JSONData.THUMBNAIL_IMAGE_URL,photo.getThumbnailUrl());
        assertEquals(JSONData.MEDIUM_IMAGE_URL,photo.getFullImageUrl());
    }

    public void testPhotoTitleFromInfo() throws JSONException {
        FlickrPhoto photo = FlickrUtils.getInstance().convertPhotoFromInfo(JSONData.VALID_INFO);
        assertEquals(JSONData.TITLE,photo.getTitle());
        assertEquals(JSONData.TAGS,photo.getTags());
        assertEquals(JSONData.LOCATION,photo.getGeoLocation());
        assertEquals(JSONData.SHARE_URL,photo.getShareUrl());
    }
}