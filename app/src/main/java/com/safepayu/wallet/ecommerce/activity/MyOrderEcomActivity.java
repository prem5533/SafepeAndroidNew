package com.safepayu.wallet.ecommerce.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.MyOrderEcomAdapter;
import com.safepayu.wallet.ecommerce.adapter.OrderItemEcomAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.ReviewRequest;
import com.safepayu.wallet.ecommerce.model.response.MyOrderListResponse;
import com.safepayu.wallet.models.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MyOrderEcomActivity extends AppCompatActivity implements View.OnClickListener, MyOrderEcomAdapter.OrderEcomListener,
                                MyOrderEcomAdapter.RatingListener{
    private RecyclerView myorderListEcom;
    private MyOrderEcomAdapter myOrderEcomAdapter;
    private OrderItemEcomAdapter orderItemEcomAdapter;
    private Button myorderEcomBackBtn,orderdetailEcomBackBtn ,btnSubmitGray,btnSubmit,OrderCancelBackBtn;
    private RatingBar ratingBar;
    public  Dialog dialog;
    private TextView tvCancelReturn,tvCheckstatus;
    private RecyclerView orderList;
    private LoadingDialog loadingDialog;
    private List<MyOrderListResponse.OrderListBean.ProductsBean> productList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_ecom);
        findId();
    }

    private void findId() {
        loadingDialog=new LoadingDialog(this);
        myorderListEcom = findViewById(R.id.myorder_list_ecom);
        myorderEcomBackBtn = findViewById(R.id.myorder_ecom_back_btn);

        myorderEcomBackBtn.setOnClickListener(this);

        myorderListEcom.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        if (isNetworkAvailable()){
            getEcommOrdersList();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.myOrderListLayout),"No Internet Connection",true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myorder_ecom_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.orderdetail_ecom_back_btn:
               dialog.dismiss();
                break;

        }
    }

    @Override
    public void orderItem(int position,String Order_id) {

        Intent intent=new Intent(MyOrderEcomActivity.this,OrderDetailEcomActivity.class);
        intent.putExtra("orderId",Order_id);
        startActivity(intent);

        //showDialogOrderDetail(MyOrderEcomActivity.this);
    }

    private void showDialogOrderDetail(MyOrderEcomActivity myOrderEcomActivity) {
        dialog = new Dialog(myOrderEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_detail_ecom_dialog);

        orderList = dialog.findViewById(R.id.list_order_item);
        tvCancelReturn = dialog.findViewById(R.id.tv_cancel_return);
        orderdetailEcomBackBtn = dialog.findViewById(R.id.orderdetail_ecom_back_btn);
        orderdetailEcomBackBtn.setOnClickListener(this);
        tvCancelReturn.setOnClickListener(this);

        orderList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        orderItemEcomAdapter = new OrderItemEcomAdapter(getApplicationContext());
//        orderList.setAdapter(orderItemEcomAdapter);

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
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getEcommOrdersList() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getEcommOrdersList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MyOrderListResponse>() {
                    @Override
                    public void onSuccess(MyOrderListResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            for (int i=0;response.getOrderList().size()>i;i++){
                                for (int j=0;response.getOrderList().get(i).getProducts().size()>j;j++){
                                    MyOrderListResponse.OrderListBean.ProductsBean productsBean=new MyOrderListResponse.OrderListBean.ProductsBean();

                                    productsBean.setOrder_details_id(response.getOrderList().get(i).getProducts().get(j).getOrder_details_id());
                                    productsBean.setProduct_name(response.getOrderList().get(i).getProducts().get(j).getProduct_name());
                                    productsBean.setProduct_image(response.getOrderList().get(i).getProducts().get(j).getProduct_image());
                                    productsBean.setProduct_qty(response.getOrderList().get(i).getProducts().get(j).getProduct_qty());
                                    productsBean.setProduct_id(response.getOrderList().get(i).getProducts().get(j).getProduct_id());
                                    productsBean.setNet_amount(response.getOrderList().get(i).getProducts().get(j).getNet_amount());
                                    productsBean.setRattings(response.getOrderList().get(i).getProducts().get(j).getRattings());
                                    productsBean.setReview(response.getOrderList().get(i).getProducts().get(j).getReview());
                                    productsBean.setOrder_id(response.getOrderList().get(i).getProducts().get(j).getOrder_id());

                                    productsBean.setUnique_code(response.getOrderList().get(i).getUnique_code());
                                    productsBean.setVenue_id(response.getOrderList().get(i).getVenue_id());
                                    productsBean.setVenue_name(response.getOrderList().get(i).getVenue_name());
                                    productsBean.setMerchant_id(response.getOrderList().get(i).getMerchant_id());
                                    productsBean.setOrder_status(response.getOrderList().get(i).getOrder_status());
                                    productsBean.setDelivery_date(response.getOrderList().get(i).getDelivery_date());
                                    //productsBean.setNet_amountX(response.getOrderList().get(i).getNet_amount());

                                    productList.add(productsBean);
                                }
                            }
                            myOrderEcomAdapter = new MyOrderEcomAdapter(getApplicationContext(),productList,MyOrderEcomActivity.this,MyOrderEcomActivity.this);
                            myorderListEcom.setAdapter(myOrderEcomAdapter);

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.myOrderListLayout),"No Order Found",true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.myOrderListLayout), true, e);
                    }
                }));
    }

    @Override
    public void ratingItem(int position,MyOrderListResponse.OrderListBean.ProductsBean productsBean,ReviewRequest reviewRequest) {

        if (isNetworkAvailable()){
            getSaveReviewRat(position,reviewRequest);
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.myOrderListLayout),"No Internet Connection",true);
        }
    }

    private void getSaveReviewRat(final int position,final ReviewRequest reviewRequest) {

        loadingDialog.showDialog(getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getSaveReviewRat(reviewRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            productList.get(position).setRattings(reviewRequest.getRating());
                            myOrderEcomAdapter.notifyDataSetChanged();
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.myOrderListLayout),response.getMessage(),true);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.myOrderListLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.myOrderListLayout), true, e);
                    }
                }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
