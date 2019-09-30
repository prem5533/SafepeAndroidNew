package com.safepayu.wallet.activity.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.PaymentType;

public class PostpaidLandlineBillpay extends BaseActivity {

    Button PayBtn,BackBtn,BillCheckBtn;
    TextView textView;
    EditText AmountEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        PayBtn=findViewById(R.id.Paybtn);
        BackBtn=findViewById(R.id.postpaid_back_btn);
        BillCheckBtn=findViewById(R.id.btnCheckBill);
        textView=findViewById(R.id.textView);
        AmountEd=findViewById(R.id.amount);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        PayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PaymentType.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                finish();
            }
        });

        BillCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.VISIBLE);
                AmountEd.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.postpaid_landline_billpay;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}


