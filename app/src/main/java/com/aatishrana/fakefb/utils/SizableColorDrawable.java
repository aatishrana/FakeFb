package com.aatishrana.fakefb.utils;

import android.graphics.drawable.ColorDrawable;

/**
 * Created by Aatish on 11/12/2017.
 */

public class SizableColorDrawable extends ColorDrawable
{

    int mWidth = -1;

    int mHeight = -1;

    public SizableColorDrawable(int color, int width, int height)
    {
        super(color);

        mWidth = width;
        mHeight = height;
    }

    @Override
    public int getIntrinsicWidth()
    {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight()
    {
        return mHeight;
    }
}
