package com.safepayu.wallet.ecommerce.fragment;


import android.os.Bundle;

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
import com.safepayu.wallet.ecommerce.adapter.CategoryAdapter;
import com.safepayu.wallet.ecommerce.adapter.EcommPagerAdapter;
import com.safepayu.wallet.ecommerce.adapter.OfferAdapter;
import com.safepayu.wallet.ecommerce.adapter.RecommendedAdapter;
import com.safepayu.wallet.ecommerce.adapter.TrendingAdapter;
import android.os.Handler;
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
    int images[] = {R.drawable.ecommvirwpager_banner, R.drawable.banner_image2, R.drawable.banner_image3};
    int NumPage,CurrentP=0 ;
    Timer timer;  final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home2, container, false);
        findId(view);
        return  view;
    }

    private void findId(View view) {
        recyclerCategory = view.findViewById(R.id.recycle_category_list);
        recycleCategoryOfferList = view.findViewById(R.id.recycle_category_offer_list);
        recycleTrendingProductList = view.findViewById(R.id.recycle_trending_product_list);
        recycleRecommendList = view.findViewById(R.id.recycle_recommend_list);
        viewpager = view.findViewById(R.id.viewpager_ecom);

        gridLayoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.HORIZONTAL,false);
        recyclerCategory.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(getActivity(),HomeFragment.this);
        recyclerCategory.setAdapter(categoryAdapter);

        recycleCategoryOfferList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        offerAdapter = new OfferAdapter(getActivity());
        recycleCategoryOfferList.setAdapter(offerAdapter);

        recycleTrendingProductList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trendingAdapter = new TrendingAdapter(getActivity());
        recycleTrendingProductList.setAdapter(trendingAdapter);

        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        recycleRecommendList.setLayoutManager(gridLayoutManager);
        recommendedAdapter = new RecommendedAdapter(getActivity());
        recycleRecommendList.setAdapter(recommendedAdapter);

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
    }

    @Override
    public void onCtategory(int position) {
        SearchProductFragment fragment = new SearchProductFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }
}
