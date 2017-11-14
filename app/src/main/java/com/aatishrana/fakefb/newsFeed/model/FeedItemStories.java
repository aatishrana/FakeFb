package com.aatishrana.fakefb.newsFeed.model;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.FriendStoryItem;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.newsFeed.NewsFeedAdapter;
import com.aatishrana.fakefb.utils.CropCircleTransformation;
import com.aatishrana.fakefb.utils.DynamicHeightImageView;
import com.aatishrana.fakefb.utils.SizableColorDrawable;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aatish on 11/12/2017.
 */
public class FeedItemStories implements FeedItem
{
    private final List<FriendStoryItem> friendStoryItemList;
    private final int rank;

    public FeedItemStories(List<FriendStoryItem> friendStoryItemList, int rank)
    {
        this.friendStoryItemList = friendStoryItemList;
        this.rank = rank;
    }

    public List<FriendStoryItem> getFriendStoryItemList()
    {
        return friendStoryItemList;
    }

    @Override
    public int rank()
    {
        return this.rank;
    }

    @Override
    public int feedLayoutType()
    {
        return R.layout.feed_item_stories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private NewsFeedAdapter.NewsFeedClickListener clickListener;
        private RecyclerView recyclerView;

        public ViewHolder(View itemView, NewsFeedAdapter.NewsFeedClickListener clickListener)
        {
            super(itemView);
            this.clickListener = clickListener;
            recyclerView = (RecyclerView) itemView.findViewById(R.id.feed_item_stories_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            itemView.setOnClickListener(ViewHolder.this);

        }

        public void bind(FeedItem feedItem)
        {
            FeedItemStories stories = (FeedItemStories) feedItem;
            if (stories != null)
                recyclerView.setAdapter(new StoriesAdapter(stories.getFriendStoryItemList()));
        }

        @Override
        public void onClick(View v)
        {
            if (clickListener != null)
                clickListener.openAllStories();
        }
    }

    public static class StoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<FriendStoryItem> data;
        private int EXTRA_ITEMS = 2;

        public StoriesAdapter(List<FriendStoryItem> data)
        {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            switch (viewType)
            {
                case R.layout.story_item_new:
                    return new NewStoryViewHolder(inflater.inflate(viewType, parent, false));
                case R.layout.story_item_me:
                    return new MyStoryViewHolder(inflater.inflate(viewType, parent, false));
                case R.layout.story_item:
                    return new StoryViewHolder(inflater.inflate(viewType, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            int viewType = getItemViewType(position);
            switch (viewType)
            {
                case R.layout.story_item_me:
                    MyStoryViewHolder myStoryHolder = (MyStoryViewHolder) holder;
                    myStoryHolder.bind("");
                    break;
                case R.layout.story_item:
                    StoryViewHolder itemHolder = (StoryViewHolder) holder;
                    itemHolder.bind(data.get(position - EXTRA_ITEMS));
                    break;
            }
        }

        @Override
        public int getItemViewType(int position)
        {
            if (position == 0)
                return R.layout.story_item_new;
            else if (position == 1)
                return R.layout.story_item_me;
            else
                return R.layout.story_item;
        }

        @Override
        public int getItemCount()
        {
            return this.data.size() + EXTRA_ITEMS;
        }

        private class MyStoryViewHolder extends RecyclerView.ViewHolder
        {

            public MyStoryViewHolder(View itemView)
            {
                super(itemView);
            }

            public void bind(String s)
            {

            }
        }

        private class StoryViewHolder extends RecyclerView.ViewHolder
        {
            TextView tvName;
            DynamicHeightImageView imageView;

            public StoryViewHolder(View itemView)
            {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.story_item_tv_name);
                imageView = (DynamicHeightImageView) itemView.findViewById(R.id.story_item_iv_dp);
            }

            public void bind(FriendStoryItem friendStoryItem)
            {
                tvName.setText(friendStoryItem.getFirstName());

                Image image = friendStoryItem.getUserImage();
                if (image != null && image.isValid())
                {
                    // Calculate the image ratio of the loaded bitmap
                    float ratio = (float) image.getHeight() / (float) image.getWidth();

                    imageView.setHeightRatio(ratio);

                    Picasso
                            .with(itemView.getContext())
                            .load(image.getUrl())
                            .transform(new CropCircleTransformation())
                            .placeholder(new SizableColorDrawable(image.getColorCode(), image.getWidth(), image.getHeight()))
                            .into(imageView);
                }
            }
        }

        private class NewStoryViewHolder extends RecyclerView.ViewHolder
        {

            public NewStoryViewHolder(View itemView)
            {
                super(itemView);
            }
        }
    }
}
