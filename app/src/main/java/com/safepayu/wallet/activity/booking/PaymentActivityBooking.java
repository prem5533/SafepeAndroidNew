package com.safepayu.wallet.activity.booking;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.CreatePassCodeActivity;
import com.safepayu.wallet.activity.Navigation;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.HashKeyRequest;
import com.safepayu.wallet.models.request.SendPaymentGatewayDetailsRequest;
import com.safepayu.wallet.models.request.booking.flight.FlightBookingDetailRequest;
import com.safepayu.wallet.models.response.HashKeyResponse;
import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
import com.safepayu.wallet.models.response.WalletLimitResponse;
import com.safepayu.wallet.models.response.booking.bus.BusBlockingResponse;
import com.safepayu.wallet.models.response.booking.flight.FlighPdfResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelBookResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;
import com.safepayu.wallet.utils.pdf.CheckForSDCard;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import datamodels.StaticDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.booking.hotel.HotelDetails.bookHotelRequest;
import static com.safepayu.wallet.fragment.bus.SelectSeatFragment.busBookingRequest;

public class PaymentActivityBooking  extends AppCompatActivity implements PasscodeClickListener {

    private TextView tvPayAmount, tvWalletlimit, tvTotalBalanceWallet, tvWalletDeductAmount, tvGatewayDeductAmount;
    private Button btnBookingPayAmount;
    private CheckBox checkBoxWallet, checkBoxGatewayPayment;
    private LoadingDialog loadingDialog;
    WalletLimitResponse WalletResponse;
    private String BookingReferenceNo="";

    String PaymentMode = "bank",bankAmount="";
    int tFare,walletDeduct, subAmount,BankAmount;
    boolean b = true;
    boolean walletcheck = true;
    String PaymentBank,PaymentWallet,bank_rs,wallet_rs;

    double amount=0,walletBal=0,walletLimit=0;
    boolean checkWalletBal=false,checkWalletLimit=false;

    //recharge/bill payment parameter
    private String Amount="0",PaymentFor="",customer_phone="";
    private String merchant_udf1="",merchant_udf2="",merchant_udf3="",merchant_udf4="",merchant_udf5="";
    private String customer_address1="",customer_address2="",customer_city="",customer_state="",customer_country="",customer_zipcode="";
    private String hash="",payment_mode="",customers_unique_id="";
    private float merchant_payment_amount= (float) 1.0;

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

