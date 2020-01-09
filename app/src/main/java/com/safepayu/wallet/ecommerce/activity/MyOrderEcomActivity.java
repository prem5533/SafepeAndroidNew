package com.safepayu.wallet.ecommerce.activity;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.ChangeAddressAdapter;
import com.safepayu.wallet.ecommerce.adapter.MyOrderEcomAdapter;
import com.safepayu.wallet.ecommerce.adapter.OrderItemEcomAdapter;

public class MyOrderEcomActivity extends AppCompatActivity implements View.OnClickListener, MyOrderEcomAdapter.OrderEcomListener {
    private RecyclerView myorderListEcom;
    private MyOrderEcomAdapter myOrderEcomAdapter;
    private OrderItemEcomAdapter orderItemEcomAdapter;
    private Button myorderEcomBackBtn,orderdetailEcomBackBtn ;
    private RatingBar ratingBar;
    public   Dialog dialog;
    private RecyclerView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_ecom);
        findId();
    }

    private void findId() {
        myorderListEcom = findViewById(R.id.myorder_list_ecom);
        myorderEcomBackBtn = findViewById(R.id.myorder_ecom_back_btn);
        ratingBar = findViewById(R.id.rating_myorder_ecom);

        myorderEcomBackBtn.setOnClickListener(this);

        myorderListEcom.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        myOrderEcomAdapter = new MyOrderEcomAdapter(getApplicationContext(),MyOrderEcomActivity.this);
        myorderListEcom.setAdapter(myOrderEcomAdapter);


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
    public void orderItem(int position ) {
       // startActivity(new Intent(MyOrderEcomActivity.this, OrderDetailEcomActivity.class));
        showDialogOrderDetail(MyOrderEcomActivity.this);
    }

    private void showDialogOrderDetail(MyOrderEcomActivity myOrderEcomActivity) {
        dialog = new Dialog(myOrderEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_detail_ecom_dialog);

        orderList = dialog.findViewById(R.id.list_order_item);
        orderdetailEcomBackBtn = dialog.findViewById(R.id.orderdetail_ecom_back_btn);
        orderdetailEcomBackBtn.setOnClickListener(this);

        orderList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        orderItemEcomAdapter = new OrderItemEcomAdapter(getApplicationContext());
        orderList.setAdapter(orderItemEcomAdapter);

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
