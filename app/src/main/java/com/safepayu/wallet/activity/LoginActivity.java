package com.safepayu.wallet.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.enums.ButtonActions;
import com.safepayu.wallet.halper.Config;
import com.safepayu.wallet.listener.MobileEditTextWatcher;
import com.safepayu.wallet.listener.SnackBarActionClickListener;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.response.AppVersionResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.LoginResponse;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements View.OnClickListener, SnackBarActionClickListener {
    private static String TAG = LoginActivity.class.getName();
    private EditText mobileNo, password;
    private ApiService apiService;
    private LoadingDialog loadingDialog;
    String versionName="",appUrl="https://play.google.com/store/apps/details?id=com.safepayu.wallet&hl=en";
    int versionCode=0;
    private ImageView im_cross;

    //Otp Dialog
    TextView TimerTV;
    EditText OtpED;
    Button continueButton, resendButton;

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
        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        mobileNo = findViewById(R.id.et_mobileNo);
        password = findViewById(R.id.et_password);
        mobileNo.addTextChangedListener(new MobileEditTextWatcher(mobileNo));
        mobileNo.setText("+91 ");
        password.setText("");
        mobileNo.setSelection(mobileNo.getText().length());
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_forgetPass).setOnClickListener(this);
        findViewById(R.id.btn_newAccount).setOnClickListener(this);

//        checkPermission();
        getAppVersion();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected) {
            findViewById(R.id.btn_login).setEnabled(true);
        } else {
            findViewById(R.id.btn_login).setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                BaseApp.getInstance().commonUtils().hideKeyboard(this);
                if (checkPermission() && validate()) {
                    resendOtp();
                }
                break;
            case R.id.btn_forgetPass:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
            case R.id.btn_newAccount:
                startActivity(new Intent(this, NewAccount.class));
                break;
        }
    }

    public Boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return false;
        }
        return true;
    }

    private void requestPermission() {
        // BEGIN_INCLUDE(read_phone_state_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.permission_read_phone_state), false, getResources().getString(R.string.ok), ButtonActions.SHOW_SETTING, this);
        } else {

            // Read phone state permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    BaseApp.getInstance().commonUtils().REQUEST_RED_PONE_STATE);
        }
        // END_INCLUDE(read_phone_state_permission_request)
    }

    @Override
    public void onPositiveClick(View view, ButtonActions action) {
        switch (action) {
            case SHOW_SETTING:
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                break;
            case VERIFY_MOBILE:
                startActivity(new Intent(LoginActivity.this, OtpVerification.class).putExtra(Config.MOBILE_NO, mobileNo.getText().toString().split(" ")[1]));
                break;
            default:
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, BaseApp.getInstance().commonUtils().REQUEST_RED_PONE_STATE);
                break;
        }

    }

    private Boolean validate() {
        if (mobileNo.getText().toString().trim().length() == 0) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter valid phone number", true);
            return false;
        } else if (PhoneNumberUtils.isGlobalPhoneNumber(mobileNo.getText().toString().trim())) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter valid phone number", true);
            return false;
        } else if (mobileNo.getText().toString().trim().length() < 10 || mobileNo.getText().toString().trim().length() > 14 || mobileNo.getText().toString().trim().matches(BaseApp.getInstance().commonUtils().phoneNumberRegex) == false) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter valid phone number", true);
            return false;
        } else if (password.getText().toString().trim().length() == 0) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter password", true);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == BaseApp.getInstance().commonUtils().REQUEST_RED_PONE_STATE) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result.
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, preview can be displayed
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.permission_granted), true);
            } else {
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.permission_not_granted), false, getResources().getString(R.string.setting), ButtonActions.SHOW_SETTING, this);
            }
            // END_INCLUDE(permission_result)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getAppVersion() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AppVersionResponse>() {
                    @Override
                    public void onSuccess(AppVersionResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            int val= Integer.parseInt(response.getVersionData().getVal());

                            if (versionCode==val){

                            }else {
                                showDialogForAppUpdate(LoginActivity.this);
                            }

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.walletHistoryLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.walletHistoryLayout), false, e.getCause());
                    }
                }));
    }

    private void loginUser() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login login = new Login(mobileNo.getText().toString().split(" ")[1], password.getText().toString());
        BaseApp.getInstance().getDisposable().add(apiService.login(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                             switch (response.getStatusCode()) {
                                case 0:
                                    SaveLoginDetails(response);
                                    startActivity(new Intent(LoginActivity.this,Navigation.class));
                                    finish();
                                    break;
                                case 1:
                                    BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, response.getMessage(), false);
                                    break;
                                case 2:
                                    BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, response.getMessage(), false, getResources().getString(R.string.verify), ButtonActions.VERIFY_MOBILE, LoginActivity.this);
                                    break;
                                case 3:
                                    SaveLoginDetails(response);
                                    Toast.makeText(LoginActivity.this, "Please First Set Passcode", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, CreatePassCodeActivity.class));
                                    break;
                                case 4:
                                    SaveLoginDetails(response);
                                    Toast.makeText(LoginActivity.this, "Please First Set Address", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, AddUpdateAddress.class));
                                    break;
                                case 5:
                                    sendVerifyLink();
                                    break;
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

    private void SaveLoginDetails(LoginResponse response){
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().MOBILE, mobileNo.getText().toString().split(" ")[1]);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, response.getAccessToken());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN_EXPIRE_IN, response.getTokenExpiresIn());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_ID, response.getUserId());
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.otp_dialog);

        TimerTV = dialog.findViewById(R.id.timerLogin);
        OtpED = dialog.findViewById(R.id.enter_otpLogin);
        im_cross = dialog.findViewById(R.id.im_cross);

        im_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        continueButton = (Button) dialog.findViewById(R.id.continue_otpLogin);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(OtpED.getText().toString().trim())) {
                    OtpED.setError("Please Enter Otp");
                    //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout),"Please Enter Otp",false);
                } else {
                    verifyOtp(OtpED.getText().toString().trim());
                    dialog.dismiss();
                }


            }
        });

        resendButton = (Button) dialog.findViewById(R.id.resend_otpLogin);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    public void showDialogForAppUpdate(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.app_update_dialog);

        Button proceedButton = (Button) dialog.findViewById(R.id.proceedBtn_appUpdate);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
                finish();
            }
        });

        Button cancelBtn_appUpdate = (Button) dialog.findViewById(R.id.cancelBtn_appUpdate);
        cancelBtn_appUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.waringforAppUpdate), true);
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    private void resendOtp() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(mobileNo.getText().toString().split(" ")[1], null);

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            showDialog(LoginActivity.this);
                            TimerTV.setVisibility(View.VISIBLE);

                            new CountDownTimer(59000, 1000) {

                                public void onTick(long millisUntilFinished) {

                                    TimerTV.setText("00:" + millisUntilFinished / 1000);
                                    //here you can have your logic to set text to edittext
                                }

                                public void onFinish() {
                                    TimerTV.setVisibility(View.GONE);
                                    resendButton.setVisibility(View.VISIBLE);
                                }

                            }.start();
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

    private void verifyOtp(String otp) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(mobileNo.getText().toString().split(" ")[1], null);
        request.setOtp(otp);
        BaseApp.getInstance().getDisposable().add(apiService.verifyOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            loginUser();
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

    private void sendVerifyLink() {

        String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        BaseApp.getInstance().getDisposable().add(apiService.verifyEmail(UserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage() + "\n" + "Please Verify From Your Email Account", false);
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

}
