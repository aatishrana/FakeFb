package com.aatishrana.fakefb.newsFeed.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.aatishrana.fakefb.utils.H;
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
                        displayTwoImages(linearLayout,
                                post.images.get(0),
                                post.images.get(1));
                    else if (post.images.size() == 4)
                        displayFourImages(linearLayout,
                                post.images.get(0),
                                post.images.get(1),
                                post.images.get(2),
                                post.images.get(3));
                }
            }
        }

        private void displayTwoImages(LinearLayout linearLayout, String firstUrl, String secondUrl)
        {
            Context context = linearLayout.getContext();
            LinearLayout a = new LinearLayout(context);
            a.setWeightSum(2);
            a.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParamsFirst = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsFirst.weight = 1;
            layoutParamsFirst.rightMargin = (int) H.dToP(context, 4);

            ImageView first = new ImageView(context);
            first.setScaleType(ImageView.ScaleType.CENTER_CROP);
            first.setLayoutParams(layoutParamsFirst);

            LinearLayout.LayoutParams layoutParamsSecond = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsSecond.weight = 1;
            layoutParamsSecond.leftMargin = (int) H.dToP(context, 4);

            ImageView second = new ImageView(context);
            second.setScaleType(ImageView.ScaleType.CENTER_CROP);
            second.setLayoutParams(layoutParamsFirst);

            a.addView(first);
            a.addView(second);

            linearLayout.addView(a);

            int[] grad = new int[2];
            grad[0] = R.color.colorPrimary;
            grad[1] = R.color.colorPrimaryDark;

            Picasso
                    .with(itemView.getContext())
                    .load(firstUrl)
                    .placeholder(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, grad))
                    .into(first);

            Picasso
                    .with(itemView.getContext())
                    .load(secondUrl)
                    .placeholder(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, grad))
                    .into(second);
        }

        private void displayThreeImages(LinearLayout linearLayout, String firstUrl, String secondUrl)
        {
            Context context = linearLayout.getContext();
            LinearLayout a = new LinearLayout(context);
            a.setWeightSum(2);
            a.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParamsFirst = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsFirst.weight = 1;
            layoutParamsFirst.rightMargin = (int) H.dToP(context, 4);

            ImageView first = new ImageView(context);
            first.setScaleType(ImageView.ScaleType.CENTER_CROP);
            first.setLayoutParams(layoutParamsFirst);

            LinearLayout.LayoutParams layoutParamsSecond = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsSecond.weight = 1;
            layoutParamsSecond.leftMargin = (int) H.dToP(context, 4);

            ImageView second = new ImageView(context);
            second.setScaleType(ImageView.ScaleType.CENTER_CROP);
            second.setLayoutParams(layoutParamsFirst);

            a.addView(first);
            a.addView(second);

            linearLayout.addView(a);

            Picasso
                    .with(itemView.getContext())
                    .load(firstUrl)
                    .into(first);

            Picasso
                    .with(itemView.getContext())
                    .load(secondUrl)
                    .into(second);
        }

        private void displayFourImages(LinearLayout linearLayout, String firstUrl, String secondUrl, String thirdUrl, String fourUrl)
        {
            Context context = linearLayout.getContext();

//            LinearLayout.LayoutParams layoutParamsParent = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParamsParent.weight = 1;

            LinearLayout a = new LinearLayout(context);
            a.setWeightSum(2);
            a.setOrientation(LinearLayout.HORIZONTAL);
//            a.setLayoutParams(layoutParamsParent);

            LinearLayout.LayoutParams layoutParamsFirst = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsFirst.weight = 1;
            layoutParamsFirst.rightMargin = (int) H.dToP(context, 4);

            ImageView first = new ImageView(context);
            first.setScaleType(ImageView.ScaleType.CENTER_CROP);
            first.setLayoutParams(layoutParamsFirst);

            LinearLayout.LayoutParams layoutParamsSecond = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsSecond.weight = 1;
            layoutParamsSecond.leftMargin = (int) H.dToP(context, 4);

            ImageView second = new ImageView(context);
            second.setScaleType(ImageView.ScaleType.CENTER_CROP);
            second.setLayoutParams(layoutParamsFirst);

            a.addView(first);
            a.addView(second);

            linearLayout.addView(a);

            int[] grad = new int[2];
            grad[0] = R.color.colorPrimary;
            grad[1] = R.color.colorPrimaryDark;

            Picasso
                    .with(itemView.getContext())
                    .load(firstUrl)
                    .placeholder(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, grad))
                    .into(first);

            Picasso
                    .with(itemView.getContext())
                    .load(secondUrl)
                    .placeholder(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, grad))
                    .into(second);


            LinearLayout b = new LinearLayout(context);
            b.setWeightSum(2);
            b.setOrientation(LinearLayout.HORIZONTAL);
//            b.setLayoutParams(layoutParamsParent);

            LinearLayout.LayoutParams layoutParamsThird = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsThird.weight = 1;
            layoutParamsThird.rightMargin = (int) H.dToP(context, 4);
            layoutParamsThird.topMargin = (int) H.dToP(context, 4);

            ImageView third = new ImageView(context);
            third.setScaleType(ImageView.ScaleType.CENTER_CROP);
            third.setLayoutParams(layoutParamsThird);

            LinearLayout.LayoutParams layoutParamsForth = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsForth.weight = 1;
            layoutParamsForth.leftMargin = (int) H.dToP(context, 4);
            layoutParamsForth.topMargin = (int) H.dToP(context, 4);

            ImageView fourth = new ImageView(context);
            fourth.setScaleType(ImageView.ScaleType.CENTER_CROP);
            fourth.setLayoutParams(layoutParamsForth);

            b.addView(third);
            b.addView(fourth);

            linearLayout.addView(b);

            Picasso
                    .with(itemView.getContext())
                    .load(thirdUrl)
                    .placeholder(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, grad))
                    .into(third);

            Picasso
                    .with(itemView.getContext())
                    .load(fourUrl)
                    .placeholder(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, grad))
                    .into(fourth);
        }

        @Override
        public void onClick(View v)
        {
            if (clickListener != null)
                clickListener.openPost();
        }
    }
}
