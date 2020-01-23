package com.safepayu.wallet.ecommerce.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.OrderCancelEcomAdapter;
import com.safepayu.wallet.ecommerce.adapter.OrderItemEcomAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.CancelOrderRequest;
import com.safepayu.wallet.ecommerce.model.request.ReturnOrderRequest;
import com.safepayu.wallet.ecommerce.model.response.OrderDetailResponse;
import com.safepayu.wallet.models.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailEcomActivity extends AppCompatActivity implements View.OnClickListener, OrderItemEcomAdapter.CancelOrderAgainListener{

    private RecyclerView orderList,listOrderItemCancel;
    private TextView tvCancelReturn,tvCheckstatus,tvKeepShopping;
    private Button myorderEcomBackBtn,orderdetailEcomBackBtn ,btnSubmitGray,btnSubmit,OrderCancelBackBtn,cancelComfirmback;
    private OrderItemEcomAdapter orderItemEcomAdapter;
    private OrderCancelEcomAdapter orderCancelEcomAdapter;
    private String orderId="";
    public Dialog dialog;
    private LoadingDialog loadingDialog;

    private TextView tvOrderNo,tvOrderDate,tvMobile,tvEmail,tvName,tvAddress,tvModeOfPayment;
    private TextView tvMrp,tvBuyPrice,tvDiscount,tvShippingFee,tvTax,tvTotalAmout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_ecom);

        findId();

    }

    private void findId() {
        loadingDialog=new LoadingDialog(this);

        orderList = findViewById(R.id.list_order_item);
        tvCancelReturn = findViewById(R.id.tvcancel_return);
        orderdetailEcomBackBtn = findViewById(R.id.orderdetail_ecom_back_btn);

        tvMrp = findViewById(R.id.priceMrp_orderDetailLayout);
        tvBuyPrice = findViewById(R.id.priceBuy_orderDetailLayout);
        tvDiscount = findViewById(R.id.discount_orderDetailLayout);
        tvShippingFee = findViewById(R.id.shippingFee_orderDetailLayout);
        tvTax = findViewById(R.id.tax_orderDetailLayout);
        tvTotalAmout = findViewById(R.id.totalAmount_orderDetailLayout);

        tvOrderNo = findViewById(R.id.orderNumber_orderDetailLayout);
        tvOrderDate = findViewById(R.id.date_orderDetailLayout);
        tvMobile = findViewById(R.id.mobile_orderDetailLayout);
        tvEmail = findViewById(R.id.email_orderDetailLayout);
        tvName = findViewById(R.id.userName_orderDetailLayout);
        tvAddress = findViewById(R.id.address_orderDetailLayout);
        tvModeOfPayment = findViewById(R.id.modeOfPayment_orderDetailLayout);

        orderdetailEcomBackBtn.setOnClickListener(this);

        tvCancelReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOrderDetCancel(OrderDetailEcomActivity.this);
            }
        });

        orderList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        try {
            orderId=getIntent().getStringExtra("orderId");
        }catch (Exception e){
            e.printStackTrace();
        }

        if (isNetworkAvailable()){
            getOrderDetailsById();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),"No Internet Connection!",true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_cancel_return:


                break;
            case R.id.orderdetail_ecom_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.btn_submit:
                dialog.dismiss();
                showDialogOrderCancelConfirm(OrderDetailEcomActivity.this);
                break;
            case R.id.btn_submit_gray:
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmitGray.setVisibility(View.GONE);
                break;
            case R.id.cancel_ecom_back_btn:
                dialog.dismiss();
                break;
            case R.id.ordercancelcomfirm_ecom_back:
                startActivity(new Intent(OrderDetailEcomActivity.this, EHomeActivity.class));
                dialog.dismiss();
                break;
            case R.id.tv_checkstatus:
                startActivity(new Intent(OrderDetailEcomActivity.this, OrderDetailEcomActivity.class));
                dialog.dismiss();
                break;
            case R.id.tv_keep_shopping:
                startActivity(new Intent(OrderDetailEcomActivity.this, EHomeActivity.class));
                dialog.dismiss();
                finish();
                break;

        }
    }

    private void showDialogOrderDetCancel(OrderDetailEcomActivity orderDetailEcomActivity) {
        dialog = new Dialog(orderDetailEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_cancel_ecom_dialog);

        btnSubmitGray = dialog.findViewById(R.id.btn_submit_gray);
        btnSubmit = dialog.findViewById(R.id.btn_submit);
        OrderCancelBackBtn = dialog.findViewById(R.id.cancel_ecom_back_btn);
        listOrderItemCancel = dialog.findViewById(R.id.list_order_item_cancel);
        btnSubmitGray.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        OrderCancelBackBtn.setOnClickListener(this);

        listOrderItemCancel.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        orderCancelEcomAdapter = new OrderCancelEcomAdapter(getApplicationContext());
        listOrderItemCancel.setAdapter(orderCancelEcomAdapter);

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
    private void showDialogOrderCancelConfirm(OrderDetailEcomActivity orderDetailEcomActivity) {
        dialog = new Dialog(orderDetailEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_cancel_confirm_ecom_dialog);

        tvCheckstatus = dialog.findViewById(R.id.tv_checkstatus);
        tvKeepShopping = dialog.findViewById(R.id.tv_keep_shopping);
        cancelComfirmback = dialog.findViewById(R.id.ordercancelcomfirm_ecom_back);

        tvCheckstatus.setOnClickListener(this);
        cancelComfirmback.setOnClickListener(this);
        tvKeepShopping.setOnClickListener(this);

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

    private void getOrderDetailsById() {

        loadingDialog.showDialog(getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getOrderDetailsById(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OrderDetailResponse>() {
                    @Override
                    public void onSuccess(OrderDetailResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            try {
                                tvOrderNo.setText(response.getOrderList().getUnique_code());
                                tvOrderDate.setText(response.getOrderList().getOrder_date());
                                tvMobile.setText(response.getOrderList().getMobile());
                                tvEmail.setText(response.getOrderList().getEmail());
                                tvName.setText(response.getOrderList().getName());
                                tvAddress.setText(response.getOrderList().getDelivery_address());
                                tvModeOfPayment.setText(response.getOrderList().getPayment_mode());

                                tvTax.setText(getResources().getString(R.string.rupees)+" "+response.getOrderList().getTotal_tax());
                                tvMrp.setText(getResources().getString(R.string.rupees)+" "+response.getOrderList().getMrp());
                                tvBuyPrice.setText(getResources().getString(R.string.rupees)+" "+response.getOrderList().getNet_amount());
                                tvDiscount.setText(getResources().getString(R.string.rupees)+" "+response.getOrderList().getTotal_discount());
                                tvShippingFee.setText(getResources().getString(R.string.rupees)+" "+response.getOrderList().getDelivery_charge());
                                tvTotalAmout.setText(getResources().getString(R.string.rupees)+" "+response.getOrderList().getNet_amount());

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            orderItemEcomAdapter = new OrderItemEcomAdapter(getApplicationContext(),response.getOrderList().getProducts(),OrderDetailEcomActivity.this);
                            orderList.setAdapter(orderItemEcomAdapter);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),"No Product Found",true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.orderDetailLayout), true, e);
                    }
                }));
    }

    @Override
    public void cancelOrderAgainItem(int position, String Module, OrderDetailResponse.OrderListBean.ProductsBean productsBean) {
        if (Module.equalsIgnoreCase("Cancel")){
            Toast.makeText(this, productsBean.getProduct_name()+" Cancel", Toast.LENGTH_SHORT).show();

            showMessage("Cancel Order","Are You Sure You Want To Cancel "+productsBean.getProduct_name(), productsBean,Module);

        }else if (Module.equalsIgnoreCase("Return")){
            Toast.makeText(this, productsBean.getProduct_name()+" Return", Toast.LENGTH_SHORT).show();

            showMessage("Return Order","Are You Sure You Want To Return "+productsBean.getProduct_name(),productsBean, Module);

        }else {
            Toast.makeText(this, productsBean.getProduct_name()+" Order Again", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCancelOrder(CancelOrderRequest cancelOrderRequest) {

        loadingDialog.showDialog(getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getCancelOrder(cancelOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),response.getMessage(),true);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.orderDetailLayout), true, e);
                    }
                }));
    }

    private void getReturnOrder(ReturnOrderRequest returnOrderRequest) {

        loadingDialog.showDialog(getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getReturnOrder(returnOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),response.getMessage(),true);
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.orderDetailLayout), true, e);
                    }
                }));
    }

    public void showMessage(String Title, String Message,final OrderDetailResponse.OrderListBean.ProductsBean productsBean, final String module) {
        new AlertDialog.Builder(OrderDetailEcomActivity.this)
                .setTitle(Title)
                .setMessage(Message)
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        if (module.equalsIgnoreCase("Cancel")){
                            List<String> order_details_ids=new ArrayList<>();
                            order_details_ids.add(""+productsBean.getOrder_details_id());

                            CancelOrderRequest cancelOrderRequest=new CancelOrderRequest();
                            cancelOrderRequest.setOrder_id(""+productsBean.getOrder_id());
                            cancelOrderRequest.setOrder_details_ids(order_details_ids);

                            if (isNetworkAvailable()){
                                getCancelOrder(cancelOrderRequest);
                            }else {
                                Toast.makeText(OrderDetailEcomActivity.this, "No Internet Connection!", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            if (productsBean.getProduct_qty()==1){
                                List<String> return_qty=new ArrayList<>();
                                return_qty.add("1");
                                List<String> order_details_ids=new ArrayList<>();
                                order_details_ids.add(""+productsBean.getOrder_details_id());

                                ReturnOrderRequest returnOrderRequest=new ReturnOrderRequest();
                                returnOrderRequest.setOrder_id(""+productsBean.getOrder_id());
                                returnOrderRequest.setOrder_details_ids(order_details_ids);
                                returnOrderRequest.setReturn_qty(return_qty);

                                if (returnOrderRequest.getReturn_qty().size()>0){
                                    if (isNetworkAvailable()){
                                        getReturnOrder(returnOrderRequest);
                                    }else {
                                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),"No Internet Connection!",true);
                                    }
                                }
                            }else {
                                showDialogForQuantity(productsBean);
                            }

                        }
                        dialog.dismiss();

                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }

    public void showDialogForQuantity(final OrderDetailResponse.OrderListBean.ProductsBean productsBean) {
        final Dialog dialog = new Dialog(OrderDetailEcomActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.product_quantity_dialog);

        final List<String> return_qty=new ArrayList<>();
        TextView tvProductName = dialog.findViewById(R.id.productName_productQuantityDialog);
        TextView tvProductQqty = dialog.findViewById(R.id.productQuantity_productQuantityDialog);
        final EditText edQuantity = dialog.findViewById(R.id.qqty_productQuantityDialog);

        tvProductName.setText(productsBean.getProduct_name());
        tvProductQqty.setText(""+productsBean.getProduct_qty());

        Button proceedButton = dialog.findViewById(R.id.ProdceedBtn_productQuantityDialog);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qqty= Integer.parseInt(edQuantity.getText().toString().trim());

                if (qqty==0){
                    Toast.makeText(OrderDetailEcomActivity.this, "Please Enter Quantity", Toast.LENGTH_LONG).show();
                }else {
                    if (qqty>productsBean.getProduct_qty()){
                        Toast.makeText(OrderDetailEcomActivity.this, "Product Quantity Exceeded", Toast.LENGTH_LONG).show();
                    }else {
                        return_qty.add(""+qqty);

                        List<String> order_details_ids=new ArrayList<>();
                        order_details_ids.add(""+productsBean.getOrder_details_id());

                        ReturnOrderRequest returnOrderRequest=new ReturnOrderRequest();
                        returnOrderRequest.setOrder_id(""+productsBean.getOrder_id());
                        returnOrderRequest.setOrder_details_ids(order_details_ids);
                        returnOrderRequest.setReturn_qty(return_qty);

                        if (returnOrderRequest.getReturn_qty().size()>0){
                            if (isNetworkAvailable()){
                                getReturnOrder(returnOrderRequest);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.orderDetailLayout),"No Internet Connection!",true);
                            }
                        }
                    }
                }
            }
        });

        Button cancelBtn_appUpdate =  dialog.findViewById(R.id.cancelBtn_productQuantityDialog);
        cancelBtn_appUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}
