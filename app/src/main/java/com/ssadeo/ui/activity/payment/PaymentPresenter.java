package com.ssadeo.ui.activity.payment;

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
public class PaymentPresenter<V extends PaymentIView> extends BasePresenter<V> implements PaymentIPresenter<V> {

    @Override
    public void deleteCard(String cardId) {

        Observable modelObservable = APIClient.getAPIClient().deleteCard(cardId, "DELETE");

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Object) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void card() {

        Observable modelObservable = APIClient.getAPIClient().card();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<Card>) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
