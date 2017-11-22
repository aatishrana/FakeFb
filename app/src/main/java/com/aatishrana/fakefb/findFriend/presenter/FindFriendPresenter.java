package com.aatishrana.fakefb.findFriend.presenter;

import com.aatishrana.fakefb.base.Presenter;
import com.aatishrana.fakefb.data.FbData;
import com.aatishrana.fakefb.data.FindFriendData;
import com.aatishrana.fakefb.data.MainRepository;
import com.aatishrana.fakefb.findFriend.Friend;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aatish Rana on 21-Nov-17.
 */

public class FindFriendPresenter implements Presenter<FindFriendView>
{
    private MainRepository repository;
    private FindFriendView view;

    private FindFriendData cache;
    private CompositeDisposable disposables;

    public FindFriendPresenter(MainRepository repository)
    {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void onViewAttached(FindFriendView view)
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
        if (cache != null)
            view.render(new FindFriendViewModel(cache, false));
        else
        {
            view.render(new FindFriendViewModel(null, true));
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
                        public void onNext(FbData data)
                        {
                            if (data != null && data.getFindFriendData() != null)
                            {
                                cache = copyData(data.getFindFriendData());
                                view.render(new FindFriendViewModel(cache, false));
                            } else
                                view.render(new FindFriendViewModel(null, false));
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            e.printStackTrace();
                            view.render(new FindFriendViewModel(null, false));
                        }

                        @Override
                        public void onComplete()
                        {

                        }
                    });
        }
    }

    /**
     * Make a copy of data
     *
     * @param findFriendData source
     * @return destination
     */
    private FindFriendData copyData(FindFriendData findFriendData)
    {
        if (findFriendData == null)
            return null;

        List<Friend> requests = new ArrayList<>();
        if (findFriendData.getFriendRequests() != null && !findFriendData.getFriendRequests().isEmpty())
            requests.addAll(findFriendData.getFriendRequests());

        List<Friend> suggestions = new ArrayList<>();
        if (findFriendData.getFriendSuggestions() != null && !findFriendData.getFriendSuggestions().isEmpty())
            suggestions.addAll(findFriendData.getFriendSuggestions());


        return new FindFriendData(requests, suggestions);
    }

    public void cleanCache()
    {
        this.cache = null;
    }
}
