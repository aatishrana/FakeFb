package com.aatishrana.fakefb.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Aatish on 11/12/2017.
 */

public class H
{
    public static void L(String msg)
    {
        Log.i("FakeFb", msg);
    }

    public static void L(int value)
    {
        L(String.valueOf(value));
    }

    public static void E(String msg)
    {
        Log.e("FakeFb", msg);
    }

    public static void E(int value)
    {
        E(String.valueOf(value));
    }

    public static float pToD(final Context context, final float px)
    {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dToP(final Context context, final float dp)
    {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
