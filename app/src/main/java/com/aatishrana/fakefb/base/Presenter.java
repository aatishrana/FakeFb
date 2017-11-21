package com.aatishrana.fakefb.base;

/**
 * Created by Aatish Rana on 21-Nov-17.
 */

public interface Presenter<V>
{
    void onViewAttached(V view);

    void onViewDetached();

    void onDestroyed();
}
