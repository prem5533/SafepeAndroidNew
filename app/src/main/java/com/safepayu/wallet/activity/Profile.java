package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class Profile extends BaseActivity {

    Button BackBtn,UpdateAddressBtn;
    TextView ChangePassBtn;
    LinearLayout ChangePassLayout;
    int ChangePassVisibility=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        ChangePassBtn=findViewById(R.id.changePassBtn);
        BackBtn=findViewById(R.id.backbtn_from_profile);
        ChangePassLayout=findViewById(R.id.change_pass_layout);
        UpdateAddressBtn=findViewById(R.id.addressupdateBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        UpdateAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,AddUpdateAddress.class));
            }
        });

        ChangePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ChangePassVisibility==0){
                    ChangePassLayout.setVisibility(View.VISIBLE);
                    ChangePassVisibility=1;
                }else {
                    ChangePassLayout.setVisibility(View.GONE);
                    ChangePassVisibility=0;
                }

            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.profile;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}

