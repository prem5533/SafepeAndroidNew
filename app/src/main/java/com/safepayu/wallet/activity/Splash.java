package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Splash extends AppCompatActivity implements PasscodeClickListener {

    public static PromotionResponse promotionResponse1=new PromotionResponse();

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int phVersion=Build.VERSION.SDK_INT;
        int kitVersion=Build.VERSION_CODES.KITKAT;

        if (phVersion >= kitVersion) {
            BaseApp.getInstance().handler().postDelayed(runnable,2000);
        }else {
            showDialogVersion(this);
        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getPromotionalOfferType121();
            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN) != null) {
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                    startActivity(new Intent(Splash.this,CreatePassCodeActivity.class));
                } else {
                    PasscodeDialog passcodeDialog = new PasscodeDialog(Splash.this, Splash.this, "");
                    passcodeDialog.show();
                }
            } else {
                startActivity(new Intent(Splash.this, LoginActivity.class));
                finish();
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
                            startActivity(new Intent(Splash.this, Navigation.class));
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
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();

    }
}