package com.ssadeo.ui.activity.card;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.Card;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class CardsPresenter<V extends CardsIView> extends BasePresenter<V> implements CarsIPresenter<V> {


    @Override
    public void card() {
        Observable modelObservable = APIClient.getAPIClient().card();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<Card>) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
