package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.PackageListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.halper.RecyclerLayoutManager;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.response.PackageListData;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BuyMemberShip extends BaseActivity implements PackageListAdapter.OnPackageSelectListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private LoadingDialog loadingDialog;
    private RecyclerView packageListView;
    private RecyclerLayoutManager layoutManager;
    private PackageListAdapter mAdapter;
    private PackageListData packageListData;
    private RadioGroup paymentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = new LoadingDialog(this);

        setToolbar(true, "Buy Member Ship", true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        packageListView = findViewById(R.id.list_packageListView);
        layoutManager = new RecyclerLayoutManager(2, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        packageListView.setLayoutManager(layoutManager);
        mAdapter = new PackageListAdapter(this, this);
        packageListView.setAdapter(mAdapter);
        getPackages();

        paymentMode = findViewById(R.id.rg_paymentMode);
        findViewById(R.id.btn_proceed).setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_packageName)).setText("");
        ((TextView) findViewById(R.id.tv_packageAmount)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(0d));
        ((TextView) findViewById(R.id.tv_totalAmountPay)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(0d));

        paymentMode.setOnCheckedChangeListener(this);
    }

    public void showPackageDetails(PackageListData.Packages selectedPackage) {
        ((TextView) findViewById(R.id.tv_packageName)).setText(selectedPackage.getPackageName());
        ((TextView) findViewById(R.id.tv_packageAmount)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(selectedPackage.getPackageAmount()));
        ((TextView) findViewById(R.id.tv_tax)).setText(packageListData.getTax().getTaxValue() + "%");
        Double totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(selectedPackage.getPackageAmount(), Double.parseDouble(packageListData.getTax().getTaxValue()));
        ((TextView) findViewById(R.id.tv_totalAmountPay)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(totalPayableAmount));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_buy_member_ship;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void getPackages() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAllPackages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PackageListData>() {
                    @Override
                    public void onSuccess(PackageListData response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            packageListData = response;
                            mAdapter.addItem(response.getPackages());
                            ((TextView) findViewById(R.id.tv_taxDetails)).setText("Additional " + response.getTax().getTaxValue() + "% GST will be charged from the total amount");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

    @Override
    public void onPackageSelect(int position, PackageListData.Packages selectedPackage) {
        showPackageDetails(selectedPackage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_proceed:
                if (mAdapter.getSelectedData() != null) {

                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(mToolbar, "Please Select Pacakage", false);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_walled:

                break;
            case R.id.rb_bank:

                break;
        }
    }
}
