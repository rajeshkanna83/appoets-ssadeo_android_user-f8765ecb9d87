package com.ssadeo.ui.activity.change_password;


import com.ssadeo.base.MvpPresenter;

import java.util.HashMap;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface ChangePasswordIPresenter<V extends ChangePasswordIView> extends MvpPresenter<V> {
    void changePassword(HashMap<String, Object> parms);
}
