package com.ssadeo.ui.activity.wallet;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.User;
import com.ssadeo.data.network.model.WalletResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class WalletPresenter<V extends WalletIView> extends BasePresenter<V> implements WalletIPresenter<V> {

    @Override
    public void profile() {

        Observable modelObservable = APIClient.getAPIClient().profile();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((User) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void addMoney(HashMap<String, Object> params) {

        Observable modelObservable = APIClient.getAPIClient().addMoney(params);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((WalletResponse) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
