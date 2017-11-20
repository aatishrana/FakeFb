package com.aatishrana.fakefb.newsFeed.model;

/**
 * Created by Aatish Rana on 15-Nov-17.
 */

public class FeedItemBaseEmo
{
    private final long totalEmotions;
    private final long totalComments;
    private final long totalShares;
    private final long totalViews;

    public FeedItemBaseEmo(long totalEmotions, long totalComments, long totalShares, long totalViews)
    {
        this.totalEmotions = totalEmotions;
        this.totalComments = totalComments;
        this.totalShares = totalShares;
        this.totalViews = totalViews;
    }

    public String getTotalEmotions()
    {
        return getFormattedCount(totalEmotions);
    }

    public String getTotalComments()
    {
        return getFormattedCount(totalComments);
    }

    public String getTotalShares()
    {
        return getFormattedCount(totalShares);
    }

    public String getTotalViews()
    {
        return getFormattedCount(totalViews);
    }

    public boolean hasEmotions()
    {
        return totalEmotions != 0;
    }

    public boolean hasComments()
    {
        return totalComments != 0;
    }

    public boolean hasShares()
    {
        return totalShares != 0;
    }

    public boolean hasViews()
    {
        return totalViews != 0;
    }

    private String getFormattedCount(long value)
    {
        final int K = 1000;
        final int M = 1000000;
        final int B = 100000000;

        if (value == 0)
            return "0";

        if (value < K)
            return String.valueOf(value);

        if (value < M)
            return String.valueOf((value / K) + "K");

        if (value < B)
            return String.valueOf((value / M) + "M");

        return String.valueOf((value / B) + "B");
    }

    @Override
    public String toString()
    {
        return "FeedItemBaseEmo{" +
                "totalEmotions=" + totalEmotions +
                ", totalComments=" + totalComments +
                ", totalShares=" + totalShares +
                ", totalViews=" + totalViews +
                '}';
    }
}
