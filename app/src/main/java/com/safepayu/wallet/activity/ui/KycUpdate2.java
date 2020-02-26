package com.safepayu.wallet.activity.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.Config;
import com.safepayu.wallet.helper.UserImageCamera;
import com.safepayu.wallet.models.response.BaseResponse;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.helper.Config.IMAGE_PATH_USER;

public class KycUpdate2 extends AppCompatActivity implements View.OnClickListener {

    private LoadingDialog loadingDialog;
    public Button BackBtn, RegisterBtn;
    public LinearLayout KycStatusLayout, KycRegisterLayout, PanImageLayout, AadhaarImageLayout, AadhaarImageBackLayout, SelfImageLayout;
    public ImageView ivSelf, ivPAN, ivAadhaar, ivAadhaarBack;
    private static final int AADHAR_FRONT = 0;
    private static final int AADHAR_BACK = 1;
    private static final int PAN = 2;
    private static final int USER_IMAGE = 3;
    private static final int USER_IMAGE_2API = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_update2);

        loadingDialog = new LoadingDialog(this);

        BackBtn = findViewById(R.id.backBtn_kycLayout);
        RegisterBtn = findViewById(R.id.register_kycLayout);
        KycStatusLayout = findViewById(R.id.statusKycLayout);
        KycRegisterLayout = findViewById(R.id.kycRegisterLayout);
        PanImageLayout = findViewById(R.id.imageLayoutPan);
        AadhaarImageLayout = findViewById(R.id.imageLayoutAdhar);
        AadhaarImageBackLayout = findViewById(R.id.imageLayoutAdharBack);
        SelfImageLayout = findViewById(R.id.imageLayoutSelf);

        ivSelf = findViewById(R.id.imageSelf_kycLayout);
        ivPAN = findViewById(R.id.imagePan_kycLayout);
        ivAadhaar = findViewById(R.id.imageAdhar_kycLayout);
        ivAadhaarBack = findViewById(R.id.imageAdharBack_kycLayout);

        if (isNetworkAvailable()) {
            getCheckKyc();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout), "Check Your Internet Connection", false);
        }

        BackBtn.setOnClickListener(this);
        RegisterBtn.setOnClickListener(this);
        PanImageLayout.setOnClickListener(this);
        AadhaarImageLayout.setOnClickListener(this);
        SelfImageLayout.setOnClickListener(this);
        AadhaarImageBackLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backBtn_kycLayout:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;

            case R.id.register_kycLayout:
                //  uploadImageToServer();
                break;

            case R.id.imageLayoutAdhar:
                if (checkCameraPermission()) {
                    selectImage(AADHAR_FRONT);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate2.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                break;
            case R.id.imageLayoutAdharBack:
                if (checkCameraPermission()) {
                    selectImage(AADHAR_BACK);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate2.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                break;
            case R.id.imageLayoutPan:
                if (checkCameraPermission()) {
                    selectImage(PAN);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate2.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }

                break;
            case R.id.imageLayoutSelf:
                if (checkCameraPermission()) {
                    selectImage(USER_IMAGE);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate2.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                break;

            case R.id.dob_kycLayout:

                break;
        }
    }

    public boolean checkCameraPermission() {
        return (ContextCompat.checkSelfPermission(KycUpdate2.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(KycUpdate2.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void selectImage(int imageType) {
        try {
            PackageManager pm = KycUpdate2.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, KycUpdate2.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                try {

                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    if (imageType == AADHAR_FRONT) {
                        startActivityForResult(intent, AADHAR_FRONT);
                    } else if (imageType == AADHAR_BACK) {
                        startActivityForResult(intent, AADHAR_BACK);
                    } else if (imageType == PAN) {
                        startActivityForResult(intent, PAN);
                    } else if (imageType == USER_IMAGE) {
                        if (Build.VERSION.SDK_INT > 21) {
                            Intent intent2 = new Intent(KycUpdate2.this, UserImageCamera.class);
                            startActivityForResult(intent2, USER_IMAGE);
                        } else {
                            startActivityForResult(intent, USER_IMAGE);
                        }
                    } else {
                        Log.v("er1", "Camera Permission error");
                        Toast.makeText(KycUpdate2.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");
            File extStore = new File(Environment.getExternalStorageDirectory() + File.separator + "SafePe" + File.separator + "Image");
            if (!extStore.exists()) {
                extStore.mkdirs();
            }
            String docFileName = "safepe" + "_" + System.currentTimeMillis();
            String path = extStore.getAbsolutePath() + "/" + docFileName + ".png";
            Log.d("Image", "Save to: " + path);
            try {
                File myFile = new File(path);
                myFile.createNewFile();
                FileOutputStream out = new FileOutputStream(myFile);

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (requestCode == AADHAR_FRONT) {
                ivAadhaar.setImageBitmap(bitmap);
                Config.IMAGE_PATH_AADAHAR_FRONT = path;

            } else if (requestCode == AADHAR_BACK) {
                ivAadhaarBack.setImageBitmap(bitmap);
                Config.IMAGE_PATH_AADAHAR_BACK = path;

            } else if (requestCode == PAN) {
                ivPAN.setImageBitmap(bitmap);
                Config.IMAGE_PATH_PAN = path;

            } else if (requestCode == USER_IMAGE) {
                //      Config.IMAGE_PATH_USER = path;
                File imgFile = new File(IMAGE_PATH_USER);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivSelf.setImageBitmap(myBitmap);
                }
            }
        }else {

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getCheckKyc() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getKycCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        // showMessage(response.getMessage(), response.getStatus());
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.kycLayout), false, e.getCause());
                    }
                }));
    }

    public void showMessage(String Message, final boolean check) {
        new AlertDialog.Builder(KycUpdate2.this)
                .setTitle("SafePe - KYC Update")
                .setMessage(Message)
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Continue with delete operation

                    if (check) {
                        dialog.dismiss();
                        finish();
                    } else {
                        dialog.dismiss();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }
}
