package com.aatishrana.fakefb.newsFeed.presenter;

import com.aatishrana.fakefb.base.Presenter;
import com.aatishrana.fakefb.data.FbData;
import com.aatishrana.fakefb.data.MainRepository;
import com.aatishrana.fakefb.newsFeed.model.FeedItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aatish on 11/21/2017.
 */

public class NewsFeedPresenter implements Presenter<NewsFeedView>
{
    private MainRepository repository;
    private NewsFeedView view;
    private CompositeDisposable disposables;
    private List<FeedItem> cache;

    public NewsFeedPresenter(MainRepository repository)
    {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void onViewAttached(NewsFeedView view)
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
            view.render(new NewsFeedViewModel(cache, false));
        else
        {
            view.render(new NewsFeedViewModel(null, true));
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
                            if (value != null && value.getNewsFeed() != null && !value.getNewsFeed().isEmpty())
                            {
                                cache = copyData(value.getNewsFeed());
                                view.render(new NewsFeedViewModel(cache, false));
                            } else
                                view.render(new NewsFeedViewModel(null, false));
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            e.printStackTrace();
                            view.render(new NewsFeedViewModel(null, false));
                        }

                        @Override
                        public void onComplete()
                        {

                        }
                    });
        }
    }

    private List<FeedItem> copyData(List<FeedItem> newsFeed)
    {
        List<FeedItem> data = new ArrayList<>();
        data.addAll(newsFeed);
        return data;
    }

    public void cleanCache()
    {
        this.cache = null;
    }
}
