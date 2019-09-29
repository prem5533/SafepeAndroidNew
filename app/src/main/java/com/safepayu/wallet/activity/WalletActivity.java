package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class WalletActivity extends BaseActivity {

    TextView AddMoneyToWallet,SendMoney;
    Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        AddMoneyToWallet=findViewById(R.id.tv_addMoneyToSafepe);
        SendMoney=findViewById(R.id.send_txt);
        BackBtn=findViewById(R.id.sendmoney_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SendMoney.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();
            }
        });

        AddMoneyToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WalletAddMoney.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
