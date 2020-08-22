package com.ssadeo.ui.fragment.book_ride;

import com.ssadeo.base.MvpView;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface BookRideIView extends MvpView{
    void onSuccess(Object object);
    void onError(Throwable e);
}
