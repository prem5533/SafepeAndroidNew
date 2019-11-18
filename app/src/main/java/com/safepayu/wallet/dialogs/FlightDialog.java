package com.safepayu.wallet.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

public class FlightDialog extends Dialog implements View.OnClickListener {
    public FlightDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
