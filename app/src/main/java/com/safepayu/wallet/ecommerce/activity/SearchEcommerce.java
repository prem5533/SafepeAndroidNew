package com.safepayu.wallet.ecommerce.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.SearchEcommerceAdapter;
import com.safepayu.wallet.ecommerce.fragment.HomeFragment;

import java.util.ArrayList;

public class SearchEcommerce extends AppCompatActivity implements SearchEcommerceAdapter.SearchListener {

    private RecyclerView TrendingsRecyclerView;
    private ArrayList<String> SearchList;
    private TextView SearchBtn;
    private Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ecommerce_fragment);
        findId();
    }

    private void findId() {

        TrendingsRecyclerView=findViewById(R.id.recycle_searchEcommerceLayout);
        SearchBtn=findViewById(R.id.searchBtn_searchEcommerceLayout);
        BackBtn=findViewById(R.id.backBtn_searchEcommerceLayout);

        SearchList=new ArrayList<>();

        SearchList.add("Jeans");
        SearchList.add("Shirts");
        SearchList.add("Toys");
        SearchList.add("Grocery");
        SearchList.add("Crockery");

        TrendingsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SearchEcommerceAdapter searchEcommerceAdapter = new SearchEcommerceAdapter(this,SearchList,this);
        TrendingsRecyclerView.setAdapter(searchEcommerceAdapter);

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(SearchEcommerce.this, HomeFragment.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onSearchItemClick(int position, String Text) {
        Intent intent=new Intent(SearchEcommerce.this, HomeFragment.class);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}