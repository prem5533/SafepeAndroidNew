package com.safepayu.wallet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;

public class CommonUtils {
    private Context context;
    public String phoneNumberRegex = "^\\+[ 0-9]{10,14}$";
    public final int REQUEST_RED_PONE_STATE = 2;

    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private DecimalFormat decimalFormatWithoutDot = new DecimalFormat("##");

    public CommonUtils(Context context) {
        this.context = context;
    }

    public TelephonyManager getTelephonyManager() {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager;
    }

    public String hideCharacter(String text, String replaceText, int replaceTextCount) {
        String _text = "";
        for (int i = 0; i < text.trim().length(); i++) {
            if (i < replaceTextCount) {
                _text += replaceText;
            } else {
                _text += text.charAt(i);
            }
        }
        return _text;
    }

    public double calculateTax(Double price, Double per) {
        return ((price * per) / 100);
    }
    public double getAmountWithTax(Double price, Double per) {
        return (price+((price * per) / 100));
    }

    public boolean isContainsDecimals(double value) {
        int intpart = (int) value;
        Double decpart = value - intpart;
        if (decpart == 0.0f) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isContainsDecimals(float value) {
        int intPart = (int) value;
        float decPart = value - intPart;
        if (decPart == 0.0f) {
            return false;
        } else {
            return true;
        }
    }

    public String decimalFormat(Double aDouble) {
        return decimalFormat.format(aDouble);
    }

    public String decimalFormat(Float aFloat) {
        return decimalFormat.format(aFloat);
    }

    public String decimalFormatWithoutDot(Double aDouble) {
        return decimalFormatWithoutDot.format(aDouble);
    }

    public String decimalFormatWithoutDot(Float aFloat) {
        return decimalFormatWithoutDot.format(aFloat);
    }

    private void dialContactPhone(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    /*for checking the validation of the email format  entered by the user*/
    public boolean isValidEmail(String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public int displayWidth(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }

    public boolean isCheckSpecialCharacter(String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public String getImagePath(Uri uri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = activity.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return path;
        } else {
            return uri.getPath();
        }
    }

    public String formatPlayingDuration(int seconds) {
        // int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        int displayedSeconds = seconds % 60;
        if (minutes == 0)
            return "00:" + addZero(displayedSeconds);
        return addZero(minutes) + ":" + addZero(displayedSeconds);

    }

    private String addZero(int number) {
        if (number < 10)
            return "0" + number;
        return "" + number;
    }

    public void sendMail(Context context) {
        Intent mailer = new Intent(Intent.ACTION_SEND);
        mailer.setType("text/plain");
        mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello@quikcatalog.com"});
        mailer.putExtra(Intent.EXTRA_SUBJECT, "");
        mailer.putExtra(Intent.EXTRA_TEXT, "");
        mailer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(mailer, "Send email..."));
    }

    public void shareApp(Context context, String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((AppCompatActivity) context).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
