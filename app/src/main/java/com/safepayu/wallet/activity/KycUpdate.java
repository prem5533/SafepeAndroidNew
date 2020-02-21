package com.safepayu.wallet.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.safepayu.wallet.models.request.KycRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.CountryListResponse;
import com.safepayu.wallet.models.response.StateListResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class KycUpdate extends BaseActivity implements View.OnClickListener{

    private LoadingDialog loadingDialog;
    private Button BackBtn,RegisterBtn;
    private EditText edName,edMobile,edEmail,edAddress,edPincode,edPan,edAdhar,edShopName;
    private TextView tvDob;
    private Spinner StateSpinner;
    private LinearLayout KycStatusLayout,KycRegisterLayout,PanImageLayout,AadhaarImageLayout,AadhaarImageBackLayout,SelfImageLayout;
    private ArrayList<String> StateIdList,StateNameList,StateCodeList;
    private ImageView ivSelf,ivPAN,ivAadhaar,ivAadhaarBack;
    private static final int PICK_IMAGE_CAMERA = 0;
    private static final int PICK_IMAGE_PAN = 1;
    private static final int PICK_IMAGE_AADHAAR = 2;
    private static final int PICK_IMAGE_AADHAAR_BACK = 3;
    private Bitmap bitmap;
    private File destination = null;
    private String textBase64Self="",textBase64Pan="",textBase64Aadhaar="",textBase64AadhaarBack="",StateName="",StateId="",StateCode="";

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        findId();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.kyc_update;
    }

    private void findId() {

        loadingDialog = new LoadingDialog(this);
        StateIdList=new ArrayList<>();
        StateNameList=new ArrayList<>();
        StateCodeList=new ArrayList<>();

        BackBtn = findViewById(R.id.backBtn_kycLayout);
        RegisterBtn = findViewById(R.id.register_kycLayout);

        edName = findViewById(R.id.name_kycLayout);
        edMobile = findViewById(R.id.mobile_kycLayout);
        edEmail = findViewById(R.id.email_kycLayout);
        edAddress = findViewById(R.id.address_kycLayout);
        edPincode = findViewById(R.id.pinCode_kycLayout);
        edPan = findViewById(R.id.pan_kycLayout);
        edAdhar = findViewById(R.id.adhar_kycLayout);
        edShopName = findViewById(R.id.shopName_kycLayout);
        tvDob = findViewById(R.id.dob_kycLayout);
        StateSpinner=findViewById(R.id.stateList_kycLayout);

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

        if (isNetworkAvailable()){
            //getCountryList();
            //getStateList();
            getCheckKyc();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout),"Check Your Internet Connection",false);
        }

        StateNameList.add("Please Select Your State");
        StateIdList.add("0");
        StateCodeList.add("0");

        StateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos!=0){
                    StateName=StateNameList.get(pos);
                    StateId=StateIdList.get(pos);
                    StateCode=StateCodeList.get(pos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backBtn_kycLayout:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
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

                                getKycDone(kycRequest);
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

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (imageType==PICK_IMAGE_CAMERA){
                    intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                }
                startActivityForResult(intent, imageType);

            } else{
                Toast.makeText(KycUpdate.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(KycUpdate.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String ConvertToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

            destination = new File(Environment.getExternalStorageDirectory() + "/" +
                    getString(R.string.app_name), "IMG_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Drawable d = new BitmapDrawable(getResources(), bitmap);

            if (requestCode == PICK_IMAGE_CAMERA) {
                ivSelf.setImageDrawable(d);
                textBase64Self = ConvertToBase64(bitmap);
            }else if (requestCode == PICK_IMAGE_PAN) {
                ivPAN.setImageDrawable(d);
                textBase64Pan = ConvertToBase64(bitmap);
            }else if (requestCode == PICK_IMAGE_AADHAAR) {
                ivAadhaar.setImageDrawable(d);
                textBase64Aadhaar = ConvertToBase64(bitmap);
            }else if (requestCode == PICK_IMAGE_AADHAAR_BACK) {
                ivAadhaarBack.setImageDrawable(d);
                textBase64AadhaarBack = ConvertToBase64(bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (!isConnected){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout),"Check Your Internet Connection",false);
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
                        if (countryListResponse.isStatus()){

                        }else{
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout),countryListResponse.getMessage(),true);
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
                        if (stateListResponse.isStatus()){

                            if (stateListResponse.getData().size()>0){
                                for (int k=0;k<stateListResponse.getData().size();k++){
                                    StateIdList.add(String.valueOf(stateListResponse.getData().get(k).getState_id()));
                                    StateNameList.add(stateListResponse.getData().get(k).getName());
                                    StateCodeList.add(String.valueOf(stateListResponse.getData().get(k).getStateCode()));
                                }
                                ArrayAdapter<String> stateList= new ArrayAdapter<>(KycUpdate.this,android.R.layout.simple_spinner_item,StateNameList);
                                stateList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                StateSpinner.setAdapter(stateList);
                            }

                        }else{
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout),stateListResponse.getMessage(),true);
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
                        if (response.getStatus()){
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout),response.getMessage(),true);

                        }else{
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.kycLayout),response.getMessage(),true);
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
                        showMessage(response.getMessage(),response.getStatus());
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.kycLayout), false, e.getCause());
                    }
                }));
    }

    public void showMessage(String Message,final boolean check) {
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

                        if (check){
                            dialog.dismiss();
                            finish();
                        }else {
                            dialog.dismiss();
                        }

                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }


    /**
     * Uploading the file to server
     * */
    /*
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("api/pefast.safepe.latepe/api/registerKyc");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart("website",
                        new StringBody("www.androidhive.info"));
                entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {

            showMessage(result,false);
            super.onPostExecute(result);
        }

    } */

}
