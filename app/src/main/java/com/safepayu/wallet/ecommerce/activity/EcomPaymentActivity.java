    package com.safepayu.wallet.ecommerce.activity;

    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.graphics.drawable.ColorDrawable;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.os.Bundle;
    import android.view.View;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.easebuzz.payment.kit.PWECouponsActivity;
    import com.safepayu.wallet.BaseApp;
    import com.safepayu.wallet.R;
    import com.safepayu.wallet.api.ApiClient;
    import com.safepayu.wallet.api.ApiService;
    import com.safepayu.wallet.dialogs.LoadingDialog;
    import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
    import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
    import com.safepayu.wallet.ecommerce.model.request.OrderSaveRequest;
    import com.safepayu.wallet.ecommerce.model.response.CartsQuantityResponse;
    import com.safepayu.wallet.ecommerce.model.response.OrderSaveResponse;
    import com.safepayu.wallet.models.request.HashKeyRequest;
    import com.safepayu.wallet.models.request.SendPaymentGatewayDetailsRequest;
    import com.safepayu.wallet.models.response.HashKeyResponse;
    import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
    import com.safepayu.wallet.models.response.WalletLimitResponse;

    import org.json.JSONObject;

    import java.security.MessageDigest;
    import java.security.NoSuchAlgorithmException;
    import java.text.DecimalFormat;
    import java.text.NumberFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;

    import datamodels.StaticDataModel;
    import io.reactivex.android.schedulers.AndroidSchedulers;
    import io.reactivex.observers.DisposableSingleObserver;
    import io.reactivex.schedulers.Schedulers;


    public class EcomPaymentActivity extends AppCompatActivity implements View.OnClickListener {

        String paidAmount,totalCartItem,totalTax,totalDeliveryCharge,totalDiscount,  MerchantAmount,deliveryType,deliveryTime;
        private TextView tvEcomPayAmount,tvWalletLimit,tvTotalBalanceWallet,tvWalletDeductAmount, tvGatewayDeductAmount,tvCheckstatus,tvKeepShopping,tvordernumber;
        private LoadingDialog loadingDialog;
        WalletLimitResponse WalletResponse;
        double amount=0,walletBal=0,walletLimit=0,debitCardPercentAmount;
        boolean checkWalletBal=false,checkWalletLimit=false;
        private CheckBox checkBoxWallet, checkBoxGatewayPayment, checkBoxCOD;
        private Button btnOrderPayAmount,confirmback;
        private String customer_address1="",customer_address2="",customer_city="",customer_state="",customer_country="",customer_zipcode="",customer_phone="";
        private String merchant_udf1="",merchant_udf2="",merchant_udf3="",merchant_udf4="",merchant_udf5="";
        private String hash="",payment_mode="",AddressPOS;
        private float merchant_payment_amount= (float) 1.0;
        List<CartsQuantityResponse.CartsBean> productList = new ArrayList<>();
        public static CartsQuantityResponse.CartsBean mProductData;
        public Dialog dialog;
        OrderSaveResponse orderSaveResponse;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ecom_payment);

            findId();

            if (isNetworkAvailable()){
                walletLimitLeft();
            }else {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.payment),"No Internet Connection",true);
            }
        }

        private void findId() {

            loadingDialog = new LoadingDialog(this);
            Intent intent = getIntent();
            paidAmount = intent.getStringExtra("paid_amount");
            totalCartItem = intent.getStringExtra("total_items");
            totalTax = intent.getStringExtra("total_tax");
            totalDeliveryCharge = intent.getStringExtra("total_deliveryCharge");
            totalDiscount = intent.getStringExtra("total_discount");
            deliveryType = intent.getStringExtra("delivery_type");
            deliveryTime = intent.getStringExtra("delivery_time");

            tvEcomPayAmount = findViewById(R.id.tv_ecom_pay_amount);
            tvWalletLimit = findViewById(R.id.tv_walletlimit_ecom);
            tvTotalBalanceWallet = findViewById(R.id.tv_total_balance_wallet);
            checkBoxWallet = findViewById(R.id.check_box_wallet);
            checkBoxGatewayPayment = findViewById(R.id.check_box_gateway_payment);
            checkBoxCOD  = findViewById(R.id.check_box_COD);
            tvWalletDeductAmount = findViewById(R.id.tv_wallet_deduct_amount);
            tvGatewayDeductAmount = findViewById(R.id.tv_gateway_deduct_amount);
            btnOrderPayAmount = findViewById(R.id.btn_order_pay_amount);

            tvEcomPayAmount.setText("₹"+paidAmount);
            AddressPOS =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ADDRESS_POS);

       /*     SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            gson = new Gson();
            json = prefs.getString("MyEcomProduct", "");
            mProductData = gson.fromJson(json, CartsQuantityResponse.CartsBean.class);
            productList.add(mProductData);*/

            checkBoxCOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked){
                        checkBoxCOD.setChecked(true);
                        checkBoxWallet.setChecked(false);
                        checkBoxGatewayPayment.setChecked(false);
                        btnOrderPayAmount.setText("COD");
                    }else {
                        checkBoxCOD.setChecked(false);
                        checkBoxWallet.setChecked(false);
                        checkBoxGatewayPayment.setChecked(true);
                        btnOrderPayAmount.setText("PAY");
                    }
                }
            });


            checkBoxWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        checkBoxWallet.setChecked(true);
                        checkBoxGatewayPayment.setChecked(true);
                        tvWalletDeductAmount.setVisibility(View.VISIBLE);
                        tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                 /*       debitCardPercentAmount = (Double.parseDouble(paidAmount)*Double.parseDouble(WalletResponse.getData().getPerBank()))/100;
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(debitCardPercentAmount)));
                        tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(Double.parseDouble(paidAmount)-debitCardPercentAmount)));*/

                        debitCardPercentAmount = (Double.parseDouble(paidAmount)*Double.parseDouble(WalletResponse.getData().getPerBank()))/100;
                        double subAmount = (Double.parseDouble(paidAmount)-debitCardPercentAmount);

                        if (subAmount<walletBal){
                            tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(subAmount)));
                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(debitCardPercentAmount)));
                        } else if (subAmount>walletBal){
                            tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(walletBal)));
                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(Double.parseDouble(paidAmount)-walletBal)));
                        }
                }
                    else {
                        checkBoxWallet.setChecked(false);
                        tvWalletDeductAmount.setVisibility(View.GONE);
                        tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                        checkBoxGatewayPayment.setChecked(true);
                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(Double.parseDouble(paidAmount))));
                    }
                }
            });

            btnOrderPayAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      customerOrderSave();
                    //*************hash key ****************

                /*    MerchantAmount = tvGatewayDeductAmount.getText().toString().trim().substring(1);

                    HashKeyRequest hashKeyRequest=new HashKeyRequest();
                    hashKeyRequest.setCustomer_firstName(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME));
                    hashKeyRequest.setMerchant_payment_amount(MerchantAmount);
                    hashKeyRequest.setMerchant_productInfo("Order Booking");
                    hashKeyRequest.setCustomer_email_id(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));
                    hashKeyRequest.setType("0");
                    if (isNetworkAvailable()){
                        getHashKey(hashKeyRequest);
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
                    }*/

                }
            });
        }



        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                                    if (String.valueOf(walletLimitResponse.getData().getLimit()).equals("0")){
                                        tvWalletDeductAmount.setVisibility(View.GONE);
                                        checkBoxWallet.setChecked(false);
                                    }

                                    walletBal= Double.parseDouble(WalletResponse.getData().getWallet_balance());
                                    amount=Double.parseDouble(paidAmount);
                                    walletLimit= WalletResponse.getData().getLimit();

                                    tvTotalBalanceWallet.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format((int) Double.parseDouble(walletLimitResponse.getData().getWallet_balance())));
                                    tvWalletLimit.setText("* Today's Wallet Limit - Rs. " + new DecimalFormat("##.##").format(walletLimitResponse.getData().getLimit()));

                                    if (amount>walletBal){
                                        checkBoxWallet.setChecked(true);
                                        checkBoxGatewayPayment.setChecked(true);
                                        checkBoxGatewayPayment.setEnabled(false);

                                        tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                                        tvWalletDeductAmount.setVisibility(View.VISIBLE);

                                        debitCardPercentAmount = (Double.parseDouble(paidAmount)*Double.parseDouble(walletLimitResponse.getData().getPerBank()))/100;
                                        double subAmount = (Double.parseDouble(paidAmount)-debitCardPercentAmount);

                                        if (subAmount<walletBal){
                                            tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(subAmount)));
                                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(debitCardPercentAmount)));
                                        } else if (subAmount>walletBal){
                                            tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(walletBal)));
                                            tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(Double.parseDouble(paidAmount)-walletBal)));
                                        }
                                     /*   tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(walletBal)));
                                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(Double.parseDouble(paidAmount)-walletBal)));*/
                                        btnOrderPayAmount.setText(getResources().getString(R.string.rupees) + " " + String.format("%.2f",Double.parseDouble(paidAmount)));
                                    }else if (amount<walletBal){
                                        checkWalletBal=true;
                                        checkWalletLimit=true;
                                        checkBoxWallet.setChecked(true);
                                        checkBoxGatewayPayment.setChecked(true);
                                        checkBoxGatewayPayment.setEnabled(false);

                                        tvGatewayDeductAmount.setVisibility(View.VISIBLE);
                                        tvWalletDeductAmount.setVisibility(View.VISIBLE);

                                        debitCardPercentAmount = (Double.parseDouble(paidAmount)*Double.parseDouble(walletLimitResponse.getData().getPerBank()))/100;
                                        tvGatewayDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(debitCardPercentAmount)));
                                        tvWalletDeductAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f",(Double.parseDouble(paidAmount)-debitCardPercentAmount)));
                                        btnOrderPayAmount.setText(getResources().getString(R.string.rupees) + " " +String.format("%.2f", Double.parseDouble(paidAmount)));
                                        }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.ecom_payment),walletLimitResponse.getMessage(),false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingDialog.hideDialog();
                            BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.ecom_payment), false, e.getCause());
                        }
                    }));
        }

        //***************payment ***********************
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
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.ecom_payment),response.getMessage(),false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingDialog.hideDialog();
                            BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.ecom_payment), true, e);
                        }
                    }));
        }

        private void DoPayment(HashKeyRequest hashKeyRequest, HashKeyResponse hashKeyResponse) {

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
            merchant_payment_amount = Float.parseFloat(String.valueOf(MerchantAmount));


            hash = hashKeyResponse.getMerchant_key() + "|" + hashKeyResponse.getTransactionId() + "|" + merchant_payment_amount + "|" + hashKeyRequest.getMerchant_productInfo()
                    + "|" + hashKeyRequest.getCustomer_firstName()
                    + "|" + hashKeyRequest.getCustomer_email_id() + "|" + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|" + merchant_udf5
                    + "||||||"
                    + hashKeyResponse.getMerchant_salt() + "|" + hashKeyResponse.getMerchant_key();

            String hashKey = hashCal("SHA-512", hash);
            merchant_payment_amount= Float.parseFloat(hashKeyRequest.getMerchant_payment_amount());

            Intent intentProceed = new Intent(EcomPaymentActivity.this, PWECouponsActivity.class);
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
                                Toast.makeText(EcomPaymentActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                customerOrderSave();
                            } else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.ecom_payment), response.getMessage(), false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                            BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.ecom_payment), true, e);
                        }
                    }));
        }

        private void customerOrderSave() {

            Date currentTime = Calendar.getInstance().getTime();
            String Time[] = String.valueOf(currentTime).split("GMT");
            String t1 = Time[0];
            String t2 = Time[1];
            double AmoutWithOutTaxDiscount = (Double.parseDouble(paidAmount)- Double.parseDouble(totalTax));

            OrderSaveRequest orderSaveRequest1 = new OrderSaveRequest();
            orderSaveRequest1=AddAddressEcomActivity.orderSaveRequest ;

            orderSaveRequest1.setMerchant_id("");
            orderSaveRequest1.setVenue_id("");
            orderSaveRequest1.setSource_type("app");

            orderSaveRequest1.setOrder_date(t1);
         //   orderSaveRequest.setOrder_status();
            orderSaveRequest1.setUser_addr_id(AddressPOS);
            orderSaveRequest1.setTotal_items(Integer.parseInt(totalCartItem));
            orderSaveRequest1.setAmt_without_tax_discount((AmoutWithOutTaxDiscount));
            orderSaveRequest1.setTotal_discount(Double.parseDouble(totalTax));
            orderSaveRequest1.setTotal_tax(Double.parseDouble(totalTax));
            orderSaveRequest1.setNet_amount(Double.parseDouble(paidAmount));
            orderSaveRequest1.setDelivery_charge(Double.parseDouble(totalDeliveryCharge));
           // orderSaveRequest.setLoyelty_used_amount();
            orderSaveRequest1.setIs_gift("0");
            orderSaveRequest1.setReorder_status(0);
            orderSaveRequest1.setDelivery_type(deliveryType);
            orderSaveRequest1.setDelivery_time(deliveryTime);



            if (checkBoxCOD.isChecked()){
                orderSaveRequest1.setPayment_wallet("0");
                orderSaveRequest1.setPayment_bank("0");
                orderSaveRequest1.setPayment_mode("Cash");
            }else {
                if (amount>walletBal){
                    orderSaveRequest1.setPayment_wallet(String.valueOf(walletBal));
                    orderSaveRequest1.setPayment_bank(String.format("%.2f",(Double.parseDouble(paidAmount)-walletBal)));
                    orderSaveRequest1.setPayment_mode("Card & Wallet");
                } else {
                    if (checkBoxWallet.isChecked()&& checkBoxGatewayPayment.isChecked()){
                        debitCardPercentAmount = (Double.parseDouble(paidAmount)*Double.parseDouble(WalletResponse.getData().getPerBank()))/100;
                        orderSaveRequest1.setPayment_wallet(String.format("%.2f",(Double.parseDouble(paidAmount)-debitCardPercentAmount)));
                        orderSaveRequest1.setPayment_bank(String.format("%.2f",(debitCardPercentAmount)));
                        orderSaveRequest1.setPayment_mode("Card & Wallet");
                    }else if (checkBoxGatewayPayment.isChecked()){
                        orderSaveRequest1.setPayment_wallet("0");
                        orderSaveRequest1.setPayment_bank(paidAmount);
                        orderSaveRequest1.setPayment_mode("Card");
                    }
                }
            }



            loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
            ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);
            BaseApp.getInstance().getDisposable().add(apiService.getCustomerOrderSave(orderSaveRequest1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<OrderSaveResponse>() {
                        @Override
                        public void onSuccess(OrderSaveResponse response) {
                            loadingDialog.hideDialog();
                            orderSaveResponse = response;
                            if (response.isStatus()) {
                                Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                              /*  Intent intent = new Intent(EcomPaymentActivity.this, MyOrderEcomActivity.class);
                                startActivity(intent);*/
                                showDialogOrderConfirm(EcomPaymentActivity.this);
                            }
                            Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingDialog.hideDialog();
                            BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.ecom_payment), false, e.getCause());
                        }
                    }));


        }

        private void showDialogOrderConfirm(EcomPaymentActivity ecomPaymentActivity) {
            dialog = new Dialog(ecomPaymentActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.order_confirm_dialog);

            tvCheckstatus = dialog.findViewById(R.id.tv_checkMyOrder);
            tvKeepShopping = dialog.findViewById(R.id.tv_keep_shopping);
            confirmback = dialog.findViewById(R.id.ordercomfirm_ecom_back);
            tvordernumber = dialog.findViewById(R.id.tvordernumber);
            tvordernumber.setText("Order Number : "+orderSaveResponse.getOrder().getUnique_code());

            tvCheckstatus.setOnClickListener(this);
            confirmback.setOnClickListener(this);
            tvKeepShopping.setOnClickListener(this);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            lp.copyFrom(window.getAttributes());
            //This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            dialog.show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_keep_shopping:
                    startActivity(new Intent(EcomPaymentActivity.this, EHomeActivity.class));
                    dialog.dismiss();
                    finish();
                    break;
                case R.id.tv_checkMyOrder:
                    startActivity(new Intent(EcomPaymentActivity.this, MyOrderEcomActivity.class));
                    dialog.dismiss();
                    finish();
                    break;
                case R.id.ordercomfirm_ecom_back:
                    startActivity(new Intent(EcomPaymentActivity.this, MyOrderEcomActivity.class));
                    overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                    finish();
                    break;
            }
        }
    }
