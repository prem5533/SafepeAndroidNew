package com.safepayu.wallet.ecommerce.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.activity.CartActivity;
import com.safepayu.wallet.ecommerce.adapter.CartAdapter;
import com.safepayu.wallet.ecommerce.adapter.WishlistAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.response.TotalCartResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WishlistFragment extends Fragment {

    private RecyclerView ProductsRecyclerView;
    GridLayoutManager gridLayoutManager;
    WishlistAdapter wishlistAdapter;
    private LoadingDialog loadingDialog;


    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.wishlist_fragment, container, false);
        findId(view);
        if (isNetworkAvailable()){
         //   getWishList();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.relative_wishlist),"No Internet Connection!",true);
        }
        return  view;
    }

    private void findId(View view) {
        loadingDialog=new LoadingDialog(getActivity());
        ProductsRecyclerView=view.findViewById(R.id.recycleWishlist);
        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        ProductsRecyclerView.setLayoutManager(gridLayoutManager);

        wishlistAdapter = new WishlistAdapter(getActivity());
        ProductsRecyclerView.setAdapter(wishlistAdapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected(); }


    private void getWishList() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getTotalCarts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TotalCartResponse>() {
                    @Override
                    public void onSuccess(TotalCartResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {


                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.cartEcommLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.cartEcommLayout), true, e);
                    }
                }));
    }
}
