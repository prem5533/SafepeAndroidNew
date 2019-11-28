package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.DonationActivity;
import com.safepayu.wallet.activity.booking.MetroActivity;
import com.safepayu.wallet.activity.booking.MovieActivity;
import com.safepayu.wallet.activity.booking.TollActivity;
import com.safepayu.wallet.activity.booking.TrainActivity;
import com.safepayu.wallet.activity.booking.bus.BusActivity;
import com.safepayu.wallet.activity.booking.flight.FlightsActivity;
import com.safepayu.wallet.activity.booking.hotel.HotelActivity;
import com.safepayu.wallet.activity.recharge.DthRecharge;
import com.safepayu.wallet.activity.recharge.ElectricityPay;
import com.safepayu.wallet.activity.recharge.GasPay;
import com.safepayu.wallet.activity.recharge.InsuranceActivity;
import com.safepayu.wallet.activity.recharge.MobileRecharge;
import com.safepayu.wallet.activity.recharge.PostpaidLandlineBillpay;
import com.safepayu.wallet.activity.recharge.WaterBillPay;
import com.safepayu.wallet.adapter.OfferPagerAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.PromotionRequest;
import com.safepayu.wallet.models.response.AppVersionResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.PromotionResponse;
import com.safepayu.wallet.models.response.UserDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.Profile.QRcodeWidth;

