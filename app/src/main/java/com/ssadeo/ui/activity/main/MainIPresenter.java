package com.ssadeo.ui.activity.main;

import com.ssadeo.base.MvpPresenter;
import com.ssadeo.data.network.model.Message;

import java.util.HashMap;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface MainIPresenter<V extends MainIView> extends MvpPresenter<V> {
    void profile();
    void logout(String id);
    void checkStatus();
    void providers(HashMap<String, Object> params);
    void getNavigationSettings();
    void payment(HashMap<String, Object> params);
}
