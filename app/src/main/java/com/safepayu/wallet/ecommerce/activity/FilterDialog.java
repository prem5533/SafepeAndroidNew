package com.safepayu.wallet.ecommerce.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.bus.BusSourcesAdapter;
import com.safepayu.wallet.ecommerce.adapter.FiltereListAdapter;
import com.safepayu.wallet.ecommerce.adapter.ProductSizeAdapter;

import java.util.ArrayList;

public class FilterDialog  extends Activity implements BusSourcesAdapter.LocationListListener {

    private LinearLayout PriceLayout,CategoryLayout,BrandLayout,DiscountLayout,SizeLayout;
    private RecyclerView PriceRecyclerView,CategoryRecyclerView,BrandRecyclerView,DiscountRecyclerView,SizeRecyclerView;
    private ImageView PriceIV,CategoryIV,BrandIV,DiscountIV,SizeIV;
    private int PriceInt=0,CategoryInt=0,BrandInt=0,DiscountInt=0,SizeInt=0;
    private Button FilterBtn;
    private ArrayList<String> BusSourcesList=new ArrayList<>();
    private TextView tvCategory,tvSize,tvPrice,tvBrand,tvDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_dilalog_);
//        this.setFinishOnTouchOutside(false);

     //   FilterBtn=findViewById(R.id.filterBtn_filterLayout);

        PriceLayout=findViewById(R.id.priceLayout_filterLayout);
        CategoryLayout=findViewById(R.id.categoryLayout_filterLayout);
        BrandLayout=findViewById(R.id.brandLayout_filterLayout);
        DiscountLayout=findViewById(R.id.discountLayout_filterLayout);
        SizeLayout=findViewById(R.id.sizeLayout_filterLayout);

        tvCategory = findViewById(R.id.tv_category);
        tvSize = findViewById(R.id.tv_size);
        tvPrice = findViewById(R.id.tv_price);
        tvBrand = findViewById(R.id.tv_brand);
        tvDiscount = findViewById(R.id.tv_discount);

        PriceRecyclerView=findViewById(R.id.recyclePrice_filterLayout);
        CategoryRecyclerView=findViewById(R.id.recycleCategory_filterLayout);
        BrandRecyclerView=findViewById(R.id.recycleBrand_filterLayout);
        DiscountRecyclerView=findViewById(R.id.recycleDiscount_filterLayout);
        SizeRecyclerView=findViewById(R.id.recycleSize_filterLayout);

        PriceIV=findViewById(R.id.downArrowPrice);
        CategoryIV=findViewById(R.id.downArrowCategory);
        BrandIV=findViewById(R.id.downArrowBrand);
        DiscountIV=findViewById(R.id.downArrowDiscount);
        SizeIV=findViewById(R.id.downArrowSize);

        PriceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        PriceRecyclerView.setNestedScrollingEnabled(false);

        CategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        CategoryRecyclerView.setNestedScrollingEnabled(false);

        BrandRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        BrandRecyclerView.setNestedScrollingEnabled(false);

        DiscountRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DiscountRecyclerView.setNestedScrollingEnabled(false);

        SizeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SizeRecyclerView.setNestedScrollingEnabled(false);

       /* filtereListAdapter.add("delhi");
        filtereListAdapter.add("goa");
        filtereListAdapter.add("bihar");*/

        FiltereListAdapter filtereListAdapter = new FiltereListAdapter(getApplicationContext(),BusSourcesList);
        PriceRecyclerView.setAdapter(filtereListAdapter);
        CategoryRecyclerView.setAdapter(filtereListAdapter);
        BrandRecyclerView.setAdapter(filtereListAdapter);
        DiscountRecyclerView.setAdapter(filtereListAdapter);
        SizeRecyclerView.setAdapter(filtereListAdapter);


        PriceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PriceInt==0){
                    PriceInt=1;
                    SizeInt=0;
                    DiscountInt=0;
                    BrandInt=0;
                    CategoryInt=0;

                    PriceRecyclerView.setVisibility(View.VISIBLE);
                    CategoryRecyclerView.setVisibility(View.GONE);
                    BrandRecyclerView.setVisibility(View.GONE);
                    DiscountRecyclerView.setVisibility(View.GONE);
                    SizeRecyclerView.setVisibility(View.GONE);

                    tvDiscount.setTextColor(getResources().getColor(R.color.textcolor));
                    tvBrand.setTextColor(getResources().getColor(R.color.textcolor));
                    tvCategory.setTextColor(getResources().getColor(R.color.textcolor));
                    tvSize.setTextColor(getResources().getColor(R.color.textcolor));

                    tvPrice.setTextColor(getResources().getColor(R.color.red_theme));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_arrow_filter));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                }else {
                    hideLayout();
                }
            }
        });

        CategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CategoryInt==0){
                    CategoryInt=1;
                    SizeInt=0;
                    DiscountInt=0;
                    BrandInt=0;
                    PriceInt=0;

                    PriceRecyclerView.setVisibility(View.GONE);
                    CategoryRecyclerView.setVisibility(View.VISIBLE);
                    BrandRecyclerView.setVisibility(View.GONE);
                    DiscountRecyclerView.setVisibility(View.GONE);
                    SizeRecyclerView.setVisibility(View.GONE);

                    tvDiscount.setTextColor(getResources().getColor(R.color.textcolor));
                    tvBrand.setTextColor(getResources().getColor(R.color.textcolor));
                    tvPrice.setTextColor(getResources().getColor(R.color.textcolor));
                    tvSize.setTextColor(getResources().getColor(R.color.textcolor));

                    tvCategory.setTextColor(getResources().getColor(R.color.red_theme));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_arrow_filter));
