package com.safepayu.wallet.ecommerce.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.ParentCategoryAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.response.CategoriesResponse;
import com.safepayu.wallet.ecommerce.model.response.ParentCategoriesResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ParentCategory extends Fragment implements ParentCategoryAdapter.OnCategoryItemListener{

    private RecyclerView CategoryRecyclerView;
    private LoadingDialog loadingDialog;
    private ArrayList<JSONArray> SubCategoryIdList;
    private ArrayList<JSONArray> SubCategoryNameList;
    private ArrayList<String> ParentIdList;

    public ParentCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.parent_category_fragment, container, false);
        findId(view);
        return  view;
    }

    private void findId(View view) {
        loadingDialog=new LoadingDialog(getActivity());
        CategoryRecyclerView=view.findViewById(R.id.recycleCat_parentCategoryLayout);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        CategoryRecyclerView.setLayoutManager(gridLayoutManager);

        if (isNetworkAvailable()){
            getAllParentCategory();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.parentCategoryLayout),"No Internet Connection!",true);
        }
    }

    @Override
    public void onCategory(int position, ParentCategoriesResponse.CategoriesBean categoriesBean) {

        Bundle args = new Bundle();
        args.putString("CatId", String.valueOf(categoriesBean.getId()));

        SearchProductFragment fragment = new SearchProductFragment();
        fragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getAllParentCategory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAllParentCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ParentCategoriesResponse>() {
                    @Override
                    public void onSuccess(ParentCategoriesResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            if (response.getCategories().size()>0){
                                ParentCategoryAdapter adapter=new ParentCategoryAdapter(getActivity(),response.getCategories(),ParentCategory.this,"Cat");
                                CategoryRecyclerView.setAdapter(adapter);

                                getAllCategories();
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.parentCategoryLayout),"No Category Found!",true);
                            }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.parentCategoryLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.parentCategoryLayout), true, e);
                    }
                }));
    }

    private void getAllCategories() {
        SubCategoryIdList=new ArrayList<>();
        SubCategoryNameList=new ArrayList<>();
        ParentIdList=new ArrayList<>();

        SubCategoryNameList.clear();
        SubCategoryIdList.clear();
        ParentIdList.clear();

        //loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(getActivity()).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CategoriesResponse>() {
                    @Override
                    public void onSuccess(CategoriesResponse response) {
                        //loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            if (response.getCategories().size()>0){

                                for (int i=0;response.getCategories().size()>i;i++){
                                    int cat=response.getCategories().get(i).getParent_cat_id();
                                    if (!ParentIdList.contains(String.valueOf(cat))){
                                        ParentIdList.add(String.valueOf(cat));

                                        JSONArray jsonArrayId=new JSONArray();
                                        JSONArray jsonArrayName=new JSONArray();
                                        try {
                                            for (int j=0;response.getCategories().size()>j;j++){
                                                int catSub=response.getCategories().get(j).getParent_cat_id();
                                                if (cat==catSub){
                                                    try {
                                                        JSONObject jsonObjectID=new JSONObject();
                                                        JSONObject jsonObjectName=new JSONObject();
                                                        jsonObjectID.put("id",response.getCategories().get(j).getId());
                                                        jsonObjectName.put("name",response.getCategories().get(j).getCat_name());

                                                        jsonArrayId.put(new JSONObject().put("id",response.getCategories().get(j).getId()));
                                                        jsonArrayName.put(new JSONObject().put("name",response.getCategories().get(j).getCat_name()));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                            SubCategoryIdList.add(jsonArrayId);
                                            SubCategoryNameList.add(jsonArrayName);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.parentCategoryLayout),"No Category Found!",true);
                            }

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.parentCategoryLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.parentCategoryLayout), true, e);
                    }
                }));
    }
}
