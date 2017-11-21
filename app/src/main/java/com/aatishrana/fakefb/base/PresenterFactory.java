package com.aatishrana.fakefb.base;

/**
 * Created by Aatish Rana on 21-Nov-17.
 */

public interface PresenterFactory<T extends Presenter> {
    T create();
}
