package com.safepayu.wallet.activity.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.PaymentType;

public class WaterBillPay extends BaseActivity {

    Button WaterPaybtn,BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        WaterPaybtn=findViewById(R.id.waterPaybtn);
        BackBtn=findViewById(R.id.water_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WaterPaybtn.setOnClickListener(new View.OnClickListener() {
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
        return R.layout.water_bill_pay;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
