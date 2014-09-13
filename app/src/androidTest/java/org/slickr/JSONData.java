package org.slickr;

/**
 * Static Variables for unit testing purposes.
 */
public class JSONData {
    /**
     *  A valid JSON response with 10 results per page
     */
    public final static String VALID_RESULTS = "{\"photos\":{\"page\":1,\"pages\":2,\"perpage\":10,\"total\":\"11\",\"photo\":[{\"id\":\"4414824269\",\"owner\":\"63355074@N00\",\"secret\":\"ef24a6b52b\",\"server\":\"2722\",\"farm\":3,\"title\":\"Dreams\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"223612607\",\"owner\":\"31356653@N00\",\"secret\":\"c20363e72a\",\"server\":\"65\",\"farm\":1,\"title\":\"morning \\/ \\u03c0\\u03c1\\u03c9\\u03ca\\u03bd\\u03bf\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"5077951310\",\"owner\":\"8471268@N03\",\"secret\":\"bf204a99a9\",\"server\":\"4002\",\"farm\":5,\"title\":\"sunrise\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"178246873\",\"owner\":\"31356653@N00\",\"secret\":\"2cf392a8b7\",\"server\":\"69\",\"farm\":1,\"title\":\"the dock\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"5486501733\",\"owner\":\"60093614@N06\",\"secret\":\"dcce567f68\",\"server\":\"5059\",\"farm\":6,\"title\":\"P2260163.jpg\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"5487076238\",\"owner\":\"60093614@N06\",\"secret\":\"8cbf1f925d\",\"server\":\"5057\",\"farm\":6,\"title\":\"P2260147.JPG\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"5486474621\",\"owner\":\"60093614@N06\",\"secret\":\"016629d6e7\",\"server\":\"5171\",\"farm\":6,\"title\":\"IMG_1442.JPG\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"5486454879\",\"owner\":\"60093614@N06\",\"secret\":\"8162d634e5\",\"server\":\"5173\",\"farm\":6,\"title\":\"IMG_1435.JPG\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"9288494418\",\"owner\":\"59662164@N05\",\"secret\":\"faf98ec3ff\",\"server\":\"5508\",\"farm\":6,\"title\":\"2013 05 25 Dolomites Italy (2)\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"9285714639\",\"owner\":\"59662164@N05\",\"secret\":\"060059bd12\",\"server\":\"2816\",\"farm\":3,\"title\":\"2013 05 25 Dolomites Italy (1)\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}";

    /**
     * A valid JSON of a single photo extracted from results JSON.
     */
    public final static String VALID_RESULT_PHOTO = "{\"id\":\"4414824269\",\"owner\":\"63355074@N00\",\"secret\":\"ef24a6b52b\",\"server\":\"2722\",\"farm\":3,\"title\":\"Dreams\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}";


