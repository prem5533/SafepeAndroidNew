package com.safepayu.wallet.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.safepayu.wallet.R;

public class SearchProductActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout liProduct ,liProductGray, liStore,liStoreGray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        findId();
    }

    private void findId() {
        liProduct = findViewById(R.id.li_product);
        liProductGray = findViewById(R.id.li_product_gray);
        liStore = findViewById(R.id.li_store);
        liStoreGray = findViewById(R.id.li_store_gray);

        liProduct.setOnClickListener(this);
        liProductGray.setOnClickListener(this);
        liStore.setOnClickListener(this);
        liStoreGray.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_product:

                break;
            case R.id.li_store_gray:
                break;
        }
    }
}
