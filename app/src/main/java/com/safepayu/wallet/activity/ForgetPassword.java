package com.safepayu.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.OtpReceivedInterface;
import com.safepayu.wallet.helper.SmsBroadcastReceiver;
import com.safepayu.wallet.models.request.ForgetPasswordRequest;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.SendOtpRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.ForgetPasswordResponse;
import com.safepayu.wallet.models.response.UserResponse;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.Gravity.CENTER_VERTICAL;

public class ForgetPassword extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener{

    EditText edit_number, enter_otp, enter_password, confrimPasswordED;
    Button btn_request_otp, btn_continue, btn_conform_password, resend_btn;
    TextView timer, label, back_forgot_password;
    String str_edit_otp, str_edit_conf_pass, str_edit_number, password = "918429";
    private int randomPIN, Otpval;
    private Integer otpToSend;
    boolean resend_top = false;
    String session_id, userid;
    LinearLayout layout1, layout2, layout3;
    private LoadingDialog loadingDialog;
    ApiService apiService;
    private ImageView ShowHidePasswordBtn, forgotImage;
    boolean showPass = false;

    //Sms Receiver
    GoogleApiClient mGoogleApiClient;
    SmsBroadcastReceiver mSmsBroadcastReceiver;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

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

        String imagePath = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LOGO_IMAGE);
        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        edit_number = (EditText) findViewById(R.id.number_forgot);
        label = findViewById(R.id.label);
        timer = findViewById(R.id.timer);
        enter_otp = (EditText) findViewById(R.id.enter_otp);
        enter_password = (EditText) findViewById(R.id.enter_password);
        confrimPasswordED = findViewById(R.id.confirm_password);
        ShowHidePasswordBtn = findViewById(R.id.show_hide_password_forgetPass);

        //  Picasso.get().load(imagePath).into(forgotImage);
        otpToSend = 0;
        Random r = new Random();
        Otpval = r.nextInt(9999 - 1000) + 1000;


        btn_request_otp = (Button) findViewById(R.id.request_otp);
        resend_btn = (Button) findViewById(R.id.resend_otp);
        btn_continue = (Button) findViewById(R.id.continue_otp);
        btn_conform_password = (Button) findViewById(R.id.conformPassword);
        back_forgot_password = (TextView) findViewById(R.id.back_forgot_password);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);

        resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend_top = true;
                resendOtp();
            }
        });

        btn_request_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_number.getText().toString().length() != 10) {
                    Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                } else {
                    str_edit_number = edit_number.getText().toString();
                    resendOtp();
                }
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //continueOtp();

                conformPassword();
            }
        });
        btn_conform_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conformPassword();
            }
        });
        back_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ShowHidePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPass) {
                    showPass = false;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.show_password48));
                    enter_password.setTransformationMethod(new PasswordTransformationMethod());
                    try {
                        enter_password.setSelection(enter_password.getText().toString().length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    showPass = true;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.hide_password48));
                    enter_password.setTransformationMethod(null);
                    try {
                        enter_password.setSelection(enter_password.getText().toString().length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        try {
//            Intent intent = getIntent();
//            if (intent.getData()!=null){
//                Log.v("data",intent.getData().toString());
//
//                String data=intent.getData().toString();
//
//                for (int i=0;i<data.length();i++){
//                    char c=data.charAt(i);
//                    if (c=='@'){
//                        data=data.substring(i+1);
//                        break;
//                    }
//                }
//                Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
//                showDialogAfterBankTrans(ForgetPassword.this,"Move To SafePe");
//            }else {
//                Toast.makeText(this, "Not Working", Toast.LENGTH_SHORT).show();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }

    public void showDialogAfterBankTrans(final Activity activity, String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Set the alert dialog title
        builder.setTitle("SafePe Alert");
        // Set a message for alert dialog
        builder.setMessage(Message);
        // Must call show() prior to fetching text view

        builder  .setIcon(getResources().getDrawable(R.drawable.appicon_new));
        // Set a positive button for alert dialog
        //  builder.setPositiveButton("Say",null);
        // Specifying a listener allows you to take an action before dismissing the dialog.
        // The dialog is automatically dismissed when a dialog button is clicked.
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Continue with delete operation
                Intent i = activity.getPackageManager().getLaunchIntentForPackage("com.safepayu.wallet");
                activity.startActivity(i);
                finish();
                dialog.dismiss();
            }
        });

        // Set a negative button for alert dialog
        //   builder.setNegativeButton("No",null);

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        // Finally, display the alert dialog
        dialog.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER|CENTER_VERTICAL);
        messageText.setPadding(40, 120, 40, 40);
        messageText.setTextSize(16);
        dialog.setCanceledOnTouchOutside(false);
        // Get screen width and height in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);
    }

    void continueOtp() {
        str_edit_otp = enter_otp.getText().toString().trim();

        if (TextUtils.isEmpty(str_edit_otp)) {
            enter_otp.setError("Please Enter OPT");
            enter_otp.requestFocus();
            return;
        } else {
            verifyOtp(enter_otp.getText().toString().trim());
        }
    }

    void conformPassword() {
        str_edit_conf_pass = enter_password.getText().toString().trim();
        String confrimPass = confrimPasswordED.getText().toString().trim();

        if (TextUtils.isEmpty(enter_otp.getText().toString().trim())  || enter_otp.getText().toString().length()!=6){
            enter_otp.setError("Please Enter OTP");
            enter_otp.requestFocus();
        }else {
            if (TextUtils.isEmpty(str_edit_conf_pass)) {

                enter_password.setError("Please Enter Password");
                enter_password.requestFocus();
                confrimPasswordED.setSelection(enter_password.getText().toString().length());
                return;
            } else {
                if (TextUtils.isEmpty(confrimPass)) {
                    confrimPasswordED.setError("Please Enter Password To Confirm");
                    confrimPasswordED.requestFocus();
                    confrimPasswordED.setSelection(confrimPasswordED.getText().toString().length());
                } else {

                    ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
                    forgetPasswordRequest.setMobile(str_edit_number);
                    forgetPasswordRequest.setOtp(Integer.parseInt(enter_otp.getText().toString().trim()));
                    forgetPasswordRequest.setPassword(str_edit_conf_pass);
                    forgetPasswordRequest.setPassword_confirmation(confrimPass);

                    resetPassword(forgetPasswordRequest);
                }
            }
        }
    }

    private void resendOtp() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(edit_number.getText().toString().trim(), null);

        SendOtpRequest sendOtpRequest=new SendOtpRequest();
        sendOtpRequest.setMobile(edit_number.getText().toString().trim());
        sendOtpRequest.setType("2");

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(sendOtpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            timer.setVisibility(View.VISIBLE);
                            layout2.setVisibility(View.VISIBLE);
                            btn_request_otp.setVisibility(View.GONE);
                            resend_btn.setVisibility(View.GONE);

                            startSMSListener();
                            countDownTimer.start();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }

    CountDownTimer countDownTimer = new CountDownTimer(4 * 60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timer.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        }

        @Override
        public void onFinish() {
            resend_btn.setVisibility(View.VISIBLE);
            timer.setVisibility(View.INVISIBLE);
        }
    };

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override public void onSuccess(Void aVoid) {

            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
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
            enter_otp.setText("");
            otp=otp.substring(otp.indexOf(':')+2);

            enter_otp.setText(otp.trim());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onOtpTimeout() {
        //Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show();
    }

    private void verifyOtp(String otp) {


        @SuppressLint("HardwareIds")String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(edit_number.getText().toString().trim(), null);
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
                            label.setHint("Enter New Passcode");
                            layout1.setVisibility(View.GONE);
                            layout2.setVisibility(View.GONE);
                            layout3.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }

    private void resetPassword(ForgetPasswordRequest forgetPasswordRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        BaseApp.getInstance().getDisposable().add(apiService.getForgetPassword(forgetPasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ForgetPasswordResponse>() {
                    @Override
                    public void onSuccess(ForgetPasswordResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            Toast.makeText(ForgetPassword.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            String message="";
                            try{
                                ForgetPasswordResponse.DataBean dataBean=response.getData();
                                if (dataBean!=null){

                                    if (dataBean.getPassword().size()==1){
                                        message=dataBean.getPassword().get(0)+"\n";
                                    }else if (dataBean.getPassword().size()>1){
                                        message=dataBean.getPassword().get(0)+"\n"+dataBean.getPassword().get(1)+"\n";
                                    }

                                    if (dataBean.getPassword_confirmation().size()==1){
                                        message=message+dataBean.getPassword_confirmation().get(0);
                                    }else if (dataBean.getPassword_confirmation().size()>1){
                                        message=message+dataBean.getPassword_confirmation().get(0)+"\n"+dataBean.getPassword_confirmation().get(1)+"\n";
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(message)) {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.forgetPasscodeId), response.getMessage(), true);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.forgetPasscodeId),  message, true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                      //  Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.forgetPasscodeId), true, e);
                    }
                }));
    }
}
