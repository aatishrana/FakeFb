package com.aatishrana.fakefb.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by Aatish Rana on 21-Nov-17.
 */

public abstract class BasePresenterFragment<P extends Presenter<V>, V> extends Fragment
{

    private static final String TAG = "base-fragment";
    private static final int LOADER_ID = 101;

    private P presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


        Loader<P> loader = getLoaderManager().getLoader(loaderId());
        if (loader == null)
        {
            initLoader();
        } else
        {
            this.presenter = ((PresenterLoader<P>) loader).getPresenter();
            onPresenterCreatedOrRestored(presenter);
        }
    }

    private void initLoader()
    {
        // LoaderCallbacks as an object, so no hint regarding loader will be leak to the subclasses.
        getLoaderManager().initLoader(loaderId(), null, new LoaderManager.LoaderCallbacks<P>()
        {
            @Override
            public final Loader<P> onCreateLoader(int id, Bundle args)
            {
                return new PresenterLoader<>(getContext(), getPresenterFactory());
            }

            @Override
            public final void onLoadFinished(Loader<P> loader, P presenter)
            {
                BasePresenterFragment.this.presenter = presenter;
                onPresenterCreatedOrRestored(presenter);
            }

            @Override
            public final void onLoaderReset(Loader<P> loader)
            {
                BasePresenterFragment.this.presenter = null;
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        presenter.onViewAttached(getPresenterView());
    }

    @Override
    public void onStop()
    {
        presenter.onViewDetached();
        super.onStop();
    }


    @NonNull
    protected abstract PresenterFactory<P> getPresenterFactory();

    protected abstract void onPresenterCreatedOrRestored(@NonNull P presenter);

    @NonNull
    protected V getPresenterView()
    {
        return (V) this;
    }

    protected int loaderId()
    {
        return LOADER_ID;
    }
}
