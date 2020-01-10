package com.safepayu.wallet.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.OrderCancelEcomAdapter;
import com.safepayu.wallet.ecommerce.adapter.OrderItemEcomAdapter;
import com.safepayu.wallet.ecommerce.fragment.HomeFragment;
import com.safepayu.wallet.ecommerce.fragment.SearchProductFragment;

public class OrderDetailEcomActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView orderList,listOrderItemCancel;
    private TextView tvCancelReturn,tvCheckstatus,tvKeepShopping;
    private Button myorderEcomBackBtn,orderdetailEcomBackBtn ,btnSubmitGray,btnSubmit,OrderCancelBackBtn,cancelComfirmback;
    private OrderItemEcomAdapter orderItemEcomAdapter;
    private OrderCancelEcomAdapter orderCancelEcomAdapter;
    public Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_ecom);

        findId();

    }

    private void findId() {
        orderList = findViewById(R.id.list_order_item);
        tvCancelReturn = findViewById(R.id.tv_cancel_return);
        orderdetailEcomBackBtn = findViewById(R.id.orderdetail_ecom_back_btn);
        orderdetailEcomBackBtn.setOnClickListener(this);
        tvCancelReturn.setOnClickListener(this);

        orderList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        orderItemEcomAdapter = new OrderItemEcomAdapter(getApplicationContext());
        orderList.setAdapter(orderItemEcomAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_cancel_return:
                showDialogOrderDetCancel(OrderDetailEcomActivity.this);

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


}
