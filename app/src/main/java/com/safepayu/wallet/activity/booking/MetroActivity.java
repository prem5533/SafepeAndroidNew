package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.multidex.MultiDex;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.adapter.SpinnerAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.OperatorResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MetroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ProceedBtn,BackBtn;
    private TextView tvRechargeamount,tvWalletCashback,tvTotalAmountpay;
    private TextView tvRechargeAmtTax,tvServiceChargeTax,tvAmt2PayTax;
    private RelativeLayout ServiceChargeLayout;
    private EditText edCardNo,edAmount;
    private String CardNo="",Amount="",OperatorId="0",OperatorCode="0",OperatorName="";
    private LoadingDialog loadingDialog;
    private List<OperatorResponse.OperatorsBean> mOperList = new ArrayList<>();
    private Spinner OperatorSpinner;
    private LinearLayout layoutSelectElectricityOper;
    private double totalAmount = 0.0f, minusAmount = 0.0f;
    private CardView cardAmount;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);

        findId();
    }

    private void findId() {

        loadingDialog=new LoadingDialog(this);
        
        BackBtn = findViewById(R.id.metro_backBtn);
        ProceedBtn = findViewById( R.id.payBtn_metroLayout);
        OperatorSpinner= findViewById( R.id.operator_metro);
        layoutSelectElectricityOper = findViewById(R.id.layoutSelect_metroOperator);
        edCardNo = findViewById( R.id.cardNumber_metroLayout);
        edAmount = findViewById( R.id.amount_metroLayout);
        tvRechargeamount = findViewById(R.id.tv_rechargeamount_metroLayout);
        tvWalletCashback = findViewById(R.id.tv_walletcashback_metroLayout);
        tvTotalAmountpay = findViewById(R.id.tv_total_amountpay_metroLayout);
        cardAmount = findViewById(R.id.cardAmount_metroLayout);
        ServiceChargeLayout= findViewById(R.id.serviceChargeLayout_metro);
        tvRechargeAmtTax= findViewById(R.id.tv_rechargeAmount_serviceChargeLayout);
        tvServiceChargeTax= findViewById(R.id.tv_serviceCharge_serviceChargeLayout);
        tvAmt2PayTax= findViewById(R.id.tv_totalAmt_serviceChargeLayout);
        
        //Set Listener
        BackBtn.setOnClickListener(this);
        ProceedBtn.setOnClickListener(this);
        layoutSelectElectricityOper.setOnClickListener(this);

        OperatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OperatorName=mOperList.get(i).getOperator_name();
                OperatorCode= mOperList.get(i).getOperator_code();
                OperatorId= String.valueOf(mOperList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String Amount = edAmount.getText().toString().trim();
                if (!Amount.equals("")) {
                    CalculateServiceCharge(Amount);
                    ServiceChargeLayout.setVisibility(View.VISIBLE);
                }else {
                    ServiceChargeLayout.setVisibility(View.GONE);
                }

                if (!Amount.equals("")) {
                    int num = Integer.parseInt(Amount);
                    if (num>99){
                        if (num <=1000) {
                            CalculateAmount(num);
                            String text = edAmount.getText().toString().trim() + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                            //  AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                            //cardAmount.setVisibility(View.VISIBLE);
                            tvRechargeamount.setText(edAmount.getText().toString().trim()+" "+getResources().getString(R.string.rupees));
                            tvWalletCashback.setText( " -  "+new DecimalFormat("##.##").format(minusAmount)+" "+getResources().getString(R.string.rupees));
                            tvTotalAmountpay.setText(String.format("%.2f", totalAmount)+" "+getResources().getString(R.string.rupees));
                        } else if (num>1000){
                            CalculateAmount1Per(num);
                            String text = edAmount.getText().toString().trim()  + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                            //  AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                            tvRechargeamount.setText(edAmount.getText().toString().trim()+" "+getResources().getString(R.string.rupees));
                            tvWalletCashback.setText( " -  "+new DecimalFormat("##.##").format(minusAmount)+" "+getResources().getString(R.string.rupees));
                            tvTotalAmountpay.setText(String.format("%.2f", totalAmount)+" "+getResources().getString(R.string.rupees));
                        }
                    }
                    cardAmount.setVisibility(View.GONE);
                } else {
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
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_metro),"Check Your Internet Connection",false);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.metro_backBtn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

            case R.id.layoutSelect_metroOperator:

                layoutSelectElectricityOper.setVisibility(View.GONE);
                OperatorSpinner.setVisibility(View.VISIBLE);
                break;
                
            case R. id.payBtn_metroLayout:

                CheckValidate();
                break;
        }

    }

    private void CalculateServiceCharge(String Amt){
        String Tax=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().SERVICE_CHARGE);
        try {
            Double totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(Double.parseDouble(Amt.trim()), Double.parseDouble(Tax));
            tvAmt2PayTax.setText(getResources().getString(R.string.rupees)+" "+totalPayableAmount);

        }catch (Exception e){
            tvAmt2PayTax.setText(getResources().getString(R.string.rupees)+" "+0);
            e.printStackTrace();
        }

        tvRechargeAmtTax.setText(getResources().getString(R.string.rupees)+" "+Amt);
        tvServiceChargeTax.setText(Tax+"%");
    }

    private void CheckValidate() {

        try {
            CardNo=edCardNo.getText().toString().trim();
            Amount =edAmount.getText().toString().trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        if (TextUtils.isEmpty(CardNo)){
            BaseApp.getInstance().toastHelper().showToast(this,"Please Enter Your Card Number",true);
        }else {
            if (Integer.parseInt(CardNo)<5){
                BaseApp.getInstance().toastHelper().showToast(this,"Please Enter Your Correct Card Number",true);
            }else {
                if (TextUtils.isEmpty(OperatorName)){
                    BaseApp.getInstance().toastHelper().showToast(this,"Please Enter Your Card Number",true);
                }else {
                    if (TextUtils.isEmpty(Amount)){
                        BaseApp.getInstance().toastHelper().showToast(this,"Please Enter Amount",true);
                    }else {
                        if (Integer.parseInt(CardNo)<100){
                            BaseApp.getInstance().toastHelper().showToast(this,"Minimum Recharge Amount Is Rs.100",true);
                        }else {
                            Intent intent=new Intent(MetroActivity.this, PaymentType.class);
                            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                            intent.putExtra("RechargePaymentId",CardNo);
                            intent.putExtra("Amount",Amount);
                            intent.putExtra("PaymentType","Recharge");
                            intent.putExtra("PaymentFor","Metro");
                            intent.putExtra("RechargeTypeId","8");
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
    }

    private void getAllOperators(){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getOperators("8")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OperatorResponse>() {
                    @Override
                    public void onSuccess(OperatorResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            mOperList=response.getOperators();
                            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),mOperList);
                            OperatorSpinner.setAdapter(customAdapter);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_metro),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_metro), true, e);
                    }
                }));

    }
}
