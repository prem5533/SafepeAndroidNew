package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PaymentType extends BaseActivity {

    RelativeLayout cardBtnLayout, upiBtnLayout, NetBankingBtnLayout, WalletBtnLayout, radioLayout4;
    LinearLayout cardLayout, UpiLayout;
    private Button btn_proceed_netBanking, proceed_upi, proceed_wallet, proceed_card,BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        cardBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout1);
        upiBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout3);
        NetBankingBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout2);
        WalletBtnLayout = (RelativeLayout) findViewById(R.id.radioLayout4);

        cardLayout = (LinearLayout) findViewById(R.id.card_layout);
        UpiLayout = (LinearLayout) findViewById(R.id.upi_layout);

        proceed_card = (Button) findViewById(R.id.btn_addMoney_card);
        btn_proceed_netBanking = (Button) findViewById(R.id.btn_proceed_netBanking);
        proceed_upi = (Button) findViewById(R.id.btn_pay_upi);
        proceed_wallet = (Button) findViewById(R.id.btn_proceed_wallet);
        BackBtn=findViewById(R.id.sendmoney_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(VISIBLE);
                UpiLayout.setVisibility(GONE);
                proceed_wallet.setVisibility(GONE);
                btn_proceed_netBanking.setVisibility(GONE);
            }
        });

        NetBankingBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(GONE);
                UpiLayout.setVisibility(GONE);
                proceed_wallet.setVisibility(GONE);
                btn_proceed_netBanking.setVisibility(VISIBLE);
            }
        });

        upiBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(GONE);
                UpiLayout.setVisibility(VISIBLE);
                proceed_wallet.setVisibility(GONE);
                btn_proceed_netBanking.setVisibility(GONE);
            }
        });

        WalletBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardLayout.setVisibility(GONE);
                UpiLayout.setVisibility(GONE);
                proceed_wallet.setVisibility(VISIBLE);
                btn_proceed_netBanking.setVisibility(GONE);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.payment_type;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
