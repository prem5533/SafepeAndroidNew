package com.safepayu.wallet.activity.fixedDiposit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.deposit.DepositRateAdapter;

public class AvailableBalanceActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btn_create_deposit;
    public LinearLayout ll_back;
    public TextView tv_interest_rate;
    public RecyclerView rv_interest_table_rate;
    public RecyclerView.Adapter mInterestTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_balance);

        ll_back = findViewById(R.id.ll_back);
        btn_create_deposit = findViewById(R.id.btn_create_deposit);
        tv_interest_rate = findViewById(R.id.tv_interest_rate);


        ll_back.setOnClickListener(this);
        tv_interest_rate.setOnClickListener(this);
        btn_create_deposit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_interest_rate:
                callInterestTable();
                break;

            case R.id.ll_back:
                finish();
                break;

            case R.id.btn_create_deposit:
                startActivity(new Intent(AvailableBalanceActivity.this, CreateFixedDepositActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
        }
    }

    private void callInterestTable() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_interest_table_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);

        LinearLayout ll_alertDismiss = dialogView.findViewById(R.id.ll_alertDismiss);

        rv_interest_table_rate = dialogView.findViewById(R.id.rv_interest_table_rate);
        rv_interest_table_rate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mInterestTable = new DepositRateAdapter(this);
        rv_interest_table_rate.setAdapter(mInterestTable);

        ll_alertDismiss.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }
}
