package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

public class BusActivity extends AppCompatActivity implements View.OnClickListener {

    private Button searchBusBtn,backBtn;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        findId();
    }

    private void findId() {
        backBtn = findViewById(R.id.recharge_back_btn);
        searchBusBtn = findViewById( R.id.search_bus_btn);

        //set listener
        backBtn.setOnClickListener(this);
        searchBusBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.search_bus_btn:
                CheckValidate();
                break;
        }

    }

    private void CheckValidate() {

        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_bus),"Coming Soon",false);
    }

}
