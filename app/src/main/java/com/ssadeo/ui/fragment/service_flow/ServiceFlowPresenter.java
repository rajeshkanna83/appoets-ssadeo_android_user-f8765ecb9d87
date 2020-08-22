package com.ssadeo.ui.fragment.service_flow;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.DataResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class ServiceFlowPresenter<V extends ServiceFlowIView> extends BasePresenter<V> implements ServiceFlowIPresenter<V> {

    @Override
    public void checkStatus() {
        Observable modelObservable = APIClient.getAPIClient().checkStatus();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((DataResponse) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
