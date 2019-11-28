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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.LoginActivity;
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.adapter.SpinnerAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.CustOperatorResponse;
import com.safepayu.wallet.models.response.OperatorResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GasPay extends BaseActivity {

    Button GasPayBtn,BackBtn;
    private Spinner OperatorSpinner;
    private EditText AmountED,GasIdED;
    String OperatorText="",OperatorCode="",OperatorId="";
    private LoadingDialog loadingDialog;
    private ArrayList<String> OperatorNameList,IdList,OperatorCodeList;
    private TextView AmountTotalTV,tvRechargeamount,tvWalletCashback,tvTotalAmountpay;
    double totalAmount = 0.0f, minusAmount = 0.0f;
    private CardView cardAmount;
    LinearLayout layoutSelectGasOper;
    List<OperatorResponse.OperatorsBean> mOperList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        loadingDialog = new LoadingDialog(this);
        
        GasPayBtn=findViewById(R.id.gasPaybtn);
        BackBtn=findViewById(R.id.gas_back_btn);
        OperatorSpinner=findViewById(R.id.operatorsGas);
        AmountED=findViewById(R.id.amountGas);
        AmountTotalTV = findViewById(R.id.calculatedamount);
        GasIdED=findViewById(R.id.gasID);
        layoutSelectGasOper = findViewById(R.id.layout_select_mobile_operator);
        cardAmount = findViewById(R.id.card_amount);
        tvRechargeamount = findViewById(R.id.tv_rechargeamount);
        tvWalletCashback = findViewById(R.id.tv_walletcashback);
        tvTotalAmountpay = findViewById(R.id.tv_total_amountpay);

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
        layoutSelectGasOper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSelectGasOper.setVisibility(View.GONE);
                OperatorSpinner.setVisibility(View.VISIBLE);
            }
        });
        GasPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.gasPayLayout),"Please Buy Membership To Enjoy App's Features",false);
                }else {
                    CheckValidate();
                }
            }
        });

        OperatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OperatorText=mOperList.get(i).getOperator_name();
                OperatorCode= mOperList.get(i).getOperator_code();
                OperatorId= String.valueOf(mOperList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GasIdED.addTextChangedListener(new TextWatcher() {
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
                        String text = AmountED.getText().toString().trim() + " - " + new DecimalFormat("##.##").format(minusAmount) + " = ";
                        //   AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                        cardAmount.setVisibility(View.VISIBLE);
                        tvRechargeamount.setText(AmountED.getText().toString().trim() + " " + getResources().getString(R.string.rupees));
                        tvWalletCashback.setText(" -  " + new DecimalFormat("##.##").format(minusAmount) + " " + getResources().getString(R.string.rupees));
                        tvTotalAmountpay.setText(String.format("%.2f", totalAmount) + " " + getResources().getString(R.string.rupees));
                    }

                    else if (num>1000){
                        CalculateAmount1Per(num);
                        String text = AmountED.getText().toString().trim()  + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                   //     AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                        tvRechargeamount.setText(AmountED.getText().toString().trim()+" "+getResources().getString(R.string.rupees));
                        tvWalletCashback.setText( " -  "+new DecimalFormat("##.##").format(minusAmount)+" "+getResources().getString(R.string.rupees));
                        tvTotalAmountpay.setText(String.format("%.2f", totalAmount)+" "+getResources().getString(R.string.rupees)); }
                    else {
                        AmountTotalTV.setText("0.0");
                    } }
                else {
                    cardAmount.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (isNetworkAvailable()){
            getAllOperators();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.gasPayLayout),"Check Your Internet Connection",false);
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
        return R.layout.gas_pay;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void CheckValidate(){
        int Amount=0;
        String GasId="";
        try{
            GasId= GasIdED.getText().toString().trim();
            Amount= Integer.parseInt(AmountED.getText().toString().trim());
        }catch (Exception e){
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(GasIdED.getText().toString().trim())){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.gasPayLayout),"Please Enter Gas Id",false);
        }else {
            if (TextUtils.isEmpty(AmountED.getText().toString().trim())){
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.gasPayLayout),"Please Enter Amount",false);
            }else {
                if (Amount==0 || Amount<0){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.gasPayLayout),"Please Enter Amount",false);
                }else {

                    if (TextUtils.isEmpty(OperatorCode) || OperatorCode.equals("0")){

                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.gasPayLayout),"Please Select Operator",false);
                    }else {
                        Intent intent=new Intent(GasPay.this,PaymentType.class);
                        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                        intent.putExtra("RechargePaymentId",GasId);
                        intent.putExtra("Amount",String.valueOf(Amount));
                        intent.putExtra("PaymentType","Bill Payment");
                        intent.putExtra("PaymentFor","Gas");
                        intent.putExtra("RechargeTypeId","5");
                        intent.putExtra("OperatorCode",OperatorCode);
                        intent.putExtra("CircleCode","51");
                        intent.putExtra("OperatorId",OperatorId);
                        intent.putExtra("walletCashback", tvWalletCashback.getText().toString());
                        intent.putExtra("totalAmount", tvTotalAmountpay.getText().toString());
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

        BaseApp.getInstance().getDisposable().add(apiService.getOperators("5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OperatorResponse>() {
                    @Override
                    public void onSuccess(OperatorResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            mOperList=response.getOperators();
                            for (int i = 0; i < response.getOperators().size(); i++) {
                                SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),mOperList);
                                OperatorSpinner.setAdapter(customAdapter); }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.gasPayLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.gasPayLayout), true, e);
                    }
                }));

    }

    private void getCustomerOperator(){
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getMobileOperator(GasIdED.getText().toString().trim())
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


