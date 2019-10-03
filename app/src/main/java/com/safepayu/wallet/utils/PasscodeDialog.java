package com.safepayu.wallet.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.ForgotPasscode;

public class PasscodeDialog extends Dialog implements View.OnClickListener {
    private TextView one, two, three, four, five, six, seven, eight, nine, zero, txvEnter, error, forgot_passcode;
    private ImageView delete;
    private EditText edtxPassCode;
    private Activity activity;
    ImageView visible;
    private PasscodeClickListener clickListener;

    public PasscodeDialog(@NonNull Activity activity, PasscodeClickListener clickListener, String action) {
        super(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.activity = activity;
        this.clickListener = clickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.passcode_layout);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        visible = findViewById(R.id.visible);
        txvEnter = findViewById(R.id.txvEnter);
        error = findViewById(R.id.error);
        forgot_passcode = findViewById(R.id.forgot_passcode);
        edtxPassCode = findViewById(R.id.edtxPassCode);
        one = findViewById(R.id.one);
        one.setOnClickListener(this);
        two = findViewById(R.id.two);
        two.setOnClickListener(this);
        three = findViewById(R.id.three);
        three.setOnClickListener(this);
        four = findViewById(R.id.four);
        four.setOnClickListener(this);
        five = findViewById(R.id.five);
        five.setOnClickListener(this);
        six = findViewById(R.id.six);
        six.setOnClickListener(this);
        seven = findViewById(R.id.seven);
        seven.setOnClickListener(this);
        eight = findViewById(R.id.eight);
        eight.setOnClickListener(this);
        nine = findViewById(R.id.nine);
        nine.setOnClickListener(this);
        zero = findViewById(R.id.zero);
        zero.setOnClickListener(this);
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(this);


        txvEnter.setText("Enter the Passcode to Access\nYour SafePe Wallet");


        edtxPassCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE))) {
                    clickListener.onPasscodeMatch(true);
                    dismiss();
                } else if (s.length() == 4 && !s.toString().equals(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE))) {
                    error.setText("Invalid Passcode");
                    error.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(s)) {
                    error.setVisibility(View.GONE);
                }
            }
        });


        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible.getContentDescription().equals("INVISIBLE")) {
                    //MAKE VISIBLE
                    edtxPassCode.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visible.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp));
                    visible.setContentDescription("VISIBLE");
                    visible.invalidate();

                } else {
                    //INVISIBLE
                    edtxPassCode.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visible.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_visibility_black_24dp));
                    visible.setContentDescription("INVISIBLE");
                    visible.invalidate();
                }
            }
        });

        forgot_passcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, ForgotPasscode.class));
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clickListener.onPasscodeMatch(false);
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "1");
                }
                break;
            case R.id.two:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "2");
                }
                break;
            case R.id.three:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "3");
                }
                break;
            case R.id.four:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "4");
                }
                break;
            case R.id.five:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "5");
                }
                break;
            case R.id.six:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "6");
                }
                break;
            case R.id.seven:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "7");
                }
                break;
            case R.id.eight:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "8");
                }
                break;
            case R.id.nine:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "9");
                }
                break;
            case R.id.zero:
                if (edtxPassCode.getText().toString().trim().toCharArray().length < 4) {
                    edtxPassCode.setText(edtxPassCode.getText().toString() + "0");
                }
                break;

            case R.id.delete:
                if (!edtxPassCode.getText().toString().trim().equals("")) {
                    String str = edtxPassCode.getText().toString().trim();
                    int index = str.length() - 1;
                    if (index == 0) {
                        edtxPassCode.setText("");
                    } else {
                        str = str.substring(0, index);
                        edtxPassCode.setText(str);
                    }
                }
                break;
        }

    }
}
