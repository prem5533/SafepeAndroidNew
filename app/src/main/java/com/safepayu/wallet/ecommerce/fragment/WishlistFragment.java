package com.safepayu.wallet.ecommerce.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.activity.ProductDetailActivity;
import com.safepayu.wallet.ecommerce.adapter.WishlistAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.AddToCartRequest;
import com.safepayu.wallet.ecommerce.model.request.WishListRequest;
import com.safepayu.wallet.ecommerce.model.response.MoveToCartResponse;
import com.safepayu.wallet.ecommerce.model.response.WishListResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.ecommerce.activity.EHomeActivity.tvCartBadge;

public class WishlistFragment extends Fragment implements WishlistAdapter.WishListAddListener {

    private RecyclerView ProductsRecyclerView;
    GridLayoutManager gridLayoutManager;
    WishlistAdapter wishlistAdapter;
    private LoadingDialog loadingDialog;
    AddToCartRequest addToCartRequest;
    WishListResponse wishListResponse;
    private LinearLayout liWishListEmpty;
    WishListRequest wishListRequest;


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
            getWishList();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.relative_wishlist),"No Internet Connection!",true);
        }
        return  view;
    }

    private void findId(View view) {
        loadingDialog=new LoadingDialog(getActivity());
        ProductsRecyclerView=view.findViewById(R.id.recycleWishlist);
        liWishListEmpty=view.findViewById(R.id.liWishEmpty);
        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        ProductsRecyclerView.setLayoutManager(gridLayoutManager);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected(); }


    private void getWishList() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getLikeProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WishListResponse>() {
                    @Override
                    public void onSuccess(WishListResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            wishListResponse = response;
                            if (response.getLikes().isEmpty()){
                                liWishListEmpty.setVisibility(View.VISIBLE);
                                ProductsRecyclerView.setVisibility(View.GONE); }
                            else {
                                wishlistAdapter = new WishlistAdapter(getActivity(),response.getLikes(),WishlistFragment.this);
                                ProductsRecyclerView.setAdapter(wishlistAdapter);
                                liWishListEmpty.setVisibility(View.GONE);}

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.relative_wishlist),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.relative_wishlist), true, e);
                    }
                }));
    }

    @Override
    public void wishlistAddItem(int position) {
        moveLikeToCart(position);
    }

    @Override
    public void wishlistRemoveItem(int position) {
        getWishListProduct(position);
    }


    @Override
    public void onWishlistList(int position, WishListResponse.LikesBean likesBean) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PRODUCT_ID,String.valueOf(likesBean.getProduct_id()));
      //  BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,String.valueOf(likesBean.getOffer_id()));
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,"");
        startActivity(intent);
    }


    private void moveLikeToCart(final int position) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.getMoveToCart( String.valueOf(wishListResponse.getLikes().get(position).getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MoveToCartResponse>(){
                    @Override
                    public void onSuccess(MoveToCartResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            int  cartNumber = Integer.parseInt(tvCartBadge.getText().toString());
                            tvCartBadge.setText(""+(cartNumber+1));
                            Toast.makeText(getActivity(),response.getMessage(), Toast.LENGTH_SHORT).show();

                            wishListResponse.getLikes().remove(position);
                            wishlistAdapter.notifyDataSetChanged();
                            if (wishListResponse.getLikes().isEmpty()){
                                liWishListEmpty.setVisibility(View.VISIBLE);
                                ProductsRecyclerView.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.relative_wishlist), false, e.getCause());
                    }
                }));
    }
//*************************Remove WishList********************************************//
    private void getWishListProduct(final int position) {

        wishListRequest=new WishListRequest();
        wishListRequest.setProduct_id(wishListResponse.getLikes().get(position).getProduct_id());
        wishListRequest.setMerchant_id(wishListResponse.getLikes().get(position).getMerchant_id());
        wishListRequest.setVenue_id(wishListResponse.getLikes().get(position).getVenue_id());
        wishListRequest.setModifier_id(wishListResponse.getLikes().get(position).getModifier_id());

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.getWishListLikeDislike(wishListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WishListResponse>(){
                    @Override
                    public void onSuccess(WishListResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            wishListResponse.getLikes().remove(position);
                            wishlistAdapter.notifyDataSetChanged();
                            if (wishListResponse.getLikes().isEmpty()){
                                liWishListEmpty.setVisibility(View.VISIBLE);
                                ProductsRecyclerView.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.relative_wishlist), false, e.getCause());
                    }
                }));
    }
}
