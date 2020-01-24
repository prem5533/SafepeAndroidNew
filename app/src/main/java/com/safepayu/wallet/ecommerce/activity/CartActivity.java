package com.safepayu.wallet.ecommerce.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.safepayu.wallet.ecommerce.model.request.CartQuantityRequest;
import com.safepayu.wallet.ecommerce.model.response.CartQuantityResponse;
import com.safepayu.wallet.ecommerce.model.response.DeleteCartResponse;
import com.safepayu.wallet.ecommerce.model.response.MoveToCartResponse;
import com.safepayu.wallet.ecommerce.model.response.TotalCartResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.ecommerce.activity.EHomeActivity.tvCartBadge;

public class CartActivity extends AppCompatActivity implements View.OnClickListener , CartAdapter.CartSizeListener {

    private RecyclerView ProductsRecyclerView;
    private Button btnCheckout;
    private LoadingDialog loadingDialog;
    TotalCartResponse totalCartResponse;
    CartQuantityRequest cartQuantityRequest;
    CartQuantityResponse cartQuantityResponse;
    CartAdapter cartAdapter;
    private LinearLayout liCartEmpty,licheckout;
    private TextView tvTotalRs;
    int  total;
    double discper =0,discamt = 0,sum = 0;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_fragment);
        findId();

        if (isNetworkAvailable()){
            getTotalCart();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.cartEcommLayout),"No Internet Connection!",true);
        }
    }



    private void findId() {
        loadingDialog=new LoadingDialog(CartActivity.this);
        ProductsRecyclerView = findViewById(R.id.recycleCart);
        btnCheckout = findViewById(R.id.btn_checkout);
        liCartEmpty = findViewById(R.id.CartEmpty);
        licheckout = findViewById(R.id.licheckout);
        tvTotalRs = findViewById(R.id.tv_total_rs);

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

    @Override
    public void cartRemoveItem(int position) {

        showDialogRemoveCart(position, CartActivity.this);
    }

    @Override
    public void onCartList(int position, TotalCartResponse.CartsBean cartsBean) {
        Intent intent = new Intent(CartActivity.this, ProductDetailActivity.class);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PRODUCT_ID,String.valueOf(cartsBean.getProduct_id()));
        if (cartsBean.getOffer_id()!=0)
        { BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,String.valueOf(cartsBean.getOffer_id())); }
        else { BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().OFFER_ID,""); }

        startActivity(intent);
    }

    @Override
    public void cartMoveItem(int position) {
        moveCartToWishList(position);
    }

    @Override
    public void cartQuantityItem(int position, TextView productQuantity, TotalCartResponse.CartsBean cartsBean, int quantity ) {

        getAddCartQuantity(position,productQuantity,cartsBean);

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
                                 totalCartResponse = response;

                                 for (total = 0 ; total<response.getCarts().size();total++){

                                     if (response.getCarts().get(total).getOffer_id()!=0)
                                     {
                                         if (response.getCarts().get(total).getOffer_type().equals("discper")){


                                             double b = ((Double.parseDouble(response.getCarts().get(total).getSelling_price())-((Double.parseDouble(response.getCarts().get(total).getSelling_price()))*(Double.parseDouble(response.getCarts().get(total).getDisc_per()))/100)));
                                            double discperTotal= b *Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                              discper=discper+ discperTotal;
                                             tvTotalRs.setText("₹ " +String.format("%.3f", discper));
                                         }
                                         else if (response.getCarts().get(total).getOffer_type().equals("discamt")){
                                             double c = (Double.parseDouble(response.getCarts().get(total).getSelling_price())- Double.parseDouble(response.getCarts().get(total).getDisc_amt()))*Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                             discamt=discamt+ c;
                                            tvTotalRs.setText("₹ "+String.format("%.2f",(discamt)));
                                         }
                                     }
                                     else {
                                         double totalAmount = Double.parseDouble(response.getCarts().get(total).getSelling_price())* Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                         sum=sum+ totalAmount;
                                         tvTotalRs.setText("₹ "+String.format("%.2f",(sum)));
                                     }
                                }

                                 if (response.getCarts().isEmpty()){
                                     liCartEmpty.setVisibility(View.VISIBLE);
                                     ProductsRecyclerView.setVisibility(View.GONE);
                                     licheckout.setVisibility(View.GONE);
                                 }
                                 else {
                                     ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
                                     cartAdapter = new CartAdapter(CartActivity.this, CartActivity.this,totalCartResponse.getCarts());
                                     ProductsRecyclerView.setAdapter(cartAdapter);
                                     liCartEmpty.setVisibility(View.GONE);
                                     licheckout.setVisibility(View.VISIBLE);
                                 }

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

//******************************Remove cart************************

    private void showDialogRemoveCart(final int position, Activity activity) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        dialog.setTitle("LooxMart Alert")
                .setCancelable(false)
                .setMessage("\nAre you sure you want to remove this item?\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        if (isNetworkAvailable()){
                            getRemoveCart(position);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
                        }

                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }

    private void getRemoveCart(final int position) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(CartActivity.this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getDeleteCart( String.valueOf(totalCartResponse.getCarts().get(position).getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DeleteCartResponse>() {
                    @Override
                    public void onSuccess(DeleteCartResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_SHORT).show();

                            totalCartResponse.getCarts().remove(position);
                            cartAdapter.notifyDataSetChanged();
                            int  cartNumber = Integer.parseInt(tvCartBadge.getText().toString());
                            tvCartBadge.setText(""+(cartNumber-1));
                            if (totalCartResponse.getCarts().isEmpty()){
                                liCartEmpty.setVisibility(View.VISIBLE);
                                ProductsRecyclerView.setVisibility(View.GONE);
                                licheckout.setVisibility(View.GONE);
                            }
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


    //******************************Move cart************************
    private void moveCartToWishList(final int position) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(CartActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.getMoveToWishList( String.valueOf(totalCartResponse.getCarts().get(position).getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MoveToCartResponse>(){
                    @Override
                    public void onSuccess(MoveToCartResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            Toast.makeText(CartActivity.this,response.getMessage(), Toast.LENGTH_SHORT).show();
                            totalCartResponse.getCarts().remove(position);
                            cartAdapter.notifyDataSetChanged();
                            int  cartNumber = Integer.parseInt(tvCartBadge.getText().toString());
                            tvCartBadge.setText(""+(cartNumber-1));
                            if (totalCartResponse.getCarts().isEmpty()){
                                liCartEmpty.setVisibility(View.VISIBLE);
                                ProductsRecyclerView.setVisibility(View.GONE);
                                licheckout.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.cartEcommLayout), false, e.getCause());
                    }
                }));
    }

    //******************************Quantity cart************************
        private void getAddCartQuantity(final int position, TextView productQuantity, final TotalCartResponse.CartsBean cartsBean) {

      final String  PlusMinus = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PLUS_MINUS);

            cartQuantityRequest = new CartQuantityRequest();
            cartQuantityRequest.setCart_id(totalCartResponse.getCarts().get(position).getId());
            if (PlusMinus.equals("plus"))
            { cartQuantityRequest.setQuantities("1"); }

            else if (PlusMinus.equals("minus"))
            { cartQuantityRequest.setQuantities("-1"); }



            ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(CartActivity.this).create(ApiServiceEcom.class);

            BaseApp.getInstance().getDisposable().add(apiServiceEcom.getCartQuantity(cartQuantityRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<CartQuantityResponse>(){

                        @Override
                        public void onSuccess(CartQuantityResponse response) {
                            cartQuantityResponse = response;
                            if (response.isStatus()==true){
                                    totalCartResponse.getCarts().get(position).setQuantities(response.getCart().getQuantities());
                                try {
                                    if (cartsBean.getOffer_id()!=0)
                                    {
                                        if (cartsBean.getOffer_type().equals("discper")){
                                            double b = ((Double.parseDouble(cartsBean.getSelling_price())-((Double.parseDouble(cartsBean.getSelling_price()))*(Double.parseDouble(cartsBean.getDisc_per()))/100)));

                                            String totalPriceText=tvTotalRs.getText().toString().trim();
                                            totalPriceText=totalPriceText.substring(1);
                                            double totalPrice = Double.parseDouble(totalPriceText);
                                            if (PlusMinus.equals("plus")){
                                                tvTotalRs.setText("₹ " +String.format("%.3f",(totalPrice + b)));
                                            }else {
                                                tvTotalRs.setText("₹ " +String.format("%.3f",(totalPrice - b)));
                                            }
                                        }
                                        else if (cartsBean.getOffer_type().equals("discamt")){
                                            double c = (Double.parseDouble(cartsBean.getSelling_price())- Double.parseDouble(cartsBean.getDisc_amt()))*Double.parseDouble(""+cartsBean.getQuantities());
                                            String totalPriceText=tvTotalRs.getText().toString().trim();
                                            totalPriceText=totalPriceText.substring(1);
                                            double totalPrice = Double.parseDouble(totalPriceText);
                                            if (PlusMinus.equals("plus")){
                                                tvTotalRs.setText("₹ " +String.format("%.3f",(totalPrice + c)));
                                            }else {
                                                tvTotalRs.setText("₹ " +String.format("%.3f",(totalPrice - c)));
                                            }
                                        }
                                    }
                                    else {
                                        double sellingPrice= Double.parseDouble(totalCartResponse.getCarts().get(position).getSelling_price());
                                        String totalPriceText=tvTotalRs.getText().toString().trim();
                                        totalPriceText=totalPriceText.substring(1);
                                        double totalPrice = Double.parseDouble(totalPriceText);
                                        if (PlusMinus.equals("plus")){
                                            tvTotalRs.setText("₹ "+(totalPrice + sellingPrice));
                                        }else {
                                            tvTotalRs.setText("₹ "+(totalPrice - sellingPrice));
                                        }
                                    }
                                }catch (Exception e){
                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.cartEcommLayout), e.getMessage(),true);
                                    e.printStackTrace();
                                }
                            }
                                else if (response.isStatus()==false){
                                    Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                cartAdapter.notifyDataSetChanged();
                        }

            @Override
            public void onError(Throwable e) {
                BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.cartEcommLayout), false, e.getCause());
            }
        }));
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CartActivity.this, EHomeActivity.class));
        finish();

    }
}


