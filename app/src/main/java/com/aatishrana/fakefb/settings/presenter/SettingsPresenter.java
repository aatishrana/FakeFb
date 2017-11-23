package com.aatishrana.fakefb.settings.presenter;

import com.aatishrana.fakefb.base.Presenter;
import com.aatishrana.fakefb.data.MainRepository;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aatish on 11/23/2017.
 */

public class SettingsPresenter implements Presenter<SettingsView>
{
    private SettingsView view;
    private MainRepository repository;
    private CompositeDisposable disposable;

    public SettingsPresenter(MainRepository repository)
    {
        this.repository = repository;
        this.disposable = new CompositeDisposable();
    }

    @Override
    public void onViewAttached(SettingsView view)
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
        if (disposable != null)
            disposable.dispose();
    }

    public void loadData(int i)
    {
        this.repository.createNewFb("", i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value)
                    {
                        view.onLoadDataResponse(value);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        e.printStackTrace();
                        view.onLoadDataResponse(false);
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }
}
