package com.safepayu.wallet.activity;

import android.content.Intent;
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
import com.safepayu.wallet.models.response.CommissionWalletTransferResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TransferCommissionToWallet extends BaseActivity implements PasscodeClickListener {

    Button SendToWalletBtn,BackBtn;
    private EditText AmountED;
    private int Amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        SendToWalletBtn=findViewById(R.id.sendToWalletBtn);
        BackBtn=findViewById(R.id.backBtn_transferCommission);
        AmountED = findViewById(R.id.withdrawAmount_commWallet);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SendToWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Amount = Integer.parseInt(AmountED.getText().toString().trim());
                } catch (Exception e) {
                    Amount = 0;
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(AmountED.getText().toString().trim())) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), "Please Enter Amount", false);
                } else {
                    if (Amount > 0) {
                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                            startActivity(new Intent(TransferCommissionToWallet.this, CreatePassCodeActivity.class));
                        } else {
                            PasscodeDialog passcodeDialog = new PasscodeDialog(TransferCommissionToWallet.this, TransferCommissionToWallet.this, "");
                            passcodeDialog.show();
                        }
                    } else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), "Please Enter Amount", false);
                    }
                }
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.transfer_commission_to_wallet;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }


    private void transferCommWalletToMainWallet() {
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.transferCommWalletToMainWallet(AmountED.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommissionWalletTransferResponse>() {
                    @Override
                    public void onSuccess(CommissionWalletTransferResponse response) {
                        Intent intentStatus=new Intent(TransferCommissionToWallet.this,PaidOrderActivity.class);
                        if (response.isStatus()) {
                            //Toast.makeText(TransferCommissionToWallet.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                           // finish();
                            intentStatus.putExtra("status","success");
                        } else {
                            intentStatus.putExtra("status","failed");
                            Toast.makeText(TransferCommissionToWallet.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), response.getMessage(), false);
                        }

                        intentStatus.putExtra("txnid",response.getUtrId());
                        intentStatus.putExtra("Amount",AmountED.getText().toString().trim());
                        intentStatus.putExtra("date",response.getDate());
                        intentStatus.putExtra("productinfo","Commission Wallet To Main Wallet Transaction");
                        startActivity(intentStatus);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.mobileRechargeLayout), true, e);
                    }
                }));

    }

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched) {
            transferCommWalletToMainWallet();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), "Invalid Passcode", false);
        }

    }
}
