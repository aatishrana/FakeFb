package com.aatishrana.fakefb.notification.presenter;

import com.aatishrana.fakefb.notification.Noti;

import java.util.List;

/**
 * Created by Aatish Rana on 22-Nov-17.
 */

public class NotificationViewModel
{
    private final List<Noti> data;
    private final boolean loading;

    public NotificationViewModel(List<Noti> data, boolean loading)
    {
        this.data = data;
        this.loading = loading;
    }

    public List<Noti> getData()
    {
        return data;
    }

    public boolean isLoading()
    {
        return loading;
    }
}
