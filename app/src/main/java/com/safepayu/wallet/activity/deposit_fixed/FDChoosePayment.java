package com.safepayu.wallet.activity.deposit_fixed;

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
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.activity.PaymentTypeNew;
import com.safepayu.wallet.adapter.BuyMembershipAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ExceptionLogRequest;
import com.safepayu.wallet.models.request.FDPayRequest;
import com.safepayu.wallet.models.response.PackageListData;
import com.safepayu.wallet.models.response.ServiceChargeResponse;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class FDChoosePayment extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, BuyMembershipAdapter.OnBankItemListener {

    private LoadingDialog loadingDialog;
    private RecyclerView recycleBankList;
    private RadioGroup paymentMode;
    private CardView cardView;
    private String TransactionType = "", BankName = "", Amount = "", ReferId = "", interestRateId = "";
    private Double totalPayableAmount;
    double gst = 0;
    private BuyMembershipAdapter buyMembershipAdapter;
    public static FDPayRequest fixedDepositPayRequest;
    private String DeviceName = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().DEVICE_NAME);
    private String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
    ExceptionLogRequest logRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fdchoose_payment);

        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);

        cardView = findViewById(R.id.bankdetails);
        recycleBankList = findViewById(R.id.recycle_bank_list);

        paymentMode = findViewById(R.id.rgPaymentMode_fdChoosePaymentLayout);

        try {
            Amount = getIntent().getStringExtra("AmountDeposit");
            ReferId = getIntent().getStringExtra("ReferId");
            interestRateId = getIntent().getStringExtra("interestRateId");
        } catch (Exception e) {
            Amount = "0";
            e.printStackTrace();
        }

        findViewById(R.id.proceedBtn_fdChoosePaymentLayout).setOnClickListener(view -> {

            if (TextUtils.isEmpty(Amount) || Amount.equalsIgnoreCase("0")) {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout), "Invalid Amount", false);
            } else {
                if (TransactionType.equalsIgnoreCase("1")) {
                    TransactionType = "3";

                    String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

                    fixedDepositPayRequest = new FDPayRequest();
                    fixedDepositPayRequest.setTransaction_type(TransactionType);
                    fixedDepositPayRequest.setBuy_date(currentDate);
                    fixedDepositPayRequest.setPayment_mode("Payment Gateway");
                    fixedDepositPayRequest.setDocument_attached("");
                    fixedDepositPayRequest.setRefrence_no("");
                    fixedDepositPayRequest.setPaid_to_account("By Admin");
                    fixedDepositPayRequest.setPaid_from_account("");
                    fixedDepositPayRequest.setPackage_amount(String.valueOf(totalPayableAmount));
                    fixedDepositPayRequest.setRefer(ReferId);
                    fixedDepositPayRequest.setAmount(Amount);
                    fixedDepositPayRequest.setInterestRateId(interestRateId);

                    Intent intent;
                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PAYMENT_SCREEN).equals("0")) {
                        intent = new Intent(FDChoosePayment.this, PaymentTypeNew.class);
                    } else {
                        intent = new Intent(FDChoosePayment.this, PaymentType.class);
                    }
                    overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                    intent.putExtra("RechargePaymentId", BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
                    intent.putExtra("Amount", String.valueOf(totalPayableAmount));
                    intent.putExtra("PaymentType", "Deposits");
                    intent.putExtra("PaymentFor", "Fixed");
                    intent.putExtra("RechargeTypeId", "0");
                    intent.putExtra("OperatorCode", "");
                    intent.putExtra("CircleCode", "0");
                    intent.putExtra("OperatorId", "");
                    startActivity(intent);
                    finish();
                } else if (TransactionType.equalsIgnoreCase("2")) {

                    if (TextUtils.isEmpty(BankName)) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout), "Please Select Bank", false);
                    } else {
                        Intent intent = new Intent(FDChoosePayment.this, FDMemberBankAddPackages.class);
                        intent.putExtra("TransactionType", TransactionType);
                        intent.putExtra("PackageID", "");
                        intent.putExtra("BankName", BankName);
                        intent.putExtra("PackageName", "");
                        intent.putExtra("Amount", String.valueOf(totalPayableAmount));
                        intent.putExtra("RealAmount", Amount);
                        intent.putExtra("Activity", "FD");
                        intent.putExtra("ReferId", ReferId);
                        intent.putExtra("interestRateId",interestRateId);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout), "Please Select Payment Option", false);
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

        getServicesCharges();
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

    private void getServicesCharges() {
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getServicesCharges()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ServiceChargeResponse>() {
                    @Override
                    public void onSuccess(ServiceChargeResponse response) {

                        if (response.isStatus()) {
                            for (int i = 0; i < response.getTax().size(); i++) {
                                if (response.getTax().get(i).getTax_id() == 8) {
                                    gst = Double.parseDouble(response.getTax().get(i).getTax_value());

                                    totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(Double.parseDouble(Amount), gst);
                                    DecimalFormat df2 = new DecimalFormat("#.##");
                                    totalPayableAmount = Double.parseDouble(df2.format(totalPayableAmount));
                                    ((TextView) findViewById(R.id.mobile_fdChoosePaymentLayout)).setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
                                    ((TextView) findViewById(R.id.taxText_Investment)).setText(response.getTax().get(i).getTax_name());
                                    ((TextView) findViewById(R.id.tax_fdChoosePaymentLayout)).setText(String.valueOf(gst) + " %");
                                    ((TextView) findViewById(R.id.amount_fdChoosePaymentLayout)).setText(getResources().getString(R.string.currency) + " " + BaseApp.getInstance().commonUtils().decimalFormat(Double.parseDouble(Amount)));
                                    ((TextView) findViewById(R.id.totalAmount_fdChoosePaymentLayout)).setText(getResources().getString(R.string.currency) + " " + BaseApp.getInstance().commonUtils().decimalFormat(totalPayableAmount));
                                    ((TextView) findViewById(R.id.tvTaxDetails_investment)).setText("Additional " + gst + "% " + response.getTax().get(i).getTax_name() + " will be charged from the total amount");
                                }
                            }
                        }
                        getPackages();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logRequest = new ExceptionLogRequest(FDChoosePayment.this, UserId, "FDChoosePayment",
                                e.getMessage(), " 210", "getServiceCharge api ", DeviceName);
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.mobileRechargeLayout), true, e);
                    }
                }));
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
                                buyMembershipAdapter = new BuyMembershipAdapter(getApplicationContext(), response.getBankDetails(), FDChoosePayment.this);
                                recycleBankList.setAdapter(buyMembershipAdapter);
                            } catch (Exception e) {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.fdChoosePaymentLayout), "Something Went Wrong", false);
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
        if (mData != null) {
            BankName = mData.getBankName();
        }
    }
}