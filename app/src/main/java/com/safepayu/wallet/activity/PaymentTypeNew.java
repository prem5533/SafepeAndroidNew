package com.safepayu.wallet.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.HashKeyRequest;
import com.safepayu.wallet.models.request.RechargeRequest;
import com.safepayu.wallet.models.request.SendPaymentGatewayDetailsRequest;
import com.safepayu.wallet.models.response.BuyPackageResponse;
import com.safepayu.wallet.models.response.HashKeyResponse;
import com.safepayu.wallet.models.response.RechargeResponse;
import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
import com.safepayu.wallet.models.response.WalletLimitResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import datamodels.StaticDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static com.safepayu.wallet.activity.BuyMemberShip.buyPackageFromDB;
import static com.safepayu.wallet.activity.LoginActivity.finalAmount;
import static com.safepayu.wallet.activity.recharge.LandlineBillPay.OptionValue2;
import static com.safepayu.wallet.activity.recharge.LandlineBillPay.StdCode;
import static com.safepayu.wallet.activity.safepe_investment.InvestmentChoosePayment.fdPayRequest;

public class PaymentTypeNew extends AppCompatActivity implements PasscodeClickListener {

    private TextView tvPayAmount, tvWalletlimit, tvTotalBalanceWallet, tvWalletDeductAmount, tvGatewayDeductAmount,tvGatewayText;
    private Button btnBookingPayAmount,sendmoneyBackBtn;
    private CheckBox checkBoxWallet, checkBoxGatewayPayment;
    private LoadingDialog loadingDialog;
    WalletLimitResponse WalletResponse;

    double amount=0,walletBal=0,walletLimit=0,balAmount;
    boolean checkWalletBal=false,checkWalletLimit=false;
    private ImageView imageService;

    //recharge/bill payment parameter
    private String RechargePaymentId="",customer_phone="",Amount="",PaymentTypeText="",PaymentFor="",RechargeTypeId="",OperatorCode="",CircleCode="",OperatorId="",WalletCashback,TotalDeductAmount;

