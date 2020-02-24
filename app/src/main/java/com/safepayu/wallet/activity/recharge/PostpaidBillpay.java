package com.safepayu.wallet.activity.recharge;

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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.Navigation;
import com.safepayu.wallet.activity.PaymentType;
import com.safepayu.wallet.activity.PaymentTypeNew;
import com.safepayu.wallet.adapter.ServiceHistoryAdapter;
import com.safepayu.wallet.adapter.SpinnerAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.CustOperatorResponse;
import com.safepayu.wallet.models.response.OperatorResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.LoginActivity.finalAmount;

public class PostpaidBillpay extends BaseActivity implements ServiceHistoryAdapter.OnSelectListener{

    Button PayBtn,BackBtn,BillCheckBtn ;
    TextView textView;
    private Spinner OperatorSpinner;
    private EditText MobileED ,AmountEd;
    String OperatorText="",OperatorCode="",OperatorId="";
    private LoadingDialog loadingDialog;
    private ArrayList<String> OperatorNameList,IdList,OperatorCodeList;
    double totalAmount = 0.0f, minusAmount = 0.0f;
    private TextView AmountTotalTV,tvRechargeamount,tvWalletCashback,tvTotalAmountpay,tvViewAllBtn,tvViewLessBtn;
    private TextView tvRechargeAmtTax,tvServiceChargeTax,tvAmt2PayTax,tvPreviousOrderText;
    private ServiceHistoryAdapter historyAdapter;
    private RelativeLayout ServiceChargeLayout;
    private CardView cardAmount;
    LinearLayout layoutSelectBillOper;
    List<OperatorResponse.OperatorsBean> mOperList = new ArrayList<>();
    private RecyclerView RechargeHistoryListView;

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
        layoutSelectBillOper = findViewById(R.id.layout_select_mobile_operator);
        cardAmount = findViewById(R.id.card_amount);
        tvRechargeamount = findViewById(R.id.tv_rechargeamount);
        tvWalletCashback = findViewById(R.id.tv_walletcashback);
        tvTotalAmountpay = findViewById(R.id.tv_total_amountpay);
        ServiceChargeLayout= findViewById(R.id.serviceChargeLayout_postpaid);
        tvRechargeAmtTax= findViewById(R.id.tv_rechargeAmount_serviceChargeLayout);
        tvServiceChargeTax= findViewById(R.id.tv_serviceCharge_serviceChargeLayout);
        tvAmt2PayTax= findViewById(R.id.tv_totalAmt_serviceChargeLayout);
        tvPreviousOrderText=findViewById(R.id.orderPreviousText);
        tvViewAllBtn=findViewById(R.id.orderViewAllText);
        tvViewLessBtn=findViewById(R.id.orderViewLessText);
        RechargeHistoryListView = findViewById(R.id.listPostpaid_rechargeHistory);

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
        layoutSelectBillOper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSelectBillOper.setVisibility(View.GONE);
                OperatorSpinner.setVisibility(View.VISIBLE);
            }
        });

        PayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
