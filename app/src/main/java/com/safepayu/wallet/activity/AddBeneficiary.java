package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.AddBeneficiaryRequest;
import com.safepayu.wallet.models.response.AddBeneficiaryResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddBeneficiary extends BaseActivity {

    private Button BackBtn,AddBenBtn;
    private EditText AccountNameED,AccountNumberED,AccountConfirmED,IFSCED;
    private LoadingDialog loadingDialog;
    private boolean CheckNet=false;
    private ImageView showAccNo,HideAccNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);

        BackBtn=findViewById(R.id.send_back_btn);
        AccountNameED=findViewById(R.id.accountName);
        AccountNumberED=findViewById(R.id.accountNumber);
        AccountConfirmED=findViewById(R.id.confirmAccountNumber);
        IFSCED=findViewById(R.id.ifscCode);
        AddBenBtn=findViewById(R.id.bankAddBtn);
        showAccNo=findViewById(R.id.password_visible);
        HideAccNo=findViewById(R.id.password_invisible);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddBenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckValidate();
            }
        });

        showAccNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccNo.setVisibility(View.GONE);
                HideAccNo.setVisibility(View.VISIBLE);
                AccountConfirmED.setTransformationMethod(null);
                try {
                    AccountConfirmED.setSelection(AccountConfirmED.getText().toString().length());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        HideAccNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccNo.setVisibility(View.VISIBLE);
                HideAccNo.setVisibility(View.GONE);
                AccountConfirmED.setTransformationMethod(new PasswordTransformationMethod());
                try {
                    AccountConfirmED.setSelection(AccountConfirmED.getText().toString().length());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.add_beneficiary;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected){
            CheckNet=true;
        }else {
            CheckNet =false;
        }

    }

    private void CheckValidate(){

        String AccountN=AccountNameED.getText().toString().trim();
        String AccountNF=AccountConfirmED.getText().toString().trim();

        if (TextUtils.isEmpty(AccountNameED.getText().toString().trim())){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout),"Please Enter Name As On Account",false);
        }else {
            if (TextUtils.isEmpty(AccountNumberED.getText().toString().trim())){
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout),"Please Enter Account Number",false);
            }else {
                if (TextUtils.isEmpty(AccountConfirmED.getText().toString().trim())){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout),"Please Enter Account Number To Confirm",false);
                }else {
                    if (TextUtils.isEmpty(IFSCED.getText().toString().trim())){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout),"Please Enter IFSC Code",false);
                    }else {
                        if (AccountN.equals(AccountNF)){
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout),"Please Same Account Number In Both Account's Field",false);
                        }else {
                            AddBeneficiaryRequest addBeneficiaryRequest=new AddBeneficiaryRequest();
                            addBeneficiaryRequest.setName(AccountNameED.getText().toString().trim());
                            addBeneficiaryRequest.setBank_account(AccountNumberED.getText().toString().trim());
                            addBeneficiaryRequest.setIfsc_code(IFSCED.getText().toString().trim());
                            addBeneficiaryRequest.setUpi("");
                            addBeneficiaryRequest.setPaytm("");

                            if (CheckNet){
                                addBenMethod(addBeneficiaryRequest);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout),"Check Your Internet Connection!",false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addBenMethod(AddBeneficiaryRequest addBeneficiaryRequest){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.addBeneficiary(addBeneficiaryRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddBeneficiaryResponse>() {
                    @Override
                    public void onSuccess(AddBeneficiaryResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            Toast.makeText(AddBeneficiary.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout),response.getMessage()+"\n"+response.getReason(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.addBeneficiaryLayout), true, e);
                    }
                }));

    }
}
