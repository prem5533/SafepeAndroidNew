package com.safepayu.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.WalletResponse;

import java.text.NumberFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WalletActivity extends BaseActivity {

    private TextView AddMoneyToWallet,SendMoney,AmountTV;
    private Button BackBtn;
    private LoadingDialog loadingDialog;
    private LinearLayout WalletHistoryLayout, RechargeHistoryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);

        AddMoneyToWallet=findViewById(R.id.tv_addMoneyToSafepe);
        SendMoney=findViewById(R.id.send_txt);
        BackBtn=findViewById(R.id.wallet_back_btn);
        AmountTV=findViewById(R.id.tv_walletAmount);
        WalletHistoryLayout = findViewById(R.id.wallet_history);
        RechargeHistoryLayout = findViewById(R.id.recharge_history);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SendMoney.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();
            }
        });

        AddMoneyToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WalletAddMoney.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();
            }
        });

        WalletHistoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, WalletHistory.class);
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                startActivity(intent);
            }
        });

        RechargeHistoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, RechargeHistory.class);
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                startActivity(intent);
            }
        });


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onResume(){
        super.onResume();
        if (isNetworkAvailable()){
            getWalletMethod();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.walletLayout),"No Internet Connection",false);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getWalletMethod( ){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getWalletDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WalletResponse>() {
                    @Override
                    public void onSuccess(WalletResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            AmountTV.setText(getResources().getString(R.string.rupees)+" "+ NumberFormat.getInstance().format(response.getWallet().getAmount()));

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.walletLayout), true, e);
                    }
                }));

    }

}
