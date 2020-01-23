package com.safepayu.wallet.ecommerce.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.FilterLisCategoryAdapter;
import com.safepayu.wallet.ecommerce.adapter.FilterListBrandAdapter;
import com.safepayu.wallet.ecommerce.adapter.FilterListDiscountAdapter;
import com.safepayu.wallet.ecommerce.adapter.FilterListPriceAdapter;
import com.safepayu.wallet.ecommerce.adapter.FilterListSizeAdapter;
import com.safepayu.wallet.ecommerce.fragment.SearchProductFragment;
import com.safepayu.wallet.ecommerce.fragment.ShopDetailFragment;

import java.util.ArrayList;
import java.util.List;

import static com.safepayu.wallet.ecommerce.fragment.HomeFragment.BrandIdList;
import static com.safepayu.wallet.ecommerce.fragment.HomeFragment.BrandNameList;
import static com.safepayu.wallet.ecommerce.fragment.HomeFragment.CatIdList;
import static com.safepayu.wallet.ecommerce.fragment.HomeFragment.CatNameList;

public class FilterDialog  extends Activity implements FilterListPriceAdapter.OnFilterListPriceListener,FilterListSizeAdapter.OnFilterListSizeListener,
                        FilterListBrandAdapter.OnFilterListBrandListener,FilterLisCategoryAdapter.OnFilterListCategoryListener,
                        FilterListDiscountAdapter.OnFilterListDiscountListener {

    private LinearLayout PriceLayout,CategoryLayout,BrandLayout,DiscountLayout,SizeLayout;
    private RecyclerView CategoryRecyclerView,BrandRecyclerView,DiscountRecyclerView,SizeRecyclerView;
    private ImageView PriceIV,CategoryIV,BrandIV,DiscountIV,SizeIV;
    private int PriceInt=0,CategoryInt=0,BrandInt=0,DiscountInt=0,SizeInt=0;
    private Button FilterBtn;
    private ArrayList<String> DiscountList,PriceList,SizeList;
    private TextView tvCategory,tvSize,tvPrice,tvBrand,tvDiscount,ApplyBtn,CloseBtn;
    private List<String> brand_id,category_id,size,price,discount;
    private AppCompatSeekBar seekBar;
    private String Class="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_dilalog_);
//        this.setFinishOnTouchOutside(false);

     //   FilterBtn=findViewById(R.id.filterBtn_filterLayout);


        brand_id=new ArrayList<>();
        category_id=new ArrayList<>();
        size=new ArrayList<>();
        price=new ArrayList<>();
        discount=new ArrayList<>();

        try {
            Class=getIntent().getStringExtra("Class");
        }catch (Exception e){
            e.printStackTrace();
        }

        seekBar=findViewById(R.id.seekbarPrice_filterLayout);
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
        ApplyBtn = findViewById(R.id.tv_filter_apply);
        CloseBtn = findViewById(R.id.tv_filter_close);

        //PriceRecyclerView=findViewById(R.id.recyclePrice_filterLayout);
        CategoryRecyclerView=findViewById(R.id.recycleCategory_filterLayout);
        BrandRecyclerView=findViewById(R.id.recycleBrand_filterLayout);
        DiscountRecyclerView=findViewById(R.id.recycleDiscount_filterLayout);
        SizeRecyclerView=findViewById(R.id.recycleSize_filterLayout);

        PriceIV=findViewById(R.id.downArrowPrice);
        CategoryIV=findViewById(R.id.downArrowCategory);
        BrandIV=findViewById(R.id.downArrowBrand);
        DiscountIV=findViewById(R.id.downArrowDiscount);
        SizeIV=findViewById(R.id.downArrowSize);

//        PriceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        PriceRecyclerView.setNestedScrollingEnabled(false);

        CategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        CategoryRecyclerView.setNestedScrollingEnabled(false);

        BrandRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        BrandRecyclerView.setNestedScrollingEnabled(false);

        DiscountRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DiscountRecyclerView.setNestedScrollingEnabled(false);

        SizeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SizeRecyclerView.setNestedScrollingEnabled(false);

        FilterLisCategoryAdapter filterLisCategoryAdapter = new FilterLisCategoryAdapter(getApplicationContext(),CatNameList,CatIdList,FilterDialog.this);
        CategoryRecyclerView.setAdapter(filterLisCategoryAdapter);

        DiscountList=new ArrayList<>();
        DiscountList.add("0-10");
        DiscountList.add("11-34");
        DiscountList.add("35-49");
        DiscountList.add("50-100");
        FilterListDiscountAdapter filterListDiscountAdapter = new FilterListDiscountAdapter(getApplicationContext(),DiscountList,FilterDialog.this);
        DiscountRecyclerView.setAdapter(filterListDiscountAdapter);

        PriceList=new ArrayList<>();
        PriceList.add("1000");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setThumb(getThumb(progress));
                price.clear();
                price.add("0");
                price.add(""+progress);
            }
        });

