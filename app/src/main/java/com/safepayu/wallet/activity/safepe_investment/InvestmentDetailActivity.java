package com.safepayu.wallet.activity.safepe_investment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.ContactUs;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.booking.flight.FlighPdfResponse;
import com.safepayu.wallet.utils.pdf.DownloadTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class InvestmentDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvFDid,tvFDAmount,tvtax,tvstatus,tvPaymentMode,operationText,tv_bonus_amount,tv_balance_amount,tv_contct_support,please_invest_fd,tv_download_pdf;
    private Button FD_back_btn;
    private String safeTransactionId,totalAmount,BonusCredit,PaymentMode,packageAmount,BonusAmount, BalanceAmount,Status,id;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fddetail);

        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);
        Intent intent = getIntent();
        safeTransactionId = intent.getStringExtra("SafepetransactionId");
        totalAmount = intent.getStringExtra("Total_amount");
        BonusCredit = intent.getStringExtra("Bonus_credited");
        PaymentMode = intent.getStringExtra("Payment_mode");
        packageAmount = intent.getStringExtra("Package_amount");
        BonusAmount = intent.getStringExtra("Bonus_amount");
        BalanceAmount = intent.getStringExtra("Balance_amount");
        Status = intent.getStringExtra("status");
        id = intent.getStringExtra("id");

        tvFDid = findViewById(R.id.tv_fd_id);
        tvFDAmount = findViewById(R.id.tv_fd_amount);
        tvtax = findViewById(R.id.tvtax);
        tvstatus = findViewById(R.id.tvstatus);
        tvPaymentMode = findViewById(R.id.tv_payment_mode);
        tv_contct_support = findViewById(R.id.tv_contct_support);
        tv_download_pdf = findViewById(R.id.tv_download_pdf);

        operationText = findViewById(R.id.operationText);
        tv_bonus_amount = findViewById(R.id.tv_bonus_amount);
        tv_balance_amount = findViewById(R.id.tv_balance_amount);
        FD_back_btn = findViewById(R.id.FD_back_btn);

        FD_back_btn.setOnClickListener(this);
        tv_contct_support.setOnClickListener(this);
        tv_download_pdf.setOnClickListener(this);

        tvFDid.setText(safeTransactionId);
        tvFDAmount.setText("₹ " +totalAmount);
        tvtax.setText("Invest Credit: " +BonusCredit);
        tvPaymentMode.setText("Payment Mode: " +PaymentMode);
        operationText.setText("Invest Amount: ₹" +packageAmount);
        tv_bonus_amount.setText("Bonus Amount: ₹" +BonusAmount);
        tv_balance_amount.setText("Invest Balance Amount: ₹" +BalanceAmount);


        if (Integer.parseInt(Status)==0){
            tvstatus.setText("Pending");
            tvstatus.setTextColor(Color.parseColor("#FFBF00"));
        }
        else if (Integer.parseInt(Status)==1){
            tvstatus.setText("Approved");
            tvstatus.setTextColor(Color.parseColor("#84DE02"));
        }
        else if (Integer.parseInt(Status)==2){
            tvstatus.setText("Failed");
            tvstatus.setTextColor(Color.parseColor("#E32636"));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.FD_back_btn:
                finish();
                break;
            case R.id.tv_contct_support:
                startActivity(new Intent(InvestmentDetailActivity.this, ContactUs.class));
                finish();
                break;
            case R.id.tv_download_pdf:
                try {
//                    if (ContextCompat.checkSelfPermission(InvestmentDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        MarshMallowPermission.requestStoragePermission(InvestmentDetailActivity.this);
//                        getFixedDepositPdf();
//
//
//                    } else {
//                        ActivityCompat.requestPermissions(InvestmentDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        }, 100);
//                        getFixedDepositPdf();
//                    }
                    Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Could't downlad the PDF",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private void getFixedDepositPdf() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(InvestmentDetailActivity.this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFixedPDF(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlighPdfResponse>(){
                    @Override
                    public void onSuccess(FlighPdfResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            new DownloadTask(InvestmentDetailActivity.this, response.getData());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.fd_detail), false, e.getCause());
                    }
                }));
    }
}
