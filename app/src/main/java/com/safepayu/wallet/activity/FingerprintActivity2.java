package com.safepayu.wallet.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.helper.FingerprintHandler2;
import com.safepayu.wallet.models.request.PromotionRequest;
import com.safepayu.wallet.models.response.PromotionResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintActivity2 extends AppCompatActivity implements PasscodeClickListener {

    // Declare a string variable for the key we’re going to use in our fingerprint authentication
    private static final String KEY_NAME = "safepe_key";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private TextView textView;
    private Button OpenPasscodeBtn;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    public static int AttemptAuthentication=0;

    public static PromotionResponse promotionResponse1=new PromotionResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2finger);

        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get an instance of KeyguardManager and FingerprintManager//
            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            textView = findViewById(R.id.errorText);
            OpenPasscodeBtn = findViewById(R.id.openPasscodeBtn);

            OpenPasscodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PasscodeDialog passcodeDialog = new PasscodeDialog(FingerprintActivity2.this, FingerprintActivity2.this, "");
                    passcodeDialog.show();
                }
            });

            //Check whether the device has a fingerprint sensor//
            try {
                if (!fingerprintManager.isHardwareDetected()) {
                    // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                    textView.setText("Your device doesn't support fingerprint authentication");
                    Toast.makeText(this, "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show();
                    PasscodeDialog passcodeDialog = new PasscodeDialog(FingerprintActivity2.this, FingerprintActivity2.this, "");
                    passcodeDialog.show();
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Your device doesn't support fingerprint authentication", Toast.LENGTH_LONG).show();
                PasscodeDialog passcodeDialog = new PasscodeDialog(FingerprintActivity2.this, FingerprintActivity2.this, "");
                passcodeDialog.show();
            }

            try {
                //Check whether the user has granted your app the USE_FINGERPRINT permission//
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    // If your app doesn't have this permission, then display the following text//
                    textView.setText("Please enable the fingerprint permission");
                    showDialog("Please enable the fingerprint permission");
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Please enable the fingerprint permission", Toast.LENGTH_LONG).show();
                showDialog("Please enable the fingerprint permission");
                textView.setText("Please enable the fingerprint permission");
            }

            try {
                //Check that the user has registered at least one fingerprint//
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    // If the user hasn’t configured any fingerprints, then display the following message//
                    textView.setText("No fingerprint configured.\nPlease register at least one fingerprint\nin your device's Settings");
                    showDialog("No fingerprint configured.\nPlease register at least one fingerprint\nin your device's Settings");
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "No fingerprint configured. Please register at least one fingerprint in your device's Settings", Toast.LENGTH_LONG).show();
                textView.setText("No fingerprint configured.\nPlease register at least one fingerprint\nin your device's Settings");
                showDialog("No fingerprint configured.\nPlease register at least one fingerprint\nin your device's Settings");
            }

            try {
                //Check that the lockscreen is secured//
                if (!keyguardManager.isKeyguardSecure()) {
                    // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                    textView.setText("Please enable lockscreen security\nin your device's Settings");
                    showDialog("Please enable lockscreen security\nin your device's Settings");
                } else {
                    try {
                        generateKey();
                    } catch (FingerprintException e) {
                        e.printStackTrace();
                    }

                    if (initCipher()) {
                        //If the cipher is initialized successfully, then create a CryptoObject instance//
                        cryptoObject = new FingerprintManager.CryptoObject(cipher);

                        // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                        // for starting the authentication process (via the startAuth method) and processing the authentication process events//
                        FingerprintHandler2 helper = new FingerprintHandler2(this);
                        helper.startAuth(fingerprintManager, cryptoObject);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Please enable lockscreen security\nin your device's Settings", Toast.LENGTH_LONG).show();
                textView.setText("Please enable lockscreen security\nin your device's Settings");
                showDialog("Please enable lockscreen security\nin your device's Settings");
            }

        }
    }

//Create the generateKey method that we’ll use to gain access to the Android keystore and generate the encryption key//

    private void generateKey() throws FingerprintException {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Generate the key//
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize an empty KeyStore//
            keyStore.load(null);

            //Initialize the KeyGenerator//
            keyGenerator.init(new

                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key//
            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    //Create a new method that we’ll use to initialize our cipher//
    public boolean initCipher() {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully//
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {

            //Return false if cipher initialization failed//
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }

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
            PasscodeDialog passcodeDialog = new PasscodeDialog(FingerprintActivity2.this, FingerprintActivity2.this, "");
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
                            startActivity(new Intent(FingerprintActivity2.this, SplashViewPagerActivity.class));
                            finish();
                        }catch (Exception e){
                            Toast.makeText(FingerprintActivity2.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        promotionResponse1.setStatus(false);
                    }
                }));
    }

    public void showDialog(String Title) {
        new AlertDialog.Builder(FingerprintActivity2.this)
                .setTitle(Title)
                .setMessage("Go To Settings")
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 1);
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {
                    //  String newText = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
                    startActivity(new Intent(FingerprintActivity2.this, FingerprintActivity2.class));
                    finish();
                }
                break;

        }
    }
}
