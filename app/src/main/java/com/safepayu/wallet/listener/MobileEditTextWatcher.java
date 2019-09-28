package com.safepayu.wallet.listener;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

public class MobileEditTextWatcher implements TextWatcher {
    EditText editText;

    public MobileEditTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text_value = editText.getText().toString().trim();
        /*if (text_value.equalsIgnoreCase("+91")) {
            editText.setText("+91");
        } else {
            if (!text_value.startsWith("+91") && text_value.length() > 0) {
                editText.setText("+91 " + charSequence.toString());
                Selection.setSelection(editText.getText(), editText.getText().length());
            }
        }*/
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!editable.toString().startsWith("+91 ")){
            editText.setText("+91 ");
            Selection.setSelection(editText.getText(), editText.getText().length());

        }
    }
}
