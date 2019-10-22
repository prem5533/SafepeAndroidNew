package com.safepayu.wallet.activity.recharge;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

public class InsuranceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button BackBtn;
    private LinearLayout liBuyInsurance, liMyInsurance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        findId();
    }

    private void findId() {
        BackBtn = findViewById(R.id.insurance_back_btn);
        liBuyInsurance = findViewById(R.id.layout_buy_insurance);
        liMyInsurance = findViewById(R.id.layout_my_insurance);

        //Set Listenere
        BackBtn.setOnClickListener(this);
        liBuyInsurance.setOnClickListener(this);
        liMyInsurance.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.donation_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

            case R.id.layout_buy_insurance:
                CheckValidate();
                break;
            case R.id.layout_my_insurance:
                CheckValidate();
                break;
        }

    }

    private void CheckValidate() {

        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_insurance),"Coming Soon",false);
    }
}



