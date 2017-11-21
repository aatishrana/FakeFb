package com.aatishrana.fakefb.newsFeed.presenter;

import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.data.MainRepository;

/**
 * Created by Aatish on 11/21/2017.
 */

public class NewsFeedPresenterFactory implements PresenterFactory<NewsFeedPresenter>
{
    private MainRepository repository;

    public NewsFeedPresenterFactory(MainRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public NewsFeedPresenter create()
    {
        return new NewsFeedPresenter(this.repository);
    }
}
