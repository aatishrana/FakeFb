package com.aatishrana.fakefb.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aatishrana.fakefb.MainActivity;
import com.aatishrana.fakefb.R;
import com.aatishrana.fakefb.base.BasePresenterFragment;
import com.aatishrana.fakefb.base.PresenterFactory;
import com.aatishrana.fakefb.settings.presenter.SettingsPresenter;
import com.aatishrana.fakefb.settings.presenter.SettingsPresenterFactory;
import com.aatishrana.fakefb.settings.presenter.SettingsView;


/**
 * Created by Aatish Rana on 07-Nov-17.
 */

public class Settings extends BasePresenterFragment<SettingsPresenter, SettingsView> implements SettingsView
{
    private SettingsPresenter presenter;

    public Settings()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view)
    {
        view.findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.loadData(1);
            }
        });

        view.findViewById(R.id.btn_two).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.loadData(2);
            }
        });
    }

    @NonNull
    @Override
    protected PresenterFactory<SettingsPresenter> getPresenterFactory()
    {
        return new SettingsPresenterFactory(((MainActivity) getActivity()).getRepository());
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull SettingsPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public void onLoadDataResponse(boolean value)
    {
        Toast.makeText(getActivity(), String.valueOf(value), Toast.LENGTH_SHORT).show();
    }
}
