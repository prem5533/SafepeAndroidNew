package com.safepayu.wallet.ecommerce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.ThankYouAdapter;

public class ThankYouFragment extends Fragment {

    private RecyclerView ProductsRecyclerView;

    public ThankYouFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.thank_you_fragment, container, false);
        findId(view);
        return  view;
    }

    private void findId(View view) {

        ProductsRecyclerView=view.findViewById(R.id.recycleCart);

        ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ThankYouAdapter thankYouAdapter = new ThankYouAdapter(getActivity());
        ProductsRecyclerView.setAdapter(thankYouAdapter);
    }
}
