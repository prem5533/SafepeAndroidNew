package com.safepayu.wallet.ecommerce.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.QrCodeScanner;
import com.safepayu.wallet.activity.ScratchActivity;
import com.safepayu.wallet.activity.WalletActivity;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.activity.EHomeActivity;
import com.safepayu.wallet.ecommerce.activity.ProductDetailActivity;
import com.safepayu.wallet.ecommerce.activity.SearchEcommerce;
import com.safepayu.wallet.ecommerce.adapter.CategoryAdapter;
import com.safepayu.wallet.ecommerce.adapter.EcommPagerAdapter;
import com.safepayu.wallet.ecommerce.adapter.OfferAdapter;
import com.safepayu.wallet.ecommerce.adapter.ParentCategoryAdapter;
import com.safepayu.wallet.ecommerce.adapter.RecommendedAdapter;
import com.safepayu.wallet.ecommerce.adapter.TrendingAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.response.CategoryModel;
import com.safepayu.wallet.ecommerce.model.response.HomeCatResponse;
import com.safepayu.wallet.ecommerce.model.response.LatLongResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.ecommerce.activity.EHomeActivity.tvCartBadge;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryItemListener,
        BottomNavigationView.OnNavigationItemSelectedListener, ParentCategoryAdapter.OnCategoryItemListener, EcommPagerAdapter.PagerListener ,
        RecommendedAdapter.OnRecommendedItemListener,TrendingAdapter.OnTrendinItemListener, OfferAdapter.OnPOfferItemListener,
        LocationListener {

    private RecyclerView recyclerCategory,recycleCategoryOfferList,recycleTrendingProductList,recycleRecommendList;
    private CategoryAdapter categoryAdapter;
    private OfferAdapter offerAdapter;
    private RecommendedAdapter recommendedAdapter;
    private TrendingAdapter trendingAdapter;
    private EcommPagerAdapter ecommPagerAdapter;
    private BottomNavigationView bottomNavigation;
    private GridLayoutManager gridLayoutManager;
    public int STATIC_INTEGER_VALUE = 3;
    private ViewPager viewpager;
    int images[] = {R.drawable.banner_image1, R.drawable.banner_image2, R.drawable.banner_image3,R.drawable.banner_image4};
    int NumPage,CurrentP=0 ;
    Timer timer;  final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.

    private String Latitude="",Longitude="";
    private LinearLayout SearchLayout,OfferLayout , liLooxStore,liWallet,liReward;
    private TextView tvSearch,tvViewAll;
    private EditText etSearchEcomm;
    public static ArrayList<String> BrandNameList,BrandIdList;
    public static ArrayList<String> CatNameList,CatIdList;


    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private LoadingDialog loadingDialog;
    TabLayout tabLayoutt;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home2, container, false);
     //   prepareCategoryData();
        findId(view);
        return  view;
    }



    private void findId(View view) {
        loadingDialog=new LoadingDialog(getActivity());
        bottomNavigation = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        recyclerCategory = view.findViewById(R.id.recycle_category_list);
        recycleCategoryOfferList = view.findViewById(R.id.recycle_category_offer_list);
        recycleTrendingProductList = view.findViewById(R.id.recycle_trending_product_list);
        recycleRecommendList = view.findViewById(R.id.recycle_recommend_list);
        viewpager = view.findViewById(R.id.viewpager_ecom);
        SearchLayout = view.findViewById(R.id.searchLayout_headerHome);
        tvSearch = view.findViewById(R.id.tv_search_ecomm);
        OfferLayout =view.findViewById(R.id.offerLayoutHome);
        tvViewAll = view.findViewById(R.id.viewAllCat_home);
        tabLayoutt = view.findViewById(R.id.tab_layout_ecom);
        liLooxStore = view.findViewById(R.id.liLooxStoreHome);
        liWallet = view.findViewById(R.id.liWalletHome);
        liReward = view.findViewById(R.id.liRewardHome);

        recyclerCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleCategoryOfferList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleTrendingProductList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        recycleRecommendList.setLayoutManager(gridLayoutManager);


        /*After setting the adapter use the timer type 3 */
        final Handler hand = new Handler();
        final Runnable Updt = new Runnable() {
            public void run() {
                if (CurrentP == NumPage) {
                    CurrentP = 0;
                }
                viewpager.setCurrentItem(CurrentP++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                hand.post(Updt);
            }
        }, DELAY_MS, PERIOD_MS);


        liWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            }
        });
        liReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScratchActivity.class));
            }
        });
        SearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchEcommerce.class);
                startActivityForResult(intent, 1);
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchEcommerce.class);
                startActivityForResult(intent, 1);
            }
        });

        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentCategory fragment = new ParentCategory();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        EHomeActivity.locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinDialog();
            }
        });

        BrandNameList=new ArrayList<>();
        BrandIdList=new ArrayList<>();

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        if (TextUtils.isEmpty(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LAT))){
            showPinDialog();
        }else {
            if (isNetworkAvailable()){
                getHomeCategory();
            }else {
                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),"No Internet Connection!",true);
            }
        }
    }

    @Override
    public void onCtategory(int position) {
        SearchProductFragment fragment = new SearchProductFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {

                    if (!TextUtils.isEmpty(data.getStringExtra("search"))){
                        Bundle args = new Bundle();
                        args.putString("search", data.getStringExtra("search"));

                        SearchProductFragment fragment = new SearchProductFragment();
                        fragment.setArguments(args);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    // TODO Update your TextView.
                }
                break;

        }
    }

    @Override
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
               getActivity(). finish();

                break;

            case R.id.b_wallet_ecomm:
                startActivity(new Intent(getActivity(), WalletActivity.class));

                break;

            case R.id.b_qrcode_ecomm:
                startActivity(new Intent(getActivity(), QrCodeScanner.class));

                break;

            case R.id.b_mall_ecomm:
                //   Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(EHomeActivity.this, EHomeActivity.class));

                break;
        }
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*private void getAllParentCategory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAllParentCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ParentCategoriesResponse>() {
                    @Override
                    public void onSuccess(ParentCategoriesResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            if (response.getCategories().size()>0){

                                ParentCategoryAdapter adapter=new ParentCategoryAdapter(getActivity(),response.getCategories(),HomeFragment.this,"Home");
                                recyclerCategory.setAdapter(adapter);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),"No Category Found!",true); }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.homeEcommLayout), true, e);
                    }
                }));
    }*/
    /*@Override
    public void onCategory(int position, ParentCategoriesResponse.CategoriesBean categoriesBean) {

        Toast.makeText(getActivity(), categoriesBean.getCat_name(), Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString("CatId", String.valueOf(categoriesBean.getId()));
        SearchProductFragment fragment = new SearchProductFragment();
        fragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
*/

    private void getHomeCategory() {
        CatNameList=new ArrayList<>();
        CatIdList=new ArrayList<>();

        String Lat=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LAT);
        String Long=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LONG);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAllCategoryHome(Lat,Long)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HomeCatResponse>() {
                    @Override
                    public void onSuccess(HomeCatResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            tvCartBadge.setText(String.valueOf(response.getData().getTotal_carts()));
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().CART_BADGE,String.valueOf(response.getData().getTotal_carts()));
                            if (response.getData().getCategories().size()>0){
                                ParentCategoryAdapter adapter=new ParentCategoryAdapter(getActivity(),response.getData().getCategories(),HomeFragment.this,"Home");
                                recyclerCategory.setAdapter(adapter);

                                try {
                                    for (int k=0;response.getData().getCategories().size()>k;k++){
                                        CatNameList.add(response.getData().getCategories().get(k).getCat_name());
                                        CatIdList.add(""+response.getData().getCategories().get(k).getId());
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            if (response.getData().getProducts_offer().size()>0){
                                OfferLayout.setVisibility(View.VISIBLE);
                                offerAdapter = new OfferAdapter(getActivity(),response.getData().getProducts_offer(),HomeFragment.this);
                                recycleCategoryOfferList.setAdapter(offerAdapter);
                            }else {
                                OfferLayout.setVisibility(View.GONE);
                            }

                            if (response.getData().getProducts_trending().size()>0){
                                trendingAdapter = new TrendingAdapter(getActivity(),response.getData().getProducts_trending(),HomeFragment.this);
                                recycleTrendingProductList.setAdapter(trendingAdapter);
                            }

                            if (response.getData().getProducts_recommended().size()>0){
                                recommendedAdapter = new RecommendedAdapter(getActivity(),response.getData().getProducts_recommended(),HomeFragment.this);
                                recycleRecommendList.setAdapter(recommendedAdapter); }

                            ecommPagerAdapter = new EcommPagerAdapter(getActivity(),response.getData().getBanners(),HomeFragment.this);
                            viewpager.setAdapter(ecommPagerAdapter);
                            NumPage= response.getData().getBanners().size();
                            if (NumPage>1){
                                tabLayoutt.setupWithViewPager(viewpager, true);
                            }
                            else {
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),"No Category Found!",true);
                            }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.homeEcommLayout), true, e);
                    }
                }));
    }

    private void getLangLong(String pincode) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiServiceEcom.getLangLong(pincode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LatLongResponse>(){
                    @Override
                    public void onSuccess(LatLongResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()){
                            Longitude=response.getData().getLongt();
                            Latitude=response.getData().getLatt();

                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ECOMM_LAT,Latitude);
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ECOMM_LONG,Longitude);

                            if (isNetworkAvailable()){
                                getHomeCategory();
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),"No Internet Connection!",true);
                            }
                        }else {
                            showPinDialog();
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        showPinDialog();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.homeEcommLayout), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onCategory(int position, HomeCatResponse.DataBean.CategoriesBean categoriesBean) {
        Toast.makeText(getActivity(), categoriesBean.getCat_name(), Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString("CatId", String.valueOf(categoriesBean.getId()));
        SearchProductFragment fragment = new SearchProductFragment();
        fragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void pgerItemListener(int position, HomeCatResponse.DataBean.BannersBean mBannerId) {
        Toast.makeText(getActivity(),String.valueOf(position),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        if (mBannerId.getAppCatUrl().equals("")){
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PRODUCT_ID,mBannerId.getAppProductUrl());
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,"");}
        else if (mBannerId.getAppProductUrl().equals("")){
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PRODUCT_ID,mBannerId.getAppCatUrl());
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,"");}
        startActivity(intent); }


    @Override
    public void onRecommended(int position, HomeCatResponse.DataBean.ProductsRecommendedBean productsRecommendedBean) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PRODUCT_ID,String.valueOf(productsRecommendedBean.getId()));
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,"");
        startActivity(intent);
    }

    @Override
    public void onTrending(int position, HomeCatResponse.DataBean.ProductsTrendingBean productsTrendingBean) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PRODUCT_ID,String.valueOf(productsTrendingBean.getId()));
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,"");
        startActivity(intent);
    }


    @Override
    public void onPOffer(int position, HomeCatResponse.DataBean.ProductsOfferBean productsOfferBean) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PRODUCT_ID,String.valueOf(productsOfferBean.getProduct_id()));
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,String.valueOf(productsOfferBean.getOffer_id()));
        startActivity(intent);
    }

    private void showPinDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dialog_pin);
        dialog.setCancelable(false);

        TextView tvLocation , tvSerch;
        ImageView imCross;
        imCross = dialog.findViewById(R.id.imCross);
        tvLocation = dialog.findViewById(R.id.tv_getLocation);
        tvSerch = dialog.findViewById(R.id.tv_search_pin);
        etSearchEcomm = dialog.findViewById(R.id.tv_search_ecomm);

        imCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LAT))){
                    Toast.makeText(getActivity(), "Please Enter Your Area Code", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.dismiss();
                }

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
                if (TextUtils.isEmpty(etSearchEcomm.getText().toString().trim())){
                    Toast.makeText(getActivity(), "Please Enter Area Pincode", Toast.LENGTH_SHORT).show();
                }else {
                    if (isNetworkAvailable()){
                        getLangLong(etSearchEcomm.getText().toString().trim());
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
                    }
                }

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
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
        LocationManager locationManager;
        try {
            loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != location && null != providerList && providerList.size() > 0) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();


            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
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
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}


