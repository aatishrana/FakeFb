package com.aatishrana.fakefb.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import com.aatishrana.fakefb.model.Text;
import com.aatishrana.fakefb.model.TextStyle;

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

    public static int dToPi(final Context context, final float dp)
    {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static SpannableString getFormattedString(Text value)
    {
        if (value != null)
        {
            SpannableString text = new SpannableString(value.getTextData());
            for (TextStyle style : value.getTextStyles())
            {
                String defaultColor = "#000000";
                int s = style.getStartIndex();
                int e = style.getEndIndex();

                if (s < 0 || e > text.length()) //if wrong index are passed continue
                    continue;

                if (style.getColor() != null && style.getColor().length() == 7 && style.getColor().contains("#"))
                    defaultColor = style.getColor();

                text.setSpan(new ForegroundColorSpan(Color.parseColor(defaultColor)), s, e, 0);

                if (style.getStyle() == null)
                    continue;

                if (style.getStyle().equalsIgnoreCase(Const.NORMAL))
                {
                    text.setSpan(new StyleSpan(Typeface.NORMAL), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }

                if (style.getStyle().equalsIgnoreCase(Const.BOLD))
                {
                    text.setSpan(new StyleSpan(Typeface.BOLD), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }

                if (style.getStyle().equalsIgnoreCase(Const.ITALIC))
                {
                    text.setSpan(new StyleSpan(Typeface.ITALIC), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    continue;
                }

                if (style.getStyle().equalsIgnoreCase(Const.BOLD_ITALIC))
                {
                    text.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return text;
        }
        return new SpannableString("");
    }
}
