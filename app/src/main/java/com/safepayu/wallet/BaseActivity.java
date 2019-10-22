package com.safepayu.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.multidex.MultiDex;

import com.safepayu.wallet.utils.CheckInternet;

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mToolbar;
    private CheckInternet checkInternet;
    private ConnectivityReceiver connectivityReceiver;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        checkInternet = new CheckInternet(this);
        connectivityReceiver = new ConnectivityReceiver();

        mToolbar = findViewById(R.id.toolbar);


        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void setToolbar(Boolean showTitle, String title,Boolean showBackArrow) {
        if (mToolbar==null){
            return;
        }
        setSupportActionBar(mToolbar);
        if (title != null) {
            mToolbar.setTitle(title);
            getSupportActionBar().setTitle(title);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showBackArrow);


    }

    protected abstract int getLayoutResourceId();


    protected abstract void connectivityStatusChanged(Boolean isConnected, String message);

    public class ConnectivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager conn = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();

            // Checks the user prefs and the network connection. Based on the result, decides whether
            // to refresh the display or keep the current display.
            // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // If device has its Wi-Fi connection, sets refreshDisplay
                // to true. This causes the display to be refreshed when the user
                // returns to the app.


                // If the setting is ANY network and there is a network connection
                // (which by process of elimination would be mobile), sets refreshDisplay to true.

                connectivityStatusChanged(true, "Wifi Connected");
            } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {

                // Otherwise, the app can't download content--either because there is no network
                // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                // is no Wi-Fi connection.
                // Sets refreshDisplay to false.
                connectivityStatusChanged(true, "LAN Connected");

            } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

                // Otherwise, the app can't download content--either because there is no network
                // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                // is no Wi-Fi connection.
                // Sets refreshDisplay to false.
                connectivityStatusChanged(true, "Mobile Data Connected");

            } else {
                connectivityStatusChanged(false, "Connection Lost");

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
    }
}