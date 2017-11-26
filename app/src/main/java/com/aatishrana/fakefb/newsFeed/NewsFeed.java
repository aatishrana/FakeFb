package com.aatishrana.fakefb.newsFeed;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aatishrana.fakefb.MainActivity;
import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.base.BasePresenterFragment;
import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.model.BoldText;
import com.aatishrana.fakefb.model.FriendStoryItem;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.model.TextStyle;
import com.aatishrana.fakefb.newsFeed.model.FeedItem;
import com.aatishrana.fakefb.newsFeed.model.FeedItemAlbum;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPost;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPostBox;
import com.aatishrana.fakefb.newsFeed.model.FeedItemShared;
import com.aatishrana.fakefb.newsFeed.model.FeedItemStories;
import com.aatishrana.fakefb.newsFeed.presenter.NewsFeedPresenter;
import com.aatishrana.fakefb.newsFeed.presenter.NewsFeedPresenterFactory;
import com.aatishrana.fakefb.newsFeed.presenter.NewsFeedView;
import com.aatishrana.fakefb.newsFeed.presenter.NewsFeedViewModel;
import com.aatishrana.fakefb.utils.Const;
import com.aatishrana.fakefb.utils.H;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aatish Rana on 07-Nov-17.
 */

public class NewsFeed extends BasePresenterFragment<NewsFeedPresenter, NewsFeedView> implements NewsFeedAdapter.NewsFeedClickListener, NewsFeedView
{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NewsFeedAdapter adapter;
    private NewsFeedPresenter presenter;

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

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.getData();
    }

    private void initViews(View view)
    {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#dfdfdf"));
        drawable.setSize(300, 30);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_news_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new NewsFeedDecorator(drawable));
        adapter = new NewsFeedAdapter(NewsFeed.this);
        recyclerView.setAdapter(adapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                presenter.cleanCache();
                presenter.getData();
            }
        });
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

    @NonNull
    @Override
    protected PresenterFactory<NewsFeedPresenter> getPresenterFactory()
    {
        return new NewsFeedPresenterFactory(((MainActivity) getActivity()).getRepository());
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull NewsFeedPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public void render(NewsFeedViewModel viewModel)
    {
        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);
        if (viewModel.getCache() != null && !viewModel.getCache().isEmpty() && !viewModel.isLoading())
        {
            //show data
            adapter.setData(viewModel.getCache());
            adapter.notifyDataSetChanged();
        } else if (viewModel.getCache() == null && viewModel.isLoading())
        {
            //show leading
            refreshLayout.setRefreshing(true);
        } else if (viewModel.getCache() == null && !viewModel.isLoading())
        {
            //show error
            Toast.makeText(getContext(), "Fetching news feed failed", Toast.LENGTH_SHORT).show();
        } else if (viewModel.getCache() != null && viewModel.getCache().isEmpty() && !viewModel.isLoading())
        {
            //empty data
            Toast.makeText(getContext(), "No news feed data", Toast.LENGTH_SHORT).show();
        }
    }
}
