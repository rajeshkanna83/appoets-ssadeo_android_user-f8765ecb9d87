package com.ssadeo;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.ssadeo.common.ConnectivityReceiver;
import com.ssadeo.common.LocaleHelper;
import com.ssadeo.data.SharedHelper;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */

public class MvpApplication extends Application {

    private static MvpApplication mInstance;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    public static final int PICK_LOCATION_REQUEST_CODE = 3;
    public static final int PERMISSIONS_REQUEST_PHONE = 4;
    public static float DEFAULT_ZOOM = 18;
    public static Location mLastKnownLocation;

    public static boolean isCCAvenueEnabled = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        MultiDex.install(this);
    }

    public static synchronized MvpApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
        //super.attachBaseContext(newBase);
        MultiDex.install(newBase);
    }

    public NumberFormat getNumberFormat() {
        String currencyCode = SharedHelper.getKey(MvpApplication.getInstance(), "currency_code", "GHS");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        if (currencyCode != null)
            numberFormat.setCurrency(Currency.getInstance(currencyCode));
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
