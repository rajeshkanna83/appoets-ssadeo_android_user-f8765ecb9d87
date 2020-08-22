package com.ssadeo.ui.activity.forgot_password;

import com.ssadeo.base.MvpView;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface ForgotPasswordIView extends MvpView {
    void onSuccess(Object object);
    void onError(Throwable e);
}