public class Navigation extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ImageView nav_icon, notification_icon;
    private DrawerLayout drawer;
    private AlertDialog.Builder alertNetwork;
    private boolean doubleBackToExitPressedOnce = false;
    private LinearLayout addMoney, sendMoney, recharge, payBill, dth, payShop, sendToBank, Upi_Pay;
    LinearLayout layout_electricity, layout_gas, layout_water, layout_broadband;
    private LinearLayout payLayout, walletLayout, send;
    String versionName = "", FirebaseToken, appUrl = "https://play.google.com/store/apps/details?id=com.safepayu.wallet&hl=en";
    private int versionCode = 0;
    private LoadingDialog loadingDialog;
    private LinearLayout liMetro, liFlight, liBusTicket, liTrainTicket, liHotles, liDonation, liToll, liFlood, liCredit, liInsurance, limovie, liGoogleplay, lihotel,
            liBigBazaar, liBrandFactory, liKFC, liDominos, liLogoutAllDevices, linearSecurityTab;
    public static int BadgeCount = 0;
    public static TextView BadgeCountTV;
    Dialog dialog;
    TextView TitleNotiDialog, ContentNotiDialog;
    ImageView ImageViewNotiDialog, imageDownSecurity, imageUpSecurity,headerLogo,navLogo;
    private RelativeLayout searchLayout_nav;
    public static String TitleNotiDialogText = "", ContentNotiDialogText = "", tollNumber = "";
    public static Bitmap ImageNotiDialog;
    private ViewPager viewpager;
    private String url;

    //for nav
    private LinearLayout liHome, liProfile, liPackageDetails, liBuyPackage, liCommission, liWallet, liShopping, liChnangePasswlrd, liMyOrders, liHistory, liGenelogy,
            liReferEarn, liUpdteKYC, liContactUs, liLogout, liWalletHistory, liSecurity;
    private TextView tv_home, tvProfile, tvPackageDetails, tvBuyPackage, tvBusinessWallet, tvMyWallet, tvShopping, tvChangePassword, tvMyOrders, tvHistory, tvGenelogy,
            tvReferEarn, tvUpdateKYC, tvContact, tvLogout, tvLogoutAlldevice, tvWalletHistory, tv_security;
    public static Bitmap qrCodeImage;
    int NUM_PAGES,currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    String ImagePath="http://india.safepayu.com/safepe-new/public/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        setToolbar(false, null, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.red_theme));
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

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.v("getInstanceId failed", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        FirebaseToken = task.getResult().getToken();
                    }
                });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    qrCodeImage = TextToImageEncode(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notification_dialog);
        TitleNotiDialog = dialog.findViewById(R.id.title_notificationDialog);
        ContentNotiDialog = dialog.findViewById(R.id.content_notificationDialog);
        ImageViewNotiDialog = dialog.findViewById(R.id.image_notificationDialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
      //  dialog.show();

        searchLayout_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.navigation;
    }

    private void setupNavigation() {
        searchLayout_nav = findViewById(R.id.searchLayout_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notification_icon = findViewById(R.id.notification);
        nav_icon = findViewById(R.id.nav_icon);
        nav_icon.setOnClickListener(nav_iconListner);

        BadgeCountTV = findViewById(R.id.cart_badge);

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
        layout_gas = findViewById(R.id.layout_gas_recharge);
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
        liLogoutAllDevices = findViewById(R.id.li_logoutAlldevice);
        liWalletHistory = findViewById(R.id.li_historyWallet);
        liGoogleplay = findViewById(R.id.layout_googleplay);
        imageDownSecurity = findViewById(R.id.image_down_arrow);
        imageUpSecurity = findViewById(R.id.image_up_arrow);
        headerLogo = findViewById(R.id.header_logo);
        navLogo = findViewById(R.id.nav_logo);
     /*   String imagePath = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LOGO_IMAGE);
        Picasso.get().load(imagePath).into(headerLogo);
        Picasso.get().load(imagePath).into(navLogo);*/

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
        tvLogoutAlldevice = findViewById(R.id.tv_logoutAlldevice);
        tvWalletHistory = findViewById(R.id.tv_historyWallet);
        tv_security = findViewById(R.id.tv_security);
        viewpager = findViewById(R.id.viewpager);


        //**********booking & offers ******
        liMetro = findViewById(R.id.layout_metro);
        liFlight = findViewById(R.id.layout_flight);
        liBusTicket = findViewById(R.id.layout_bus_tickets);
        liTrainTicket = findViewById(R.id.layout_train_ticket);
        liHotles = findViewById(R.id.layout_hotel);
        liDonation = findViewById(R.id.layout_donation);
        liToll = findViewById(R.id.layout_toll);
        liFlood = findViewById(R.id.layout_flood);
        liCredit = findViewById(R.id.credit_layout);
        liInsurance = findViewById(R.id.layout_insurance);
        limovie = findViewById(R.id.layout_movie);
        lihotel = findViewById(R.id.layout_hotel);
        liBigBazaar = findViewById(R.id.layout_big_bazar);
        liBrandFactory = findViewById(R.id.layout_brand_factory);
        liKFC = findViewById(R.id.layout_kfc);
        liDominos = findViewById(R.id.layout_dominos);
        linearSecurityTab = findViewById(R.id.linear_security_tab);
        liSecurity = findViewById(R.id.li_security);


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
        liLogoutAllDevices.setOnClickListener(this);
        liWalletHistory.setOnClickListener(this);
        liInsurance.setOnClickListener(this);
        limovie.setOnClickListener(this);
        liGoogleplay.setOnClickListener(this);
        lihotel.setOnClickListener(this);
        liDominos.setOnClickListener(this);
        liKFC.setOnClickListener(this);
        liBrandFactory.setOnClickListener(this);
        liBigBazaar.setOnClickListener(this);
        linearSecurityTab.setVisibility(View.GONE);
        //  addMoney.setOnClickListener(this);
        //  sendMoney.setOnClickListener(this);
        recharge.setOnClickListener(this);
        payBill.setOnClickListener(this);
        dth.setOnClickListener(this);
        liCredit.setOnClickListener(this);

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
        liBigBazaar.setOnClickListener(this);
        liKFC.setOnClickListener(this);
        liBrandFactory.setOnClickListener(this);
        liBigBazaar.setOnClickListener(this);


        getPrmotionalOffer();


        imageDownSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearSecurityTab.setVisibility(View.VISIBLE);
                imageDownSecurity.setVisibility(View.GONE);
                imageUpSecurity.setVisibility(View.VISIBLE);
                liSecurity.setBackgroundColor(getResources().getColor(R.color.white));
                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHome.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liProfile.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liPackageDetails.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liBuyPackage.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liCommission.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWallet.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liShopping.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.white));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.white));


                tv_home.setTextColor(getResources().getColor(R.color.black));
                tv_security.setTextColor(getResources().getColor(R.color.bue_A800));
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
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

            }
        });
        imageUpSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityTab();
            }
        });


        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // startActivity(new Intent(Navigation.this, BellNotifictionActivity.class));
                startActivity(new Intent(Navigation.this, BellNotifictionActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);

            }
        });

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


        createNotificationChannel();
        getFirebaseToken(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewpager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }


    private void securityTab() {
        linearSecurityTab.setVisibility(View.GONE);
        imageDownSecurity.setVisibility(View.VISIBLE);
        imageUpSecurity.setVisibility(View.GONE);
        liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
        tv_security.setTextColor(getResources().getColor(R.color.black));
        liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
        liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
        tvChangePassword.setTextColor(getResources().getColor(R.color.black));
        tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));
    }

    private View.OnClickListener nav_iconListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawer.openDrawer(GravityCompat.START);

        }
    };


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("SafePe", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
            getUserDetails();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.walletLayout), "No Internet Connection", false);
        }

        if (BadgeCount == 0) {
            BadgeCountTV.setVisibility(View.GONE);
        } else {
            BadgeCountTV.setText("" + BadgeCount);
            BadgeCountTV.setVisibility(View.VISIBLE);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

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
                startActivity(new Intent(Navigation.this, QrCodeScanner.class));
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
            case R.id.credit_layout:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.layout_insurance:
                startActivity(new Intent(Navigation.this, InsuranceActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
            case R.id.layout_movie:
                startActivity(new Intent(Navigation.this, MovieActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;

            case R.id.layout_googleplay:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_hotel:
                startActivity(new Intent(Navigation.this, HotelActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
            case R.id.layout_donation:
                startActivity(new Intent(Navigation.this, DonationActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
            case R.id.layout_toll:
                startActivity(new Intent(Navigation.this, TollActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
            case R.id.layout_flood:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_big_bazar:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_brand_factory:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_kfc:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_dominos:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;


            case R.id.li_home:
                tv_home.setTextColor(getResources().getColor(R.color.bue_A800));
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.bue_A800));
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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                drawer.closeDrawers();
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));
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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
                break;

            case R.id.li_myorder:
                drawer.closeDrawers();
                startActivity(new Intent(Navigation.this, MyOrdersActivity.class));
                //  overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);

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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();

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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));

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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                securityTab();
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
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));
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
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liSecurity.setBackgroundColor(getResources().getColor(R.color.nav_bg));

                break;

            case R.id.li_logout:
                drawer.closeDrawers();
                liLogout.setBackgroundColor(getResources().getColor(R.color.white));
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FIREBASE_TOKEN, null);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, null);
                startActivity(intent);
                finish();
                securityTab();

                break;

            case R.id.li_logoutAlldevice:
                drawer.closeDrawers();

                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.bue_A800));
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
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));

                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.white));
                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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

                showDialogLogoutAll(Navigation.this);

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
                            int val = Integer.parseInt(response.getVersionData().getVal());
                            url = response.getVersionData().getLogo();
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().LOGO_IMAGE, ImagePath+url);
                       /*     Picasso.get().load(ImagePath+url).into(headerLogo);
                            Picasso.get().load(ImagePath+url).into(navLogo);*/

                            try {
                                tollNumber = response.getVersionData().getTollfree();
                            } catch (Exception e) {
                                tollNumber = "";
                                e.printStackTrace();
                            }

                            if (versionCode == val) {

                            } else {
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
        if (!isConnected) {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.drawer_layout), "No Internet Connection!", false);
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

                        if (response.getUser().getStatus() == 0) {
                            showDialogBlocked(Navigation.this);
                        } else {
                            BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().USER, new Gson().toJson(response.getUser()));

                            //BaseApp.getInstance().toastHelper().log(HomeActivity.class, BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER));
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED, String.valueOf(response.getUser().getEmail_verified()));
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PASSCODE, String.valueOf(response.getUser().getPasscode()));
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_EMAIL, response.getUser().getEmail());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_FIRST_NAME, response.getUser().getFirst_name());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().USER_LAST_NAME, response.getUser().getLast_name());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().IS_BLOCKED, String.valueOf(response.getUser().getBlocked()));
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED, String.valueOf(response.getUser().getPackage_status()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(drawer, true, e);
                    }
                }));
    }

    public void showDialogBlocked(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("SafePe Alert")
                .setMessage("You are blocked. Please Contact Customer Support Immediately.")
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }

    public void showDialogLogoutAll(Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        dialog.setTitle("SafePe Alert")
                .setCancelable(false)
                .setMessage("\nAre You Sure You Want To Logout Your Account From All Devices?\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        getlogoutAlldevices();

                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }

    private void getFirebaseToken(String userId) {

        if (TextUtils.isEmpty(FirebaseToken)) {
            FirebaseToken = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FIREBASE_TOKEN);
        }
        //  loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getFirebaseToken(userId, FirebaseToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        //  loadingDialog.hideDialog();

                        if (response.getStatus()) {

                        } else {
                            Toast.makeText(Navigation.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  loadingDialog.hideDialog();
                        Toast.makeText(Navigation.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
                        getResources().getColor(R.color.bue_A800) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void getlogoutAlldevices() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getlogoutAlldevices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            Intent intentAlldevice = new Intent(getApplicationContext(), LoginActivity.class);
                            intentAlldevice.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FIREBASE_TOKEN, null);
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN, null);
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().LOGOUT_ALL, "1");
                            startActivity(intentAlldevice);
                            finish();
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(drawer, response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(drawer, false, e.getCause());
                    }
                }));
    }



    private void getPrmotionalOffer() {

       // loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);


        final PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setType("1");
        BaseApp.getInstance().getDisposable().add(apiService.getPromotionOffer(promotionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PromotionResponse>() {
                    @Override
                    public void onSuccess(PromotionResponse promotionResponse) {

                        NUM_PAGES= promotionResponse.getData().size();
                        loadingDialog.hideDialog();
                        if (promotionResponse.isStatus()) {

                            OfferPagerAdapter adapter = new OfferPagerAdapter(Navigation.this,promotionResponse.getData());
                            viewpager.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                    }
                }));


    }
}
