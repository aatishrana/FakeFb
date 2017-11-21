package com.aatishrana.fakefb.data;

import com.aatishrana.fakefb.newsFeed.model.FeedItem;
import com.aatishrana.fakefb.notification.Noti;

import java.util.List;

/**
 * Created by Aatish on 11/21/2017.
 */

public class FbData
{
    private final List<FeedItem> newsFeed;

    private final FindFriendData findFriendData;

    private final List<Noti> notificationData;

    public FbData(List<FeedItem> newsFeed, FindFriendData findFriendData, List<Noti> notificationData)
    {
        this.newsFeed = newsFeed;
        this.findFriendData = findFriendData;
        this.notificationData = notificationData;
    }

    public List<FeedItem> getNewsFeed()
    {
        return newsFeed;
    }

    public FindFriendData getFindFriendData()
    {
        return findFriendData;
    }

    public List<Noti> getNotificationData()
    {
        return notificationData;
    }
}
