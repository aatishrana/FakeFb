package com.aatishrana.fakefb.newsFeed.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.newsFeed.NewsFeedAdapter;

/**
 * Created by Aatish on 11/12/2017.
 */

public class FeedItemPostBox implements FeedItem
{
    private final String userPic;
    private final int rank;

    public FeedItemPostBox(String userPic, int rank)
    {
        this.userPic = userPic;
        this.rank = rank;
    }

    public String getUserPic()
    {
        return userPic;
    }

    @Override
    public int rank()
    {
        return this.rank;
    }

    @Override
    public int feedLayoutType()
    {
        return R.layout.feed_item_post_box;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private NewsFeedAdapter.NewsFeedClickListener clickListener;

        public ViewHolder(View itemView, NewsFeedAdapter.NewsFeedClickListener clickListener)
        {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(ViewHolder.this);
        }

        public void bind(FeedItem feedItem)
        {

        }

        @Override
        public void onClick(View v)
        {
            if (clickListener != null)
                clickListener.createNewPost();
        }
    }
}
