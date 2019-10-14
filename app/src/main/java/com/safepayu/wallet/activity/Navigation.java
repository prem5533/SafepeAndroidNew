package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.BusActivity;
import com.safepayu.wallet.activity.booking.FlightsActivity;
import com.safepayu.wallet.activity.booking.MetroActivity;
import com.safepayu.wallet.activity.booking.TrainActivity;
import com.safepayu.wallet.activity.recharge.DthRecharge;
import com.safepayu.wallet.activity.recharge.ElectricityPay;
import com.safepayu.wallet.activity.recharge.GasPay;
import com.safepayu.wallet.activity.recharge.MobileRecharge;
import com.safepayu.wallet.activity.recharge.PostpaidLandlineBillpay;
import com.safepayu.wallet.activity.recharge.WaterBillPay;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.AppVersionResponse;
import com.safepayu.wallet.models.response.UserDetailResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.Profile.QRcodeWidth;

public class Navigation extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private ImageView nav_icon, notification_icon;
    private DrawerLayout drawer;
    private AlertDialog.Builder alertNetwork;
    private boolean doubleBackToExitPressedOnce = false;
    private LinearLayout addMoney, sendMoney, recharge, payBill, dth, payShop, sendToBank,Upi_Pay;
    LinearLayout layout_electricity, layout_gas, layout_water, layout_broadband;
    private LinearLayout payLayout,walletLayout,send;
    String versionName="",appUrl="https://play.google.com/store/apps/details?id=com.safepayu.wallet&hl=en";
    int versionCode=0;
    private LoadingDialog loadingDialog;
    private LinearLayout liMetro,liFlight, liBusTicket,liTrainTicket, liHotles,liDonation,liToll, liFlood;

    //for nav
    private LinearLayout liHome, liProfile, liPackageDetails, liBuyPackage, liCommission, liWallet,liShopping,liChnangePasswlrd,liMyOrders,liHistory,liGenelogy,
            liReferEarn,liUpdteKYC, liContactUs, liLogout,liWalletHistory;
    private TextView tv_home,tvProfile,tvPackageDetails,tvBuyPackage,tvBusinessWallet,tvMyWallet,tvShopping,tvChangePassword,tvMyOrders,tvHistory,tvGenelogy,
            tvReferEarn,tvUpdateKYC, tvContact,tvLogout,tvWalletHistory;
    public static Bitmap qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blue_theme));
        }

        loadingDialog = new LoadingDialog(this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        getAppVersion();
        setupNavigation();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    qrCodeImage=TextToImageEncode(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
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

    /*    addMoney = findViewById(R.id.layout_add_money);
        sendMoney = findViewById(R.id.layout_send_money);*/
        recharge = findViewById(R.id.layout_recharge);
        Upi_Pay = findViewById(R.id.upi_layout1);
        payBill = findViewById(R.id.layout_pay_bill);
        dth = findViewById(R.id.layout_dth);
        send = findViewById(R.id.send);
     /*   payShop = findViewById(R.id.pay_shop);
        sendToBank = findViewById(R.id.layout_send_bank);*/
        payLayout = findViewById(R.id.pay_layout);
        walletLayout = findViewById(R.id.wallet_layout);
        layout_electricity = findViewById(R.id.layout_electricity);
        layout_gas =  findViewById(R.id.layout_gas_recharge);
        layout_water = findViewById(R.id.layout_water);
        layout_broadband = findViewById(R.id.layout_broadband);

        liHome = findViewById(R.id.li_home);
        liProfile = findViewById(R.id.li_profile);
        liPackageDetails = findViewById(R.id.li_package_details);
        liBuyPackage = findViewById(R.id.li_buy_package);
        liCommission = findViewById(R.id.li_commission);
        liWallet = findViewById(R.id.li_my_wallet);
        liShopping = findViewById(R.id.li_shopping);
        liChnangePasswlrd = findViewById(R.id.li_change_password);
        liMyOrders = findViewById(R.id.li_myorder);
        liHistory = findViewById(R.id.li_history);
        liGenelogy = findViewById(R.id.li_genelogy);
        liReferEarn = findViewById(R.id.li_refer_earn);
        liUpdteKYC = findViewById(R.id.li_update_kyc);
        liContactUs = findViewById(R.id.li_contact_us);
        liLogout = findViewById(R.id.li_logout);
        liWalletHistory = findViewById(R.id.li_historyWallet);

        tv_home = findViewById(R.id.tv_home);
        tvProfile = findViewById(R.id.tv_profile);
        tvPackageDetails = findViewById(R.id.tv_package_details);
        tvBuyPackage = findViewById(R.id.tv_buy_packge);
        tvBusinessWallet = findViewById(R.id.tv_commission);
        tvMyWallet = findViewById(R.id.tv_my_wallet);
        tvShopping = findViewById(R.id.tv_shopping);
        tvChangePassword = findViewById(R.id.tv_change_password);
        tvMyOrders = findViewById(R.id.tv_my_orders);
        tvHistory = findViewById(R.id.tv_history);
        tvGenelogy = findViewById(R.id.tv_genelogy);
        tvReferEarn = findViewById(R.id.tv_refer_earn);
        tvUpdateKYC = findViewById(R.id.tv_update_kyc);
        tvContact = findViewById(R.id.tv_contact_us);
        tvLogout = findViewById(R.id.tv_logout);
        tvWalletHistory = findViewById(R.id.tv_historyWallet);

        //**********booking & offers ******
        liMetro = findViewById(R.id.layout_metro);
        liFlight = findViewById(R.id.layout_flight);
        liBusTicket = findViewById(R.id.layout_bus_tickets);
        liTrainTicket = findViewById(R.id.layout_train_ticket);
        liHotles = findViewById(R.id.layout_hotel);
        liDonation = findViewById(R.id.layout_donation);
        liToll = findViewById(R.id.layout_toll);
        liFlood = findViewById(R.id.layout_flood);

        //********************set listener&*****************
        liHome.setOnClickListener(this);
        liProfile.setOnClickListener(this);
        liPackageDetails.setOnClickListener(this);
        liBuyPackage.setOnClickListener(this);
        liCommission.setOnClickListener(this);
        liWallet.setOnClickListener(this);
        liShopping.setOnClickListener(this);
        liChnangePasswlrd.setOnClickListener(this);
        liMyOrders.setOnClickListener(this);
        liHistory.setOnClickListener(this);
        liGenelogy.setOnClickListener(this);
        liReferEarn.setOnClickListener(this);
        liUpdteKYC.setOnClickListener(this);
        liContactUs.setOnClickListener(this);
        liLogout.setOnClickListener(this);
        liWalletHistory.setOnClickListener(this);

      //  addMoney.setOnClickListener(this);
      //  sendMoney.setOnClickListener(this);
        recharge.setOnClickListener(this);
        payBill.setOnClickListener(this);
        dth.setOnClickListener(this);
      //  payShop.setOnClickListener(this);
       // sendToBank.setOnClickListener(this);
        layout_broadband.setOnClickListener(this);
        layout_water.setOnClickListener(this);
        layout_electricity.setOnClickListener(this);
        layout_gas.setOnClickListener(this);
        payLayout.setOnClickListener(this);

        liMetro.setOnClickListener(this);
        liFlight.setOnClickListener(this);
        liBusTicket.setOnClickListener(this);
        liTrainTicket.setOnClickListener(this);
        liHotles.setOnClickListener(this);
        liDonation.setOnClickListener(this);
        liToll.setOnClickListener(this);
        liFlood.setOnClickListener(this);

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
                //startActivity(new Intent(Navigation.this,QrCodeScanner.class));
                Toast.makeText(Navigation.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
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
//        int id = menuItem.getItemId();
//
//        if (id == R.id.nav_home) {
//
//
//        } else if (id == R.id.nav_profile) {
//            startActivity(new Intent(Navigation.this, Profile.class));
//        } else if (id == R.id.nav_show_package) {
//            startActivity(new Intent(Navigation.this, PackageDetails.class));
//        } else if (id == R.id.nav_buy_packge) {
//            startActivity(new Intent(Navigation.this, BuyMemberShip.class));
//        } else if (id == R.id.wallet) {
//            startActivity(new Intent(Navigation.this, WalletActivity.class));
//        } else if (id == R.id.shoping) {
//            Toast.makeText(this, "Coming Sooon", Toast.LENGTH_SHORT).show();
//            drawer.closeDrawers();
//        } else if (id == R.id.orders) {
//            Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
//        } else if (id == R.id.passcode) {
//          startActivity(new Intent(Navigation.this, ForgotPasscode.class));
//        } else if (id == R.id.history) {
//           // startActivity(new Intent(Navigation.this, HistoryActivity.class));
//        } else if (id == R.id.geneology) {
//
//            startActivity(new Intent(Navigation.this, Geneology.class));
//
//        } else if (id == R.id.refer) {
//            startActivity(new Intent(Navigation.this, ReferAndEarn.class));
//
//        } else if (id == R.id.kyc) {
//            Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
//        } else if (id == R.id.logout) {
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, null);
//            startActivity(intent);
//            finish();
//
//        } else if (id == R.id.contact) {
//            startActivity(new Intent(Navigation.this, ContactUs.class));
//        } else if (id == R.id.commission) {
//            startActivity(new Intent(Navigation.this, Commission.class));
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);



        return false;
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

            case R.id.layout_metro:
                startActivity(new Intent(Navigation.this, MetroActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;

            case R.id.layout_flight:
                startActivity(new Intent(Navigation.this, FlightsActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
            case R.id.layout_bus_tickets:
                startActivity(new Intent(Navigation.this, BusActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
            case R.id.layout_train_ticket:
                startActivity(new Intent(Navigation.this, TrainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
            case R.id.li_home:
                tv_home.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                drawer.closeDrawers();
                break;

            case R.id.li_profile:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, Profile.class));

                tvProfile.setTextColor(getResources().getColor(R.color.bue_A800));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liProfile.setBackgroundColor(getResources().getColor(R.color.white));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_package_details:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, PackageDetails.class));

                tvPackageDetails.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));


                break;
            case R.id.li_buy_package:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, BuyMemberShip.class));

                tvBuyPackage.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_commission:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, Commission.class));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_my_wallet:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, WalletActivity.class));

                tvMyWallet.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_shopping:
                drawer.closeDrawers();
                tvShopping.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                Toast.makeText(this, "Coming Sooon", Toast.LENGTH_SHORT).show();
                liShopping.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_change_password:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, ForgotPasscode.class));

                tvChangePassword.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_myorder:
                drawer.closeDrawers();

                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();

                tvMyOrders.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_history:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, RechargeHistory.class));

                tvHistory.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_genelogy:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, Geneology.class));

                tvGenelogy.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_refer_earn:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, ReferAndEarn.class));

                tvReferEarn.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_update_kyc:
                drawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();

                tvUpdateKYC.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_historyWallet:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, WalletHistory.class));

                tvWalletHistory.setTextColor(getResources().getColor(R.color.bue_A800));
                tvContact.setTextColor(getResources().getColor(R.color.black));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.white));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_contact_us:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, ContactUs.class));

                tvContact.setTextColor(getResources().getColor(R.color.bue_A800));
                tvProfile.setTextColor(getResources().getColor(R.color.black));
                tvPackageDetails.setTextColor(getResources().getColor(R.color.black));
                tvBuyPackage.setTextColor(getResources().getColor(R.color.black));
                tvBusinessWallet.setTextColor(getResources().getColor(R.color.black));
                tvMyWallet.setTextColor(getResources().getColor(R.color.black));
                tvShopping.setTextColor(getResources().getColor(R.color.black));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
                tvMyOrders.setTextColor(getResources().getColor(R.color.black));
                tvHistory.setTextColor(getResources().getColor(R.color.black));
                tvGenelogy.setTextColor(getResources().getColor(R.color.black));
                tvReferEarn.setTextColor(getResources().getColor(R.color.black));
                tvUpdateKYC.setTextColor(getResources().getColor(R.color.black));
                tv_home.setTextColor(getResources().getColor(R.color.black));
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.white));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_logout:
                drawer.closeDrawers();
                liLogout.setBackgroundColor(getResources().getColor(R.color.white));
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, null);
                startActivity(intent);
                finish();


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

    private void getAppVersion() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AppVersionResponse>() {
                    @Override
                    public void onSuccess(AppVersionResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            int val= Integer.parseInt(response.getVersionData().getVal());

                            if (versionCode==val){

                            }else {
                                showDialogForAppUpdate(Navigation.this);
                            }

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(drawer, response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(drawer, false, e.getCause());
                    }
                }));
    }

    public void showDialogForAppUpdate(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.app_update_dialog);

        Button proceedButton = (Button) dialog.findViewById(R.id.proceedBtn_appUpdate);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
                finish();
            }
        });

        Button cancelBtn_appUpdate = (Button) dialog.findViewById(R.id.cancelBtn_appUpdate);
        cancelBtn_appUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApp.getInstance().toastHelper().showSnackBar(drawer, getResources().getString(R.string.waringforAppUpdate), true);
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

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
                .subscribeWith(new DisposableSingleObserver<UserDetailResponse>() {
                    @Override
                    public void onSuccess(UserDetailResponse response) {
                        BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().USER, new Gson().toJson(response.getUser()));

                        //BaseApp.getInstance().toastHelper().log(HomeActivity.class, BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER));

                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PASSCODE, String.valueOf(response.getUser().getPasscode()));
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_EMAIL,response.getUser().getEmail());
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME,response.getUser().getFirst_name());
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_LAST_NAME,response.getUser().getLast_name());
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().IS_BLOCKED,String.valueOf(response.getUser().getBlocked()));
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED,String.valueOf(response.getUser().getPackage_status()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());

                        BaseApp.getInstance().toastHelper().showApiExpectation(drawer, true, e);
                    }
                }));
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.bue_A800):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


}