        try {
            Intent intent = getIntent();
            Amount = intent.getStringExtra("Amount");
            PaymentFor = intent.getStringExtra("PaymentFor");
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
                    tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                }else {
                    checkBoxWallet.setChecked(true);
                    checkBoxGatewayPayment.setChecked(false);
                    tvWalletDeductAmount.setVisibility(View.VISIBLE);
                    tvGatewayDeductAmount.setVisibility(View.GONE);
                    tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                }
            }
        });

            checkBoxWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    checkBoxWallet.setChecked(true);
                    checkBoxGatewayPayment.setChecked(false);
                    tvWalletDeductAmount.setVisibility(View.VISIBLE);
                    tvGatewayDeductAmount.setVisibility(View.GONE);
                    tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));


                    /*
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

                        if (subAmount>(int)Double.parseDouble(WalletResponse.getData().getWallet_balance())){

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

                    double walletDouble = Double.parseDouble(WalletResponse.getData().getWallet_balance());
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

                    }
                     */
                } else {

                    checkBoxWallet.setChecked(false);
                    tvWalletDeductAmount.setVisibility(View.GONE);
                    tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                    tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                    btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));

                    /*
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
                    */
                }
            }
        });

        btnBookingPayAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxWallet.isChecked()){

                    if (checkWalletBal){
                        if (checkWalletLimit){
                            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                                startActivity(new Intent(PaymentActivityBooking.this, CreatePassCodeActivity.class));
                            } else {
                                PasscodeDialog passcodeDialog = new PasscodeDialog(PaymentActivityBooking.this, PaymentActivityBooking.this, "");
                                passcodeDialog.show();
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Wallet Limit Is Exceeded",true);
                        }
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Wallet Balance Is Low",true);
                    }
                }else {

                    if (PaymentFor.equalsIgnoreCase("Hotel Booking")){
                        bookHotelRequest.setPayment_mode("bank");
                        bookHotelRequest.setBank_amount(Amount);
                        bookHotelRequest.setWallet_amount("0");
                    }else {
                        busBookingRequest.setPayment_mode("bank");
                    }
                    HashKeyRequest hashKeyRequest=new HashKeyRequest();
                    hashKeyRequest.setCustomer_firstName(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME));
                    hashKeyRequest.setMerchant_payment_amount(Amount);
                    hashKeyRequest.setMerchant_productInfo(PaymentFor);
                    hashKeyRequest.setCustomer_email_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));
                    hashKeyRequest.setType("0");

                    if (isNetworkAvailable()){
                        getHashKey(hashKeyRequest);
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Please Check Your Internet Connection",false);
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

                            try {
                                walletBal= Double.parseDouble(WalletResponse.getData().getWallet_balance());
                                amount=Double.parseDouble(Amount);
                                walletLimit= WalletResponse.getData().getLimit();

                                tvTotalBalanceWallet.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format((int) Double.parseDouble(walletLimitResponse.getData().getWallet_balance())));
                                tvWalletlimit.setText("* Today's Wallet Limit - Rs. " + new DecimalFormat("##.##").format(walletLimitResponse.getData().getLimit()));

                                if (amount>walletBal){
                                    checkWalletBal=false;
                                    checkBoxWallet.setChecked(false);
                                    checkBoxGatewayPayment.setChecked(true);

                                    tvWalletDeductAmount.setVisibility(View.GONE);
                                    tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                                    tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                    btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                }else {
                                    if (amount>walletLimit){
                                        checkWalletLimit=false;
                                        checkBoxWallet.setChecked(false);
                                        checkBoxGatewayPayment.setChecked(true);

                                        tvWalletDeductAmount.setVisibility(View.GONE);
                                        tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                    }else {
                                        checkWalletBal=true;
                                        checkWalletLimit=true;
                                        checkBoxWallet.setChecked(true);
                                        checkBoxGatewayPayment.setChecked(false);

                                        tvGatewayDeductAmount.setVisibility(View.GONE);
                                        tvWalletDeductAmount.setVisibility(View.VISIBLE);
                                        tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                        btnBookingPayAmount.setText(getResources().getString(R.string.rupees) + " " + Double.parseDouble(Amount));
                                    }
                                }
                            }catch (Exception e){
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),e.getMessage(),true);
                                e.printStackTrace();
                            }

                            /*
                            if (String.valueOf(walletLimitResponse.getData().getLimit()).equals("0")){
                                tvWalletDeductAmount.setVisibility(View.GONE);
                                checkBoxWallet.setChecked(false);
                            }

                            tFare = Integer.parseInt(Amount);

                            walletDeduct =(int)WalletResponse.getData().getLimit();

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


                            } */

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

            if (PaymentFor.equalsIgnoreCase("Hotel Booking")){
                bookHotelRequest.setPayment_mode("wallet");
                bookHotelRequest.setBank_amount("0");
                bookHotelRequest.setWallet_amount(Amount);
                getHotelBook();
            }else {
                busBookingRequest.setPayment_mode("wallet");
                getBusBlockSeat();
            }

        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(PaymentActivityBooking.this.findViewById(R.id.payment),"Wrong Passcode", false);
        }
    }

    private void getBusBlockSeat() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(PaymentActivityBooking.this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getBusBlocking(busBookingRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BusBlockingResponse>() {
                    @Override
                    public void onSuccess(BusBlockingResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            try {
                                BookingReferenceNo=response.getData().getReferenceNo();

                                showDialogBook(response.getMessage()+"\n Booking Reference No - "+BookingReferenceNo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(PaymentActivityBooking.this.findViewById(R.id.payment), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(PaymentActivityBooking.this.findViewById(R.id.payment), false, e.getCause());
                    }
                }));
    }

    private void getHotelBook() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getHotelBook(bookHotelRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HotelBookResponse>() {
                    @Override
                    public void onSuccess(HotelBookResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            try {
                                BookingReferenceNo=response.getData().getReferenceNo();
                                showDialogBook(response.getData().getMessage()+" \nReference No - "+response.getData().getReferenceNo());
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment), response.getData().getMessage()+" \nReference No - "+response.getData().getReferenceNo(), false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.payment), false, e.getCause());
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
        merchant_payment_amount = Float.parseFloat(String.valueOf(Amount));


        hash = hashKeyResponse.getMerchant_key() + "|" + hashKeyResponse.getTransactionId() + "|" + merchant_payment_amount + "|" + hashKeyRequest.getMerchant_productInfo()
                + "|" + hashKeyRequest.getCustomer_firstName()
                + "|" + hashKeyRequest.getCustomer_email_id() + "|" + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|" + merchant_udf5
                + "||||||"
                + hashKeyResponse.getMerchant_salt() + "|" + hashKeyResponse.getMerchant_key();

        String hashKey = hashCal("SHA-512", hash);
        merchant_payment_amount= Float.parseFloat(hashKeyRequest.getMerchant_payment_amount());

        Intent intentProceed = new Intent(PaymentActivityBooking.this, PWECouponsActivity.class);
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
            sendPaymentGatewayDetailsRequest.setPackage_amount("");
            sendPaymentGatewayDetailsRequest.setPackage_id("");

            if (resultCode==-1){
                if (PaymentFor.equalsIgnoreCase("Bus Booking")){
                    getBusBlockSeat();
                } else {
                    getHotelBook();
                }
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment), "Payment Success", false);
            }else {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"Payment Failed\n"+result+"\n"+response,true);
            }

            saveTransactionDetails(sendPaymentGatewayDetailsRequest);
        }else {
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
                            Toast.makeText(PaymentActivityBooking.this, response.getMessage(), Toast.LENGTH_LONG).show();
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

    public void showDialogBook(String Message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PaymentActivityBooking.this);

        dialog.setTitle("Booking Confirmation")
                .setCancelable(false)
                .setMessage("\n"+Message+"\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        startActivity(new Intent(PaymentActivityBooking.this, Navigation.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Download Ticket", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        if (PaymentFor.equalsIgnoreCase("Hotel Booking")){
                            Toast.makeText(PaymentActivityBooking.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                        }else {
                            getPdf(BookingReferenceNo);
                        }
                        dialog.dismiss();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }

    //**********************************for pdf download************************
    private void getPdf(String REFno) {

        FlightBookingDetailRequest flightBookingDetailRequest = new FlightBookingDetailRequest();
        //  flightBookingDetailRequest.setReferenceNo("300905016582");
        // flightBookingDetailRequest.setReferenceNo("300270016738");
        flightBookingDetailRequest.setReferenceNo(REFno);
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(PaymentActivityBooking.this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getBusPdf(flightBookingDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlighPdfResponse>(){
                    @Override
                    public void onSuccess(FlighPdfResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            //new DownloadTask(PaymentActivityBooking.this, response.getData());

                            new DownloadingTask(response.getData()).execute();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),response.getMessage(),true);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.payment), false, e.getCause());
                    }
                }));

    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;
        ProgressDialog progressDialog;
        private String downloadUrl, downloadFileName = "";

        public DownloadingTask(String Url) {
            this.downloadUrl=Url;
            downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PaymentActivityBooking.this);
            progressDialog.setMessage("Downloading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                progressDialog.dismiss();
                if (outputFile != null) {

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            //TODO your background code
                            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SafePe/" + downloadFileName);  // -> filename = maven.pdf
                            Uri path = FileProvider.getUriForFile(PaymentActivityBooking.this,PaymentActivityBooking.this.getApplicationContext().getPackageName() + ".provider",pdfFile);
                            Intent intent= new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(path, "application/pdf");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            PendingIntent resultIntent = PendingIntent.getActivity( PaymentActivityBooking.this , 0, intent,
                                    PendingIntent.FLAG_ONE_SHOT);

                            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( PaymentActivityBooking.this)
                                    .setSmallIcon(R.drawable.safepe1)
                                    .setContentTitle("Ticket Downloaded")
                                    .setContentText(BookingReferenceNo)
                                    .setStyle( new NotificationCompat.BigTextStyle().bigText("SafePe"))

                                    .setAutoCancel( true )
                                    .setSound(notificationSoundURI)
                                    .setContentIntent(resultIntent);

                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            notificationManager.notify(0, mNotificationBuilder.build());
                        }
                    });

                    ContextThemeWrapper ctw = new ContextThemeWrapper( PaymentActivityBooking.this, R.style.AppTheme);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                    alertDialogBuilder.setTitle("Document  ");
                    alertDialogBuilder.setMessage("Document Downloaded Successfully ");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            startActivity(new Intent(PaymentActivityBooking.this, Navigation.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                        }
                    });

//                    alertDialogBuilder.setNegativeButton("Open Ti",new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SafePe/" + downloadFileName);  // -> filename = maven.pdf
//                            Uri path = FileProvider.getUriForFile(PaymentActivityBooking.this,PaymentActivityBooking.this.getApplicationContext().getPackageName() + ".provider",pdfFile);
//                            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//                            pdfIntent.setDataAndType(path, "application/pdf");
//                            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            try{
//                                startActivity(pdfIntent);
//                            }catch(ActivityNotFoundException e){
//                                Toast.makeText(PaymentActivityBooking.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
                    alertDialogBuilder.show();

                } else {

                    Toast.makeText(PaymentActivityBooking.this, "Ticket Downloaded Failed", Toast.LENGTH_LONG).show();

                    Log.e("Error - ", "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e("Error - ", "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e("HttpURLConnection Error", "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());
                }


                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "SafePe");
                } else
                    //Toast.makeText(PaymentActivityBooking.this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
            }

            return null;
        }
    }

}
