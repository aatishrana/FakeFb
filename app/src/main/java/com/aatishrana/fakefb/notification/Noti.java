package com.aatishrana.fakefb.notification;

/**
 * Created by Aatish Rana on 20-Nov-17.
 */

public class Noti
{
    private final int rank;
    private final String title, userPic, time;
    private final int icon;
    private boolean read;

    public Noti(int rank, String title, String userPic, String time, int icon, boolean read)
    {
        this.rank = rank;
        this.title = title;
        this.userPic = userPic;
        this.time = time;
        this.icon = icon;
        this.read = read;
    }

    public void setRead(boolean read)
    {
        this.read = read;
    }

    public int getRank()
    {
        return rank;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUserPic()
    {
        return userPic;
    }

    public String getTime()
    {
        return time;
    }

    public int getIcon()
    {
        return icon;
    }

    public boolean isRead()
    {
        return read;
    }

    @Override
    public String toString()
    {
        return "Noti{" +
                "rank=" + rank +
                ", title='" + title + '\'' +
                ", userPic='" + userPic + '\'' +
                ", time='" + time + '\'' +
                ", icon=" + icon +
                ", read=" + read +
                '}';
    }
}
