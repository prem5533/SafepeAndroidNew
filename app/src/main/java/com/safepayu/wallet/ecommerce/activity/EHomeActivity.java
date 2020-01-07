package com.safepayu.wallet.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.CategoryAdapter;
import com.safepayu.wallet.ecommerce.adapter.EcommPagerAdapter;
import com.safepayu.wallet.ecommerce.adapter.OfferAdapter;
import com.safepayu.wallet.ecommerce.adapter.RecommendedAdapter;
import com.safepayu.wallet.ecommerce.adapter.TrendingAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EHomeActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryItemListener {
    private RecyclerView recyclerCategory,recycleCategoryOfferList,recycleTrendingProductList,recycleRecommendList;
    private CategoryAdapter categoryAdapter;
    private OfferAdapter offerAdapter;
    private RecommendedAdapter recommendedAdapter;
    private TrendingAdapter trendingAdapter;
    private EcommPagerAdapter ecommPagerAdapter;
    GridLayoutManager gridLayoutManager;
    private ViewPager viewpager;
    int images[] = {R.drawable.ecommvirwpager_banner, R.drawable.banner_image2, R.drawable.banner_image3};
    int NumPage,CurrentP=0 ;
    Timer timer;  final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ehome);

        findId();
    }

    private void findId() {
        recyclerCategory = findViewById(R.id.recycle_category_list);
        recycleCategoryOfferList = findViewById(R.id.recycle_category_offer_list);
        recycleTrendingProductList = findViewById(R.id.recycle_trending_product_list);
        recycleRecommendList = findViewById(R.id.recycle_recommend_list);
        viewpager = findViewById(R.id.viewpager_ecom);

         gridLayoutManager = new GridLayoutManager(getApplicationContext(),3, LinearLayoutManager.HORIZONTAL,false);
        recyclerCategory.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(getApplicationContext(),EHomeActivity.this);
        recyclerCategory.setAdapter(categoryAdapter);

        recycleCategoryOfferList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        offerAdapter = new OfferAdapter(getApplicationContext());
        recycleCategoryOfferList.setAdapter(offerAdapter);

        recycleTrendingProductList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingAdapter = new TrendingAdapter(getApplicationContext());
        recycleTrendingProductList.setAdapter(trendingAdapter);

         gridLayoutManager = new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false);
        recycleRecommendList.setLayoutManager(gridLayoutManager);
        recommendedAdapter = new RecommendedAdapter(getApplicationContext());
        recycleRecommendList.setAdapter(recommendedAdapter);

        ecommPagerAdapter = new EcommPagerAdapter(this,images);
        viewpager.setAdapter(ecommPagerAdapter);
        NumPage= images.length;
        TabLayout tabLayoutt = findViewById(R.id.tab_layout_ecom);
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

    }

    @Override
    public void onCtategory(int position) {
       startActivity(new Intent(EHomeActivity.this, SearchProductActivity.class));
    }
}
