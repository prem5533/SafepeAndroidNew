package com.safepayu.wallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.SaveAddressRequest;
import com.safepayu.wallet.models.request.UpdateAddress;
import com.safepayu.wallet.models.response.SaveAddressResponse;
import com.safepayu.wallet.models.response.UpdateAddressResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddUpdateAddress extends BaseActivity implements View.OnClickListener {

    private EditText etLocation, etCity, etState, etPincode, etCountry;
    public int STATIC_INTEGER_VALUE = 1;
    private Button add_address, update_address;
    private LoadingDialog loadingDialog;
    private CheckBox current_location;
    boolean check = false;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private String Location, City, State, Country, Pincode, mapSelectLocality, mapSelectCity, mapSelectState, mapSelectPincode, mapSelectCountry;

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
        current_location.setChecked(false);

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
        etPincode.setText(""+Pincode);


        if (Location != null && City != null && State != null && Country != null && Pincode != null) {

            update_address.setVisibility(View.VISIBLE);
            add_address.setVisibility(View.GONE);
        }else {
            check=false;
        }

        current_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    Intent intent = new Intent(AddUpdateAddress.this, MapsActivity.class);
                    startActivityForResult(intent, STATIC_INTEGER_VALUE);

                //  Toast.makeText(getApplicationContext(),"Map Coming Soon",Toast.LENGTH_SHORT).show();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    Activity#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for Activity#requestPermissions for more details.
//                            return;
//                        }
//                    }
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, AddUpdateAddress.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {
                    //  String newText = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
                    mapSelectLocality = data.getStringExtra("select_locality");
                    mapSelectCity = data.getStringExtra("select_city");
                    mapSelectState = data.getStringExtra("select_state");
                    mapSelectPincode = data.getStringExtra("select_pincode");
                    mapSelectCountry = data.getStringExtra("select_country");

                    etLocation.setText(mapSelectLocality);
                    etCity.setText(mapSelectCity);
                    etState.setText(mapSelectState);
                    etCountry.setText(mapSelectCountry);
                    etPincode.setText(mapSelectPincode);
                    // TODO Update your TextView.
                }
                break;

        }
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

                if (validate()) {
                    addNewAddress();
                }

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
                            Toast.makeText(AddUpdateAddress.this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
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

    private void addNewAddress() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        SaveAddressRequest saveAddressRequest = new SaveAddressRequest( );
        saveAddressRequest.setUser_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));
        saveAddressRequest.setLocation(etLocation.getText().toString().trim());
        saveAddressRequest.setState(etState.getText().toString().trim());
        saveAddressRequest.setCity(etCity.getText().toString().trim());
        saveAddressRequest.setCountry(etCountry.getText().toString().trim());
        saveAddressRequest.setPin(etPincode.getText().toString().trim());


        BaseApp.getInstance().getDisposable().add(apiService.addAddress(saveAddressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SaveAddressResponse>() {
                    @Override
                    public void onSuccess(SaveAddressResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()){

                            String Msg="Address Updated Successfully.\n Verification Link Has Been Sent To Your Email. Please Verify ";
                            Toast.makeText(AddUpdateAddress.this, Msg, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddUpdateAddress.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
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