package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class TransferCommissionToWallet  extends BaseActivity {

    Button SendToWalletBtn,BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        SendToWalletBtn=findViewById(R.id.sendToWalletBtn);
        BackBtn=findViewById(R.id.backBtn_transferCommission);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SendToWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PaymentType.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.transfer_commission_to_wallet;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
