package com.safepayu.wallet.activity.fixed_deposit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.ContactUs;
import com.safepayu.wallet.adapter.MyFixedDepositAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.InvestmentResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FixedDepositListActivity extends AppCompatActivity implements MyFixedDepositAdapter.OnFDSelectListener {

    private RecyclerView recyclerView;
    private MyFixedDepositAdapter myFixedDepositAdapter;
    private Dialog dialogFDeposit;
    private LoadingDialog loadingDialog;
    private Button send_back_btn_fd,FD_back_btn;

    private TextView tvFDid,tvFDAmount,tvtax,tvstatus,tvPaymentMode,operationText,tv_bonus_amount,tv_balance_amount,tv_contct_support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit_list);
        findId();
        getInvestment();
    }



    private void findId() {
        recyclerView = findViewById(R.id.recycle_deposit);
        send_back_btn_fd = findViewById(R.id.send_back_btn_fd);
        loadingDialog = new LoadingDialog(this);
        send_back_btn_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
            }
        });
    }




    private void getInvestment() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getInvestmentlist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InvestmentResponse>() {
                    @Override
                    public void onSuccess(InvestmentResponse response) {
                        if (response.isStatus()){
                            loadingDialog.hideDialog();
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            myFixedDepositAdapter = new MyFixedDepositAdapter(getApplicationContext(),response.getData().getInvestment(),FixedDepositListActivity.this);
                            recyclerView.setAdapter(myFixedDepositAdapter);

                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.fixedLayout), false, e.getCause());
                    }
                }));
    }


    @Override
    public void onFDItemSelect(int position, InvestmentResponse.DataBean.InvestmentBean investmentBean) {
        dialogFDeposit(FixedDepositListActivity.this, position,  investmentBean);
    }

    private void dialogFDeposit(FixedDepositListActivity fixedDepositListActivity, int position, InvestmentResponse.DataBean.InvestmentBean investmentBean) {
        dialogFDeposit = new Dialog(fixedDepositListActivity);
        dialogFDeposit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFDeposit.setContentView(R.layout.fixed_deposit_detail);

         tvFDid = dialogFDeposit.findViewById(R.id.tv_fd_id);
         tvFDAmount = dialogFDeposit.findViewById(R.id.tv_fd_amount);
         tvtax = dialogFDeposit.findViewById(R.id.tvtax);
         tvstatus = dialogFDeposit.findViewById(R.id.tvstatus);
         tvPaymentMode = dialogFDeposit.findViewById(R.id.tv_payment_mode);
        tv_contct_support = dialogFDeposit.findViewById(R.id.tv_contct_support);

         operationText = dialogFDeposit.findViewById(R.id.operationText);
         tv_bonus_amount = dialogFDeposit.findViewById(R.id.tv_bonus_amount);
         tv_balance_amount = dialogFDeposit.findViewById(R.id.tv_balance_amount);
         FD_back_btn = dialogFDeposit.findViewById(R.id.FD_back_btn);

         FD_back_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialogFDeposit.dismiss();
             }
         });
        tv_contct_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FixedDepositListActivity.this, ContactUs.class));
                dialogFDeposit.dismiss();
                finish();
            }
        });

         tvFDid.setText(investmentBean.getSafepetransactionId());
         tvFDAmount.setText("₹ " +investmentBean.getTotal_amount());
         tvtax.setText("Invest Credit: " +investmentBean.getBonus_credited());
         tvPaymentMode.setText("Payment Mode: " +investmentBean.getPayment_mode());
         operationText.setText("Invest Amount: ₹" +investmentBean.getPackage_amount());
        tv_bonus_amount.setText("Bonus Amount: ₹" +investmentBean.getBonus_amount());
        tv_balance_amount.setText("Invest Balance Amount: ₹" +investmentBean.getBalance_amount());

        if (investmentBean.getStatus()==0){
            tvstatus.setText("Pending");
            tvstatus.setTextColor(Color.parseColor("#FFBF00"));
        }
        else if (investmentBean.getStatus()==1){
            tvstatus.setText("Approved");
            tvstatus.setTextColor(Color.parseColor("#84DE02"));
        }
        else if (investmentBean.getStatus()==2){
            tvstatus.setText("Failed");
            tvstatus.setTextColor(Color.parseColor("#E32636"));
        }


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogFDeposit.getWindow();
        dialogFDeposit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialogFDeposit.show();
    }
}
