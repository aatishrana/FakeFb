package com.aatishrana.fakefb.settings;

/**
 * Created by Aatish on 11/25/2017.
 */

public class FbData
{
    private String name;
    private boolean isSelected;

    public FbData(String name, boolean isSelected)
    {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName()
    {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public boolean isSelected()
    {
        return isSelected;
    }
}
