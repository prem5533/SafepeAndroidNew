package com.safepayu.wallet.activity.loan;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.R;

public class LoanActivity extends AppCompatActivity implements View.OnClickListener {

    public LinearLayout ll_back;
    public TextView tv_toolbar_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        ll_back = findViewById(R.id.ll_back);
        tv_toolbar_name = findViewById(R.id.tv_toolbar_name);
        tv_toolbar_name.setText("SafePe Loan");

        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
