package com.aatishrana.fakefb.newsFeed.model;

/**
 * Created by Aatish on 11/12/2017.
 * <p>
 * Represent a feed item, can have n implementations and show
 * different types of views in news feed
 */
public interface FeedItem
{
    int rank();

    int feedLayoutType();
}
