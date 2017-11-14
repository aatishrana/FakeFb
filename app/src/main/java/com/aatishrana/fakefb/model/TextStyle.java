package com.aatishrana.fakefb.model;

/**
 * Created by Aatish Rana on 14-Nov-17.
 */

public class TextStyle
{
    private final int startIndex, endIndex;
    private final String style;
    private final String color;

    public TextStyle(int startIndex, int endIndex, String style, String color)
    {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.style = style;
        this.color = color;
    }

    public TextStyle(int startIndex, int endIndex, String style)
    {
        this(startIndex, endIndex, style, null);
    }

    public int getStartIndex()
    {
        return startIndex;
    }

    public int getEndIndex()
    {
        return endIndex;
    }

    public String getColor()
    {
        return color;
    }

    public String getStyle()
    {
        return style;
    }
}
