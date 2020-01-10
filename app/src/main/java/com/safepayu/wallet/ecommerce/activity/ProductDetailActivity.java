package com.safepayu.wallet.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.EcommPagerAdapter;
import com.safepayu.wallet.ecommerce.adapter.ProductSizeAdapter;
import com.safepayu.wallet.ecommerce.fragment.CartFragment;
import com.safepayu.wallet.ecommerce.fragment.SearchProductFragment;


public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewpagerProductDetail;
    private EcommPagerAdapter ecommPagerAdapter;

    private ProductSizeAdapter productSizeAdapter;
    private RecyclerView productSizeList;
    int images[] = {R.drawable.girl, R.drawable.imge2, R.drawable.image3};
    int NumPage,CurrentP=0 ;
    private Button backBtnProductDetail;
    private TextView tvBuyNow,tvAddCart,tvActualPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        findId();
    }

    private void findId() {
        viewpagerProductDetail = findViewById(R.id.viewpager__product_detil);
        productSizeList = findViewById(R.id.product_size_list);
        backBtnProductDetail = findViewById(R.id.back_btn_product_detail);
        tvBuyNow = findViewById(R.id.tv_product_detail_buyNow);
        tvAddCart = findViewById(R.id.tv_product_detail_add_cart);
        tvActualPrice = findViewById(R.id.tv_product_detail_actualprice);

        backBtnProductDetail.setOnClickListener(this);
        tvBuyNow.setOnClickListener(this);
        tvAddCart.setOnClickListener(this);


        tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        ecommPagerAdapter = new EcommPagerAdapter(ProductDetailActivity.this,images);
        viewpagerProductDetail.setAdapter(ecommPagerAdapter);
        NumPage= images.length;
        TabLayout tabLayoutt = findViewById(R.id.tab_layout_productDetail);
        if (NumPage>1){
            tabLayoutt.setupWithViewPager(viewpagerProductDetail, true);
        }

        /*After setting the adapter use the timer type 3 */
        final Handler hand = new Handler();
        final Runnable Updt = new Runnable() {
            public void run() {
                if (CurrentP == NumPage) {
                    CurrentP = 0;
                }
                viewpagerProductDetail.setCurrentItem(CurrentP++, true);
            }
        };

        productSizeList.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        productSizeAdapter = new ProductSizeAdapter(ProductDetailActivity.this);
        productSizeList.setAdapter(productSizeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn_product_detail:
                finish();
                break;
            case R.id.tv_product_detail_buyNow:
                startActivity(new Intent(ProductDetailActivity.this, AddAddressEcomActivity.class));
                Toast.makeText(getApplicationContext(),"Coming Soon Buy Now",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_product_detail_add_cart:
                Toast.makeText(getApplicationContext(),"Product Added to your Cart",Toast.LENGTH_SHORT).show();
break;
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_frame, fragment)
                    .commit();

            return true;
        }
        return false;
    }
}
