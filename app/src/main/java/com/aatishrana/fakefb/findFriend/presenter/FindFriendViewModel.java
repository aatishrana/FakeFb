package com.aatishrana.fakefb.findFriend.presenter;

import com.aatishrana.fakefb.data.FindFriendData;

/**
 * Created by Aatish Rana on 22-Nov-17.
 */

public class FindFriendViewModel
{
    private final FindFriendData data;
    private final boolean loading;

    public FindFriendViewModel(FindFriendData data, boolean loading)
    {
        this.data = data;
        this.loading = loading;
    }


    public FindFriendData getData()
    {
        return data;
    }

    public boolean isLoading()
    {
        return loading;
    }
}
