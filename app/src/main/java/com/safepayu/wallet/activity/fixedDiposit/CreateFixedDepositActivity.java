package com.safepayu.wallet.activity.fixedDiposit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.deposit.DepositInterestRateAdapter;

public class CreateFixedDepositActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    public LinearLayout ll_interest_table, ll_back;
    public TextView tv_term_cond, tv_interest_rate, tv_available_balance;
    public Button btn_create_deposit;
    public EditText ed_amount;
    public RecyclerView rv_deposit_interest_table_rate;
    public RecyclerView.Adapter mDepositInterestTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fixed_deposit);

        ll_interest_table = findViewById(R.id.ll_interest_table);
        tv_term_cond = findViewById(R.id.tv_term_cond);
        tv_interest_rate = findViewById(R.id.tv_interest_rate);
        tv_available_balance = findViewById(R.id.tv_available_balance);
        btn_create_deposit = findViewById(R.id.btn_create_deposit);
        ed_amount = findViewById(R.id.ed_amount);
        ll_back = findViewById(R.id.ll_back);

        tv_term_cond.setOnClickListener(this);
        btn_create_deposit.setOnClickListener(this);
        ll_interest_table.setOnClickListener(this);
        ed_amount.addTextChangedListener(this);
        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_term_cond:
                callTermAndCondition();
                break;

            case R.id.ll_interest_table:
                callInterestTable();
                break;

            case R.id.ll_back:
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            ll_interest_table.setVisibility(View.VISIBLE);
        } else {
            ll_interest_table.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void callInterestTable() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_deposit_interest_table_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);

        LinearLayout ll_alertDismiss = dialogView.findViewById(R.id.ll_alertDismiss);
        rv_deposit_interest_table_rate = dialogView.findViewById(R.id.rv_deposit_interest_table_rate);
        rv_deposit_interest_table_rate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        mDepositInterestTable = new DepositInterestRateAdapter(this);
        rv_deposit_interest_table_rate.setAdapter(mDepositInterestTable);

        ll_alertDismiss.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }

    private void callTermAndCondition() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_term_cond_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);

        alertDialog.show();
    }
}
