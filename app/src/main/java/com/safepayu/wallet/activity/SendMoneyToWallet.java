package com.safepayu.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.safepayu.wallet.models.request.SendToWalletRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SendMoneyToWallet extends BaseActivity implements View.OnClickListener, PasscodeClickListener {

    Button BackBtn,SendMoneyBtn;
    private EditText MobileED,AmountED;
    private LoadingDialog loadingDialog;
    SendToWalletRequest sendToWalletRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        loadingDialog = new LoadingDialog(this);

        BackBtn=findViewById(R.id.sendmoney_back_btn);
        MobileED=findViewById(R.id.mobile_num);
        AmountED=findViewById(R.id.edit_send_money);
        SendMoneyBtn=findViewById(R.id.send_money_button);

        BackBtn.setOnClickListener(this);
        SendMoneyBtn.setOnClickListener(this);
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
                CheckValidate();
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
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {

                            Toast.makeText(SendMoneyToWallet.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.sendMoneyToWalletLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
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
}
