package com.safepayu.wallet.ecommerce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.ThankYouAdapter;

public class ThankYouFragment extends AppCompatActivity {

    private RecyclerView ProductsRecyclerView;

    private TextView tvSkipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thank_you_fragment);
        findId();
    }

    private void findId() {

        ProductsRecyclerView=findViewById(R.id.recycleProduct_thankYouLayout);

        ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ThankYouAdapter thankYouAdapter = new ThankYouAdapter(this);
        ProductsRecyclerView.setAdapter(thankYouAdapter);
    }
}
