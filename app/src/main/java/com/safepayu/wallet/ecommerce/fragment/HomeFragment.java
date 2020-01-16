package com.safepayu.wallet.ecommerce.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.safepayu.wallet.activity.WalletActivity;
import com.safepayu.wallet.dialogs.LoadingDialog;
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
import com.safepayu.wallet.ecommerce.model.response.ParentCategoriesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryItemListener,
        BottomNavigationView.OnNavigationItemSelectedListener, ParentCategoryAdapter.OnCategoryItemListener {

    private RecyclerView recyclerCategory,recycleCategoryOfferList,recycleTrendingProductList,recycleRecommendList;
    private CategoryAdapter categoryAdapter;
    private OfferAdapter offerAdapter;
    private RecommendedAdapter recommendedAdapter;
    private TrendingAdapter trendingAdapter;
    private EcommPagerAdapter ecommPagerAdapter;
    BottomNavigationView bottomNavigation;
    GridLayoutManager gridLayoutManager;

    private ViewPager viewpager;
    int images[] = {R.drawable.banner_image1, R.drawable.banner_image2, R.drawable.banner_image3,R.drawable.banner_image4};
    int NumPage,CurrentP=0 ;
    Timer timer;  final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    private LinearLayout SearchLayout;
    private TextView tvSearch,tvViewAll;
    private ArrayList<String> ProductNameList,ProductImageList;
    private ArrayList<String> TrendingNameList,TrendingImageList;
    private ArrayList<String> RecommendNameList,RecommendImageList;

    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private LoadingDialog loadingDialog;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home2, container, false);
        prepareCategoryData();
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
        tvViewAll = view.findViewById(R.id.viewAllCat_home);

        gridLayoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        recyclerCategory.setLayoutManager(gridLayoutManager);
//        categoryAdapter = new CategoryAdapter(getActivity(),categoryModelList,HomeFragment.this);
//        recyclerCategory.setAdapter(categoryAdapter);

        recycleCategoryOfferList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycleTrendingProductList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        recycleRecommendList.setLayoutManager(gridLayoutManager);

        ecommPagerAdapter = new EcommPagerAdapter(getActivity(),images);
        viewpager.setAdapter(ecommPagerAdapter);
        NumPage= images.length;

        TabLayout tabLayoutt = view.findViewById(R.id.tab_layout_ecom);
        if (NumPage>1){
            tabLayoutt.setupWithViewPager(viewpager, true);
        }

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

        ProductNameList=new ArrayList<>();
        ProductImageList=new ArrayList<>();
        RecommendNameList=new ArrayList<>();
        RecommendImageList=new ArrayList<>();
        TrendingNameList=new ArrayList<>();
        TrendingImageList=new ArrayList<>();

        ProductNameList.add("Shoes");
        ProductNameList.add("Shoes");
        ProductNameList.add("Shoes");
        ProductNameList.add("Shoes");

        ProductImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/4.png");
        ProductImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/3.png");
        ProductImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/2.png");
        ProductImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/1.png");

        offerAdapter = new OfferAdapter(getActivity(),ProductNameList,ProductImageList);
        recycleCategoryOfferList.setAdapter(offerAdapter);

        TrendingNameList.add("Men's T-Shirt");
        TrendingNameList.add("Jeans");
        TrendingNameList.add("Women's Top");
        TrendingNameList.add("Shoes");

        TrendingImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/8.png");
        TrendingImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/7.png");
        TrendingImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/6.png");
        TrendingImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/5.png");

        trendingAdapter = new TrendingAdapter(getActivity(),TrendingNameList,TrendingImageList);
        recycleTrendingProductList.setAdapter(trendingAdapter);

        RecommendNameList.add("Women's Fashion");
        RecommendNameList.add("Men's Casual");
        RecommendNameList.add("Men's Casual");
        RecommendNameList.add("Men's T-Shirt");

        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/12.png");
        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/11.png");
        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/10.png");
        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/9.png");

        recommendedAdapter = new RecommendedAdapter(getActivity(),RecommendNameList,RecommendImageList);
        recycleRecommendList.setAdapter(recommendedAdapter);

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

        if (isNetworkAvailable()){
            getAllParentCategory();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),"No Internet Connection!",true);
        }
    }

    private void prepareCategoryData() {
        categoryModelList.clear();
        CategoryModel catList = new CategoryModel("Food & Drinks");
        categoryModelList.add(catList);

        catList = new CategoryModel("Beauty");
        categoryModelList.add(catList);
        catList = new CategoryModel("Sports");
        categoryModelList.add(catList);
        catList = new CategoryModel("Jeans");
        categoryModelList.add(catList);
        catList = new CategoryModel("Grocery");
        categoryModelList.add(catList);
        catList = new CategoryModel("Fashion");
        categoryModelList.add(catList);
        catList = new CategoryModel("Men Grooming");
        categoryModelList.add(catList);
        catList = new CategoryModel("Video Games");
        categoryModelList.add(catList);
        catList = new CategoryModel("iPads");
        categoryModelList.add(catList);
        catList = new CategoryModel("Flower");
        categoryModelList.add(catList);
        catList = new CategoryModel("Furniture");
        categoryModelList.add(catList);
        catList = new CategoryModel("Jewellery");
        categoryModelList.add(catList);

        //categoryAdapter.notifyDataSetChanged();
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

    private void getAllParentCategory() {
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
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.homeEcommLayout),"No Category Found!",true);
                            }

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
    }

    @Override
    public void onCategory(int position, ParentCategoriesResponse.CategoriesBean categoriesBean) {

        Toast.makeText(getActivity(), categoriesBean.getCat_name(), Toast.LENGTH_SHORT).show();

        SearchProductFragment fragment = new SearchProductFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
