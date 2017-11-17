package com.aatishrana.fakefb.model;

import android.graphics.Color;

/**
 * Created by Aatish on 11/12/2017.
 */

public class Image
{
    private static final String defaultColor = "#dfdfdf";
    private final String url;
    private final int height, width;
    private final String colorCode;

    public Image(String url, int height, int width, String colorCode)
    {
        this.url = url;
        this.height = height;
        this.width = width;
        this.colorCode = colorCode;
    }

    public String getUrl()
    {
        return url;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getColorCode()
    {
        if (colorCode != null && colorCode.length() == 7 && colorCode.contains("#"))
            return Color.parseColor(this.colorCode);
        return Color.parseColor(defaultColor);
    }

    public boolean isValid()
    {
        return url != null && url.length() > 0 && height != 0 && width != 0;
    }

    @Override
    public String toString()
    {
        return "Image{" +
                "url='" + url + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", colorCode='" + colorCode + '\'' +
                '}';
    }
}
