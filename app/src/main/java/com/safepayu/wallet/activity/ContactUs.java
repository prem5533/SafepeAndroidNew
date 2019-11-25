package com.safepayu.wallet.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import androidx.core.app.ActivityCompat;

import static com.safepayu.wallet.activity.Navigation.tollNumber;


public class ContactUs extends BaseActivity implements View.OnClickListener {

    EditText editEmail,editSubject,editMessage;
    TextView visit_website, sendmail, TollfreeNoTV,TollfreeNoText,tvWebsite;
    private Button submit, callButtonIndia, callButtonUsa;
    String str_mail,str_subject,str_message,id,trueStr="true";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        Button clear_btn = findViewById(R.id.recharge_back_btn);
        editEmail=findViewById(R.id.contactus_email);
        visit_website= findViewById(R.id.visit_website);
        editSubject=findViewById(R.id.contactus_subject);
        editMessage=findViewById(R.id.contactus_message);
        submit=findViewById(R.id.contactus_submit);
        callButtonIndia = findViewById(R.id.call_button_india);
        callButtonUsa = findViewById(R.id.call_button_usa);
        sendmail =  findViewById(R.id.sendmail);
        TollfreeNoTV=  findViewById(R.id.tollfreeNo);
        TollfreeNoText=  findViewById(R.id.tollfreeNoText);
        tvWebsite=  findViewById(R.id.website);

        id= BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
        tvWebsite.setOnClickListener(this);
        sendmail.setOnClickListener(this);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUs();
            }
        });

        try{
            if (TextUtils.isEmpty(tollNumber)){
                TollfreeNoTV.setVisibility(View.GONE);
                TollfreeNoText.setVisibility(View.GONE);
            }else {
                TollfreeNoTV.setText(tollNumber);
            }
        }catch (Exception e){
            TollfreeNoTV.setVisibility(View.GONE);
            TollfreeNoText.setVisibility(View.GONE);
            e.printStackTrace();
        }


        //-------------askr permission-------------------------
        ActivityCompat.requestPermissions(ContactUs.this,
                new String[]{Manifest.permission.CALL_PHONE}, 1);



        //-------------END askr permission-------------------------



        callButtonIndia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onClickWhatsApp("8860722217");
                return true;
            }
        });

        visit_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.safepeindia.com")));
            }
        });

    }

    public void onClickWhatsApp(String number) {

        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.contact_us;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                  //  Toast.makeText(ContactUs.this, "Permission denied to Call Phone", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    void contactUs(){
        str_mail=editEmail.getText().toString().trim();
        str_subject=editSubject.getText().toString().trim();
        str_message=editMessage.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(str_mail).matches()) {
            editEmail.setError("Enter a valid email");
            editEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(str_subject)) {
            editSubject.setError("Enter Subject");
            editSubject.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(str_message)) {
            editMessage.setError("Enter Message");
            editMessage.requestFocus();
            return;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.website:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.safepeindia.com/"));
                startActivity(intent);
                break;

            case R.id.sendmail:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"support@safepeindia.com"});

                try{
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }catch (Exception e){
                    Log.e("SendEmailERRO", e.toString());
                }
                break;
        }
    }
}