package com.safepayu.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChangePasscode extends BaseActivity {

    Button BackBtn;
    TextView CreatePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        CreatePasswordBtn=findViewById(R.id.create_password);
        BackBtn=findViewById(R.id.backBtn_changePasscode);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.change_passcode;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
