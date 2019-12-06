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
    public final String DEP_DAY = "depDay";
    public final String USER_EMAIL = "userEmail";
    public final String USER_FIRST_NAME = "User_first_name";
    public final String USER_LAST_NAME = "User_last_name";
    public final String PASSCODE = "passcode";
    public final String MOBILE = "mobile";
    public final String IS_BLOCKED = "is_blocked";
    public final String PACKAGE_PURCHASED = "package_purchased";
    public final String EMAIL_VERIFIED = "email_verified";
    public final String FIREBASE_TOKEN = "FirebaseToken";
    public final String REMEMBER_ME = "remember_me";
    public final String LOGOUT_ALL = "logout_all";
    public final String LOGO_IMAGE = "logo_image";

    //***********FLIGHT*******************
    public final String FLIGHT_DATE = "flight_date";
    public final String FLIGHT_SOURCE = "flight_source";
    public final String FLIGHT_DESTINATION = "flight_destination";
    public final String FLIGHT_JOURNEY_DATE = "flight_journey_date";
    public final String FLIGHT_TRIP_TYPE = "flight_trip_type";
    public final String FLIGHT_USER = "flight_user";
    public final String FLIGHT_USER_TYPE = "flight_user_type";
    public final String FLIGHT_ADULTS = "flight_adult";
    public final String FLIGHT_INFANTS = "flight_infant";
    public final String FLIGHT_CHILDREN = "flight_children";
    public final String FLIGHT_TYPE = "flight_type";
    public final String FLIGHT_RETURN_DATE = "flight_return_date";
    public final String FLIGHT_TRAVELLERS_CLASS = "flight_trvellers_class";
    public final String FLIGHT_TRAVELLERS_COUNT = "flight_trvellers_count";
    public final String FLIGHT_TRAVELLERS_CLASS_TYPE = "flight_trvellers_class_type";
    public final String FLIGHT_SOURCE_FULL_NAME = "flight_source_full_name";
    public final String FLIGHT_DESTINATION_FULL_NAME = "flight_destination_full_name";
    public final String FLIGHT_IMAGE = "flight_image";
    public final String FLIGHT_NAME = "flight_name";
    public final String FLIGHT_OPERATING_AIRLINE_CODE = "flight_airline_code";
    public final String FLIGHT_OPERATING_AIRLINE_FLIGHT_NUMBER = "flight_airline_flight_number";
    public final String FLIGHT_DURATION = "flight_duration";
    public final String FLIGHT_HAND_BAGGAGE = "flight_hand_baggage";
    public final String FLIGHT_CHECKIN_BAGGAGE = "flight_checkin_baggage";
    public final String FLIGHT_TOTAL_FARE = "flight_total_fare";
    public final String FLIGHT_DEP_TIME = "flight_dep_time";
    public final String FLIGHT_ARRIVAL_TIME = "flight_arrival_time";

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
