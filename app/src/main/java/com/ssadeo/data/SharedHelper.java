package com.ssadeo.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.ssadeo.user.BuildConfig;

public class SharedHelper {

    public static final String CURRENCY = "currency";
    public static final String NAME = "userName";
    public static final String GOOGLE_API_KEY = "google_api_key";
    public static final String EMAIL = "userMail";
    public static final String MOBILE = "userMobile";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static void putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.apply();

    }

    public static String getKey(Context context, String Key) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, "");
    }

    public static void putKey(Context context, String Key, boolean Value) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(Key, Value);
        editor.apply();
    }

    public static void putKey(Context context, String Key, Integer value) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(Key, value);
        editor.apply();
    }

    public static Integer getIntKey(Context context, String Key) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Key, -1);
    }

    public static boolean getBoolKey(Context context, String Key, boolean defalultValue) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Key, defalultValue);
    }

    public static String getKey(Context context, String Key, String defaultValue) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, defaultValue);
    }

    public static void clearSharedPreferences(Context context) {
        String device_token = String.valueOf(getKey(context, "device_token"));
        String device_id = String.valueOf(getKey(context, "device_id"));

        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        putKey(context, "device_token", device_token);
        putKey(context, "device_id", device_id);
    }


}
