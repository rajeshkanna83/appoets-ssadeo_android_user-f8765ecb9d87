package com.ssadeo.ui.activity.help;

import com.ssadeo.base.MvpView;
import com.ssadeo.data.network.model.Help;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface HelpIView extends MvpView {
    void onSuccess(Help help);
    void onError(Throwable e);
}
