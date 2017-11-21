package com.aatishrana.fakefb.data;

import com.aatishrana.fakefb.findFriend.Friend;

import java.util.List;

/**
 * Created by Aatish on 11/21/2017.
 */

public class FindFriendData
{
    private final List<Friend> friendRequests;
    private final List<Friend> friendSuggestions;

    public FindFriendData(List<Friend> friendRequests, List<Friend> friendSuggestions)
    {
        this.friendRequests = friendRequests;
        this.friendSuggestions = friendSuggestions;
    }

    public List<Friend> getFriendRequests()
    {
        return friendRequests;
    }

    public List<Friend> getFriendSuggestions()
    {
        return friendSuggestions;
    }
}
