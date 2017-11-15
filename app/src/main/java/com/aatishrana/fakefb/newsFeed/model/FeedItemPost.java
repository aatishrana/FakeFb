package com.aatishrana.fakefb.newsFeed.model;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.Image;
import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.model.TextStyle;
import com.aatishrana.fakefb.newsFeed.NewsFeedAdapter;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderEmoBar;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderTitleBar;
import com.aatishrana.fakefb.utils.Const;
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
    private final Text title;
    private final Text descText;
    private final String time;
    private final String location;
    private final int privacy;
    private final String userPic;
    private final Image postImage;
    private final long totalEmotions;
    private final long totalComments;
    private final long totalShares;
    private final long totalViews;

    public FeedItemPost(int rank, Text title, Text descText, String time, String location, int privacy, String userPic, Image postImage, long totalEmotions, long totalComments, long totalShares, long totalViews)
    {
        this.rank = rank;
        this.title = title;
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

    public SpannableString getTitleText()
    {
        return getFormattedString(title);
    }

    public SpannableString getDescText()
    {
        return getFormattedString(descText);
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

    private SpannableString getFormattedString(Text value)
    {
        if (value != null)
        {
            SpannableString text = new SpannableString(value.getTextData());
            for (TextStyle style : value.getTextStyles())
            {
                String defaultColor = "#000000";
                int s = style.getStartIndex();
                int e = style.getEndIndex();

                if (s < 0 || e > text.length()) //if wrong index are passed continue
                    continue;

                if (style.getColor() != null && style.getColor().length() == 7 && style.getColor().contains("#"))
                    defaultColor = style.getColor();

                text.setSpan(new ForegroundColorSpan(Color.parseColor(defaultColor)), s, e, 0);

                if (style.getStyle() == null)
                    continue;

                if (style.getStyle().equalsIgnoreCase(Const.NORMAL))
                {
                    text.setSpan(new StyleSpan(Typeface.NORMAL), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }

                if (style.getStyle().equalsIgnoreCase(Const.BOLD))
                {
                    text.setSpan(new StyleSpan(Typeface.BOLD), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }

                if (style.getStyle().equalsIgnoreCase(Const.ITALIC))
                {
                    text.setSpan(new StyleSpan(Typeface.ITALIC), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }

                if (style.getStyle().equalsIgnoreCase(Const.BOLD_ITALIC))
                {
                    text.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return text;
        }
        return new SpannableString("");
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
