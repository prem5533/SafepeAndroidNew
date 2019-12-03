package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import java.text.NumberFormat;

import pl.droidsonroids.gif.GifImageView;

import static android.view.Gravity.CENTER_VERTICAL;

public class PaidOrderActivity extends AppCompatActivity {
    private TextView tvNeedHelp,StatusTV,DateTV,TxnIdTV,AmountTV,ProductInfoTV,ServiceInfoTV;
    private GifImageView gifImageView;
    private String status="",txnid="",Amount="",date="",productinfo="",Message="";
    private Button BackBtn;

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

        String PendingText=Message;

        try{
            if (productinfo.contains("wallet") || productinfo.contains("Wallet")){
                PendingText="Due To Server Down, It Might Take 5 Mins To 3 Hrs. Have Patience For Transaction. Sorry For The Inconvenience. \nThank You!";
            }else {
                PendingText="Due To Server Down, It Might Take 5 Mins To 3 Hrs For Recharge. Have Patience. Sorry For The Inconvenience. \nThank You!";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (status.equalsIgnoreCase("success")){
           // gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.success));
            Glide.with(getApplicationContext()).load(R.drawable.success).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(gifImageView);
            StatusTV.setTextColor(getResources().getColor(R.color.green_500));
            ServiceInfoTV.setText(PendingText);
        } else if (status.equalsIgnoreCase("pending")){
            //gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.pending));

            Glide.with(getApplicationContext()).load(R.drawable.pending2).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(gifImageView);
            ServiceInfoTV.setText(PendingText);
            StatusTV.setTextColor(getResources().getColor(R.color.clay_yellow));
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.statusorderLayout),PendingText,true);
        } else {
       //     gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.failed_gif));
            Glide.with(getApplicationContext()).load(R.drawable.failed_gif).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(gifImageView);
            StatusTV.setTextColor(getResources().getColor(R.color.red_400));
            ServiceInfoTV.setText(PendingText);
        }

        StatusTV.setText(status);
        AmountTV.setText(getResources().getString(R.string.rupees)+" "+ NumberFormat.getIntegerInstance().format(Integer.parseInt(Amount)));
        DateTV.setText(date);
        ProductInfoTV.setText(productinfo);

        if (TextUtils.isEmpty(txnid)){
            TxnIdTV.setVisibility(View.GONE);
        }else {
            TxnIdTV.setText("Txn ID: "+txnid);
        }

        if (productinfo.equalsIgnoreCase("Wallet To Bank Transaction")){

            if (status.equalsIgnoreCase("success")){
                Message="\nYour Credit Status Confirmation Will Be Available In 2 Working Days.\nPlease Ignore If Credit Is Already Received." +
                        "\nThank You For Using SafePe App.";
                showDialogAfterBankTrans(this,Message);
            }else {
                showDialogAfterBankTrans(this,Message);
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


    }

