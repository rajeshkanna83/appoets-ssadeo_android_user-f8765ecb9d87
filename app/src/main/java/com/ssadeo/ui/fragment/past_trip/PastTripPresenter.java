package com.ssadeo.ui.fragment.past_trip;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.Datum;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class PastTripPresenter<V extends PastTripIView> extends BasePresenter<V> implements PastTripIPresenter<V> {

    @Override
    public void pastTrip() {
        Observable modelObservable = APIClient.getAPIClient().pastTrip();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<Datum>) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
