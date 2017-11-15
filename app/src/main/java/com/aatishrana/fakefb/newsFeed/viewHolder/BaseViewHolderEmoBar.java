package com.aatishrana.fakefb.newsFeed.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.newsFeed.model.FeedItemPost;

/**
 * Created by Aatish Rana on 15-Nov-17.
 */

public class BaseViewHolderEmoBar
{
    private TextView tvCountEmo, tvCountOther;

    public BaseViewHolderEmoBar(View itemView)
    {
        //ids from emo bar
        this.tvCountEmo = (TextView) itemView.findViewById(R.id.feed_item_emo_bar_tv_count_emo);
        this.tvCountOther = (TextView) itemView.findViewById(R.id.feed_item_emo_bar_tv_count_others);
    }

    public void bind(FeedItemPost post)
    {
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
    }
}
