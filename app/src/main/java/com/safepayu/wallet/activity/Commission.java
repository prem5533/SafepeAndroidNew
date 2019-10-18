package com.safepayu.wallet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.CommissionDetailsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Commission extends BaseActivity {

    private Button BackBtn;
    private TextView SendWallet,CommBalanceTV,LeftBussTV,RightBussTV,TotalBussTV,SponserBussTV,MatchingBussTV,GetMemberShipBtn;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog=new LoadingDialog(this);
        SendWallet=findViewById(R.id.send_to_wallet);
        BackBtn=findViewById(R.id.send_back_btn);
        CommBalanceTV=findViewById(R.id.current_referral_business);
        LeftBussTV=findViewById(R.id.left_business);
        RightBussTV=findViewById(R.id.right_business);
        TotalBussTV=findViewById(R.id.total_business);
        SponserBussTV=findViewById(R.id.sponser_business);
        MatchingBussTV=findViewById(R.id.matching_business);
        GetMemberShipBtn=findViewById(R.id.getMemberShipBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SendWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TransferCommissionToWallet.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
            }
        });

        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")){
            GetMemberShipBtn.setVisibility(View.VISIBLE);
            SendWallet.setVisibility(View.GONE);
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),"Please Goto Your Profile and Verify Your Email First",true);
        }else {
            getCommissionDetails();
            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
                GetMemberShipBtn.setVisibility(View.VISIBLE);
                SendWallet.setVisibility(View.GONE);
             //   BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),"Please Buy Membership To Enjoy App's Features",true);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //    alertDialogBuilder.setTitle("Commission");
                alertDialogBuilder.setTitle( Html.fromHtml("<font color='#3db7c2'>Commission</font>"));
                alertDialogBuilder
                        .setMessage("Please Buy Membership To Enjoy App's Features")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }else {
                GetMemberShipBtn.setVisibility(View.GONE);
                SendWallet.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.commission;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected){

        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),getResources().getString(R.string.internet_check),false);
        }

    }

    private void getCommissionDetails() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getCommissionDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommissionDetailsResponse>() {
                    @Override
                    public void onSuccess(CommissionDetailsResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            CommBalanceTV.setText(getResources().getString(R.string.rupees)+" "+response.getTotcurrentcommwallet());
                            LeftBussTV.setText(getResources().getString(R.string.rupees)+" "+response.getTotleftbusiness());
                            RightBussTV.setText(getResources().getString(R.string.rupees)+" "+response.getTotrightbusiness());
                            TotalBussTV.setText(getResources().getString(R.string.rupees)+" "+response.getTotincome());
                            SponserBussTV.setText(getResources().getString(R.string.rupees)+" "+response.getTotdirectbusiness());
                            MatchingBussTV.setText(getResources().getString(R.string.rupees)+" "+response.getTotmatchtbusiness());

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.commissionLayout), false, e.getCause());
                    }
                }));
    }




}
