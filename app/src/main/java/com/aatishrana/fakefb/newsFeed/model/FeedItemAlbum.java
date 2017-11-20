package com.aatishrana.fakefb.newsFeed.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.newsFeed.NewsFeedAdapter;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderEmoBar;
import com.aatishrana.fakefb.newsFeed.viewHolder.BaseViewHolderTitleBar;
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

                    LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                    if (post.images.size() == 2)
                        showTwoPhotos(inflater, post.images.get(0), post.images.get(1));
                    else if (post.images.size() == 3)
                        showThreePhotos(inflater, post.images.get(0), post.images.get(1), post.images.get(2));
                    else if (post.images.size() == 4)
                        showFourPhotos(inflater, post.images.get(0), post.images.get(1), post.images.get(2), post.images.get(3));
                    else if (post.images.size() == 5)
                        showFivePhotos(inflater, post.images.get(0), post.images.get(1), post.images.get(2), post.images.get(3));
                }
            }
        }

        private void showTwoPhotos(LayoutInflater inflater, String urlOne, String urlTwo)
        {
            View view = inflater.inflate(R.layout.album_layout_two, linearLayout, false);
            ImageView ivOne = (ImageView) view.findViewById(R.id.album_layout_iv_one);
            ImageView ivTwo = (ImageView) view.findViewById(R.id.album_layout_iv_two);
            linearLayout.addView(view);

            Picasso
                    .with(itemView.getContext())
                    .load(urlOne)
                    .into(ivOne);

            Picasso
                    .with(itemView.getContext())
                    .load(urlTwo)
                    .into(ivTwo);
        }

        private void showThreePhotos(LayoutInflater inflater, String urlOne, String urlTwo, String urlThree)
        {
            View view = inflater.inflate(R.layout.album_layout_three, linearLayout, false);
            ImageView ivOne = (ImageView) view.findViewById(R.id.album_layout_iv_one);
            ImageView ivTwo = (ImageView) view.findViewById(R.id.album_layout_iv_two);
            ImageView ivThree = (ImageView) view.findViewById(R.id.album_layout_iv_three);
            linearLayout.addView(view);

            Picasso
                    .with(itemView.getContext())
                    .load(urlOne)
                    .into(ivOne);

            Picasso
                    .with(itemView.getContext())
                    .load(urlTwo)
                    .into(ivTwo);

            Picasso
                    .with(itemView.getContext())
                    .load(urlThree)
                    .into(ivThree);
        }

        private void showFourPhotos(LayoutInflater inflater, String urlOne, String urlTwo, String urlThree, String urlFour)
        {
            View view = inflater.inflate(R.layout.album_layout_four, linearLayout, false);
            ImageView ivOne = (ImageView) view.findViewById(R.id.album_layout_iv_one);
            ImageView ivTwo = (ImageView) view.findViewById(R.id.album_layout_iv_two);
            ImageView ivThree = (ImageView) view.findViewById(R.id.album_layout_iv_three);
            ImageView ivFour = (ImageView) view.findViewById(R.id.album_layout_iv_four);
            linearLayout.addView(view);

            Picasso
                    .with(itemView.getContext())
                    .load(urlOne)
                    .into(ivOne);

            Picasso
                    .with(itemView.getContext())
                    .load(urlTwo)
                    .into(ivTwo);

            Picasso
                    .with(itemView.getContext())
                    .load(urlThree)
                    .into(ivThree);

            Picasso
                    .with(itemView.getContext())
                    .load(urlFour)
                    .into(ivFour);
        }

        private void showFivePhotos(LayoutInflater inflater, String urlOne, String urlTwo, String urlThree, String urlFour)
        {
            View view = inflater.inflate(R.layout.album_layout_five, linearLayout, false);
            ImageView ivOne = (ImageView) view.findViewById(R.id.album_layout_iv_one);
            ImageView ivTwo = (ImageView) view.findViewById(R.id.album_layout_iv_two);
            ImageView ivThree = (ImageView) view.findViewById(R.id.album_layout_iv_three);
            ImageView ivFour = (ImageView) view.findViewById(R.id.album_layout_iv_four);
            linearLayout.addView(view);

            Picasso
                    .with(itemView.getContext())
                    .load(urlOne)
                    .into(ivOne);

            Picasso
                    .with(itemView.getContext())
                    .load(urlTwo)
                    .into(ivTwo);

            Picasso
                    .with(itemView.getContext())
                    .load(urlThree)
                    .into(ivThree);

            Picasso
                    .with(itemView.getContext())
                    .load(urlFour)
                    .into(ivFour);
        }


        @Override
        public void onClick(View v)
        {
            if (clickListener != null)
                clickListener.openPost();
        }
    }
}
