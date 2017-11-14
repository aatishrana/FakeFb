package com.aatishrana.fakefb.newsFeed.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.newsFeed.NewsFeedAdapter;
import com.aatishrana.fakefb.utils.CropCircleTransformation;
import com.aatishrana.fakefb.utils.DynamicHeightImageView;
import com.aatishrana.fakefb.utils.SizableColorDrawable;
import com.squareup.picasso.Picasso;

/**
 * Created by Aatish Rana on 14-Nov-17.
 */

public class FeedItemPost implements FeedItem
{
    private final int rank;
    private final String titleText;
    private final String descText;
    private final String time;
    private final String location;
    private final int privacy;
    private final String userPic;
    private final Image postImage;
    private final long totalEmotions;
    private final long totalComments;
    private final long totalShares;
    private final long totalViews;

    public FeedItemPost(int rank, String titleText, String descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions, long totalComments, long totalShares, long totalViews)
    {
        this.rank = rank;
        this.titleText = titleText;
        this.descText = descText;
        this.time = time;
        this.location = location;
        this.privacy = privacy;
        this.userPic = userPic;
        this.postImage = postImage;
        this.totalEmotions = totalEmotions;
        this.totalComments = totalComments;
        this.totalShares = totalShares;
        this.totalViews = totalViews;
    }

    public FeedItemPost(int rank, String titleText, String descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions, long totalComments, long totalShares)
    {
        this(rank, titleText, descText, time, location, privacy, userPic, postImage, totalEmotions, totalComments, totalShares, 0);
    }

    public FeedItemPost(int rank, String titleText, String descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions, long totalComments)
    {
        this(rank, titleText, descText, time, location, privacy, userPic, postImage, totalEmotions, totalComments, 0, 0);
    }

    public FeedItemPost(int rank, String titleText, String descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions)
    {
        this(rank, titleText, descText, time, location, privacy, userPic, postImage, totalEmotions, 0, 0, 0);
    }

    public FeedItemPost(int rank, String titleText, String descText, String time, String location, int privacy, String userPic, Image postImage)
    {
        this(rank, titleText, descText, time, location, privacy, userPic, postImage, 0, 0, 0, 0);
    }

    public String getTitleText()
    {
        return titleText;
    }

    public String getDescText()
    {
        return descText;
    }

    public String getTime()
    {
        return time;
    }

    public String getLocation()
    {
        return location;
    }

    public int getPrivacy()
    {
        return privacy;
    }

    public String getUserPic()
    {
        return userPic;
    }

    public Image getPostImage()
    {
        return postImage;
    }

    public String getTotalEmotions()
    {
        return getFormattedCount(totalEmotions);
    }

    public String getTotalComments()
    {
        return getFormattedCount(totalComments);
    }

    public String getTotalShares()
    {
        return getFormattedCount(totalShares);
    }

    public String getTotalViews()
    {
        return getFormattedCount(totalViews);
    }

    private String getFormattedCount(long value)
    {
        final int K = 1000;
        final int M = 1000000;
        final int B = 100000000;

        if (value == 0)
            return "0";

        if (value < K)
            return String.valueOf(value);

        if (value < M)
            return String.valueOf((value / K) + "K");

        if (value < B)
            return String.valueOf((value / M) + "M");

        return String.valueOf((value / B) + "B");
    }

    public boolean hasEmotions()
    {
        return totalEmotions != 0;
    }

    public boolean hasComments()
    {
        return totalComments != 0;
    }

    public boolean hasShares()
    {
        return totalShares != 0;
    }

    public boolean hasViews()
    {
        return totalViews != 0;
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

        private ImageView ivUserPic;
        private DynamicHeightImageView ivPostPic;
        private TextView tvTitle, tvSubtitle, tvDesc, tvCountEmo, tvCountOther;

        public ViewHolder(View itemView, NewsFeedAdapter.NewsFeedClickListener clickListener)
        {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(FeedItemPost.ViewHolder.this);

            this.ivUserPic = (ImageView) itemView.findViewById(R.id.feed_item_post_iv_user_pic);
            this.ivPostPic = (DynamicHeightImageView) itemView.findViewById(R.id.feed_item_post_iv_post_pic);
            this.tvTitle = (TextView) itemView.findViewById(R.id.feed_item_post_tv_title);
            this.tvSubtitle = (TextView) itemView.findViewById(R.id.feed_item_post_tv_sub_title);
            this.tvDesc = (TextView) itemView.findViewById(R.id.feed_item_post_tv_desc);
            this.tvCountEmo = (TextView) itemView.findViewById(R.id.feed_item_post_tv_count_emo);
            this.tvCountOther = (TextView) itemView.findViewById(R.id.feed_item_post_tv_count_others);
        }

        public void bind(FeedItem feedItem)
        {
            FeedItemPost post = (FeedItemPost) feedItem;
            if (post != null)
            {
                this.tvTitle.setText(post.getTitleText());
                this.tvSubtitle.setText(String.valueOf(post.getTime() + " " + post.getLocation()));
                this.tvDesc.setText(post.getDescText());

                if (post.hasEmotions())
                    this.tvCountEmo.setText(post.getTotalEmotions());

                String other = "";
                String d = " ";
                if (post.hasComments())
                    other = post.getTotalComments() + " Comments" + d;

                if (post.hasShares())
                    other = other + post.getTotalShares() + " Shares" + d;

                if (post.hasViews())
                    other = other + post.getTotalViews() + " Views";

                this.tvCountOther.setText(other);

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

                if (post.getUserPic() != null && post.getUserPic().length() > 0)
                    Picasso
                            .with(itemView.getContext())
                            .load(post.getUserPic())
                            .transform(new CropCircleTransformation())
                            .into(this.ivUserPic);
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
