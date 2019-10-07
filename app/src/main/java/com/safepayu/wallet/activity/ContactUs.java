package com.safepayu.wallet.activity;

import android.Manifest;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;


public class ContactUs extends BaseActivity {

    EditText editEmail,editSubject,editMessage;
    TextView visit_website, sendmail;
    private Button submit, callButtonIndia, callButtonUsa;
    String str_mail,str_subject,str_message,id,trueStr="true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        Button clear_btn=(Button) findViewById(R.id.recharge_back_btn);
        editEmail=(EditText)findViewById(R.id.contactus_email);
        visit_website=(TextView) findViewById(R.id.visit_website);
        editSubject=(EditText)findViewById(R.id.contactus_subject);
        editMessage=(EditText)findViewById(R.id.contactus_message);
        submit=(Button)findViewById(R.id.contactus_submit);
        callButtonIndia = (Button) findViewById(R.id.call_button_india);
        callButtonUsa = (Button) findViewById(R.id.call_button_usa);
        sendmail = (TextView) findViewById(R.id.sendmail);

        id= BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
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


        //-------------askr permission-------------------------
        ActivityCompat.requestPermissions(ContactUs.this,
                new String[]{Manifest.permission.CALL_PHONE}, 1);



        //-------------END askr permission-------------------------

        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"support@safepayu.com"});


                try{
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }catch (Exception e){
                    Log.e("SendEmailERRO", e.toString());
                }
//                Uri uri = Uri.parse("mailto:support@safepayu.com");
//                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
//                startActivity(Intent.createChooser(i, "Send mail"));

            }
        });

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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.safepayu.com/")));
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
                    Toast.makeText(ContactUs.this, "Permission denied to Call Phone", Toast.LENGTH_SHORT).show();
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
}