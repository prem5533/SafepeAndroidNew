package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.AddBeneficiaryRequest;
import com.safepayu.wallet.models.request.TransferWalletToBankRequest;
import com.safepayu.wallet.models.response.AddBeneficiaryResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.TransferWalletToBankResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class SendMoney extends BaseActivity implements  RadioGroup.OnCheckedChangeListener {

    Button AddBankBenBtn,BackBtn,WithDrawBtn;
    private LinearLayout WithdrawAmountlayout;
    private Spinner BankBenSpinner;
    private EditText AmountED;
    private RadioGroup radioGroup;
    private String Mode="",BenID="";
    private LoadingDialog loadingDialog;
    private boolean CheckNet=false;

    ArrayList<String> NameList,IdList,BenIdList;

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
                CheckValidate();
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
                if (Amount<8001 && Amount>99){

                    if (TextUtils.isEmpty(BenID)){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),"Please Select Any Beneficiary",false);
                    }else {
                        TransferWalletToBankRequest transferWalletToBankRequest=new TransferWalletToBankRequest();
                        transferWalletToBankRequest.setAmount(String.valueOf(Amount));
                        transferWalletToBankRequest.setBeneId(BenID);

                        WithAmountMethod(transferWalletToBankRequest);
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

                            for (int i=0;i<response.getBeneficiary().size();i++){
                                NameList.add(response.getBeneficiary().get(i).getName());
                                IdList.add(String.valueOf(response.getBeneficiary().get(i).getId()));
                                BenIdList.add(response.getBeneficiary().get(i).getBenId());
                            }

                            ArrayAdapter<String> TransferType= new ArrayAdapter<>(SendMoney.this,android.R.layout.simple_spinner_item,NameList);
                            TransferType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            BankBenSpinner.setAdapter(TransferType);
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
                        if (response.isStatus()) {

                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),response.getTransaction()+"\n"+response.getMessage(),false);

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout),response.getMessage(),false);
                        }
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
}
