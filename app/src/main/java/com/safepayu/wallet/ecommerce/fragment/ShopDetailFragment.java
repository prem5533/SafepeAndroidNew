package com.safepayu.wallet.ecommerce.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.OpeningHoursAdapter;
import com.safepayu.wallet.ecommerce.adapter.RecommendedAdapter;

import java.util.ArrayList;

public class ShopDetailFragment extends Fragment {

    private RecyclerView HoursRecyclerView,ProductsRecyclerView;
    private LinearLayout HoursLayout;
    private ImageView HoursIV;
    private TextView tvShopName;
    private int HoursInt=0;
    private RecommendedAdapter recommendedAdapter;
    private OpeningHoursAdapter openingHoursAdapter;
    private ArrayList<String> HoursList,DaysList;

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        ProductsRecyclerView.setLayoutManager(gridLayoutManager);
        recommendedAdapter = new RecommendedAdapter(getActivity());
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
                    HoursIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                }
            }
        });
    }

}
