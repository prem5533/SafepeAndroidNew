package com.safepayu.wallet.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
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
import androidx.multidex.MultiDex;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ForgetPasswordRequest;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.ForgetPasswordResponse;
import com.safepayu.wallet.models.response.UserResponse;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgetPassword extends AppCompatActivity {

    EditText edit_number, enter_otp, enter_password, confrimPasswordED;
    Button btn_request_otp, btn_continue, btn_conform_password, resend_btn;
    TextView timer, label, back_forgot_password;
    String str_edit_otp, str_edit_conf_pass, str_edit_number, password = "918429";
    private int randomPIN, Otpval;
    private Integer otpToSend;
    boolean resend_top = false;
    String session_id, userid;
    LinearLayout layout1, layout2, layout3;
    private LoadingDialog loadingDialog;
    ApiService apiService;
    private ImageView ShowHidePasswordBtn, forgotImage;
    boolean showPass = false;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        String imagePath = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LOGO_IMAGE);
        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        edit_number = (EditText) findViewById(R.id.number_forgot);
        label = findViewById(R.id.label);
        timer = findViewById(R.id.timer);
        enter_otp = (EditText) findViewById(R.id.enter_otp);
        enter_password = (EditText) findViewById(R.id.enter_password);
        confrimPasswordED = findViewById(R.id.confirm_password);
        ShowHidePasswordBtn = findViewById(R.id.show_hide_password_forgetPass);
        forgotImage = findViewById(R.id.forgot_image);

        //  Picasso.get().load(imagePath).into(forgotImage);
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
                //continueOtp();

                conformPassword();
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
                if (showPass) {
                    showPass = false;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.show_password48));
                    enter_password.setTransformationMethod(new PasswordTransformationMethod());
                    try {
                        enter_password.setSelection(enter_password.getText().toString().length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    showPass = true;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.hide_password48));
                    enter_password.setTransformationMethod(null);
                    try {
                        enter_password.setSelection(enter_password.getText().toString().length());
                    } catch (Exception e) {
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
        } else {
            verifyOtp(enter_otp.getText().toString().trim());
        }
    }

    void conformPassword() {
        str_edit_conf_pass = enter_password.getText().toString().trim();
        String confrimPass = confrimPasswordED.getText().toString().trim();

        if (TextUtils.isEmpty(str_edit_conf_pass)) {

            enter_password.setError("Please Enter Password");
            enter_password.requestFocus();
            confrimPasswordED.setSelection(enter_password.getText().toString().length());
            return;
        } else {
            if (TextUtils.isEmpty(confrimPass)) {
                confrimPasswordED.setError("Please Enter Password To Confirm");
                confrimPasswordED.requestFocus();
                confrimPasswordED.setSelection(confrimPasswordED.getText().toString().length());
            } else {

                ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
                forgetPasswordRequest.setMobile(str_edit_number);
                forgetPasswordRequest.setOtp(Integer.parseInt(enter_otp.getText().toString().trim()));
                forgetPasswordRequest.setPassword(str_edit_conf_pass);
                forgetPasswordRequest.setPassword_confirmation(confrimPass);

                resetPassword(forgetPasswordRequest);
            }
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
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }

    CountDownTimer countDownTimer = new CountDownTimer(4 * 60000, 1000) {
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
        Login request = new Login(edit_number.getText().toString().trim(), null);
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
                            label.setHint("Enter New Passcode");
                            layout1.setVisibility(View.GONE);
                            layout2.setVisibility(View.GONE);
                            layout3.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }

    private void resetPassword(ForgetPasswordRequest forgetPasswordRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        BaseApp.getInstance().getDisposable().add(apiService.getForgetPassword(forgetPasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ForgetPasswordResponse>() {
                    @Override
                    public void onSuccess(ForgetPasswordResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            Toast.makeText(ForgetPassword.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            String message="";
                            try{
                                ForgetPasswordResponse.DataBean dataBean=response.getData();
                                if (dataBean!=null){

                                    if (dataBean.getPassword().size()==1){
                                        message=dataBean.getPassword().get(0)+"\n";
                                    }else if (dataBean.getPassword().size()>1){
                                        message=dataBean.getPassword().get(0)+"\n"+dataBean.getPassword().get(1)+"\n";
                                    }

                                    if (dataBean.getPassword_confirmation().size()==1){
                                        message=message+dataBean.getPassword_confirmation().get(0);
                                    }else if (dataBean.getPassword_confirmation().size()>1){
                                        message=message+dataBean.getPassword_confirmation().get(0)+"\n"+dataBean.getPassword_confirmation().get(1)+"\n";
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(message)) {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.forgetPasscodeId), response.getMessage(), true);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.forgetPasscodeId),  message, true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                      //  Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }
}
