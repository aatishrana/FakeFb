package com.aatishrana.fakefb.data;

import com.aatishrana.fakefb.findFriend.Friend;
import com.aatishrana.fakefb.model.BoldText;
import com.aatishrana.fakefb.model.FriendStoryItem;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.newsFeed.model.FeedItem;
import com.aatishrana.fakefb.newsFeed.model.FeedItemAlbum;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPost;
import com.aatishrana.fakefb.newsFeed.model.FeedItemShared;
import com.aatishrana.fakefb.newsFeed.model.FeedItemStories;
import com.aatishrana.fakefb.notification.Noti;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Aatish Rana on 20-Nov-17.
 */

public class MainRepository
{
    /**
     * @param url url from where json is to be downloaded
     * @return if successful in creating new fb
     */
    public Observable<Boolean> createNewFb(String url)
    {
        return Observable.create(new ObservableOnSubscribe<Boolean>()
        {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception
            {

            }

        });
    }

    public Observable<FbData> getData()
    {
        return Observable.create(new ObservableOnSubscribe<FbData>()
        {
            @Override
            public void subscribe(ObservableEmitter<FbData> e) throws Exception
            {
                e.onNext(processData(getSampleJson1()));
                e.onComplete();
            }

        });
    }

    FbData processData(String responseString)
    {
        try
        {
            final String DATA = "data";
            final String NEWS_FEED = "news_feed";
            final String FRIENDS = "friends";
            final String NOTIFICATION = "notification";

            final String errorMsg = " not present in json source";

            JSONObject response = new JSONObject(responseString);

            if (!response.has(DATA) && response.get(DATA) instanceof JSONObject)
                throw new RuntimeException(DATA + errorMsg);
            JSONObject data = response.getJSONObject(DATA);

            if (!data.has(NEWS_FEED) && data.get(NEWS_FEED) instanceof JSONObject)
                throw new RuntimeException(NEWS_FEED + errorMsg);

            List<FeedItem> newsFeed = extractNewsFeed(data.getJSONObject(NEWS_FEED));

            if (!data.has(FRIENDS) && data.get(FRIENDS) instanceof JSONObject)
                throw new RuntimeException(FRIENDS + errorMsg);
            FindFriendData findFriendData = extractFriends(data.getJSONObject(FRIENDS));

            if (!data.has(NOTIFICATION) && data.get(NOTIFICATION) instanceof JSONArray)
                throw new RuntimeException(NOTIFICATION + errorMsg);
            List<Noti> notificationData = extractNotifications(data.getJSONArray(NOTIFICATION));


            return new FbData(newsFeed, findFriendData, notificationData);
        } catch (JSONException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private List<FeedItem> extractNewsFeed(JSONObject news_feed) throws JSONException
    {
        List<FeedItem> data = new ArrayList<>();

        final String STORY = "story";
        final String STORY_FIRST_NAME = "first_name";
        final String STORY_LAST_NAME = "last_name";
        final String STORY_USER_PIC_URL = "user_pic_url";
        final String STORY_WATCHED = "watched";

        final String FEED = "feed";
        final String FEED_RANK = "rank";
        final String FEED_TYPE = "type";
        final String FEED_USER_PIC_URL = "user_pic_url";
        final String FEED_USER_NAME = "user_name";
        final String FEED_TIME = "time";
        final String FEED_LOCATION = "location";
        final String FEED_PRIVACY = "privacy";
        final String FEED_EMOTIONS = "emotions";
        final String FEED_COMMENTS = "comments";
        final String FEED_VIEWS = "views";
        final String FEED_SHARES = "shares";
        final String FEED_DATA = "data";

        final String FEED_SHARED_FROM = "shared_from";


        if (news_feed.has(STORY) && news_feed.get(STORY) instanceof JSONArray)
        {
            List<FriendStoryItem> friendStoryItemList = new ArrayList<>();
            JSONArray stories = news_feed.getJSONArray(STORY);
            for (int i = 0; i < stories.length(); i++)
            {
                JSONObject story = stories.getJSONObject(i);

                //logic of required fields
                if (story.has(STORY_FIRST_NAME) && story.has(STORY_USER_PIC_URL))
                {
                    String first_name = story.getString(STORY_FIRST_NAME);
                    String user_pic_url = story.getString(STORY_USER_PIC_URL);
                    String last_name = "";
                    boolean watched = false;

                    if (story.has(STORY_LAST_NAME) && story.get(STORY_LAST_NAME) instanceof String)
                        last_name = story.getString(STORY_LAST_NAME);

                    if (story.has(STORY_WATCHED) && story.get(STORY_WATCHED) instanceof Boolean)
                        watched = story.getBoolean(STORY_WATCHED);

                    friendStoryItemList.add(new FriendStoryItem(first_name, last_name, new Image(user_pic_url, 100, 100, "#dfdfdf"), watched));
                } else
                    throw new RuntimeException(STORY_FIRST_NAME + " or " + STORY_USER_PIC_URL + " missing in " + STORY + " at position=" + i + 1);
            }
            System.out.println("\nNews Feed Stories");
            System.out.println(friendStoryItemList.toString());

            if (friendStoryItemList != null && !friendStoryItemList.isEmpty())
                data.add(new FeedItemStories(friendStoryItemList, 0));
        }

        if (news_feed.has(FEED) && news_feed.get(FEED) instanceof JSONArray)
        {
            JSONArray feeds = news_feed.getJSONArray(FEED);
            for (int i = 0; i < feeds.length(); i++)
            {
                JSONObject feed = feeds.getJSONObject(i);
                if (feed.has(FEED_DATA) && feed.get(FEED_DATA) instanceof JSONObject &&
                        feed.has(FEED_TYPE) && feed.get(FEED_TYPE) instanceof String &&
                        feed.has(FEED_USER_NAME) && feed.get(FEED_USER_NAME) instanceof String &&
                        feed.has(FEED_USER_PIC_URL) && feed.get(FEED_USER_PIC_URL) instanceof String)
                {
                    //set default value
                    int rank = i + 1;
                    String time = "1 min ago";
                    String location = "";
                    int privacy = 1;
                    int emotions = 0, comments = 0, views = 0, shares = 0;

                    //get required values from json
                    String type = feed.getString(FEED_TYPE);
                    String user_name = feed.getString(FEED_USER_NAME);
                    String user_pic_url = feed.getString(FEED_USER_PIC_URL);

                    if (feed.has(FEED_RANK) && feed.get(FEED_RANK) instanceof Integer)
                        rank = feed.getInt(FEED_RANK);

                    if (feed.has(FEED_TIME) && feed.get(FEED_TIME) instanceof String)
                        time = feed.getString(FEED_TIME);

                    if (feed.has(FEED_LOCATION) && feed.get(FEED_LOCATION) instanceof String)
                        location = feed.getString(FEED_LOCATION);

                    if (feed.has(FEED_PRIVACY) && feed.get(FEED_PRIVACY) instanceof Integer)
                        privacy = feed.getInt(FEED_PRIVACY);

                    if (feed.has(FEED_EMOTIONS) && feed.get(FEED_EMOTIONS) instanceof Integer)
                        emotions = feed.getInt(FEED_EMOTIONS);

                    if (feed.has(FEED_COMMENTS) && feed.get(FEED_COMMENTS) instanceof Integer)
                        comments = feed.getInt(FEED_COMMENTS);

                    if (feed.has(FEED_VIEWS) && feed.get(FEED_VIEWS) instanceof Integer)
                        views = feed.getInt(FEED_VIEWS);

                    if (feed.has(FEED_SHARES) && feed.get(FEED_SHARES) instanceof Integer)
                        shares = feed.getInt(FEED_SHARES);

                    if (type.equalsIgnoreCase("post"))
                    {
                        FeedItemPost post = extractSimplePost(feed.getJSONObject(FEED_DATA),
                                rank, user_pic_url, user_name, time, location, privacy,
                                emotions, comments, views, shares);

                        if (post != null)
                            data.add(post);

                    } else if (type.equalsIgnoreCase("shared"))
                    {
                        if (feed.has(FEED_SHARED_FROM) && feed.get(FEED_SHARED_FROM) instanceof JSONObject)
                        {
                            FeedItemShared post = extractSharedPost(feed.getJSONObject(FEED_DATA),
                                    feed.getJSONObject(FEED_SHARED_FROM),
                                    rank, user_pic_url, user_name, time, location, privacy,
                                    emotions, comments, views, shares);

                            if (post != null)
                                data.add(post);
                        }
                    } else if (type.equalsIgnoreCase("album_of_two"))
                    {
                        FeedItemAlbum post = extractAlbum(feed.getJSONObject(FEED_DATA), 2,
                                rank, user_pic_url, user_name, time, location, privacy,
                                emotions, comments, views, shares);

                        if (post != null)
                            data.add(post);

                    } else if (type.equalsIgnoreCase("album_of_three"))
                    {
                        FeedItemAlbum post = extractAlbum(feed.getJSONObject(FEED_DATA), 3,
                                rank, user_pic_url, user_name, time, location, privacy,
                                emotions, comments, views, shares);

                        if (post != null)
                            data.add(post);
                    } else if (type.equalsIgnoreCase("album_of_four"))
                    {
                        FeedItemAlbum post = extractAlbum(feed.getJSONObject(FEED_DATA), 4,
                                rank, user_pic_url, user_name, time, location, privacy,
                                emotions, comments, views, shares);

                        if (post != null)
                            data.add(post);
                    } else if (type.equalsIgnoreCase("album_of_many"))
                    {
                        FeedItemAlbum post = extractAlbum(feed.getJSONObject(FEED_DATA), 5,
                                rank, user_pic_url, user_name, time, location, privacy,
                                emotions, comments, views, shares);

                        if (post != null)
                            data.add(post);
                    }
                }
            }
        }
        return data;
    }

    private FeedItemPost extractSimplePost(JSONObject data, int rank, String user_pic_url, String user_name, String time, String location, int privacy, int emotions, int comments, int views, int shares) throws JSONException
    {
        final String FEED_DATA_CONTENT_TEXT = "content_text";

        final String FEED_DATA_CONTENT_IMAGE = "content_image";
        final String FEED_DATA_CONTENT_IMAGE_HEIGHT = "height";
        final String FEED_DATA_CONTENT_IMAGE_WIDTH = "width";
        final String FEED_DATA_CONTENT_IMAGE_HEXCODE = "hexcode";
        final String FEED_DATA_CONTENT_IMAGE_URL = "url";

        // data must not be null
        if (data == null)
            return null;

        // either text or image or both should be present
        if (!(data.has(FEED_DATA_CONTENT_TEXT) && data.get(FEED_DATA_CONTENT_TEXT) instanceof String) ||
                !(data.has(FEED_DATA_CONTENT_IMAGE) && data.get(FEED_DATA_CONTENT_IMAGE) instanceof JSONObject))
            return null;

        String content_text = "";
        String content_image_url = "";
        String content_image_hex = "#dfdfdf";
        int content_image_height = 0;
        int content_image_width = 0;

        if (data.has(FEED_DATA_CONTENT_TEXT))
            content_text = data.getString(FEED_DATA_CONTENT_TEXT);

        if (data.has(FEED_DATA_CONTENT_IMAGE))
        {
            JSONObject image = data.getJSONObject(FEED_DATA_CONTENT_IMAGE);

            if (image.has(FEED_DATA_CONTENT_IMAGE_HEIGHT) && image.get(FEED_DATA_CONTENT_IMAGE_HEIGHT) instanceof Integer)
                content_image_height = image.getInt(FEED_DATA_CONTENT_IMAGE_HEIGHT);
            if (image.has(FEED_DATA_CONTENT_IMAGE_WIDTH) && image.get(FEED_DATA_CONTENT_IMAGE_WIDTH) instanceof Integer)
                content_image_width = image.getInt(FEED_DATA_CONTENT_IMAGE_WIDTH);
            if (image.has(FEED_DATA_CONTENT_IMAGE_HEXCODE) && image.get(FEED_DATA_CONTENT_IMAGE_HEXCODE) instanceof String)
                content_image_hex = image.getString(FEED_DATA_CONTENT_IMAGE_HEXCODE);
            if (image.has(FEED_DATA_CONTENT_IMAGE_URL) && image.get(FEED_DATA_CONTENT_IMAGE_URL) instanceof String)
                content_image_url = image.getString(FEED_DATA_CONTENT_IMAGE_URL);
        }

        FeedItemPost feedItem = new FeedItemPost(rank,
                new BoldText(user_name),
                new BoldText(content_text),
                time, location, privacy, user_pic_url,
                new Image(content_image_url, content_image_height, content_image_width, content_image_hex),
                emotions, comments, shares, views);

        System.out.println("\nNews Feed Post");
        System.out.println(feedItem);
        return feedItem;
    }

    private FeedItemShared extractSharedPost(JSONObject data, JSONObject shared_from, int rank, String user_pic_url, String user_name, String time, String location, int privacy, int emotions, int comments, int views, int shares) throws JSONException
    {
        final String FEED_USER_PIC_URL = "user_pic_url";
        final String FEED_USER_NAME = "user_name";
        final String FEED_TIME = "time";
        final String FEED_LOCATION = "location";
        final String FEED_PRIVACY = "privacy";

        String time2 = "1 min ago";
        String location2 = "";
        int privacy2 = 1;


        // data must not be null
        if (data == null)
            return null;

        //shared from must not be null
        if (shared_from == null)
            return null;

        // user name or user's pic is missing in shared user throw exception
        if (!(shared_from.has(FEED_USER_NAME) && shared_from.get(FEED_USER_NAME) instanceof String &&
                shared_from.has(FEED_USER_PIC_URL) && shared_from.get(FEED_USER_PIC_URL) instanceof String))
            throw new RuntimeException(FEED_USER_NAME + " or " + FEED_USER_PIC_URL + " missing is Shared User at position=" + rank);

        String user_name2 = shared_from.getString(FEED_USER_NAME);
        String user_pic_url2 = shared_from.getString(FEED_USER_PIC_URL);

        if (shared_from.has(FEED_TIME) && shared_from.get(FEED_TIME) instanceof String)
            time2 = shared_from.getString(FEED_TIME);

        if (shared_from.has(FEED_LOCATION) && shared_from.get(FEED_LOCATION) instanceof String)
            location2 = shared_from.getString(FEED_LOCATION);

        if (shared_from.has(FEED_PRIVACY) && shared_from.get(FEED_PRIVACY) instanceof Integer)
            privacy2 = shared_from.getInt(FEED_PRIVACY);

        FeedItemPost post = extractSimplePost(data, rank, user_pic_url, user_name, time, location, privacy, emotions, comments, views, shares);
        if (post == null)
            return null;

        FeedItemShared feedItemShared = new FeedItemShared(rank,
                post.getTitle().getTitle(),
                post.getDescTextText(),
                post.getTitle().getTime(),
                post.getTitle().getLocation(),
                post.getTitle().getPrivacy(),
                post.getTitle().getUserPic(),
                post.getPostImage(),
                new BoldText(user_name2),
                time2,
                location2,
                privacy2,
                user_pic_url2,
                emotions,
                comments,
                views,
                shares);

        System.out.println("\nNews Feed Shared Post");
        System.out.println(feedItemShared);

        return feedItemShared;
    }

    private FeedItemAlbum extractAlbum(JSONObject data, int no, int rank, String user_pic_url, String user_name, String time, String location, int privacy, int emotions, int comments, int views, int shares) throws JSONException
    {
        final String FEED_DATA_IMAGE_ONE_URL = "image_one_url";
        final String FEED_DATA_IMAGE_TWO_URL = "image_two_url";
        final String FEED_DATA_IMAGE_THREE_URL = "image_three_url";
        final String FEED_DATA_IMAGE_FOUR_URL = "image_four_url";
        final String FEED_DATA_IMAGE_COUNT = "count";

        if (data == null)
            return null;

        String image_one = "", image_two = "", image_three = "", image_four = "";
        List<String> imagesUrls = new ArrayList<>();
        if (no == 2)
        {
            if (data.has(FEED_DATA_IMAGE_ONE_URL) && data.get(FEED_DATA_IMAGE_ONE_URL) instanceof String)
                image_one = data.getString(FEED_DATA_IMAGE_ONE_URL);

            if (data.has(FEED_DATA_IMAGE_TWO_URL) && data.get(FEED_DATA_IMAGE_TWO_URL) instanceof String)
                image_two = data.getString(FEED_DATA_IMAGE_TWO_URL);

            imagesUrls.add(image_one);
            imagesUrls.add(image_two);
        } else if (no == 3)
        {
            if (data.has(FEED_DATA_IMAGE_ONE_URL) && data.get(FEED_DATA_IMAGE_ONE_URL) instanceof String)
                image_one = data.getString(FEED_DATA_IMAGE_ONE_URL);

            if (data.has(FEED_DATA_IMAGE_TWO_URL) && data.get(FEED_DATA_IMAGE_TWO_URL) instanceof String)
                image_two = data.getString(FEED_DATA_IMAGE_TWO_URL);

            if (data.has(FEED_DATA_IMAGE_THREE_URL) && data.get(FEED_DATA_IMAGE_THREE_URL) instanceof String)
                image_three = data.getString(FEED_DATA_IMAGE_THREE_URL);

            imagesUrls.add(image_one);
            imagesUrls.add(image_two);
            imagesUrls.add(image_three);
        } else if (no == 4)
        {
            if (data.has(FEED_DATA_IMAGE_ONE_URL) && data.get(FEED_DATA_IMAGE_ONE_URL) instanceof String)
                image_one = data.getString(FEED_DATA_IMAGE_ONE_URL);

            if (data.has(FEED_DATA_IMAGE_TWO_URL) && data.get(FEED_DATA_IMAGE_TWO_URL) instanceof String)
                image_two = data.getString(FEED_DATA_IMAGE_TWO_URL);

            if (data.has(FEED_DATA_IMAGE_THREE_URL) && data.get(FEED_DATA_IMAGE_THREE_URL) instanceof String)
                image_three = data.getString(FEED_DATA_IMAGE_THREE_URL);

            if (data.has(FEED_DATA_IMAGE_FOUR_URL) && data.get(FEED_DATA_IMAGE_FOUR_URL) instanceof String)
                image_four = data.getString(FEED_DATA_IMAGE_FOUR_URL);

            imagesUrls.add(image_one);
            imagesUrls.add(image_two);
            imagesUrls.add(image_three);
            imagesUrls.add(image_four);
        } else if (no > 4)
        {
            int count = 5;

            if (data.has(FEED_DATA_IMAGE_ONE_URL) && data.get(FEED_DATA_IMAGE_ONE_URL) instanceof String)
                image_one = data.getString(FEED_DATA_IMAGE_ONE_URL);

            if (data.has(FEED_DATA_IMAGE_TWO_URL) && data.get(FEED_DATA_IMAGE_TWO_URL) instanceof String)
                image_two = data.getString(FEED_DATA_IMAGE_TWO_URL);

            if (data.has(FEED_DATA_IMAGE_THREE_URL) && data.get(FEED_DATA_IMAGE_THREE_URL) instanceof String)
                image_three = data.getString(FEED_DATA_IMAGE_THREE_URL);

            if (data.has(FEED_DATA_IMAGE_FOUR_URL) && data.get(FEED_DATA_IMAGE_FOUR_URL) instanceof String)
                image_four = data.getString(FEED_DATA_IMAGE_FOUR_URL);

            if (data.has(FEED_DATA_IMAGE_COUNT) && data.get(FEED_DATA_IMAGE_COUNT) instanceof Integer)
                count = data.getInt(FEED_DATA_IMAGE_COUNT);

            imagesUrls.add(image_one);
            imagesUrls.add(image_two);
            imagesUrls.add(image_three);
            imagesUrls.add(image_four);
        }

        FeedItemAlbum album = new FeedItemAlbum(rank, new BoldText(user_name),
                time, location, privacy, user_pic_url, imagesUrls,
                emotions, comments, views, shares);

        System.out.println("\nNews Feed Album Post");
        System.out.println(album.toString());
        return album;
    }

    private FindFriendData extractFriends(JSONObject friends) throws JSONException
    {
        FindFriendData data;

        final String FRIENDS_REQUEST = "request";
        final String FRIENDS_SUGGESTION = "suggestions";


        if (friends == null)
            return null;

        // either request or suggestion or both should be present
        if (!(friends.has(FRIENDS_REQUEST) && friends.get(FRIENDS_REQUEST) instanceof JSONArray) ||
                !(friends.has(FRIENDS_SUGGESTION) && friends.get(FRIENDS_SUGGESTION) instanceof JSONArray))
            return null;

        List<Friend> friendRequests = new ArrayList<>();
        if (friends.has(FRIENDS_REQUEST))
        {
            extractFriendList(friends, friendRequests, FRIENDS_REQUEST);
            System.out.println("\nFriends request");
            System.out.println(friendRequests);
        }

        List<Friend> friendSuggestions = new ArrayList<>();
        if (friends.has(FRIENDS_SUGGESTION))
        {
            extractFriendList(friends, friendSuggestions, FRIENDS_SUGGESTION);
            System.out.println("\nFriends suggestions");
            System.out.println(friendSuggestions);
        }

        data = new FindFriendData(friendRequests, friendSuggestions);
        return data;
    }

    private void extractFriendList(JSONObject friends, List<Friend> list, String type) throws JSONException
    {
        final String FRIENDS_FIRST_NAME = "first_name";
        final String FRIENDS_LAST_NAME = "last_name";
        final String FRIENDS_USER_PIC_URL = "user_pic_url";
        final String FRIENDS_MUTUAL_FRIENDS = "mutual_friends";

        JSONArray requests = friends.getJSONArray(type);
        for (int i = 0; i < requests.length(); i++)
        {
            JSONObject request = requests.getJSONObject(i);
            if (!(request.has(FRIENDS_FIRST_NAME) && request.get(FRIENDS_FIRST_NAME) instanceof String &&
                    request.has(FRIENDS_USER_PIC_URL) && request.get(FRIENDS_USER_PIC_URL) instanceof String))
                throw new RuntimeException(FRIENDS_FIRST_NAME + " or " + FRIENDS_USER_PIC_URL + " missing in Friends " + type + " at position=" + i + 1);

            String first_name = request.getString(FRIENDS_FIRST_NAME);
            String user_pic_url = request.getString(FRIENDS_USER_PIC_URL);

            String last_name = "";
            int mutual_friends = 0;

            if (request.has(FRIENDS_LAST_NAME) && request.get(FRIENDS_LAST_NAME) instanceof String)
                last_name = request.getString(FRIENDS_LAST_NAME);

            if (request.has(FRIENDS_MUTUAL_FRIENDS) && request.get(FRIENDS_MUTUAL_FRIENDS) instanceof Integer)
                mutual_friends = request.getInt(FRIENDS_MUTUAL_FRIENDS);

            list.add(new Friend(first_name, last_name, mutual_friends, new Image(user_pic_url, 100, 100, "#dfdfdf")));
        }
    }

    private List<Noti> extractNotifications(JSONArray notifications) throws JSONException
    {
        final String NOTI_RANK = "rank";
        final String NOTI_TITLE = "title";
        final String NOTI_USER_PIC_URL = "user_pic_url";
        final String NOTI_TIME = "time";
        final String NOTI_ICON = "icon";
        final String NOTI_READ = "read";

        if (notifications == null)
            return null;

        List<Noti> notificationsList = new ArrayList<>();
        for (int i = 0; i < notifications.length(); i++)
        {
            JSONObject notification = notifications.getJSONObject(i);
            if (!(notification.has(NOTI_TITLE) && notification.get(NOTI_TITLE) instanceof String &&
                    notification.has(NOTI_USER_PIC_URL) && notification.get(NOTI_USER_PIC_URL) instanceof String))
                throw new RuntimeException(NOTI_TITLE + " or " + NOTI_USER_PIC_URL + " is missing in Notifications at position=" + i + 1);

            String title = notification.getString(NOTI_TITLE);
            String user_pic_url = notification.getString(NOTI_USER_PIC_URL);
            int rank = i + 1;
            String time = "2 min ago";
            int icon = 1;
            boolean read = false;

            if (notification.has(NOTI_RANK) && notification.get(NOTI_RANK) instanceof Integer)
                rank = notification.getInt(NOTI_RANK);

            if (notification.has(NOTI_TIME) && notification.get(NOTI_TIME) instanceof String)
                time = notification.getString(NOTI_TIME);

            if (notification.has(NOTI_ICON) && notification.get(NOTI_ICON) instanceof Integer)
                icon = notification.getInt(NOTI_ICON);

            if (notification.has(NOTI_READ) && notification.get(NOTI_READ) instanceof Boolean)
                read = notification.getBoolean(NOTI_READ);

            notificationsList.add(new Noti(rank, title, user_pic_url, time, icon, read));
        }

        System.out.println("\nNotifications");
        System.out.println(notificationsList.toString());

        return notificationsList;
    }

    private String getSampleJson1()
    {
        return "{\"data\":{\"news_feed\":{\"story\":[{\"first_name\":\"sample\",\"last_name\":\"sample\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample2\",\"last_name\":\"sample2\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample3\",\"last_name\":\"sample3\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample4\",\"last_name\":\"sample4\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false}],\"feed\":[{\"rank\":1,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"time\":\"5 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"comments\":33,\"views\":56,\"shares\":133,\"data\":{\"content_text\":\"blah blah blah blah blah blah blah\",\"content_image\":{\"height\":300,\"width\":500,\"hexcode\":\"#dfdfdf\",\"url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}},{\"rank\":2,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"data\":{\"content_text\":\"Only blah blah blah blah blah blah blah\"}},{\"rank\":3,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"content_text\":\"Only only only blah blah blah blah blah blah blah\"}},{\"rank\":4,\"type\":\"shared\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana shared Dilpreet Singh's photo\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"shared_from\":{\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Dilpreet Singh\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1},\"data\":{\"content_text\":\"Only blah blah blah blah blah blah blah\",\"content_image\":{\"height\":300,\"width\":500,\"hexcode\":\"#dfdfdf\",\"url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}},{\"rank\":5,\"type\":\"album_of_two\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":6,\"type\":\"album_of_three\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":7,\"type\":\"album_of_four\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_four_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":8,\"type\":\"album_of_many\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"count\":7,\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_four_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}]},\"friends\":{\"request\":[{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6}],\"suggestions\":[{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6}]},\"notification\":[{\"rank\":1,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":2,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":3,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":4,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":5,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false}]}}";
    }
}
