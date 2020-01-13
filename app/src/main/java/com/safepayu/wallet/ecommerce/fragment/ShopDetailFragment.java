package com.safepayu.wallet.ecommerce.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.activity.FilterDialog;
import com.safepayu.wallet.ecommerce.activity.SearchEcommerce;
import com.safepayu.wallet.ecommerce.adapter.OpeningHoursAdapter;
import com.safepayu.wallet.ecommerce.adapter.RecommendedAdapter;

import java.util.ArrayList;

public class ShopDetailFragment extends Fragment {

    private RecyclerView HoursRecyclerView,ProductsRecyclerView;
    private LinearLayout HoursLayout;
    private ImageView HoursIV;
    private TextView tvShopName,tvSearch;
    private int HoursInt=0;
    private RecommendedAdapter recommendedAdapter;
    private OpeningHoursAdapter openingHoursAdapter;
    private ArrayList<String> HoursList,DaysList;
    private LinearLayout SearchLayout,FilterLayout;
    private ArrayList<String> RecommendNameList,RecommendImageList;

    public ShopDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.shop_detail_fragment, container, false);
        findId(view);
        return  view;
    }

    private void findId(View view) {
        tvShopName=view.findViewById(R.id.shopName_shopDetail);
        HoursRecyclerView=view.findViewById(R.id.recycleHours_shopDetail);
        HoursLayout=view.findViewById(R.id.openingHours_shopDetail);
        HoursIV=view.findViewById(R.id.downArrowHours_shopDetail);
        ProductsRecyclerView=view.findViewById(R.id.recycleProducts_shopDetail);
        FilterLayout=view.findViewById(R.id.filter_shopDetail);
        SearchLayout = view.findViewById(R.id.searchLayout_Shop);
        tvSearch =  view.findViewById(R.id.tv_search_ecomm);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        ProductsRecyclerView.setLayoutManager(gridLayoutManager);

        RecommendNameList=new ArrayList<>();
        RecommendImageList=new ArrayList<>();

        RecommendNameList.add("Women's Fashion");
        RecommendNameList.add("Men's Casual");
        RecommendNameList.add("Men's Casual");
        RecommendNameList.add("Men's T-Shirt");

        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/12.png");
        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/11.png");
        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/10.png");
        RecommendImageList.add("https://secure.safepeindia.com//uploaded/ecomImages/9.png");

        recommendedAdapter = new RecommendedAdapter(getActivity(),RecommendNameList,RecommendImageList);
        ProductsRecyclerView.setAdapter(recommendedAdapter);

        HoursList=new ArrayList<>();
        DaysList=new ArrayList<>();

        DaysList.add("Monday");
        DaysList.add("Tuesday");
        DaysList.add("Wednesday");
        DaysList.add("Thrusday");
        DaysList.add("Friday");

        HoursList.add("06:15 - 20:30");
        HoursList.add("06:15 - 20:30");
        HoursList.add("06:15 - 20:30");
        HoursList.add("06:15 - 20:30");
        HoursList.add("06:15 - 20:30");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvShopName.setLetterSpacing(0.1f);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        GridLayoutManager gridLayoutManagerHours = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        HoursRecyclerView.setLayoutManager(gridLayoutManagerHours);
        openingHoursAdapter = new OpeningHoursAdapter(getActivity(),DaysList,HoursList);
        HoursRecyclerView.setAdapter(openingHoursAdapter);

        HoursLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HoursInt==0){
                    HoursInt=1;
                    HoursRecyclerView.setVisibility(View.VISIBLE);

                    HoursIV.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_new));
                }else {
                    HoursInt=0;
                    HoursRecyclerView.setVisibility(View.GONE);
                    HoursIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                }
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

        FilterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FilterDialog.class);
                startActivity(intent);
            }
        });
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
