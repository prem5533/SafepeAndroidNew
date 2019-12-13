package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ChangePasswordRequest;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ChangePassword extends BaseActivity {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private LoadingDialog loadingDialog;
    Button BackBtn,btnChangePassSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        loadingDialog = new LoadingDialog(this);

        etOldPassword = findViewById(R.id.old_password_id_changePassword);
        etNewPassword = findViewById(R.id.new_password_id_changePassword);
        etConfirmPassword = findViewById(R.id.confirm_password_id_changePassword);
        BackBtn=findViewById(R.id.backBtn_changePassword);
        btnChangePassSubmit=findViewById(R.id.change_pass_submit_changePassword);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnChangePassSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    changePassword();
                }
            }
        });
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.change_password;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (!isConnected){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.changePasswordLayout),"Check Your Internet Connection!",true);
        }

    }

    private boolean validate() {
        if (etOldPassword.getText().toString().trim().length() == 0) {
            etOldPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.changePasswordLayout), "Please enter old password", true);
            return false;
        } else if (etNewPassword.getText().toString().trim().length() == 0) {
            etNewPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.changePasswordLayout), "Please enter new password", true);
            return false;
        } else if (etConfirmPassword.getText().toString().trim().length() == 0) {
            etConfirmPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.changePasswordLayout), "Please enter confirm password", true);
            return false;
        } else if (!etConfirmPassword.getText().toString().trim().equals(etNewPassword.getText().toString().trim())) {
            etConfirmPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.changePasswordLayout),"Password do not match",true);
            return false;
        }
        return true;
    }
    private void changePassword() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        ChangePasswordRequest changePassword = new ChangePasswordRequest(etOldPassword.getText().toString(),etNewPassword.getText().toString(),etConfirmPassword.getText().toString());
        BaseApp.getInstance().getDisposable().add(apiService.changePwd(changePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()){
                            Toast.makeText(getApplicationContext(),response.getSuccess(),Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.changePasswordLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.changePasswordLayout),e.getMessage(),false);

                    }
                }));
    }
}
