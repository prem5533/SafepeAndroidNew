package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import pl.droidsonroids.gif.GifImageView;

import static android.view.Gravity.CENTER_VERTICAL;

public class PaidOrderActivity extends AppCompatActivity {
    private TextView tvNeedHelp,StatusTV,DateTV,TxnIdTV,AmountTV,ProductInfoTV,ServiceInfoTV,tvSafepeUtrId;
    private GifImageView gifImageView;
    private String status="",txnid="",Amount="",date="",productinfo="",Message="",UTR="";
    private Button BackBtn;
    private Dialog dialog;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_order);

        Intent intentStatus=getIntent();
        try{
            status=intentStatus.getStringExtra("status");
            Amount=intentStatus.getStringExtra("Amount");
            date=intentStatus.getStringExtra("date");
            productinfo=intentStatus.getStringExtra("productinfo");
            txnid=intentStatus.getStringExtra("txnid");
            Message=intentStatus.getStringExtra("Message");
            UTR=intentStatus.getStringExtra("utr_id");
        }catch (Exception e){
            e.printStackTrace();
        }
        findId();

        tvNeedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PaidOrderActivity.this, ContactUs.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findId() {
        tvNeedHelp = findViewById(R.id.tv_needhelp);
        gifImageView = findViewById(R.id.statusImage);
        StatusTV = findViewById(R.id.statusText);
        DateTV = findViewById(R.id.tv_date_time);
        TxnIdTV = findViewById(R.id.txnId);
        AmountTV = findViewById(R.id.tv_paid_rs);
        ProductInfoTV = findViewById(R.id.productInfo);
        BackBtn=findViewById(R.id.status_back_btn);
        ServiceInfoTV=findViewById(R.id.serviceInfo);
        tvSafepeUtrId=findViewById(R.id.tv_safepe_utr_id);

        try {
            if (Message==null || TextUtils.isEmpty(Message)){
                Message="";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String PendingText=Message;

        try{
            if (productinfo.contains("wallet") || productinfo.contains("Wallet")){
               // PendingText="It Might Take 5 Mins To 3 Hrs. Have Patience For Transaction. Sorry For The Inconvenience. \nThank You!";
            }else {
             //   PendingText="It Might Take 5 Mins To 3 Hrs For Recharge. Have Patience. Sorry For The Inconvenience. \nThank You!";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if (productinfo.contains("Package") || productinfo.contains("package")){
                if (status.equalsIgnoreCase("success")){
                    showPkgDialog();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if (status.equalsIgnoreCase("success")){
           // gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.success));
            Glide.with(getApplicationContext()).load(R.drawable.success).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(gifImageView);
            StatusTV.setTextColor(getResources().getColor(R.color.green_500));
//            if(PendingText.equalsIgnoreCase("It Might Take 5 Mins To 3 Hrs. Have Patience For Transaction. Sorry For The Inconvenience. \nThank You!")){
//                ServiceInfoTV.setVisibility(View.GONE);
//            } else {
//                ServiceInfoTV.setText(PendingText);
//            }
            if (UTR==null  || TextUtils.isEmpty(UTR)){
                tvSafepeUtrId.setVisibility(View.GONE);
            }else {
                tvSafepeUtrId.setText("UTR ID: "+UTR);
            }

            ServiceInfoTV.setText(PendingText);

        } else if (status.equalsIgnoreCase("pending")){
            //gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.pending));

            Glide.with(getApplicationContext()).load(R.drawable.pending2).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(gifImageView);
            ServiceInfoTV.setText(PendingText);
            if (UTR==null || TextUtils.isEmpty(UTR)){
                tvSafepeUtrId.setVisibility(View.GONE);
            }else {
                tvSafepeUtrId.setText("Bank Ref. No. : "+UTR);
            }

            StatusTV.setTextColor(getResources().getColor(R.color.clay_yellow));
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.statusorderLayout),PendingText,true);
        } else {
       //     gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.failed_gif));
            Glide.with(getApplicationContext()).load(R.drawable.failed_gif).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(gifImageView);
            StatusTV.setTextColor(getResources().getColor(R.color.red_400));
//            if(PendingText.equals("It Might Take 5 Mins To 3 Hrs. Have Patience For Transaction. Sorry For The Inconvenience. \nThank You!")){
//                ServiceInfoTV.setVisibility(View.GONE);
//            } else {
//                ServiceInfoTV.setText(PendingText);
//            }
            tvSafepeUtrId.setVisibility(View.GONE);

            ServiceInfoTV.setText(PendingText);
        }

        StatusTV.setText(status);
        AmountTV.setText(getResources().getString(R.string.rupees)+" "+Amount);
        DateTV.setText(date);
        ProductInfoTV.setText(productinfo);

        if (TextUtils.isEmpty(txnid)){
            TxnIdTV.setVisibility(View.GONE);
        }else {
            TxnIdTV.setText("S_P (Txn ID): "+txnid);
        }

        if (productinfo.equalsIgnoreCase("Wallet To Bank Transaction")){

            if (status.equalsIgnoreCase("success")){
                Message="\nYour Credit Status Confirmation Will Be Available In 2 Working Days.\nPlease Ignore If Credit Is Already Received." +
                        "\nThank You For Using SafePe App.";
                showDialogAfterBankTrans(this,Message);
            }else {
                //showDialogAfterBankTrans(this,Message);
            }

        }
    }

    public void showDialogAfterBankTrans(Activity activity,String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Set the alert dialog title
        builder.setTitle("SafePe Alert");
        // Set a message for alert dialog
        builder.setMessage(Message);
        // Must call show() prior to fetching text view

        builder  .setIcon(getResources().getDrawable(R.drawable.new_safepe_logo));
        // Set a positive button for alert dialog
      //  builder.setPositiveButton("Say",null);
        // Specifying a listener allows you to take an action before dismissing the dialog.
        // The dialog is automatically dismissed when a dialog button is clicked.
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Continue with delete operation
                dialog.dismiss();
            }
        });

        // Set a negative button for alert dialog
     //   builder.setNegativeButton("No",null);

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        // Finally, display the alert dialog
        dialog.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER|CENTER_VERTICAL);
        messageText.setPadding(40, 120, 40, 40);
        messageText.setTextSize(16);
        dialog.setCanceledOnTouchOutside(false);
        // Get screen width and height in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void showPkgDialog() {

        dialog = new Dialog(PaidOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pkg_detail_dialog);

        Button btn_back_home_page = dialog.findViewById(R.id.btn_back_home_page);
        btn_back_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();

    }

}

