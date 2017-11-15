package com.aatishrana.fakefb.newsFeed.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.newsFeed.model.FeedItemAlbum;
import com.aatishrana.fakefb.newsFeed.model.FeedItemBaseTitle;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPost;
import com.aatishrana.fakefb.utils.CropCircleTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by Aatish Rana on 15-Nov-17.
 */

public class BaseViewHolderTitleBar
{
    private ImageView ivUserPic;
    private TextView tvTitle, tvSubtitle;

    public BaseViewHolderTitleBar(View itemView)
    {
        //ids from title bar
        this.ivUserPic = (ImageView) itemView.findViewById(R.id.feed_item_title_bar_iv_user_pic);
        this.tvTitle = (TextView) itemView.findViewById(R.id.feed_item_title_bar_tv_title);
        this.tvSubtitle = (TextView) itemView.findViewById(R.id.feed_item_title_bar_tv_sub_title);
    }

    public void bind(FeedItemPost post)
    {
        setTitleAndSubtitle(post.getTitle());
        setUserImage(post.getTitle());
    }

    public void bind(FeedItemAlbum post)
    {
        setTitleAndSubtitle(post.getTitle());
        setUserImage(post.getTitle());
    }

    private void setTitleAndSubtitle(FeedItemBaseTitle feedItemBaseTitle)
    {
        this.tvTitle.setText(feedItemBaseTitle.getTitleText());
        this.tvSubtitle.setText(String.valueOf(feedItemBaseTitle.getTime() + " " + feedItemBaseTitle.getLocation()));
    }

    private void setUserImage(FeedItemBaseTitle feedItemBaseTitle)
    {
        if (feedItemBaseTitle.getUserPic() != null && feedItemBaseTitle.getUserPic().length() > 0)
            Picasso
                    .with(ivUserPic.getContext())
                    .load(feedItemBaseTitle.getUserPic())
                    .transform(new CropCircleTransformation())
                    .into(this.ivUserPic);
    }
}
