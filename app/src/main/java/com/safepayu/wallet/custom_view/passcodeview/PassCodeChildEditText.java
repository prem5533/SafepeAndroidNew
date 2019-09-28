package com.safepayu.wallet.custom_view.passcodeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import com.safepayu.wallet.R;

@SuppressLint("AppCompatCustomView")
public class PassCodeChildEditText extends EditText {

    public PassCodeChildEditText(Context context) {
        super(context);
        init(context);
    }

    public PassCodeChildEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PassCodeChildEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setCursorVisible(false);
        setTextColor(context.getResources().getColor(R.color.transparent));
        setBackgroundDrawable(null);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setSelectAllOnFocus(false);
        setTextIsSelectable(false);
        setFocusable(false);
    }

    @Override
    public void onSelectionChanged(int start, int end) {

        CharSequence text = getText();
        if (text != null && start != text.length() || end != text.length()) {
            setSelection(text.length(), text.length());
            return;
        }

        super.onSelectionChanged(start, end);
    }

}