package com.ssadeo.ui.activity.wallet;

import com.ssadeo.base.MvpPresenter;

import java.util.HashMap;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface WalletIPresenter<V extends WalletIView> extends MvpPresenter<V>{
    void profile();
    void addMoney( HashMap<String, Object> params);
}
