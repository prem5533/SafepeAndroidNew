package com.safepayu.wallet.activity.deposit_fixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ExceptionLogRequest;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class TransferFdToBank extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    public Button send_back_btn, btn_withdraw;
    public TextView tv_fd_amount;
    private RadioGroup radioGroup;
    public Spinner sp_account_beneficiary;
    public LoadingDialog loadingDialog;
    private LinearLayout benLayout_sendMoney;
    public String FD_AMOUNT, FD_ID;
    public String Mode = "", BenID = "";
    public List<GetBeneficiaryResponse.BeneficiaryBean> beneficiaryBeanList = new ArrayList<>();
    private String DeviceName = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().DEVICE_NAME);
    private String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
    ExceptionLogRequest logRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_fd_to_bank);

        loadingDialog = new LoadingDialog(TransferFdToBank.this);

        Intent intent = getIntent();
        if (intent != null) {
            FD_ID = intent.getStringExtra("FD_ID");
            FD_AMOUNT = intent.getStringExtra("FD_AMOUNT");
        }

        tv_fd_amount = findViewById(R.id.tv_fd_amount);
        tv_fd_amount.setText(FD_AMOUNT);
        benLayout_sendMoney = findViewById(R.id.benLayout_sendMoney);
        send_back_btn = findViewById(R.id.send_back_btn);
        sp_account_beneficiary = findViewById(R.id.sp_account_beneficiary);
        btn_withdraw = findViewById(R.id.btn_withdraw);
        radioGroup = findViewById(R.id.radioGroupWithdraw);
        radioGroup.setOnCheckedChangeListener(this);

        sp_account_beneficiary.setOnItemSelectedListener(this);
        btn_withdraw.setOnClickListener(this);
        send_back_btn.setOnClickListener(v -> finish());
        getBenList();
    }

    @Override
    public void onClick(View v) {
        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Goto Your Profile and Verify Your Email First", true);
        } else {
            try {
//                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")) {
//                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Buy Membership To Enjoy App's Features", true);
//                        } else {
//                            try {
//                                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().IS_BLOCKED).equalsIgnoreCase("0")) {
//                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Withdraw Is Closed Today", true);
//                                } else {
//                                    CheckValidate();
//                                }
//                            }catch (Exception e){
//                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Withdraw Is Closed Today", true);
//                                e.printStackTrace();
//                            }
//                        }
                try {
                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().IS_BLOCKED).equalsIgnoreCase("0")) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Withdraw Is Closed Today", true);
                    } else {
                        //  CheckValidate();
                    }
                } catch (Exception e) {
                    logRequest = new ExceptionLogRequest(TransferFdToBank.this, UserId, "TransferInvestmentToBank",
                            e.getMessage(), " 201", "IS_BLOCKED ", DeviceName);
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Withdraw Is Closed Today", true);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Buy Membership To Enjoy App's Features", true);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.upiradiobtn:

                break;

            case R.id.bankradiobtn:
                Mode = "Bank Transfer";
                benLayout_sendMoney.setVisibility(VISIBLE);
                break;
        }
    }

    private void getBenList() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getBeneficiary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GetBeneficiaryResponse>() {
                    @Override
                    public void onSuccess(GetBeneficiaryResponse response) {
                        loadingDialog.hideDialog();
                        try {
                            if (response.isStatus()) {
                                if (response.getBeneficiary().size() > 0) {
                                    for (int i = 0; i < response.getBeneficiary().size(); i++) {
                                        beneficiaryBeanList.add(response.getBeneficiary().get(i));
                                    }
                                    populateStateSpinner();
                                } else {
                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "No Beneficiary Found!. Please Add One", true);
                                }
                            } else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), response.getMessage(), true);
                            }
                        } catch (Exception e) {
                            //  logRequest = new ExceptionLogRequest(TransferFdToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 397", "getBeneficiary api ", DeviceName);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.withMoneyLayout), true, e);
                    }
                }));
    }

    private void populateStateSpinner() {
        List<String> lables = new ArrayList<>();
        lables.add("Select Beneficiary Account");
        for (int i = 0; i < beneficiaryBeanList.size(); i++) {
            lables.add(beneficiaryBeanList.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_account_beneficiary.setAdapter(spinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!(parent.getSelectedItem().toString().equalsIgnoreCase("Select Beneficiary Account"))) {
            BenID = String.valueOf(beneficiaryBeanList.get(position - 1).getBenId());
        } else {
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}