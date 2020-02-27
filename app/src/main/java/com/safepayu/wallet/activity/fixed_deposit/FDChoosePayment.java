package com.safepayu.wallet.activity.fixed_deposit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.MemberBankAddPackages;
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.activity.PaymentTypeNew;
import com.safepayu.wallet.adapter.BuyMembershipAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.FDPayRequest;
import com.safepayu.wallet.models.response.PackageListData;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class FDChoosePayment extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,BuyMembershipAdapter.OnBankItemListener{

    private LoadingDialog loadingDialog;
    private RecyclerView recycleBankList;
    private RadioGroup paymentMode;
    private CardView cardView;
    private String TransactionType="",BankName="",Amount="",ReferId="";
    private Double totalPayableAmount;
    private BuyMembershipAdapter buyMembershipAdapter;
    public static FDPayRequest fdPayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fd_choose_payment);

        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);

        cardView = findViewById(R.id.bankdetails);
        recycleBankList = findViewById(R.id.recycle_bank_list);

        paymentMode = findViewById(R.id.rgPaymentMode_fdChoosePaymentLayout);

        try {
            Amount=getIntent().getStringExtra("AmountDeposit");
            ReferId=getIntent().getStringExtra("ReferId");
        }catch (Exception e){
            Amount="0";
            e.printStackTrace();
        }

        findViewById(R.id.proceedBtn_fdChoosePaymentLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(Amount) || Amount.equalsIgnoreCase("0")){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout), "Invalid Amount", false);
                }else {
                    if (TransactionType.equalsIgnoreCase("1")){
                        TransactionType="3";

                        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

                        fdPayRequest=new FDPayRequest();
                        fdPayRequest.setTransaction_type(TransactionType);
                        fdPayRequest.setBuy_date(currentDate);
                        fdPayRequest.setPayment_mode("Payment Gateway");
                        fdPayRequest.setDocument_attached("");
                        fdPayRequest.setRefrence_no("");
                        fdPayRequest.setPaid_to_account("By Admin");
                        fdPayRequest.setPaid_from_account("");
                        fdPayRequest.setPackage_amount(String.valueOf(totalPayableAmount));
                        fdPayRequest.setRefer(ReferId);
                        fdPayRequest.setAmount(Amount);

                        Intent intent;
                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PAYMENT_SCREEN).equals("0")) {
                            intent = new Intent(FDChoosePayment.this, PaymentTypeNew.class);
                        }else {
                            intent = new Intent(FDChoosePayment.this, PaymentType.class);
                        }
                        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                        intent.putExtra("RechargePaymentId",BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
                        intent.putExtra("Amount",String.valueOf(totalPayableAmount));
                        intent.putExtra("PaymentType","Investment");
                        intent.putExtra("PaymentFor","SafePe");
                        intent.putExtra("RechargeTypeId","0");
                        intent.putExtra("OperatorCode","");
                        intent.putExtra("CircleCode","0");
                        intent.putExtra("OperatorId","");
                        startActivity(intent);
                        finish();
                    }else if (TransactionType.equalsIgnoreCase("2")) {

                        if (TextUtils.isEmpty(BankName)){
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout), "Please Select Bank", false);
                        }else {
                            Intent intent=new Intent(FDChoosePayment.this, MemberBankAddPackages.class);
                            intent.putExtra("TransactionType",TransactionType);
                            intent.putExtra("PackageID","");
                            intent.putExtra("BankName",BankName);
                            intent.putExtra("PackageName","");
                            intent.putExtra("Amount",String.valueOf(totalPayableAmount));
                            intent.putExtra("RealAmount",Amount);
                            intent.putExtra("Activity","FD");
                            intent.putExtra("ReferId",ReferId);
                            startActivity(intent);
                            finish();
                        }

                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout), "Please Select Payment Option", false);
                    }
                }


            }
        });

        paymentMode.setOnCheckedChangeListener(this);
        findViewById(R.id.backbtn_fdChoosePaymentLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        double gst=Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().GST));
        totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(Double.parseDouble(Amount),gst );
        DecimalFormat df2 = new DecimalFormat("#.##");
        totalPayableAmount= Double.parseDouble(df2.format(totalPayableAmount));
        ((TextView) findViewById(R.id.mobile_fdChoosePaymentLayout)).setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        ((TextView) findViewById(R.id.tax_fdChoosePaymentLayout)).setText(gst+" %");
        ((TextView) findViewById(R.id.amount_fdChoosePaymentLayout)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(Double.parseDouble(Amount)));
        ((TextView) findViewById(R.id.totalAmount_fdChoosePaymentLayout)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(totalPayableAmount));
        getPackages();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.walletRadio_fdChoosePaymentLayout:

                cardView.setVisibility(View.GONE);
                recycleBankList.setVisibility(View.GONE);

                TransactionType = "1";

                break;

            case R.id.bankRadio_fdChoosePaymentLayout:
                cardView.setVisibility(VISIBLE);
                recycleBankList.setVisibility(VISIBLE);
                TransactionType = "2";
                break;
        }
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

                            try {
                                recycleBankList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                buyMembershipAdapter = new BuyMembershipAdapter(getApplicationContext(),response.getBankDetails(), FDChoosePayment.this);
                                recycleBankList.setAdapter(buyMembershipAdapter);
                            }catch (Exception e){
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout),"Something Went Wrong",false);
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.fdChoosePaymentLayout), true, e);
                    }
                }));
    }

    @Override
    public void onBankItemListerner(int position, PackageListData.BankDetails mData) {
        if (mData!=null){
            BankName=mData.getBankName();
        }
    }
}
