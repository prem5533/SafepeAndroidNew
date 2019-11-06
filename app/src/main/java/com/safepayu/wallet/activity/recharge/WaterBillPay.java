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

public class WaterBillPay extends BaseActivity {

    Button WaterPaybtn,BackBtn;
    private Spinner OperatorSpinner;
    private EditText AmountED,WaterIdED;
    String OperatorText="",OperatorCode="",OperatorId="";
    private LoadingDialog loadingDialog;
    private ArrayList<String> OperatorNameList,IdList,OperatorCodeList;
    private TextView AmountTotalTV;
    double totalAmount = 0.0f, minusAmount = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);
        
        OperatorSpinner=findViewById(R.id.operatorWater);
        AmountED=findViewById(R.id.amountWater);
        WaterIdED=findViewById(R.id.customeridWater);
        WaterPaybtn=findViewById(R.id.waterPaybtn);
        BackBtn=findViewById(R.id.water_back_btn);
        AmountTotalTV = findViewById(R.id.calculatedamount);

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

        WaterPaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.waterBillLayout),"Please Buy Membership To Enjoy App's Features",false);
                }else {
                    CheckValidate();
                }
            }
        });

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

        WaterIdED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                if (s.length()==10 || s.length()>10){
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
                        CalculateAmount2Per(num);
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

        if (isNetworkAvailable()){
            getAllOperators();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.waterBillLayout),"Check Your Internet Connection",false);
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
        return R.layout.water_bill_pay;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void CheckValidate(){
        int Amount=0;
        String WaterBillID="";
        try{
            WaterBillID= WaterIdED.getText().toString().trim();
            Amount= Integer.parseInt(AmountED.getText().toString().trim());
        }catch (Exception e){
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(WaterIdED.getText().toString().trim())){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.waterBillLayout),"Please Enter DTH Number",false);
        }else {
            if (TextUtils.isEmpty(AmountED.getText().toString().trim())){
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.waterBillLayout),"Please Enter Amount",false);
            }else {
                if (Amount==0 || Amount<0){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.waterBillLayout),"Please Enter Amount",false);
                }else {

                    if (TextUtils.isEmpty(OperatorCode) || OperatorCode.equals("0")){

                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.waterBillLayout),"Please Select Operator",false);
                    }else {
                        Intent intent=new Intent(WaterBillPay.this,PaymentType.class);
                        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                        intent.putExtra("RechargePaymentId",WaterBillID);
                        intent.putExtra("Amount",String.valueOf(Amount));
                        intent.putExtra("PaymentType","Bill Payment");
                        intent.putExtra("PaymentFor","Water");
                        intent.putExtra("RechargeTypeId","4");
                        intent.putExtra("OperatorCode",OperatorCode);
                        intent.putExtra("CircleCode","51");
                        intent.putExtra("OperatorId",OperatorId);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }
    }

    private void getAllOperators(){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getOperators("4")
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

                            ArrayAdapter<String> TransferType= new ArrayAdapter<>(WaterBillPay.this,android.R.layout.simple_spinner_item,OperatorNameList);
                            TransferType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            OperatorSpinner.setAdapter(TransferType);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.waterBillLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.waterBillLayout), true, e);
                    }
                }));

    }

    private void getCustomerOperator(){
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getMobileOperator(WaterIdED.getText().toString().trim())
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

    private double CalculateAmount2Per(int num) {
        //   double totalAmount = 0.0f, minusAmount = 0.0f;
        int checkAmount = 0;

        minusAmount = ((((double) num) / 100) * 2);
        totalAmount = (double) num - minusAmount;
        checkAmount = (int) minusAmount;

        return totalAmount;
    }
}
