package com.aatishrana.fakefb.newsFeed.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
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
        this.tvTitle.setText(post.getTitleText());
        this.tvSubtitle.setText(String.valueOf(post.getTime() + " " + post.getLocation()));

        if (post.getUserPic() != null && post.getUserPic().length() > 0)
            Picasso
                    .with(ivUserPic.getContext())
                    .load(post.getUserPic())
                    .transform(new CropCircleTransformation())
                    .into(this.ivUserPic);
    }
}
