package com.safepayu.wallet.ecommerce.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.bus.BusSourcesAdapter;

import java.util.ArrayList;

public class FilterDialog  extends Activity implements BusSourcesAdapter.LocationListListener {

    private LinearLayout PriceLayout,CategoryLayout,BrandLayout,DiscountLayout,SizeLayout;
    private RecyclerView PriceRecyclerView,CategoryRecyclerView,BrandRecyclerView,DiscountRecyclerView,SizeRecyclerView;
    private ImageView PriceIV,CategoryIV,BrandIV,DiscountIV,SizeIV;
    private int PriceInt=0,CategoryInt=0,BrandInt=0,DiscountInt=0,SizeInt=0;

    private ArrayList<String> BusSourcesList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_dialog);
        this.setFinishOnTouchOutside(false);

        PriceLayout=findViewById(R.id.priceLayout_filterLayout);
        CategoryLayout=findViewById(R.id.categoryLayout_filterLayout);
        BrandLayout=findViewById(R.id.brandLayout_filterLayout);
        DiscountLayout=findViewById(R.id.discountLayout_filterLayout);
        SizeLayout=findViewById(R.id.sizeLayout_filterLayout);

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

        BusSourcesList.add("delhi");
        BusSourcesList.add("goa");
        BusSourcesList.add("bihar");

        BusSourcesAdapter busSourcesAdapter = new BusSourcesAdapter(getApplicationContext(),BusSourcesList, FilterDialog.this,"from");
        PriceRecyclerView.setAdapter(busSourcesAdapter);
        CategoryRecyclerView.setAdapter(busSourcesAdapter);
        BrandRecyclerView.setAdapter(busSourcesAdapter);
        DiscountRecyclerView.setAdapter(busSourcesAdapter);
        SizeRecyclerView.setAdapter(busSourcesAdapter);


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

                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_new));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
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

                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_new));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
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

                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_new));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
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

                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_new));

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
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

                    SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_new));

                    DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                    CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
                }else {
                    hideLayout();
                }
            }
        });
    }

    private void hideLayout(){
        PriceRecyclerView.setVisibility(View.GONE);
        CategoryRecyclerView.setVisibility(View.GONE);
        BrandRecyclerView.setVisibility(View.GONE);
        DiscountRecyclerView.setVisibility(View.GONE);
        SizeRecyclerView.setVisibility(View.GONE);

        SizeIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
        DiscountIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
        BrandIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
        PriceIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));
        CategoryIV.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow_new));

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
