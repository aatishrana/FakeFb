package com.aatishrana.fakefb.model;

/**
 * Created by Aatish on 11/12/2017.
 */

public class FriendStoryItem extends Friend
{
    private boolean storyWatched;

    public FriendStoryItem(String firstName, String lastName, Image userImage, boolean storyWatched)
    {
        super(firstName, lastName, userImage);
        this.storyWatched = storyWatched;
    }

    public boolean isStoryWatched()
    {
        return storyWatched;
    }
}
