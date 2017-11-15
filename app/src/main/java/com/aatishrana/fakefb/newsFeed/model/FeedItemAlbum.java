package com.aatishrana.fakefb.newsFeed.model;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.newsFeed.NewsFeedAdapter;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderEmoBar;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderTitleBar;
import com.aatishrana.fakefb.utils.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aatish Rana on 15-Nov-17.
 */

public class FeedItemAlbum implements FeedItem
{
    private final int rank;
    private final FeedItemBaseTitle title;
    private final FeedItemBaseEmo emo;
    private final List<String> images;

    public FeedItemAlbum(int rank, Text title, String time, String location, int privacy, String userPic, List<String> images, long totalEmotions, long totalComments, long totalShares, long totalViews)
    {
        this.rank = rank;
        this.images = images;

        //instance created rather then injected to avoid NPE even when user does'nt want to give emo, location, time etc
        this.title = new FeedItemBaseTitle(title, time, location, privacy, userPic);
        this.emo = new FeedItemBaseEmo(totalEmotions, totalComments, totalShares, totalViews);
    }

    public FeedItemAlbum(int rank, Text title, String time, String location, int privacy, String userPic, List<String> images, long totalEmotions, long totalComments, long totalShares)
    {
        this(rank, title, time, location, privacy, userPic, images, totalEmotions, totalComments, totalShares, 0);
    }

    public FeedItemAlbum(int rank, Text title, String time, String location, int privacy, String userPic, List<String> images, long totalEmotions, long totalComments)
    {
        this(rank, title, time, location, privacy, userPic, images, totalEmotions, totalComments, 0, 0);
    }

    public FeedItemAlbum(int rank, Text title, String time, String location, int privacy, String userPic, List<String> images, long totalEmotions)
    {
        this(rank, title, time, location, privacy, userPic, images, totalEmotions, 0, 0, 0);
    }

    public FeedItemAlbum(int rank, Text title, String time, String location, int privacy, String userPic, List<String> images)
    {
        this(rank, title, time, location, privacy, userPic, images, 0, 0, 0, 0);
    }

    public FeedItemBaseTitle getTitle()
    {
        return title;
    }

    public FeedItemBaseEmo getEmo()
    {
        return emo;
    }

    @Override
    public int rank()
    {
        return this.rank;
    }

    @Override
    public int feedLayoutType()
    {
        return R.layout.feed_item_post_album;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private NewsFeedAdapter.NewsFeedClickListener clickListener;

        private BaseViewHolderTitleBar titleBar;//composition
        private BaseViewHolderEmoBar emoBar;//composition
        private LinearLayout linearLayout;

        public ViewHolder(View itemView, NewsFeedAdapter.NewsFeedClickListener clickListener)
        {
            super(itemView);
            this.titleBar = new BaseViewHolderTitleBar(itemView);
            this.emoBar = new BaseViewHolderEmoBar(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.feed_item_post_album_view);
            this.clickListener = clickListener;
            itemView.setOnClickListener(FeedItemAlbum.ViewHolder.this);

        }

        public void bind(FeedItem feedItem)
        {
            FeedItemAlbum post = (FeedItemAlbum) feedItem;
            if (post != null)
            {
                titleBar.bind(post);
                emoBar.bind(post);

                if (post.images != null && !post.images.isEmpty())
                {
                    if (post.images.size() == 2)
                    {
                        LinearLayout a = new LinearLayout(itemView.getContext());
                        a.setWeightSum(2);
                        a.setOrientation(LinearLayout.HORIZONTAL);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.weight = 1;

                        ImageView first = new ImageView(itemView.getContext());
                        first.setLayoutParams(layoutParams);
                        first.setBackgroundColor(Color.BLUE);

                        ImageView second = new ImageView(itemView.getContext());
                        second.setLayoutParams(layoutParams);
                        second.setBackgroundColor(Color.GREEN);

                        a.addView(first);
                        a.addView(second);

                        linearLayout.addView(a);

                        Picasso
                                .with(itemView.getContext())
                                .load(post.images.get(0))
                                .transform(new CropCircleTransformation())
                                .into(first);

                        Picasso
                                .with(itemView.getContext())
                                .load(post.images.get(1))
                                .transform(new CropCircleTransformation())
                                .into(second);
                    }

                }
            }
        }

        @Override
        public void onClick(View v)
        {
            if (clickListener != null)
                clickListener.openPost();
        }
    }
}
