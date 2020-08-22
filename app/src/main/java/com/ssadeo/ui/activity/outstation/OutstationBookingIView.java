package com.ssadeo.ui.activity.outstation;


import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.EstimateFare;
import com.ssadeo.ui.adapter.ServiceAdapterSingle;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface OutstationBookingIView extends MvpView {

    void onSuccess(ServiceAdapterSingle adapter);

    void onSuccessRequest(Object object);
    void onSuccess(EstimateFare estimateFare);
}
