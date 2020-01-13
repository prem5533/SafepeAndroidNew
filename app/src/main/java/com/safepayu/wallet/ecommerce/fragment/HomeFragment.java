package com.safepayu.wallet.ecommerce.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.AddUpdateAddress;
import com.safepayu.wallet.activity.MapsActivity;
import com.safepayu.wallet.ecommerce.activity.SearchEcommerce;
import com.safepayu.wallet.ecommerce.adapter.CategoryAdapter;
import com.safepayu.wallet.ecommerce.adapter.EcommPagerAdapter;
import com.safepayu.wallet.ecommerce.adapter.OfferAdapter;
import com.safepayu.wallet.ecommerce.adapter.RecommendedAdapter;
import com.safepayu.wallet.ecommerce.adapter.TrendingAdapter;
import com.safepayu.wallet.ecommerce.model.CategoryModel;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryItemListener {
    private RecyclerView recyclerCategory,recycleCategoryOfferList,recycleTrendingProductList,recycleRecommendList;
    private CategoryAdapter categoryAdapter;
    private OfferAdapter offerAdapter;
    private RecommendedAdapter recommendedAdapter;
    private TrendingAdapter trendingAdapter;
    private EcommPagerAdapter ecommPagerAdapter;
    GridLayoutManager gridLayoutManager;
    private ViewPager viewpager;
    int images[] = {R.drawable.banner_image1, R.drawable.banner_image2, R.drawable.banner_image3,R.drawable.banner_image4};
    int NumPage,CurrentP=0 ;
    Timer timer;  final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    private LinearLayout SearchLayout;
    private TextView tvSearch;
    private ArrayList<String> ProductNameList,ProductImageList;
    private ArrayList<String> TrendingNameList,TrendingImageList;
    private ArrayList<String> RecommendNameList,RecommendImageList;

    private List<CategoryModel> categoryModelList = new ArrayList<>();
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
        recyclerCategory = view.findViewById(R.id.recycle_category_list);
        recycleCategoryOfferList = view.findViewById(R.id.recycle_category_offer_list);
        recycleTrendingProductList = view.findViewById(R.id.recycle_trending_product_list);
        recycleRecommendList = view.findViewById(R.id.recycle_recommend_list);
        viewpager = view.findViewById(R.id.viewpager_ecom);
        SearchLayout = view.findViewById(R.id.searchLayout_headerHome);
        tvSearch =  view.findViewById(R.id.tv_search_ecomm);

        gridLayoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        recyclerCategory.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(getActivity(),categoryModelList,HomeFragment.this);
        recyclerCategory.setAdapter(categoryAdapter);

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

                    Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();

                    // TODO Update your TextView.
                }
                break;

        }
    }

}
