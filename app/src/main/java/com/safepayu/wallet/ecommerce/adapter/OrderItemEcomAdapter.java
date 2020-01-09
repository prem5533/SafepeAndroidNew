package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class OrderItemEcomAdapter extends RecyclerView.Adapter<OrderItemEcomAdapter.ProductViewHolder> {


     private Context context;
    /* private OrderEcomListener orderEcomListener;

    public interface OrderEcomListener{
        void orderItem(int position);
    }*/

    public OrderItemEcomAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_ecom_adapter,parent,false);
        return new OrderItemEcomAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOrderDetail;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  /*  if (orderEcomListener != null) {
                        orderEcomListener.orderItem(getLayoutPosition()); }*/
                }
            });
        }
    }
}