//        FilterListPriceAdapter filterListPriceAdapter= new FilterListPriceAdapter(getApplicationContext(),PriceList,FilterDialog.this);
//        PriceRecyclerView.setAdapter(filterListPriceAdapter);

        SizeList=new ArrayList<>();
        SizeList.add("S");
        SizeList.add("M");
        SizeList.add("L");
        SizeList.add("XL");
        FilterListSizeAdapter filterListSizeAdapter = new FilterListSizeAdapter(getApplicationContext(),SizeList,FilterDialog.this);
        SizeRecyclerView.setAdapter(filterListSizeAdapter);

        FilterListBrandAdapter filterListBrandAdapter = new FilterListBrandAdapter(getApplicationContext(),BrandNameList,BrandIdList,FilterDialog.this);
        BrandRecyclerView.setAdapter(filterListBrandAdapter);

        PriceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PriceInt==0){
                    PriceInt=1;
                    SizeInt=0;
                    DiscountInt=0;
                    BrandInt=0;
                    CategoryInt=0;

                    seekBar.setVisibility(View.VISIBLE);
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

                    seekBar.setVisibility(View.GONE);
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

                    seekBar.setVisibility(View.GONE);
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

                    seekBar.setVisibility(View.GONE);
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

                    seekBar.setVisibility(View.GONE);
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

        ApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=null;
                if (Class.equalsIgnoreCase("Product")){
                    intent=new Intent(FilterDialog.this, SearchProductFragment.class);
                }else {
                    intent=new Intent(FilterDialog.this, ShopDetailFragment.class);
                }

                intent.putStringArrayListExtra("brand_id",(ArrayList<String>) brand_id);
                intent.putStringArrayListExtra("category_id",(ArrayList<String>) category_id);
                intent.putStringArrayListExtra("size",(ArrayList<String>) size);
                intent.putStringArrayListExtra("price",(ArrayList<String>) price);
                intent.putStringArrayListExtra("discount",(ArrayList<String>) brand_id);
                setResult(2, intent);
                finish();
            }
        });

        CloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public Drawable getThumb(int progress) {
        View thumbView = LayoutInflater.from(FilterDialog.this).inflate(R.layout.layout_seekbar_thumb, null, false);
        ((TextView) thumbView.findViewById(R.id.tvProgress)).setText(progress + "");

        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        thumbView.layout(0, 0, thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight());
        thumbView.draw(canvas);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void hideLayout(){
        seekBar.setVisibility(View.GONE);
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
    public void onFilterListPrice(ArrayList<String> priceList) {
        price.clear();
        price=priceList;
    }

    @Override
    public void onFilterListSize(ArrayList<String> sizeList) {
        size.clear();
        size=sizeList;
    }

    @Override
    public void onFilterListBrand(ArrayList<String> brandList) {
        brand_id.clear();
        brand_id=brandList;
    }

    @Override
    public void onFilterListCategory(ArrayList<String> categoryList) {
        category_id.clear();
        category_id=categoryList;
    }

    @Override
    public void onFilterListDiscount(ArrayList<String> discountList) {
        discount.clear();
        discount=discountList;
    }
}
