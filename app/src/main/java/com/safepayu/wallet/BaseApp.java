package com.safepayu.wallet;

import android.app.Application;
import android.os.Handler;

import com.safepayu.wallet.halper.ToastHelper;
import com.safepayu.wallet.utils.CommonUtils;
import com.safepayu.wallet.utils.DateUtil;
import com.safepayu.wallet.utils.SharedPref;


import io.reactivex.disposables.CompositeDisposable;

public class BaseApp extends Application {
    private static BaseApp mInstance;

    public static synchronized BaseApp getInstance() {
        return mInstance;
    }

    private CompositeDisposable disposable;
    private ToastHelper toastHelper;
    private CommonUtils commonUtils;
    private DateUtil dateUtil;
    private SharedPref sharedPref;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        disposable = new CompositeDisposable();
        toastHelper = new ToastHelper();
        commonUtils = new CommonUtils(this);
        dateUtil = new DateUtil();
        sharedPref = new SharedPref(this);
        handler = new Handler();
    }

    public SharedPref sharedPref() {
        return sharedPref;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public ToastHelper toastHelper() {
        return toastHelper;
    }

    public CommonUtils commonUtils() {
        return commonUtils;
    }

    public DateUtil dateUtil() {
        return dateUtil;
    }

    public Handler handler() {
        return handler;
    }
}
