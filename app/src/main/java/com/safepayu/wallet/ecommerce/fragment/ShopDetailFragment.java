package com.safepayu.wallet.ecommerce.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.activity.FilterDialog;
import com.safepayu.wallet.ecommerce.activity.ProductDetailActivity;
import com.safepayu.wallet.ecommerce.activity.SearchEcommerce;
import com.safepayu.wallet.ecommerce.adapter.OpeningHoursAdapter;
import com.safepayu.wallet.ecommerce.adapter.SearchProductAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.FilterRequest;
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;
import com.safepayu.wallet.ecommerce.model.response.VenueDetailsResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.ecommerce.fragment.HomeFragment.BrandIdList;
import static com.safepayu.wallet.ecommerce.fragment.HomeFragment.BrandNameList;

public class ShopDetailFragment extends Fragment implements SearchProductAdapter.OnProductDetailItemListener{

    private RecyclerView HoursRecyclerView,ProductsRecyclerView;
    private LinearLayout HoursLayout;
    private ImageView HoursIV,ShopLocationIV,OpenStatusIV;
    private TextView tvShopName,tvSearch,tvShopAddress,tvShopRating,tvShopTimeHours,tvStatusText;
    private RatingBar ratingBar;
    private int HoursInt=0;
    private String Latitude="",Longitude="";
    private OpeningHoursAdapter openingHoursAdapter;
    private LinearLayout SearchLayout,FilterLayout;
    private ArrayList<String> DayList;
    private ArrayList<String> brand_id,category_id,size,price,discount;
    private LoadingDialog loadingDialog;
    private VenueDetailsResponse venueDetailsResponse;

