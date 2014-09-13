package org.slickr.flickr;

/**
 * Created by marlog on 9/13/14.
 */
public class FlickrPhoto {

    /**
     * Photo id
     */
    String id;

    /**
     * Photo title.
     */
    String title;

    /**
     * The full info url returning a Json response
     */
    String infoUrl;

    /**
     * The url that returns a jpg image.
     */
    String fullImageUrl;

    /**
     * String describing the location of the photo.
     */
    String geoLocation;

    /**
     * String containing all the tags.
     */
    String tags;

    /**
     * Flick photopage.
     */
    String shareUrl;

    /**
     * The url that returns the jpg thumbnail of the photo.
     */
    String thumbnailUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getFullImageUrl() {
        return fullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
