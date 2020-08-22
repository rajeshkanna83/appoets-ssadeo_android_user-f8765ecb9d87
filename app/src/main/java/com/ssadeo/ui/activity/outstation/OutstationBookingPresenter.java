package com.ssadeo.ui.activity.outstation;


import android.widget.Toast;

import com.ssadeo.user.R;
import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.EstimateFare;
import com.ssadeo.data.network.model.Service;
import com.ssadeo.ui.adapter.ServiceAdapterSingle;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class OutstationBookingPresenter<V extends OutstationBookingIView> extends BasePresenter<V> implements OutstationBookingIPresenter<V> {
    @Override
    public void services() {

        Observable modelObservable = APIClient.getAPIClient().services();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            ServiceAdapterSingle adapter = new ServiceAdapterSingle(getMvpView().activity(), (List<Service>) trendsResponse);
                            getMvpView().onSuccess(adapter);
                        },
                        (Consumer) throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void sendRequest(HashMap<String, Object> params) {
        Observable modelObservable = APIClient.getAPIClient().sendRequest(params);
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            Toast.makeText(activity(), R.string.new_outstation_request_created, Toast.LENGTH_SHORT).show();
                            OutstationBookingPresenter.this.getMvpView().onSuccessRequest((Object) trendsResponse);
                        },
                        throwable -> {
                            getMvpView().hideLoading();
                            OutstationBookingPresenter.this.getMvpView().onError((Throwable) throwable);
                        });
    }

    @Override
    public void estimateFare(HashMap<String, Object> params) {
        getMvpView().showLoading();
        Observable modelObservable = APIClient.getAPIClient().estimateFare(params);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            OutstationBookingPresenter.this.getMvpView().onSuccess((EstimateFare) trendsResponse);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            OutstationBookingPresenter.this.getMvpView().onError((Throwable) throwable);
                        });
    }
}