    /**
     * A valid response to the info method.
     */
    public final static String VALID_INFO = "{\"photo\":{\"id\":\"4414824269\", \"secret\":\"ef24a6b52b\", \"server\":\"2722\", \"farm\":3, \"dateuploaded\":\"1268007642\", \"isfavorite\":0, \"license\":\"0\", \"safety_level\":\"0\", \"rotation\":0, \"owner\":{\"nsid\":\"63355074@N00\", \"username\":\"Kostas Koronios\", \"realname\":\"Kostas Koronios\", \"location\":\"\\u03a0\\u03ac\\u03c4\\u03c1\\u03b1, Greece\", \"iconserver\":\"4062\", \"iconfarm\":5, \"path_alias\":\"koronios\"}, \"title\":{\"_content\":\"Dreams\"}, \"description\":{\"_content\":\"\"}, \"visibility\":{\"ispublic\":1, \"isfriend\":0, \"isfamily\":0}, \"dates\":{\"posted\":\"1268007642\", \"taken\":\"2010-03-05 18:22:24\", \"takengranularity\":\"0\", \"lastupdate\":\"1292059276\"}, \"views\":\"73\", \"editability\":{\"cancomment\":0, \"canaddmeta\":0}, \"publiceditability\":{\"cancomment\":1, \"canaddmeta\":0}, \"usage\":{\"candownload\":0, \"canblog\":0, \"canprint\":0, \"canshare\":1}, \"comments\":{\"_content\":\"2\"}, \"notes\":{\"note\":[]}, \"people\":{\"haspeople\":0}, \"tags\":{\"tag\":[{\"id\":\"2640989-4414824269-223\", \"author\":\"63355074@N00\", \"authorname\":\"Kostas Koronios\", \"raw\":\"Sunset\", \"_content\":\"sunset\", \"machine_tag\":0}, {\"id\":\"2640989-4414824269-8741\", \"author\":\"63355074@N00\", \"authorname\":\"Kostas Koronios\", \"raw\":\"Places\", \"_content\":\"places\", \"machine_tag\":0}]}, \"location\":{\"latitude\":38.208528, \"longitude\":21.743735, \"accuracy\":\"16\", \"context\":\"0\", \"locality\":{\"_content\":\"Patra\", \"place_id\":\"fCndjj9YV7Mhhrc\", \"woeid\":\"959401\"}, \"county\":{\"_content\":\"Achaia\", \"place_id\":\"sb1domZQUL8BlMYtGA\", \"woeid\":\"12591092\"}, \"region\":{\"_content\":\"Dytiki Ellada\", \"place_id\":\"G6HCVS9QUL.H63CH0Q\", \"woeid\":\"12577880\"}, \"country\":{\"_content\":\"Greece\", \"place_id\":\"s1JMkOJTUb5LfB9USg\", \"woeid\":\"23424833\"}, \"place_id\":\"fCndjj9YV7Mhhrc\", \"woeid\":\"959401\"}, \"geoperms\":{\"ispublic\":1, \"iscontact\":0, \"isfriend\":0, \"isfamily\":0}, \"urls\":{\"url\":[{\"type\":\"photopage\", \"_content\":\"https:\\/\\/www.flickr.com\\/photos\\/koronios\\/4414824269\\/\"}]}, \"media\":\"photo\"}, \"stat\":\"ok\"}";

    /**
     * A valid url calling the search REST method.
     */
    public final static String SEARCH_URL = "https://api.flickr.com/services/rest/?format=json&nojsoncallback=1&method=flickr.photos.search&per_page=10&api_key=a3e5933dec792ea59fd64d636716f4bc&text=landscape&lat=38.1657579&lon=21.7330497";

    /**
     * A valid URL requesting the second page of results.
     */
    public final static String SEARCH_URL_PAGE_2 =SEARCH_URL+"&page=2";

    /**
     * Maximum results per page.
     */
    public static final int SEARCH_RESULTS= 10;

    /**
     * The Url corresponding to the thumbnail of the photo.
     */
    public static final String THUMBNAIL_IMAGE_URL = "https://farm3.staticflickr.com/2722/4414824269_ef24a6b52b_q.jpg";

    /**
     * The Url corresponding to a medium size image of the photo.
     */
    public static final String MEDIUM_IMAGE_URL = "https://farm3.staticflickr.com/2722/4414824269_ef24a6b52b_z.jpg";


    /**
     * A valid url calling the info REST method.
     */
    public static final String INFO_URL = "https://api.flickr.com/services/rest/?format=json&nojsoncallback=1&method=flickr.photos.getInfo&api_key=a3e5933dec792ea59fd64d636716f4bc&photo_id=4414824269&secret=ef24a6b52b";

    /**
     * A string with all corresponding photo tags
     */
    public static final String TAGS = "Sunset Places ";

    /**
     * The number of total result pages.
     */
    public static final int PAGE_COUNT= 2;

    /**
     * The current result page number.
     */
    public static final int CURRENT_PAGE = 1;

    /**
     * A string with the geolocation description.
     */
    public static final String LOCATION ="Achaia, Dytiki Ellada, Greece";

    /**
     * The photopage url used for media sharing.
     */
    public static final String SHARE_URL ="https://www.flickr.com/photos/koronios/4414824269/";

    /**
     * The photo title
     */
    public static final String TITLE = "Dreams";

}
