package com.aatishrana.fakefb.findFriend.presenter;

import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.data.MainRepository;

/**
 * Created by Aatish Rana on 21-Nov-17.
 */

public class FindFriendPresenterFactory implements PresenterFactory<FindFriendPresenter>
{
    private MainRepository repository;

    public FindFriendPresenterFactory(MainRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public FindFriendPresenter create()
    {
        return new FindFriendPresenter(this.repository);
    }
}
