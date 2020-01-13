package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class MyOrderEcomAdapter extends RecyclerView.Adapter<MyOrderEcomAdapter.ProductViewHolder> {


     private Context context;
     private OrderEcomListener orderEcomListener;

    public interface OrderEcomListener{
        void orderItem(int position );
    }

    public MyOrderEcomAdapter(Context context, OrderEcomListener orderEcomListener) {
        this.context = context;
        this.orderEcomListener = orderEcomListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_ecom_adapter,parent,false);
        return new MyOrderEcomAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOrderDetail,tvWriteReview;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderDetail = itemView.findViewById(R.id.tv_order_detail);
            tvWriteReview = itemView.findViewById(R.id.tv_write_review);

            tvOrderDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (orderEcomListener != null) {
                        orderEcomListener.orderItem(getLayoutPosition());
                    }
                }
            });

            tvWriteReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (orderEcomListener != null) {
                        orderEcomListener.orderItem(getLayoutPosition());
                    }
                }
            });
        }
    }
}
