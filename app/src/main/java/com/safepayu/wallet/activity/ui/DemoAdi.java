package com.safepayu.wallet.activity.ui;


import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.safepayu.wallet.R;

public class DemoAdi extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_adi);
        Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
    }
}