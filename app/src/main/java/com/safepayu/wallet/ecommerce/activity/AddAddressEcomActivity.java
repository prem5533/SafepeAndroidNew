package com.safepayu.wallet.ecommerce.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.ChangeAddressAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.SaveEcomAddressRequest;
import com.safepayu.wallet.ecommerce.model.response.AddressUserResponse;
import com.safepayu.wallet.ecommerce.model.response.RemoveEcomAddressResponse;
import com.safepayu.wallet.ecommerce.model.response.UpdateEcomAddressResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddAddressEcomActivity extends AppCompatActivity implements View.OnClickListener, ChangeAddressAdapter.onEditAddress {

    private TextView tvAddNewAddress, tvChangeAddress, tvContinue, tvSaveAddress, tvConfirmAddress,saveBtnAddress,tvUsername,tvType,tvAddress,tvCity,
            tvPincode,tvState,tvMobile;
    private EditText etUserName,etMobile,etLocation,etCity,etState,etPincode,etLandmark,etCountry;
    public Dialog dialog;
    private Button backBtnDialog, backBtnChangeAddress, backBtnAddress;
    private RecyclerView addressList;
    private ChangeAddressAdapter changeAddressAdapter;
    private LoadingDialog loadingDialog;
    private RadioGroup radioType;
    SaveEcomAddressRequest saveEcomAddressRequest;
    private String selectedRadioButtonText ,SaveEdit;
    AddressUserResponse addressUserResponse;
    private RecyclerView recylceItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_ecom);
        findId();

        getCarts();
    }



    private void findId() {
        loadingDialog=new LoadingDialog(AddAddressEcomActivity.this);
        tvAddNewAddress = findViewById(R.id.tvaddnew_addaddress);
        tvChangeAddress = findViewById(R.id.tvchange_addaddress);
        tvContinue = findViewById(R.id.tv_continue_addaddress);
        backBtnAddress = findViewById(R.id.back_btn_address);
        tvUsername = findViewById(R.id.tv_username_addddress);
        tvType = findViewById(R.id.tvtype);
        tvAddress = findViewById(R.id.tvAddress_addaddress);
        tvCity = findViewById(R.id.tvcity_addaddress);
        tvPincode = findViewById(R.id.tvpincode_addaddress);
        tvState = findViewById(R.id.tvstate_addaddress);
        tvMobile = findViewById(R.id.tvmobile_addaddress);
        recylceItemDetail = findViewById(R.id.recylceItemDetail);


        //set listener
        tvAddNewAddress.setOnClickListener(this);
        tvChangeAddress.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        backBtnAddress.setOnClickListener(this);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvaddnew_addaddress:
                showDialogAddress(AddAddressEcomActivity.this);
                break;
            case R.id.tvchange_addaddress:
                showDialogChange(AddAddressEcomActivity.this);
                break;
            case R.id.back_btn_address_dialog:
                dialog.dismiss();
                break;
            case R.id.back_btn_change_address_dialog:
                dialog.dismiss();
                break;
            case R.id.tv_continue_addaddress:
                Toast.makeText(getApplicationContext(), "Payment Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_add_address_addaddress:
                if (validate()) {
                    //************address type*************************
                    int selectedRadioButtonID = radioType.getCheckedRadioButtonId();
                    // If nothing is selected from Radio Group, then it return -1
                    if (selectedRadioButtonID != -1) {
                        RadioButton selectedRadioButton = radioType.findViewById(selectedRadioButtonID);
                         selectedRadioButtonText = selectedRadioButton.getText().toString();
                        Toast.makeText(getApplicationContext(), selectedRadioButtonText, Toast.LENGTH_SHORT).show();

                        //************save location *******************
                        saveNewAddress();

                    } else{
                        Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();
                    }
                }

         /*   case R.id.tv_confirm_addaddress:


              *//*  tvUsername.setText(addressUserResponse.getAddressess().get(po)getName());
                tvType.setText(response.getAddress().getType());
                tvAddress.setText(response.getAddress().getArea());
                tvCity.setText(response.getAddress().getCity());
                tvPincode.setText(response.getAddress().getPincode());
                tvState.setText(response.getAddress().getState());
                tvMobile.setText(response.getAddress().getMobile());*//*
                break;*/
            case R.id.back_btn_address:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;
        }
    }


    private boolean validate() {

        if (etUserName.getText().toString().trim().length() == 0) {
            etUserName.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etUserName, "Please enter User Name", true);
            return false;
        }
        else  if (etMobile.getText().toString().trim().length() == 0) {
            etMobile.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etMobile, "Please enter Contact number", true);
            return false;
        } else if (etLocation.getText().toString().trim().length() == 0) {
            etLocation.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCity, "Please enter Location", true);
            return false;
        }
        else if (etCity.getText().toString().trim().length() == 0) {
            etCity.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCity, "Please enter City", true);
            return false;
        }
        else if (etState.getText().toString().trim().length() == 0) {
            etState.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etState, "Please enter State", true);
            return false;
        }
        else if (etPincode.getText().toString().trim().length() == 0) {
            etPincode.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etPincode, "Please enter Pincode", true);
            return false;
        }
        else if (etLandmark.getText().toString().trim().length() == 0) {
            etLandmark.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etLandmark, "Please enter Landmark", true);
            return false;
        }
        else if (etCountry.getText().toString().trim().length() == 0) {
            etCountry.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCountry, "Please enter Country", true);
            return false;
        }
        return true;
    }

    private void getCarts() {

    }

    private void saveNewAddress() {
        saveEcomAddressRequest=new SaveEcomAddressRequest();
        saveEcomAddressRequest.setName(etUserName.getText().toString());
        saveEcomAddressRequest.setMobile(etMobile.getText().toString());
        saveEcomAddressRequest.setArea(etLocation.getText().toString());
        saveEcomAddressRequest.setCity(etCity.getText().toString());
        saveEcomAddressRequest.setState(etState.getText().toString());
        saveEcomAddressRequest.setPost_code(etPincode.getText().toString());
        saveEcomAddressRequest.setLandmark(etLandmark.getText().toString());
        saveEcomAddressRequest.setCountry(etCountry.getText().toString());
        saveEcomAddressRequest.setType(selectedRadioButtonText);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(AddAddressEcomActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.saveuserLocaiton(saveEcomAddressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpdateEcomAddressResponse>(){
                    @Override
                    public void onSuccess(UpdateEcomAddressResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                             dialog.dismiss();
                             Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();
                            tvUsername.setText(response.getAddress().getName());
                            tvType.setText(response.getAddress().getType());
                            tvAddress.setText(response.getAddress().getArea());
                            tvCity.setText(response.getAddress().getCity());
                            tvPincode.setText(response.getAddress().getPincode());
                            tvState.setText(response.getAddress().getState());
                            tvMobile.setText(response.getAddress().getMobile());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_address), false, e.getCause());
                    }
                }));
    }

    private void updateAddress(final int position) {
        saveEcomAddressRequest=new SaveEcomAddressRequest();
        saveEcomAddressRequest.setAddress_id(String.valueOf(addressUserResponse.getAddressess().get(position).getId()));
        saveEcomAddressRequest.setName(etUserName.getText().toString());
        saveEcomAddressRequest.setMobile(etMobile.getText().toString());
        saveEcomAddressRequest.setArea(etLocation.getText().toString());
        saveEcomAddressRequest.setCity(etCity.getText().toString());
        saveEcomAddressRequest.setState(etState.getText().toString());
        saveEcomAddressRequest.setPost_code(etPincode.getText().toString());
        saveEcomAddressRequest.setLandmark(etLandmark.getText().toString());
        saveEcomAddressRequest.setCountry(etCountry.getText().toString());
        saveEcomAddressRequest.setType(selectedRadioButtonText);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(AddAddressEcomActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.updateUserLocaiton(saveEcomAddressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpdateEcomAddressResponse>(){
                    @Override
                    public void onSuccess(UpdateEcomAddressResponse response) {
                        loadingDialog.hideDialog();

                        if (response.isStatus()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();

                            addressUserResponse.getAddressess().get(position).setName(response.getAddress().getName());
                            addressUserResponse.getAddressess().get(position).setMobile(response.getAddress().getMobile());
                            addressUserResponse.getAddressess().get(position).setArea(response.getAddress().getArea());
                            addressUserResponse.getAddressess().get(position).setCity(response.getAddress().getCity());
                            addressUserResponse.getAddressess().get(position).setState(response.getAddress().getState());
                            addressUserResponse.getAddressess().get(position).setPincode(response.getAddress().getPincode());
                            addressUserResponse.getAddressess().get(position).setLandmark(response.getAddress().getLandmark());
                            addressUserResponse.getAddressess().get(position).setCountry(response.getAddress().getCountry());
                            addressUserResponse.getAddressess().get(position).setType(response.getAddress().getType());

                            changeAddressAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_address), false, e.getCause());
                    }
                }));
    }

    private void showDialogAddress(AddAddressEcomActivity addAddressEcomActivity) {
        dialog = new Dialog(addAddressEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.address_ecom_dialog);

        backBtnDialog = dialog.findViewById(R.id.back_btn_address_dialog);
        tvSaveAddress = dialog.findViewById(R.id.tv_add_address_addaddress);
        etUserName = dialog.findViewById(R.id.et_username_dialog);
        etMobile = dialog.findViewById(R.id.mobilenumber);
        etLocation = dialog.findViewById(R.id.update_location);
        etCity = dialog.findViewById(R.id.update_city);
        etState = dialog.findViewById(R.id.update_state);
        etPincode = dialog.findViewById(R.id.update_pin);
        etLandmark = dialog.findViewById(R.id.update_landmark);
        etCountry = dialog.findViewById(R.id.dialog_country);
        radioType = dialog.findViewById(R.id.radio_type);
        saveBtnAddress = dialog.findViewById(R.id.tv_save_address_addaddress);

        backBtnDialog.setOnClickListener(this);
        tvSaveAddress.setOnClickListener(this);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void showDialogChange(AddAddressEcomActivity addAddressEcomActivity) {
        dialog = new Dialog(addAddressEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.changeaddress_ecom_dialog);

        backBtnChangeAddress = dialog.findViewById(R.id.back_btn_change_address_dialog);
        tvConfirmAddress = dialog.findViewById(R.id.tv_confirm_addaddress);
        addressList = dialog.findViewById(R.id.address_list);
        backBtnChangeAddress.setOnClickListener(this);
       // tvConfirmAddress.setOnClickListener(this);

        if (isNetworkAvailable()) {
            getUserAddress(addAddressEcomActivity);
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.cartEcommLayout), "No Internet Connection!", true);
        }


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void getUserAddress(AddAddressEcomActivity addAddressEcomActivity) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiService = ApiClientEcom.getClient(addAddressEcomActivity).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getUserAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddressUserResponse>() {
                    @Override
                    public void onSuccess(AddressUserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            addressUserResponse = response;
                            addressList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            changeAddressAdapter = new ChangeAddressAdapter(getApplicationContext(),response.getAddressess(),AddAddressEcomActivity.this);
                            addressList.setAdapter(changeAddressAdapter);

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.lichangeAddress), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.lichangeAddress), true, e);
                    }
                }));
    }


    @Override
    public void onEditAddressItem(final int position, AddressUserResponse.AddressessBean addressessBean) {
        showDialogAddress(AddAddressEcomActivity.this);
        etUserName.setText(addressessBean.getName());
        etMobile.setText(addressessBean.getMobile());
        etLocation.setText(addressessBean.getArea());
        etCity.setText(addressessBean.getCity());
        etState.setText(addressessBean.getState());
        etPincode.setText(addressessBean.getPincode());
        etLandmark.setText(addressessBean.getLandmark());
        etCountry.setText(addressessBean.getCountry());

        saveBtnAddress.setVisibility(View.VISIBLE);
        tvSaveAddress.setVisibility(View.GONE);

        saveBtnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validate()) {
                    //************address type*************************
                    int selectedRadioButtonID = radioType.getCheckedRadioButtonId();
                    // If nothing is selected from Radio Group, then it return -1
                    if (selectedRadioButtonID != -1) {
                        RadioButton selectedRadioButton = radioType.findViewById(selectedRadioButtonID);
                        selectedRadioButtonText = selectedRadioButton.getText().toString();
                        Toast.makeText(getApplicationContext(), selectedRadioButtonText, Toast.LENGTH_SHORT).show();

                        //************update location *******************
                        updateAddress(position);

                    } else{
                        Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });



    }



    @Override
    public void onRemoveAddressItem(int position, AddressUserResponse.AddressessBean addressessBean) {

        getRemoveAddress(position,addressessBean);
    }

    @Override
    public void onCheckedAddressItem(final int position, final AddressUserResponse.AddressessBean addressessBean) {
        Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();

        tvConfirmAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"SELECTCONFIRM",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                tvUsername.setText(addressessBean.getName());
                tvType.setText(addressessBean.getType());
                tvAddress.setText(addressessBean.getArea());
                tvCity.setText(addressessBean.getCity());
                tvPincode.setText(addressessBean.getPincode());
                tvState.setText(addressessBean.getState());
                tvMobile.setText(addressessBean.getMobile());
            }
        });
    }

    private void getRemoveAddress(final int position, final AddressUserResponse.AddressessBean addressessBean) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(AddAddressEcomActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.deleteUserAddress(String.valueOf(addressessBean.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RemoveEcomAddressResponse>(){
                    @Override
                    public void onSuccess(RemoveEcomAddressResponse response) {
                        loadingDialog.hideDialog();

                        if (response.isStatus()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();

                            addressUserResponse.getAddressess().remove(position);
                            changeAddressAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.lichangeAddress), false, e.getCause());
                    }
                }));
    }
}