//                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
//                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),"Please Buy Membership To Enjoy App's Features",false);
//                    }else {
//                        CheckValidate();
//                    }
                    CheckValidate();
                }catch (Exception e){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout), "Please Buy Membership To Enjoy App's Features", false);
                    e.printStackTrace();
                }
            }
        });

       /* BillCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.GONE);
                AmountEd.setVisibility(View.VISIBLE);
                BillCheckBtn.setVisibility(View.GONE);
                PayBtn.setVisibility(View.VISIBLE);

            }
        });*/

        AmountEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Amount = AmountEd.getText().toString().trim();

                if (!Amount.equals("")) {
                    CalculateServiceCharge(Amount);
                    ServiceChargeLayout.setVisibility(View.VISIBLE);
                }else {
                    ServiceChargeLayout.setVisibility(View.GONE);
                }

                if (!Amount.equals("")) {
                    int num = Integer.parseInt(Amount);
                    if (num <=1000) {
                        CalculateAmount(num);
                        String text = AmountEd.getText().toString().trim() + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                      //  AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                        //cardAmount.setVisibility(View.VISIBLE);
                        tvRechargeamount.setText(AmountEd.getText().toString().trim() + " " + getResources().getString(R.string.rupees));
                        tvWalletCashback.setText(" -  " + new DecimalFormat("##.##").format(minusAmount) + " " + getResources().getString(R.string.rupees));
                        tvTotalAmountpay.setText(String.format("%.2f", totalAmount) + " " + getResources().getString(R.string.rupees));
                    } else if (num>1000){
                        CalculateAmount1Per(num);
                        String text = AmountEd.getText().toString().trim()  + " - " +new DecimalFormat("##.##").format(minusAmount) + " = ";
                        //AmountTotalTV.setText(text + String.format("%.2f", totalAmount));
                        tvRechargeamount.setText(AmountEd.getText().toString().trim()+" "+getResources().getString(R.string.rupees));
                        tvWalletCashback.setText( " -  "+new DecimalFormat("##.##").format(minusAmount)+" "+getResources().getString(R.string.rupees));
                        tvTotalAmountpay.setText(String.format("%.2f", totalAmount)+" "+getResources().getString(R.string.rupees));
                    } else {
                        AmountTotalTV.setText("0.0");
                    }
                    cardAmount.setVisibility(View.GONE);
                }
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

    private void CalculateServiceCharge(String Amt){
        String Tax=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().SERVICE_CHARGE);
        try {
            Double totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(Double.parseDouble(Amt.trim()), Double.parseDouble(Tax));
            tvAmt2PayTax.setText(getResources().getString(R.string.rupees)+" "+totalPayableAmount);
            finalAmount=totalPayableAmount;
        }catch (Exception e){
            tvAmt2PayTax.setText(getResources().getString(R.string.rupees)+" "+0);
            e.printStackTrace();
        }

        tvRechargeAmtTax.setText(getResources().getString(R.string.rupees)+" "+Amt);
        tvServiceChargeTax.setText(Tax+"%");
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
                            Intent intent;
                            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PAYMENT_SCREEN).equals("0")) {

                                intent = new Intent(PostpaidBillpay.this, PaymentTypeNew.class);
                            }else {
                                intent = new Intent(PostpaidBillpay.this, PaymentType.class);
                            }

                            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                            intent.putExtra("RechargePaymentId",Mobile);
                            intent.putExtra("Amount",String.valueOf(Amount));
                            intent.putExtra("PaymentType","Bill Payment");
                            intent.putExtra("PaymentFor","Postpaid");
                            intent.putExtra("RechargeTypeId","6");
                            intent.putExtra("OperatorCode",OperatorCode);
                            intent.putExtra("CircleCode","51");
                            intent.putExtra("OperatorId",OperatorId);
                            intent.putExtra("walletCashback", tvWalletCashback.getText().toString());
                            intent.putExtra("totalAmount", tvTotalAmountpay.getText().toString());
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
                            mOperList=response.getOperators();
                            for (int i = 0; i < response.getOperators().size(); i++) {
                                SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),mOperList);
                                OperatorSpinner.setAdapter(customAdapter);
                            }
                            try {
                                if (response.getHistory().size()>0){
                                    tvPreviousOrderText.setVisibility(View.VISIBLE);
                                    historyAdapter=new ServiceHistoryAdapter(PostpaidBillpay.this,response.getHistory(),PostpaidBillpay.this);
                                    RechargeHistoryListView.setAdapter(historyAdapter);
                                }else {
                                    tvPreviousOrderText.setVisibility(View.GONE);
                                    tvViewAllBtn.setVisibility(View.GONE);
                                    tvViewLessBtn.setVisibility(View.GONE);
                                }
                            }catch (Exception e){
                                tvPreviousOrderText.setVisibility(View.GONE);
                                tvViewAllBtn.setVisibility(View.GONE);
                                tvViewLessBtn.setVisibility(View.GONE);
                                e.printStackTrace();
                            }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.postpaidBillLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.postpaidBillLayout), true, e);
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
                OperatorSpinner.setSelection(j);
                AmountEd.setText(""+selectOrderItem.getAmount());
                MobileED.setText(selectOrderItem.getNumber());
                break;
            }
        }
        MobileED.requestFocus();
        OperatorId= selectOrderItem.getOperator_id();
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
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
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


