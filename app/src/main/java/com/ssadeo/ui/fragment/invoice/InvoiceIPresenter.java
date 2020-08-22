package com.ssadeo.ui.fragment.invoice;

import com.ssadeo.base.MvpPresenter;

import java.util.HashMap;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface InvoiceIPresenter<V extends InvoiceIView> extends MvpPresenter<V> {
    void payment(HashMap<String, Object> params);
}
