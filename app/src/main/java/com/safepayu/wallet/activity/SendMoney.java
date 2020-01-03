package com.safepayu.wallet.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.SendOtpRequest;
import com.safepayu.wallet.models.request.TransferWalletToBankRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.TransferWalletToBankResponse;
import com.safepayu.wallet.models.response.UserResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class SendMoney extends BaseActivity implements RadioGroup.OnCheckedChangeListener, PasscodeClickListener {

    Button BackBtn, WithDrawBtn;
    private LinearLayout WithdrawAmountlayout, AddBankBenBtn,BenLayout;
    private TextView AmountTotalTV, tvWithdrawalAmount, tvTax, tvTotalAmountsendmoney,tvWalletBalance,tvTransactionFee;
    private TextView tvMaxLimit,tvMinLimit;
    private Spinner BankBenSpinner;
    private EditText AmountED;
    private RadioGroup radioGroup;
    private String Mode = "", BenID = "", Mobile = "",Limit="0",LimitMin="0";
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
    double totalAmount = 0.0f, minusAmount = 0.0f,WalletBalance=0.0f;
    int checkAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        Limit=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LIMIT);
        LimitMin=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LIMIT_MIN);

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
        tvTransactionFee=findViewById(R.id.transactionFeeText_sendMoney);
        tvMaxLimit=findViewById(R.id.maxLimit_sendMoney);
        tvMinLimit=findViewById(R.id.minLimit_sendMoney);
        BenLayout=findViewById(R.id.benLayout_sendMoney);

        tvMaxLimit.setText(getResources().getString(R.string.rupees)+" "+Limit);
        tvMinLimit.setText(getResources().getString(R.string.rupees)+" "+LimitMin);

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

        final String tax=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().TRANSACTION_CHARGE);
        tvTransactionFee.setText("Transaction fee: "+tax+"% or Minimum ₹ 10 ");

        AddBankBenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            }
        });

        WithDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Goto Your Profile and Verify Your Email First", true);
                } else {
                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Buy Membership To Enjoy App's Features", true);
                    } else {
                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().IS_BLOCKED).equalsIgnoreCase("0")) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Withdraw Is Closed Today", true);
                        } else {
                            CheckValidate();
                        }
                    }
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
                if (s.length() > 2) {
                    cardAmount.setVisibility(View.VISIBLE);
                    double amt = CalculateAmount(Integer.parseInt(AmountED.getText().toString().trim()),Double.parseDouble(tax));
                    String text = AmountED.getText().toString().trim() + " - Tax = ";
                    AmountTotalTV.setText(text + String.format("%.2f", amt));
                    tvWithdrawalAmount.setText(AmountED.getText().toString().trim() + " " + getResources().getString(R.string.rupees));
                    tvTax.setText(" -  " + new DecimalFormat("##.##").format(minusAmount) + " " + getResources().getString(R.string.rupees));
                    tvTotalAmountsendmoney.setText(String.format("%.2f", totalAmount) + " " + getResources().getString(R.string.rupees));
                } else {
                    cardAmount.setVisibility(View.GONE);
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
            e.printStackTrace();
        }

        try {
            WalletBalance= Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().WALLET_BALANCE));
        }catch (Exception e){
            WalletBalance=0.0f;
            e.printStackTrace();
        }

        tvWalletBalance.setText(getResources().getString(R.string.rupees)+" "+WalletBalance);
    }

    private void CheckValidate() {
        int Amount = 0,limit=0,limitMin=0;

        try {
            Amount = Integer.parseInt(AmountED.getText().toString().trim());
            limit = (int)Double.parseDouble(Limit)+1;
            limitMin = (int)Double.parseDouble(LimitMin)-1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Mode.equalsIgnoreCase("UPI")) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Coming Soon", false);
        } else if (Mode.equalsIgnoreCase("Bank Transfer")) {
            if (TextUtils.isEmpty(AmountED.getText().toString().trim())) {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Enter Amount", false);
            } else {
                if (Amount < limit && Amount > limitMin) {

                    if (TextUtils.isEmpty(BenID)) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Select Any Beneficiary", false);
                    } else {

                        if (Amount==(int)WalletBalance || Amount<(int)WalletBalance){
                            TransferWalletToBankRequest transferWalletToBankRequest = new TransferWalletToBankRequest();
                            transferWalletToBankRequest.setAmount(String.valueOf(Amount));
                            transferWalletToBankRequest.setBeneId(BenID);
                            transferWalletToBankRequestDate = transferWalletToBankRequest;

                            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                                startActivity(new Intent(SendMoney.this, CreatePassCodeActivity.class));
                            } else {
                                PasscodeDialog passcodeDialog = new PasscodeDialog(SendMoney.this, SendMoney.this, "");
                                passcodeDialog.show();
                            }
                        }else {
                            showDialogFailed("Please Enter Amount Less Than Wallet Balance");
                        }
                    }

                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Please Enter Amount Between Rs "+LimitMin+" & Rs "+Limit, true);
                }
            }
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.withMoneyLayout), "Choose Mode Of Transfer", false);
        }


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.send_money;
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
                                                ArrayAdapter<String> TransferType = new ArrayAdapter<>(SendMoney.this, android.R.layout.simple_spinner_item, NameList);
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

        BaseApp.getInstance().getDisposable().add(apiService.transferWalletToBank(transferWalletToBankRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TransferWalletToBankResponse>() {
                    @Override
                    public void onSuccess(TransferWalletToBankResponse response) {
                        loadingDialog.hideDialog();
                       if (response.isStatus()){
                           responseData = response;
                           ShowPending(response);
                       }else {
                           showDialogFailed(response.getMessage());
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
            final Intent intentStatus = new Intent(SendMoney.this, PaidOrderActivity.class);

            if (responseData.getStatusCode() == 1) {
                intentStatus.putExtra("status", "success");
            } else if (responseData.getStatusCode() == 2) {
                //Toast.makeText(SendMoney.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                intentStatus.putExtra("status", "success");
                //change status to pending on bank issue resolved
            } else {
                Toast.makeText(SendMoney.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
                intentStatus.putExtra("status", "failed");
            }
            intentStatus.putExtra("txnid", responseData.getTransactionId());
            intentStatus.putExtra("Amount", AmountED.getText().toString().trim());
            intentStatus.putExtra("date", responseData.getDate());
            intentStatus.putExtra("productinfo", "Wallet To Bank Transaction");
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

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

            String currentDateandTime = sdf.format(new Date());

            String txnID = sdf2.format(new Date())+generateRandom(6);


            String formattedDate = df.format(c);

            String utrId="93"+String.valueOf(generateRandom(10));


            final Intent intentStatus = new Intent(SendMoney.this, PaidOrderActivity.class);

//            if (responseData.getStatusCode() == 1) {
//                intentStatus.putExtra("status", "success");
//            } else if (responseData.getStatusCode() == 2) {
//                //Toast.makeText(SendMoney.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
//                intentStatus.putExtra("status", "success");
//                //change status to pending on bank issue resolved
//            } else {
//                Toast.makeText(SendMoney.this, responseData.getMessage(), Toast.LENGTH_LONG).show();
//                intentStatus.putExtra("status", "failed");
//            }
            intentStatus.putExtra("status", "success");
            intentStatus.putExtra("txnid", txnID);
            intentStatus.putExtra("Amount", AmountED.getText().toString().trim());
            intentStatus.putExtra("date", currentDateandTime);
            intentStatus.putExtra("productinfo", "Wallet To Bank Transaction");
            intentStatus.putExtra("Message", "Transfer completed successfully");
            intentStatus.putExtra("utr_id", utrId);
            startActivity(intentStatus);
            finish();
            //dialogStatus.dismiss();

        }
    };

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    */

    private double CalculateAmount(int amount,double tax) {
        minusAmount = ((((double) amount) / 100) * tax);
        totalAmount = (double) amount - minusAmount;
        checkAmount = (int) minusAmount;
        if (checkAmount > 9) {

        } else {
            minusAmount = 10;
            totalAmount = (double) amount - (double) minusAmount;
        }

        return totalAmount;
    }

    private void resendOtp() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(Mobile, null);

        SendOtpRequest sendOtpRequest=new SendOtpRequest();
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
                            showDialog(SendMoney.this);

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(Mobile, null);
        request.setDeviceid(BaseApp.getInstance().commonUtils().getTelephonyManager().getDeviceId());
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

    CountDownTimer countDownTimerOtp = new CountDownTimer(4*60000, 1000) {
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
                .setMessage("\n"+Message+"\n")

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
