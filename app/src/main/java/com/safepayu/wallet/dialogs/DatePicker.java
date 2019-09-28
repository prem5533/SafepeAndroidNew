package com.safepayu.wallet.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.utils.DateUtil;

import java.util.Calendar;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText editText;
    private TextView textView;

    public static DatePicker newInstance(EditText editText, TextView textView) {
        DatePicker fragment = new DatePicker();
        fragment.editText = editText;
        fragment.textView = textView;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        return dialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        if (textView != null) {
            textView.setText(BaseApp.getInstance().dateUtil().parseStringDate((year + "-" + (month + 1) + "-" + day), BaseApp.getInstance().dateUtil().yyyy_MM_dd, BaseApp.getInstance().dateUtil().dd_MM_yyyy));
            ;
        } else if (editText != null) {
            editText.setText(BaseApp.getInstance().dateUtil().parseStringDate((year + "-" + (month + 1) + "-" + day), BaseApp.getInstance().dateUtil().yyyy_MM_dd, BaseApp.getInstance().dateUtil().dd_MM_yyyy));
        }
    }

}