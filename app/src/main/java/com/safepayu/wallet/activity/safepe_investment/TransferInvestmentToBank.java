package com.safepayu.wallet.activity.safepe_investment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import androidx.cardview.widget.CardView;

import com.safepayu.wallet.BaseActivity;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class TransferInvestmentToBank extends BaseActivity implements RadioGroup.OnCheckedChangeListener, PasscodeClickListener {

    Button BackBtn, WithDrawBtn;
    private LinearLayout WithdrawAmountlayout, AddBankBenBtn, BenLayout;
    private TextView AmountTotalTV, tvWithdrawalAmount, tvTax, tvTotalAmountsendmoney, tvWalletBalance, tvTransactionFee;
    private TextView tvMinLimit;
    private Spinner BankBenSpinner;
    private EditText AmountED;
    private RadioGroup radioGroup;
    private String Mode = "", BenID = "", Mobile = "", Limit = "0", LimitMin = "0";
    private LoadingDialog loadingDialog;
    private boolean CheckNet = false;
    Dialog dialogStatus;
    TextView TImer;
    TransferWalletToBankResponse responseData;
    TransferWalletToBankRequest transferWalletToBankRequestDate;
    private CardView cardAmount;
    ArrayList<String> NameList, IdList, BenIdList;
    private static int SPLASH_TIME_OUT = 59000;

    //Otp Dialog
    TextView TimerTV;
    EditText OtpED;
    Button continueButton, resendButton;
    private ImageView im_cross;

    private ApiService apiService;
    double totalAmount = 0.0f, minusAmount = 0.0f, WalletBalance = 0;
    int checkAmount = 0;
    private String DeviceName = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().DEVICE_NAME);
    private String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
    ExceptionLogRequest logRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        Limit = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LIMIT);
        LimitMin = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LIMIT_MIN);

        AddBankBenBtn = findViewById(R.id.BankBenAddBtn);
        BackBtn = findViewById(R.id.send_back_btn);
        WithdrawAmountlayout = findViewById(R.id.withdrawAmountlayout);
        BankBenSpinner = findViewById(R.id.bankBenSpinner);
        radioGroup = findViewById(R.id.radioGroupWithdraw);
        WithDrawBtn = findViewById(R.id.btnWithdraw);
        AmountTotalTV = findViewById(R.id.calculatedamount);
        tvWithdrawalAmount = findViewById(R.id.tv_withdrawalamount);
        tvTax = findViewById(R.id.tv_sendtax);
        tvTotalAmountsendmoney = findViewById(R.id.tv_total_amountsendmoney);
        AmountED = findViewById(R.id.withdrawAmount);
        cardAmount = findViewById(R.id.card_amount);
        tvWalletBalance = findViewById(R.id.walletBalance);
        tvTransactionFee = findViewById(R.id.transactionFeeText_sendMoney);
        tvMinLimit = findViewById(R.id.minLimit_sendMoney);
        BenLayout = findViewById(R.id.benLayout_sendMoney);


        tvMinLimit.setText(getResources().getString(R.string.rupees) + " " + LimitMin);

        BankBenSpinner.setVisibility(View.GONE);
        BenLayout.setVisibility(View.GONE);
        cardAmount.setVisibility(View.GONE);

        NameList = new ArrayList<>();
        IdList = new ArrayList<>();
        BenIdList = new ArrayList<>();

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

        final String tax = "2";
        final String minCharge = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MIN_WITHDRAW_CHARGE);
        tvTransactionFee.setText("Transaction fee: " + tax + "% or Minimum ₹ " + 10);

        AddBankBenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 165", "PACKAGE_PURCHASED ", DeviceName);
                }

            }
        });

        WithDrawBtn.setOnClickListener(view -> {

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
                        logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 201", "IS_BLOCKED ", DeviceName);
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Withdraw Is Closed Today", true);
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Buy Membership To Enjoy App's Features", true);
                    e.printStackTrace();
                }
            }

        });

        BankBenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BenID = BenIdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AmountED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                try {
                    if (s.length() > 2) {
                        cardAmount.setVisibility(View.VISIBLE);
                        double amt = CalculateAmount(Integer.parseInt(AmountED.getText().toString().trim()), Double.parseDouble(tax));
                        String text = AmountED.getText().toString().trim() + " - Tax = ";
                        AmountTotalTV.setText(text + String.format("%.2f", amt));
                        tvWithdrawalAmount.setText(getResources().getString(R.string.rupees) + " " + String.format("%.2f", Double.parseDouble(AmountED.getText().toString().trim())));
                        tvTax.setText(" -  " + getResources().getString(R.string.rupees) + " " + String.format("%.2f", minusAmount));
                        tvTotalAmountsendmoney.setText(getResources().getString(R.string.rupees) + " " + String.format("%.2f", totalAmount));
                    } else {
                        cardAmount.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 246", "amount edit text watcher ", DeviceName);
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

        try {
            Mobile = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
            getBenList();
        } catch (Exception e) {
            logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 267", "onCreate ", DeviceName);
            e.printStackTrace();
        }

        try {
            WalletBalance = Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().INVESTMENT_WALLET_BALANCE));
        } catch (Exception e) {
            WalletBalance = 0.0f;
            logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 246", "INVESTMENT_WALLET_BALANCE ", DeviceName);
            e.printStackTrace();
        }

        tvWalletBalance.setText(getResources().getString(R.string.rupees) + " " + WalletBalance);
    }

    private void CheckValidate() {
        int Amount = 0, limit = 0, limitMin = 0;

        try {
            Amount = Integer.parseInt(AmountED.getText().toString().trim());
            limitMin = (int) Double.parseDouble(LimitMin) - 1;
        } catch (Exception e) {
            logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 246", "number format ", DeviceName);
            e.printStackTrace();
        }

        if (Mode.equalsIgnoreCase("UPI")) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Coming Soon", false);
        } else if (Mode.equalsIgnoreCase("Bank Transfer")) {
            if (TextUtils.isEmpty(AmountED.getText().toString().trim())) {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Enter Amount", false);
            } else {
                if (Amount > limitMin) {

                    if (TextUtils.isEmpty(BenID)) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Select Any Beneficiary", false);
                    } else {

                        if (Amount == (int) WalletBalance || Amount < (int) WalletBalance) {
                            TransferWalletToBankRequest transferWalletToBankRequest = new TransferWalletToBankRequest();
                            transferWalletToBankRequest.setAmount(String.valueOf(Amount));
                            transferWalletToBankRequest.setBeneId(BenID);
                            transferWalletToBankRequestDate = transferWalletToBankRequest;

//                            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
//                                startActivity(new Intent(TransferInvestmentToBank.this, CreatePassCodeActivity.class));
//                            } else {
//                                PasscodeDialog passcodeDialog = new PasscodeDialog(TransferInvestmentToBank.this, TransferInvestmentToBank.this, "");
//                                passcodeDialog.show();
//                            }
                            PasscodeDialog passcodeDialog = new PasscodeDialog(TransferInvestmentToBank.this, TransferInvestmentToBank.this, "");
                            passcodeDialog.show();
                        } else {
                            showDialogFailed("Please Enter Amount Less Than Wallet Balance");
                        }
                    }

                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Enter Amount Greater Than Rs " + LimitMin + ".", true);
                }
            }
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Choose Mode Of Transfer", false);
        }


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.transfer_investment_to_bank;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

        if (isConnected) {
            CheckNet = true;
        } else {
            CheckNet = false;
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Check Your Internet Connection ", false);
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

                                try {
                                    if (response.getBeneficiary().size() > 0) {
                                        try {
                                            for (int i = 0; i < response.getBeneficiary().size(); i++) {
                                                NameList.add(response.getBeneficiary().get(i).getName());
                                                IdList.add(String.valueOf(response.getBeneficiary().get(i).getId()));
                                                BenIdList.add(response.getBeneficiary().get(i).getBenId());
                                            }

                                            try {
                                                ArrayAdapter<String> TransferType = new ArrayAdapter<>(TransferInvestmentToBank.this, android.R.layout.simple_spinner_item, NameList);
                                                TransferType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                BankBenSpinner.setAdapter(TransferType);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "No Beneficiary Found!. Please Add One", true);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), response.getMessage(), true);
                            }
                        } catch (Exception e) {
                            logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 397", "getBeneficiary api ", DeviceName);
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

    private void WithAmountMethod(TransferWalletToBankRequest transferWalletToBankRequest) {


        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.transferInvWalletToBank(transferWalletToBankRequest)
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
                        logRequest = new ExceptionLogRequest(TransferInvestmentToBank.this, UserId, "TransferInvestmentToBank", e.getMessage(), " 438", "transferInvWalletToBank api ", DeviceName);
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.withMoneyLayout), true, e);
                    }
                }));

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.upiradiobtn:
                Mode = "UPI";
                WithdrawAmountlayout.setVisibility(VISIBLE);
                BankBenSpinner.setVisibility(View.GONE);
                BenLayout.setVisibility(View.GONE);
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Coming Soon", false);
                break;

            case R.id.bankradiobtn:
                Mode = "Bank Transfer";
                WithdrawAmountlayout.setVisibility(VISIBLE);
                BankBenSpinner.setVisibility(VISIBLE);
                BenLayout.setVisibility(VISIBLE);
                break;
        }
    }

    private void ShowPending(TransferWalletToBankResponse response) {
        TextView tvNeedHelp, StatusTV, DateTV, TxnIdTV, AmountTV, ProductInfoTV;

        dialogStatus = new Dialog(TransferInvestmentToBank.this, android.R.style.Theme_Light);
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
        AmountTV.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(AmountED.getText().toString())));
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
            final Intent intentStatus = new Intent(TransferInvestmentToBank.this, PaidOrderActivity.class);

            if (responseData.getStatusCode() == 1) {
                intentStatus.putExtra("status", "success");
            } else if (responseData.getStatusCode() == 2) {
                //Toast.makeText(TransferInvestmentToBank.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                intentStatus.putExtra("status", "success");
                //change status to pending on bank issue resolved
            } else {
                Toast.makeText(TransferInvestmentToBank.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                intentStatus.putExtra("status", "failed");
            }
            intentStatus.putExtra("txnid", responseData.getTransactionId());
            intentStatus.putExtra("Amount", AmountED.getText().toString().trim());
            intentStatus.putExtra("date", responseData.getDate());
            intentStatus.putExtra("productinfo", "Investment Wallet To Bank Transaction");
            intentStatus.putExtra("Message", responseData.getMessage());
            intentStatus.putExtra("utr_id", responseData.getUtr());
            startActivity(intentStatus);
            finish();
            dialogStatus.dismiss();

        }
    };

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched) {

            WithAmountMethod(transferWalletToBankRequestDate);
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Invalid Passcode", false);
        }

    }

    /**
     * For static transaction show
     * <p>
     * CountDownTimer countDownTimer = new CountDownTimer(0, 1000) {
     *
     * @Override public void onTick(long millisUntilFinished) {
     * int seconds = (int) (millisUntilFinished / 1000);
     * int minutes = seconds / 60;
     * seconds = seconds % 60;
     * TImer.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
     * }
     * @Override public void onFinish() {
     * <p>
     * Date c = Calendar.getInstance().getTime();
     * <p>
     * SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     * <p>
     * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
     * <p>
     * SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
     * <p>
     * String currentDateandTime = sdf.format(new Date());
     * <p>
     * String txnID = sdf2.format(new Date())+generateRandom(6);
     * <p>
     * <p>
     * String formattedDate = df.format(c);
     * <p>
     * String utrId="93"+String.valueOf(generateRandom(10));
     * <p>
     * <p>
     * final Intent intentStatus = new Intent(TransferInvestmentToBank.this, PaidOrderActivity.class);
     * <p>
     * //            if (responseData.getStatusCode() == 1) {
     * //                intentStatus.putExtra("status", "success");
     * //            } else if (responseData.getStatusCode() == 2) {
     * //                //Toast.makeText(TransferInvestmentToBank.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
     * //                intentStatus.putExtra("status", "success");
     * //                //change status to pending on bank issue resolved
     * //            } else {
     * //                Toast.makeText(TransferInvestmentToBank.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
     * //                intentStatus.putExtra("status", "failed");
     * //            }
     * intentStatus.putExtra("status", "success");
     * intentStatus.putExtra("txnid", txnID);
     * intentStatus.putExtra("Amount", AmountED.getText().toString().trim());
     * intentStatus.putExtra("date", currentDateandTime);
     * intentStatus.putExtra("productinfo", "Wallet To Bank Transaction");
     * intentStatus.putExtra("Message", "Transfer completed successfully");
     * intentStatus.putExtra("utr_id", utrId);
     * startActivity(intentStatus);
     * finish();
     * //dialogStatus.dismiss();
     * <p>
     * }
     * };
     * <p>
     * public static long generateRandom(int length) {
     * Random random = new Random();
     * char[] digits = new char[length];
     * digits[0] = (char) (random.nextInt(9) + '1');
     * for (int i = 1; i < length; i++) {
     * digits[i] = (char) (random.nextInt(10) + '0');
     * }
     * return Long.parseLong(new String(digits));
     * }
     */

    private double CalculateAmount(int amount, double tax) {
        minusAmount = ((((double) amount) / 100) * tax);

        if (minusAmount > 9) {
            totalAmount = (double) amount - minusAmount;
        } else {
            minusAmount = Double.parseDouble("10");
            totalAmount = (double) amount - minusAmount;
        }
        return totalAmount;
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
                            showDialog(TransferInvestmentToBank.this);

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

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.otp_dialog);

        TimerTV = dialog.findViewById(R.id.timerLogin);
        OtpED = dialog.findViewById(R.id.enter_otpLogin);
        im_cross = dialog.findViewById(R.id.im_cross);

        im_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        continueButton = (Button) dialog.findViewById(R.id.continue_otpLogin);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(OtpED.getText().toString().trim())) {
                    OtpED.setError("Please Enter Otp");
                    //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout),"Please Enter Otp",false);
                } else {
                    verifyOtp(OtpED.getText().toString().trim());
                    dialog.dismiss();
                }
            }
        });

        resendButton = (Button) dialog.findViewById(R.id.resend_otpLogin);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public void showDialogFailed(String Message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("SafePe Alert")
                .setCancelable(false)
                .setMessage("\n" + Message + "\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation


                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }
}

