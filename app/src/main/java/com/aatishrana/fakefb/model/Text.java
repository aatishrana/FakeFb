package com.aatishrana.fakefb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aatish Rana on 14-Nov-17.
 */

public class Text
{
    private final String textData;
    private final List<TextStyle> textStyles;

    public Text(String textData, List<TextStyle> textStyles)
    {
        this.textData = textData;
        this.textStyles = textStyles;
    }

    public Text(String textData, TextStyle textStyle)
    {
        this.textData = textData;
        this.textStyles = new ArrayList<>();
        this.textStyles.add(textStyle);
    }

    public Text(String textData)
    {
        this(textData, new ArrayList<TextStyle>());
    }

    public String getTextData()
    {
        return textData;
    }

    public List<TextStyle> getTextStyles()
    {
        return textStyles;
    }
}

