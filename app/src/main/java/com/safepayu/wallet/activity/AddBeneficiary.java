package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class AddBeneficiary extends BaseActivity {

    Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        BackBtn=findViewById(R.id.send_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.add_beneficiary;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
