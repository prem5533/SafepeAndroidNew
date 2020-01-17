package com.safepayu.wallet.ecommerce.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.CartAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.response.TotalCartResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity implements View.OnClickListener , CartAdapter.CartSizeListener {

    private RecyclerView ProductsRecyclerView;
    private Button btnCheckout;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_fragment);
        findId();

        if (isNetworkAvailable()){
            getTotalCart();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.homeEcommLayout),"No Internet Connection!",true);
        }
    }



    private void findId() {
        loadingDialog=new LoadingDialog(CartActivity.this);
        ProductsRecyclerView = findViewById(R.id.recycleCart);
        btnCheckout = findViewById(R.id.btn_checkout);

        btnCheckout.setOnClickListener(this);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_checkout:
                startActivity(new Intent(getApplicationContext(), AddAddressEcomActivity.class));
                break;
        }
    }

    @Override
    public void cartSizeItem(int position) {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
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

    private void getTotalCart() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(CartActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getTotalCarts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TotalCartResponse>() {
                    @Override
                    public void onSuccess(TotalCartResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
                            CartAdapter cartAdapter = new CartAdapter(CartActivity.this, CartActivity.this,response.getCarts());
                            ProductsRecyclerView.setAdapter(cartAdapter);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.cartEcommLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.cartEcommLayout), true, e);
                    }
                }));
    }
}
