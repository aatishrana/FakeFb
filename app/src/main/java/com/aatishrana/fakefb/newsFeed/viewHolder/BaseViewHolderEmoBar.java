package com.aatishrana.fakefb.newsFeed.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.newsFeed.model.FeedItemAlbum;
import com.aatishrana.fakefb.newsFeed.model.FeedItemBaseEmo;
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
        setEmoCount(post.getEmo());

        setOthers(post.getEmo());

    }

    public void bind(FeedItemAlbum post)
    {
        setEmoCount(post.getEmo());

        setOthers(post.getEmo());
    }

    private void setEmoCount(FeedItemBaseEmo emo)
    {
        if (emo.hasEmotions())
            this.tvCountEmo.setText(emo.getTotalEmotions());
    }

    private void setOthers(FeedItemBaseEmo emo)
    {
        String other = "";
        String d = " ";
        if (emo.hasComments())
            other = emo.getTotalComments() + " Comments" + d;

        if (emo.hasShares())
            other = other + emo.getTotalShares() + " Shares" + d;

        if (emo.hasViews())
            other = other + emo.getTotalViews() + " Views";

        this.tvCountOther.setText(other);
    }
}
