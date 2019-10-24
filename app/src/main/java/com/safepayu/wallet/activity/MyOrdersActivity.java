package com.safepayu.wallet.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.MyOrdersAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.halper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.MyOrderResponse;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MyOrdersActivity extends BaseActivity implements MyOrdersAdapter.OnPackageSelectListener {

    private RecyclerView recyclerMyOrders;
    private RecyclerLayoutManager layoutManager;
    private LoadingDialog loadingDialog;
    private MyOrdersAdapter mAdapter;
    private Button backBtn;

    private Dialog dialogStatus;
    private TextView tvTransactionIdTV,tvCustomerNumberId,tvAmount,tvRechargeType,tvDescriptionText,tvOperationText,tvContactSupport,tvDate,tvStatus;
    LinearLayout layoutDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = new LoadingDialog(this);
        recyclerMyOrders = findViewById(R.id.list_orderHistory);
        backBtn = findViewById(R.id.myorder_back_btn);


        dialogStatus = new Dialog(MyOrdersActivity.this, android.R.style.Theme_Light);
        dialogStatus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStatus.setContentView(R.layout.recharge_histroy_detail);
        dialogStatus.setCancelable(true);

        tvTransactionIdTV=dialogStatus.findViewById(R.id.tv_recharge_transction_id);
        tvCustomerNumberId=dialogStatus.findViewById(R.id.tv_customer_id_number);
        tvAmount=dialogStatus.findViewById(R.id.tv_wallet_amount);
        tvRechargeType=dialogStatus.findViewById(R.id.tv_recharge_type);
//        GoToWalletBtn=dialogStatus.findViewById(R.id.recharge_back_btn);
        tvDescriptionText=dialogStatus.findViewById(R.id.description);
        tvOperationText=dialogStatus.findViewById(R.id.operationText);
        tvContactSupport=dialogStatus.findViewById(R.id.tv_contct_support);
        layoutDescription=dialogStatus.findViewById(R.id.description_layout);
        tvDate=dialogStatus.findViewById(R.id.tv_detil_time_date);



        backBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
            finish();
        }
    });

        getBankPayments();



    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_orders;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void getBankPayments() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getBankPayment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MyOrderResponse>() {
                    @Override
                    public void onSuccess(MyOrderResponse myOrderResponse) {
                        loadingDialog.hideDialog();
                        if (myOrderResponse.isStatus()){
                            if (myOrderResponse.getBankToWallet().size()>0){

                                recyclerMyOrders.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                mAdapter = new MyOrdersAdapter(getApplicationContext(), myOrderResponse.getBankToWallet(),MyOrdersActivity.this);
                                recyclerMyOrders.setAdapter(mAdapter);
                            }
                        }else{
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.myOrderLayout),"No Data Found",false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(BellNotifictionActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.myOrderLayout), false, e.getCause());
                    }
                }));

    }

    @Override
    public void onOrderItemSelect(int position, MyOrderResponse.BankToWalletBean selectOrderItem) {

        showDetail(selectOrderItem);
    }

    private void showDetail(MyOrderResponse.BankToWalletBean selectOrderItem) {

        tvDescriptionText.setText(selectOrderItem.getDescription());

        tvTransactionIdTV.setText(selectOrderItem.getTransaction_id());
        tvCustomerNumberId.setText(selectOrderItem.getUserid());
        tvDate.setText(selectOrderItem.getCreated_at());
        dialogStatus.show();
    }
}
