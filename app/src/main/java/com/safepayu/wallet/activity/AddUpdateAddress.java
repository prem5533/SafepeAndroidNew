package com.safepayu.wallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;

public class AddUpdateAddress extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(false, null, false);

        findViewById(R.id.add_address).setOnClickListener(this);
        findViewById(R.id.back_btn_address).setOnClickListener(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_update_address2;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_address:
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

                break;
            case R.id.back_btn_address:
                finish();
                break;
        }
    }
}
