package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.custom_view.passcodeview.PassCodeListener;
import com.safepayu.wallet.custom_view.passcodeview.PasscodeTextView;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.enums.ButtonActions;
import com.safepayu.wallet.fragment.NumberBoard;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.response.UserDetailResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CreatePassCodeActivity extends BaseActivity implements NumberBoard.OnKeyBoard {
    private PasscodeTextView passCodeTextView;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);

        passCodeTextView = findViewById(R.id.otp_view);
        passCodeTextView.requestFocusOTP();
        passCodeTextView.setOtpListener(new PassCodeListener() {
            @Override
            public void onInteractionListener() {
                passCodeTextView.showError();
            }

            @Override
            public void onOTPComplete(String otp) {

//                Toast.makeText(CreatePassCodeActivity.this, "The OTP is " + otp, Toast.LENGTH_SHORT).show();
            }
        });
        NumberBoard.getInstance().setButtonAction(ButtonActions.CREATE);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_pass_code;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private String clearText() {
        String str = passCodeTextView.getPassCode();

        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Override
    public void onOnDeletePressed(View v) {
        passCodeTextView.setPassCode(clearText());
    }

    private String appendText(String key) {

        return passCodeTextView.getPassCode().concat(key);
    }

    @Override
    public void onKeyPressed(int value) {
        passCodeTextView.setPassCode(appendText(String.valueOf(value)));
    }

    @Override
    public void onOkPressed(View v, ButtonActions buttonActions) {
        switch (buttonActions) {
            case CREATE:
                if (passCodeTextView.getPassCode().length() == 4)
                    savePassCode();
                else
                    BaseApp.getInstance().toastHelper().showSnackBar(passCodeTextView, "Enter Otem First", false);
                break;
        }
    }

    private void savePassCode() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login login = new Login();
        login.setPassCode(passCodeTextView.getPassCode());
        login.setUserId(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.savePassCode(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserDetailResponse>() {
                    @Override
                    public void onSuccess(UserDetailResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PASSCODE,passCodeTextView.getPassCode());
                            startActivity(new Intent(CreatePassCodeActivity.this, AddUpdateAddress.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.createPasscode),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.createPasscode), true, e);
                    }
                }));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreatePassCodeActivity.this, LoginActivity.class));

        super.onBackPressed();
    }
}
