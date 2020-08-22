package com.ssadeo.ui.activity.wallet;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.User;
import com.ssadeo.data.network.model.WalletResponse;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface WalletIView extends MvpView {

    void onSuccess(User user);
    void onSuccess(WalletResponse walletResponse);
    void onError(Throwable e);
}
