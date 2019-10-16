package com.safepayu.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.PackageDetailsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PackageDetails extends BaseActivity {

    Button BackBtn;
    private LoadingDialog loadingDialog;
    private TextView PackageNameTV,PackageAmountTV,BonusAmountTV,BalanceAmountTV,BonusCreditTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);

        BackBtn=findViewById(R.id.backbtn_packagedetails);
        PackageNameTV=findViewById(R.id.txtPckgName);
        PackageAmountTV=findViewById(R.id.txtPckgAmount);
        BonusAmountTV=findViewById(R.id.txtPckgBonusAmnt);
        BalanceAmountTV=findViewById(R.id.txtPckgAmountBalance);
        BonusCreditTv=findViewById(R.id.txtPckgAmountCredit);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (isNetworkAvailable()){
            getPackageDetails();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.packageDetailsLayout),"Check Your Internet Connection",false);
        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.package_details;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getPackageDetails(){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getPackageDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PackageDetailsResponse>() {
                    @Override
                    public void onSuccess(PackageDetailsResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            PackageNameTV.setText(response.getPackageX().getPackage_name());
                            PackageAmountTV.setText(getResources().getString(R.string.rupees)+String.valueOf(response.getPackageX().getPackage_amount()));
                            BonusAmountTV.setText(getResources().getString(R.string.rupees)+String.valueOf(response.getPackageX().getBonus_amount()));
                            BalanceAmountTV.setText(getResources().getString(R.string.rupees)+String.valueOf(response.getPackageX().getBalance_amount()));
                            BonusCreditTv.setText(getResources().getString(R.string.rupees)+String.valueOf(response.getPackageX().getBonus_credited()));

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.withMoneyLayout), true, e);
                    }
                }));

    }
}
