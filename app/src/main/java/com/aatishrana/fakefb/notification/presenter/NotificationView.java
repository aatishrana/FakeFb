package com.aatishrana.fakefb.notification.presenter;

import com.aatishrana.fakefb.notification.Noti;

import java.util.List;

/**
 * Created by Aatish Rana on 22-Nov-17.
 */

public interface NotificationView
{
    void showData(List<Noti> cache);

    void showError();
}
