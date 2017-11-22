package com.aatishrana.fakefb.notification.presenter;

import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.data.MainRepository;

/**
 * Created by Aatish Rana on 22-Nov-17.
 */

public class NotificationPresenterFactory implements PresenterFactory<NotificationPresenter>
{
    private MainRepository repository;

    public NotificationPresenterFactory(MainRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public NotificationPresenter create()
    {
        return new NotificationPresenter(this.repository);
    }
}
