package com.ssadeo.ui.activity.register;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.MyOTP;
import com.ssadeo.data.network.model.SettingsResponse;
import com.ssadeo.data.network.model.Token;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface RegisterIView extends MvpView{
    void onSuccess(Token token);
    void onSuccess(MyOTP otp);
    void onError(Throwable e);
    void onSuccess(SettingsResponse response);
}
