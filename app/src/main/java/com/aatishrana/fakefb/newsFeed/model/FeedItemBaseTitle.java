package com.aatishrana.fakefb.newsFeed.model;

import android.text.SpannableString;

import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.utils.H;

/**
 * Created by Aatish Rana on 15-Nov-17.
 */

public class FeedItemBaseTitle
{
    private final Text title;
    private final String time;
    private final String location;
    private final int privacy;
    private final String userPic;

    public FeedItemBaseTitle(Text title, String time, String location, int privacy, String userPic)
    {
        this.title = title;
        this.time = time;
        this.location = location;
        this.privacy = privacy;
        this.userPic = userPic;
    }

    public Text getTitle()
    {
        return title;
    }

    public String getTime()
    {
        return time;
    }

    public String getLocation()
    {
        return location;
    }

    public int getPrivacy()
    {
        return privacy;
    }

    public String getUserPic()
    {
        return userPic;
    }

    public SpannableString getTitleText()
    {
        return H.getFormattedString(title);
    }
}
