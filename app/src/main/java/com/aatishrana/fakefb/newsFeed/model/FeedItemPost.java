package com.aatishrana.fakefb.newsFeed.model;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.newsFeed.NewsFeedAdapter;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderEmoBar;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderTitleBar;
import com.aatishrana.fakefb.utils.DynamicHeightImageView;
import com.aatishrana.fakefb.utils.H;
import com.aatishrana.fakefb.utils.SizableColorDrawable;
import com.squareup.picasso.Picasso;

/**
 * Created by Aatish Rana on 14-Nov-17.
 */

public class FeedItemPost implements FeedItem
{
    private final int rank;
    private final Text descText;
    private final Image postImage;
    private final FeedItemBaseTitle title;
    private final FeedItemBaseEmo emo;


    public FeedItemPost(int rank, Text title, Text descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions, long totalComments, long totalShares, long totalViews)
    {
        this.rank = rank;
        this.descText = descText;
        this.postImage = postImage;

        //instance created rather then injected to avoid NPE even when user does'nt want to give emo, location, time etc
        this.title = new FeedItemBaseTitle(title, time, location, privacy, userPic);
        this.emo = new FeedItemBaseEmo(totalEmotions, totalComments, totalShares, totalViews);
    }

    public FeedItemPost(int rank, Text title, Text descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions, long totalComments, long totalShares)
    {
        this(rank, title, descText, time, location, privacy, userPic, postImage, totalEmotions, totalComments, totalShares, 0);
    }

    public FeedItemPost(int rank, Text title, Text descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions, long totalComments)
    {
        this(rank, title, descText, time, location, privacy, userPic, postImage, totalEmotions, totalComments, 0, 0);
    }

    public FeedItemPost(int rank, Text title, Text descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions)
    {
        this(rank, title, descText, time, location, privacy, userPic, postImage, totalEmotions, 0, 0, 0);
    }

    public FeedItemPost(int rank, Text title, Text descText, String time, String location, int privacy, String userPic, Image postImage)
    {
        this(rank, title, descText, time, location, privacy, userPic, postImage, 0, 0, 0, 0);
    }


    public SpannableString getDescText()
    {
        return H.getFormattedString(descText);
    }


    public Image getPostImage()
    {
        return postImage;
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
        return R.layout.feed_item_post;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private NewsFeedAdapter.NewsFeedClickListener clickListener;

        private BaseViewHolderTitleBar titleBar;//composition
        private BaseViewHolderEmoBar emoBar;//composition

        private DynamicHeightImageView ivPostPic;
        private TextView tvDesc;

        public ViewHolder(View itemView, NewsFeedAdapter.NewsFeedClickListener clickListener)
        {
            super(itemView);
            this.titleBar = new BaseViewHolderTitleBar(itemView);
            this.emoBar = new BaseViewHolderEmoBar(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(FeedItemPost.ViewHolder.this);


            this.ivPostPic = (DynamicHeightImageView) itemView.findViewById(R.id.feed_item_post_iv_post_pic);
            this.tvDesc = (TextView) itemView.findViewById(R.id.feed_item_post_tv_desc);
        }

        public void bind(FeedItem feedItem)
        {
            FeedItemPost post = (FeedItemPost) feedItem;
            if (post != null)
            {
                titleBar.bind(post);
                emoBar.bind(post);

                //set desc text
                this.tvDesc.setText(post.getDescText());

                //set content img if any exist
                Image image = post.getPostImage();
                if (image != null && image.isValid())
                {
                    this.ivPostPic.setVisibility(View.VISIBLE);
                    float ratio = (float) image.getHeight() / (float) image.getWidth();
                    this.ivPostPic.setHeightRatio(ratio);
                    Picasso
                            .with(itemView.getContext())
                            .load(image.getUrl())
                            .placeholder(new SizableColorDrawable(image.getColorCode(), image.getWidth(), image.getHeight()))
                            .into(this.ivPostPic);
                } else
                {
                    this.ivPostPic.setVisibility(View.GONE);
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
