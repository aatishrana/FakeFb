package com.aatishrana.fakefb.newsFeed.presenter;

import com.aatishrana.fakefb.newsFeed.model.FeedItem;

import java.util.List;

/**
 * Created by Aatish on 11/21/2017.
 */

public interface NewsFeedView
{
    void showData(List<FeedItem> cache);

    void showError();
}
