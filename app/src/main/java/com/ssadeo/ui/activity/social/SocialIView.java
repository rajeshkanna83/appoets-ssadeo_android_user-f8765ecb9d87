package com.ssadeo.ui.activity.social;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.Token;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface SocialIView extends MvpView{
    void onSuccess(Token token);
    void onError(Throwable e);
}
