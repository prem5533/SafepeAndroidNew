package com.safepayu.wallet.ecommerce.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.flight.FlightListActivity;
import com.safepayu.wallet.adapter.fight.OneWayFlightListAdapter;

import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.ProductImageAdapter;
import com.safepayu.wallet.ecommerce.adapter.ProductSizeAdapter;
import com.safepayu.wallet.ecommerce.adapter.SerchProductAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private RecyclerView productImageList,productSizeList;
    private ProductImageAdapter  productImageAdapter;



    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_detail, container, false);
        findId(view);

        return view;
    }



    private void findId(View view) {
        productImageList = view.findViewById(R.id.product_image_list);
        productSizeList = view.findViewById(R.id.product_size_list);

        productImageList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        productImageAdapter = new ProductImageAdapter(getActivity());
        productImageList.setAdapter(productImageAdapter);
    }



}
