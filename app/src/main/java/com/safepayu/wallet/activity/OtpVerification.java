package com.safepayu.wallet.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.safepayu.wallet.models.request.SendOtpRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class OtpVerification extends BaseActivity implements View.OnClickListener, TextWatcher {
    private TextView otpReadRemainingTime, mobileNo;
    private LinearLayout resendAgainLayout;
    private EditText otp;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private LoadingDialog loadingDialog;
    ApiService apiService;
    EditText et1 ,et2,et3,et4,et5,et6;
    String Otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, true);

       /* mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        otp = findViewById(R.id.et_otp);
        mobileNo = findViewById(R.id.tv_mobileNo1);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);

        et1.addTextChangedListener(this);
        et2.addTextChangedListener(this);
        et3.addTextChangedListener(this);
        et4.addTextChangedListener(this);
        et5.addTextChangedListener(this);
        et6.addTextChangedListener(this);

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
        //return R.layout.activity_otp_verification;
        return R.layout.otp_demo;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verify:
                if (TextUtils.isEmpty(otp.getText().toString().trim())){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.otpLayout),"Please Enter OTP",false);
                }else {
//                    Otp = (et1.getText().toString().trim()+(et2.getText().toString().trim()+et3.getText().toString().trim()+et4.getText().toString().trim()+
//                            et5.getText().toString().trim()+(et6.getText().toString().trim())));

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

        SendOtpRequest sendOtpRequest=new SendOtpRequest();
        sendOtpRequest.setMobile(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        sendOtpRequest.setType("1");

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(sendOtpRequest)
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
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.otpLayout), true, e);
                    }
                }));
    }

    private void verifyOtp(String otp) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        String Mobile=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
        Login request = new Login(Mobile, null);
        request.setDeviceid(BaseApp.getInstance().commonUtils().getTelephonyManager().getDeviceId());
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

                                startActivity(new Intent(OtpVerification.this, CreatePassCodeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.otpLayout), response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.otpLayout), true, e);
                    }
                }));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if(text.length()==1){
            if (et1.length()==1){
                et2.requestFocus();
            }
            if(et2.length()==1){
                et3.requestFocus(); }

            if(et3.length()==1){
                et4.requestFocus(); }

            if(et4.length()==1){
                et5.requestFocus(); }

            if(et5.length()==1){
                et6.requestFocus(); }
        } else if (text.length()==0){
            if(et6.length()==0){
                et5.requestFocus(); }
            if(et5.length()==0){
                et4.requestFocus(); }
            if(et4.length()==0){
                et3.requestFocus(); }
            if(et3.length()==0){
                et2.requestFocus(); }
            if(et2.length()==0){
                et1.requestFocus(); }
        }
    }
}
