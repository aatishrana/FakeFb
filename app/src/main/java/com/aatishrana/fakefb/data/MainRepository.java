package com.aatishrana.fakefb.data;

import com.aatishrana.fakefb.model.FriendStoryItem;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.utils.H;

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

    public void processData()
    {
        try
        {
            final String DATA = "data";
            final String NEWS_FEED = "news_feed";
            final String FRIENDS = "friends";
            final String NOTIFICATION = "notification";

            final String errorMsg = " not present in json source";

            String sampleJson = getSampleJson1();
            JSONObject response = new JSONObject(sampleJson);

            if (!response.has(DATA) && response.get(DATA) instanceof JSONObject)
                throw new RuntimeException(DATA + errorMsg);
            JSONObject data = response.getJSONObject(DATA);

            if (!data.has(NEWS_FEED) && data.get(NEWS_FEED) instanceof JSONObject)
                throw new RuntimeException(NEWS_FEED + errorMsg);
            extractNewsFeed(data.getJSONObject(NEWS_FEED));

            if (!data.has(FRIENDS) && data.get(FRIENDS) instanceof JSONObject)
                throw new RuntimeException(FRIENDS + errorMsg);
            extractFriends(data.getJSONObject(FRIENDS));

            if (!data.has(NOTIFICATION) && data.get(NOTIFICATION) instanceof JSONArray)
                throw new RuntimeException(NOTIFICATION + errorMsg);
            extractNotifications(data.getJSONArray(NOTIFICATION));

        } catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }


    private void extractNewsFeed(JSONObject news_feed) throws JSONException
    {
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

        final String FEED_DATA_CONTENT_TEXT = "content_text";

        final String FEED_DATA_CONTENT_IMAGE = "content_image";
        final String FEED_DATA_CONTENT_IMAGE_HEIGHT = "height";
        final String FEED_DATA_CONTENT_IMAGE_WIDTH = "width";
        final String FEED_DATA_CONTENT_IMAGE_HEXCODE = "hexcode";
        final String FEED_DATA_CONTENT_IMAGE_URL = "url";

        final String FEED_DATA_IMAGE_ONE_URL = "image_one_url";
        final String FEED_DATA_IMAGE_TWO_URL = "image_two_url";
        final String FEED_DATA_IMAGE_THREE_URL = "image_three_url";
        final String FEED_DATA_IMAGE_FOUR_URL = "image_four_url";
        final String FEED_DATA_IMAGE_COUNT = "count";


        if (news_feed.has(STORY) && news_feed.get(STORY) instanceof JSONArray)
        {
            List<FriendStoryItem> friendStoryItemList = new ArrayList<>();
            JSONArray stories = news_feed.getJSONArray(STORY);
            for (int i = 0; i < stories.length(); i++)
            {
                JSONObject story = stories.getJSONObject(i);

                //logic, required fields
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
            System.out.println(friendStoryItemList.toString());
        }

        if (news_feed.has(FEED) && news_feed.get(FEED) instanceof JSONArray)
        {

        }

    }

    private void extractFriends(JSONObject friends) throws JSONException
    {

    }

    private void extractNotifications(JSONArray notifications) throws JSONException
    {

    }


    private String getSampleJson1()
    {
        return "{\"data\":{\"news_feed\":{\"story\":[{\"first_name\":\"sample\",\"last_name\":\"sample\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample2\",\"last_name\":\"sample2\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample3\",\"last_name\":\"sample3\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample4\",\"last_name\":\"sample4\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false}],\"feed\":[{\"rank\":1,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"time\":\"5 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"comments\":33,\"views\":56,\"shares\":133,\"data\":{\"content_text\":\"blah blah blah blah blah blah blah\",\"content_image\":{\"height\":300,\"width\":500,\"hexcode\":\"#dfdfdf\",\"url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}},{\"rank\":2,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"data\":{\"content_text\":\"Only blah blah blah blah blah blah blah\"}},{\"rank\":3,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"content_text\":\"Only only only blah blah blah blah blah blah blah\"}},{\"rank\":4,\"type\":\"shared\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana shared Dilpreet Singh's photo\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"shared_from\":{\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Dilpreet Singh\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1},\"data\":{\"content_text\":\"Only blah blah blah blah blah blah blah\",\"content_image\":{\"height\":300,\"width\":500,\"hexcode\":\"#dfdfdf\",\"url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}},{\"rank\":5,\"type\":\"album_of_two\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":6,\"type\":\"album_of_three\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":7,\"type\":\"album_of_four\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_four_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":8,\"type\":\"album_of_many\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"count\":7,\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_four_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}]},\"friends\":{\"request\":[{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6}],\"suggestions\":[{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6}]},\"notification\":[{\"rank\":1,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":2,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":3,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":4,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":5,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false}]}}";
    }
}
