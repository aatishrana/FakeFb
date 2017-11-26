package com.aatishrana.fakefb.notification.presenter;

import com.aatishrana.fakefb.base.Presenter;
import com.aatishrana.fakefb.data.FbData;
import com.aatishrana.fakefb.data.MainRepository;
import com.aatishrana.fakefb.notification.Noti;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aatish Rana on 22-Nov-17.
 */

public class NotificationPresenter implements Presenter<NotificationView>
{
    private MainRepository repository;
    private NotificationView view;
    private CompositeDisposable disposables;
    private List<Noti> cache;

    public NotificationPresenter(MainRepository repository)
    {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void onViewAttached(NotificationView view)
    {
        this.view = view;
    }

    @Override
    public void onViewDetached()
    {
        this.view = null;
    }

    @Override
    public void onDestroyed()
    {
        cleanCache();
        disposables.dispose();
    }

    public void getData()
    {
        if (cache != null && !cache.isEmpty())
            view.render(new NotificationViewModel(cache, false));
        else
        {
            view.render(new NotificationViewModel(null, true));
            repository.getData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FbData>()
                    {
                        @Override
                        public void onSubscribe(Disposable d)
                        {
                            disposables.add(d);
                        }

                        @Override
                        public void onNext(FbData value)
                        {
                            if (value != null && value.getNotificationData() != null && !value.getNotificationData().isEmpty())
                            {
                                cache = copyData(value.getNotificationData());
                                view.render(new NotificationViewModel(cache, false));
                            } else
                                view.render(new NotificationViewModel(null, false));
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            e.printStackTrace();
                            view.render(new NotificationViewModel(null, false));
                        }

                        @Override
                        public void onComplete()
                        {

                        }
                    });
        }
    }

    private List<Noti> copyData(List<Noti> notificationData)
    {
        List<Noti> data = new ArrayList<>();
        data.addAll(notificationData);
        return data;
    }

    public void cleanCache()
    {
        if (this.cache != null)
        {
            this.cache = null;
        }
    }
}
