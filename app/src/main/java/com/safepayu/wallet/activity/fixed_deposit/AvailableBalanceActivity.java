package com.safepayu.wallet.activity.fixed_deposit;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.deposit.DepositRateAdapter;
import com.safepayu.wallet.adapter.deposit.InstructionAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.AllListData;
import com.safepayu.wallet.models.response.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AvailableBalanceActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btn_create_deposit;
    public LinearLayout ll_back;
    private LoadingDialog loadingDialog;
    public TextView tv_interest_rate, tv_fixed_deposit_amount;
    public RecyclerView rv_interest_table_rate, rv_instruction;
    public RecyclerView.Adapter mInterestTable, mInstruction;
    public List<AllListData> dataList = new ArrayList<>();
    public String depositAmount, fdInterest, balanceAmount,term_and_conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_balance);

        loadingDialog = new LoadingDialog(AvailableBalanceActivity.this);
        Intent intent = getIntent();
        if (intent != null) {
            depositAmount = intent.getStringExtra("depositAmount");
            fdInterest = intent.getStringExtra("fdInterest");
            balanceAmount = intent.getStringExtra("balanceAmount");
        }

        ll_back = findViewById(R.id.ll_back);
        btn_create_deposit = findViewById(R.id.btn_create_deposit);
        tv_interest_rate = findViewById(R.id.tv_interest_rate);
        tv_fixed_deposit_amount = findViewById(R.id.tv_fixed_deposit_amount);
        tv_fixed_deposit_amount.setText(depositAmount);
        rv_instruction = findViewById(R.id.rv_instruction);
        rv_instruction.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        setPeriodData();
        getInvestment();

        ll_back.setOnClickListener(this);
        tv_interest_rate.setOnClickListener(this);
        btn_create_deposit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_interest_rate:
                callInterestTable();
                break;

            case R.id.ll_back:
                finish();
                break;

            case R.id.btn_create_deposit:
                startActivity(new Intent(AvailableBalanceActivity.this, CreateFixedDepositActivity.class).
                        putExtra("fdInterest", fdInterest).
                        putExtra("balanceAmount", balanceAmount).putExtra("term_and_conditions",term_and_conditions));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
        }
    }

    private void callInterestTable() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_interest_table_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);

        LinearLayout ll_alertDismiss = dialogView.findViewById(R.id.ll_alertDismiss);

        rv_interest_table_rate = dialogView.findViewById(R.id.rv_interest_table_rate);
        rv_interest_table_rate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mInterestTable = new DepositRateAdapter(this, dataList);
        rv_interest_table_rate.setAdapter(mInterestTable);

        ll_alertDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void getInvestment() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(AvailableBalanceActivity.this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getInvestment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponseModel>() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        loadingDialog.hideDialog();
                        if (response.status) {
                            try {
                                term_and_conditions = response.data.tnc;
                                mInstruction = new InstructionAdapter(AvailableBalanceActivity.this, response.data.instruction);
                                rv_instruction.setAdapter(mInstruction);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(AvailableBalanceActivity.this.findViewById(R.id.ll_parant), response.message, false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(AvailableBalanceActivity.this.findViewById(R.id.ll_parant), false, e.getCause());
                    }
                }));
    }

    private void setPeriodData() {
        AllListData allListData12 = new AllListData();
        allListData12.period = "12-14 Months";
        allListData12.r_rate = "6.65 %";
        dataList.add(allListData12);

        AllListData allListData11 = new AllListData();
        allListData11.period = "270-364 Days";
        allListData11.r_rate = "6.25 %";
        dataList.add(allListData11);

        AllListData allListData10 = new AllListData();
        allListData10.period = "211-269 Days";
        allListData10.r_rate = "6.00 %";
        dataList.add(allListData10);

        AllListData allListData9 = new AllListData();
        allListData9.period = "181-210 Days";
        allListData9.r_rate = "6.00 %";
        dataList.add(allListData9);

        AllListData allListData8 = new AllListData();
        allListData8.period = "121-180 Days";
        allListData8.r_rate = "5.75 %";
        dataList.add(allListData8);

        AllListData allListData7 = new AllListData();
        allListData7.period = "90-120 Days";
        allListData7.r_rate = "5.75 %";
        dataList.add(allListData7);

        AllListData allListData6 = new AllListData();
        allListData6.period = "61-90 Days";
        allListData6.r_rate = "5.50 %";
        dataList.add(allListData6);

        AllListData allListData5 = new AllListData();
        allListData5.period = "46-60 Days";
        allListData5.r_rate = "5.50 %";
        dataList.add(allListData5);

        AllListData allListData4 = new AllListData();
        allListData4.period = "31-45 Days";
        allListData4.r_rate = "5.00 %";
        dataList.add(allListData4);

        AllListData allListData3 = new AllListData();
        allListData3.period = "15-30 Days";
        allListData3.r_rate = "4.25 %";
        dataList.add(allListData3);

        AllListData allListData2 = new AllListData();
        allListData2.period = "7-14 Days";
        allListData2.r_rate = "4.00 %";
        dataList.add(allListData2);

        AllListData allListData = new AllListData();
        allListData.period = "0-6 Days";
        allListData.r_rate = "0.00 %";
        dataList.add(allListData);

    }
}
