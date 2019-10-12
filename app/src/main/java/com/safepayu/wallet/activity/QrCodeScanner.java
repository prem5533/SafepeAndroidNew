package com.safepayu.wallet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.BaseResponse1;
import com.safepayu.wallet.models.response.UpiUserDetailsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private LoadingDialog loadingDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "onCreate");

        loadingDialog=new LoadingDialog(this);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {

            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(QrCodeScanner.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String result1 = result.getText();
        getUserDetails(result1);
    }

    private void getUserDetails(String userId) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getUserDetailUPI(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpiUserDetailsResponse>() {
                    @Override
                    public void onSuccess(UpiUserDetailsResponse response) {
                        loadingDialog.hideDialog();

                        if (response.isStatus()){
                            Intent intent=new Intent(QrCodeScanner.this,SendMoneyToWallet.class);
                            intent.putExtra("Mobile",response.getUser().getMobile());
                            startActivity(intent);
                            finish();
                        }else {
                            String message="";

                            try{
                                BaseResponse1.DataBean dataBean=response.getData();
                                if (dataBean!=null){

                                    if (dataBean.getEmail().size()==1){
                                        message=dataBean.getEmail().get(0)+"\n";
                                    }else if (dataBean.getEmail().size()>1){
                                        message=dataBean.getEmail().get(0)+"\n"+dataBean.getEmail().get(1)+"\n";
                                    }

                                    if (dataBean.getMobile().size()==1){
                                        message=message+dataBean.getMobile().get(0);
                                    }else if (dataBean.getEmail().size()>1){
                                        message=message+dataBean.getMobile().get(0)+"\n"+dataBean.getMobile().get(1)+"\n";
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(message)) {
                                Toast.makeText(QrCodeScanner.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(QrCodeScanner.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        finish();

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Toast.makeText(QrCodeScanner.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }));
    }
}
