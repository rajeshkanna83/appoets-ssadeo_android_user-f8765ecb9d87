package com.ssadeo.ui.activity.login;

import com.ssadeo.data.network.APIClient;
import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.model.ForgotResponse;
import com.ssadeo.data.network.model.Token;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class loginPresenter<V extends LoginIView> extends BasePresenter<V> implements LoginIPresenter<V> {
    @Override
    public void login(HashMap<String, Object> obj) {

        Observable modelObservable = APIClient.getAPIClient().login(obj);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Token) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void verifyOTP(HashMap<String, Object> obj) {

        Observable modelObservable = APIClient.getAPIClient().verifyOTP(obj);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Token) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void forgotPassword(String mobile) {

        Observable modelObservable = APIClient.getAPIClient().forgotPassword(mobile);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((ForgotResponse) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
