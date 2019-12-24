package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.google.gson.Gson;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.flight.FlightBookDetailActivity;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.HashKeyRequest;
import com.safepayu.wallet.models.request.SendPaymentGatewayDetailsRequest;
import com.safepayu.wallet.models.request.booking.flight.FlightBlockTicketRequest;
import com.safepayu.wallet.models.response.HashKeyResponse;
import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
import com.safepayu.wallet.models.response.WalletLimitResponse;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.safepayu.wallet.models.response.booking.flight.FlightBlockTicketResponse;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import datamodels.StaticDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.booking.flight.FlightListActivity.MY_PREFS_NAME;

public class BookingPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvFlightPayAmount, tvWalletlimit, tvTotalBalanceWallet, tvWalletDeductAmount, tvGatewayDeductAmount;
    private Button btnBookingPayAmount;
    private CheckBox checkBoxWallet, checkBoxGatewayPayment;
    private LoadingDialog loadingDialog;
    WalletLimitResponse WalletResponse;
    String json;
    private String Source, Destination, JourneyDate, TrvaellersCount, ClassType,TripType, Adults, Infants, Children, FlightImage, TravelClass, AirLineCode, AirLineNumber,TotalFareReturnOnward;
    public static AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mdata;
    public  static AvailableFlightResponse.DataBean.DomesticReturnFlightsBean mdataReturn;
    private FlightBlockTicketRequest flightBlockTicketRequest;
    FlightBlockTicketResponse FlightResponse;
    //recharge/bill payment parameter
    private String RechargePaymentId="",Amount="",PaymentTypeText="",PaymentFor="",RechargeTypeId="",OperatorCode="",CircleCode="",OperatorId="",WalletCashback,TotalDeductAmount;
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
    List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> internationalFlights = new ArrayList<>();
    List<AvailableFlightResponse.DataBean.DomesticReturnFlightsBean> domesticReturnFlights = new ArrayList<>();
    List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> domesticOnwardFlights = new ArrayList<>();
    List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> domestictwoFlights = new ArrayList<>();
    ArrayList<String> adultList;
    ArrayList<String> DobList;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_payment);
        findId();

        walletLimitLeft();
    }


    private void findId() {

        adultList = (ArrayList<String>) getIntent().getSerializableExtra("adult_list");
        DobList = (ArrayList<String>) getIntent().getSerializableExtra("dob_list");
        loadingDialog = new LoadingDialog(this);
        tvFlightPayAmount = findViewById(R.id.tv_flight_pay_amount);
        tvWalletlimit = findViewById(R.id.tv_walletlimit);
        tvTotalBalanceWallet = findViewById(R.id.tv_total_balance_wallet);
        tvWalletDeductAmount = findViewById(R.id.tv_wallet_deduct_amount);
        tvGatewayDeductAmount = findViewById(R.id.tv_gateway_deduct_amount);
        btnBookingPayAmount = findViewById(R.id.btn_booking_pay_amount);
        checkBoxWallet = findViewById(R.id.check_box_wallet);
        checkBoxGatewayPayment = findViewById(R.id.check_box_gateway_payment);
        btnBookingPayAmount.setOnClickListener(this);



        //***************get data****************

        Source = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
        TrvaellersCount = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        Adults = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
        Infants = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
        Children = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);
        FlightImage = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        TravelClass = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS);
        AirLineCode = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_CODE);
        AirLineNumber = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_FLIGHT_NUMBER);
        TotalFareReturnOnward =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().TOTALFARE_RETURN_ONWARDS);
        TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);

        if (TripType.equals("1")){
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
             gson = new Gson();
            json = prefs.getString("MyObject", "");
            mdata = gson.fromJson(json, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);

        } else if (TripType.equals("2")){
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            gson = new Gson();
            json = prefs.getString("MyObjectOnward", "");
            mdata = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);

            json = prefs.getString("MyObjectReturn", "");
            mdataReturn = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticReturnFlightsBean.class);
        }



        if (TripType.equals("1")){
            tvFlightPayAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));
        }else if (TripType.equals("2")){
            tvFlightPayAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(TotalFareReturnOnward)));
        }


        if (Infants.equals("")) {
            Infants = "0";
        }
        if (Children.equals("")) {
            Children = "0";
        }

        if (TravelClass.equals("E")) {
            TravelClass = "Economy";
        } else if (TravelClass.equals("PE")) {
            TravelClass = "Preminum Economy";
        } else if (TravelClass.equals("B")) {
            TravelClass = "Business";
        } else if (TravelClass.equals("F")) {
            TravelClass = "First Class";
        }

        int flightType = 1;
        switch (Integer.parseInt(TripType)) {
            case 1:
                domesticOnwardFlights.add(mdata);
                break;
            case 2:
                domesticReturnFlights.add(mdataReturn);
                domesticOnwardFlights.add(mdata);

                break;
            case 3:
                //  internationalFlights.add(mdata);
                break;
        }


      //  flightBlockTicketRequest.setPayment_wallet(wallet_rs);
      //  flightBlockTicketRequest.setPayment_bank(bank_rs);

      //  Amount = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE);

      //  btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));

     //   tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));

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
                        btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(subAmount));


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
                    if (TripType.equals("1")){
                        btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " +  NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));

                    }
                    else if (TripType.equals("2")){
                        btnBookingPayAmount.setText("Pay " + getResources().getString(R.string.rupees) + " " +  NumberFormat.getIntegerInstance().format(Integer.parseInt(TotalFareReturnOnward)));
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +NumberFormat.getIntegerInstance().format(Integer.parseInt(TotalFareReturnOnward)));
                    }
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
                            if (TripType.equals("1")){
                                tFare = Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE));
                            } else if (TripType.equals("2")){
                                tFare = (Integer.parseInt(TotalFareReturnOnward));
                            }

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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_booking_pay_amount: {

                if (tvGatewayDeductAmount.getVisibility() == View.GONE){
                    getflightBlockTicket(flightBlockTicketRequest);
                }

                else  if (checkBoxGatewayPayment.isChecked()&&!String.valueOf(WalletResponse.getData().getLimit()).equals("0")||!Amount.equals("0")){
                    Amount = btnBookingPayAmount.getText().toString().trim();
                    String amount[] = Amount.split(" ");
                    String s1 = amount[0];
                    String s2 = amount[1];
                    bankAmount = amount[2];
                    BankAmount = Integer.parseInt(bankAmount.replaceAll(",", ""));

                    //*************hash key ****************
                    HashKeyRequest hashKeyRequest=new HashKeyRequest();
                    hashKeyRequest.setCustomer_firstName(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME));
                    hashKeyRequest.setMerchant_payment_amount(String.valueOf(BankAmount));
                    hashKeyRequest.setMerchant_productInfo("Flight Booking");
                    hashKeyRequest.setCustomer_email_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));
                    hashKeyRequest.setType("0");
                    if (isNetworkAvailable()){
                        getHashKey(hashKeyRequest);
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
                    }
                }


                break;
            }
        }
    }

    private void getHashKey(final HashKeyRequest hashKeyRequest) {
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
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.paymentLayout), true, e);
                    }
                }));
    }

    private void DoPayment(HashKeyRequest hashKeyRequest, HashKeyResponse hashKeyResponse){
        //key|txnid|amount|productinfo|firstname|email_id|udf1|udf2|udf3|udf4|udf5||||||salt|key

        customer_phone = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
     //   address = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ADDRESS);
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
        merchant_payment_amount = Float.parseFloat(String.valueOf(BankAmount));


        hash = hashKeyResponse.getMerchant_key() + "|" + hashKeyResponse.getTransactionId() + "|" + merchant_payment_amount + "|" + hashKeyRequest.getMerchant_productInfo()
                + "|" + hashKeyRequest.getCustomer_firstName()
                + "|" + hashKeyRequest.getCustomer_email_id() + "|" + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|" + merchant_udf5
                + "||||||"
                + hashKeyResponse.getMerchant_salt() + "|" + hashKeyResponse.getMerchant_key();

        String hashKey = hashCal("SHA-512", hash);
        merchant_payment_amount= Float.parseFloat(hashKeyRequest.getMerchant_payment_amount());

        Intent intentProceed = new Intent(BookingPaymentActivity.this, PWECouponsActivity.class);
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
        String response = "", result = "";
        JSONObject jsonObject = null;
        String bank_ref_num = "", txnid = "", productinfo = "", net_amount_debit = "", mode = "", status = "", easepayid = "", date = "";
        SendPaymentGatewayDetailsRequest sendPaymentGatewayDetailsRequest = new SendPaymentGatewayDetailsRequest();

        Intent intentStatus = new Intent(BookingPaymentActivity.this, FlightBookDetailActivity.class);

        if (requestCode == 100) {
            try {
                result = data.getStringExtra("result");
                response = data.getStringExtra("payment_response");
                //Log.d("response",response+result);

                jsonObject = new JSONObject(response);

                bank_ref_num = jsonObject.getString("bank_ref_num");
                txnid = jsonObject.getString("txnid");
                productinfo = jsonObject.getString("productinfo");
                net_amount_debit = jsonObject.getString("net_amount_debit");
                mode = jsonObject.getString("mode");
                status = jsonObject.getString("status");
                easepayid = jsonObject.getString("easepayid");
                date = jsonObject.getString("addedon");
            } catch (Exception e) {
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

//            sendPaymentGatewayDetailsRequest.setPackage_amount("");
//            sendPaymentGatewayDetailsRequest.setPackage_id("");

            saveTransactionDetails(sendPaymentGatewayDetailsRequest);
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
                            Toast.makeText(BookingPaymentActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            getflightBlockTicket(flightBlockTicketRequest);
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.paymentLayout), true, e);
                    }
                }));
    }


    private void getflightBlockTicket(FlightBlockTicketRequest flightBlockTicketRequest) {

        flightBlockTicketRequest = new FlightBlockTicketRequest();
        FlightBlockTicketRequest.Data data = new FlightBlockTicketRequest.Data(internationalFlights, domesticReturnFlights, domesticOnwardFlights);
        flightBlockTicketRequest.setData(data);
        flightBlockTicketRequest.setInfantPax(Infants);
        flightBlockTicketRequest.setAdultPax(Adults);
        flightBlockTicketRequest.setChildPax(Children);
        flightBlockTicketRequest.setNames(adultList);
        flightBlockTicketRequest.setDob(DobList);
        flightBlockTicketRequest.setTripType(TripType);
        flightBlockTicketRequest.setFlightType("1");
        if (tFare>walletDeduct){
            flightBlockTicketRequest.setPayment_wallet(String.valueOf(WalletResponse.getData().getLimit()));
            flightBlockTicketRequest.setPayment_bank(String.valueOf(subAmount));
        } else {
            if (b){
                flightBlockTicketRequest.setPayment_wallet(String.valueOf(0));
                flightBlockTicketRequest.setPayment_bank(String.valueOf(tFare));
            }
            flightBlockTicketRequest.setPayment_wallet(String.valueOf(tFare));
            flightBlockTicketRequest.setPayment_bank(String.valueOf(0));
        }

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightBlockTicket(flightBlockTicketRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlightBlockTicketResponse>() {
                    @Override
                    public void onSuccess(FlightBlockTicketResponse flightBlockTicketResponse) {
                        loadingDialog.hideDialog();
                        FlightResponse = flightBlockTicketResponse;
                        if (flightBlockTicketResponse.isStatus()) {
                            Toast.makeText(getApplicationContext(), flightBlockTicketResponse.getMessage(), Toast.LENGTH_LONG).show();
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_REFERENCE_NO,flightBlockTicketResponse.getData().getReferenceNo());
                            Intent intent = new Intent(BookingPaymentActivity.this,FlightBookDetailActivity.class);
                            startActivity(intent);
                        }
                        Toast.makeText(getApplicationContext(), flightBlockTicketResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_passenger), false, e.getCause());
                    }
                }));
    }
}
