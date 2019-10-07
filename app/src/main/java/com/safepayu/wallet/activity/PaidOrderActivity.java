package com.safepayu.wallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.safepayu.wallet.R;

public class PaidOrderActivity extends AppCompatActivity {
    private TextView tvNeedHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_order);

        findId();
    }

    private void findId() {
        tvNeedHelp = (TextView)findViewById(R.id.tv_needhelp);
        tvNeedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
