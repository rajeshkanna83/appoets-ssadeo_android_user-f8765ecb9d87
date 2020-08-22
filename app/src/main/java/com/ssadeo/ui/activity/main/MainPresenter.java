package com.ssadeo.ui.activity.main;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.DataResponse;
import com.ssadeo.data.network.model.Message;
import com.ssadeo.data.network.model.Provider;
import com.ssadeo.data.network.model.SettingsResponse;
import com.ssadeo.data.network.model.User;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class MainPresenter<V extends MainIView> extends BasePresenter<V> implements MainIPresenter<V> {
    @Override
    public void profile() {

        Observable modelObservable = APIClient.getAPIClient().profile();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((User) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void checkStatus() {
        Observable modelObservable = APIClient.getAPIClient().checkStatus();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object trendsResponse) throws Exception {
                                   MainPresenter.this.getMvpView().onSuccess((DataResponse) trendsResponse);
                               }
                           },
                        new Consumer() {
                            @Override
                            public void accept(Object throwable) throws Exception {
                                MainPresenter.this.getMvpView().onError((Throwable) throwable);
                            }
                        });
    }

    @Override
    public void payment(HashMap<String, Object> params) {
        Observable modelObservable = APIClient.getAPIClient().payment(params);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Message) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void logout(String id) {
        Observable modelObservable = APIClient.getAPIClient().logout(id);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessLogout((Object) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }


    @Override
    public void providers(HashMap<String, Object> params) {
        Observable modelObservable = APIClient.getAPIClient().providers(params);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<Provider>) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }


    @Override
    public void getNavigationSettings() {
        Observable modelObservable = APIClient.getAPIClient().getSettings();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                    MainPresenter.this.getMvpView().onSuccess((SettingsResponse) response);
                }, (Consumer) throwable -> {
                    MainPresenter.this.getMvpView().onError((Throwable) throwable);
                });
    }
}
