package com.aatishrana.fakefb.newsFeed;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.BoldText;
import com.aatishrana.fakefb.model.FriendStoryItem;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.model.TextStyle;
import com.aatishrana.fakefb.newsFeed.model.FeedItem;
import com.aatishrana.fakefb.newsFeed.model.FeedItemAlbum;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPost;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPostBox;
import com.aatishrana.fakefb.newsFeed.model.FeedItemStories;
import com.aatishrana.fakefb.utils.Const;
import com.aatishrana.fakefb.utils.H;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aatish Rana on 07-Nov-17.
 */

public class NewsFeed extends Fragment implements NewsFeedAdapter.NewsFeedClickListener
{

    private RecyclerView recyclerView;
    private NewsFeedAdapter adapter;

    public NewsFeed()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view)
    {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#dfdfdf"));
        drawable.setSize(300, 30);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_news_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new NewsFeedDecorator(drawable));
        adapter = new NewsFeedAdapter(getSampleData(), NewsFeed.this);
        recyclerView.setAdapter(adapter);
    }

    private List<FeedItem> getSampleData()
    {
        List<FeedItem> data = new ArrayList<>();


        List<FriendStoryItem> friendStoryItemList = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            String firstName = "User" + (i + 1);
            String lastName = "User" + (i + 1);

            Image image = new Image(getImageUrl(i % 3 == 0), (int) H.pToD(getContext(), 100), (int) H.pToD(getContext(), 100), (i % 2 == 0 ? "#acacac" : "#0000ff"));

            friendStoryItemList.add(new FriendStoryItem(firstName, lastName, image, (i % 3 == 0)));
        }
        FeedItem stories = new FeedItemStories(friendStoryItemList, 1);

        FeedItem postBox = new FeedItemPostBox("https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg", 2);


        FeedItem post1 = new FeedItemPost(3,
                new BoldText("UberEATS:Food Delivery"),
                new Text("Not just Delivery, Uber Delivery. From Momos to Biryani, get a taste of the new UberEATS app in Gurugram. Download the app now and get all your deliveries at just 1"),
                "4 hrs",
                "",
                0,
                "https://lh3.googleusercontent.com/-M679h76FAjLJI6sv9VYUGKLXK7jdG1_95XRV8DJslC1e3_2QAesO7H7jDtDYEObTc3w=w300",
                new Image(
                        "https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg",
                        300,
                        500,
                        "#000000"
                ),
                64,
                52);

        FeedItem post2 = new FeedItemPost(4,
                new BoldText("Rajnikant v/s CID jokes"),
                new Text(""),
                "2 hrs",
                "",
                0,
                "https://lh3.ggpht.com/mUTxFiX_9_jvQLvMEA6Tm22wbprSDeo2RSX5gDRiQm6TW0qzsZ4w6vU98LVh6f9pL8qM=w300",
                new Image(
                        "https://pbs.twimg.com/media/CHPct0BUkAASr6b.jpg",
                        300,
                        500,
                        "#000000"
                ));

        FeedItem post3 = new FeedItemPost(5,
                new BoldText("Spiderman"),
                new Text("Spider-Man, Spider-Man, \n" +
                        "Does whatever a spider can \n" +
                        "Spins a web, any size, \n" +
                        "Catches thieves just like flies \n" +
                        "Look Out! \n" +
                        "Here comes the Spider-Man.\n"),
                "5 min",
                "",
                0,
                "https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg",
                null,
                20,
                39,
                590);

        FeedItem post4 = new FeedItemPost(6,
                new Text("Bill Gates updated his cover photo", new TextStyle(0, 10, Const.BOLD)),
                new Text(""),
                "7 hrs",
                "",
                0,
                "https://pbs.twimg.com/profile_images/889736688624312321/xVAFH9ZH_400x400.jpg",
                new Image(
                        "https://marketplace.canva.com/MAB8O5F76AI/1/0/thumbnail_large/canva-golden-gate-bridge-facebook-cover-MAB8O5F76AI.jpg",
                        209,
                        550,
                        "#000000"
                ),
                290);

        List<String> sampleImages = new ArrayList<>();
        sampleImages.add("https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg");
        sampleImages.add("https://pre00.deviantart.net/3f00/th/pre/i/2011/221/b/0/spider_man_in_times_square_by_freshtofu-d45zs0j.jpg");

        FeedItem post5 = new FeedItemAlbum(7,
                new Text("Aatish Rana uploaded a new album", new TextStyle(0, 11, Const.BOLD)),
                "7 hrs",
                "",
                0,
                "https://pbs.twimg.com/profile_images/889736688624312321/xVAFH9ZH_400x400.jpg",
                sampleImages,
                43);

        List<String> sampleImages2 = new ArrayList<>();
        sampleImages2.add("http://www.litizen.com/uploads/story_images/sunny%20leone%20porn%20star%20litizen.com.png");
        sampleImages2.add("https://photos.filmibeat.com/ph-big/2016/07/sunny-leone-on-set-of-calender-photoshoot-for-manforce_1468481967110.jpg");
        sampleImages2.add("https://i.pinimg.com/originals/8a/e5/3a/8ae53a73483bdd77d388d75ee70d7749.jpg");
        sampleImages2.add("https://images-na.ssl-images-amazon.com/images/I/51K62L9ZjtL.jpg");

        FeedItem post6 = new FeedItemAlbum(8,
                new Text("Dilpreet Singh uploaded a new album", new TextStyle(0, 15, Const.BOLD)),
                "2 min",
                "",
                0,
                "https://pbs.twimg.com/profile_images/889736688624312321/xVAFH9ZH_400x400.jpg",
                sampleImages2,
                98);

        data.add(stories);
        data.add(postBox);
        data.add(post1);
        data.add(post2);
        data.add(post3);
        data.add(post4);
        data.add(post5);
        data.add(post6);
        //todo add comparator
        return data;
    }

    private String getImageUrl(boolean value)
    {
//        return "https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-1/c0.9.40.40/p40x40/22549840_905255206299578_6244307732919157017_n.jpg?oh=4812e4376fbf8fd2e0ddb3918228c2d6&oe=5AA2A2D7";
        if (value)
            return "https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg";
        else
            return "https://pre00.deviantart.net/3f00/th/pre/i/2011/221/b/0/spider_man_in_times_square_by_freshtofu-d45zs0j.jpg";
    }

    @Override
    public void createNewPost()
    {
        Toast.makeText(getContext(), "Create new post", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openAllStories()
    {
        Toast.makeText(getContext(), "Open stories ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openPost()
    {

    }
}
