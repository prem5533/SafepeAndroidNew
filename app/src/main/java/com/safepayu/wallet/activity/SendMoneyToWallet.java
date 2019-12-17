package com.safepayu.wallet.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.SendToWalletRequest;
import com.safepayu.wallet.models.response.ReferralCodeResponse;
import com.safepayu.wallet.models.response.SendToWalletResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SendMoneyToWallet extends BaseActivity implements View.OnClickListener, PasscodeClickListener {

    Button BackBtn,SendMoneyBtn;
    private EditText MobileED,AmountED;
    private LoadingDialog loadingDialog;
    private TextView tvReferUserName;
    private boolean referralCheck=false;
    private double WalletBalance=0.0f;
    SendToWalletRequest sendToWalletRequest;
    String Mobile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        loadingDialog = new LoadingDialog(this);

        BackBtn=findViewById(R.id.sendmoney_back_btn);
        MobileED=findViewById(R.id.mobile_num);
        AmountED=findViewById(R.id.edit_send_money);
        SendMoneyBtn=findViewById(R.id.send_money_button);
        tvReferUserName=findViewById(R.id.user_name_sendToWallet);

        BackBtn.setOnClickListener(this);
        SendMoneyBtn.setOnClickListener(this);

        try{
            Mobile=getIntent().getStringExtra("Mobile");

            if (Mobile==null || TextUtils.isEmpty(Mobile)){

            }else {
                MobileED.setText(Mobile);
            }

        }catch (Exception e){
            Mobile="";
            e.printStackTrace();
        }

        MobileED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                if (s.length()==10){
                    getReferralDetails();
                }else {
                    if (referralCheck) {
                        referralCheck=false;
                        tvReferUserName.setText("");
                        tvReferUserName.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


        try {
            WalletBalance= Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().WALLET_BALANCE));
        }catch (Exception e){
            WalletBalance=0.0f;
            e.printStackTrace();
        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.send_money_to_wallet;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sendmoney_back_btn:
                finish();
                break;

            case R.id.send_money_button:

                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Goto Your Profile and Verify Your Email First",true);
                }else {
                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Buy Membership To Enjoy App's Features",true);
                    }else {
                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().IS_BLOCKED).equalsIgnoreCase("0")){
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Transaction Is Closed Today",true);
                        }else {
                            CheckValidate();
                        }

                    }
                }
                break;
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void CheckValidate(){

        String Mobile=MobileED.getText().toString().trim();
        String Amount=AmountED.getText().toString().trim();
        int AmountInt=0;
        try{
            AmountInt= Integer.parseInt(Amount);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(Mobile)){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Enter Your Mobile Number",false);
        }else {
            if (TextUtils.isEmpty(Amount)){
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Enter Amount",false);
            }else {
                if (Mobile.length()==10){

                    if (Integer.parseInt(Amount)<0 || Integer.parseInt(Amount)==0 ){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Enter Correct Amount",false);
                    }else {
                        if (AmountInt<8001 && AmountInt>99){

                            if (AmountInt==(int)WalletBalance || AmountInt<(int)WalletBalance){
                                if (referralCheck){
                                    sendToWalletRequest=new SendToWalletRequest();
                                    sendToWalletRequest.setAmount(Amount);
                                    sendToWalletRequest.setMobile(Mobile);
                                    sendToWalletRequest.setUser_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));

                                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                                        startActivity(new Intent(SendMoneyToWallet.this,CreatePassCodeActivity.class));
                                    } else {
                                        PasscodeDialog passcodeDialog = new PasscodeDialog(SendMoneyToWallet.this, SendMoneyToWallet.this, "");
                                        passcodeDialog.show();
                                    }
                                }else {
                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Transaction User Not Registered Or Blocked",true);
                                }
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Enter Amount Less Than Wallet Balance",true);
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Enter Amount Between Rs 100 And Rs 8000 ",true);
                        }
                    }
                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Enter Valid Mobile Number",false);
                }
            }
        }
    }

    private void WithAmountMethod(SendToWalletRequest sendToWalletRequest){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.transferWalletToWallet(sendToWalletRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SendToWalletResponse>() {
                    @Override
                    public void onSuccess(SendToWalletResponse response) {
                        loadingDialog.hideDialog();
                        Intent intentStatus=new Intent(SendMoneyToWallet.this,PaidOrderActivity.class);
                        if (response.isStatus()) {

                            intentStatus.putExtra("status","success");
                            intentStatus.putExtra("txnid",response.getUtrId());
                            intentStatus.putExtra("Amount",AmountED.getText().toString().trim());
                            intentStatus.putExtra("date",response.getData());
                            intentStatus.putExtra("productinfo","Wallet To Wallet Transaction");
                            startActivity(intentStatus);
                            finish();
                        }else {
                            showDialogFailed(response.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.sendMoneyToWalletLayout), true, e);
                    }
                }));

    }

    private void getReferralDetails() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getReferralDetails("","2",MobileED.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ReferralCodeResponse>() {
                    @Override
                    public void onSuccess(ReferralCodeResponse response) {
                        loadingDialog.hideDialog();
                        try {
                            if (response.isStatus()) {
                                referralCheck=true;
                                tvReferUserName.setText(response.getPackages());
                                MobileED.setSelection(MobileED.getText().toString().length());
                                tvReferUserName.setVisibility(View.VISIBLE);
                                //
                            } else {
                                referralCheck=false;
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout), response.getMessage(), true);
                                MobileED.setText("");
                                tvReferUserName.setText("");
                                tvReferUserName.setVisibility(View.GONE);
                                MobileED.requestFocus();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.sendMoneyToWalletLayout), true, e);
                    }
                }));

    }

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched){
            if (isNetworkAvailable()){
                WithAmountMethod(sendToWalletRequest);
            }else {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Please Check Your Internet Connection",false);
            }
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),"Invalid Passcode",false);
        }
    }

    public void showDialogFailed(String Message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("SafePe Alert")
                .setCancelable(false)
                .setMessage("\n"+Message+"\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation


                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }
}
