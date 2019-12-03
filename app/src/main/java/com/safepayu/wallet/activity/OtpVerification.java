package com.safepayu.wallet.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.Config;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class OtpVerification extends BaseActivity implements View.OnClickListener {
    private TextView otpReadRemainingTime, mobileNo;
    private LinearLayout resendAgainLayout;
    private EditText otp;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private LoadingDialog loadingDialog;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        otp = findViewById(R.id.et_otp);
        mobileNo = findViewById(R.id.tv_mobileNo1);

        otpReadRemainingTime = findViewById(R.id.tv_otpReadRemainingTime);
        resendAgainLayout = findViewById(R.id.layout_resendAgainLayout);

        try{
            mobileNo.setText("+91 " + BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        }catch (Exception e){
            e.printStackTrace();
        }

        findViewById(R.id.btn_verify).setOnClickListener(this);
        findViewById(R.id.btn_resendOtp).setOnClickListener(this);

        loadingDialog = new LoadingDialog(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.OTP_VERIFICATION)) {
                    if (intent.getStringExtra(Config.OTP) != null) {
                        countDownTimer.cancel();
                        otpReadRemainingTime.setVisibility(View.INVISIBLE);
                        otp.setText(intent.getStringExtra(Config.OTP));
                        verifyOtp(intent.getStringExtra(Config.OTP));
                    }
                    if (intent.getBooleanExtra(Config.OTP_VERIFICATION_STATUS, false)) {

                    }
                }
            }
        };
        resendOtp();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_otp_verification;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected) {
            findViewById(R.id.btn_verify).setEnabled(true);
        } else {
            findViewById(R.id.btn_verify).setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verify:
                if (TextUtils.isEmpty(otp.getText().toString().trim())){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.otpLayout),"Please Enter OTP",false);
                }else {
                    verifyOtp(otp.getText().toString().trim());
                }
                break;
            case R.id.btn_resendOtp:
                startTimer();
                break;
        }
    }

    private void startTimer() {
        resendAgainLayout.setVisibility(View.INVISIBLE);
        otpReadRemainingTime.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    CountDownTimer countDownTimer = new CountDownTimer(4*60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            otpReadRemainingTime.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        }

        @Override
        public void onFinish() {
            resendAgainLayout.setVisibility(View.VISIBLE);
            otpReadRemainingTime.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.OTP_VERIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void resendOtp() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE), null);

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            startTimer();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.otpLayout), true, e);
                    }
                }));
    }

    private void verifyOtp(String otp) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        String Mobile=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
        Login request = new Login(Mobile, null);
        request.setOtp(otp);
        BaseApp.getInstance().getDisposable().add(apiService.verifyOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().USER, new Gson().toJson(response.getUser()));
                            if (response.getUser().getPassCode() == null) {
                                startActivity(new Intent(OtpVerification.this, CreatePassCodeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.otpLayout), response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.otpLayout), true, e);
                    }
                }));
    }
}
