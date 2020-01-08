package com.safepayu.wallet.ecommerce.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.activity.ProductDetailActivity;
import com.safepayu.wallet.ecommerce.adapter.EcomSpinnerAdapter;
import com.safepayu.wallet.ecommerce.adapter.SerchProductAdapter;
import com.safepayu.wallet.ecommerce.adapter.StoreListAdapter;
import com.safepayu.wallet.ecommerce.adapter.TrendingAdapter;
import com.safepayu.wallet.models.response.OperatorResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProductFragment extends Fragment implements View.OnClickListener, SerchProductAdapter.OnProductDetailItemListener  {

    private LinearLayout liProduct ,liProductGray, liStore,liStoreGray;
    private RecyclerView SearchProductList,searchStoreList;
    private SerchProductAdapter serchProductAdapter;
    private StoreListAdapter storeListAdapter;
    private Spinner categorySpinner;
    GridLayoutManager gridLayoutManager;
    private TextView tvSearchProductMatching;
    List<OperatorResponse.OperatorsBean> mCategoryList = new ArrayList<>();


    public SearchProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_product, container, false);
        findId(view);
        return view;
    }

    private void findId(View view) {
        liProduct = view.findViewById(R.id.li_product);
        liProductGray = view.findViewById(R.id.li_product_gray);
        liStore = view.findViewById(R.id.li_store);
        liStoreGray = view.findViewById(R.id.li_store_gray);
        SearchProductList = view.findViewById(R.id.search_product_list);
        searchStoreList = view.findViewById(R.id.search_store_list);
        categorySpinner = view.findViewById(R.id.category_spinner);
        tvSearchProductMatching = view.findViewById(R.id.tv_search_product_matching);
        tvSearchProductMatching.setText("Found 40 products matching the search keyword, near you");
        liProduct.setOnClickListener(this);
        liProductGray.setOnClickListener(this);
        liStore.setOnClickListener(this);
        liStoreGray.setOnClickListener(this);

        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        SearchProductList.setLayoutManager(gridLayoutManager);
        serchProductAdapter = new SerchProductAdapter(getActivity(),SearchProductFragment.this);
        SearchProductList.setAdapter(serchProductAdapter);

        EcomSpinnerAdapter customAdapter=new EcomSpinnerAdapter(getActivity(),mCategoryList);
        categorySpinner.setAdapter(customAdapter);

        searchStoreList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        storeListAdapter = new StoreListAdapter(getActivity());
        searchStoreList.setAdapter(storeListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_product:
                liStoreGray.setVisibility(View.GONE);
                liProduct.setVisibility(View.GONE);
                liStore.setVisibility(View.VISIBLE);
                liProductGray.setVisibility(View.VISIBLE);
                tvSearchProductMatching.setText("Found 10 stores matching the search keyword, near you");
                searchStoreList.setVisibility(View.VISIBLE);
                SearchProductList.setVisibility(View.GONE);

                break;
            case R.id.li_store:
                liProductGray.setVisibility(View.GONE);
                liStore.setVisibility(View.GONE);
                liProduct.setVisibility(View.VISIBLE);
                liStoreGray.setVisibility(View.VISIBLE);
                tvSearchProductMatching.setText("Found 25 products matching the search keyword, near you");
                searchStoreList.setVisibility(View.GONE);
                SearchProductList.setVisibility(View.VISIBLE);
                break;} }

    @Override
    public void onProductItemDetail(int position) {

        startActivity(new Intent(getActivity(), ProductDetailActivity.class));
    }
}
