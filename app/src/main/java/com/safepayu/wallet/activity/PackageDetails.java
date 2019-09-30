package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class PackageDetails extends BaseActivity {

    Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        BackBtn=findViewById(R.id.backbtn_packagedetails);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.package_details;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
