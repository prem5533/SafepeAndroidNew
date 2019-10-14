package com.safepayu.wallet.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.TransferWalletToBankRequest;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.TransferWalletToBankResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class SendMoney extends BaseActivity implements  RadioGroup.OnCheckedChangeListener, PasscodeClickListener {

    Button BackBtn,WithDrawBtn;
    private LinearLayout WithdrawAmountlayout,AddBankBenBtn;
    private  TextView AmountTotalTV;
    private Spinner BankBenSpinner;
    private EditText AmountED;
    private RadioGroup radioGroup;
    private String Mode="",BenID="";
    private LoadingDialog loadingDialog;
    private boolean CheckNet=false;
    Dialog dialogStatus;
    TextView TImer;
    TransferWalletToBankResponse responseData;
    TransferWalletToBankRequest transferWalletToBankRequestDate;

    ArrayList<String> NameList,IdList,BenIdList;
    private static int SPLASH_TIME_OUT = 59000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);

        AddBankBenBtn=findViewById(R.id.BankBenAddBtn);
        BackBtn=findViewById(R.id.send_back_btn);
        WithdrawAmountlayout=findViewById(R.id.withdrawAmountlayout);
        BankBenSpinner=findViewById(R.id.bankBenSpinner);
        radioGroup=findViewById(R.id.radioGroupWithdraw);
        WithDrawBtn=findViewById(R.id.btnWithdraw);
        AmountTotalTV=findViewById(R.id.calculatedamount);
        AmountED=findViewById(R.id.withdrawAmount);
        BankBenSpinner.setVisibility(View.GONE);

        NameList=new ArrayList<>();
        IdList=new ArrayList<>();
        BenIdList=new ArrayList<>();

        NameList.clear();
        IdList.clear();
        BenIdList.clear();

        radioGroup.setOnCheckedChangeListener(this);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddBankBenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddBeneficiary.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
            }
        });

        WithDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),"Please Buy Membership To Enjoy App's Features",false);
                }else {
                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().IS_BLOCKED).equalsIgnoreCase("0")){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.dthRechargeLayout),"Withdraw Is Closed Today",true);
                    }else {
                        CheckValidate();
                    }
                }

            }
        });

        BankBenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BenID=BenIdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AmountED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                if (s.length()>2){
                    double amt=CalculateAmount(Integer.parseInt(AmountED.getText().toString().trim()));
                    String text = AmountED.getText().toString().trim()+" - Tax = ";
                    AmountTotalTV.setText(text+String.format("%.2f", amt));
                }else {
                    AmountTotalTV.setText("0.0");
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

        getBenList();

    }

    private void CheckValidate(){
        int Amount= 0;
        try {
            Amount= Integer.parseInt(AmountED.getText().toString().trim());
        }catch (Exception e){
            e.printStackTrace();
        }

        if (Mode.equalsIgnoreCase("UPI")){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Coming Soon",false);
        }else  if (Mode.equalsIgnoreCase("Bank Transfer")){
            if (TextUtils.isEmpty(AmountED.getText().toString().trim())){
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Please Enter Amount",false);
            }else {
                if (Amount<8001 && Amount>99){//99

                    if (TextUtils.isEmpty(BenID)){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Please Select Any Beneficiary",false);
                    }else {
                        TransferWalletToBankRequest transferWalletToBankRequest=new TransferWalletToBankRequest();
                        transferWalletToBankRequest.setAmount(String.valueOf(Amount));
                        transferWalletToBankRequest.setBeneId(BenID);
                        transferWalletToBankRequestDate=transferWalletToBankRequest;

                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                            startActivity(new Intent(SendMoney.this,CreatePassCodeActivity.class));
                        } else {
                            PasscodeDialog passcodeDialog = new PasscodeDialog(SendMoney.this, SendMoney.this, "");
                            passcodeDialog.show();
                        }
                    }

                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Please Enter Amount Between Rs 100 And Rs 8000 ",false);
                }
            }
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Choose Mode Of Transfer",false);
        }


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.send_money;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

        if (isConnected){
            CheckNet=true;
        }else {
            CheckNet=false;
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Check Your Internet Connection ",false);
        }

    }

    private void getBenList(){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getBeneficiary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GetBeneficiaryResponse>() {
                    @Override
                    public void onSuccess(GetBeneficiaryResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            if (response.getBeneficiary().size()>0){
                                for (int i=0;i<response.getBeneficiary().size();i++){
                                    NameList.add(response.getBeneficiary().get(i).getName());
                                    IdList.add(String.valueOf(response.getBeneficiary().get(i).getId()));
                                    BenIdList.add(response.getBeneficiary().get(i).getBenId());
                                }

                                ArrayAdapter<String> TransferType= new ArrayAdapter<>(SendMoney.this,android.R.layout.simple_spinner_item,NameList);
                                TransferType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                BankBenSpinner.setAdapter(TransferType);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"No Beneficiary Found!. Please Add One",true);
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.withMoneyLayout), true, e);
                    }
                }));

    }

    private void WithAmountMethod(TransferWalletToBankRequest transferWalletToBankRequest){


        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.transferWalletToBank(transferWalletToBankRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TransferWalletToBankResponse>() {
                    @Override
                    public void onSuccess(TransferWalletToBankResponse response) {
                        loadingDialog.hideDialog();
                        responseData=response;
                        ShowPending(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.buy_packageId), true, e);
                    }
                }));

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.upiradiobtn:
                Mode="UPI";
                WithdrawAmountlayout.setVisibility(VISIBLE);
                BankBenSpinner.setVisibility(View.GONE);
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Coming Soon",false);
                break;

            case R.id.bankradiobtn:
                Mode="Bank Transfer";
                WithdrawAmountlayout.setVisibility(VISIBLE);
                BankBenSpinner.setVisibility(VISIBLE);
                break;
        }
    }

    private void ShowStatus(final TransferWalletToBankResponse response){
        final Intent intentStatus=new Intent(SendMoney.this,PaidOrderActivity.class);

        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                // close this activity
                if (response.getStatusCode()==1){
                    intentStatus.putExtra("status","success");
                }else if (response.getStatusCode()==2){
                    Toast.makeText(SendMoney.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    intentStatus.putExtra("status","pending");
                }else {
                    Toast.makeText(SendMoney.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    intentStatus.putExtra("status","failed");
                }
                intentStatus.putExtra("txnid",response.getTransaction());
                intentStatus.putExtra("Amount",AmountED.getText().toString().trim());
                intentStatus.putExtra("date","");
                intentStatus.putExtra("productinfo","Wallet To Bank Transaction");
                startActivity(intentStatus);
                finish();

            }
        }, SPLASH_TIME_OUT);  */
    }

    private void ShowPending(TransferWalletToBankResponse response){
        TextView tvNeedHelp,StatusTV,DateTV,TxnIdTV,AmountTV,ProductInfoTV;

        dialogStatus = new Dialog(SendMoney.this, android.R.style.Theme_Light);
        dialogStatus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStatus.setContentView(R.layout.dialog_for_pending);
        dialogStatus.setCancelable(false);

        StatusTV = dialogStatus.findViewById(R.id.statusText);
        DateTV = dialogStatus.findViewById(R.id.tv_date_time);
        TxnIdTV = dialogStatus.findViewById(R.id.txnId);
        AmountTV = dialogStatus.findViewById(R.id.tv_paid_rs);
        ProductInfoTV = dialogStatus.findViewById(R.id.productInfo);
        TImer = dialogStatus.findViewById(R.id.tv_Timer_pendingDialog);

        DateTV.setText(response.getDate());
        TxnIdTV.setText(response.getTransactionId());
        AmountTV.setText(getResources().getString(R.string.rupees)+" "+AmountED.getText().toString());
        ProductInfoTV.setText("Transfer Wallet To Bank");

        countDownTimer.start();

        dialogStatus.show();
    }

    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            TImer.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        }

        @Override
        public void onFinish() {
            final Intent intentStatus=new Intent(SendMoney.this,PaidOrderActivity.class);

            if (responseData.getStatusCode()==1){
                intentStatus.putExtra("status","success");
            }else if (responseData.getStatusCode()==2){
                Toast.makeText(SendMoney.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                intentStatus.putExtra("status","pending");
            }else {
                Toast.makeText(SendMoney.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                intentStatus.putExtra("status","failed");
            }
            intentStatus.putExtra("txnid",responseData.getTransactionId());
            intentStatus.putExtra("Amount",AmountED.getText().toString().trim());
            intentStatus.putExtra("date",responseData.getDate());
            intentStatus.putExtra("productinfo","Wallet To Bank Transaction");
            startActivity(intentStatus);
            finish();
            dialogStatus.dismiss();

        }
    };

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched){
            WithAmountMethod(transferWalletToBankRequestDate);
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId),"Invalid Passcode",false);
        }

    }

    private double CalculateAmount(int amount){

        double totalAmount=0.0f,minusAmount=0.0f;
        int checkAmount=0;

        minusAmount=((((double) amount) / 100) * 3.56);
        totalAmount=(double)amount- minusAmount;
        checkAmount=(int)minusAmount;
        if (checkAmount>9){

        }else {
            totalAmount=(double)amount-(double)10;
        }

        return totalAmount;
    }
}
