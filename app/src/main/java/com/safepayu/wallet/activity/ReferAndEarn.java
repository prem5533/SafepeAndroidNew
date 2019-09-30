package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class ReferAndEarn extends BaseActivity {

    Button BackBtn,inviteFriends_btn;
    TextView referralCode;
    String strReferalcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);


        BackBtn=findViewById(R.id.ref_back_btn);

        inviteFriends_btn=findViewById(R.id.referralbtn);
        referralCode= findViewById(R.id.tv_referralcode);

        strReferalcode="SafePe_UserTest";
        referralCode.setText(strReferalcode);
        inviteFriends_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"SafePay UPI Payments, Recharges & Money Transfer");
                String shareMessage="Checkout this amazing app for Recharges, Bill payments and UPI transfer. Use this code "+strReferalcode+" to signup and get rewards. Share this app to make money. Just download SafePe from https://play.google.com/store/apps/details?id=" + getPackageName() +"";
                intent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                startActivity(Intent.createChooser(intent,"Sharing via"));
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.refer_and_earn;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
