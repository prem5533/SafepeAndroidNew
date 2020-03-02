package com.safepayu.wallet.activity.safepe_investment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.MyFixedDepositAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ExceptionLogRequest;
import com.safepayu.wallet.models.response.InvestmentResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class InvestmentDepositListActivity extends AppCompatActivity implements MyFixedDepositAdapter.OnFDSelectListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private MyFixedDepositAdapter myFixedDepositAdapter;
    private Dialog dialogFDeposit;
    private LoadingDialog loadingDialog;
    private Button send_back_btn_fd;
    private LinearLayout fdEmpty;
    private String depositAmount, fdInterest, balanceAmount;
    private InvestmentResponse investmentResponse;
    private TextView tvFDid,tvFDAmount,tvtax,tvstatus,tvPaymentMode,operationText,tv_bonus_amount,tv_balance_amount,tv_contct_support,please_invest_fd,tv_download_pdf;
    private String DeviceName=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().DEVICE_NAME);
    private String UserId=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
    ExceptionLogRequest logRequest;

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
        please_invest_fd = findViewById(R.id.please_invest_fd);
        fdEmpty = findViewById(R.id.fdEmpty);
        loadingDialog = new LoadingDialog(this);

        please_invest_fd.setOnClickListener(this);
        send_back_btn_fd.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            depositAmount = intent.getStringExtra("depositAmount");
            fdInterest = intent.getStringExtra("fdInterest");
            balanceAmount = intent.getStringExtra("balanceAmount");
        }

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

                            investmentResponse = response;
                            if (response.getData().getInvestment().isEmpty()){
                                fdEmpty.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                            else {
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                myFixedDepositAdapter = new MyFixedDepositAdapter(getApplicationContext(),response.getData().getInvestment(), InvestmentDepositListActivity.this);
                                recyclerView.setAdapter(myFixedDepositAdapter);
                                fdEmpty.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        logRequest = new ExceptionLogRequest(InvestmentDepositListActivity.this,UserId,"InvestmentDepositListActivity",e.getMessage()," 105","getInvestmentlist api ",DeviceName);
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.fixedLayout), false, e.getCause());
                    }
                }));
    }


    @Override
    public void onFDItemSelect(int position, InvestmentResponse.DataBean.InvestmentBean investmentBean) {
      //  dialogFDeposit(InvestmentDepositListActivity.this, position,  investmentBean);
        Intent intent = new Intent(InvestmentDepositListActivity.this, InvestmentDetailActivity.class);
        intent.putExtra("SafepetransactionId",investmentBean.getSafepetransactionId());
        intent.putExtra("Total_amount",String.valueOf(investmentBean.getTotal_amount()));
        intent.putExtra("Bonus_credited",String.valueOf(investmentBean.getBonus_credited()));
        intent.putExtra("Payment_mode",investmentBean.getPayment_mode());
        intent.putExtra("Package_amount",String.valueOf(investmentBean.getPackage_amount()));
        intent.putExtra("Bonus_amount",String.valueOf(investmentBean.getBonus_amount()));
        intent.putExtra("Balance_amount",String.valueOf(investmentBean.getBalance_amount()));
        intent.putExtra("status",String.valueOf(investmentBean.getStatus()));
        intent.putExtra("id",String.valueOf(investmentBean.getId()));
        startActivity(intent);
    }

  /*  private void dialogFDeposit(InvestmentDepositListActivity fixedDepositListActivity, int position, InvestmentResponse.DataBean.InvestmentBean investmentBean) {
        dialogFDeposit = new Dialog(fixedDepositListActivity);
        dialogFDeposit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFDeposit.setContentView(R.layout.fixed_deposit_detail);

         tvFDid = dialogFDeposit.findViewById(R.id.tv_fd_id);
         tvFDAmount = dialogFDeposit.findViewById(R.id.tv_fd_amount);
         tvtax = dialogFDeposit.findViewById(R.id.tvtax);
         tvstatus = dialogFDeposit.findViewById(R.id.tvstatus);
         tvPaymentMode = dialogFDeposit.findViewById(R.id.tv_payment_mode);
         tv_contct_support = dialogFDeposit.findViewById(R.id.tv_contct_support);
         tv_download_pdf = dialogFDeposit.findViewById(R.id.tv_download_pdf);

         operationText = dialogFDeposit.findViewById(R.id.operationText);
         tv_bonus_amount = dialogFDeposit.findViewById(R.id.tv_bonus_amount);
         tv_balance_amount = dialogFDeposit.findViewById(R.id.tv_balance_amount);
        // FD_back_btn = dialogFDeposit.findViewById(R.id.FD_back_btn);

       *//*  FD_back_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialogFDeposit.dismiss();
             }
         });
        tv_contct_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InvestmentDepositListActivity.this, ContactUs.class));
                dialogFDeposit.dismiss();
                finish();
            }
        });
        tv_download_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(InvestmentDepositListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    MarshMallowPermission.requestStoragePermission(getApplicationContext());
                    getFixedDepositPdf(investmentBean);


                }
                else {
                    ActivityCompat.requestPermissions(InvestmentDepositListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 100);
                    getFixedDepositPdf(investmentBean);
                }
            }
        });*//*

    *//*     tvFDid.setText(investmentBean.getSafepetransactionId());
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

*//*
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
*/



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_back_btn_fd:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.please_invest_fd:
                startActivity(new Intent(InvestmentDepositListActivity.this, CreateInvestmentDepositActivity.class).
                        putExtra("depositAmount", depositAmount).
                        putExtra("fdInterest", fdInterest).putExtra("balanceAmount", balanceAmount));
                break;
        }
    }

  /*  private void getFixedDepositPdf(InvestmentResponse.DataBean.InvestmentBean investmentBean) {

        String id = String.valueOf(investmentBean.getId());

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(InvestmentDepositListActivity.this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFixedPDF(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlighPdfResponse>(){
                    @Override
                    public void onSuccess(FlighPdfResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            new DownloadTask(InvestmentDepositListActivity.this, response.getData());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_book_detail), false, e.getCause());
                    }
                }));
    }*/

}
