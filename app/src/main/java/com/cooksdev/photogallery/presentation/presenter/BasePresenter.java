package com.cooksdev.photogallery.presentation.presenter;

/**
 * Created by roma on 17.08.16.
 */
public interface BasePresenter<T> {

    void setView(T t);
    void onStop();
}
