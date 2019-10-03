package com.safepayu.wallet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.Register;
import com.safepayu.wallet.models.request.UpdateAddress;
import com.safepayu.wallet.models.response.UpdateAddressResponse;
import com.safepayu.wallet.models.response.UserResponse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddUpdateAddress extends BaseActivity implements View.OnClickListener {

    private EditText etLocation, etCity, etState, etPincode, etCountry;
    private String Location, City, State, Country, Pincode;
    private Button add_address, update_address;
    private LoadingDialog loadingDialog;
    private CheckBox current_location;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(false, null, false);
        loadingDialog = new LoadingDialog(this);

        //  findViewById(R.id.add_address).setOnClickListener(this);
        findViewById(R.id.back_btn_address).setOnClickListener(this);
        etLocation = findViewById(R.id.update_location);
        etCity = findViewById(R.id.update_city);
        etState = findViewById(R.id.update_state);
        etPincode = findViewById(R.id.update_pin);
        etCountry = findViewById(R.id.update_country);
        add_address = findViewById(R.id.add_address);
        update_address = findViewById(R.id.edit_address);
        current_location = findViewById(R.id.current_location);
        add_address.setOnClickListener(this);
        update_address.setOnClickListener(this);


        //*******************get data *****************
        Location = getIntent().getStringExtra("location");
        City = getIntent().getStringExtra("city");
        State = getIntent().getStringExtra("state");
        Country = getIntent().getStringExtra("country");
        Pincode = getIntent().getStringExtra("pincode");

        //***************set text*********************
        etLocation.setText(Location);
        etCity.setText(City);
        etState.setText(State);
        etCountry.setText(Country);
        etPincode.setText(Pincode);

        if (Location != null && City != null && State != null && Country != null && etPincode != null) {
            update_address.setVisibility(View.VISIBLE);
            add_address.setVisibility(View.GONE);
        }

        current_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                Intent intent = new Intent( AddUpdateAddress.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_update_address2;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address:

                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

                break;
            case R.id.back_btn_address:
                finish();
                break;

            case R.id.edit_address:
                BaseApp.getInstance().commonUtils().hideKeyboard(this);
                if (validate()) {
                    updateAddress();
                }

                break;
        }
    }

    private boolean validate() {
        if (etLocation.getText().toString().trim().length() == 0) {
            etLocation.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etLocation, "Please enter Flat/ House location", true);
            return false;
        } else if (etCity.getText().toString().trim().length() == 0) {
            etCity.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCity, "Please enter city", true);
            return false;
        }
        else if (etState.getText().toString().trim().length() == 0) {
            etState.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etState, "Please enter state", true);
            return false;
        } else if (etPincode.getText().toString().trim().length() == 0) {
            etPincode.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etPincode, "Please enter pincode", true);
            return false;
        }
        else if (etCountry.getText().toString().trim().length() == 0) {
            etCountry.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCountry, "Please enter country", true);
            return false;
        }
        return true;
    }

    private void updateAddress() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        UpdateAddress update = new UpdateAddress(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID), etLocation.getText().toString(), etCity.getText().toString(), etState.getText().toString(),etCountry.getText().toString(), etPincode.getText().toString() );

        BaseApp.getInstance().getDisposable().add(apiService.updateAddress(update)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpdateAddressResponse>() {
                    @Override
                    public void onSuccess(UpdateAddressResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()){
                            finish();

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