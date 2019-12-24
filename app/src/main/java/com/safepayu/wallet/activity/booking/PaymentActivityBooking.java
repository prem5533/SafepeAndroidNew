package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.WalletLimitResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PaymentActivityBooking  extends AppCompatActivity {

    private TextView tvPayAmount, tvWalletlimit, tvTotalBalanceWallet, tvWalletDeductAmount, tvGatewayDeductAmount;
    private Button btnBookingPayAmount;
    private CheckBox checkBoxWallet, checkBoxGatewayPayment;
    private LoadingDialog loadingDialog;
    WalletLimitResponse WalletResponse;

    //recharge/bill payment parameter
    private String RechargePaymentId="",Amount="11000",PaymentTypeText="",PaymentFor="",RechargeTypeId="",OperatorCode="",CircleCode="",OperatorId="",WalletCashback,TotalDeductAmount;
    private String merchant_trxnId="",merchant_productInfo="",customer_firstName="",customer_email_id="",customer_phone="",address="";
    private String merchant_udf1="",merchant_udf2="",merchant_udf3="",merchant_udf4="",merchant_udf5="";
    private String customer_address1="",customer_address2="",customer_city="",customer_state="",customer_country="",customer_zipcode="";
    private String hash="",payment_mode="",customers_unique_id="";
    private float merchant_payment_amount= (float) 1.0;
    String PaymentMode = "bank",bankAmount="";
    int tFare,walletDeduct, subAmount,BankAmount;
    boolean b = true;
    boolean walletcheck = true;
    String PaymentBank,PaymentWallet,bank_rs,wallet_rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_payment);
        findId();

        if (isNetworkAvailable()){
            walletLimitLeft();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"No Internet Connection",true);
        }

    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);
        tvPayAmount = findViewById(R.id.tv_flight_pay_amount);
        tvWalletlimit = findViewById(R.id.tv_walletlimit);
        tvTotalBalanceWallet = findViewById(R.id.tv_total_balance_wallet);
        tvWalletDeductAmount = findViewById(R.id.tv_wallet_deduct_amount);
        tvGatewayDeductAmount = findViewById(R.id.tv_gateway_deduct_amount);
        btnBookingPayAmount = findViewById(R.id.btn_booking_pay_amount);
        checkBoxWallet = findViewById(R.id.check_box_wallet);
        checkBoxGatewayPayment = findViewById(R.id.check_box_gateway_payment);

        tvPayAmount.setText(getResources().getString(R.string.rupees)+" "+Amount);
        //btnBookingPayAmount.setOnClickListener(this);

        checkBoxWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    tvWalletDeductAmount.setVisibility(View.VISIBLE);
                    tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                    PaymentMode = "wallet";

                    PaymentBank = tvGatewayDeductAmount.getText().toString().trim();
                    String bank[] = PaymentBank.split(" ");
                    String s1 = bank[0];
                    bank_rs = bank[1];
                    Log.e("ban",PaymentBank);
                    PaymentWallet = tvWalletDeductAmount.getText().toString().trim();
                    String wallet[] = PaymentWallet.split(" ");
                    String s2 = wallet[0];
                    wallet_rs = wallet[1];
                    Log.e("wall",PaymentWallet);

                    if (tFare>walletDeduct){
                        subAmount = tFare-walletDeduct;
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(subAmount));
                        tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(WalletResponse.getData().getLimit()));
                        checkBoxWallet.setChecked(true);
                        checkBoxGatewayPayment.setChecked(true);
                        checkBoxGatewayPayment.setEnabled(false);
                        tvGatewayDeductAmount.setVisibility(View.VISIBLE);

                        if (subAmount>Integer.parseInt(WalletResponse.getData().getWallet_balance())){

                            int restAmount=subAmount-Integer.parseInt(WalletResponse.getData().getWallet_balance());

                            btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(WalletResponse.getData().getWallet_balance())));
                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(restAmount));

                        }else {
                            btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(subAmount));

                        }

                    } else if (tFare<walletDeduct){
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(tFare));
                        tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(tFare));
                        btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(tFare));

                        if (b){
                            checkBoxGatewayPayment.setChecked(false);
                            tvGatewayDeductAmount.setVisibility(View.GONE);
                            checkBoxGatewayPayment.setEnabled(false);
                        }
                    }

                 /*   double walletDouble = Double.parseDouble(WalletResponse.getData().getWallet_balance());
                    double totalDouble = Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE));
                    double sub = totalDouble - walletDouble;
                    int IntValue = (int) sub;
                    tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(IntValue));
                    checkBoxGatewayPayment.setChecked(true);
                    checkBoxGatewayPayment.setEnabled(false);

                    if (totalDouble < walletDouble) {
                        checkBoxGatewayPayment.setChecked(false);
                        int totalAmount = (int) totalDouble;
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(totalAmount));
                        tvGatewayDeductAmount.setVisibility(View.GONE);
                        btnBookingPayAmount.setText("Pay");
                        checkBoxGatewayPayment.setEnabled(true);

                    }*/
                } else {

                    PaymentBank = tvGatewayDeductAmount.getText().toString().trim();
                    String bank[] = PaymentBank.split(" ");
                    String s1 = bank[0];
                    bank_rs = bank[1];
                    Log.e("ban",PaymentBank);
                    PaymentWallet = tvWalletDeductAmount.getText().toString().trim();
                    String wallet[] = PaymentWallet.split(" ");
                    String s2 = wallet[0];
                    wallet_rs = wallet[1];
                    Log.e("wall",PaymentWallet);
                    checkBoxWallet.setChecked(false);
                    tvWalletDeductAmount.setVisibility(View.GONE);
                    tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                    checkBoxGatewayPayment.setChecked(true);
                    btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " +  NumberFormat.getIntegerInstance().format(Integer.parseInt(Amount)));
                    tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(Amount)));

                }
            }
        });
    }

    private void walletLimitLeft() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getWalletLimitLeft()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WalletLimitResponse>() {
                    @Override
                    public void onSuccess(WalletLimitResponse walletLimitResponse) {
                        loadingDialog.hideDialog();
                        if (walletLimitResponse.isStatus()) {
                            WalletResponse = walletLimitResponse;

                            if (String.valueOf(walletLimitResponse.getData().getLimit()).equals("0")){
                                tvWalletDeductAmount.setVisibility(View.GONE);
                                checkBoxWallet.setChecked(false);
                            }

                            tvTotalBalanceWallet.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format((int) Double.parseDouble(walletLimitResponse.getData().getWallet_balance())));
                            tFare = Integer.parseInt(Amount);

                            walletDeduct =WalletResponse.getData().getLimit();

                            if (tFare>walletDeduct){
                                int subAmount = tFare-walletDeduct;
                                tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(subAmount));
                                tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(walletLimitResponse.getData().getLimit()));
                                checkBoxWallet.setChecked(true);
                                checkBoxGatewayPayment.setChecked(true);
                                checkBoxGatewayPayment.setEnabled(false);
                                tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                                btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(subAmount));


                            } else if (tFare<walletDeduct){
                                tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(tFare));
                                tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(tFare));
                                btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(tFare));


                            }
                            //    tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(walletLimitResponse.getData().getLimit())));
                            //  tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));

                            tvWalletlimit.setText("* Today's Wallet Limit - Rs. " + new DecimalFormat("##.##").format(walletLimitResponse.getData().getLimit()));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.payment), false, e.getCause());
                    }
                }));

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
