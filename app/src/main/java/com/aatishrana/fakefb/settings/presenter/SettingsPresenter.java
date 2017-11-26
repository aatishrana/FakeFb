package com.aatishrana.fakefb.settings.presenter;

import com.aatishrana.fakefb.base.Presenter;
import com.aatishrana.fakefb.data.MainRepository;
import com.aatishrana.fakefb.settings.FbData;
import com.aatishrana.fakefb.utils.H;

import java.util.List;

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

    public void createData(String name, String url)
    {
        view.showLoading();
        this.repository.createNewData(name, url)
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
                        if (value)
                            view.showSuccess();
                        else
                            view.showError("Not able to process data");
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        e.printStackTrace();
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }

    public void showListOfAllData()
    {
        view.showAllDataList(this.repository.getListOfAllData());
    }

    public void loadData(String name)
    {
        this.repository.loadAFbData(name)
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

    public void removeData(String name)
    {
        view.onRemoveDataResponse(repository.removeFbData(name));
    }
}
