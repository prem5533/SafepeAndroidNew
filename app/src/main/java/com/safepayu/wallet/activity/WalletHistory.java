package com.safepayu.wallet.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private Dialog dialogStatus;
    private RelativeLayout StatusColorBackground;
    private LinearLayout DescriptionLayout;
    private TextView StatusTV,TransactionIdTV,CustomerNumberIdTV,AmountTV,RechargeTypeTV,DescriptionTextTV,OperationTextTV,ContactSupportTV;
    private Button GoToWalletBtn;

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

        dialogStatus = new Dialog(WalletHistory.this, android.R.style.Theme_Light);
        dialogStatus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStatus.setContentView(R.layout.recharge_histroy_detail);
        dialogStatus.setCancelable(true);

        StatusColorBackground=dialogStatus.findViewById(R.id.headerbar);
        StatusTV=dialogStatus.findViewById(R.id.tv_status);
        TransactionIdTV=dialogStatus.findViewById(R.id.tv_recharge_transction_id);
        CustomerNumberIdTV=dialogStatus.findViewById(R.id.tv_customer_id_number);
        AmountTV=dialogStatus.findViewById(R.id.tv_wallet_amount);
        RechargeTypeTV=dialogStatus.findViewById(R.id.tv_recharge_type);
        GoToWalletBtn=dialogStatus.findViewById(R.id.recharge_back_btn);
        DescriptionTextTV=dialogStatus.findViewById(R.id.description);
        OperationTextTV=dialogStatus.findViewById(R.id.operationText);
        ContactSupportTV=dialogStatus.findViewById(R.id.tv_contct_support);
        DescriptionLayout=dialogStatus.findViewById(R.id.description_layout);
        DescriptionLayout.setVisibility(View.VISIBLE);
        OperationTextTV.setText("Operation ");
        
        getWalletHistory();

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GoToWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStatus.dismiss();
            }
        });

        ContactSupportTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WalletHistory.this,ContactUs.class));
                dialogStatus.dismiss();
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
        int statusNo=0;
        String CustNo="";

        try{
            statusNo=selectedPackage.getStatus();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (statusNo==2){
            StatusTV.setText("Pending");
            StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.clay_yellow));
        }else  if (statusNo==1){
            StatusTV.setText("Successful");
            StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.green_500));
        }else {
            StatusTV.setText("Failed");
            StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.red_500));
        }

        CustNo=selectedPackage.getWallet_no();
        CustNo=CustNo.substring(3,CustNo.length());

        DescriptionTextTV.setText(selectedPackage.getDescription());
        RechargeTypeTV.setText(selectedPackage.getOperation());
        TransactionIdTV.setText(selectedPackage.getTransaction_no());
        AmountTV.setText(getResources().getString(R.string.rupees)+" "+selectedPackage.getAmount());
        CustomerNumberIdTV.setText(CustNo);

        dialogStatus.show();

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
