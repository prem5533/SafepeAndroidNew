package com.safepayu.wallet.ecommerce.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.ThankYouAdapter;

public class ThankYouFragment extends AppCompatActivity {

    private RecyclerView ProductsRecyclerView;

    private Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thank_you_fragment);
        findId();
    }

    private void findId() {

        BackBtn=findViewById(R.id.backBtn_thankYouLayout);
        ProductsRecyclerView=findViewById(R.id.recycleProduct_thankYouLayout);

        ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ThankYouAdapter thankYouAdapter = new ThankYouAdapter(this);
        ProductsRecyclerView.setAdapter(thankYouAdapter);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
