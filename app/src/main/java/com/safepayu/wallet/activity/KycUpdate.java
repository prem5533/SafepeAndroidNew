package com.safepayu.wallet.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDex;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.DatePickerHidePreviousDate;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.UserImageCamera;
import com.safepayu.wallet.models.request.KycRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.CountryListResponse;
import com.safepayu.wallet.models.response.ResponseModel;
import com.safepayu.wallet.models.response.StateListResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class KycUpdate extends BaseActivity implements View.OnClickListener {

    private LoadingDialog loadingDialog;
    private Button BackBtn, RegisterBtn;
    private EditText edName, edMobile, edEmail, edAddress, edPincode, edPan, edAdhar, edShopName;
    private TextView tvDob, txtPercentage;
    private Spinner StateSpinner;
    private LinearLayout KycStatusLayout, KycRegisterLayout, PanImageLayout, AadhaarImageLayout, AadhaarImageBackLayout, SelfImageLayout;
    private ArrayList<String> StateIdList, StateNameList, StateCodeList;
    private ImageView ivSelf, ivPAN, ivAadhaar, ivAadhaarBack;
    private static final int PICK_IMAGE_CAMERA = 0;
    private static final int PICK_IMAGE_PAN = 1;
    private static final int PICK_IMAGE_AADHAAR = 2;
    private static final int PICK_IMAGE_AADHAAR_BACK = 3;
    private static final int PICK_IMAGE_CAMERA_2 = 4;
    private Bitmap bitmap;
    private Uri uriAdharFrontFile,uriAdharBackFile,uriPanFile,uriUserFile;
    private long totalSize = 0;
    private ProgressBar progressBar;
    private File destination = null;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private String textBase64Self = "", textBase64Pan = "", textBase64Aadhaar = "", textBase64AadhaarBack = "", StateName = "", StateId = "", StateCode = "";

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        findId();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.kyc_update;
    }

    private void findId() {

        loadingDialog = new LoadingDialog(this);
        StateIdList = new ArrayList<>();
        StateNameList = new ArrayList<>();
        StateCodeList = new ArrayList<>();

        BackBtn = findViewById(R.id.backBtn_kycLayout);
        RegisterBtn = findViewById(R.id.register_kycLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtPercentage = (TextView) findViewById(R.id.progressBarText);
        edName = findViewById(R.id.name_kycLayout);
        edMobile = findViewById(R.id.mobile_kycLayout);
        edEmail = findViewById(R.id.email_kycLayout);
        edAddress = findViewById(R.id.address_kycLayout);
        edPincode = findViewById(R.id.pinCode_kycLayout);
        edPan = findViewById(R.id.pan_kycLayout);
        edAdhar = findViewById(R.id.adhar_kycLayout);
        edShopName = findViewById(R.id.shopName_kycLayout);
        tvDob = findViewById(R.id.dob_kycLayout);
        StateSpinner = findViewById(R.id.stateList_kycLayout);

        ivSelf = findViewById(R.id.imageSelf_kycLayout);
        ivPAN = findViewById(R.id.imagePan_kycLayout);
        ivAadhaar = findViewById(R.id.imageAdhar_kycLayout);
        ivAadhaarBack = findViewById(R.id.imageAdharBack_kycLayout);

        KycStatusLayout = findViewById(R.id.statusKycLayout);
        KycRegisterLayout = findViewById(R.id.kycRegisterLayout);
        PanImageLayout = findViewById(R.id.imageLayoutPan);
        AadhaarImageLayout = findViewById(R.id.imageLayoutAdhar);
        AadhaarImageBackLayout = findViewById(R.id.imageLayoutAdharBack);
        SelfImageLayout = findViewById(R.id.imageLayoutSelf);

        BackBtn.setOnClickListener(this);
        RegisterBtn.setOnClickListener(this);
        PanImageLayout.setOnClickListener(this);
        AadhaarImageLayout.setOnClickListener(this);
        SelfImageLayout.setOnClickListener(this);
        AadhaarImageBackLayout.setOnClickListener(this);
        tvDob.setOnClickListener(this);

        if (isNetworkAvailable()) {
            getCheckKyc();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout), "Check Your Internet Connection", false);
        }

        StateNameList.add("Please Select Your State");
        StateIdList.add("0");
        StateCodeList.add("0");

        StateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos != 0) {
                    StateName = StateNameList.get(pos);
                    StateId = StateIdList.get(pos);
                    StateCode = StateCodeList.get(pos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backBtn_kycLayout:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;

            case R.id.register_kycLayout:

                if (TextUtils.isEmpty(textBase64Aadhaar)){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout)," Please Click Your Aadhaar Front Image",false);
                }else {
                    if (TextUtils.isEmpty(textBase64Pan)){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout)," Please Click  Your PAN Image",false);
                    }else {
                        if (TextUtils.isEmpty(textBase64Self)){
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout)," Please Click  Your Image",false);
                        }else {
                            if (TextUtils.isEmpty(textBase64AadhaarBack)){
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout)," Please Click Aadhaar Back Image",false);
                            }else {
                                KycRequest kycRequest=new KycRequest();
                                kycRequest.setAdharCard_img("data:image/png;base64,"+textBase64Aadhaar);
                                kycRequest.setAdharCardBack_img("data:image/png;base64,"+textBase64AadhaarBack);
                                kycRequest.setPanCard_img("data:image/png;base64,"+textBase64Pan);
                                kycRequest.setUser_img("data:image/png;base64,"+textBase64Self);
                                kycRequest.setMobile(edMobile.getText().toString().trim());
                                kycRequest.setCustomerName(edName.getText().toString().trim());
                                kycRequest.setShopName(edShopName.getText().toString().trim());
                                kycRequest.setAddress(edAddress.getText().toString().trim());
                                kycRequest.setPincode(edPincode.getText().toString().trim());
                                kycRequest.setStateCode(StateCode);
                                kycRequest.setEmail(edEmail.getText().toString().trim());
                                kycRequest.setPan(edPan.getText().toString().trim());
                                kycRequest.setDob(tvDob.getText().toString().trim());
                                kycRequest.setAadhaar(edAdhar.getText().toString().trim());

                                //getKycDone(kycRequest);
                                uploadImageToServer();
                            }
                        }
                    }
                }


                break;

            case R.id.imageLayoutPan:
                if (ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectImage(PICK_IMAGE_PAN);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                break;

            case R.id.imageLayoutAdhar:
                if (ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectImage(PICK_IMAGE_AADHAAR);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                break;

            case R.id.imageLayoutAdharBack:
                if (ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectImage(PICK_IMAGE_AADHAAR_BACK);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                break;

            case R.id.imageLayoutSelf:
                if (ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(KycUpdate.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectImage(PICK_IMAGE_CAMERA);
                } else {
                    ActivityCompat.requestPermissions(KycUpdate.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                break;

            case R.id.dob_kycLayout:
                DatePickerHidePreviousDate datePicker = DatePickerHidePreviousDate.newInstance(null, tvDob);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;
        }

    }

    private void selectImage(int imageType) {
        try {
            PackageManager pm = KycUpdate.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, KycUpdate.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                try {
                    if (imageType == PICK_IMAGE_CAMERA) {
                        int apiLevel=Integer.valueOf(android.os.Build.VERSION.SDK);
                        if (apiLevel>21){
                            Intent intent = new Intent(KycUpdate.this, UserImageCamera.class);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA_2);
                        }else {
                            CallCameraIntent(imageType);
                        }

                    }else {
                        CallCameraIntent(imageType);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    CallCameraIntent(imageType);
                }
            } else {
                Log.v("er1", "Camera Permission error");
                Toast.makeText(KycUpdate.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.v("er2", "Camera Permission error");
            Toast.makeText(KycUpdate.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void CallCameraIntent(int imageType){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (imageType == PICK_IMAGE_CAMERA) {
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        }

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        File output = new File(dir, "IMG_" + System.currentTimeMillis() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        uriAdharFrontFile = Uri.fromFile(output);
        // start the image capture Intent
        startActivityForResult(intent, imageType);
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SafepeWallet");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("error", "Oops! Failed create " + "SafepeWallet" + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    private String ConvertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_CAMERA_2) {
            try {
                String file =data.getStringExtra("usr_img");
                uriUserFile= Uri.parse(file);
                ivSelf.setImageURI(uriUserFile);
                textBase64Self="done";
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {

                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(uriAdharFrontFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                try {
                    stream.close();
                    stream = null;
                    long lengthbmp = byteArray.length;
                    int i = (int) (long) lengthbmp;
                    Log.v("size", (i / 1024) + " kb");
                } catch (IOException e) {

                    e.printStackTrace();
                }

                Bitmap converetdImage = getResizedBitmap(bmp, 600);
//                ByteArrayOutputStream streamC = new ByteArrayOutputStream();
//                converetdImage.compress(Bitmap.CompressFormat.JPEG, 100, streamC);
//                byte[] byteArraCy = streamC.toByteArray();
//                long lengthbmp = byteArraCy.length;
//                int i = (int) (long) lengthbmp;
//                Log.v("size2", (i / 1024) + " kb");

                try {

                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

                    File output = new File(dir, "Safepe_" + System.currentTimeMillis() + ".jpg");

                    //File pictureFile = getOutputMediaFile();
                    Log.d("PAth", output.getPath());
                    if (output == null) {
                        Log.d("TAG", "Error creating media file, check storage permissions: ");// e.getMessage());
                        return;
                    }
                    try {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Log.v("TAG", "Permission is granted");
                            //File write logic here
                            FileOutputStream fos = new FileOutputStream(output);
                            converetdImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();


                            if (requestCode == PICK_IMAGE_CAMERA) {
                                uriUserFile = Uri.fromFile(output);
                            } else if (requestCode == PICK_IMAGE_PAN) {
                                uriPanFile = Uri.fromFile(output);
                            } else if (requestCode == PICK_IMAGE_AADHAAR) {
                                uriAdharFrontFile = Uri.fromFile(output);
                            } else if (requestCode == PICK_IMAGE_AADHAAR_BACK) {
                                uriAdharBackFile = Uri.fromFile(output);
                            }
                        } else {
                            Log.v("TAG", "Permission is Revoked");
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }

                    } catch (FileNotFoundException e) {
                        Log.d("TAG", "File not found: " + e.getMessage());
                    } catch (IOException e) {
                        Log.d("TAG", "Error accessing file: " + e.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (requestCode == PICK_IMAGE_CAMERA) {
                    ivSelf.setImageBitmap(converetdImage);
                    textBase64Self = "done";
                } else if (requestCode == PICK_IMAGE_PAN) {
                    ivPAN.setImageBitmap(converetdImage);
                    textBase64Pan = "done";
                } else if (requestCode == PICK_IMAGE_AADHAAR) {
                    ivAadhaar.setImageBitmap(converetdImage);
                    textBase64Aadhaar = "done";
                } else if (requestCode == PICK_IMAGE_AADHAAR_BACK) {
                    ivAadhaarBack.setImageBitmap(converetdImage);
                    textBase64AadhaarBack = "done";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "Safepe_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (!isConnected) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout), "Check Your Internet Connection", false);
        }
    }

    private void getCountryList() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getCountryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CountryListResponse>() {
                    @Override
                    public void onSuccess(CountryListResponse countryListResponse) {
                        loadingDialog.hideDialog();
                        if (countryListResponse.isStatus()) {

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout), countryListResponse.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.kycLayout), false, e.getCause());
                    }
                }));

    }

    private void getStateList() {

        //loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getStateList("101")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<StateListResponse>() {
                    @Override
                    public void onSuccess(StateListResponse stateListResponse) {
                        //loadingDialog.hideDialog();
                        if (stateListResponse.isStatus()) {

                            if (stateListResponse.getData().size() > 0) {
                                for (int k = 0; k < stateListResponse.getData().size(); k++) {
                                    StateIdList.add(String.valueOf(stateListResponse.getData().get(k).getState_id()));
                                    StateNameList.add(stateListResponse.getData().get(k).getName());
                                    StateCodeList.add(String.valueOf(stateListResponse.getData().get(k).getStateCode()));
                                }
                                ArrayAdapter<String> stateList = new ArrayAdapter<>(KycUpdate.this, android.R.layout.simple_spinner_item, StateNameList);
                                stateList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                StateSpinner.setAdapter(stateList);
                            }

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout), stateListResponse.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.kycLayout), false, e.getCause());
                    }
                }));

    }

    private void getKycDone(KycRequest kycRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getKYCDone(kycRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout), response.getMessage(), true);

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.kycLayout), false, e.getCause());
                    }
                }));

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
                        showMessage(response.getMessage(), response.getStatus());
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.kycLayout), false, e.getCause());
                    }
                }));
    }

    public void showMessage(String Message, final boolean check) {
        new AlertDialog.Builder(KycUpdate.this)
                .setTitle("SafePe - KYC Update")
                .setMessage(Message)
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        if (check) {
                            dialog.dismiss();
                            finish();
                        } else {
                            dialog.dismiss();
                        }
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }

    public void uploadImageToServer() {


        File file1 = new File(uriAdharFrontFile.getPath());
        File file2 = new File(uriAdharBackFile.getPath());
        File file3 = new File(uriPanFile.getPath());
        File file4 = new File(uriUserFile.getPath());

        Log.v("1path",file1.getPath());
        Log.v("2path",file2.getPath());
        Log.v("3path",file3.getPath());
        Log.v("4path",file4.getPath());

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        RequestBody requestBodyId = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part body = MultipartBody.Part.createFormData("adharCard_img", file1.getName(), requestBodyId);

        RequestBody requestBodyId2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("adharCardBack_img", file2.getName(), requestBodyId2);

        RequestBody requestBodyId3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
        MultipartBody.Part body3 = MultipartBody.Part.createFormData("panCard_img", file3.getName(), requestBodyId3);

        RequestBody requestBodyId4 = RequestBody.create(MediaType.parse("multipart/form-data"), file4);
        MultipartBody.Part body4 = MultipartBody.Part.createFormData("User_img", file4.getName(), requestBodyId4);

        BaseApp.getInstance().getDisposable().add(apiService.registerKyc(body, body2, body3, body4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponseModel>() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        loadingDialog.hideDialog();
                        showMessage(response.message,response.status);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(KycUpdate.this.findViewById(R.id.kycLayout), false, e.getCause());
                    }
                }));
    }
}
