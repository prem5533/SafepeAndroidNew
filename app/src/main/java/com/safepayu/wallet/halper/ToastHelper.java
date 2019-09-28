package com.safepayu.wallet.halper;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.safepayu.wallet.R;
import com.safepayu.wallet.enums.ButtonActions;
import com.safepayu.wallet.listener.SnackBarActionClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ToastHelper {

    public String getTag(Class<?> mClass) {
        return mClass.getName();
    }

    public void showApiExpectation(View view, Boolean showLong, Throwable e) {
        String message = "";
        try {
            if (e instanceof IOException) {
                message = "No internet connection!";
            } else if (e instanceof HttpException) {
                HttpException error = (HttpException) e;
                String errorBody = error.response().errorBody().string();
                JSONObject jObj = new JSONObject(errorBody);

                message = jObj.getString("error");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            message = e.getMessage();
        } catch (JSONException e1) {
            e1.printStackTrace();
            message = e.getMessage();
        } catch (Exception e1) {
            e1.printStackTrace();
            message = e.getMessage();
        }

        if (TextUtils.isEmpty(message)) {
            message = "Unknown error occurred! Check LogCat.";
        }

        showSnackBar(view, message, showLong);
    }

    /**
     * for showing toast messages
     */
    public void showToast(Context context, String msg, Boolean showLong) {
        Toast.makeText(context, msg, showLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * for showing Log
     */
    public void log(Class<?> mClass, String msg) {
        Log.e(mClass.getName(), msg);
    }

    /**
     * for showing the messages in the bottom
     */
    public void showSnackBar(View view, String message, Boolean showLong) {
        try {
//            Snackbar snackbar = Snackbar.make(view, message, showLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
//            snackbar.show();

            Snackbar snackbar = Snackbar.make(view, message, showLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);

            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * for showing the messages in the bottom
     */
    public void showSnackBar(View view, String message, Boolean showLong, String buttonPositiveText, final ButtonActions action, final SnackBarActionClickListener clickListener) {
        try {
            Snackbar.make(view, message,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(buttonPositiveText, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (clickListener != null) {
                                clickListener.onPositiveClick(view, action);
                            }
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
