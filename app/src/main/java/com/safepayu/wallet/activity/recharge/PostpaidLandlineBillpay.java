package com.safepayu.wallet.activity.recharge;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.LoginActivity;
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.CustOperatorResponse;
import com.safepayu.wallet.models.response.OperatorResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PostpaidLandlineBillpay extends BaseActivity {

    Button PayBtn,BackBtn,BillCheckBtn ;
    TextView textView;
    private Spinner OperatorSpinner;
    private EditText MobileED ,AmountEd;
    String OperatorText="",OperatorCode="",OperatorId="";
    private LoadingDialog loadingDialog;
    private ArrayList<String> OperatorNameList,IdList,OperatorCodeList;
    double totalAmount = 0.0f, minusAmount = 0.0f;
    private TextView AmountTotalTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);

        PayBtn = findViewById(R.id.Paybtn);
        BackBtn = findViewById(R.id.postpaid_back_btn);
        BillCheckBtn = findViewById(R.id.btnCheckBill);
        textView = findViewById(R.id.textView);
        AmountEd = findViewById(R.id.amountPostpaid);
        OperatorSpinner = findViewById(R.id.operatorPostpaid);
        MobileED = findViewById(R.id.mobilePostpaid);
        AmountTotalTV = findViewById(R.id.calculatedamount_bill);

        OperatorNameList=new ArrayList<>();
        IdList=new ArrayList<>();
        OperatorCodeList=new ArrayList<>();

        OperatorNameList.clear();
        IdList.clear();
        OperatorCodeList.clear();

        OperatorNameList.add("Select Operator");
        IdList.add("0");
        OperatorCodeList.add("0");

        OperatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OperatorText=OperatorSpinner.getItemAtPosition(i).toString();
                OperatorCode=OperatorCodeList.get(i);
                OperatorId=IdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MobileED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                if (s.length()==10){
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

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        PayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),"Please Buy Membership To Enjoy App's Features",false);
                }else {
                    CheckValidate();
                }
            }
        });

        BillCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.GONE);
                AmountEd.setVisibility(View.VISIBLE);
                BillCheckBtn.setVisibility(View.GONE);
                PayBtn.setVisibility(View.VISIBLE);

            }
        });

        AmountEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Amount = AmountEd.getText().toString().trim();
                if (!Amount.equals("")) {
                    int num = Integer.parseInt(Amount);
                    if (num <=1000) {
                        CalculateAmount(num);
                        String text = AmountEd.getText().toString().trim() + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                        AmountTotalTV.setText(text + String.format("%.2f", totalAmount)); }

                    else if (num>1000){
                        CalculateAmount1Per(num);
                        String text = AmountEd.getText().toString().trim()  + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
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

        if (isNetworkAvailable()){
            getAllOperators();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),"Check Your Internet Connection",false);
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
        return R.layout.postpaid_landline_billpay;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void CheckValidate(){
        int Amount=0;
        String Mobile="";
        try{
            Mobile= MobileED.getText().toString().trim();
            Amount= Integer.parseInt(AmountEd.getText().toString().trim());
        }catch (Exception e){
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(MobileED.getText().toString().trim())){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),"Please Enter Mobile Number",false);
        }else {
            if (TextUtils.isEmpty(AmountEd.getText().toString().trim())){
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),"Please Enter Amount",false);
            }else {
                if (Mobile.length()>9 || Mobile.length()<0){
                    if (Amount==0 || Amount<0){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),"Please Enter Amount",false);
                    }else {

                        if (TextUtils.isEmpty(OperatorCode) || OperatorCode.equals("0")){

                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),"Please Select Operator",false);
                        }else {
                            Intent intent=new Intent(PostpaidLandlineBillpay.this,PaymentType.class);
                            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                            intent.putExtra("RechargePaymentId",Mobile);
                            intent.putExtra("Amount",String.valueOf(Amount));
                            intent.putExtra("PaymentType","Bill Payment");
                            intent.putExtra("PaymentFor","Postpaid");
                            intent.putExtra("RechargeTypeId","6");
                            intent.putExtra("OperatorCode",OperatorCode);
                            intent.putExtra("CircleCode","51");
                            intent.putExtra("OperatorId",OperatorId);
                            startActivity(intent);
                            finish();
                        }
                    }
                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),"Please Enter Correct Mobile Number",false);
                }
            }
        }
    }

    private void getAllOperators(){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getOperators("6")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OperatorResponse>() {
                    @Override
                    public void onSuccess(OperatorResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            for (int i=0;i<response.getOperators().size();i++){
                                OperatorNameList.add(response.getOperators().get(i).getOperator_name());
                                IdList.add(String.valueOf(response.getOperators().get(i).getId()));
                                OperatorCodeList.add(response.getOperators().get(i).getOperator_code());
                            }

                            ArrayAdapter<String> TransferType= new ArrayAdapter<>(PostpaidLandlineBillpay.this,android.R.layout.simple_spinner_item,OperatorNameList);
                            TransferType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            OperatorSpinner.setAdapter(TransferType);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.postpaidBillLayout), true, e);
                    }
                }));

    }

    private void getCustomerOperator(){
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getMobileOperator(MobileED.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CustOperatorResponse>() {
                    @Override
                    public void onSuccess(CustOperatorResponse response) {

                        if (response.isStatus()) {
                            try{
                                OperatorText=response.getOperator().getOperator_name();
                                OperatorCode=response.getOperator().getOperator_code();
                                int indexx=OperatorCodeList.indexOf(OperatorCode);
                                OperatorId=IdList.get(indexx);
                                OperatorSpinner.setSelection(indexx);
                            }catch (Exception e){
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

        return totalAmount;
    }
}


