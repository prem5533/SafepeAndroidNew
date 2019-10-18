package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity implements PasscodeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        BaseApp.getInstance().handler().postDelayed(runnable,2000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN) != null) {
                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
                    startActivity(new Intent(Splash.this,CreatePassCodeActivity.class));
                } else {
                    PasscodeDialog passcodeDialog = new PasscodeDialog(Splash.this, Splash.this, "");
                    passcodeDialog.show();
                }
            } else {
                startActivity(new Intent(Splash.this, LoginActivity.class));
                finish();
            }
        }
    };

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched){
            startActivity(new Intent(Splash.this, Navigation.class));
            finish();
        }else {
            Toast.makeText(this, "Invalid Passcode", Toast.LENGTH_SHORT).show();
            PasscodeDialog passcodeDialog = new PasscodeDialog(Splash.this, Splash.this, "");
            passcodeDialog.show();
        }

    }
}
