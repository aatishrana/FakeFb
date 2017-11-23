package com.aatishrana.fakefb.settings.presenter;

import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.data.MainRepository;

/**
 * Created by Aatish on 11/23/2017.
 */

public class SettingsPresenterFactory implements PresenterFactory<SettingsPresenter>
{
    private MainRepository repository;

    public SettingsPresenterFactory(MainRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public SettingsPresenter create()
    {
        return new SettingsPresenter(this.repository);
    }
}
