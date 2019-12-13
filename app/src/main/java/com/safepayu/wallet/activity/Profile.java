package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ChangePasswordRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.UserDetailResponse;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.LoginActivity.isValidEmail;
import static com.safepayu.wallet.activity.Navigation.qrCodeImage;

public class Profile extends BaseActivity implements View.OnClickListener {

    Button BackBtn,UpdateAddressBtn,btnChangePassSubmit;
    TextView tvPhoneNumber, tvEmil,tvDOB, tvAddress,tvPincode,tvUsername1,tvSponserId,tvSponserName,tvSponserContactNumber;
    LinearLayout ChangePassLayout, ShowMyQRcodeLayout,ChangePassBtn;
    int ChangePassVisibility=0;
    UserDetailResponse uResponse;
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private LoadingDialog loadingDialog;
    public final static int QRcodeWidth = 500 ;
    private ImageView im_cross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);
        loadingDialog = new LoadingDialog(this);

        //getProfileData
        getProfileData();

        ShowMyQRcodeLayout=findViewById(R.id.showMyQRcode);
        ChangePassBtn=findViewById(R.id.change_pass_button);
        BackBtn=findViewById(R.id.backbtn_from_profile);
        ChangePassLayout=findViewById(R.id.change_pass_layout);
        UpdateAddressBtn=findViewById(R.id.addressupdateBtn);
        tvPhoneNumber = findViewById(R.id.p_mobile);
        tvEmil = findViewById(R.id.p_mail);
        tvDOB = findViewById(R.id.p_dob);
        tvPincode = findViewById(R.id.p_pincode);
        tvAddress = findViewById(R.id.p_address);
        tvUsername1 = findViewById(R.id.username1);
        etOldPassword = findViewById(R.id.old_password_id);
        etNewPassword = findViewById(R.id.new_password_id);
        etConfirmPassword = findViewById(R.id.confirm_password_id);
        btnChangePassSubmit = findViewById(R.id.change_pass_submit);
        tvSponserId = findViewById(R.id.p_sponser_id);
        tvSponserName = findViewById(R.id.p_sponser_name);
        tvSponserContactNumber = findViewById(R.id.p_sponser_contact_number);

        //set Listener
        BackBtn.setOnClickListener(this);
        UpdateAddressBtn.setOnClickListener(this);
        ChangePassBtn.setOnClickListener(this);
        btnChangePassSubmit.setOnClickListener(this);

      /*  Geocoder geocoder = new Geocoder(Profile.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(23.322, 43.23232, 1);
            String address = addresses.get(0).getAddressLine(0);

            //Log.d("mylog", "Complete Address: " + addresses.toString());
            //Log.d("mylog", "Address: " + address);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ShowMyQRcodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForQRcode(Profile.this,qrCodeImage);

            }
        });

        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equals("0")){
            showDialogForEmail(this);
        }
    }


    private void getProfileData() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getUserDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserDetailResponse>() {
                    @Override
                    public void onSuccess(UserDetailResponse userResponse) {
//                        BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().USER, new Gson().toJson(userResponse.getUser()));
                        if (userResponse.isStatus()){
                            uResponse = userResponse;
                            tvUsername1.setText(userResponse.getUser().getFirst_name()+" "+ userResponse.getUser().getLast_name());
                            tvPhoneNumber.setText(userResponse.getUser().getMobile());
                            tvEmil.setText(userResponse.getUser().getEmail());
                            tvDOB.setText(userResponse.getUser().getDob());
                            tvAddress.setText(userResponse.getUser().getLocation()+" "+ userResponse.getUser().getCity()+" "+userResponse.getUser().getState()+" "+userResponse.getUser().getCountry());
                            tvPincode.setText(String.valueOf(userResponse.getUser().getPin()));
                            tvSponserId.setText(userResponse.getUser().getReferral_recieved());
                            tvSponserContactNumber.setText(userResponse.getUser().getReferral_recieved());
                            tvSponserName.setText(userResponse.getUser().getSponsorname());
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.profileLayout),userResponse.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.profileLayout),e.getMessage(),false);
                    }
                }));

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.profile;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.backbtn_from_profile:
                finish();
                break;
            case R.id.addressupdateBtn:

                Intent i = new Intent(Profile.this, AddUpdateAddress.class);
                i.putExtra("location",  uResponse.getUser().getLocation());
                i.putExtra("city",  uResponse.getUser().getCity());
                i.putExtra("state",  uResponse.getUser().getState());
                i.putExtra("country",  uResponse.getUser().getCountry());
                i.putExtra("pincode",  ""+uResponse.getUser().getPin());
                startActivity(i);
                finish();
                break;
            case R.id.change_pass_button:
                Intent intentChPassword=new Intent(Profile.this, ChangePassword.class);
                startActivity(intentChPassword);

//                if (ChangePassVisibility==0){
//                    ChangePassLayout.setVisibility(View.VISIBLE);
//                    ChangePassVisibility=1;
//
//                }else {
//                    ChangePassLayout.setVisibility(View.GONE);
//                    ChangePassVisibility=0;
//                }
                break;
            case R.id.change_pass_submit:

                BaseApp.getInstance().commonUtils().hideKeyboard(this);
                if (validate()) {
                    changePassword();
                }

                break;
        }
    }

    private boolean validate() {
        if (etOldPassword.getText().toString().trim().length() == 0) {
            etOldPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etOldPassword, "Please enter old password", true);
            return false;
        } else if (etNewPassword.getText().toString().trim().length() == 0) {
            etNewPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etNewPassword, "Please enter new password", true);
            return false;
        }
        else if (etConfirmPassword.getText().toString().trim().length() == 0) {
            etConfirmPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etConfirmPassword, "Please enter confirm password", true);
            return false;
        } else if (!etConfirmPassword.getText().toString().trim().equals(etNewPassword.getText().toString().trim())) {
            etConfirmPassword.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etConfirmPassword,"Password do not match",true);
            return false;
        }
        return true;
    }
    private void changePassword() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        ChangePasswordRequest changePassword = new ChangePasswordRequest(etOldPassword.getText().toString(),etNewPassword.getText().toString(),etConfirmPassword.getText().toString());
        BaseApp.getInstance().getDisposable().add(apiService.changePwd(changePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()){
                            Toast.makeText(getApplicationContext(),response.getSuccess(),Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.profileLayout),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.profileLayout),e.getMessage(),false);

                    }
                }));
    }

    public void showDialogForQRcode(Activity activity,Bitmap bitmap) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.qr_code_dialog);
        dialog.getWindow().setLayout(370, 800);
        dialog.getWindow().setGravity(Gravity.CENTER| Gravity.CENTER);

        ImageView QRcodeImageView=dialog.findViewById(R.id.imageViewQRcode);
        QRcodeImageView.setImageBitmap(bitmap);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;



        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    public void showDialogForEmail(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.verify_email_dialog1);

        im_cross = dialog.findViewById(R.id.im_cross);

        final EditText emailEd=dialog.findViewById(R.id.enter_EmailLogin);

        im_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button proceedButton = (Button) dialog.findViewById(R.id.continue_EmailLogin);
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

    private void sendVerifyLink(String Email, final Dialog dialog) {
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
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
                            Toast.makeText(Profile.this, response.getMessage() + "\n" + "Please Verify From Your Email Account", Toast.LENGTH_LONG).show();
                            finish();
                        } else {

                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.profileLayout), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.profileLayout), true, e);
                    }
                }));
    }

}

