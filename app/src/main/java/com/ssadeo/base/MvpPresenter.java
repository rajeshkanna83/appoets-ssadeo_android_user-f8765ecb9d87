package com.ssadeo.base;

import android.app.Activity;

import com.ssadeo.MvpApplication;


public interface MvpPresenter<V extends MvpView> {
    Activity activity();
    MvpApplication appContext();
    void attachView(V mvpView);

    void detachView();

}
