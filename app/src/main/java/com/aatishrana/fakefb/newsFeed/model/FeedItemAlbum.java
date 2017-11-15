package com.aatishrana.fakefb.newsFeed.model;

import com.aatishrana.fakefb.R;

/**
 * Created by Aatish Rana on 15-Nov-17.
 */

public class FeedItemAlbum implements FeedItem
{
    @Override
    public int rank()
    {
        return 0;
    }

    @Override
    public int feedLayoutType()
    {
        return R.layout.feed_item_post_album;
    }
}
