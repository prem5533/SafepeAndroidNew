package com.safepayu.wallet.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.enums.ButtonActions;
import com.safepayu.wallet.helper.AppSignatureHashHelper;
import com.safepayu.wallet.helper.Config;
import com.safepayu.wallet.helper.OtpReceivedInterface;
import com.safepayu.wallet.helper.SmsBroadcastReceiver;
import com.safepayu.wallet.listener.SnackBarActionClickListener;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.SendOtpRequest;
import com.safepayu.wallet.models.response.AppVersionResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.LoginResponse;
import com.safepayu.wallet.models.response.UserResponse;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements View.OnClickListener, SnackBarActionClickListener,
        GoogleApiClient.ConnectionCallbacks, OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener{
    private static String TAG = LoginActivity.class.getName();
    private EditText mobileNo, password;
    private CheckBox RememberMeCB;
    private ApiService apiService;
    private LoadingDialog loadingDialog;
    String versionName = "", appUrl = "https://play.google.com/store/apps/details?id=com.safepayu.wallet&hl=en";
    private int versionCode = 0;
    private ImageView im_cross, ShowHidePasswordBtn,loginImageLogo;
    private boolean showPass = false, checkedRemember=false;
    private LoginResponse loginResponse;
    private String url;
    public static double finalAmount=0;

    //Otp Dialog
    private TextView TimerTV;
    private EditText OtpED;
    private Dialog dialogOTP;
    private Button continueButton, resendButton;
    private String ImagePath="http://india.safepayu.com/safepe-new/public/";

     //Sms Receiver
     GoogleApiClient mGoogleApiClient;
    SmsBroadcastReceiver mSmsBroadcastReceiver;
    private int RESOLVE_HINT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setToolbar(false, null, true);

        // init broadcast receiver
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        //set google api client for hint request
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingDialog = new LoadingDialog(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);

        // This code requires one time to get Hash keys do comment and share key
        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);
        //Log.v(TAG, "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0));

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        mobileNo = findViewById(R.id.et_mobileNo);
        loginImageLogo = findViewById(R.id.login_image_logo);

        password = findViewById(R.id.et_password);
        RememberMeCB = findViewById(R.id.cb_rememberMe);
        ShowHidePasswordBtn = findViewById(R.id.show_hide_password);
        //mobileNo.addTextChangedListener(new MobileEditTextWatcher(mobileNo));
        //mobileNo.setText("+91 ");
        password.setText("");
        //mobileNo.setSelection(mobileNo.getText().length());
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_forgetPass).setOnClickListener(this);
        findViewById(R.id.btn_newAccount).setOnClickListener(this);
        findViewById(R.id.show_hide_password).setOnClickListener(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.v("getInstanceId failed", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String FirebaseToken = task.getResult().getToken();
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FIREBASE_TOKEN, FirebaseToken);
                    }
                });

        RememberMeCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck){
                    RememberMeCB.setChecked(true);
                    checkedRemember=true;
                }else {
                    RememberMeCB.setChecked(false);
                    checkedRemember=false;
                }
            }
        });

//        checkPermission();
        getAppVersion();

        //getHintPhoneNumber();
    }