    private String merchant_udf1="",merchant_udf2="",merchant_udf3="",merchant_udf4="",merchant_udf5="";
    private String customer_address1="",customer_address2="",customer_city="",customer_state="",customer_country="",customer_zipcode="";
    private String hash="",payment_mode="",customers_unique_id="";
    private float merchant_payment_amount= (float) 1.0;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private LinearLayout linearLayoutPayment,linearLayoutWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_payment);
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
        tvGatewayText = findViewById(R.id.tv_gateway_deduct_Text);
        btnBookingPayAmount = findViewById(R.id.btn_booking_pay_amount);
        checkBoxWallet = findViewById(R.id.check_box_wallet);
        checkBoxGatewayPayment = findViewById(R.id.check_box_gateway_payment);
        sendmoneyBackBtn = findViewById(R.id.sendmoney_back_btn);
        imageService=findViewById(R.id.image_serviceNew);
        linearLayoutPayment=findViewById(R.id.paymentLayoutService);
        linearLayoutWallet=findViewById(R.id.walletLayoutService);
        
        sendmoneyBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        try{
            Intent intent=getIntent();
            PaymentTypeText=intent.getStringExtra("PaymentType");
            RechargePaymentId=intent.getStringExtra("RechargePaymentId");
            Amount=intent.getStringExtra("Amount");
            PaymentFor=intent.getStringExtra("PaymentFor");
            RechargeTypeId=intent.getStringExtra("RechargeTypeId");
            OperatorCode=intent.getStringExtra("OperatorCode");
            CircleCode=intent.getStringExtra("CircleCode");
            OperatorId=intent.getStringExtra("OperatorId");
            WalletCashback=intent.getStringExtra("walletCashback");
            TotalDeductAmount=intent.getStringExtra("totalAmount");

            if (PaymentFor.equalsIgnoreCase("Wallet") || PaymentFor.equalsIgnoreCase("Buy Package")
                                || PaymentFor.equalsIgnoreCase("Fixed")) {
                checkBoxWallet.setChecked(false);
                checkBoxWallet.setClickable(false);
                checkBoxWallet.setEnabled(false);
                linearLayoutPayment.setVisibility(View.VISIBLE);
                linearLayoutWallet.setVisibility(GONE);
                checkBoxGatewayPayment.setChecked(true);
                checkBoxGatewayPayment.setClickable(false);
                checkBoxGatewayPayment.setEnabled(false);
            }else {
                checkBoxGatewayPayment.setChecked(false);
                linearLayoutWallet.setVisibility(View.VISIBLE);
                linearLayoutPayment.setVisibility(GONE);
                checkBoxWallet.setChecked(true);
                checkBoxWallet.setClickable(false);
                checkBoxWallet.setEnabled(false);
                checkBoxGatewayPayment.setChecked(false);
                checkBoxGatewayPayment.setClickable(false);
                checkBoxGatewayPayment.setEnabled(false);
            }

            if (RechargeTypeId.equals("1")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_mobile_login));
            }else if (RechargeTypeId.equals("2")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_tv));
            } else if (RechargeTypeId.equals("3")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_flash));
            } else if (RechargeTypeId.equals("4")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_drop));
            } else if (RechargeTypeId.equals("5")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_fire));
            } else if (RechargeTypeId.equals("6")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_receipt));
            }else if (RechargeTypeId.equals("7")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_receipt));
            }else if (RechargeTypeId.equals("8")){
                imageService.setImageDrawable(getResources().getDrawable(R.drawable.ic_metro));
            }else {
                imageService.setVisibility(GONE);
            }

            try {
                finalAmount= Double.parseDouble(df2.format(finalAmount));
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        tvPayAmount.setText(getResources().getString(R.string.rupees)+" "+Double.parseDouble(Amount));

        checkBoxGatewayPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    checkBoxWallet.setChecked(false);
                    tvWalletDeductAmount.setVisibility(View.GONE);
                    tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                    tvGatewayText.setVisibility(View.VISIBLE);
                    if (PaymentFor.equalsIgnoreCase("Buy Package")  || PaymentFor.equalsIgnoreCase("Fixed")){
                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    }else {
                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " +Float.parseFloat(String.valueOf(finalAmount)));
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +Float.parseFloat(String.valueOf(finalAmount)));
                    }
                }else {
                    if (!PaymentFor.equalsIgnoreCase("Buy Package")  || !PaymentFor.equalsIgnoreCase("Fixed")){
                        checkBoxWallet.setChecked(true);
                        checkBoxGatewayPayment.setChecked(false);
                        tvWalletDeductAmount.setVisibility(View.VISIBLE);
                        tvGatewayDeductAmount.setVisibility(View.GONE);
                        tvGatewayText.setVisibility(GONE);
                        tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    }
                }
            }
        });

        checkBoxWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    try {
//                        if (walletBal!=0 && walletBal<finalAmount){
//                            balAmount=finalAmount-walletBal;
//                            checkBoxWallet.setChecked(true);
//                            checkBoxGatewayPayment.setChecked(true);
//                            tvWalletDeductAmount.setVisibility(View.VISIBLE);
//                            tvGatewayDeductAmount.setVisibility(View.VISIBLE);
//                            tvGatewayText.setVisibility(View.VISIBLE);
//                            tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + walletBal);
//                            btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + balAmount);
//                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + balAmount);
//                        }else {
//                            checkBoxWallet.setChecked(true);
//                            checkBoxGatewayPayment.setChecked(false);
//                            tvWalletDeductAmount.setVisibility(View.VISIBLE);
//                            tvGatewayDeductAmount.setVisibility(View.GONE);
//                            tvGatewayText.setVisibility(GONE);
//                            tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
//                            btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
//                        }

                        checkBoxWallet.setChecked(true);
                        checkBoxGatewayPayment.setChecked(false);
                        tvWalletDeductAmount.setVisibility(View.VISIBLE);
                        tvGatewayDeductAmount.setVisibility(View.GONE);
                        tvGatewayText.setVisibility(GONE);
                        tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {

//                    checkBoxWallet.setChecked(false);
//                    tvWalletDeductAmount.setVisibility(View.GONE);
//                    tvGatewayDeductAmount.setVisibility(View.VISIBLE);
//                    tvGatewayText.setVisibility(View.VISIBLE);
//                    if (PaymentFor.equalsIgnoreCase("Buy Package")){
//                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
//                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
//                    }else {
//                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " +Float.parseFloat(String.valueOf(finalAmount)));
//                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +Float.parseFloat(String.valueOf(finalAmount)));
//                    }
                }
            }
        });

        btnBookingPayAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxGatewayPayment.isChecked() && checkBoxWallet.isChecked()){
                    HashKeyRequest hashKeyRequest=new HashKeyRequest();
                    hashKeyRequest.setCustomer_firstName(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME));
                    hashKeyRequest.setMerchant_productInfo(PaymentFor+""+PaymentTypeText);
                    hashKeyRequest.setCustomer_email_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));
                    hashKeyRequest.setType("0");
                    if (PaymentFor.equalsIgnoreCase("Buy Package") || PaymentFor.equalsIgnoreCase("Fixed")){
                        hashKeyRequest.setMerchant_payment_amount(Amount);
                    }else {
                        hashKeyRequest.setMerchant_payment_amount(""+Float.parseFloat(String.valueOf(balAmount)));
                    }

                    if (isNetworkAvailable()){
                        getHashKey(hashKeyRequest);
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Please Check Your Internet Connection",false);
                    }
                }else if (checkBoxWallet.isChecked()){

                    if (checkWalletBal){
                        if (checkWalletLimit){
                            PasscodeDialog passcodeDialog = new PasscodeDialog(PaymentTypeNew.this, PaymentTypeNew.this, "");
                            passcodeDialog.show();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Wallet Limit Is Exceeded",true);
                        }
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Wallet Balance Is Low",true);
                    }
                }else if (checkBoxGatewayPayment.isChecked()){
                    HashKeyRequest hashKeyRequest=new HashKeyRequest();
                    hashKeyRequest.setCustomer_firstName(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME));
                    hashKeyRequest.setMerchant_productInfo(PaymentFor+""+PaymentTypeText);
                    hashKeyRequest.setCustomer_email_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));
                    hashKeyRequest.setType("0");
                    if (PaymentFor.equalsIgnoreCase("Buy Package") || PaymentFor.equalsIgnoreCase("Fixed")){
                        hashKeyRequest.setMerchant_payment_amount(Amount);
                    }else {
                        hashKeyRequest.setMerchant_payment_amount(""+Float.parseFloat(String.valueOf(finalAmount)));
                    }

                    if (isNetworkAvailable()){
                        getHashKey(hashKeyRequest);
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Please Check Your Internet Connection",false);
                    }
                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Please Select Payment Option",false);
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

                            try {
                                walletBal= Double.parseDouble(WalletResponse.getData().getWallet_balance());
                                amount=Double.parseDouble(Amount);
                                walletLimit= WalletResponse.getData().getLimit();

                                tvTotalBalanceWallet.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format((int) Double.parseDouble(walletLimitResponse.getData().getWallet_balance())));
                                tvWalletlimit.setText("* Today's Wallet Limit - Rs. " + new DecimalFormat("##.##").format(walletLimitResponse.getData().getLimit()));

                                if (PaymentFor.equalsIgnoreCase("Buy Package") || PaymentFor.equalsIgnoreCase("Fixed")){
                                    if (amount>walletBal){
                                        checkWalletBal=false;
                                        checkBoxWallet.setChecked(false);
                                        checkBoxGatewayPayment.setChecked(true);

                                        tvWalletDeductAmount.setVisibility(View.GONE);
                                        tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                                        tvGatewayText.setVisibility(View.VISIBLE);
                                        if (PaymentFor.equalsIgnoreCase("Buy Package") || PaymentFor.equalsIgnoreCase("Fixed")){
                                            btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                        }else {
                                            btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Float.parseFloat(String.valueOf(finalAmount)));
                                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Float.parseFloat(String.valueOf(finalAmount)));
                                        }
                                    }else {
                                        if (amount>walletLimit){
                                            checkWalletLimit=false;
                                            checkBoxWallet.setChecked(false);
                                            checkBoxGatewayPayment.setChecked(true);

                                            tvWalletDeductAmount.setVisibility(View.GONE);
                                            tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                                            tvGatewayText.setVisibility(View.VISIBLE);
                                            if (PaymentFor.equalsIgnoreCase("Buy Package") || PaymentFor.equalsIgnoreCase("Fixed")){
                                                btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                                tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                            }else {
                                                btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Float.parseFloat(String.valueOf(finalAmount)));
                                                tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Float.parseFloat(String.valueOf(finalAmount)));
                                            }
                                        }else {
                                            checkWalletBal=true;
                                            checkWalletLimit=true;
                                            checkBoxWallet.setChecked(true);
                                            checkBoxGatewayPayment.setChecked(false);

                                            tvGatewayDeductAmount.setVisibility(View.GONE);
                                            tvGatewayText.setVisibility(GONE);
                                            tvWalletDeductAmount.setVisibility(View.VISIBLE);
                                            tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                            btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                        }
                                    }
                                }else {
                                    if (amount>walletBal){
                                        checkWalletBal=false;
                                    }else {
                                        if (amount>walletLimit){
                                            checkWalletLimit=false;
                                        }else {
                                            checkWalletBal=true;
                                            checkWalletLimit=true;
                                        }
                                    }
                                    checkBoxGatewayPayment.setChecked(false);
                                    tvWalletDeductAmount.setVisibility(View.VISIBLE);
                                    tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                    btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                }
                            }catch (Exception e){
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),e.getMessage(),true);
                                e.printStackTrace();
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),walletLimitResponse.getMessage(),false);
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

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched){
            if (PaymentFor.equalsIgnoreCase("Buy Package") || PaymentFor.equalsIgnoreCase("Fixed")){
                //BuyPackageMethod("","");
            }else {
                RechargeRequest rechargeRequest=new RechargeRequest();
                rechargeRequest.setAmount(Amount);
                rechargeRequest.setCircle_code(CircleCode);
                rechargeRequest.setNumber(RechargePaymentId);
                rechargeRequest.setOperator_code(OperatorCode);
                rechargeRequest.setRecharge_type(RechargeTypeId);
                rechargeRequest.setOperator_id(RechargeTypeId);
                rechargeRequest.setTransaction_id("");
                rechargeRequest.setNumber_type("");
                rechargeRequest.setDescription(PaymentFor+" "+PaymentTypeText);

                if (RechargeTypeId.equals("7")){
                    rechargeRequest.setStdCode(StdCode);
                    rechargeRequest.setOpvalue2(OptionValue2);
                }else {
                    rechargeRequest.setStdCode("");
                    rechargeRequest.setOpvalue2("");
                }

                try {
                    if (checkBoxGatewayPayment.isChecked() && checkBoxWallet.isChecked()){
                        rechargeRequest.setPayment_mode("both");
                        rechargeRequest.setWallet_amount(""+walletBal);
                        rechargeRequest.setBank_amount(""+balAmount);
                    }else {
                        rechargeRequest.setPayment_mode("wallet");
                        rechargeRequest.setWallet_amount("");
                        rechargeRequest.setBank_amount("");
                    }
                }catch (Exception e){
                    rechargeRequest.setPayment_mode("wallet");
                    e.printStackTrace();
                }

                doRecharge(rechargeRequest);
            }

        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Invalid Passcode",false);
        }
    }

    private void doRecharge(final RechargeRequest rechargeRequest){
        final Intent intentRecharge=new Intent(PaymentTypeNew.this,PaidOrderActivity.class);
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.doRecharge(rechargeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RechargeResponse>() {
                    @Override
                    public void onSuccess(RechargeResponse response) {
                        loadingDialog.hideDialog();
                        String date=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        if (response.isStatus()) {

                            if (response.getStatusCode()==0){
                                intentRecharge.putExtra("status","pending");
                            }else {
                                intentRecharge.putExtra("status","success");
                            }
                            try{
                                intentRecharge.putExtra("Amount",Amount);
                                intentRecharge.putExtra("date",date);
                                intentRecharge.putExtra("productinfo",PaymentFor+" "+PaymentTypeText);
                                intentRecharge.putExtra("txnid",rechargeRequest.getTransaction_id());
                                intentRecharge.putExtra("Message",response.getMessage());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            startActivity(intentRecharge);
                            finish();
                            // Toast.makeText(PaymentTypeNew.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {

                            if (response.getStatusCode()==2){
                                intentRecharge.putExtra("status","failed");
                                try{
                                    intentRecharge.putExtra("Amount",Amount);
                                    intentRecharge.putExtra("date",date);
                                    intentRecharge.putExtra("productinfo",PaymentFor+" "+PaymentTypeText);
                                    intentRecharge.putExtra("txnid",rechargeRequest.getTransaction_id());
                                    intentRecharge.putExtra("Message",response.getMessage());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                startActivity(intentRecharge);
                                finish();
                            }else {
                                showMessage(response.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.payment), true, e);
                    }
                }));

    }

    private void BuyPackageMethod(final String txnid,final String easepayid){
        buyPackageFromDB.setRefrence_no(easepayid);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.buyPackage(buyPackageFromDB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BuyPackageResponse>() {
                    @Override
                    public void onSuccess(BuyPackageResponse response) {
                        loadingDialog.hideDialog();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
                        Intent intentStatus=new Intent(PaymentTypeNew.this,PaidOrderActivity.class);
                        if (response.isStatus()) {
                            intentStatus.putExtra("status","success");
                        }else {
                            intentStatus.putExtra("status","failed");
                            Toast.makeText(PaymentTypeNew.this, response.getMessage(), Toast.LENGTH_LONG).show();

                        }
                        intentStatus.putExtra("txnid",txnid);
                        intentStatus.putExtra("Amount",Amount);
                        intentStatus.putExtra("Message",response.getMessage());
                        intentStatus.putExtra("productinfo",PaymentFor+" "+PaymentTypeText);

                        intentStatus.putExtra("toAccount",response.getData().getPaid_to_account());
                        intentStatus.putExtra("fromAccount",response.getData().getPaid_from_account());
                        intentStatus.putExtra("RefNo",response.getData().getRefrence_no());
                        intentStatus.putExtra("date",response.getData().getCreated_at());
                        intentStatus.putExtra("PayMode",response.getData().getPayment_mode());
                        intentStatus.putExtra("Note",response.getData().getFootnote());
                        startActivity(intentStatus);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.payment), true, e);
                    }
                }));

    }

    private void PayFixedDeposit(final String txnid, final String easepayid){
        fdPayRequest.setRefrence_no(easepayid);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.saveInvestment(fdPayRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BuyPackageResponse>() {
                    @Override
                    public void onSuccess(BuyPackageResponse response) {
                        loadingDialog.hideDialog();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
                        Intent intentStatus=new Intent(PaymentTypeNew.this,PaidOrderActivity.class);
                        if (response.isStatus()) {
                            intentStatus.putExtra("status","success");
                        }else {
                            intentStatus.putExtra("status","failed");
                            Toast.makeText(PaymentTypeNew.this, response.getMessage(), Toast.LENGTH_LONG).show();

                        }
                        intentStatus.putExtra("txnid",txnid);
                        intentStatus.putExtra("Amount",Amount);
                        intentStatus.putExtra("Message",response.getMessage());
                        intentStatus.putExtra("productinfo",PaymentFor+" "+PaymentTypeText);

                        intentStatus.putExtra("toAccount",response.getData().getPaid_to_account());
                        intentStatus.putExtra("fromAccount",response.getData().getPaid_from_account());
                        intentStatus.putExtra("RefNo",response.getData().getRefrence_no());
                        intentStatus.putExtra("date",response.getData().getCreated_at());
                        intentStatus.putExtra("PayMode",response.getData().getPayment_mode());
                        intentStatus.putExtra("Note",response.getData().getFootnote());
                        startActivity(intentStatus);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.payment), true, e);
                    }
                }));

    }

    public void showMessage(String Message) {
        new AlertDialog.Builder(PaymentTypeNew.this)
                .setTitle("SafePe Alert")
                .setMessage(Message)
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        dialog.dismiss();
                        finish();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }
    
    private void getHashKey(final HashKeyRequest hashKeyRequest){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getHashKey(hashKeyRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HashKeyResponse>() {
                    @Override
                    public void onSuccess(HashKeyResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            DoPayment(hashKeyRequest,response);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.busSeatLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.busSeatLayout), true, e);
                    }
                }));
    }

    public String hashCal(String type,String str){
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance(type);
            byte[] digest = md.digest(str.getBytes());

            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch(NoSuchAlgorithmException nsae){ }
        return hexString.toString();
    }

    private void DoPayment(HashKeyRequest hashKeyRequest, HashKeyResponse hashKeyResponse){
        //key|txnid|amount|productinfo|firstname|email_id|udf1|udf2|udf3|udf4|udf5||||||salt|key

        customer_phone = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
        payment_mode="production";//test production
        customer_address2="";
        customer_city="";
        customer_state="";
        customer_country="";
        customer_zipcode="";
        merchant_udf1 = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
        merchant_udf2="udf2";
        merchant_udf3="udf3";
        merchant_udf4="udf4";
        merchant_udf5="udf5";

        if (TextUtils.isEmpty(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ADDRESS)) ||
                BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ADDRESS)==null){
            customer_address1=" ";
        }else {
            customer_address1=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ADDRESS);
        }
        //merchant_payment_amount = Float.parseFloat(Amount);

        if (PaymentFor.equalsIgnoreCase("Buy Package") || PaymentFor.equalsIgnoreCase("Fixed")){
            merchant_payment_amount = Float.parseFloat(Amount);
        }else {
            if (checkBoxGatewayPayment.isChecked() && checkBoxWallet.isChecked()){
                merchant_payment_amount = Float.parseFloat(String.valueOf(balAmount));
            }else {
                merchant_payment_amount = Float.parseFloat(String.valueOf(finalAmount));
            }
        }

        hash = hashKeyResponse.getMerchant_key() + "|" + hashKeyResponse.getTransactionId() + "|" + merchant_payment_amount + "|" + hashKeyRequest.getMerchant_productInfo()
                + "|" + hashKeyRequest.getCustomer_firstName()
                + "|" + hashKeyRequest.getCustomer_email_id() + "|" + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|" + merchant_udf5
                + "||||||"
                + hashKeyResponse.getMerchant_salt() + "|" + hashKeyResponse.getMerchant_key();

        String hashKey = hashCal("SHA-512", hash);
        merchant_payment_amount= Float.parseFloat(hashKeyRequest.getMerchant_payment_amount());

        Intent intentProceed = new Intent(PaymentTypeNew.this, PWECouponsActivity.class);
        intentProceed.putExtra("txnid",hashKeyResponse.getTransactionId());
        intentProceed.putExtra("amount",merchant_payment_amount);
        intentProceed.putExtra("productinfo",hashKeyRequest.getMerchant_productInfo());
        intentProceed.putExtra("firstname",hashKeyRequest.getCustomer_firstName());
        intentProceed.putExtra("email",hashKeyRequest.getCustomer_email_id());
        intentProceed.putExtra("phone",customer_phone);
        intentProceed.putExtra("key",hashKeyResponse.getMerchant_key());
        intentProceed.putExtra("udf1",BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));
        intentProceed.putExtra("udf2",merchant_udf2);
        intentProceed.putExtra("udf3",merchant_udf3);
        intentProceed.putExtra("udf4",merchant_udf4);
        intentProceed.putExtra("udf5",merchant_udf5);
        intentProceed.putExtra("address1",customer_address1);
        intentProceed.putExtra("address2",customer_address2);
        intentProceed.putExtra("city",customer_city);
        intentProceed.putExtra("state",customer_state);
        intentProceed.putExtra("country",customer_country);
        intentProceed.putExtra("zipcode",customer_zipcode);
        intentProceed.putExtra("hash", hashKey);
        intentProceed.putExtra("unique_id", "");
        intentProceed.putExtra("pay_mode",payment_mode);
        intentProceed.putExtra("sub_merchant_id","");
        startActivityForResult(intentProceed, StaticDataModel.PWE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //onfail,reqCode=100,resCode=0

        //onSuccess, reqCode=100,resCode=-1
        //{"name_on_card":"NA","deduction_percentage":"1.2","net_amount_debit":"1.0","txnid":"SafePeTest1242","issuing_bank":"NA",
        // "cardCategory":"NA","unmappedstatus":"NA","PG_TYPE":"NA","addedon":"2019-10-03 12:38:59","card_type":"Debit Card",
        // "error":"ResponseCode : 00 (Transaction Successful) - Successful transaction","cash_back_percentage":"50.0",
        // "amount":"1.0","furl":"https://pay.easebuzz.in/failed","productinfo":"wallet","email":"adarshmandal515@gmail.com",
        // "udf4":"udf4","status":"success","bank_ref_num":"192760892520",
        // "hash":"49899e8fb8096af68aac8bf323d3280b9b5654054a528e0484054b06815e96e924bbe013e4415e95a6f96d55b7311b7fd63b19690f
        // 5d3c4a9e6f24003ca52544","firstname":"Adarsh Test","surl":"https://pay.easebuzz.in/success","error_Message":
        // "ResponseCode : 00 (Transaction Successful) - Successful transaction","phone":"9811871855","easepayid":"T86WQMPB4J",
        // "cardnum":"NA","key":"FGMXRKGLPF","bankcode":"NA","merchant_logo":"NA","udf10":"","payment_source":"Easebuzz",
        // "udf1":"udf1","udf3":"udf3","udf2":"udf2","udf5":"udf5","mode":"DC","udf7":"","udf6":"","udf9":"","udf8":"","flag":0}
        String response="",result="";
        JSONObject jsonObject=null;
        String bank_ref_num="",txnid="",productinfo="",net_amount_debit="",mode="",status="",easepayid="",date="";
        SendPaymentGatewayDetailsRequest sendPaymentGatewayDetailsRequest = new SendPaymentGatewayDetailsRequest();

        Intent intentStatus=new Intent(PaymentTypeNew.this,PaidOrderActivity.class);

        if (requestCode==100){
            try{
                result = data.getStringExtra("result");
                response = data.getStringExtra("payment_response");
                //Log.d("response",response+result);

                jsonObject=new JSONObject(response);

                bank_ref_num =jsonObject.getString("bank_ref_num");
                txnid =jsonObject.getString("txnid");
                productinfo =jsonObject.getString("productinfo");
                net_amount_debit =jsonObject.getString("net_amount_debit");
                mode =jsonObject.getString("mode");
                status =jsonObject.getString("status");
                easepayid =jsonObject.getString("easepayid");
                date =jsonObject.getString("addedon");
            }catch (Exception e){
                e.printStackTrace();
            }

            sendPaymentGatewayDetailsRequest.setUser_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));
            sendPaymentGatewayDetailsRequest.setBank_ref_no(bank_ref_num);
            sendPaymentGatewayDetailsRequest.setTransaction_id(txnid);
            sendPaymentGatewayDetailsRequest.setDescription(productinfo);
            sendPaymentGatewayDetailsRequest.setOpration("Debit");
            sendPaymentGatewayDetailsRequest.setAmount(net_amount_debit);
            sendPaymentGatewayDetailsRequest.setMode_of_payment(mode);
            sendPaymentGatewayDetailsRequest.setStatus(status);
            sendPaymentGatewayDetailsRequest.setEasy_pay_id(easepayid);
            sendPaymentGatewayDetailsRequest.setType("bank");

            if (PaymentFor.equalsIgnoreCase("Wallet")) {
                sendPaymentGatewayDetailsRequest.setPackage_amount("");
                sendPaymentGatewayDetailsRequest.setPackage_id("");

            } else if (PaymentFor.equalsIgnoreCase("Buy Package")){
                sendPaymentGatewayDetailsRequest.setPackage_amount(Amount);
                sendPaymentGatewayDetailsRequest.setPackage_id(OperatorCode);
            }  else if (PaymentFor.equalsIgnoreCase("Fixed")){
                sendPaymentGatewayDetailsRequest.setPackage_amount("");
                sendPaymentGatewayDetailsRequest.setPackage_id("");
            }else {
                sendPaymentGatewayDetailsRequest.setPackage_amount("");
                sendPaymentGatewayDetailsRequest.setPackage_id("");
            }

            if (resultCode==-1){
                intentStatus.putExtra("status","success");
                if (PaymentFor.equalsIgnoreCase("Wallet")) {
                    try{
                        intentStatus.putExtra("txnid",txnid);
                        intentStatus.putExtra("Amount",Amount);
                        intentStatus.putExtra("date",date);
                        intentStatus.putExtra("productinfo",productinfo);
                        startActivity(intentStatus);
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } else if (PaymentFor.equalsIgnoreCase("Buy Package")){
                    BuyPackageMethod(txnid,easepayid);
                }else if (PaymentFor.equalsIgnoreCase("Fixed")){
                    PayFixedDeposit(txnid,easepayid);
                } else {

                    RechargeRequest rechargeRequest = new RechargeRequest();
                    rechargeRequest.setAmount(Amount);
                    rechargeRequest.setCircle_code(CircleCode);
                    rechargeRequest.setNumber(RechargePaymentId);
                    rechargeRequest.setOperator_code(OperatorCode);
                    rechargeRequest.setRecharge_type(RechargeTypeId);
                    rechargeRequest.setOperator_id(RechargeTypeId);
                    rechargeRequest.setTransaction_id(easepayid);
                    rechargeRequest.setNumber_type("");
                    rechargeRequest.setDescription(PaymentFor+" "+PaymentTypeText);

                    if (RechargeTypeId.equals("7")){
                        rechargeRequest.setStdCode(StdCode);
                        rechargeRequest.setOpvalue2(OptionValue2);
                    }else{
                        rechargeRequest.setStdCode("");
                        rechargeRequest.setOpvalue2("");
                    }

                    try {
                        if (checkBoxGatewayPayment.isChecked() && checkBoxWallet.isChecked()){
                            rechargeRequest.setPayment_mode("both");
                            rechargeRequest.setWallet_amount(""+walletBal);
                            rechargeRequest.setBank_amount(""+balAmount);
                        }else {
                            rechargeRequest.setPayment_mode("bank");
                            rechargeRequest.setWallet_amount("");
                            rechargeRequest.setBank_amount("");
                        }
                    }catch (Exception e){
                        rechargeRequest.setPayment_mode("bank");
                        e.printStackTrace();
                    }

                    doRecharge(rechargeRequest);
                }
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment), "Payment Success", false);
            }else {
                intentStatus.putExtra("status","failed");
                try{
                    intentStatus.putExtra("txnid",txnid);
                    intentStatus.putExtra("Amount",Amount);
                    intentStatus.putExtra("date",date);
                    intentStatus.putExtra("productinfo",productinfo);
                    startActivity(intentStatus);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Payment Failed\n"+result+"\n"+response,true);
            }

            saveTransactionDetails(sendPaymentGatewayDetailsRequest);
        }else {

            try{
                intentStatus.putExtra("status","failed");
                intentStatus.putExtra("txnid",txnid);
                intentStatus.putExtra("Amount",Amount);
                intentStatus.putExtra("date",date);
                intentStatus.putExtra("productinfo",productinfo);
                startActivity(intentStatus);
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Payment Failed",false);
        }
    }

    private void saveTransactionDetails(SendPaymentGatewayDetailsRequest sendPaymentGatewayDetailsRequest) {

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.addBankToWallet(sendPaymentGatewayDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SendPaymentGatewayDetailsResponse>() {
                    @Override
                    public void onSuccess(SendPaymentGatewayDetailsResponse response) {

                        if (response.isStatus()) {
                            Toast.makeText(PaymentTypeNew.this, "Success", Toast.LENGTH_LONG).show();
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.payment), true, e);
                    }
                }));
    }

}
