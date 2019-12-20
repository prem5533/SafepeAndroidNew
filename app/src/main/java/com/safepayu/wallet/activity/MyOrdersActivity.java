package com.safepayu.wallet.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.flight.FlightHistoryActivity;
import com.safepayu.wallet.adapter.MyOrdersAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.MyOrderResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MyOrdersActivity extends BaseActivity implements MyOrdersAdapter.OnPackageSelectListener, View.OnClickListener {

    private RecyclerView recyclerMyOrders;
    private RecyclerLayoutManager layoutManager;
    private LoadingDialog loadingDialog;
    private MyOrdersAdapter mAdapter;
    private Button backBtn;
    private RelativeLayout StatusColorBackground;

    private Dialog dialogStatus;
    private TextView tvTransactionIdTV,tvCustomerNumberId,tvAmount,tvRechargeType,tvDescriptionText,tvOperationText,tvContactSupport,tvDate,tvStatus,tvCustomerNameNumber
    ,GoToWalletBtn;
    LinearLayout layoutDescription,layoutFlightHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = new LoadingDialog(this);
        recyclerMyOrders = findViewById(R.id.list_orderHistory);
        layoutFlightHistory=findViewById(R.id.layout_flight_history);

        backBtn = findViewById(R.id.myorder_back_btn);


        dialogStatus = new Dialog(MyOrdersActivity.this, android.R.style.Theme_Light);
        dialogStatus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStatus.setContentView(R.layout.recharge_histroy_detail);
        dialogStatus.setCancelable(true);

        StatusColorBackground=dialogStatus.findViewById(R.id.headerbar);
        tvTransactionIdTV=dialogStatus.findViewById(R.id.tv_recharge_transction_id);
        tvCustomerNumberId=dialogStatus.findViewById(R.id.tv_customer_id_number);
        tvAmount=dialogStatus.findViewById(R.id.tv_wallet_amount);
        tvRechargeType=dialogStatus.findViewById(R.id.tv_recharge_type);
        GoToWalletBtn=dialogStatus.findViewById(R.id.recharge_back_btn);
        tvDescriptionText=dialogStatus.findViewById(R.id.description);
        tvOperationText=dialogStatus.findViewById(R.id.operationText);
        tvContactSupport=dialogStatus.findViewById(R.id.tv_contct_support);
        layoutDescription=dialogStatus.findViewById(R.id.description_layout);
        tvCustomerNameNumber=dialogStatus.findViewById(R.id.tv_customer_name_number);
        tvDate=dialogStatus.findViewById(R.id.tv_detil_time_date);
        tvStatus=dialogStatus.findViewById(R.id.tv_status);

        layoutFlightHistory.setOnClickListener(this);
        layoutFlightHistory.setVisibility(View.GONE);


        backBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
            finish();
        }
    });
        tvContactSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrdersActivity.this,ContactUs.class));
                dialogStatus.dismiss();
            }
        });

        GoToWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStatus.dismiss();
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
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(BellNotifictionActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.myOrderLayout), false, e.getCause());
                    }
                }));

    }

    @Override
    public void onOrderItemSelect(int position, MyOrderResponse.BankToWalletBean selectOrderItem) {

        showDetail(selectOrderItem);
    }

    private void showDetail(MyOrderResponse.BankToWalletBean selectOrderItem) {

        int statusNo=0;
        String CustNo="";

        try{
            statusNo=selectOrderItem.getStatus();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (statusNo==2){
            tvStatus.setText("Pending");
            StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.clay_yellow));
        }else  if (statusNo==1){
            tvStatus.setText("Successful");
            StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.green_500));
        }else {
            tvStatus.setText("Failed");
            StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.red_500));
        }

        tvDescriptionText.setText(selectOrderItem.getDescription());

        tvTransactionIdTV.setText(selectOrderItem.getTransaction_id());
      //  tvCustomerNumberId.setText(selectOrderItem.getUserid());
        tvDate.setText(selectOrderItem.getCreated_at());
        tvRechargeType.setText(selectOrderItem.getDescription());
        tvAmount.setText(getResources().getString(R.string.rupees)+ " " +selectOrderItem.getAmount());
        String  usernumber = selectOrderItem.getUserid();
        String[] parts = usernumber.split("u");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        tvCustomerNumberId.setText(part2);
        dialogStatus.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_flight_history:
                Intent intent = new Intent( MyOrdersActivity.this, FlightHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
