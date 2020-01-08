package com.safepayu.wallet.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.EcommPagerAdapter;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager viewpagerProductDetail;
    private EcommPagerAdapter ecommPagerAdapter;
    int images[] = {R.drawable.girl_jeans, R.drawable.girl_jeans, R.drawable.girl_jeans};
    int NumPage,CurrentP=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        findId();
    }

    private void findId() {
        viewpagerProductDetail = findViewById(R.id.viewpager__product_detil);

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
    }
}
