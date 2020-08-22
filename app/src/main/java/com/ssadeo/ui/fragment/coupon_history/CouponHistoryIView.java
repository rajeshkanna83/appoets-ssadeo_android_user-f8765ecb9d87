package com.ssadeo.ui.fragment.coupon_history;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.Coupon;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface CouponHistoryIView extends MvpView {
    void onSuccess(List<Coupon> couponList);
    void onError(Throwable e);
}
