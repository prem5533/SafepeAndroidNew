package com.safepayu.wallet.activity.recharge;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.LoginActivity;
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.adapter.OfferAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.CustOperatorResponse;
import com.safepayu.wallet.models.response.Offer;
import com.safepayu.wallet.models.response.OperatorResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MobileRecharge extends BaseActivity implements OfferAdapter.OnOfferSelectListener {

    private Button RechargeBtn, BackBtn;
    private TextView OffersBtn;
    private Spinner OperatorSpinner;
    public static EditText AmountED;
    private EditText MobileED;
    String OperatorText = "", OperatorCode = "", OperatorId = "";
    private LoadingDialog loadingDialog;
    private ArrayList<String> OperatorNameList, IdList, OperatorCodeList;
    OfferAdapter adapter;
    SuperRecyclerView recyclerView;
    Dialog dialog;
    ArrayList<Offer> offers;
    private TextView AmountTotalTV;
    double totalAmount = 0.0f, minusAmount = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blue_theme));
        }


        loadingDialog = new LoadingDialog(this);
        dialog = new Dialog(this);
        offers = new ArrayList<>();

        RechargeBtn = findViewById(R.id.recharge_btn);
        BackBtn = findViewById(R.id.recharge_back_btn);
        OffersBtn = findViewById(R.id.Offersbtn);
        OperatorSpinner = findViewById(R.id.operator);
        AmountED = findViewById(R.id.amountRecharge);
        MobileED = findViewById(R.id.mobile_recharge);
        AmountTotalTV = findViewById(R.id.calculatedamount);

        OperatorNameList = new ArrayList<>();
        IdList = new ArrayList<>();
        OperatorCodeList = new ArrayList<>();

        OperatorNameList.clear();
        IdList.clear();
        OperatorCodeList.clear();

        OperatorNameList.add("Select Operator");
        IdList.add("0");
        OperatorCodeList.add("0");

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        OffersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(MobileED.getText().toString().trim())) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Your Mobile Number", false);
                } else {
                    if (MobileED.getText().toString().trim().length() == 10) {
                        if (isNetworkAvailable()) {
                            if (offers.isEmpty()) {
                                new GetOfferData().execute();
                            } else {
                                dialog.show();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Check Your Internet Connection", false);
                        }

                    } else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Your Correct Mobile Number", false);
                    }
                }

            }
        });

        RechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* startActivity(new Intent(getApplicationContext(), PaymentType.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();*/

                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Buy Membership To Enjoy App's Features", false);
                } else {
                    CheckValidate();
                }
            }
        });

        OperatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OperatorText = OperatorSpinner.getItemAtPosition(i).toString();
                OperatorCode = OperatorCodeList.get(i);
                OperatorId = IdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MobileED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                if (s.length() == 10) {
                    getCustomerOperator();

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


        AmountED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String Amount = AmountED.getText().toString().trim();
                if (!Amount.equals("")) {
                    int num = Integer.parseInt(Amount);
                    if (num <=1000) {
                    CalculateAmount(num);
                        String text = AmountED.getText().toString().trim() + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                        AmountTotalTV.setText(text + String.format("%.2f", totalAmount)); }

                    else if (num>1000){
                        CalculateAmount1Per(num);
                        String text = AmountED.getText().toString().trim()  + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                        AmountTotalTV.setText(text + String.format("%.2f", totalAmount)); }

                    else {
                        AmountTotalTV.setText("0.0");
                    }
                }
                else {
                    AmountTotalTV.setText(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (isNetworkAvailable()) {
            getAllOperators();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Check Your Internet Connection", false);
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
        return R.layout.mobile_recharge;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (!isConnected) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Check Your Internet Connection", false);
        }
    }


    private void CheckValidate() {
        int Amount = 0;
        String Mobile = "";
        try {
            Mobile = MobileED.getText().toString().trim();
             Amount= Integer.parseInt(AmountED.getText().toString().trim());

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(MobileED.getText().toString().trim())) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Mobile Number", false);
        } else {
            if (TextUtils.isEmpty(AmountED.getText().toString().trim())) {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Amount", false);
            } else {
                if (Mobile.length() == 10) {
                    if (Amount == 0 || Amount < 0) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Amount", false);
                    } else {

                        if (TextUtils.isEmpty(OperatorCode) || OperatorCode.equals("0")) {

                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Select Operator", false);
                        } else {
                            Intent intent = new Intent(MobileRecharge.this, PaymentType.class);
                            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                            intent.putExtra("RechargePaymentId", Mobile);
                            intent.putExtra("Amount", String.valueOf(Amount));
                            intent.putExtra("PaymentType", "Recharge");
                            intent.putExtra("PaymentFor", "Mobile");
                            intent.putExtra("RechargeTypeId", "1");
                            intent.putExtra("OperatorCode", OperatorCode);
                            intent.putExtra("CircleCode", "51");
                            intent.putExtra("OperatorId", OperatorId);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Correct Mobile Number", false);
                }
            }
        }
    }

    private void getAllOperators() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getOperators("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OperatorResponse>() {
                    @Override
                    public void onSuccess(OperatorResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            for (int i = 0; i < response.getOperators().size(); i++) {
                                OperatorNameList.add(response.getOperators().get(i).getOperator_name());
                                IdList.add(String.valueOf(response.getOperators().get(i).getId()));
                                OperatorCodeList.add(response.getOperators().get(i).getOperator_code());
                            }

                            ArrayAdapter<String> TransferType = new ArrayAdapter<>(MobileRecharge.this, android.R.layout.simple_spinner_item, OperatorNameList);
                            TransferType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            OperatorSpinner.setAdapter(TransferType);
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.mobileRechargeLayout), true, e);
                    }
                }));

    }

    private void getCustomerOperator() {
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getMobileOperator(MobileED.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CustOperatorResponse>() {
                    @Override
                    public void onSuccess(CustOperatorResponse response) {

                        if (response.isStatus()) {

                            try {
                                OperatorText = response.getOperator().getOperator_name();
                                OperatorCode = response.getOperator().getOperator_code();
                                int indexx = OperatorCodeList.indexOf(OperatorCode);
                                OperatorId = IdList.get(indexx);
                                OperatorSpinner.setSelection(indexx);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.mobileRechargeLayout), true, e);
                    }
                }));
    }

    @Override
    public void onOfferSelect(int position, Offer offer) {
        AmountED.setText(offer.getAmount());
        AmountED.setSelection(offer.getAmount().length());
        dialog.dismiss();
    }

    public class GetOfferData extends AsyncTask<String, String, String> {
        String apiUrl = "http://api.sakshamapp.com/MobileOffer?mobile=" + MobileED.getText().toString().trim();


        protected void onPreExecute() {
            loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        }

        protected String doInBackground(String... urls) {

            String result = null;
            try {
                URL url = new URL(apiUrl);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();

                con.setAllowUserInteraction(false);
                con.setInstanceFollowRedirects(true);
                // con.setRequestMethod("POST");
                con.connect();
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try {
                        InputStream in = new BufferedInputStream(con.getInputStream());

                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(in));

                        String json;

                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    result = sb.toString().trim();
                } else {

                    //    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                // return null;
            }
            return result;
        }

        protected void onPostExecute(String response) {
            loadingDialog.hideDialog();

            offers.clear();
            if (response.length() > 0) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Offer offer = new Offer();
                            offer.setCategory(obj.getString("category"));
                            offer.setSubCategory(obj.getString("subCategory"));
                            offer.setValidity(obj.getString("validity"));
                            offer.setShortdesc(obj.getString("description"));
                            offer.setAmount(obj.getString("amount"));
                            offer.setTalktime(obj.getString("talktime"));
                            offers.add(offer);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    SetOffersDialog(offers);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void SetOffersDialog(ArrayList<Offer> offers) {
        dialog.setContentView(R.layout.dialog_offer);
        dialog.setTitle("Select Offer");
        dialog.setCancelable(false);

        recyclerView = (SuperRecyclerView) dialog.findViewById(R.id.list);
        Button cancelBtn = (Button) dialog.findViewById(R.id.cancel);

        TabLayout tabs = (TabLayout) dialog.findViewById(R.id.tabLayout);
        tabs.addTab(tabs.newTab().setText("FULL TALKTIME")); // FTT
        tabs.addTab(tabs.newTab().setText("Top Up"));  // TUP
        /*tabs.addTab(tabs.newTab().setText("4G"));*/
        tabs.addTab(tabs.newTab().setText("3G"));  // 3G
        tabs.addTab(tabs.newTab().setText("2G"));  // 2G
        tabs.addTab(tabs.newTab().setText("ROAMING"));   // RMG
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() == "FULL TALKTIME") {

                    adapter.setOfferType("FULL TT");
                } else if (tab.getText() == "Top Up") {

                    adapter.setOfferType("Topup");
                } else if (tab.getText() == "3G") {

                    adapter.setOfferType("3G/4G");
                } else if (tab.getText() == "2G") {

                    adapter.setOfferType("2G");
                } else if (tab.getText() == "ROAMING") {

                    adapter.setOfferType("");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new OfferAdapter(MobileRecharge.this, offers, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private double CalculateAmount(int num) {

        int checkAmount = 0;

        minusAmount = ((((double) num) / 100) * 3);
        totalAmount = (double) num - minusAmount;
        checkAmount = (int) minusAmount;

        return totalAmount;
    }

    private double CalculateAmount1Per(int num) {
     //   double totalAmount = 0.0f, minusAmount = 0.0f;
        int checkAmount = 0;

        minusAmount = ((((double) num) / 100) * 1);
        totalAmount = (double) num - minusAmount;
        checkAmount = (int) minusAmount;
     /*   if (checkAmount > 9) {

        } else {
            totalAmount = (double) num - (double) 10;
        }*/

        return totalAmount;
    }

}