package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

public class MetroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ProceedBtn,BackBtn;
    private TextView tvMetroType;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);

        findId();
    }

    private void findId() {
        BackBtn = findViewById(R.id.recharge_back_btn);
        tvMetroType = findViewById(R.id.tv_metro_type);
        ProceedBtn = findViewById( R.id.proceed_btn);

        //Set Listenere
        BackBtn.setOnClickListener(this);
        tvMetroType.setOnClickListener(this);
        ProceedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

            case R.id.tv_metro_type:
                break;
            case R. id.proceed_btn:

                CheckValidate();
                break;
        }

    }

    private void CheckValidate() {

        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_metro),"Coming Soon",false);
    }
}
