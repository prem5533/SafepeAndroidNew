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
import com.safepayu.wallet.ecommerce.adapter.OfferAdapter;
import com.safepayu.wallet.ecommerce.adapter.SearchProductAdapter;
import com.safepayu.wallet.ecommerce.adapter.StoreListAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;
import com.safepayu.wallet.models.response.OperatorResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProductFragment extends Fragment implements View.OnClickListener, SearchProductAdapter.OnProductDetailItemListener,
                StoreListAdapter.ShopItemListListener{

    private LinearLayout liProduct ,liProductGray, liStore,liStoreGray,liSort,liFilter;
    private RecyclerView SearchProductList,searchStoreList,offerProductList;
    private SearchProductAdapter serchProductAdapter;
    private StoreListAdapter storeListAdapter;
    private Spinner categorySpinner;
    private GridLayoutManager gridLayoutManager;
    private TextView tvSearchProductMatching;
    List<OperatorResponse.OperatorsBean> mCategoryList = new ArrayList<>();
    private int ShopNumbers=0,ProductNumber=0;
    private LinearLayout SearchLayout;
    private TextView tvSearch,tvNoOfProduct,tvNoOfProductGrey,tvNoOfShop,tvNoOfShopGrey;
    private ArrayList<String> CategoryList,CategoryIDList;
    public Dialog dialog;
    private LoadingDialog loadingDialog;

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



        liProduct.setOnClickListener(this);
        liProductGray.setOnClickListener(this);
        liStore.setOnClickListener(this);
        liStoreGray.setOnClickListener(this);
        liSort.setOnClickListener(this);
        liFilter.setOnClickListener(this);

        gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        SearchProductList.setLayoutManager(gridLayoutManager);

//        EcomSpinnerAdapter customAdapter=new EcomSpinnerAdapter(getActivity(),mCategoryList);
//        categorySpinner.setAdapter(customAdapter);

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
                startActivity(intent);
                 break;
        }
    }



    @Override
    public void onProductItemDetail(int position,ProductsByCategoryIdResponse.ProductsBean productsBean) {

        Toast.makeText(getActivity(), productsBean.getProduct_name(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), ProductDetailActivity.class));
    }

    @Override
    public void onShopItemClick(int position) {
        Fragment fragment=new ShopDetailFragment();
        if (fragment != null) {
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

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getProductsByCategoryId(CatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductsByCategoryIdResponse>() {
                    @Override
                    public void onSuccess(ProductsByCategoryIdResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

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
                                ProductNumber=response.getProducts().size();
                                tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                                tvNoOfProduct.setText(" "+response.getProducts().size());
                                tvNoOfProductGrey.setText(" "+response.getProducts().size());

                                serchProductAdapter = new SearchProductAdapter(getActivity(),response.getProducts(),SearchProductFragment.this);
                                SearchProductList.setAdapter(serchProductAdapter);
                            }else {
                                tvNoOfProduct.setText("0");
                                tvNoOfProductGrey.setText("0");
                            }

                            if (response.getVenues().size()>0){
                                ShopNumbers=response.getVenues().size();
                                tvNoOfShop.setText(" "+response.getVenues().size());
                                tvNoOfShopGrey.setText(" "+response.getVenues().size());

                                storeListAdapter = new StoreListAdapter(getActivity(),response.getVenues(),SearchProductFragment.this);
                                searchStoreList.setAdapter(storeListAdapter);
                            }else {
                                tvNoOfShop.setText("0");
                                tvNoOfShopGrey.setText("0");
                            }

                            if (response.getProducts_offers().size()>0){

                                OfferAdapter offerAdapter = new OfferAdapter(getActivity(),CategoryList,CategoryList);
                                offerProductList.setAdapter(offerAdapter);
                            }else {

                            }

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

    private void getProductsBySearch(String search) {
        CategoryList=new ArrayList<>();
        CategoryIDList=new ArrayList<>();
        CategoryList.clear();
        CategoryIDList.clear();

        CategoryList.add("Change Category");
        CategoryIDList.add("0");

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getProductBySearch(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductsByCategoryIdResponse>() {
                    @Override
                    public void onSuccess(ProductsByCategoryIdResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

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
                                ProductNumber=response.getProducts().size();
                                tvSearchProductMatching.setText("Found "+ProductNumber+" products matching, near you");
                                tvNoOfProduct.setText(" "+response.getProducts().size());
                                tvNoOfProductGrey.setText(" "+response.getProducts().size());

                                serchProductAdapter = new SearchProductAdapter(getActivity(),response.getProducts(),SearchProductFragment.this);
                                SearchProductList.setAdapter(serchProductAdapter);
                            }else {
                                tvNoOfProduct.setText("0");
                                tvNoOfProductGrey.setText("0");
                            }

                            if (response.getVenues().size()>0){
                                ShopNumbers=response.getVenues().size();
                                tvNoOfShop.setText(" "+response.getVenues().size());
                                tvNoOfShopGrey.setText(" "+response.getVenues().size());

                                storeListAdapter = new StoreListAdapter(getActivity(),response.getVenues(),SearchProductFragment.this);
                                searchStoreList.setAdapter(storeListAdapter);
                            }else {
                                tvNoOfShop.setText("0");
                                tvNoOfShopGrey.setText("0");
                            }

//                            if (response.getProducts_offers().size()>0){
//
//                                OfferAdapter offerAdapter = new OfferAdapter(getActivity(),CategoryList,CategoryList);
//                                offerProductList.setAdapter(offerAdapter);
//                            }else {
//
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
}
