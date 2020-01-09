package com.safepayu.wallet.ecommerce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.CartAdapter;
import com.safepayu.wallet.ecommerce.adapter.WishlistAdapter;

public class WishlistFragment extends Fragment {

    private RecyclerView ProductsRecyclerView;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.wishlist_fragment, container, false);
        findId(view);
        return  view;
    }
    private void findId(View view) {

        ProductsRecyclerView=view.findViewById(R.id.recycleWishlist);

        ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        WishlistAdapter wishlistAdapter = new WishlistAdapter(getActivity());
        ProductsRecyclerView.setAdapter(wishlistAdapter);
    }
}
