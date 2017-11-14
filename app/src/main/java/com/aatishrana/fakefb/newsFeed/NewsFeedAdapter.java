package com.aatishrana.fakefb.newsFeed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.newsFeed.model.FeedItem;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPost;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPostBox;
import com.aatishrana.fakefb.newsFeed.model.FeedItemStories;

import java.util.List;

/**
 * Created by Aatish on 11/12/2017.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<FeedItem> data;
    private NewsFeedClickListener clickListener;

    public NewsFeedAdapter(List<FeedItem> data, NewsFeedClickListener clickListener)
    {
        this.data = data;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType)
        {
            case R.layout.feed_item_stories:
                return new FeedItemStories.ViewHolder(inflater.inflate(viewType, parent, false), clickListener);
            case R.layout.feed_item_post_box:
                return new FeedItemPostBox.ViewHolder(inflater.inflate(viewType, parent, false), clickListener);
            case R.layout.feed_item_post:
                return new FeedItemPost.ViewHolder(inflater.inflate(viewType, parent, false), clickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);
        switch (viewType)
        {
            case R.layout.feed_item_stories:
                FeedItemStories.ViewHolder storiesHolder = (FeedItemStories.ViewHolder) holder;
                storiesHolder.bind(data.get(position));
                break;
            case R.layout.feed_item_post_box:
                FeedItemPostBox.ViewHolder postBoxHolder = (FeedItemPostBox.ViewHolder) holder;
                postBoxHolder.bind(data.get(position));
                break;
            case R.layout.feed_item_post:
                FeedItemPost.ViewHolder postViewHolder = (FeedItemPost.ViewHolder) holder;
                postViewHolder.bind(data.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return data.get(position).feedLayoutType();
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public interface NewsFeedClickListener
    {
        void createNewPost();

        void openAllStories();

        void openPost();
    }

}
