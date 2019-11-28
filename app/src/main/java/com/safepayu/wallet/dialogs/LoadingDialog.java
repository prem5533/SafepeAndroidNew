package com.safepayu.wallet.dialogs;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.custom_view.rotate_loading.RotateLoading;

public class LoadingDialog {
    Context context;
    Dialog dialog;
    private RotateLoading rotateLoading;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void showDialog(String message, Boolean cancelable) {

        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(cancelable);
        dialog.setContentView(R.layout.loading);
        rotateLoading = (RotateLoading) dialog.findViewById(R.id.rotateloading);
        if (message != null) {
            ((TextView) dialog.findViewById(R.id.tv_loadingMessage)).setText(message);
        } else {
            dialog.findViewById(R.id.tv_loadingMessage).setVisibility(View.GONE);
        }
        rotateLoading.start();
        dialog.show();
    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            rotateLoading.stop();
            dialog.dismiss();
        }
    }

}
