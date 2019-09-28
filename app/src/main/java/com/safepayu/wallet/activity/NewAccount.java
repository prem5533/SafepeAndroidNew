package com.safepayu.wallet.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.DatePicker;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.enums.ButtonActions;
import com.safepayu.wallet.listener.MobileEditTextWatcher;
import com.safepayu.wallet.listener.SnackBarActionClickListener;
import com.safepayu.wallet.models.request.Register;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewAccount extends BaseActivity implements View.OnClickListener, SnackBarActionClickListener {

    private EditText firstName, lastName, email, mobileNo, password, dob, referralCode;
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
        firstName = findViewById(R.id.et_firstName);
        lastName = findViewById(R.id.et_lastName);
        email = findViewById(R.id.et_email);
        mobileNo = findViewById(R.id.et_mobileNo);
        dob = findViewById(R.id.et_dob);
        referralCode = findViewById(R.id.et_referralCode);
        password = findViewById(R.id.et_password);
        mobileNo.addTextChangedListener(new MobileEditTextWatcher(mobileNo));
        mobileNo.setText("+91 ");
        mobileNo.setSelection(mobileNo.getText().length());


        findViewById(R.id.btn_process).setOnClickListener(this);
        dob.setOnClickListener(this);

        checkPermission();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_new_account;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected) {
            findViewById(R.id.btn_process).setEnabled(true);
        } else {
            findViewById(R.id.btn_process).setEnabled(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_process:
                BaseApp.getInstance().commonUtils().hideKeyboard(this);
                if (validate()) {
                    register();
                }
                break;
            case R.id.et_dob:
                DatePicker datePicker = DatePicker.newInstance(dob, null);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;
        }
    }

    private Boolean validate() {
        if (firstName.getText().toString().trim().length() == 0) {
            firstName.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter first name", true);
            return false;
        } else if (lastName.getText().toString().trim().length() == 0) {
            lastName.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter last name", true);
            return false;
        } else if (email.getText().toString().trim().length() == 0) {
            email.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter email", true);
            return false;
        } else if (!BaseApp.getInstance().commonUtils().isValidEmail(email.getText().toString())) {
            email.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter valid email", true);
            return false;
        } else if (mobileNo.getText().toString().trim().length() == 0) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter valid phone number", true);
            return false;
        } else if (PhoneNumberUtils.isGlobalPhoneNumber(mobileNo.getText().toString().trim())) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter valid phone number", true);
            return false;
        } else if (mobileNo.getText().toString().trim().length() < 10 || mobileNo.getText().toString().trim().length() > 14 || mobileNo.getText().toString().trim().matches(BaseApp.getInstance().commonUtils().phoneNumberRegex) == false) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter valid phone number", true);
            return false;
        } else if (dob.getText().toString().trim().length() == 0) {
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter DOB", true);
            return false;
        } else if (password.getText().toString().trim().length() == 0) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter password", true);
            return false;
        } else if (referralCode.getText().toString().trim().length() == 0) {
            referralCode.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(firstName, "Please enter referral code", true);
            return false;
        }
        return true;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void register() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Register register = new Register(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), mobileNo.getText().toString().split(" ")[1], BaseApp.getInstance().dateUtil().parseStringDate(dob.getText().toString(), BaseApp.getInstance().dateUtil().dd_MM_yyyy, BaseApp.getInstance().dateUtil().yyyy_MM_dd), referralCode.getText().toString(), "Adroid", BaseApp.getInstance().commonUtils().getTelephonyManager().getDeviceId());

        BaseApp.getInstance().getDisposable().add(apiService.register(register)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().USER, new Gson().toJson(response.getUser()));
                            BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().REFERRAL_USER, new Gson().toJson(response.getReferralUser()));
                            startActivity(new Intent(NewAccount.this, OtpVerification.class));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(NewAccount.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);

                    }
                }));
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
            default:
                ActivityCompat.requestPermissions(NewAccount.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, BaseApp.getInstance().commonUtils().REQUEST_RED_PONE_STATE);
                break;
        }

    }
}
