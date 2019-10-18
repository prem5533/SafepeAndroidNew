package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.ResetPasscodeModel;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.UserResponse;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasscode extends AppCompatActivity {

    EditText edit_number, enter_otp, enter_password;
    Button btn_request_otp, btn_continue, btn_conform_password, resend_btn,BackBtn;
    TextView timer, label, back_forgot_password;
    String str_edit_otp, str_edit_conf_pass, str_edit_number, password = "918429";
    private int randomPIN, Otpval;
    private Integer otpToSend;
    boolean resend_top = false;
    String session_id, userid;
    LinearLayout layout1, layout2, layout3;
    private LoadingDialog loadingDialog;
    private ImageView ShowHidePasswordBtn;
    boolean showPass=false;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_passcode);

        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        edit_number = (EditText) findViewById(R.id.number_forgot);
        BackBtn=findViewById(R.id.FP_back_btn);
        label = findViewById(R.id.label);
        timer = findViewById(R.id.timer);
        enter_otp = (EditText) findViewById(R.id.enter_otp);
        enter_password = (EditText) findViewById(R.id.enter_password);
        ShowHidePasswordBtn= findViewById(R.id.show_hide_password_forgetPass);

        otpToSend = 0;
        Random r = new Random();
        Otpval = r.nextInt(9999 - 1000) + 1000;


        btn_request_otp = (Button) findViewById(R.id.request_otp);
        resend_btn = (Button) findViewById(R.id.resend_otp);
        btn_continue = (Button) findViewById(R.id.continue_otp);
        btn_conform_password = (Button) findViewById(R.id.conformPassword);
        back_forgot_password = (TextView) findViewById(R.id.back_forgot_password);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);

        resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend_top = true;
                resendOtp();

            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_request_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_number.getText().toString().length() != 10) {
                    Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                } else {
                    str_edit_number = edit_number.getText().toString();
                    resendOtp();
                }
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueOtp();
            }
        });
        btn_conform_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conformPassword();
            }
        });
        back_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ShowHidePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPass){
                    showPass=false;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.show_password48));
                    enter_password.setTransformationMethod(new PasswordTransformationMethod());
                    try {
                        enter_password.setSelection(enter_password.getText().toString().length());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {
                    showPass=true;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.hide_password48));
                    enter_password.setTransformationMethod(null);
                    try {
                        enter_password.setSelection(enter_password.getText().toString().length());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    void continueOtp() {
        str_edit_otp = enter_otp.getText().toString().trim();

        if (TextUtils.isEmpty(str_edit_otp)) {
            enter_otp.setError("Please Enter OPT");
            enter_otp.requestFocus();
            return;
        }else {
            verifyOtp( enter_otp.getText().toString().trim());
        }
    }

    void conformPassword() {
        str_edit_conf_pass = enter_password.getText().toString().trim();

        if (TextUtils.isEmpty(str_edit_conf_pass) || enter_password.getText().toString().trim().length() < 4) {

            enter_password.setError("Please Enter Valid Passcode");
            enter_password.requestFocus();
            return;
        } else {
            resetPasscode(str_edit_conf_pass);
        }

    }

    private void resendOtp() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(edit_number.getText().toString().trim(), null);

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            timer.setVisibility(View.VISIBLE);
                            layout2.setVisibility(View.VISIBLE);
                            btn_request_otp.setVisibility(View.GONE);
                            resend_btn.setVisibility(View.GONE);

                            countDownTimer.start();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.forgetPasscodeId),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }

    CountDownTimer countDownTimer = new CountDownTimer(4*60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timer.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        }

        @Override
        public void onFinish() {
            resend_btn.setVisibility(View.VISIBLE);
            timer.setVisibility(View.INVISIBLE);
        }
    };

    private void verifyOtp(String otp) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(edit_number.getText().toString().trim(), null);
        request.setOtp(otp);
        BaseApp.getInstance().getDisposable().add(apiService.verifyOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            label.setHint("Enter New Passcode");
                            layout1.setVisibility(View.GONE);
                            layout2.setVisibility(View.GONE);
                            layout3.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }

    private void resetPasscode(final String Passcode) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ResetPasscodeModel resetPasscodeModel=new ResetPasscodeModel();
        resetPasscodeModel.setPasscode(Passcode);

        BaseApp.getInstance().getDisposable().add(apiService.resetPasscode(resetPasscodeModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PASSCODE,Passcode);
                            Toast.makeText(ForgotPasscode.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.forgetPasscodeId),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }
}
