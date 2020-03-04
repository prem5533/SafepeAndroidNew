package com.safepayu.wallet.activity.deposit_fixed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.AddBeneficiary;
import com.safepayu.wallet.activity.PaidOrderActivity;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ExceptionLogRequest;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.SendOtpRequest;
import com.safepayu.wallet.models.request.TransferWalletToBankRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.TransferWalletToBankResponse;
import com.safepayu.wallet.models.response.UserResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class TransferFdToBank extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener, PasscodeClickListener {

    public Button send_back_btn, btn_withdraw;
    public TextView tv_fd_amount;
    private RadioGroup radioGroup;
    public Spinner sp_account_beneficiary;
    public LoadingDialog loadingDialog;
    private LinearLayout benLayout_sendMoney, BankBenAddBtn;
    public String FD_AMOUNT, FD_ID;
    public String Mode = "", BenID = "", Mobile = "";
    public TransferWalletToBankResponse responseData;
    public TransferWalletToBankRequest transferWalletToBankRequestDate;
    public List<GetBeneficiaryResponse.BeneficiaryBean> beneficiaryBeanList = new ArrayList<>();
    private String DeviceName = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().DEVICE_NAME);
    private String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
    ExceptionLogRequest logRequest;
    Dialog dialogStatus;
    TextView TImer;
    //Otp Dialog
    TextView TimerTV;
    EditText OtpED;
    Button continueButton, resendButton;
    private ImageView im_cross;
    private ApiService apiService;

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
        BankBenAddBtn = findViewById(R.id.BankBenAddBtn);
        radioGroup.setOnCheckedChangeListener(this);

        sp_account_beneficiary.setOnItemSelectedListener(this);
        btn_withdraw.setOnClickListener(this);
        BankBenAddBtn.setOnClickListener(this);
        send_back_btn.setOnClickListener(v -> finish());

        Mobile = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
        getBenList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_withdraw:
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
                                CheckValidate();
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
                break;

            case R.id.BankBenAddBtn:
                try {
                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Goto Your Profile and Verify Your Email First", true);
                    } else {
                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Buy Membership To Enjoy App's Features", true);
                        } else {
                            startActivity(new Intent(getApplicationContext(), AddBeneficiary.class));
                            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logRequest = new ExceptionLogRequest(TransferFdToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 165", "PACKAGE_PURCHASED ", DeviceName);
                }

                break;
        }

    }

    private void CheckValidate() {
        if (TextUtils.isEmpty(BenID)) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Select Any Beneficiary", false);
        } else {
            TransferWalletToBankRequest transferWalletToBankRequest = new TransferWalletToBankRequest();
            // transferWalletToBankRequest.setAmount(String.valueOf(FD_AMOUNT));
            transferWalletToBankRequest.setBeneId(BenID);
            transferWalletToBankRequest.setId(FD_ID);
            transferWalletToBankRequestDate = transferWalletToBankRequest;

            PasscodeDialog passcodeDialog = new PasscodeDialog(TransferFdToBank.this, TransferFdToBank.this, "");
            passcodeDialog.show();
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
                            logRequest = new ExceptionLogRequest(TransferFdToBank.this, UserId, "TransferFDToBank", e.getMessage(),
                                    " 234", "getBeneficiary api ", DeviceName);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        logRequest = new ExceptionLogRequest(TransferFdToBank.this, UserId, "TransferFDToBank", e.getMessage(),
                                " 143", "getBeneficiary api ", DeviceName);
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

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched) {
            WithAmountMethod(transferWalletToBankRequestDate);
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Invalid Passcode", false);
        }
    }

    private void WithAmountMethod(TransferWalletToBankRequest transferWalletToBankRequest) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.transferFDWalletToBank(transferWalletToBankRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TransferWalletToBankResponse>() {
                    @Override
                    public void onSuccess(TransferWalletToBankResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            responseData = response;
                            ShowPending(response);
                        } else {
                            showDialogFailed(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        logRequest = new ExceptionLogRequest(TransferFdToBank.this, UserId, "TransferFDToBank", e.getMessage(),
                                "308", "transferFDWalletToBank api ", DeviceName);
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.withMoneyLayout), true, e);
                    }
                }));

    }

    private void ShowPending(TransferWalletToBankResponse response) {

        TextView tvNeedHelp, StatusTV, DateTV, TxnIdTV, AmountTV, ProductInfoTV;
        dialogStatus = new Dialog(TransferFdToBank.this, android.R.style.Theme_Light);
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
        AmountTV.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(FD_AMOUNT)));
        ProductInfoTV.setText("Transfer Wallet To Bank");

        countDownTimer.start();
        dialogStatus.show();
    }

    CountDownTimer countDownTimer = new CountDownTimer(0, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            TImer.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        }

        @Override
        public void onFinish() {
            final Intent intentStatus = new Intent(TransferFdToBank.this, PaidOrderActivity.class);

            if (responseData.getStatusCode() == 1) {
                intentStatus.putExtra("status", "success");
            } else if (responseData.getStatusCode() == 2) {
                //Toast.makeText(TransferInvestmentToBank.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                intentStatus.putExtra("status", "success");
                //change status to pending on bank issue resolved
            } else {
                Toast.makeText(TransferFdToBank.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                intentStatus.putExtra("status", "failed");
            }
            intentStatus.putExtra("txnid", responseData.getTransactionId());
            intentStatus.putExtra("Amount", FD_AMOUNT);
            intentStatus.putExtra("date", responseData.getDate());
            intentStatus.putExtra("productinfo", "Investment Wallet To Bank Transaction");
            intentStatus.putExtra("Message", responseData.getMessage());
            intentStatus.putExtra("utr_id", responseData.getUtr());
            startActivity(intentStatus);
            finish();
            dialogStatus.dismiss();

        }
    };

    CountDownTimer countDownTimerOtp = new CountDownTimer(4 * 60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            TimerTV.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        }

        @Override
        public void onFinish() {
            resendButton.setVisibility(View.VISIBLE);
            TimerTV.setVisibility(View.INVISIBLE);
        }
    };

    public void showDialogFailed(String Message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("SafePe Alert")
                .setCancelable(false)
                .setMessage("\n" + Message + "\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Ok", (dialog1, which) -> {
                    // Continue with delete operation
                    dialog1.dismiss();
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.otp_dialog);

        TimerTV = dialog.findViewById(R.id.timerLogin);
        OtpED = dialog.findViewById(R.id.enter_otpLogin);
        im_cross = dialog.findViewById(R.id.im_cross);

        im_cross.setOnClickListener(v -> dialog.dismiss());

        continueButton = dialog.findViewById(R.id.continue_otpLogin);
        continueButton.setOnClickListener(v -> {

            if (TextUtils.isEmpty(OtpED.getText().toString().trim())) {
                OtpED.setError("Please Enter Otp");
                //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout),"Please Enter Otp",false);
            } else {
                verifyOtp(OtpED.getText().toString().trim());
                dialog.dismiss();
            }
        });

        resendButton = dialog.findViewById(R.id.resend_otpLogin);
        resendButton.setOnClickListener(v -> {
            resendOtp();
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    private void resendOtp() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(Mobile, null);

        SendOtpRequest sendOtpRequest = new SendOtpRequest();
        sendOtpRequest.setMobile(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        sendOtpRequest.setType("3");

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(sendOtpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            countDownTimerOtp.start();
                            showDialog(TransferFdToBank.this);

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), response.getMessage(), false);
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

    private void verifyOtp(String otp) {

        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(Mobile, null);
        request.setDeviceid(android_id);
        request.setOtp(otp);
        BaseApp.getInstance().getDisposable().add(apiService.verifyOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), response.getMessage(), false);
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
}