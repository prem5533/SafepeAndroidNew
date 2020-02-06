package com.safepayu.wallet.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.activity.SplashViewPagerActivity;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.models.request.PromotionRequest;
import com.safepayu.wallet.models.response.PromotionResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.FingerprintActivity2.AttemptAuthentication;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler2 extends FingerprintManager.AuthenticationCallback implements PasscodeClickListener {

    // You should use the CancellationSignal method whenever your app can no longer process user input, for example when your app goes
    // into the background. If you don’t use this method, then other apps will be unable to access the touch sensor, including the lockscreen!//

    private CancellationSignal cancellationSignal;
    private Context context;
    public static PromotionResponse promotionResponse1=new PromotionResponse();

    public FingerprintHandler2(Context mContext) {
        context = mContext;
    }

    //Implement the startAuth method, which is responsible for starting the fingerprint authentication process//

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    //onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters//

    public void onAuthenticationError(int errMsgId, CharSequence errString) {

        //I’m going to display the results of fingerprint authentication as a series of toasts.
        //Here, I’m creating the message that’ll be displayed if an error occurs//

        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();

        AttemptAuthentication=AttemptAuthentication+1;

        if (AttemptAuthentication==3 || AttemptAuthentication>3){
            PasscodeDialog passcodeDialog = new PasscodeDialog(((Activity)context), FingerprintHandler2.this, "");
            passcodeDialog.show();
        }
    }

    @Override

    //onAuthenticationFailed is called when the fingerprint doesn’t match with any of the fingerprints registered on the device//

    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();

        AttemptAuthentication=AttemptAuthentication+1;

        if (AttemptAuthentication==3 || AttemptAuthentication>3){
            PasscodeDialog passcodeDialog = new PasscodeDialog(((Activity)context), FingerprintHandler2.this, "");
            passcodeDialog.show();
        }
    }

    @Override

    //onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
    //so to provide the user with as much feedback as possible I’m incorporating this information into my toast//
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }@Override

    //onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device//
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        getPromotionalOfferType1();
    }

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched){
            if (isNetworkAvailable()){
                getPromotionalOfferType1();
            }else {
                Toast.makeText(context, "Internet Is Not Connected", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, "Invalid Passcode", Toast.LENGTH_SHORT).show();
            PasscodeDialog passcodeDialog = new PasscodeDialog(((Activity)context), FingerprintHandler2.this, "");
            passcodeDialog.show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getPromotionalOfferType1() {

        // loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);


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
                            context.startActivity(new Intent(context, SplashViewPagerActivity.class));
                            ((Activity)context).finish();
                        }catch (Exception e){
                            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        promotionResponse1.setStatus(false);
                    }
                }));
    }
}