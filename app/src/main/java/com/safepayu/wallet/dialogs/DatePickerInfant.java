package com.safepayu.wallet.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.safepayu.wallet.BaseApp;

import java.util.Calendar;
import java.util.Date;

public class DatePickerInfant extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText editText;
    private TextView textView;

    public static DatePickerInfant newInstance(EditText editText, TextView textView) {
        DatePickerInfant fragment = new DatePickerInfant();
        fragment.editText = editText;
        fragment.textView = textView;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        //c.add(Calendar.YEAR, -2);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Date today2 = new Date();
        c.setTime(today2);
        c.add( Calendar.YEAR, -2) ;// Subtract 6 months
        long minDate = c.getTime().getTime(); // Twice!

        Date today = new Date();
        c.setTime(today);
        long maxDate = c.getTime().getTime(); // Twice!

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
      //  dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        dialog.getDatePicker().setMinDate(minDate);
        dialog.getDatePicker().setMaxDate(maxDate);
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