package com.ssadeo.ui.fragment.service;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.EstimateFare;
import com.ssadeo.data.network.model.RateCardResponse;
import com.ssadeo.data.network.model.Service;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface ServiceIView extends MvpView{
    void onSuccess(List<Service> serviceList);
    void onSuccess(RateCardResponse service);
    void onSuccess(EstimateFare estimateFare);
    void onError(Throwable e);
}
