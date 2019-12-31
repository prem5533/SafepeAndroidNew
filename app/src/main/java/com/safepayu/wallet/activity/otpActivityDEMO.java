package com.safepayu.wallet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;

public class otpActivityDEMO extends Activity implements TextWatcher {
    EditText et1 ,et2,et3,et4,et5,et6;
    private View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_demo);

        findID();



    }

    private void findID() {
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);

        et1.addTextChangedListener(this);
        et2.addTextChangedListener(this);
        et3.addTextChangedListener(this);
        et4.addTextChangedListener(this);
        et5.addTextChangedListener(this);
        et6.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        if(text.length()==1){
            if (et1.length()==1){
                et2.requestFocus();
            }
            if(et2.length()==1){
                et3.requestFocus(); }

            if(et3.length()==1){
                et4.requestFocus(); }

            if(et4.length()==1){
                et5.requestFocus(); }

            if(et5.length()==1){
                et6.requestFocus(); } }

        else if (text.length()==0){
            if(et6.length()==0){
                et5.requestFocus(); }
            if(et5.length()==0){
                et4.requestFocus(); }
            if(et4.length()==0){
                et3.requestFocus(); }
            if(et3.length()==0){
                et2.requestFocus(); }
            if(et2.length()==0){
                et1.requestFocus(); } }

       /* else if(text.length()==0)
            et1.requestFocus();

        if(text.length()==1)
            et4.requestFocus();
        else if(text.length()==0)
            et2.requestFocus();

        if(text.length()==0)
            et3.requestFocus();

        if(text.length()==0)
            et3.requestFocus();

        if(text.length()==0)
            et3.requestFocus();*/

    }
}
