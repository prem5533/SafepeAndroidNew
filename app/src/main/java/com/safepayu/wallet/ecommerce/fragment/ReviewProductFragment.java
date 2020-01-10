package com.safepayu.wallet.ecommerce.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.ThankYouAdapter;

public class ReviewProductFragment extends AppCompatActivity {

    private TextView tvSkipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_product_fragment);
        findId();
    }

    private void findId() {
        tvSkipBtn=findViewById(R.id.skipBtn_reviewProducts);

        tvSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReviewProductFragment.this,ThankYouFragment.class));
                finish();
            }
        });

    }
}

