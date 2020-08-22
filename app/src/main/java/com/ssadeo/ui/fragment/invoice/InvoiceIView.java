package com.ssadeo.ui.fragment.invoice;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.Message;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface InvoiceIView extends MvpView{
    void onSuccess(Message message);
    void onError(Throwable e);
}
