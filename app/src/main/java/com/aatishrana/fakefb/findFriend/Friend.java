package com.aatishrana.fakefb.findFriend;

import com.aatishrana.fakefb.model.Image;

/**
 * Created by Aatish Rana on 17-Nov-17.
 */

public class Friend
{
    private final String firstName, lastName;
    private final int mutualFriends;
    private final Image picUrl;

    public Friend(String firstName, String lastName, int mutualFriends, Image picUrl)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mutualFriends = mutualFriends;
        this.picUrl = picUrl;
    }

    public String getFirstName()
    {
        return firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
    }

    public String getLastName()
    {
        return lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
    }

    public String getName()
    {
        return getFirstName() + " " + getLastName();
    }

    public int getMutualFriends()
    {
        return mutualFriends;
    }

    public Image getPicUrl()
    {
        return picUrl;
    }

    @Override
    public String toString()
    {
        return "Friend{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mutualFriends=" + mutualFriends +
                ", picUrl=" + picUrl +
                '}';
    }
}
