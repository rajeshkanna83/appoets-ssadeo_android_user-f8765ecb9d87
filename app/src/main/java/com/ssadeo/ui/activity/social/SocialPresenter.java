package com.ssadeo.ui.activity.social;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.Token;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class SocialPresenter<V extends SocialIView> extends BasePresenter<V> implements SocialIPresenter<V> {
    @Override
    public void loginGoogle(HashMap<String, Object> obj) {

        Observable modelObservable = APIClient.getAPIClient().loginGoogle(obj);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Token) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
    @Override
    public void loginFacebook(HashMap<String, Object> obj) {

        Observable modelObservable = APIClient.getAPIClient().loginFacebook(obj);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Token) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

}
