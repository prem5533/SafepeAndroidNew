package com.safepayu.wallet.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.WalletHistoryAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.halper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.WalletHistoryResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WalletHistory extends BaseActivity implements WalletHistoryAdapter.OnPackageSelectListener {

    Button BackBtn;
    private RecyclerView WalletHistoryListView;
    private RecyclerLayoutManager layoutManager;
    private LoadingDialog loadingDialog;
    private WalletHistoryAdapter mAdapter;
    private WalletHistoryResponse historyResponse;

    private Dialog dialogPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);
        BackBtn = findViewById(R.id.backbtn_walletHistory);
        WalletHistoryListView = findViewById(R.id.list_walletHistory);

        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        WalletHistoryListView.setLayoutManager(layoutManager);
        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(true);
        WalletHistoryListView.setLayoutManager(layoutManager);
        mAdapter = new WalletHistoryAdapter(this, this);
        WalletHistoryListView.setAdapter(mAdapter);

        dialogPending = new Dialog(WalletHistory.this, android.R.style.Theme_Light);
        dialogPending.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPending.setContentView(R.layout.recharge_histroy_detail);
        dialogPending.setCancelable(false);


        getWalletHistory();

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.wallet_history;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onPackageSelect(int position, WalletHistoryResponse.DataBean selectedPackage) {
        SelectedHistory(selectedPackage);
    }

    private void SelectedHistory(WalletHistoryResponse.DataBean selectedPackage) {

        Toast.makeText(this, selectedPackage.getTransaction_no(), Toast.LENGTH_SHORT).show();

    }

    private void getWalletHistory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getWalletHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WalletHistoryResponse>() {
                    @Override
                    public void onSuccess(WalletHistoryResponse response) {
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
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.walletHistoryLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.walletHistoryLayout), false, e.getCause());
                    }
                }));
    }
}
