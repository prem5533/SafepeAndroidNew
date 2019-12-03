package com.safepayu.wallet.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.safepayu.wallet.helper.Config;

public class OtpVerifyService extends IntentService {
    private static String TAG = OtpVerifyService.class.getSimpleName();

    public OtpVerifyService() {
        super(OtpVerifyService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra(Config.OTP);
            verifyOtp(otp);
        }
    }

    private void verifyOtp(String otp) {
        Intent intent = new Intent(Config.OTP_VERIFICATION);
        intent.putExtra(Config.OTP, otp);
//        intent.putExtra(Config.OTP_VERIFICATION_STATUS, true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
