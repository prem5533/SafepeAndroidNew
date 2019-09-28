package com.safepayu.wallet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPref {
    private static final String SHARED_PREF_NAME = "safepe_pref";
    private Context context;

    public final String IS_FB_TOKEN_UPDATE = "IS_FB_TOKEN_UPDATE";
    public final String FB_TOKEN_ID = "FB_TOKEN_ID";
    public final String USER = "userData";
    public final String REFERRAL_USER = "referralUser";
    public final String ACCESS_TOKEN = "accessToken";
    public final String ACCESS_TOKEN_EXPIRE_IN = "accessTokenExpiresIn";
    public final String USER_ID = "userId";

    public SharedPref(Context context) {
        this.context = context;
    }

    private static SharedPreferences getUserSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }


    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public Long getLong(String key) {
        return getUserSharedPreferences(context).getLong(key, 0);
    }

    public boolean getBoolean(String key) {
        return getUserSharedPreferences(context).getBoolean(key, false);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }


    public String getString(String key) {
        return getUserSharedPreferences(context).getString(key, null);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return getUserSharedPreferences(context).getInt(key, 0);
    }

    public void setObject(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
//        editor.putString(key, new Gson().toJson(value));
        editor.putString(key, value);
        editor.commit();
    }

    public Object getObject(String key, final Class<?> aClass) {
        return new Gson().fromJson(getString(key), aClass);
    }

    public void clearPref() {
        SharedPreferences.Editor editor = getUserSharedPreferences(context).edit();
        String fcmToken = getString(FB_TOKEN_ID);
        editor.clear();
        editor.apply();

        if (fcmToken != null) {
            setString(FB_TOKEN_ID, fcmToken);
        }

    }
}
