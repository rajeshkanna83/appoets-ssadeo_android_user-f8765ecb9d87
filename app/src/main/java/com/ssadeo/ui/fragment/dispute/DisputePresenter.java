package com.ssadeo.ui.fragment.dispute;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class DisputePresenter<V extends DisputeIView> extends BasePresenter<V> implements DisputeIPresenter<V> {

    @Override
    public void dispute(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().dispute(obj);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Object) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
