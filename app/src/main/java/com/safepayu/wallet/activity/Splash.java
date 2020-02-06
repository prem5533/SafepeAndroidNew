package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.models.request.PromotionRequest;
import com.safepayu.wallet.models.response.PromotionResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import java.io.File;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Splash extends AppCompatActivity implements PasscodeClickListener {

    public static PromotionResponse promotionResponse1=new PromotionResponse();
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private boolean HardwareDetected=false;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_splash);
        setContentView(R.layout.activity_splash_new);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int phVersion=Build.VERSION.SDK_INT;
        int kitVersion=Build.VERSION_CODES.KITKAT;

        if (phVersion >= kitVersion) {
            BaseApp.getInstance().handler().postDelayed(runnable,3000);
        }else {
            showDialogVersion(this);
        }

        try {
            Configuration configuration = getResources().getConfiguration();
            configuration.locale = new Locale("en");
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
            // or higher before executing any fingerprint-related code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Get an instance of KeyguardManager and FingerprintManager//
                keyguardManager =
                        (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                fingerprintManager =
                        (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

                //Check whether the device has a fingerprint sensor//
                try {
                    if (!fingerprintManager.isHardwareDetected()) {
                        // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                       // Toast.makeText(this, "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show();
                        HardwareDetected=false;
                    }else {
                        HardwareDetected=true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    HardwareDetected=false;
                    //Toast.makeText(this, "Your device doesn't support fingerprint authentication", Toast.LENGTH_LONG).show();

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                getPromotionalOfferType121();
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN) != null) {

                    if (HardwareDetected){
                        startActivity(new Intent(Splash.this, FingerprintActivity2.class));
                        finish();
                    }else {
                        PasscodeDialog passcodeDialog = new PasscodeDialog(Splash.this, Splash.this, "");
                        passcodeDialog.show();
                    }

                } else {
                    startActivity(new Intent(Splash.this, LoginActivity.class));
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched){
            if (isNetworkAvailable()){
                getPromotionalOfferType1();
            }else {
                Toast.makeText(this, "Internet Is Not Connected", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Invalid Passcode", Toast.LENGTH_SHORT).show();
            PasscodeDialog passcodeDialog = new PasscodeDialog(Splash.this, Splash.this, "");
            passcodeDialog.show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getPromotionalOfferType1() {

        // loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);


        final PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setType("1");
        BaseApp.getInstance().getDisposable().add(apiService.getPromotionOffer(promotionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PromotionResponse>() {
                    @Override
                    public void onSuccess(PromotionResponse promotionResponse) {
                        try {
                            if (promotionResponse.isStatus()){
                                promotionResponse1=promotionResponse;

                            }else {
                                promotionResponse1.setStatus(false);
                            }
                            startActivity(new Intent(Splash.this, SplashViewPagerActivity.class));
                            finish();
                        }catch (Exception e){
                            Toast.makeText(Splash.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        promotionResponse1.setStatus(false);
                    }
                }));
    }

    private void getPromotionalOfferType121() {

        // loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);


        final PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setType("1");
        BaseApp.getInstance().getDisposable().add(apiService.getPromotionOffer(promotionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PromotionResponse>() {
                    @Override
                    public void onSuccess(PromotionResponse promotionResponse) {
                        try {
                            if (promotionResponse.isStatus()){
                                promotionResponse1=promotionResponse;

                            }else {
                                promotionResponse1.setStatus(false);
                            }
                        }catch (Exception e){
                            Toast.makeText(Splash.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        promotionResponse1.setStatus(false);
                    }
                }));


    }

    public void showDialogVersion(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("SafePe Alert")
                .setMessage("Your Phone Version Is Not Suitable For SafePe App.\nSafePe App Will Run On Android 4.4(Api Level 19) KitKat Or Higher Version.")
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();

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

    private void clearAppData() {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}