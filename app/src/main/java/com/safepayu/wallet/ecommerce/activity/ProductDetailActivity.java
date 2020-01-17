package com.safepayu.wallet.ecommerce.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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
import com.safepayu.wallet.ecommerce.adapter.ProductValueAdapter;
import com.safepayu.wallet.ecommerce.adapter.TrendingAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.ProductDetailRequest;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;
import com.safepayu.wallet.models.response.Offer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener, ProdouctOfferAdapter.OnProductOfferItemListener,
        ProductSizeAdapter.OnProductItem , ProdouctOfferRelatedAdapter.OnProductRelatedItemListener {

    private ViewPager viewpagerProductDetail;
    private ProductDetailPagerAdapter productDetailPagerAdapter;
    private ProdouctOfferAdapter prodouctOfferAdapter;
    private ProdouctOfferRelatedAdapter prodouctOfferRelated;
    private LoadingDialog loadingDialog;
    ProductDetailRequest productDetailRequest;
    ProductValueAdapter productValueAdapter;
    ProductsDetailsResponse productsResponse ;

    private ProductSizeAdapter productSizeAdapter;
    private RecyclerView productSizeList,productColorList,recycleProductOffer,recycleProductRelated;

    int NumPage,CurrentP=0 ;
    private Button backBtnProductDetail;
    private TextView tvBuyNow,tvAddCart,tvActualPrice,tvProductDetailName,tvProductDetail,tvEarnPoint,tvOffprice,tvStoreName,tvPoffer,tvRelated;
    RatingBar ratingBar;
    String Productid = "70",Offerid,SellPrice;
    private NestedScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        findId();
        getProductDetail();
    }



    private void findId() {
        loadingDialog = new LoadingDialog(this);
        Productid = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PRODUCT_ID);
        Offerid = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().OFFER_ID);
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
        scroll = findViewById(R.id.scroll);
        tvRelated = findViewById(R.id.tvRelated);
        tvPoffer = findViewById(R.id.tvPoffer);


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

        if (Offerid.equals("")){
            productDetailRequest=new ProductDetailRequest();
            productDetailRequest.setProduct_id(Productid);
        }
        else {
            productDetailRequest=new ProductDetailRequest();
            productDetailRequest.setProduct_id(Productid);
            productDetailRequest.setOffer_id(Offerid);
        }

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

            /*    Fragment fragment = CartActivity.newInstance();

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

                            productsResponse = productsDetailsResponse;
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

                            tvStoreName.setText(productsDetailsResponse.getVenues().getVenue_name());
                            ratingBar.setRating(productsDetailsResponse.getProducts().getProductsRating());



                            if (!Offerid.equals("")){
                                tvActualPrice.setText("₹ "+productsDetailsResponse.getProducts().getSelling_price());
                                tvActualPrice.setVisibility(View.VISIBLE);
                                if (productsDetailsResponse.getProductOfers().get(0).getOffer_type().equals("discper")){
                                    Double b = ((Double.parseDouble(productsDetailsResponse.getProductOfers().get(0).getSelling_price())-((Double.parseDouble(productsDetailsResponse.getProductOfers().get(0).getSelling_price()))*(Double.parseDouble(productsDetailsResponse.getProductOfers().get(0).getDisc_per()))/100)));
                                    tvOffprice.setText("₹ " +String.format("%.3f", b)); }

                                else if (productsDetailsResponse.getProductOfers().get(0).getOffer_type().equals("discamt")){
                                    tvOffprice.setText("₹ "+String.format("%.2f",(Double.parseDouble(productsDetailsResponse.getProductOfers().get(0).getSelling_price())- Double.parseDouble(productsDetailsResponse.getProductOfers().get(0).getDisc_amt()))));
                                }
                            }
                            else {
                                tvOffprice.setText("₹ "+productsDetailsResponse.getProducts().getSelling_price());
                                tvActualPrice.setVisibility(View.GONE);
                            }

                          /*  productSizeList.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            productSizeAdapter = new ProductSizeAdapter(ProductDetailActivity.this,productsDetailsResponse.getProducts().getModifier_list());
                            productSizeList.setAdapter(productSizeAdapter);*/

                            productColorList.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                            productSizeAdapter = new ProductSizeAdapter(ProductDetailActivity.this,productsDetailsResponse.getProducts().getModifier_list(),ProductDetailActivity.this);
                            productColorList.setAdapter(productSizeAdapter);


                            recycleProductOffer.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            prodouctOfferAdapter = new ProdouctOfferAdapter(ProductDetailActivity.this,productsDetailsResponse.getProductOfers(),ProductDetailActivity.this);
                            recycleProductOffer.setAdapter(prodouctOfferAdapter);

                            recycleProductRelated.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            prodouctOfferRelated = new ProdouctOfferRelatedAdapter(ProductDetailActivity.this,productsDetailsResponse.getRelatedproduct(),ProductDetailActivity.this);
                            recycleProductRelated.setAdapter(prodouctOfferRelated);


                            if (productsDetailsResponse.getProducts().getModifier_list().isEmpty()){
                                productColorList.setVisibility(View.GONE); }
                            if (productsDetailsResponse.getProductOfers().isEmpty()){
                                tvPoffer.setVisibility(View.GONE); }
                            if (productsDetailsResponse.getRelatedproduct().isEmpty()){
                                tvRelated.setVisibility(View.GONE); }

                            else if (!productsDetailsResponse.getProductOfers().isEmpty() && !productsDetailsResponse.getRelatedproduct().isEmpty()&&productsDetailsResponse.getProducts().getModifier_list().isEmpty()){
                                tvRelated.setVisibility(View.VISIBLE);
                                tvPoffer.setVisibility(View.VISIBLE);
                                productColorList.setVisibility(View.GONE); }

                            else if (productsDetailsResponse.getProductOfers().isEmpty() && productsDetailsResponse.getRelatedproduct().isEmpty()&&productsDetailsResponse.getProducts().getModifier_list().isEmpty()){
                                tvRelated.setVisibility(View.GONE);
                                tvPoffer.setVisibility(View.GONE);
                                productColorList.setVisibility(View.GONE); }

                            else if (productsDetailsResponse.getProductOfers().isEmpty() && productsDetailsResponse.getRelatedproduct().isEmpty()&&!productsDetailsResponse.getProducts().getModifier_list().isEmpty()){
                                tvRelated.setVisibility(View.GONE);
                                tvPoffer.setVisibility(View.GONE);
                                productColorList.setVisibility(View.VISIBLE); }

                            else if (!productsDetailsResponse.getProductOfers().isEmpty() && !productsDetailsResponse.getRelatedproduct().isEmpty()&&!productsDetailsResponse.getProducts().getModifier_list().isEmpty()){
                                tvRelated.setVisibility(View.VISIBLE);
                                tvPoffer.setVisibility(View.VISIBLE);
                                productColorList.setVisibility(View.VISIBLE); }

                           else if (productsDetailsResponse.getProductOfers().isEmpty() && !productsDetailsResponse.getRelatedproduct().isEmpty()){
                                tvRelated.setVisibility(View.VISIBLE);
                                tvPoffer.setVisibility(View.GONE); }

                           else if (!productsDetailsResponse.getProductOfers().isEmpty() && productsDetailsResponse.getRelatedproduct().isEmpty()){
                                tvRelated.setVisibility(View.GONE);
                                tvPoffer.setVisibility(View.VISIBLE); }

                            else if (!productsDetailsResponse.getProductOfers().isEmpty() && !productsDetailsResponse.getRelatedproduct().isEmpty()){
                                tvRelated.setVisibility(View.GONE);
                                tvPoffer.setVisibility(View.GONE); }

                            else {
                                productColorList.setVisibility(View.VISIBLE);
                                tvRelated.setVisibility(View.VISIBLE);
                                tvPoffer.setVisibility(View.VISIBLE);}
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
    public void onProductItem(RecyclerView recyclerView, ProductsDetailsResponse.ProductsBean.ModifierListBean modifierListBean) {
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        productValueAdapter = new ProductValueAdapter(ProductDetailActivity.this,modifierListBean.getValue());
        recyclerView.setAdapter(productValueAdapter);
    }

    @Override
    public void onProductOfferItem(int position, ProductsDetailsResponse.ProductOfersBean productOfersBean) {

        productDetailRequest=new ProductDetailRequest();
        productDetailRequest.setProduct_id(Productid);
        //productDetailRequest.setOffer_id("1");
        tvActualPrice.setVisibility(View.VISIBLE);
        tvActualPrice.setText("₹ "+productOfersBean.getSelling_price());

        productDetailPagerAdapter=null;
        productDetailPagerAdapter = new ProductDetailPagerAdapter(getApplicationContext(),productOfersBean.getProduct_images());
        viewpagerProductDetail.setAdapter(productDetailPagerAdapter);
        productDetailPagerAdapter.notifyDataSetChanged();
        NumPage= productOfersBean.getProduct_images().size();
        TabLayout tabLayoutt = findViewById(R.id.tab_layout_productDetail);
        if (NumPage>1){
            tabLayoutt.setupWithViewPager(viewpagerProductDetail, true); }

      //  tvOffprice.setText("₹ "+String.valueOf(Float.parseFloat(productsResponse.getProducts().getSelling_price()) - Float.parseFloat(productOfersBean.getDiscount_amount())));
        if (productsResponse.getProductOfers().get(position).getOffer_type().equals("discper")){

            Double b = ((Double.parseDouble(productsResponse.getProducts().getSelling_price())-((Double.parseDouble(productsResponse.getProducts().getSelling_price()))*(Double.parseDouble(productsResponse.getProductOfers().get(position).getDisc_per()))/100)));
            tvOffprice.setText("₹ " +String.format("%.3f", b)); }

        else if (productsResponse.getProductOfers().get(position).getOffer_type().equals("discamt")){
            tvOffprice.setText("₹ "+String.format("%.2f",(Double.parseDouble(productsResponse.getProductOfers().get(position).getSelling_price())- Double.parseDouble(productsResponse.getProductOfers().get(position).getDisc_amt())))); }
        focusOnView();

    }

    private void focusOnView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scroll.scrollTo(0, viewpagerProductDetail.getTop());
            }
        });
    }

    @Override
    public void onReltedItem(int position, ProductsDetailsResponse.RelatedproductBean productOfersBean) {
        Productid = "71";
        productDetailRequest=new ProductDetailRequest();
        productDetailRequest.setProduct_id(Productid);

        getProductDetail();
    }
}
