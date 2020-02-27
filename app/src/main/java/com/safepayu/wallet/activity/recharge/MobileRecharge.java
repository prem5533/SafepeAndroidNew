package com.safepayu.wallet.activity.recharge;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.Navigation;
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.activity.PaymentTypeNew;
import com.safepayu.wallet.adapter.OfferAdapter;
import com.safepayu.wallet.adapter.ServiceHistoryAdapter;
import com.safepayu.wallet.adapter.SpinnerAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.CustOperatorResponse;
import com.safepayu.wallet.models.response.Offer;
import com.safepayu.wallet.models.response.OperatorResponse;
import com.safepayu.wallet.models.response.RechargePlanResponse;

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
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.LoginActivity.finalAmount;

public class MobileRecharge extends BaseActivity implements OfferAdapter.OnOfferSelectListener, ServiceHistoryAdapter.OnSelectListener {

    private Button RechargeBtn, BackBtn;
    private TextView OffersBtn,tvRechargeamount,tvWalletCashback,tvTotalAmountpay,tvViewAllBtn,tvViewLessBtn;
    private TextView tvRechargeAmtTax,tvServiceChargeTax,tvAmt2PayTax,tvPreviousOrderText;
    private Spinner OperatorSpinner;
    public static EditText AmountED;
    private EditText MobileED;
    private RecyclerView RechargeHistoryListView;
    private String OperatorText,OperatorCode="0",OperatorId,OperatorCodeSelected="",Amount2Pay="";
    private boolean checkOnce=false;
    private ServiceHistoryAdapter historyAdapter;

