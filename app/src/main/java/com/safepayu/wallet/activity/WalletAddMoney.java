package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class WalletAddMoney extends BaseActivity {

    Button AddMoneyBtn,BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        AddMoneyBtn=findViewById(R.id.btn_addMoneyType);
        BackBtn=findViewById(R.id.sendmoney_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddMoneyBtn.setOnClickListener(new View.OnClickListener() {
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
        return R.layout.wallet_add_money;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
