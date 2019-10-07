package com.safepayu.wallet.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ChangePassword;
import com.safepayu.wallet.models.request.UpdateAddress;
import com.safepayu.wallet.models.response.UpdateAddressResponse;
import com.safepayu.wallet.models.response.UserResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Profile extends BaseActivity implements View.OnClickListener {

    Button BackBtn,UpdateAddressBtn,btnChangePassSubmit;
    TextView ChangePassBtn,tvPhoneNumber, tvEmil,tvDOB, tvAddress,tvPincode,tvUsername1;
    LinearLayout ChangePassLayout;
    int ChangePassVisibility=0;
    UserResponse uResponse;
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        loadingDialog = new LoadingDialog(this);

        //getProfileData
        getProfileData();

        ChangePassBtn=findViewById(R.id.changePassBtn);
        BackBtn=findViewById(R.id.backbtn_from_profile);
        ChangePassLayout=findViewById(R.id.change_pass_layout);
        UpdateAddressBtn=findViewById(R.id.addressupdateBtn);
        tvPhoneNumber = findViewById(R.id.p_mobile);
        tvEmil = findViewById(R.id.p_mail);
        tvDOB = findViewById(R.id.p_dob);
        tvPincode = findViewById(R.id.p_pincode);
        tvAddress = findViewById(R.id.p_address);
        tvUsername1 = findViewById(R.id.username1);
        etOldPassword = findViewById(R.id.old_password_id);
        etNewPassword = findViewById(R.id.new_password_id);
        etConfirmPassword = findViewById(R.id.confirm_password_id);
        btnChangePassSubmit = findViewById(R.id.change_pass_submit);

        //set Listener
        BackBtn.setOnClickListener(this);
        UpdateAddressBtn.setOnClickListener(this);
        ChangePassBtn.setOnClickListener(this);
        btnChangePassSubmit.setOnClickListener(this);

      /*  Geocoder geocoder = new Geocoder(Profile.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(23.322, 43.23232, 1);
            String address = addresses.get(0).getAddressLine(0);

            Log.d("mylog", "Complete Address: " + addresses.toString());
            Log.d("mylog", "Address: " + address);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private void getProfileData() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getUserDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse userResponse) {
//                        BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().USER, new Gson().toJson(userResponse.getUser()));
                        uResponse = userResponse;
                        tvUsername1.setText(userResponse.getUser().getFirstName()+" "+ userResponse.getUser().getLastName());
                        tvPhoneNumber.setText(userResponse.getUser().getMobile());
                        tvEmil.setText(userResponse.getUser().getEmail());
                        tvDOB.setText(userResponse.getUser().getDob());
                        tvAddress.setText(userResponse.getUser().getLocation()+" "+ userResponse.getUser().getCity()+" "+userResponse.getUser().getState()+" "+userResponse.getUser().getCountry());
                        tvPincode.setText(userResponse.getUser().getPin());



                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(Profile.class), "onError: " + e.getMessage());


                    }
                }));

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.profile;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.backbtn_from_profile:
                finish();
                break;
            case R.id.addressupdateBtn:

                Intent i = new Intent(Profile.this, AddUpdateAddress.class);
                i.putExtra("location",  uResponse.getUser().getLocation());
                i.putExtra("city",  uResponse.getUser().getCity());
                i.putExtra("state",  uResponse.getUser().getState());
                i.putExtra("country",  uResponse.getUser().getCountry());
                i.putExtra("pincode",  uResponse.getUser().getPin());
                startActivity(i);
                finish();
                break;
            case R.id.changePassBtn:
                if (ChangePassVisibility==0){
                    ChangePassLayout.setVisibility(View.VISIBLE);
                    ChangePassVisibility=1;

                }else {
                    ChangePassLayout.setVisibility(View.GONE);
                    ChangePassVisibility=0;
                }
                break;
            case R.id.change_pass_submit:

                BaseApp.getInstance().commonUtils().hideKeyboard(this);
                if (validate()) {
                    changePassword();
                }

                break;
        }
    }

    private boolean validate() {
        if (etOldPassword.getText().toString().trim().length() == 0) {
            etOldPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etOldPassword, "Please enter old password", true);
            return false;
        } else if (etNewPassword.getText().toString().trim().length() == 0) {
            etNewPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etNewPassword, "Please enter new password", true);
            return false;
        }
        else if (etConfirmPassword.getText().toString().trim().length() == 0) {
            etConfirmPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etConfirmPassword, "Please enter confirm password", true);
            return false;
        } else if (!etConfirmPassword.getText().toString().trim().equals(etNewPassword.getText().toString().trim())) {
            etConfirmPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etConfirmPassword,"Password do not match",true);
            return false;
        }
        return true;
    }
    private void changePassword() {
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        ChangePassword changePassword = new ChangePassword(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID),etOldPassword.getText().toString(),etNewPassword.getText().toString(),etConfirmPassword.getText().toString());
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

                           //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addUpdateAddressLayout), response.getSuccess(), false);

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addUpdateAddressLayout),response.getMessage(),false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(AddUpdateAddress.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                    }
                }));
    }

}

