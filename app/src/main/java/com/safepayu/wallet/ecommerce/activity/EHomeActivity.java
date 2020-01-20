package com.safepayu.wallet.ecommerce.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.BuyMemberShip;
import com.safepayu.wallet.activity.ChangePassword;
import com.safepayu.wallet.activity.Commission;
import com.safepayu.wallet.activity.ContactUs;
import com.safepayu.wallet.activity.ForgotPasscode;
import com.safepayu.wallet.activity.Geneology;
import com.safepayu.wallet.activity.KycUpdate;
import com.safepayu.wallet.activity.LoginActivity;
import com.safepayu.wallet.activity.PackageDetails;
import com.safepayu.wallet.activity.Profile;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.activity.ReferAndEarn;
import com.safepayu.wallet.activity.WalletActivity;
import com.safepayu.wallet.activity.WalletHistory;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.fragment.HomeFragment;
import com.safepayu.wallet.ecommerce.fragment.WishlistFragment;
import com.safepayu.wallet.models.response.BaseResponse;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class EHomeActivity extends AppCompatActivity implements View.OnClickListener , LocationListener {

    private ImageView CartBtn,NotificationBtn,NavIcon,imageDownSecurity, imageUpSecurity,imageDownLogout, imageUpLogout,locationBtn,imCross;
    private DrawerLayout drawer;
    private LoadingDialog loadingDialog;
    public  Dialog dialog;
    public static TextView tvLocation , tvSerch,tvCartBadge;
    private EditText etSearchEcomm;
    LocationManager locationManager;
    Geocoder geocoder;
    List<Address> listAddresses;




    //for nav
    private LinearLayout liHome, liProfile, liPackageDetails, liBuyPackage, liCommission, liWallet, liShopping, liChnangePasswlrd, liMyOrders, liHistory, liGenelogy,
            liReferEarn, liUpdteKYC, liContactUs, liLogout, liWalletHistory, liSecurity,liLogoutParent,liChnangePassword ,
            liLogoutAllDevices, linearSecurityTab,linearLogoutTab;
    private TextView tv_home, tvProfile, tvPackageDetails, tvBuyPackage, tvBusinessWallet, tvMyWallet, tvShopping, tvChangePassword, tvMyOrders, tvHistory, tvGenelogy,
            tvReferEarn, tvUpdateKYC, tvContact, tvLogout, tvLogoutAlldevice, tvWalletHistory, tv_security,tvChangePasswordChild,tvLogoutTextParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ehome);
        loadFragment(new HomeFragment());
        findId();

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    private void findId() {
        CartBtn = findViewById(R.id.cartBtn_main);
        tvCartBadge = findViewById(R.id.cart_badge);
        NotificationBtn = findViewById(R.id.favBtn_main);
        NavIcon = findViewById(R.id.nav_iconEcommerce);
        locationBtn = findViewById(R.id.location_ecom);




        loadingDialog = new LoadingDialog(this);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog(EHomeActivity.this);
            }
        });

        CartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        NotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new WishlistFragment());
            }
        });

        NavIcon.setOnClickListener(nav_iconListner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layoutEcommerce);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);


        liHome = findViewById(R.id.li_home);
        liProfile = findViewById(R.id.li_profile);
        liPackageDetails = findViewById(R.id.li_package_details);
        liBuyPackage = findViewById(R.id.li_buy_package);
        liCommission = findViewById(R.id.li_commission);
        liWallet = findViewById(R.id.li_my_wallet);
        liShopping = findViewById(R.id.li_shopping);
        liChnangePasswlrd = findViewById(R.id.li_change_password);
        liChnangePassword = findViewById(R.id.li_change_passwordChild);
        liMyOrders = findViewById(R.id.li_myorder);
        liHistory = findViewById(R.id.li_history);
        liGenelogy = findViewById(R.id.li_genelogy);
        liReferEarn = findViewById(R.id.li_refer_earn);
        liUpdteKYC = findViewById(R.id.li_update_kyc);
        liContactUs = findViewById(R.id.li_contact_us);
        liLogout = findViewById(R.id.li_logout);
        liLogoutAllDevices = findViewById(R.id.li_logoutAlldevice);
        liWalletHistory = findViewById(R.id.li_historyWallet);
        linearSecurityTab = findViewById(R.id.linear_security_tab);
        linearLogoutTab= findViewById(R.id.linear_logout_tab);
        liSecurity = findViewById(R.id.li_LogoutParent);
        liLogoutParent = findViewById(R.id.li_security);
        imageDownSecurity = findViewById(R.id.image_down_arrow);
        imageUpSecurity = findViewById(R.id.image_up_arrow);
        imageDownLogout = findViewById(R.id.image_down_arrowLogout);
        imageUpLogout = findViewById(R.id.image_up_arrowLogout);

        tv_home = findViewById(R.id.tv_home);
        tvProfile = findViewById(R.id.tv_profile);
        tvPackageDetails = findViewById(R.id.tv_package_details);
        tvBuyPackage = findViewById(R.id.tv_buy_packge);
        tvBusinessWallet = findViewById(R.id.tv_commission);
        tvMyWallet = findViewById(R.id.tv_my_wallet);
        tvShopping = findViewById(R.id.tv_shopping);
        tvChangePassword = findViewById(R.id.tv_change_password);
        tvChangePasswordChild= findViewById(R.id.tv_change_passwordChild);
        tvMyOrders = findViewById(R.id.tv_my_orders);
        tvHistory = findViewById(R.id.tv_history);
        tvGenelogy = findViewById(R.id.tv_genelogy);
        tvReferEarn = findViewById(R.id.tv_refer_earn);
        tvUpdateKYC = findViewById(R.id.tv_update_kyc);
        tvContact = findViewById(R.id.tv_contact_us);
        tvLogout = findViewById(R.id.tv_logout);
        tvLogoutAlldevice = findViewById(R.id.tv_logoutAlldevice);
        tvLogoutTextParent=findViewById(R.id.tv_logoutText);
        tvWalletHistory = findViewById(R.id.tv_historyWallet);
        tvShopping = findViewById(R.id.tv_shopping);
        tv_security = findViewById(R.id.tv_security);

        //********************set listener&*****************
        liHome.setOnClickListener(this);
        liProfile.setOnClickListener(this);
        liPackageDetails.setOnClickListener(this);
        liBuyPackage.setOnClickListener(this);
        liCommission.setOnClickListener(this);
        liWallet.setOnClickListener(this);
        liShopping.setOnClickListener(this);
        liChnangePasswlrd.setOnClickListener(this);
        liChnangePassword.setOnClickListener(this);
        liMyOrders.setOnClickListener(this);
        liHistory.setOnClickListener(this);
        liGenelogy.setOnClickListener(this);
        liReferEarn.setOnClickListener(this);
        liUpdteKYC.setOnClickListener(this);
        liContactUs.setOnClickListener(this);
        liLogout.setOnClickListener(this);
        liLogoutAllDevices.setOnClickListener(this);
        liWalletHistory.setOnClickListener(this);
        linearSecurityTab.setVisibility(View.GONE);
        linearLogoutTab.setVisibility(View.GONE);

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
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liMyOrders.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liGenelogy.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liReferEarn.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liUpdteKYC.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liContactUs.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogout.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liLogoutAllDevices.setBackgroundColor(getResources().getColor(R.color.nav_bg));


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

        imageDownLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLogoutTab.setVisibility(View.VISIBLE);
                imageDownLogout.setVisibility(View.GONE);
                imageUpLogout.setVisibility(View.VISIBLE);
                linearLogoutTab.requestFocus();
                tvLogoutTextParent.setTextColor(getResources().getColor(R.color.bue_A800));

            }
        });

        imageUpLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLogoutTab.setVisibility(View.GONE);
                imageDownLogout.setVisibility(View.VISIBLE);
                imageUpLogout.setVisibility(View.GONE);
                tvLogoutTextParent.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }

    private void showPinDialog(EHomeActivity eHomeActivity) {
        dialog = new Dialog(eHomeActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dialog_pin);

        imCross = dialog.findViewById(R.id.imCross);
        tvLocation = dialog.findViewById(R.id.tv_getLocation);
        tvSerch = dialog.findViewById(R.id.tv_search_pin);
        etSearchEcomm = dialog.findViewById(R.id.tv_search_ecomm);
        imCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCurrentLocation();

            }
        });
        tvSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        int dialogWindowHeight = (int) (displayHeight * 0.7f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void getCurrentLocation() {
        try {
            loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

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

    @Override
    public void onClick(View view) {

        LinearLayout l = (LinearLayout) view;
        switch (l.getId()) {
            case R.id.li_home:
                finish();
                tv_home.setTextColor(getResources().getColor(R.color.bue_A800));
                tvLogoutAlldevice.setTextColor(getResources().getColor(R.color.black));
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
                tvLogout.setTextColor(getResources().getColor(R.color.black));
                tvWalletHistory.setTextColor(getResources().getColor(R.color.black));
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, Profile.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, PackageDetails.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, BuyMemberShip.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, Commission.class));
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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, WalletActivity.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, ForgotPasscode.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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

            case R.id.li_change_passwordChild:
                drawer.closeDrawers();
                startActivity(new Intent(EHomeActivity.this, ChangePassword.class));

                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.bue_A800));
                tvChangePassword.setTextColor(getResources().getColor(R.color.black));
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

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.white));
                liWalletHistory.setBackgroundColor(getResources().getColor(R.color.nav_bg));
                liChnangePasswlrd.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                // startActivity(new Intent(Navigation.this, MyOrdersActivity.class));
                startActivity(new Intent(EHomeActivity.this, MyOrderEcomActivity.class));
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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, RechargeHistory.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, Geneology.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, ReferAndEarn.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, KycUpdate.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, WalletHistory.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                startActivity(new Intent(EHomeActivity.this, ContactUs.class));

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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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
                tvChangePasswordChild.setTextColor(getResources().getColor(R.color.black));

                liChnangePassword.setBackgroundColor(getResources().getColor(R.color.nav_bg));
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

                showDialogLogoutAll(EHomeActivity.this);

                break;
        }
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
                .setIcon(getResources().getDrawable(R.drawable.logo))
                .show();
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

    @Override
    public void onLocationChanged(Location location) {


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != location && null != providerList && providerList.size() > 0) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();


            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String address = listAddresses.get(0).getAddressLine(0);
                    String postalCode = listAddresses.get(0).getPostalCode();
                    etSearchEcomm.setText(address);
                    loadingDialog.hideDialog();

                }
            } catch (Exception e) {
                loadingDialog.hideDialog();
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(EHomeActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        int mMenuId;
        mMenuId = item.getItemId();
        for (int i = 0; i < bottomNavigation.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigation.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }

        switch (item.getItemId()) {
            case R.id.b_home_ecomm:
             //   startActivity(new Intent(EHomeActivity.this, Navigation.class));
                finish();

            break;

            case R.id.b_wallet_ecomm:
                startActivity(new Intent(EHomeActivity.this, WalletActivity.class));

            break;

            case R.id.b_qrcode_ecomm:
                startActivity(new Intent(EHomeActivity.this, QrCodeScanner.class));

            break;

            case R.id.b_mall_ecomm:
                //   Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(EHomeActivity.this, EHomeActivity.class));

            break;
        }
        return true;
    }*/
}