//    public void getHintPhoneNumber() {
//        HintRequest hintRequest =
//                new HintRequest.Builder()
//                        .setPhoneNumberIdentifierSupported(true)
//                        .build();
//        PendingIntent mIntent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
//        try {
//            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
//        } catch (IntentSender.SendIntentException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    // credential.getId();  <-- will need to process phone number string
                    mobileNo.setText(credential.getId());
                }
            }
        }
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override public void onSuccess(Void aVoid) {
                //Toast.makeText(LoginActivity.this, "SMS Retriever starts", Toast.LENGTH_LONG).show();
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
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
    public void onOtpTimeout() {
        //Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show();
    }

    @Override
    protected int getLayoutResourceId() {
       // return R.layout.activity_login;
        return R.layout.login_demo;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                BaseApp.getInstance().commonUtils().hideKeyboard(this);
                if (checkPermission() && validate()) {
                    //resendOtp();
                    loginUser();
                }
                break;
            case R.id.btn_forgetPass:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
            case R.id.btn_newAccount:
                startActivity(new Intent(this, NewAccount.class));
                break;
            case R.id.show_hide_password:


                if (showPass) {
                    showPass = false;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.show_password48));
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    try {
                        password.setSelection(password.getText().toString().length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    showPass = true;
                    ShowHidePasswordBtn.setImageDrawable(getResources().getDrawable(R.drawable.hide_password48));
                    password.setTransformationMethod(null);
                    try {
                        password.setSelection(password.getText().toString().length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    public Boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return false;
        }
        return true;
    }

    private void requestPermission() {
        // BEGIN_INCLUDE(read_phone_state_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            loginUser();
            BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.permission_read_phone_state), false, getResources().getString(R.string.ok), ButtonActions.SHOW_SETTING, this);
        } else {

            // Read phone state permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    BaseApp.getInstance().commonUtils().REQUEST_RED_PONE_STATE);
        }
        // END_INCLUDE(read_phone_state_permission_request)
    }

    @Override
    public void onPositiveClick(View view, ButtonActions action) {
        switch (action) {
            case SHOW_SETTING:
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                break;
            case VERIFY_MOBILE:
                startActivity(new Intent(LoginActivity.this, OtpVerification.class).putExtra(Config.MOBILE_NO, mobileNo.getText().toString()));
                break;
            default:
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, BaseApp.getInstance().commonUtils().REQUEST_RED_PONE_STATE);
                break;
        }

    }

    private Boolean validate() {
        if (mobileNo.getText().toString().trim().length() == 0) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter valid phone number", true);
            return false;
        } else if (PhoneNumberUtils.isGlobalPhoneNumber("+91 " + mobileNo.getText().toString().trim())) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter valid phone number", true);
            return false;
        } else if (mobileNo.getText().toString().trim().length() != 10) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter valid phone number", true);
            return false;
        } else if (password.getText().toString().trim().length() == 0) {
            mobileNo.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), "Please enter password", true);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == BaseApp.getInstance().commonUtils().REQUEST_RED_PONE_STATE) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result.
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, preview can be displayed
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.permission_granted), true);
            } else {
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.permission_not_granted), false, getResources().getString(R.string.setting), ButtonActions.SHOW_SETTING, this);
            }
            // END_INCLUDE(permission_result)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getAppVersion() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AppVersionResponse>() {
                    @Override
                    public void onSuccess(AppVersionResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            if (response.getVersionData().getStatus()==1){
                                int val = Integer.parseInt(response.getVersionData().getVal());

                                url = response.getVersionData().getLogo();
                                //  BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().LOGO_IMAGE, ImagePath+url);
                                //  Picasso.get().load(ImagePath+url).into(loginImageLogo);

                                if (versionCode == val) {

                                } else {
                                    showDialogForAppUpdate(LoginActivity.this);
                                }
                            }

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                      //  Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), false, e.getCause());
                    }
                }));
    }

    private void loginUser() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        @SuppressLint("HardwareIds")String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Login login=new Login(mobileNo.getText().toString().trim(),password.getText().toString().trim(),android_id);

        BaseApp.getInstance().getDisposable().add(apiService.login(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            loginResponse=response;
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().MOBILE, mobileNo.getText().toString().trim());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_ID, response.getUserId());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().REMEMBER_ME, response.getRemember_me());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, response.getAccessToken());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN_EXPIRE_IN, response.getTokenExpiresIn());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN_ECOM, response.getToken());

                            CheckStatusCode(response);

                        }else {
                            String message="";

                            try{
                                BaseResponse.DataBean dataBean=response.getData();
                                if (dataBean!=null){

                                    if (dataBean.getMobile().size()==1){
                                        message=message+dataBean.getMobile().get(0);
                                    }else if (dataBean.getMobile().size()>1){
                                        message=message+dataBean.getMobile().get(0)+"\n"+dataBean.getMobile().get(1)+"\n";
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(message)) {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), false);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout),  message, false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

    private void CheckStatusCode(LoginResponse response){

        switch (response.getStatusCode()) {
            case 0:
                if (response.getRemember_me().equalsIgnoreCase("1")){
                    SaveLoginDetails(response);
                    startActivity(new Intent(LoginActivity.this,SplashViewPagerActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }else {
                    resendOtp();
                }

                break;
            case 1:
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, response.getMessage(), false);
                break;
            case 2:
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, response.getMessage(), true, getResources().getString(R.string.verify), ButtonActions.VERIFY_MOBILE, LoginActivity.this);
                break;
            case 3:

                Toast.makeText(LoginActivity.this, "Please First Set Passcode", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, CreatePassCodeActivity.class));
                break;
            case 4:
                Toast.makeText(LoginActivity.this, "Please First Set Address", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, AddUpdateAddress.class));
                break;
            case 5:
                //showDialogForEmail(LoginActivity.this);  for mail verification
                if (response.getRemember_me().equalsIgnoreCase("1")){
                    SaveLoginDetails(response);
                    startActivity(new Intent(LoginActivity.this,Navigation.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }else {
                    resendOtp();
                }

                break;
        }
    }

    private void SaveLoginDetails(LoginResponse response){
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().REMEMBER_ME, response.getRemember_me());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, response.getAccessToken());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN_EXPIRE_IN, response.getTokenExpiresIn());
    }

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
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().MOBILE, "");
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_ID, "");
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().REMEMBER_ME, "");
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, null);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN_EXPIRE_IN, "");
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN_ECOM, "");

                dialogOTP.dismiss();
            }
        });

        continueButton =  dialogOTP.findViewById(R.id.continue_otpLogin);
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

        resendButton = dialogOTP.findViewById(R.id.resend_otpLogin);
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
    public void onOtpReceived(String otp) {
        try {
            OtpED.setText("");
            otp=otp.substring(otp.indexOf(':')+2);

            OtpED.setText(otp.trim());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDialogForAppUpdate(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.app_update_dialog);

        Button proceedButton = dialog.findViewById(R.id.proceedBtn_appUpdate);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
                try {
                    deleteCache(LoginActivity.this);
                }catch (Exception e){
                    e.printStackTrace();

                }
                try {
                    clearAppData();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button cancelBtn_appUpdate =  dialog.findViewById(R.id.cancelBtn_appUpdate);
        cancelBtn_appUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApp.getInstance().toastHelper().showSnackBar(mobileNo, getResources().getString(R.string.waringforAppUpdate), true);
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    private void clearAppData() {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
              try {
                  String packageName = getApplicationContext().getPackageName();
                  Runtime runtime = Runtime.getRuntime();
                  runtime.exec("pm clear "+packageName);
              }catch (Exception rr){
                  rr.printStackTrace();
              }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {

        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void showDialogForEmail(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.verify_email_dialog);

        im_cross = dialog.findViewById(R.id.im_cross);

        final EditText emailEd=dialog.findViewById(R.id.enter_EmailLogin);

        im_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button proceedButton = dialog.findViewById(R.id.continue_EmailLogin);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailEd.getText().toString().trim())){
                    emailEd.setError("Please Enter Email Id");
                    emailEd.requestFocus();
                }else {
                    if (isValidEmail(emailEd.getText().toString().trim())){

                        sendVerifyLink(emailEd.getText().toString().trim(),dialog);
                    }else {
                        emailEd.setError("Please Enter Correct Email Id");
                        emailEd.requestFocus();
                    }
                }

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void resendOtp() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(mobileNo.getText().toString().trim(), null);

        SendOtpRequest sendOtpRequest=new SendOtpRequest();
        sendOtpRequest.setMobile(mobileNo.getText().toString().trim());
        sendOtpRequest.setType("1");

        BaseApp.getInstance().getDisposable().add(apiService.resendOtp(sendOtpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            showDialog(LoginActivity.this);
                            TimerTV.setVisibility(View.VISIBLE);

                            countDownTimer.start();
                            startSMSListener();
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

    CountDownTimer countDownTimer = new CountDownTimer(4*60000, 1000) {
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

    private void verifyOtp(String otp) {

        @SuppressLint("HardwareIds")String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        Login request = new Login(mobileNo.getText().toString().trim(), null);
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
                            SaveLoginDetails(loginResponse);
                            startActivity(new Intent(LoginActivity.this,Navigation.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }

    private void sendVerifyLink(String Email, final Dialog dialog) {

        String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        BaseApp.getInstance().getDisposable().add(apiService.verifyEmail(UserId,Email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        dialog.dismiss();
                        if (response.getStatus()) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage() + "\n" + "Please Verify From Your Email Account", true);
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.layout_mainLayout), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                      //  Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.layout_mainLayout), true, e);
                    }
                }));
    }
}
