package com.safepayu.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.OtpReceivedInterface;
import com.safepayu.wallet.helper.SmsBroadcastReceiver;
import com.safepayu.wallet.models.request.AddBeneficiaryRequest;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.SendOtpRequest;
import com.safepayu.wallet.models.response.AddBeneficiaryResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.UserResponse;
import com.safepayu.wallet.models.response.VerifyIFSCResponse;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddBeneficiary extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {

    private Button BackBtn, AddBenBtn;
    private EditText AccountNameED, AccountNumberED, AccountConfirmED, IFSCED;
    private LoadingDialog loadingDialog;
    private boolean CheckNet = false;
    private ImageView showAccNo, HideAccNo;
    private ApiService apiService;
    private String Mobile = "", BankName = "", BankBranch = "", BankAddress = "";
    private AddBeneficiaryRequest addBeneficiaryRequest;
    private boolean checkIfsc = false;
    //Otp Dialog
    TextView TimerTV, VerifyIfscBtn;
    EditText OtpED;
    Button continueButton, resendButton;
    private ImageView im_cross;
    private Dialog dialogOTP;
    //Sms Receiver
    GoogleApiClient mGoogleApiClient;
    SmsBroadcastReceiver mSmsBroadcastReceiver;
    private int RESOLVE_HINT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        try {
            Configuration configuration = getResources().getConfiguration();
            configuration.locale = new Locale("en");
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            Locale.setDefault(configuration.locale);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // init broadcast receiver
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        //set google api client for hint request
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);

        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        addBeneficiaryRequest = new AddBeneficiaryRequest();

        Mobile = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);

        BackBtn = findViewById(R.id.send_back_btn);
        AccountNameED = findViewById(R.id.accountName);
        AccountNumberED = findViewById(R.id.accountNumber);
        AccountConfirmED = findViewById(R.id.confirmAccountNumber);
        IFSCED = findViewById(R.id.ifscCode);
        AddBenBtn = findViewById(R.id.bankAddBtn);
        showAccNo = findViewById(R.id.password_visible);
        HideAccNo = findViewById(R.id.password_invisible);
        VerifyIfscBtn = findViewById(R.id.verifyIfscBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddBenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckValidate();
            }
        });

        showAccNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccNo.setVisibility(View.GONE);
                HideAccNo.setVisibility(View.VISIBLE);
                AccountConfirmED.setTransformationMethod(null);
                try {
                    AccountConfirmED.setSelection(AccountConfirmED.getText().toString().length());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        HideAccNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccNo.setVisibility(View.VISIBLE);
                HideAccNo.setVisibility(View.GONE);
                AccountConfirmED.setTransformationMethod(new PasswordTransformationMethod());
                try {
                    AccountConfirmED.setSelection(AccountConfirmED.getText().toString().length());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        VerifyIfscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(IFSCED.getText().toString().trim())) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Please Enter IFSC Code", false);
                } else {
                    if (checkIfsc) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "IFSC Code Already Verified", false);
                    } else {
                        //new CheckIfscMethod().execute();
                        getifscVerify();
                    }
                }
            }
        });

        IFSCED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                if (checkIfsc) {
                    checkIfsc = false;
                    VerifyIfscBtn.setTextColor(getResources().getColor(R.color.red_theme));
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
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.add_beneficiary;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected) {
            CheckNet = true;
        } else {
            CheckNet = false;
        }

    }

    private void CheckValidate() {

        String AccountN = AccountNameED.getText().toString().trim();
        String AccountNF = AccountConfirmED.getText().toString().trim();

        if (TextUtils.isEmpty(AccountNameED.getText().toString().trim())) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Please Enter Name As On Account", false);
        } else {
            if (TextUtils.isEmpty(AccountNumberED.getText().toString().trim())) {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Please Enter Account Number", false);
            } else {
                if (TextUtils.isEmpty(AccountConfirmED.getText().toString().trim())) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Please Enter Account Number To Confirm", false);
                } else {
                    if (TextUtils.isEmpty(IFSCED.getText().toString().trim())) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Please Enter IFSC Code", false);
                    } else {
                        if (AccountN.equals(AccountNF)) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Please Same Account Number In Both Account's Field", false);
                        } else {
                            addBeneficiaryRequest.setName(AccountNameED.getText().toString().trim());
                            addBeneficiaryRequest.setBank_account(AccountNumberED.getText().toString().trim());
                            addBeneficiaryRequest.setIfsc_code(IFSCED.getText().toString().trim());
                            addBeneficiaryRequest.setUpi("");
                            addBeneficiaryRequest.setPaytm("");

                            if (checkIfsc) {
                                if (CheckNet) {

                                    showDetailDialog();
                                } else {
                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Check Your Internet Connection!", false);
                                }
                            } else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Please Verify Your IFSC Code First", false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addBenMethod(AddBeneficiaryRequest addBeneficiaryRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        BaseApp.getInstance().getDisposable().add(apiService.addBeneficiary(addBeneficiaryRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddBeneficiaryResponse>() {
                    @Override
                    public void onSuccess(AddBeneficiaryResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            showDialogAfterAddBen(AddBeneficiary.this, response.getMessage());
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), response.getMessage() + "\n" + response.getReason(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.addBeneficiaryLayout), true, e);
                    }
                }));

    }

    private void resendOtp() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(Mobile, null);
        SendOtpRequest sendOtpRequest = new SendOtpRequest();
        sendOtpRequest.setMobile(Mobile);
        sendOtpRequest.setType("3");

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(sendOtpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            countDownTimer.start();
                            startSMSListener();
                            showDialog(AddBeneficiary.this);

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.addBeneficiaryLayout), true, e);
                    }
                }));
    }

    private void verifyOtp(String otp) {

        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(Mobile, null);
        request.setOtp(otp);
        request.setDeviceid(android_id);
        BaseApp.getInstance().getDisposable().add(apiService.verifyOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            addBenMethod(addBeneficiaryRequest);
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.addBeneficiaryLayout), true, e);
                    }
                }));
    }

    CountDownTimer countDownTimer = new CountDownTimer(4 * 60000, 1000) {
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
        dialogOTP = new Dialog(activity);
        dialogOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(R.layout.otp_dialog);

        TimerTV = dialogOTP.findViewById(R.id.timerLogin);
        OtpED = dialogOTP.findViewById(R.id.enter_otpLogin);
        im_cross = dialogOTP.findViewById(R.id.im_cross);

        im_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOTP.dismiss();
            }
        });

        continueButton = (Button) dialogOTP.findViewById(R.id.continue_otpLogin);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(OtpED.getText().toString().trim())) {
                    OtpED.setError("Please Enter Otp");
                    //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout),"Please Enter Otp",false);
                } else {
                    verifyOtp(OtpED.getText().toString().trim());
                    dialogOTP.dismiss();
                }
            }
        });

        resendButton = (Button) dialogOTP.findViewById(R.id.resend_otpLogin);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
                dialogOTP.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogOTP.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialogOTP.getWindow().setAttributes(lp);
        dialogOTP.show();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOtpReceived(String otp) {
        try {
            OtpED.setText("");
            OtpED.setText(otp.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOtpTimeout() {
        //Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show();
    }

    public void showDialogAfterAddBen(Activity activity, String Response) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("SafePe Alert")
                .setMessage("\n" + Response + "\nPlease Wait For Minimum Of 30 Mins After Adding Of Beneficiary")
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        finish();
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }

    private void getifscVerify() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(AddBeneficiary.this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getifscVerify(IFSCED.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<VerifyIFSCResponse>() {
                    @Override
                    public void onSuccess(VerifyIFSCResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                BankName = response.getData().getBANK();
                                BankAddress = response.getData().getADDRESS();
                                BankBranch = response.getData().getBRANCH();
                                checkIfsc = true;
                                VerifyIfscBtn.setTextColor(getResources().getColor(R.color.yellow_theme));
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), response.getMessage(), false);
                            } catch (Exception e) {
                                checkIfsc = false;
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), e.getMessage(), false);
                                e.printStackTrace();
                            }
                        } else {
                            checkIfsc = false;
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.addBeneficiaryLayout), false, e.getCause());
                    }
                }));
    }

    private void showDetailDialog() {

        final Dialog dialog = new Dialog(AddBeneficiary.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ifsc_detail_dialog);


        TextView tvAccName, tvAccNumber, tvBank, tvBranch, tvAddress;
        Button CancelBtn, ProceedBtn;

        CancelBtn = dialog.findViewById(R.id.cancelBtn_dialogBen);
        ProceedBtn = dialog.findViewById(R.id.bankAddBtn_dialogBen);
        tvAccName = dialog.findViewById(R.id.accountName);
        tvBank = dialog.findViewById(R.id.accountBank);
        tvAccNumber = dialog.findViewById(R.id.accountNumber);
        tvBranch = dialog.findViewById(R.id.accountBranch);
        tvAddress = dialog.findViewById(R.id.accountBankAddress);

        tvAccName.setText(addBeneficiaryRequest.getName());
        tvBank.setText(BankName);
        tvAccNumber.setText(addBeneficiaryRequest.getBank_account());
        tvBranch.setText(BankBranch);
        tvAddress.setText(BankAddress);

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();

    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddBeneficiary.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public class CheckIfscMethod extends AsyncTask<String, String, String> {
        String apiUrl = "https://ifsc.razorpay.com/" + IFSCED.getText().toString().trim();


        protected void onPreExecute() {
            loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        }

        protected String doInBackground(String... urls) {

            String result = null;
            try {
                URL url = new URL(apiUrl);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();

                con.setAllowUserInteraction(false);
                con.setInstanceFollowRedirects(true);
                // con.setRequestMethod("POST");
                con.connect();
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try {
                        InputStream in = new BufferedInputStream(con.getInputStream());

                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(in));

                        String json;

                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    result = sb.toString().trim();
                } else {

                    //    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                // return null;
            }
            return result;
        }

        protected void onPostExecute(String response) {
            loadingDialog.hideDialog();

            try {

                if (TextUtils.isEmpty(new JSONObject(response).getString("IFSC"))) {
                    checkIfsc = false;
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Wrong IFSC Code", false);
                } else {
                    checkIfsc = true;
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "IFSC Code Verified", false);
                }
            } catch (Exception e) {
                checkIfsc = false;
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addBeneficiaryLayout), "Wrong IFSC Code", false);
                e.printStackTrace();
            }
        }
    }
}
