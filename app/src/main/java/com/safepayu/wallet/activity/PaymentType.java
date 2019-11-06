package com.safepayu.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.HashKeyRequest;
import com.safepayu.wallet.models.request.RechargeRequest;
import com.safepayu.wallet.models.request.SendPaymentGatewayDetailsRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.BuyPackageResponse;
import com.safepayu.wallet.models.response.HashKeyResponse;
import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import datamodels.StaticDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.safepayu.wallet.activity.MemberBankAddPackages.buyPackageFromDB;

public class PaymentType extends BaseActivity implements PasscodeClickListener {

    RelativeLayout cardBtnLayout, upiBtnLayout, NetBankingBtnLayout, WalletBtnLayout, radioLayout4;
    LinearLayout cardLayout, UpiLayout;
    private Button btn_proceed_netBanking, proceed_upi, proceed_wallet, proceed_card,BackBtn,btn_addMoney_card;
    private Button ProceedWalletBtn;
    private LoadingDialog loadingDialog;
    private TextView AmountTV;
    private CardView Card_fillLayout;

    //recharge/bill payment parameter
    private String RechargePaymentId="",Amount="",PaymentTypeText="",PaymentFor="",RechargeTypeId="",OperatorCode="",CircleCode="",OperatorId="";

    private String merchant_trxnId="",merchant_productInfo="",customer_firstName="",customer_email_id="",customer_phone="";
    private String merchant_udf1="",merchant_udf2="",merchant_udf3="",merchant_udf4="",merchant_udf5="";
    private String customer_address1="",customer_address2="",customer_city="",customer_state="",customer_country="",customer_zipcode="";
    private String hash="",payment_mode="",customers_unique_id="";
    private float merchant_payment_amount= (float) 1.0;
    private String merchant_key="FGMXRKGLPF",merchant_salt="79W2K9FO44";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);

        cardBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout1);
        upiBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout3);
        NetBankingBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout2);
        WalletBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout4);
        Card_fillLayout=findViewById(R.id.card_fillLayout);

        cardLayout = (LinearLayout) findViewById(R.id.card_layout);
        UpiLayout = (LinearLayout) findViewById(R.id.upi_layout);

        proceed_card = (Button) findViewById(R.id.btn_addMoney_card);
        btn_proceed_netBanking = (Button) findViewById(R.id.btn_proceed_netBanking);
        proceed_upi = (Button) findViewById(R.id.btn_pay_upi);
        proceed_wallet = (Button) findViewById(R.id.btn_proceed_wallet);
        btn_addMoney_card=findViewById(R.id.btn_addMoney_card);
        BackBtn=findViewById(R.id.sendmoney_back_btn);
        ProceedWalletBtn=findViewById(R.id.btn_proceed_wallet);

        AmountTV = findViewById(R.id.tv_walletAddedAmount);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(VISIBLE);
                UpiLayout.setVisibility(GONE);
                proceed_wallet.setVisibility(GONE);
                btn_proceed_netBanking.setVisibility(GONE);
                Card_fillLayout.setVisibility(GONE);
            }
        });

        NetBankingBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(GONE);
                UpiLayout.setVisibility(GONE);
                proceed_wallet.setVisibility(GONE);
                btn_proceed_netBanking.setVisibility(VISIBLE);
            }
        });

        upiBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(GONE);
                UpiLayout.setVisibility(VISIBLE);
                proceed_wallet.setVisibility(GONE);
                btn_proceed_netBanking.setVisibility(GONE);
            }
        });

        WalletBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(GONE);
                UpiLayout.setVisibility(GONE);
                proceed_wallet.setVisibility(VISIBLE);
                btn_proceed_netBanking.setVisibility(GONE);
            }
        });

        btn_addMoney_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashKeyRequest hashKeyRequest=new HashKeyRequest();
                hashKeyRequest.setCustomer_firstName(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME));
                hashKeyRequest.setMerchant_payment_amount(Amount);
                hashKeyRequest.setMerchant_productInfo(PaymentFor+" "+PaymentTypeText);
                hashKeyRequest.setCustomer_email_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));

                if (isNetworkAvailable()){
                    getHashKey(hashKeyRequest);
                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
                }
            }
        });

        ProceedWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                    startActivity(new Intent(PaymentType.this,CreatePassCodeActivity.class));
                } else {
                    PasscodeDialog passcodeDialog = new PasscodeDialog(PaymentType.this, PaymentType.this, "");
                    passcodeDialog.show();
                }
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

            AmountTV.setText(getResources().getString(R.string.rupees)+" "+Amount);
            if (PaymentFor.equalsIgnoreCase("Wallet") || PaymentFor.equalsIgnoreCase("Buy Package")) {
                WalletBtnLayout.setVisibility(GONE);
            }
            NetBankingBtnLayout.setVisibility(GONE);
            upiBtnLayout.setVisibility(GONE);
            Card_fillLayout.setVisibility(GONE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.payment_type;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

        if (isConnected){

        }else{
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
        }
    }

    private void doRecharge(final RechargeRequest rechargeRequest){
        final Intent intentRecharge=new Intent(PaymentType.this,PaidOrderActivity.class);
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.doRecharge(rechargeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        String date=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        if (response.getStatus()) {

                            if (response.getStatusCode()==0){
                                intentRecharge.putExtra("status","pending");
                            }else {
                                intentRecharge.putExtra("status","success");
                            }
                           // Toast.makeText(PaymentType.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            intentRecharge.putExtra("status","failed");
                            //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),response.getMessage(),false);
                        }
                        try{
                            intentRecharge.putExtra("Amount",Amount);
                            intentRecharge.putExtra("date",date);
                            intentRecharge.putExtra("productinfo",PaymentFor+" "+PaymentTypeText);
                            intentRecharge.putExtra("txnid",rechargeRequest.getTransaction_id());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        startActivity(intentRecharge);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.paymentLayout), true, e);
                    }
                }));

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
                            Toast.makeText(PaymentType.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.paymentLayout), true, e);
                    }
                }));
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
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.paymentLayout), true, e);
                    }
                }));
    }

    private void DoPayment(HashKeyRequest hashKeyRequest, HashKeyResponse hashKeyResponse){
        //key|txnid|amount|productinfo|firstname|email_id|udf1|udf2|udf3|udf4|udf5||||||salt|key

        customer_phone = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
        payment_mode="production";//test production
        customer_address1="noida";
        customer_address2="noida";
        customer_city="noida";
        customer_state="UP";
        customer_country="India";
        customer_zipcode="121004";
        merchant_udf1 = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
        merchant_udf2="udf2";
        merchant_udf3="udf3";
        merchant_udf4="udf4";
        merchant_udf5="udf5";

        merchant_payment_amount = Float.parseFloat(Amount);


        hash = hashKeyResponse.getMerchant_key() + "|" + hashKeyResponse.getTransactionId() + "|" + merchant_payment_amount + "|" + hashKeyRequest.getMerchant_productInfo()
                + "|" + hashKeyRequest.getCustomer_firstName()
                + "|" + hashKeyRequest.getCustomer_email_id() + "|" + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|" + merchant_udf5
                + "||||||"
                + hashKeyResponse.getMerchant_salt() + "|" + hashKeyResponse.getMerchant_key();
//+"|"+customer_address1+"|"+customer_city+"|"+customer_state+"|"+customer_country+"|"+customer_zipcode

        String hashKey = hashCal("SHA-512", hash);
        merchant_payment_amount= Float.parseFloat(hashKeyRequest.getMerchant_payment_amount());

        Intent intentProceed = new Intent(PaymentType.this, PWECouponsActivity.class);
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
        
        Intent intentStatus=new Intent(PaymentType.this,PaidOrderActivity.class);

        if (requestCode==100){
            try{
                result = data.getStringExtra("result");
                response = data.getStringExtra("payment_response");
                Log.d("response",response+result);

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
            } else {
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
                } else {

                    RechargeRequest rechargeRequest = new RechargeRequest();
                    rechargeRequest.setAmount(Amount);
                    rechargeRequest.setCircle_code(CircleCode);
                    rechargeRequest.setNumber(RechargePaymentId);
                    rechargeRequest.setOperator_code(OperatorCode);
                    rechargeRequest.setRecharge_type(RechargeTypeId);
                    rechargeRequest.setOperator_id(OperatorId);
                    rechargeRequest.setTransaction_id(easepayid);
                    rechargeRequest.setPayment_mode("bank");
                    rechargeRequest.setNumber_type("");
                    rechargeRequest.setDescription(PaymentFor+" "+PaymentTypeText);

                    doRecharge(rechargeRequest);
                }
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout), "Payment Success", false);
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
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Payment Failed\n"+result+"\n"+response,true);
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
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Payment Failed",false);
        }

    }

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {

        if (isPasscodeMatched){
            if (PaymentFor.equalsIgnoreCase("Buy Package")){
                BuyPackageMethod("","");
            }else {
                RechargeRequest rechargeRequest=new RechargeRequest();
                rechargeRequest.setAmount(Amount);
                rechargeRequest.setCircle_code(CircleCode);
                rechargeRequest.setNumber(RechargePaymentId);
                rechargeRequest.setOperator_code(OperatorCode);
                rechargeRequest.setRecharge_type(RechargeTypeId);
                rechargeRequest.setOperator_id(OperatorId);
                rechargeRequest.setTransaction_id("");
                rechargeRequest.setPayment_mode("wallet");
                rechargeRequest.setNumber_type("");
                rechargeRequest.setDescription(PaymentFor+" "+PaymentTypeText);

                doRecharge(rechargeRequest);
            }

        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Invalid Passcode",false);
        }

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
                        Intent intentStatus=new Intent(PaymentType.this,PaidOrderActivity.class);
                        if (response.isStatus()) {
                            intentStatus.putExtra("status","success");
                        }else {
                            intentStatus.putExtra("status","failed");
                            Toast.makeText(PaymentType.this, response.getMessage(), Toast.LENGTH_LONG).show();

                        }
                        intentStatus.putExtra("txnid",txnid);
                        intentStatus.putExtra("Amount",Amount);
                        intentStatus.putExtra("date",currentDate);
                        intentStatus.putExtra("productinfo",PaymentFor+" "+PaymentTypeText);
                        startActivity(intentStatus);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.paymentLayout), true, e);
                    }
                }));

    }
}
