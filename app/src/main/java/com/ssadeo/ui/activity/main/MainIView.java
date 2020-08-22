package com.ssadeo.ui.activity.main;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.DataResponse;
import com.ssadeo.data.network.model.Message;
import com.ssadeo.data.network.model.Provider;
import com.ssadeo.data.network.model.SettingsResponse;
import com.ssadeo.data.network.model.User;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface MainIView extends MvpView{
    void onSuccess(User user);
    void onSuccess(DataResponse dataResponse);
    void onSuccessLogout(Object object);
    void onSuccess(List<Provider> objects);
    void onError(Throwable e);
    void onSuccess(SettingsResponse response);
    void onSuccess(Message message);
}
