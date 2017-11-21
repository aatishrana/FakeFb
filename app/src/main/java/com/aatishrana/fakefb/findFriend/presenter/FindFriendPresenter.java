package com.aatishrana.fakefb.findFriend.presenter;

import com.aatishrana.fakefb.base.Presenter;
import com.aatishrana.fakefb.data.MainRepository;
import com.aatishrana.fakefb.findFriend.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aatish Rana on 21-Nov-17.
 */

public class FindFriendPresenter implements Presenter<FindFriendView>
{
    private MainRepository repository;
    private FindFriendView view;

    private List<Friend> cacheOne, cacheTwo;

    public FindFriendPresenter(MainRepository repository)
    {
        this.repository = repository;
        this.cacheOne = new ArrayList<>();
        this.cacheTwo = new ArrayList<>();
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

    }

    public void getData()
    {
        repository.getFb();
    }
}