    public ShopDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.shop_detail_fragment, container, false);
        findId(view);
        return  view;
    }

    private void findId(View view) {

        brand_id=new ArrayList<>();
        category_id=new ArrayList<>();
        size=new ArrayList<>();
        price=new ArrayList<>();
        discount=new ArrayList<>();

        loadingDialog=new LoadingDialog(getActivity());
        tvShopName=view.findViewById(R.id.shopName_shopDetail);
        HoursRecyclerView=view.findViewById(R.id.recycleHours_shopDetail);
        HoursLayout=view.findViewById(R.id.openingHours_shopDetail);
        HoursIV=view.findViewById(R.id.downArrowHours_shopDetail);
        ProductsRecyclerView=view.findViewById(R.id.recycleProducts_shopDetail);
        FilterLayout=view.findViewById(R.id.filter_shopDetail);
        SearchLayout = view.findViewById(R.id.searchLayout_Shop);
        tvSearch =  view.findViewById(R.id.tv_search_ecomm);
        ratingBar=  view.findViewById(R.id.ratingBar_shopDetail);
        tvShopAddress =  view.findViewById(R.id.shopAddress_shopDetail);
        tvShopRating =  view.findViewById(R.id.shopRatingText_shopDetail);
        tvShopTimeHours = view.findViewById(R.id.timeHours_shopDetail);
        ShopLocationIV = view.findViewById(R.id.shopLocation_shopDetail);
        OpenStatusIV = view.findViewById(R.id.openStatus_shopDetail);
        tvStatusText = view.findViewById(R.id.statusText_shopDetail);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        ProductsRecyclerView.setLayoutManager(gridLayoutManager);

        DayList=new ArrayList<>();
        DayList.add("Sunday");
        DayList.add("Monday");
        DayList.add("Tuesday");
        DayList.add("Wednesday");
        DayList.add("Thursday");
        DayList.add("Friday");
        DayList.add("Saturday");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvShopName.setLetterSpacing(0.1f);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        GridLayoutManager gridLayoutManagerHours = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        HoursRecyclerView.setLayoutManager(gridLayoutManagerHours);

        HoursLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HoursInt==0){
                    HoursInt=1;
                    HoursRecyclerView.setVisibility(View.VISIBLE);

                    HoursIV.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_new));
                }else {
                    HoursInt=0;
                    HoursRecyclerView.setVisibility(View.GONE);
                    HoursIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow_new));
                }
            }
        });

        SearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchEcommerce.class);
                startActivityForResult(intent, 1);
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchEcommerce.class);
                startActivityForResult(intent, 1);
            }
        });

        FilterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FilterDialog.class);
                intent.putExtra("Class","Shop");
                startActivityForResult(intent,1);
            }
        });

        ShopLocationIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(String.valueOf(Latitude)) || TextUtils.isEmpty(String.valueOf(Longitude))){
                    BaseApp.getInstance().toastHelper().showSnackBar(view.findViewById(R.id.shopDetailFragment), "Location Not Found", false);
                }else {
                    String url = "https://www.google.com/maps/dir/?api=1&destination=" + Latitude + "," + Longitude + "&travelmode=driving";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });

        try {
            String VenueID = getArguments().getString("VenueID");

            if (isNetworkAvailable()){
                getVenueDetail(VenueID);
            }else {
                BaseApp.getInstance().toastHelper().showSnackBar(view.findViewById(R.id.shopDetailFragment), "No Internet Connection", false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {

                    if (!TextUtils.isEmpty(data.getStringExtra("search"))) {
                        Bundle args = new Bundle();
                        args.putString("search", data.getStringExtra("search"));

                        SearchProductFragment fragment = new SearchProductFragment();
                        fragment.setArguments(args);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }else if (resultCode == 2) {

                    brand_id = data.getStringArrayListExtra("brand_id");
                    category_id = data.getStringArrayListExtra("category_id");
                    size = data.getStringArrayListExtra("size");
                    price = data.getStringArrayListExtra("price");
                    discount = data.getStringArrayListExtra("discount");

                    FilterRequest filterRequest=new FilterRequest();
                    filterRequest.setBrand_id(brand_id);
                    filterRequest.setCategory_id(category_id);
                    filterRequest.setSize(size);
                    filterRequest.setPrice(price);
                    filterRequest.setDiscount(discount);

                    getProductByFilter(filterRequest);

                    Toast.makeText(getActivity(), "Filter", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void getVenueDetail(String venue_id) {

        BrandNameList.clear();
        BrandIdList.clear();

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getVenueDetails(venue_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<VenueDetailsResponse>() {
                    @Override
                    public void onSuccess(VenueDetailsResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            venueDetailsResponse=response;

                            tvShopName.setText(response.getVenueDetails().getVenue_name());
                            ratingBar.setRating(Float.parseFloat(response.getVenueDetails().getVenueRating()));
                            tvShopAddress.setText(response.getVenueDetails().getAddress_1());
                            tvShopRating.setText(response.getVenueDetails().getVenueRatingCount()+" Ratings");
                            Longitude=response.getVenueDetails().getLongitude();
                            Latitude=response.getVenueDetails().getLatitude();
                            tvShopTimeHours.setText(response.getVenueDetails().getToday_opening_time());

                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("HH");
                                int str = Integer.parseInt(sdf.format(new Date()));

                                String open=response.getVenueDetails().getToday_opening_time();
                                open=open.substring(0,2);

                                String close=response.getVenueDetails().getToday_closing_time();
                                close=close.substring(0,2);

                                if (str>=Integer.parseInt(open) && str<=Integer.parseInt(close)){
                                    OpenStatusIV.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.online_new));
                                    tvStatusText.setText("Open");
                                    tvStatusText.setTextColor(getActivity().getResources().getColor(R.color.green_900));
                                }else {
                                    OpenStatusIV.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.close_new));
                                    tvStatusText.setText("Closed");
                                    tvStatusText.setTextColor(getActivity().getResources().getColor(R.color.red_700));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            if (response.getOpening_day().size()>0){
                                openingHoursAdapter = new OpeningHoursAdapter(getActivity(),response.getOpening_day(),response.getClosing_day(),DayList);
                                HoursRecyclerView.setAdapter(openingHoursAdapter);
                            }

                            if (response.getProductList().size()>0){
                                List<ProductsByCategoryIdResponse.ProductsBean> productList=new ArrayList<>();

                                ProductsByCategoryIdResponse.ProductsBean productsBean=new ProductsByCategoryIdResponse.ProductsBean();

                                for (int i=0;response.getProductList().size()>i;i++){
                                    productsBean.setImages(response.getProductList().get(i).getImages());
                                    productsBean.setProduct_name(response.getProductList().get(i).getProduct_name());
                                    productsBean.setBuy_price(response.getProductList().get(i).getBuy_price());
                                    productsBean.setSelling_price(response.getProductList().get(i).getSell_price());
                                    productsBean.setProduct_description(response.getProductList().get(i).getProduct_description());
                                    productsBean.setVenue_name(response.getProductList().get(i).getVenue_name());
                                    productsBean.setDistance("");
                                    productsBean.setStars(0.0);

                                    productList.add(productsBean);
                                }
                                SearchProductAdapter searchProductAdapter = new SearchProductAdapter(getActivity(),productList,ShopDetailFragment.this);
                                ProductsRecyclerView.setAdapter(searchProductAdapter);
                                searchProductAdapter.notifyDataSetChanged();
                            }

                            try {
                                for (int j=0;response.getBrands().size()>j;j++){
                                    BrandNameList.add(response.getBrands().get(j).getBrand_name());
                                    BrandIdList.add(String.valueOf(response.getBrands().get(j).getId()));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
//
//                            if (response.getVenues().size()>0){
//                                ShopNumbers=response.getVenues().size();
//                                tvNoOfShop.setText(" "+response.getVenues().size());
//                                tvNoOfShopGrey.setText(" "+response.getVenues().size());
//
//                                storeListAdapter = new StoreListAdapter(getActivity(),responseSort.getVenues(),SearchProductFragment.this);
//                                searchStoreList.setAdapter(storeListAdapter);
//                            }else {
//                                tvNoOfShop.setText("0");
//                                tvNoOfShopGrey.setText("0");
//                            }
//
//                            if (response.getProducts_offers().size()>0){
//                                offerAdapter=null;
//                                offerAdapter = new OfferSearchProductAdapter(getActivity(),responseSort.getProducts_offers(),SearchProductFragment.this);
//                                offerProductList.setAdapter(offerAdapter);
//                                offerAdapter.notifyDataSetChanged();
//                            }else {
//                                offerProductList.setVisibility(View.GONE);
//                                tvNearByText.setVisibility(View.GONE);
//                            }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.shopDetailFragment),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.shopDetailFragment), true, e);
                    }
                }));
    }

    private void getProductByFilter(FilterRequest filterRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getProductsFilter(filterRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductsByCategoryIdResponse>() {
                    @Override
                    public void onSuccess(ProductsByCategoryIdResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            if (response.getProducts().size()>0){

                                SearchProductAdapter serchProductAdapter = new SearchProductAdapter(getActivity(),response.getProducts(),ShopDetailFragment.this);
                                ProductsRecyclerView.setAdapter(serchProductAdapter);
                                serchProductAdapter.notifyDataSetChanged();
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.shopDetailFragment),"No Products Found",true);
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.shopDetailFragment),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.shopDetailFragment), true, e);
                    }
                }));
    }

    @Override
    public void onProductItemDetail(int position, ProductsByCategoryIdResponse.ProductsBean productsBean) {
        Toast.makeText(getActivity(), productsBean.getProduct_name(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), ProductDetailActivity.class));
    }
}