//                    CategoryLayout.setBackgroundColor(Color.parseColor("#f4f5ff"));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                }else {
                    hideLayout();
                }
            }
        });

        BrandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BrandInt==0){
                    BrandInt=1;
                    SizeInt=0;
                    DiscountInt=0;
                    CategoryInt=0;
                    PriceInt=0;

                    PriceRecyclerView.setVisibility(View.GONE);
                    CategoryRecyclerView.setVisibility(View.GONE);
                    BrandRecyclerView.setVisibility(View.VISIBLE);
                    DiscountRecyclerView.setVisibility(View.GONE);
                    SizeRecyclerView.setVisibility(View.GONE);

                    tvDiscount.setTextColor(getResources().getColor(R.color.textcolor));
                    tvCategory.setTextColor(getResources().getColor(R.color.textcolor));
                    tvPrice.setTextColor(getResources().getColor(R.color.textcolor));
                    tvSize.setTextColor(getResources().getColor(R.color.textcolor));

                    tvBrand.setTextColor(getResources().getColor(R.color.red_theme));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_arrow_filter));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                }else {
                    hideLayout();
                }
            }
        });

        DiscountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DiscountInt==0){
                    DiscountInt=1;
                    SizeInt=0;
                    BrandInt=0;
                    CategoryInt=0;
                    PriceInt=0;

                    PriceRecyclerView.setVisibility(View.GONE);
                    CategoryRecyclerView.setVisibility(View.GONE);
                    BrandRecyclerView.setVisibility(View.GONE);
                    DiscountRecyclerView.setVisibility(View.VISIBLE);
                    SizeRecyclerView.setVisibility(View.GONE);

                    tvBrand.setTextColor(getResources().getColor(R.color.textcolor));
                    tvCategory.setTextColor(getResources().getColor(R.color.textcolor));
                    tvPrice.setTextColor(getResources().getColor(R.color.textcolor));
                    tvSize.setTextColor(getResources().getColor(R.color.textcolor));

                    tvDiscount.setTextColor(getResources().getColor(R.color.red_theme));
                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_arrow_filter));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                }else {
                    hideLayout();
                }
            }
        });

        SizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SizeInt==0){
                    SizeInt=1;
                    DiscountInt=0;
                    BrandInt=0;
                    CategoryInt=0;
                    PriceInt=0;

                    PriceRecyclerView.setVisibility(View.GONE);
                    CategoryRecyclerView.setVisibility(View.GONE);
                    BrandRecyclerView.setVisibility(View.GONE);
                    DiscountRecyclerView.setVisibility(View.GONE);
                    SizeRecyclerView.setVisibility(View.VISIBLE);

                    tvBrand.setTextColor(getResources().getColor(R.color.textcolor));
                    tvCategory.setTextColor(getResources().getColor(R.color.textcolor));
                    tvPrice.setTextColor(getResources().getColor(R.color.textcolor));
                    tvDiscount.setTextColor(getResources().getColor(R.color.textcolor));

                    tvSize.setTextColor(getResources().getColor(R.color.red_theme));
                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_arrow_filter));

                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                }else {
                    hideLayout();
                }
            }
        });

        /*FilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
    }

    private void hideLayout(){
        PriceRecyclerView.setVisibility(View.GONE);
        CategoryRecyclerView.setVisibility(View.GONE);
        BrandRecyclerView.setVisibility(View.GONE);
        DiscountRecyclerView.setVisibility(View.GONE);
        SizeRecyclerView.setVisibility(View.GONE);

        SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
        DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
        BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
        PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
        CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));

        tvBrand.setTextColor(getResources().getColor(R.color.textcolor));
        tvCategory.setTextColor(getResources().getColor(R.color.textcolor));
        tvPrice.setTextColor(getResources().getColor(R.color.textcolor));
        tvDiscount.setTextColor(getResources().getColor(R.color.textcolor));
        tvSize.setTextColor(getResources().getColor(R.color.textcolor));

        SizeInt=0;
        DiscountInt=0;
        BrandInt=0;
        CategoryInt=0;
        PriceInt=0;
    }

    @Override
    public void onLocationClickFrom(int position, ArrayList<String> Buslist) {

    }

    @Override
    public void onLocationClickTo(int position, ArrayList<String> Buslist) {

    }
}
