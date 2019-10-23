package com.safepayu.wallet.activity.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import androidx.appcompat.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        findId();
    }

    private void findId() {
        backBtn = findViewById(R.id.recharge_back_btn);


        //set listener
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

        }

    }

    private void CheckValidate() {

        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_bus),"Coming Soon",false);
    }

}