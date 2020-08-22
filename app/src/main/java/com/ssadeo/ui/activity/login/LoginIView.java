package com.ssadeo.ui.activity.login;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.ForgotResponse;
import com.ssadeo.data.network.model.Token;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface LoginIView extends MvpView{
    void onSuccess(Token token);
    void onSuccess(ForgotResponse object);
    void onError(Throwable e);
}
