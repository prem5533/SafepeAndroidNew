package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import pl.droidsonroids.gif.GifImageView;

public class PaidOrderActivity extends AppCompatActivity {
    private TextView tvNeedHelp,StatusTV,DateTV,TxnIdTV,AmountTV,ProductInfoTV;
    private GifImageView gifImageView;
    private String status="",txnid="",Amount="",date="",productinfo="";
    private Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_order);

        Intent intentStatus=getIntent();
        try{
            status=intentStatus.getStringExtra("status");
            Amount=intentStatus.getStringExtra("Amount");
            date=intentStatus.getStringExtra("date");
            productinfo=intentStatus.getStringExtra("productinfo");
            txnid=intentStatus.getStringExtra("txnid");
        }catch (Exception e){
            e.printStackTrace();
        }
        findId();

        tvNeedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findId() {
        tvNeedHelp = findViewById(R.id.tv_needhelp);
        gifImageView = findViewById(R.id.statusImage);
        StatusTV = findViewById(R.id.statusText);
        DateTV = findViewById(R.id.tv_date_time);
        TxnIdTV = findViewById(R.id.txnId);
        AmountTV = findViewById(R.id.tv_paid_rs);
        ProductInfoTV = findViewById(R.id.productInfo);
        BackBtn=findViewById(R.id.status_back_btn);

        String PendingText="";

        try{
            if (productinfo.contains("wallet") || productinfo.contains("Wallet")){
                PendingText="Due To Server Down, It Might Take 5 Mins To 3 Hrs. Have Patience For Transaction. Sorry For The Inconvenience. \nThank You!";
            }else {
                PendingText="Due To Server Down, It Might Take 5 Mins To 3 Hrs For Recharge. Have Patience. Sorry For The Inconvenience. \nThank You!";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (status.equalsIgnoreCase("success")){
            gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.success));
            StatusTV.setTextColor(getResources().getColor(R.color.green_500));
        }else if (status.equalsIgnoreCase("pending")){
            gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.pending));
            StatusTV.setTextColor(getResources().getColor(R.color.clay_yellow));
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.statusorderLayout),PendingText,true);
        }else {
            gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.failed_gif));
            StatusTV.setTextColor(getResources().getColor(R.color.red_400));
        }

        StatusTV.setText(status);
        TxnIdTV.setText("Txn ID: "+txnid);
        AmountTV.setText(Amount);
        DateTV.setText(date);
        ProductInfoTV.setText(productinfo);
    }
}
