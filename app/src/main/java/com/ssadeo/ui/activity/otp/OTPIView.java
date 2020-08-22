package com.ssadeo.ui.activity.otp;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.MyOTP;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface OTPIView extends MvpView{
    void onSuccess(MyOTP otp);
    void onError(Throwable e);
}
