package com.safepayu.wallet.utils;

import com.safepayu.wallet.BaseApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public SimpleDateFormat TIME_STAMP_FORMAT1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public SimpleDateFormat dd_MM_yyyy = new SimpleDateFormat("dd-MM-yyyy");
    public SimpleDateFormat dd_MMM_yyyy = new SimpleDateFormat("dd-MMM-yyyy");
    public  SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public  SimpleDateFormat dd_MM_yyyy_HH_MM_SS = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public  SimpleDateFormat _dd_MM_yyyy = new SimpleDateFormat("dd-MM-yyyy");
    public  SimpleDateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public SimpleDateFormat timeHRFormat = new SimpleDateFormat("HH");
    public  SimpleDateFormat monthFullNameFormat = new SimpleDateFormat("MMMM");


    public String parseDateToString(Date input, SimpleDateFormat outFormat) {
        return outFormat.format(input);
    }

    public String parseStringDate(String input, SimpleDateFormat mFormatFrom, SimpleDateFormat mFormatTo) {
        String strDate = null;

        if (null == input) {
            return input;
        }
        try {
            Date date = mFormatFrom.parse(input);
            strDate = mFormatTo.format(date);

        } catch (ParseException e) {
            BaseApp.getInstance().toastHelper().log(DateUtil.class, e.getMessage());
        }
        return strDate;
    }



}
