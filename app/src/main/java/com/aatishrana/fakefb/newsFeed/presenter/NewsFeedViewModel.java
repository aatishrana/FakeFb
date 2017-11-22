package com.aatishrana.fakefb.newsFeed.presenter;

import com.aatishrana.fakefb.newsFeed.model.FeedItem;

import java.util.List;

/**
 * Created by Aatish Rana on 22-Nov-17.
 */

public class NewsFeedViewModel
{
    private final List<FeedItem> cache;
    private final boolean loading;

    public NewsFeedViewModel(List<FeedItem> cache, boolean loading)
    {
        this.cache = cache;
        this.loading = loading;
    }

    public List<FeedItem> getCache()
    {
        return cache;
    }

    public boolean isLoading()
    {
        return loading;
    }
}
