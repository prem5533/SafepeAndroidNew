package com.safepayu.wallet.activity.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.PaidOrderActivity;
import com.safepayu.wallet.activity.PaymentType;

public class MobileRecharge extends BaseActivity {

    Button RechargeBtn,BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        RechargeBtn=findViewById(R.id.recharge_btn);
        BackBtn=findViewById(R.id.recharge_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* startActivity(new Intent(getApplicationContext(), PaymentType.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();*/
                startActivity(new Intent(getApplicationContext(), PaidOrderActivity.class));
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.mobile_recharge;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}

