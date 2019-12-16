package com.safepayu.wallet.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.DialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class FlightDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText editText;
    private TextView textView ,tvDepMonth ,tvWeekDay,tvDepmY;
    int dayOfWeek;

    public static FlightDatePicker newInstance(EditText editText, TextView textView,TextView tvDepMonthYear,TextView tvDepartureDay) //TextView tv_departure_mo_ye
     {
        FlightDatePicker fragment = new FlightDatePicker();
        fragment.editText = editText;
        fragment.textView = textView;
        fragment.tvDepMonth = tvDepMonthYear;
        fragment.tvWeekDay = tvDepartureDay;
      //  fragment.tvDepmY = tv_departure_mo_ye;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
      //  c.add(Calendar.YEAR, -18);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
         dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        return dialog;

    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        if (textView != null) {


            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
            String value = sharedPreferences.getString("date", "rDate");
            if (value.equals("dDate")) {
                String date = BaseApp.getInstance().dateUtil().parseStringDate((year + "-" + (month + 1) + "-" + day), BaseApp.getInstance().dateUtil().yyyy_MM_dd, BaseApp.getInstance().dateUtil().dd_MMM_yyyy);

                String[] separated = date.split("-");
                String d = separated[0];
                String m = separated[1];
                String y = separated[2];
                textView.setText(d);
                tvDepMonth.setText(m + " " + y);
               /* String date2 = BaseApp.getInstance().dateUtil().parseStringDate((year + "-" + (month + 1) + "-" + day), BaseApp.getInstance().dateUtil().yyyy_MM_dd, BaseApp.getInstance().dateUtil().dd_MM_yyyy);
                tvDepmY.setText(date2);*/

                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date1 = new Date(year, month, day - 1);
                String dayOfWeek = simpledateformat.format(date1);
                tvWeekDay.setText(dayOfWeek);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DATE,date);

            }
            else if(value.equals("rDate")) {
                String date = BaseApp.getInstance().dateUtil().parseStringDate((year + "-" + (month + 1) + "-" + (day)), BaseApp.getInstance().dateUtil().yyyy_MM_dd, BaseApp.getInstance().dateUtil().dd_MMM_yyyy);

                String[] separated = date.split("-");
                String d = separated[0];
                String m = separated[1];
                String y = separated[2];
                textView.setText(d);
                tvDepMonth.setText(m + " " + y);

                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date1 = new Date(year, month, day - 1);
                String dayOfWeek = simpledateformat.format(date1);
                tvWeekDay.setText(dayOfWeek);
            }
        }else if (editText != null) {
            editText.setText(BaseApp.getInstance().dateUtil().parseStringDate((year + "-" + (month + 1) + "-" + day), BaseApp.getInstance().dateUtil().yyyy_MM_dd, BaseApp.getInstance().dateUtil().dd_MM_yyyy));
        }
    }

}