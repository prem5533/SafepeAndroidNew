package com.safepayu.wallet.listener;

import android.view.View;

import com.safepayu.wallet.enums.ButtonActions;

public interface SnackBarActionClickListener {
    void onPositiveClick(View view, ButtonActions action);
}
