package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.RechargeHistoryAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.halper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.RechargeHistoryResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RechargeHistory extends BaseActivity implements RechargeHistoryAdapter.OnPackageSelectListener {

    Button BackBtn;
    private RecyclerView RechargeHistoryListView;
    private RecyclerLayoutManager layoutManager;
    private LoadingDialog loadingDialog;
    private RechargeHistoryAdapter mAdapter;
    private RechargeHistoryResponse historyResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);
        BackBtn = findViewById(R.id.backbtn_rechargeHistory);
        RechargeHistoryListView = findViewById(R.id.list_rechargHistory);

        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        RechargeHistoryListView.setLayoutManager(layoutManager);
        mAdapter = new RechargeHistoryAdapter(this, this);
        RechargeHistoryListView.setAdapter(mAdapter);

        getRechargeHistory();

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.recharge_history;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void SelectedHistory(RechargeHistoryResponse.DataBean selectedPackage) {

        Toast.makeText(this, selectedPackage.getTransactionID(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPackageSelect(int position, RechargeHistoryResponse.DataBean selectedPackage) {
        SelectedHistory(selectedPackage);
    }

    private void getRechargeHistory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getRechargeHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RechargeHistoryResponse>() {
                    @Override
                    public void onSuccess(RechargeHistoryResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                historyResponse = response;
                                mAdapter.addItem(response.getData());
                            } catch (Exception e) {
                                e.printStackTrace();
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.rechargeHistoryLayout), "Something Went Wrong", false);
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.rechargeHistoryLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.rechargeHistoryLayout), false, e.getCause());
                    }
                }));
    }
}
