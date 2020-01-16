package com.safepayu.wallet.ecommerce.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.activity.AddAddressEcomActivity;
import com.safepayu.wallet.ecommerce.adapter.CartAdapter;

public class CartFragment extends Fragment implements View.OnClickListener , CartAdapter.CartSizeListener {

    private RecyclerView ProductsRecyclerView;
    private Button btnCheckout;


    public CartFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.cart_fragment, container, false);
        findId(view);
        return  view;
    }

    private void findId(View view) {

        ProductsRecyclerView = view.findViewById(R.id.recycleCart);
        btnCheckout = view.findViewById(R.id.btn_checkout);


        btnCheckout.setOnClickListener(this);

        ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        CartAdapter cartAdapter = new CartAdapter(getActivity(),CartFragment.this);
        ProductsRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_checkout:
                startActivity(new Intent(getActivity(), AddAddressEcomActivity.class));
                break;
        }
    }

    @Override
    public void cartSizeItem(int position) {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public static class BottomSheetFragment extends BottomSheetDialogFragment {

        private RecyclerView recyclerSize;

        public BottomSheetFragment() {
        }

        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view =  inflater.inflate(R.layout.bottom_size, container, false);
            setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);

            recyclerSize =view. findViewById(R.id.recycler_size);

            return view;
        }
    }
}
