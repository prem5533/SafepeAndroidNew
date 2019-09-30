package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class Commission extends BaseActivity {

    Button BackBtn;
    TextView SendWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        SendWallet=findViewById(R.id.send_to_wallet);
        //BackBtn=findViewById(R.id.water_back_btn);

//        BackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        SendWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TransferCommissionToWallet.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.commission;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
