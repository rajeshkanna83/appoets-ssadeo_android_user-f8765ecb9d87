package com.ssadeo.ui.fragment.service_flow;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.DataResponse;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface ServiceFlowIView extends MvpView{
    void onSuccess(DataResponse dataResponse);
    void onError(Throwable e);
}
