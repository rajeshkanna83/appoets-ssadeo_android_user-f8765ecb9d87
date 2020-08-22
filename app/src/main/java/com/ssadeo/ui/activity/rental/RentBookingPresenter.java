package com.ssadeo.ui.activity.rental;


import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.EstimateFare;
import com.ssadeo.data.network.model.Service;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class RentBookingPresenter<V extends RentBookingIView> extends BasePresenter<V> implements RentBookingIPresenter<V> {
    @Override
    public void services() {

        Observable modelObservable = APIClient.getAPIClient().services();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().onSuccess((List<Service>) trendsResponse);
                        },
                        (Consumer) throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void sendRequest(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().sendRequest(obj);
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            getMvpView().onSuccessRequest((Object) trendsResponse);
                        },
                        throwable -> {
                            getMvpView().hideLoading();
                            getMvpView().onError((Throwable) throwable);
                        });
    }

    @Override
    public void estimateFare(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().estimateFare(obj);
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            getMvpView().onSuccess((EstimateFare) trendsResponse);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            getMvpView().onError((Throwable) throwable);
                        });
    }
}
