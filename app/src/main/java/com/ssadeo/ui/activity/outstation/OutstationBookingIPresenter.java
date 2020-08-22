package com.ssadeo.ui.activity.outstation;


import com.ssadeo.base.MvpPresenter;

import java.util.HashMap;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface OutstationBookingIPresenter<V extends OutstationBookingIView> extends MvpPresenter<V> {
    void services();
    void sendRequest(HashMap<String, Object> params);
    void estimateFare(HashMap<String, Object> params);
}
