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
import com.aatishrana.fakefb.model.FriendStoryItem;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.model.TextStyle;
import com.aatishrana.fakefb.newsFeed.model.FeedItem;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPost;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPostBox;
import com.aatishrana.fakefb.newsFeed.model.FeedItemStories;
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
        drawable.setColor(Color.parseColor("#f7f7f7"));
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
        for (int i = 0; i < 10; i++)
        {
            String firstName = "User" + (i + 1);
            String lastName = "User" + (i + 1);

            Image image = new Image(getImageUrl(i % 3 == 0), (int) H.pToD(getContext(), 100), (int) H.pToD(getContext(), 100), (i % 2 == 0 ? "#acacac" : "#0000ff"));

            friendStoryItemList.add(new FriendStoryItem(firstName, lastName, image, (i % 3 == 0)));
        }
        FeedItem stories = new FeedItemStories(friendStoryItemList, 1);

        FeedItem postBox = new FeedItemPostBox("https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg", 2);


        FeedItem post1 = new FeedItemPost(3,
                new Text("UberEATS:Food Delivery", new TextStyle(9, 20, "normal", "#ff0000")),
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
                new Text("Rajnikant v/s CID jokes"),
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
                new Text("Spiderman"),
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
                null);

        data.add(stories);
        data.add(postBox);
        data.add(post1);
        data.add(post2);
        data.add(post3);
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
