package com.safepayu.wallet.ecommerce.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.ProdouctOfferAdapter;
import com.safepayu.wallet.ecommerce.adapter.ProdouctOfferRelatedAdapter;
import com.safepayu.wallet.ecommerce.adapter.ProductDetailPagerAdapter;
import com.safepayu.wallet.ecommerce.adapter.ProductSizeAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.ProductDetailRequest;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener, ProdouctOfferAdapter.OnProductOfferItemListener {

    private ViewPager viewpagerProductDetail;
    private ProductDetailPagerAdapter productDetailPagerAdapter;
    private ProdouctOfferAdapter prodouctOfferAdapter;
    private ProdouctOfferRelatedAdapter prodouctOfferRelated;
    private LoadingDialog loadingDialog;
    ProductDetailRequest productDetailRequest;

    private ProductSizeAdapter productSizeAdapter;
    private RecyclerView productSizeList,productColorList,recycleProductOffer,recycleProductRelated;

    int NumPage,CurrentP=0 ;
    private Button backBtnProductDetail;
    private TextView tvBuyNow,tvAddCart,tvActualPrice,tvProductDetailName,tvProductDetail,tvEarnPoint,tvOffprice,tvStoreName;
    RatingBar ratingBar;
    String Productid = "70";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        findId();
        getProductDetail();
    }



    private void findId() {
        loadingDialog = new LoadingDialog(this);
        viewpagerProductDetail = findViewById(R.id.viewpager__product_detil);
        productSizeList = findViewById(R.id.product_size_list);
        productColorList = findViewById(R.id.product_color_list);
        backBtnProductDetail = findViewById(R.id.back_btn_product_detail);
        tvBuyNow = findViewById(R.id.tv_product_detail_buyNow);
        tvAddCart = findViewById(R.id.tv_product_detail_add_cart);
        tvActualPrice = findViewById(R.id.tv_product_detail_actualprice);
        tvProductDetailName = findViewById(R.id.tv_product_detail_name);
        tvProductDetail = findViewById(R.id.tv_product_detail_);
        tvEarnPoint = findViewById(R.id.tv_product_detail_earn_point);
        tvOffprice = findViewById(R.id.tv_product_detail_offprice);
        tvStoreName = findViewById(R.id.tv_product_detail_from);
        ratingBar = findViewById(R.id.rating_product_detail);
        recycleProductOffer = findViewById(R.id.recycleProductOffer);
        recycleProductRelated = findViewById(R.id.recycleProductRelated);
        backBtnProductDetail.setOnClickListener(this);
        tvBuyNow.setOnClickListener(this);
        tvAddCart.setOnClickListener(this);


        tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



        /*After setting the adapter use the timer type 3 */
        final Handler hand = new Handler();
        final Runnable Updt = new Runnable() {
            public void run() {
                if (CurrentP == NumPage) {
                    CurrentP = 0;
                }
                viewpagerProductDetail.setCurrentItem(CurrentP++, true);
            }
        };

        productDetailRequest=new ProductDetailRequest();
        productDetailRequest.setProduct_id(Productid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn_product_detail:
                finish();
                break;
            case R.id.tv_product_detail_buyNow:
                startActivity(new Intent(ProductDetailActivity.this, AddAddressEcomActivity.class));
                Toast.makeText(getApplicationContext(),"Coming Soon Buy Now",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_product_detail_add_cart:

            /*    Fragment fragment = CartFragment.newInstance();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.content_frame, fragment).commit();*/
                Toast.makeText(getApplicationContext(),"Product Added to your Cart",Toast.LENGTH_SHORT).show();
break;
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_frame, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    private void getProductDetail() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);


        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(ProductDetailActivity.this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiServiceEcom.getProductDetails(productDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductsDetailsResponse>(){

                    @Override
                    public void onSuccess(ProductsDetailsResponse productsDetailsResponse) {
                        loadingDialog.hideDialog();
                        if (productsDetailsResponse.isStatus()) {

                            productDetailPagerAdapter = new ProductDetailPagerAdapter(getApplicationContext(),productsDetailsResponse.getProducts().getImages());
                            viewpagerProductDetail.setAdapter(productDetailPagerAdapter);
                            NumPage= productsDetailsResponse.getProducts().getImages().size();
                            TabLayout tabLayoutt = findViewById(R.id.tab_layout_productDetail);
                            if (NumPage>1){
                                tabLayoutt.setupWithViewPager(viewpagerProductDetail, true);
                            }

                            tvProductDetailName.setText(productsDetailsResponse.getProducts().getProduct_name());
                            tvProductDetail.setText(productsDetailsResponse.getProducts().getProduct_description());
                            tvEarnPoint.setText("Earn "+productsDetailsResponse.getLoyalitypoints().getLoyalty_points()+" points");
                            tvOffprice.setText("₹ "+productsDetailsResponse.getProducts().getSelling_price());
                            tvStoreName.setText(productsDetailsResponse.getVenues().getVenue_name());
                            ratingBar.setRating(productsDetailsResponse.getProducts().getProductsRating());

                            productSizeList.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            productSizeAdapter = new ProductSizeAdapter(ProductDetailActivity.this,productsDetailsResponse.getProducts().getModifier_list().getSize());
                            productSizeList.setAdapter(productSizeAdapter);

                            productColorList.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            productSizeAdapter = new ProductSizeAdapter(ProductDetailActivity.this,productsDetailsResponse.getProducts().getModifier_list().getColor());
                            productColorList.setAdapter(productSizeAdapter);


                            recycleProductOffer.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            prodouctOfferAdapter = new ProdouctOfferAdapter(ProductDetailActivity.this,productsDetailsResponse.getProductOfers(),ProductDetailActivity.this);
                            recycleProductOffer.setAdapter(prodouctOfferAdapter);

                            recycleProductRelated.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            prodouctOfferRelated = new ProdouctOfferRelatedAdapter(ProductDetailActivity.this,productsDetailsResponse.getRelatedproduct());
                            recycleProductRelated.setAdapter(prodouctOfferRelated);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.product_detail), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onProductOfferItem(int position) {
        productDetailRequest=new ProductDetailRequest();
        productDetailRequest.setProduct_id(Productid);
        productDetailRequest.setOffer_id("1");
        getProductDetail();
       // Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT).show();
    }
}
