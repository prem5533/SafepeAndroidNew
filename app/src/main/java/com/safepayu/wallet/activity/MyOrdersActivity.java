package com.safepayu.wallet.activity;

import android.os.Bundle;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.MyOrdersAdapter;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.halper.RecyclerLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

public class MyOrdersActivity extends BaseActivity {

    private RecyclerView recyclerMyOrders;
    private RecyclerLayoutManager layoutManager;
    private LoadingDialog loadingDialog;
    private MyOrdersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recyclerMyOrders = findViewById(R.id.list_orderHistory);

        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(true);
        recyclerMyOrders.setLayoutManager(layoutManager);
        mAdapter = new MyOrdersAdapter(this);
        recyclerMyOrders.setAdapter(mAdapter);



    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_orders;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }
}
