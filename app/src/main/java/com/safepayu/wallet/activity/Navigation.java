package com.safepayu.wallet.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.recharge.DthRecharge;
import com.safepayu.wallet.activity.recharge.ElectricityPay;
import com.safepayu.wallet.activity.recharge.GasPay;
import com.safepayu.wallet.activity.recharge.MobileRecharge;
import com.safepayu.wallet.activity.recharge.PostpaidLandlineBillpay;
import com.safepayu.wallet.activity.recharge.WaterBillPay;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Navigation extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private ImageView nav_icon, notification_icon;
    private DrawerLayout drawer;
    private AlertDialog.Builder alertNetwork;
    private boolean doubleBackToExitPressedOnce = false;
    private LinearLayout addMoney, sendMoney, recharge, payBill, dth, payShop, sendToBank,Upi_Pay;
    LinearLayout layout_electricity, layout_gas, layout_water, layout_broadband;
    private LinearLayout payLayout,walletLayout,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        setupNavigation();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.navigation;
    }

    private void setupNavigation() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notification_icon = findViewById(R.id.notification);
        nav_icon = findViewById(R.id.nav_icon);
        nav_icon.setOnClickListener(nav_iconListner);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        alertNetwork = new AlertDialog.Builder(Navigation.this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addMoney = findViewById(R.id.layout_add_money);
        sendMoney = findViewById(R.id.layout_send_money);
        recharge = findViewById(R.id.layout_recharge);
        Upi_Pay = findViewById(R.id.upi_layout1);
        payBill = findViewById(R.id.layout_pay_bill);
        dth = findViewById(R.id.layout_dth);
        send = findViewById(R.id.send);
        payShop = findViewById(R.id.pay_shop);
        sendToBank = findViewById(R.id.layout_send_bank);
        payLayout = findViewById(R.id.pay_layout);
        walletLayout = findViewById(R.id.wallet_layout);
        layout_electricity = findViewById(R.id.layout_electricity);
        layout_gas =  findViewById(R.id.layout_gas_recharge);
        layout_water = findViewById(R.id.layout_water);
        layout_broadband = findViewById(R.id.layout_broadband);

        addMoney.setOnClickListener(this);
        sendMoney.setOnClickListener(this);
        recharge.setOnClickListener(this);
        payBill.setOnClickListener(this);
        dth.setOnClickListener(this);
        payShop.setOnClickListener(this);
        sendToBank.setOnClickListener(this);
        layout_broadband.setOnClickListener(this);
        layout_water.setOnClickListener(this);
        layout_electricity.setOnClickListener(this);
        layout_gas.setOnClickListener(this);
        payLayout.setOnClickListener(this);

        walletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation.this, WalletActivity.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SendMoney.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
            }
        });

        Upi_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.drawer_layout),"Coming Soon!",false);
            }
        });

        getUserDetails();

    }

    private View.OnClickListener nav_iconListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawer.openDrawer(GravityCompat.START);

        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {


        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(Navigation.this, Profile.class));
        } else if (id == R.id.nav_show_package) {
            startActivity(new Intent(Navigation.this, PackageDetails.class));
        } else if (id == R.id.nav_buy_packge) {
            startActivity(new Intent(Navigation.this, BuyMemberShip.class));
        } else if (id == R.id.wallet) {
            startActivity(new Intent(Navigation.this, WalletActivity.class));
        } else if (id == R.id.shoping) {
            Toast.makeText(this, "Coming Sooon", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (id == R.id.orders) {
            Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
        } else if (id == R.id.passcode) {
          startActivity(new Intent(Navigation.this, ForgotPasscode.class));
        } else if (id == R.id.history) {
           // startActivity(new Intent(Navigation.this, HistoryActivity.class));
        } else if (id == R.id.geneology) {

            startActivity(new Intent(Navigation.this, Geneology.class));

        } else if (id == R.id.refer) {
            startActivity(new Intent(Navigation.this, ReferAndEarn.class));

        } else if (id == R.id.kyc) {
            Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
        } else if (id == R.id.logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, null);
            startActivity(intent);
            finish();

        } else if (id == R.id.contact) {
            startActivity(new Intent(Navigation.this, ContactUs.class));
        } else if (id == R.id.commission) {
            startActivity(new Intent(Navigation.this, Commission.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        LinearLayout l = (LinearLayout) view;
        switch (l.getId()) {
            case R.id.layout_recharge:
                startActivity(new Intent(Navigation.this, MobileRecharge.class));
                break;

            case R.id.layout_pay_bill:
                startActivity(new Intent(Navigation.this, PostpaidLandlineBillpay.class));
                break;

            case R.id.layout_broadband:
                startActivity(new Intent(Navigation.this, PostpaidLandlineBillpay.class));
                break;

            case R.id.layout_dth:
                startActivity(new Intent(Navigation.this, DthRecharge.class));
                break;

            case R.id.layout_water:
                startActivity(new Intent(Navigation.this, WaterBillPay.class));
                break;

            case R.id.layout_electricity:
                startActivity(new Intent(Navigation.this, ElectricityPay.class));
                break;

            case R.id.layout_gas_recharge:
                startActivity(new Intent(Navigation.this, GasPay.class));
                break;

            case R.id.pay_layout:
                startActivity(new Intent(Navigation.this, SendMoneyToWallet.class));
                break;
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected){

        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.drawer_layout),"No Internet Connection!",false);
        }
    }

    private void getUserDetails() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getUserDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse response) {
                        BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().USER, new Gson().toJson(response.getUser()));

                        //BaseApp.getInstance().toastHelper().log(HomeActivity.class, BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER));

                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PASSCODE,response.getUser().getPassCode());
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_EMAIL,response.getUser().getEmail());
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME,response.getUser().getFirstName());
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_LAST_NAME,response.getUser().getLastName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());

                        BaseApp.getInstance().toastHelper().showApiExpectation(drawer, true, e);
                    }
                }));
    }


}
