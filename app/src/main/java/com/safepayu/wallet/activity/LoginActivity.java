package com.safepayu.wallet.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import com.safepayu.wallet.models.response.LoginResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements View.OnClickListener, SnackBarActionClickListener {
    private static String TAG = LoginActivity.class.getName();
    private EditText mobileNo, password;
    private ApiService apiService;
    private LoadingDialog loadingDialog;

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


        mobileNo = findViewById(R.id.et_mobileNo);
        password = findViewById(R.id.et_password);
        mobileNo.addTextChangedListener(new MobileEditTextWatcher(mobileNo));
        mobileNo.setText("+91 8750110867");
        password.setText("qwerty123");
        mobileNo.setSelection(mobileNo.getText().length());
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_forgetPass).setOnClickListener(this);
        findViewById(R.id.btn_newAccount).setOnClickListener(this);

//        checkPermission();
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
                    loginUser();
                }
                break;
            case R.id.btn_forgetPass:

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
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, response.getAccessToken());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN_EXPIRE_IN, response.getTokenExpiresIn());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_ID, response.getUserId());
                            switch (response.getStatusCode()) {
                                case 1:
                                    BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, response.getMessage(), false);
                                    break;
                                case 2:
                                    BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, response.getMessage(), false, getResources().getString(R.string.verify), ButtonActions.VERIFY_MOBILE, LoginActivity.this);
                                    break;
                                case 3:
//                                    BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, response.getMessage(), false, getResources().getString(R.string.verify), ButtonActions.VERIFY_MOBILE, LoginActivity.this);
                                    startActivity(new Intent(LoginActivity.this, CreatePassCodeActivity.class));
                                    break;
                                case 4:
                                    startActivity(new Intent(LoginActivity.this,Navigation.class));
                                    finish();
                                    break;
                            }
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
