package com.safepayu.wallet.activity.deposit_fixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.safepe_investment.AvailableBalanceActivity;
import com.safepayu.wallet.activity.safepe_investment.InvestmentDepositActivity;
import com.safepayu.wallet.activity.safepe_investment.InvestmentProfileActivity;

public class FixedDepositActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout liTotalAmountFD,liFixedDeposit,llProfileVisibility;
    private Button send_back_btn_fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit2);
        findId();
    }

    private void findId() {
        liTotalAmountFD = findViewById( R.id.ll_total_amount_FD);
        liFixedDeposit = findViewById( R.id.ll_fixed_deposit);
        llProfileVisibility = findViewById( R.id.ll_profile_visibility);
        send_back_btn_fd = findViewById( R.id.send_back_btn_fd);

        liTotalAmountFD.setOnClickListener(this);
        liFixedDeposit.setOnClickListener(this);
        llProfileVisibility.setOnClickListener(this);
        send_back_btn_fd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_total_amount_FD:
                startActivity(new Intent(FixedDepositActivity.this,FixedDepositListActivity.class));
                break;
            case R.id.ll_fixed_deposit:
                startActivity(new Intent(FixedDepositActivity.this, AvailableBalanceFDActivity.class)
//                        putExtra("depositAmount", depositAmount).
//                        putExtra("fdInterest", fdInterest).putExtra("balanceAmount", balanceAmount)
                );
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;

            case R.id.ll_profile_visibility:
                startActivity(new Intent(FixedDepositActivity.this, InvestmentProfileActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);

                break;
            case R.id.send_back_btn_fd:
                finish();
                break;

        }
    }
}