    private LoadingDialog loadingDialog;
    private ArrayList<String> OperatorNameList, IdList, OperatorCodeList,OperatorImage;
    private OfferAdapter adapter;
    private SuperRecyclerView recyclerView;
    private Dialog dialog;
    private ArrayList<Offer> offers;
    private TextView AmountTotalTV;
    double totalAmount = 0.0f, minusAmount = 0.0f;
    private CardView cardAmount;
    private RelativeLayout ServiceChargeLayout;
    private List<OperatorResponse.OperatorsBean> mOperList = new ArrayList<>();
    private LinearLayout layoutSelectMobileOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.red_theme));
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
        tvRechargeamount = findViewById(R.id.tv_rechargeamount);
        tvWalletCashback = findViewById(R.id.tv_walletcashback);
        tvTotalAmountpay = findViewById(R.id.tv_total_amountpay);
        cardAmount = findViewById(R.id.card_amount);
        ServiceChargeLayout= findViewById(R.id.serviceChargeLayout1);
        layoutSelectMobileOperator = findViewById(R.id.layout_select_mobile_operator);
        tvRechargeAmtTax= findViewById(R.id.tv_rechargeAmount_serviceChargeLayout);
        tvServiceChargeTax= findViewById(R.id.tv_serviceCharge_serviceChargeLayout);
        tvAmt2PayTax= findViewById(R.id.tv_totalAmt_serviceChargeLayout);
        tvPreviousOrderText=findViewById(R.id.orderPreviousText);
        tvViewAllBtn=findViewById(R.id.orderViewAllText);
        tvViewLessBtn=findViewById(R.id.orderViewLessText);
        RechargeHistoryListView = findViewById(R.id.list_rechargeHistory);

        RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        RechargeHistoryListView.setLayoutManager(layoutManager);


        OperatorNameList=new ArrayList<>();
        IdList=new ArrayList<>();
        OperatorCodeList=new ArrayList<>();

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
        layoutSelectMobileOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSelectMobileOperator.setVisibility(View.GONE);
                OperatorSpinner.setVisibility(View.VISIBLE);
            }
        });

        Navigation.sizeMobileRecharge=0;
        if (Navigation.sizeMobileRecharge==0){
            tvViewAllBtn.setVisibility(View.VISIBLE);
            tvViewLessBtn.setVisibility(View.GONE);
        }else {
            tvViewAllBtn.setVisibility(View.GONE);
            tvViewLessBtn.setVisibility(View.VISIBLE);
        }

        tvViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.sizeMobileRecharge=1;
                tvViewAllBtn.setVisibility(View.GONE);
                tvViewLessBtn.setVisibility(View.VISIBLE);
                historyAdapter.notifyDataSetChanged();
            }
        });

        tvViewLessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.sizeMobileRecharge=0;
                tvViewAllBtn.setVisibility(View.VISIBLE);
                tvViewLessBtn.setVisibility(View.GONE);
                historyAdapter.notifyDataSetChanged();
            }
        });

        OffersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(MobileED.getText().toString().trim())) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Your Mobile Number", false);
                } else {
                    if (MobileED.getText().toString().trim().length() == 10) {
                        if (TextUtils.isEmpty(OperatorCode) || OperatorCode.equals("0")) {

                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Select Operator", false);
                        }else {
                            if (isNetworkAvailable()) {
//                                if (offers.isEmpty()) {
//                                    //new GetOfferData().execute();
//                                    getCustomerOffer();
//                                } else {
//                                    if (OperatorCodeSelected.equals(OperatorCode)){
//                                        dialog.show();
//                                    }else {
//                                        getCustomerOffer();
//                                    }
//
//                                }

                                getCustomerOffer();
                            } else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Check Your Internet Connection", false);
                            }
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

                try {
//                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")) {
//                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Buy Membership To Enjoy App's Features", false);
//                    } else {
//                        CheckValidate();
//                    }
                    CheckValidate();
                }catch (Exception e){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Buy Membership To Enjoy App's Features", false);
                    e.printStackTrace();
                }
            }
        });


        MobileED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                try {
                    if (s.length() == 10) {
                        getCustomerOperator();

                    }
                }catch (Exception e){
                    e.printStackTrace();
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
                    CalculateServiceCharge(Amount);
                    ServiceChargeLayout.setVisibility(View.VISIBLE);
                }else {
                    ServiceChargeLayout.setVisibility(View.GONE);
                }
                if (!Amount.equals("")) {

                    Double totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(Double.parseDouble(Amount), Double.parseDouble("2"));
                    int num = Integer.parseInt(Amount);
                    if (num <=1000) {
                    CalculateAmount(num);
                        String text = AmountED.getText().toString().trim() + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                      //  AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                        //cardAmount.setVisibility(View.VISIBLE);
                        cardAmount.setVisibility(View.GONE);
                    tvRechargeamount.setText(AmountED.getText().toString().trim()+" "+getResources().getString(R.string.rupees));
                    tvWalletCashback.setText( " -  "+new DecimalFormat("##.##").format(minusAmount)+" "+getResources().getString(R.string.rupees));
                    tvTotalAmountpay.setText(String.format("%.2f", totalAmount)+" "+getResources().getString(R.string.rupees));

                    } else if (num>1000){
                        CalculateAmount1Per(num);
                        String text = AmountED.getText().toString().trim()  + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                       // AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                        tvRechargeamount.setText(AmountED.getText().toString().trim()+" "+getResources().getString(R.string.rupees));
                        tvWalletCashback.setText( " -  "+new DecimalFormat("##.##").format(minusAmount)+" "+getResources().getString(R.string.rupees));
                        tvTotalAmountpay.setText(String.format("%.2f", totalAmount)+" "+getResources().getString(R.string.rupees));}


                    else {
                      //  AmountTotalTV.setText("0.0");
                    }
                    cardAmount.setVisibility(View.GONE);
                } else {
                  //  AmountTotalTV.setText(" ");
                    cardAmount.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        OperatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (checkOnce){

                }else {
                    checkOnce=true;
                }
                if (i!=0){
                    OperatorText=mOperList.get(i).getOperator_name();
                    OperatorCode= mOperList.get(i).getOperator_code();
                    OperatorId= String.valueOf(mOperList.get(i).getId());
                    OperatorCodeSelected=OperatorCode;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                checkOnce=false;
                OperatorCode="0";
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

    private void CalculateServiceCharge(String Amt){
        String Tax=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().SERVICE_CHARGE);
        try {
            Double totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(Double.parseDouble(Amt.trim()), Double.parseDouble(Tax));
            tvAmt2PayTax.setText(getResources().getString(R.string.rupees)+" "+totalPayableAmount);
            Amount2Pay=String.valueOf(totalPayableAmount);
            finalAmount=totalPayableAmount;
        }catch (Exception e){
            tvAmt2PayTax.setText(getResources().getString(R.string.rupees)+" "+0);
            Amount2Pay="0";
            e.printStackTrace();
        }

        tvRechargeAmtTax.setText(getResources().getString(R.string.rupees)+" "+Amt);
        tvServiceChargeTax.setText(Tax+"%");
    }


    private void CheckValidate() {
        int Amount = 0;
        String Mobile = "";
        try {
            Mobile = MobileED.getText().toString().trim();
            Amount= Integer.parseInt(AmountED.getText().toString().trim());

        } catch (Exception e) {
            Amount=0;
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
                            Intent intent;
                            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PAYMENT_SCREEN).equals("0")) {

                                intent = new Intent(MobileRecharge.this, PaymentTypeNew.class);
                            }else {
                                intent = new Intent(MobileRecharge.this, PaymentType.class);
                            }

                            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                            intent.putExtra("RechargePaymentId", Mobile);
                            intent.putExtra("Amount", String.valueOf(Amount));
                            intent.putExtra("PaymentType", "Recharge");
                            intent.putExtra("PaymentFor", "Mobile");
                            intent.putExtra("RechargeTypeId", "1");
                            intent.putExtra("OperatorCode", OperatorCode);
                            intent.putExtra("CircleCode", "51");
                            intent.putExtra("OperatorId", OperatorId);
                            intent.putExtra("walletCashback", tvWalletCashback.getText().toString());
                            intent.putExtra("totalAmount", tvTotalAmountpay.getText().toString());
                            startActivity(intent);
                          //  finish();
                        }
                    }
                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), "Please Enter Correct Mobile Number", false);
                }
            }
        }
    }

    private void getAllOperators() {

//        final OperatorResponse.OperatorsBean operatorsBean=new OperatorResponse.OperatorsBean();
//        operatorsBean.setId(0);
//        operatorsBean.setImage("");
//        operatorsBean.setOperator_code("0");
//        operatorsBean.setOperator_name("Select Operator");
//        mOperList.add(operatorsBean);

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

                            if (response.getOperators().size()>0){
                                mOperList=response.getOperators();

//                            for (int i = 0; i < response.getOperators().size(); i++) {
//                                operatorsBean.setId(response.getOperators().get(i).getId());
//                                operatorsBean.setImage(response.getOperators().get(i).getImage());
//                                operatorsBean.setOperator_code(response.getOperators().get(i).getOperator_code());
//                                operatorsBean.setOperator_name(response.getOperators().get(i).getOperator_name());
//                                mOperList.add(operatorsBean);
//                            }

                                SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),mOperList);
                                OperatorSpinner.setAdapter(customAdapter);

                                try {
                                    if (response.getHistory().size()>0){
                                        tvPreviousOrderText.setVisibility(View.VISIBLE);
                                        historyAdapter=new ServiceHistoryAdapter(MobileRecharge.this,response.getHistory(),MobileRecharge.this);
                                        RechargeHistoryListView.setAdapter(historyAdapter);
                                    }else {
                                        tvPreviousOrderText.setVisibility(View.GONE);
                                        tvViewAllBtn.setVisibility(View.GONE);
                                        tvViewLessBtn.setVisibility(View.GONE);
                                    }
                                }catch (Exception e1){
                                    tvPreviousOrderText.setVisibility(View.GONE);
                                    tvViewAllBtn.setVisibility(View.GONE);
                                    tvViewLessBtn.setVisibility(View.GONE);
                                    e1.printStackTrace();
                                }
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout),"No Operator Found", false);
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.mobileRechargeLayout), true, e);
                    }
                }));
    }

    @Override
    public void onOrderItemSelect(int position, OperatorResponse.HistoryBean selectOrderItem) {
       // Toast.makeText(this, selectOrderItem.getNumber(), Toast.LENGTH_SHORT).show();
        OperatorText=selectOrderItem.getOperator_name();

        for (int j=0;j<mOperList.size();j++){
            if (OperatorText.equalsIgnoreCase(mOperList.get(j).getOperator_name())){
                OperatorCode= mOperList.get(j).getOperator_code();
                OperatorCodeSelected=OperatorCode;
                OperatorSpinner.setSelection(j);
                AmountED.setText(""+selectOrderItem.getAmount());
                MobileED.setText(selectOrderItem.getNumber());
                break;
            }
        }
        MobileED.requestFocus();
        OperatorId= selectOrderItem.getOperator_id();
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
//                                OperatorText = response.getOperator().getOperator_name();
//                                OperatorCode = response.getOperator().getOperator_code();
//                                int indexx = OperatorCodeList.indexOf(OperatorCode);
//                                OperatorId = IdList.get(indexx);
//                                OperatorSpinner.setSelection(indexx);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
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

    private void getCustomerOffer() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getRechargePlan(MobileED.getText().toString().trim(),OperatorCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RechargePlanResponse>() {
                    @Override
                    public void onSuccess(RechargePlanResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            try {

                                if (response.getData().getDATA().size()>0){
                                    for (int i=0;i<response.getData().getDATA().size();i++){
                                        Offer offer = new Offer();
                                        offer.setCategory("3G/4G");
                                        offer.setSubCategory("");
                                        offer.setValidity(response.getData().getDATA().get(i).getValidity());
                                        offer.setShortdesc(response.getData().getDATA().get(i).getDetail());
                                        offer.setAmount(response.getData().getDATA().get(i).getAmount());
                                        offer.setTalktime(response.getData().getDATA().get(i).getTalktime());
                                        offers.add(offer);
                                    }
                                }

                                if (response.getData().getRMG().size()>0){
                                    for (int i=0;i<response.getData().getRMG().size();i++){
                                        Offer offer = new Offer();
                                        offer.setCategory("ROAMING");
                                        offer.setSubCategory("");
                                        offer.setValidity(response.getData().getRMG().get(i).getValidity());
                                        offer.setShortdesc(response.getData().getRMG().get(i).getDetail());
                                        offer.setAmount(response.getData().getRMG().get(i).getAmount());
                                        offer.setTalktime(response.getData().getRMG().get(i).getTalktime());
                                        offers.add(offer);
                                    }
                                }


                                if (response.getData().getTUP().size()>0){
                                    for (int i=0;i<response.getData().getTUP().size();i++){
                                        Offer offer = new Offer();
                                        offer.setCategory("Topup");
                                        offer.setSubCategory("");
                                        offer.setValidity(response.getData().getTUP().get(i).getValidity());
                                        offer.setShortdesc(response.getData().getTUP().get(i).getDetail());
                                        offer.setAmount(response.getData().getTUP().get(i).getAmount());
                                        offer.setTalktime(response.getData().getTUP().get(i).getTalktime());
                                        offers.add(offer);
                                    }
                                }


                                if (response.getData().getSPL().size()>0){
                                    for (int i=0;i<response.getData().getSPL().size();i++){
                                        Offer offer = new Offer();
                                        offer.setCategory("SPECIAL");
                                        offer.setSubCategory("");
                                        offer.setValidity(response.getData().getSPL().get(i).getValidity());
                                        offer.setShortdesc(response.getData().getSPL().get(i).getDetail());
                                        offer.setAmount(response.getData().getSPL().get(i).getAmount());
                                        offer.setTalktime(response.getData().getSPL().get(i).getTalktime());
                                        offers.add(offer);
                                    }
                                }

                                if (response.getData().getFTT().size()>0){
                                    for (int i=0;i<response.getData().getSPL().size();i++){
                                        Offer offer = new Offer();
                                        offer.setCategory("FULL TT");
                                        offer.setSubCategory("");
                                        offer.setValidity(response.getData().getSPL().get(i).getValidity());
                                        offer.setShortdesc(response.getData().getSPL().get(i).getDetail());
                                        offer.setAmount(response.getData().getSPL().get(i).getAmount());
                                        offer.setTalktime(response.getData().getSPL().get(i).getTalktime());
                                        offers.add(offer);
                                    }
                                }


                                SetOffersDialog(offers);

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.mobileRechargeLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.mobileRechargeLayout), true, e);
                    }
                }));
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

        tabs.addTab(tabs.newTab().setText("SPECIAL"));  // special
        tabs.addTab(tabs.newTab().setText("3G"));  // 3G
        tabs.addTab(tabs.newTab().setText("Top Up"));  // TUP
        /*tabs.addTab(tabs.newTab().setText("4G"));*/
        tabs.addTab(tabs.newTab().setText("ROAMING"));   // RMG
        tabs.addTab(tabs.newTab().setText("FULL TALKTIME")); // FTT
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() == "FULL TALKTIME") {

                    adapter.setOfferType("FULL TT");
                } else if (tab.getText() == "Top Up") {

                    adapter.setOfferType("Topup");
                } else if (tab.getText() == "3G") {

                    adapter.setOfferType("3G/4G");
                } else if (tab.getText() == "SPECIAL") {

                    adapter.setOfferType("SPECIAL");
                } else if (tab.getText() == "ROAMING") {

                    adapter.setOfferType("ROAMING");
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