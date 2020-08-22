package com.ssadeo.ui.fragment.invoice;

import com.ssadeo.base.BasePresenter;
import com.ssadeo.data.network.APIClient;
import com.ssadeo.data.network.model.Message;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class InvoicePresenter<V extends InvoiceIView> extends BasePresenter<V> implements InvoiceIPresenter<V> {

    @Override
    public void payment(HashMap<String, Object> params) {
        Observable modelObservable = APIClient.getAPIClient().payment(params);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Message) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
