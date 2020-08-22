package com.ssadeo.ui.activity.profile;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.User;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface ProfileIView extends MvpView{
    void onSuccess(User user);
    void onError(Throwable e);
}
