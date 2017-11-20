package com.aatishrana.fakefb.model;

/**
 * Created by Aatish on 11/12/2017.
 */

public class Friend
{
    private String firstName, lastName;
    private Image userImage;

    public Friend(String firstName, String lastName, Image userImage)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userImage = userImage;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Image getUserImage()
    {
        return userImage;
    }

    @Override
    public String toString()
    {
        return "Friend{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userImage=" + userImage +
                '}';
    }
}
