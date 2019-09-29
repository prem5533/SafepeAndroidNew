package com.safepayu.wallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        BaseApp.getInstance().handler().postDelayed(runnable,1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN) != null) {
                startActivity(new Intent(Splash.this, Navigation.class));
            } else {
                startActivity(new Intent(Splash.this, LoginActivity.class));
            }
            finish();
        }
    };
}
