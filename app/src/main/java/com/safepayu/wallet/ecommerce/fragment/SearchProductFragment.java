package com.safepayu.wallet.ecommerce.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.activity.FilterDialog;
import com.safepayu.wallet.ecommerce.activity.ProductDetailActivity;
import com.safepayu.wallet.ecommerce.activity.SearchEcommerce;
import com.safepayu.wallet.ecommerce.adapter.OfferSearchProductAdapter;
import com.safepayu.wallet.ecommerce.adapter.SearchProductAdapter;
import com.safepayu.wallet.ecommerce.adapter.StoreListAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.FilterRequest;
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProductFragment extends Fragment implements View.OnClickListener, SearchProductAdapter.OnProductDetailItemListener,
                StoreListAdapter.ShopItemListListener,OfferSearchProductAdapter.OnProductDetailItemListener{

    private LinearLayout liProduct ,liProductGray, liStore,liStoreGray,liSort,liFilter;
    private RecyclerView SearchProductList,searchStoreList,offerProductList;
    private SearchProductAdapter serchProductAdapter;
    private OfferSearchProductAdapter offerAdapter;
    private StoreListAdapter storeListAdapter;
    private Spinner categorySpinner;
    private GridLayoutManager gridLayoutManager;
    private TextView tvSearchProductMatching;
    private int ShopNumbers=0,ProductNumber=0;
    private LinearLayout SearchLayout;
    private TextView tvSearch,tvNoOfProduct,tvNoOfProductGrey,tvNoOfShop,tvNoOfShopGrey,tvNearByText;
    private ArrayList<String> CategoryList,CategoryIDList;
    public Dialog dialog;
    private LoadingDialog loadingDialog;
    private ProductsByCategoryIdResponse responseSort;
    private ArrayList<String> brand_id,category_id,size,price,discount;

    public SearchProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_product, container, false);
        findId(view);
        return view;
    }

    private void findId(View view) {

        loadingDialog=new LoadingDialog(getActivity());

        brand_id=new ArrayList<>();
        category_id=new ArrayList<>();
        size=new ArrayList<>();
        price=new ArrayList<>();
        discount=new ArrayList<>();

        SearchLayout = view.findViewById(R.id.searchLayout_SearchFrag);
        tvSearch =  view.findViewById(R.id.tv_search_ecomm);
        tvNoOfProduct = view.findViewById(R.id.tv_number_of_product);
        tvNoOfProductGrey =  view.findViewById(R.id.tv_number_of_gray);
        tvNoOfShop =  view.findViewById(R.id.tv_number_of_store);
        tvNoOfShopGrey =  view.findViewById(R.id.tv_number_of_store_gray);
        liSort =  view.findViewById(R.id.li_sort);
        liFilter =  view.findViewById(R.id.li_filter);

        liProduct = view.findViewById(R.id.li_product);
        liProductGray = view.findViewById(R.id.li_product_gray);
        liStore = view.findViewById(R.id.li_store);
        liStoreGray = view.findViewById(R.id.li_store_gray);
        SearchProductList = view.findViewById(R.id.search_product_list);
        searchStoreList = view.findViewById(R.id.search_store_list);
        offerProductList = view.findViewById(R.id.offer_product_list);
        categorySpinner = view.findViewById(R.id.category_spinner_searchFrag);
        tvSearchProductMatching = view.findViewById(R.id.tv_search_product_matching);
        tvNearByText = view.findViewById(R.id.offerNearbyText);

        liProduct.setOnClickListener(this);
        liProductGray.setOnClickListener(this);
        liStore.setOnClickListener(this);
        liStoreGray.setOnClickListener(this);
        liSort.setOnClickListener(this);
        liFilter.setOnClickListener(this);

        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        SearchProductList.setLayoutManager(gridLayoutManager);

        offerProductList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        searchStoreList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos!=0){
                    if (isNetworkAvailable()){
                        getProductsByCategoryId(CategoryIDList.get(pos));
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.SearchProductLayout),"No Internet Connection!",true);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (isNetworkAvailable()){

            String valueSearch="",value="";

            try {
                value = getArguments().getString("CatId");
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                valueSearch = getArguments().getString("search");
            }catch (Exception e){
                e.printStackTrace();
            }

            if (TextUtils.isEmpty(valueSearch)){
                getProductsByCategoryId(value);
            }else {
                getProductsBySearch(valueSearch);
            }

        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.SearchProductLayout),"No Internet Connection!",true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {

                    if (!TextUtils.isEmpty(data.getStringExtra("search"))){
                        getProductsBySearch(data.getStringExtra("search"));
                    }

                    // TODO Update your TextView.
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

                    // TODO Update your TextView.
                }
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_product_gray:
                liProductGray.setVisibility(View.GONE);
                liStore.setVisibility(View.GONE);
                liProduct.setVisibility(View.VISIBLE);
                liStoreGray.setVisibility(View.VISIBLE);
                tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                searchStoreList.setVisibility(View.GONE);
                SearchProductList.setVisibility(View.VISIBLE);
                break;
            case R.id.li_store_gray:

                liStoreGray.setVisibility(View.GONE);
                liProduct.setVisibility(View.GONE);
                liStore.setVisibility(View.VISIBLE);
                liProductGray.setVisibility(View.VISIBLE);
                tvSearchProductMatching.setText("Found "+ShopNumbers+" shops matching, near you");
                searchStoreList.setVisibility(View.VISIBLE);
                SearchProductList.setVisibility(View.GONE);
                break;
            case R.id.li_sort:
                showSortingDialog(getActivity());
             break;
            case R.id.li_filter:
                Intent intent = new Intent(getActivity(), FilterDialog.class);
                intent.putExtra("Class","Product");
                startActivityForResult(intent,1);
                break;
        }
    }



    @Override
    public void onProductItemDetail(int position,ProductsByCategoryIdResponse.ProductsBean productsBean) {

        Toast.makeText(getActivity(), productsBean.getProduct_name(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), ProductDetailActivity.class));
    }

    @Override
    public void onShopItemClick(int position,String VenueID) {
        Fragment fragment=new ShopDetailFragment();
        if (fragment != null) {
            Bundle args = new Bundle();
            args.putString("VenueID", VenueID);
            fragment.setArguments(args);
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    private void showSortingDialog(FragmentActivity activity) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sort_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        TextView tvPriceLowToHigh=dialog.findViewById(R.id.priceLowToHigh);
        TextView tvPriceHighToLow=dialog.findViewById(R.id.priceHighToLow);
        TextView tvDiscount=dialog.findViewById(R.id.priceDiscountSort);

        tvPriceLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SortAscending();
            }
        });

        tvPriceHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SortDescending();
            }
        });

        tvDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SortDiscount();
            }
        });

        dialog.show();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getProductsByCategoryId(String CatId) {
        CategoryList=new ArrayList<>();
        CategoryIDList=new ArrayList<>();
        CategoryList.clear();
        CategoryIDList.clear();

        CategoryList.add("Change Category");
        CategoryIDList.add("0");

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        String Lat=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LAT);
        String Long=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LONG);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getProductsByCategoryId(CatId,Lat,Long)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductsByCategoryIdResponse>() {
                    @Override
                    public void onSuccess(ProductsByCategoryIdResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            responseSort=response;
                            if (response.getCategory().size()>0){
                                for (int j=0;response.getCategory().size()>j;j++){
                                    CategoryList.add(response.getCategory().get(j).getCat_name());
                                    CategoryIDList.add(""+response.getCategory().get(j).getId());
                                }

                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_ecom_search,CategoryList);
                                categorySpinner.setAdapter(arrayAdapter);
                            }else {
                                CategoryList.add("NA");
                                CategoryIDList.add("0");

                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_ecom_search,CategoryList);
                                categorySpinner.setAdapter(arrayAdapter);
                            }

                                if (response.getProducts().size()>0){
                                    serchProductAdapter=null;
                                    ProductNumber=response.getProducts().size();
                                    tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                                    tvNoOfProduct.setText(" "+response.getProducts().size());
                                    tvNoOfProductGrey.setText(" "+response.getProducts().size());

                                    serchProductAdapter = new SearchProductAdapter(getActivity(),responseSort.getProducts(),SearchProductFragment.this);
                                    SearchProductList.setAdapter(serchProductAdapter);
                                    serchProductAdapter.notifyDataSetChanged();
                                }else {
                                    tvNoOfProduct.setText("0");
                                    tvNoOfProductGrey.setText("0");
                                    ProductNumber=0;
                                    tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                                    BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.SearchProductLayout),"No Product Found",true);
                                }

                                if (response.getVenues().size()>0){
                                    ShopNumbers=response.getVenues().size();
                                    tvNoOfShop.setText(" "+response.getVenues().size());
                                    tvNoOfShopGrey.setText(" "+response.getVenues().size());

                                    storeListAdapter = new StoreListAdapter(getActivity(),responseSort.getVenues(),SearchProductFragment.this);
                                    searchStoreList.setAdapter(storeListAdapter);
                                }else {
                                    ShopNumbers=0;
                                    tvNoOfShop.setText("0");
                                    tvNoOfShopGrey.setText("0");
                                }

                                if (response.getProducts_offers().size()>0){
                                    offerAdapter=null;
                                    offerAdapter = new OfferSearchProductAdapter(getActivity(),responseSort.getProducts_offers(),SearchProductFragment.this);
                                    offerProductList.setAdapter(offerAdapter);
                                    offerAdapter.notifyDataSetChanged();
                                }else {
                                    offerProductList.setVisibility(View.GONE);
                                    tvNearByText.setVisibility(View.GONE);
                                }
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.SearchProductLayout),"No Product Found",true);
                            }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.SearchProductLayout), true, e);
                    }
                }));
    }

    private void getProductsBySearch(String search) {
        CategoryList=new ArrayList<>();
        CategoryIDList=new ArrayList<>();
        CategoryList.clear();
        CategoryIDList.clear();

        CategoryList.add("Change Category");
        CategoryIDList.add("0");

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        String Lat=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LAT);
        String Long=BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ECOMM_LONG);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getProductBySearch(search,Lat,Long)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductsByCategoryIdResponse>() {
                    @Override
                    public void onSuccess(ProductsByCategoryIdResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            responseSort=response;
                            if (response.getCategory().size()>0){
                                for (int j=0;response.getCategory().size()>j;j++){
                                    CategoryList.add(response.getCategory().get(j).getCat_name());
                                    CategoryIDList.add(""+response.getCategory().get(j).getId());
                                }

                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_ecom_search,CategoryList);
                                categorySpinner.setAdapter(arrayAdapter);
                            }else {
                                CategoryList.add("NA");
                                CategoryIDList.add("0");

                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_ecom_search,CategoryList);
                                categorySpinner.setAdapter(arrayAdapter);
                            }

                            if (response.getProducts().size()>0){
                                serchProductAdapter=null;
                                ProductNumber=response.getProducts().size();
                                tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                                tvNoOfProduct.setText(" "+response.getProducts().size());
                                tvNoOfProductGrey.setText(" "+response.getProducts().size());

                                serchProductAdapter = new SearchProductAdapter(getActivity(),responseSort.getProducts(),SearchProductFragment.this);
                                SearchProductList.setAdapter(serchProductAdapter);
                                serchProductAdapter.notifyDataSetChanged();
                            }else {
                                tvNoOfProduct.setText("0");
                                tvNoOfProductGrey.setText("0");
                                ProductNumber=0;
                                tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.SearchProductLayout),"No Product Found",true);
                            }

                            if (response.getVenues().size()>0){
                                ShopNumbers=response.getVenues().size();
                                tvNoOfShop.setText(" "+response.getVenues().size());
                                tvNoOfShopGrey.setText(" "+response.getVenues().size());

                                storeListAdapter = new StoreListAdapter(getActivity(),responseSort.getVenues(),SearchProductFragment.this);
                                searchStoreList.setAdapter(storeListAdapter);
                            }else {
                                ShopNumbers=0;
                                tvNoOfShop.setText("0");
                                tvNoOfShopGrey.setText("0");
                            }

                            if (response.getProducts_offers().size()>0){
                                offerAdapter=null;
                                offerAdapter = new OfferSearchProductAdapter(getActivity(),responseSort.getProducts_offers(),SearchProductFragment.this);
                                offerProductList.setAdapter(offerAdapter);
                                offerAdapter.notifyDataSetChanged();
                            }else {
                                offerProductList.setVisibility(View.GONE);
                                tvNearByText.setVisibility(View.GONE);
                            }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.SearchProductLayout),"No Product Found",true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.SearchProductLayout), true, e);
                    }
                }));
    }

    private void getProductByFilter(FilterRequest filterRequest) {
        CategoryList=new ArrayList<>();
        CategoryIDList=new ArrayList<>();
        CategoryList.clear();
        CategoryIDList.clear();

        CategoryList.add("Change Category");
        CategoryIDList.add("0");

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
                            responseSort=response;

                            categorySpinner.setVisibility(View.GONE);

                            if (response.getProducts().size()>0){
                                serchProductAdapter=null;
                                ProductNumber=response.getProducts().size();
                                tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                                tvNoOfProduct.setText(" "+response.getProducts().size());
                                tvNoOfProductGrey.setText(" "+response.getProducts().size());

                                serchProductAdapter = new SearchProductAdapter(getActivity(),responseSort.getProducts(),SearchProductFragment.this);
                                SearchProductList.setAdapter(serchProductAdapter);
                                serchProductAdapter.notifyDataSetChanged();
                            }else {
                                tvNoOfProduct.setText("0");
                                tvNoOfProductGrey.setText("0");
                            }

                            searchStoreList.setVisibility(View.GONE);
//                            if (response.getVenues().size()>0){
//                                ShopNumbers=response.getVenues().size();
//                                tvNoOfShop.setText(" "+response.getVenues().size());
//                                tvNoOfShopGrey.setText(" "+response.getVenues().size());
//
//                                storeListAdapter = new StoreListAdapter(getActivity(),responseSort.getVenues(),SearchProductFragment.this);
//                                searchStoreList.setAdapter(storeListAdapter);
//
//                            }else {
//                                tvNoOfShop.setText("0");
//                                tvNoOfShopGrey.setText("0");
//                            }


                            offerProductList.setVisibility(View.GONE);
                            tvNearByText.setVisibility(View.GONE);
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
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.SearchProductLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.SearchProductLayout), true, e);
                    }
                }));
    }


    @Override
    public void onProductItemDetail(int position, ProductsByCategoryIdResponse.ProductsOffersBean productsBean) {

        Toast.makeText(getActivity(), productsBean.getProduct_name(), Toast.LENGTH_SHORT).show();
    }

    private void SortDescending(){
        List<ProductsByCategoryIdResponse.ProductsBean> products=responseSort.getProducts();

        List<ProductsByCategoryIdResponse.ProductsBean> names= responseSort.getProducts();

        try {
            Collections.sort(responseSort.getProducts(), new Comparator<ProductsByCategoryIdResponse.ProductsBean>() {

                @Override
                public int compare(ProductsByCategoryIdResponse.ProductsBean price1, ProductsByCategoryIdResponse.ProductsBean price2) {
//                    if(Double.parseDouble(price1.getBuy_price()) > Double.parseDouble(price2.getBuy_price())) return 1;
//                    else if(Double.parseDouble(price1.getBuy_price()) < Double.parseDouble(price2.getBuy_price())) return -1;
//                    else return 0;  //Ascending order.
                    //return (lhs.compareToIgnoreCase(rhs)*(-1));//Descending order.
                    if(Double.parseDouble(price1.getBuy_price()) < Double.parseDouble(price2.getBuy_price()))
                        return -1;
                    return -1;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("products : "+products);
        System.out.println("Names : "+names);

//        serchProductAdapter = new SearchProductAdapter(getActivity(),names,SearchProductFragment.this);
//        SearchProductList.setAdapter(serchProductAdapter);
        serchProductAdapter.notifyDataSetChanged();
    }

    private void SortAscending(){

        try {
            Collections.sort(responseSort.getProducts(), new Comparator<ProductsByCategoryIdResponse.ProductsBean>() {
                @Override
                public int compare(ProductsByCategoryIdResponse.ProductsBean price1, ProductsByCategoryIdResponse.ProductsBean price2) {
                    if(Double.parseDouble(price1.getBuy_price()) > Double.parseDouble(price2.getBuy_price()))
                        return 1;
                    return 1;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        serchProductAdapter.notifyDataSetChanged();
    }

    private void SortDiscount(){

        try {
            Collections.sort(responseSort.getProducts(), new Comparator<ProductsByCategoryIdResponse.ProductsBean>() {
                @Override
                public int compare(ProductsByCategoryIdResponse.ProductsBean price1, ProductsByCategoryIdResponse.ProductsBean price2) {
                    if(price1.getDiscount_amount() < price2.getDiscount_amount())
                        return -1;
                    return -1;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        serchProductAdapter.notifyDataSetChanged();
    }
}
