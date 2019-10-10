package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
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
import com.safepayu.wallet.models.response.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ReferAndEarn extends BaseActivity implements View.OnClickListener {

    Button BackBtn,inviteFriends_btn;
    TextView referralCode;
    String strReferalcode;
    private ImageView imageCopy;
    LinearLayout WhatsappLayout;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog=new LoadingDialog(this);
        BackBtn=findViewById(R.id.ref_back_btn);
        imageCopy = (ImageView) findViewById(R.id.image_copy);

        inviteFriends_btn=findViewById(R.id.referralbtn);
        referralCode= findViewById(R.id.tv_referralcode);
        WhatsappLayout=findViewById(R.id.li_whtasapp);

        strReferalcode= BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE);
        referralCode.setText(strReferalcode);

        //set listener ******************
        imageCopy.setOnClickListener(this);
        WhatsappLayout.setOnClickListener(this);
        inviteFriends_btn.setOnClickListener(this);
        BackBtn.setOnClickListener(this);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.refer_and_earn;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void sendToWhatsapp(){
        PackageManager pm=getPackageManager();
        try {
            String shareMessage="Checkout this amazing app for Recharges, Bill payments and UPI transfer. Use this code "+strReferalcode+" to signup and get rewards. Share this app to make money. Just download SafePe from https://play.google.com/store/apps/details?id=com.safepayu.wallet&hl=en ";
            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            waIntent.putExtra(Intent.EXTRA_SUBJECT,"SafePay UPI Payments, Recharges & Money Transfer");
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(waIntent, "Share via"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendReferral(String MOBILE) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.sendRefer(MOBILE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.referAndEarnLayout), "App Download Link Sent To Referred Person With Referral Code", true);
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.referAndEarnLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.referAndEarnLayout), true, e);
                    }
                }));
    }

    public void showDialogMobile(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.refer_and_earn_dialog);

        final EditText mobileED=dialog.findViewById(R.id.mobile_referEarn);

        Button proceedButton = (Button) dialog.findViewById(R.id.proceedBtn_referEarn);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mobileED.getText().toString().trim())){
                    Toast.makeText(activity, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }else {
                    if (mobileED.getText().toString().trim().length()==10){
                        sendReferral(mobileED.getText().toString().trim());
                        dialog.dismiss();
                    }else {
                        Toast.makeText(activity, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        Button cancelBtn_appUpdate = (Button) dialog.findViewById(R.id.cancelBtn_referEarn);
        cancelBtn_appUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_whtasapp:
                sendToWhatsapp();
                break;
            case R.id.referralbtn:
                showDialogMobile(ReferAndEarn.this);
                break;
            case R.id.ref_back_btn:
                finish();
                break;
            case R.id.image_copy:
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text label", strReferalcode);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
