package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class OrderCancelEcomAdapter extends RecyclerView.Adapter<OrderCancelEcomAdapter.ProductViewHolder> {


     private Context context;
    /* private OrderEcomListener orderEcomListener;

    public interface OrderEcomListener{
        void orderItem(int position);
    }*/

    public OrderCancelEcomAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ordercancel_ecom_adapter,parent,false);
        return new OrderCancelEcomAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvActualPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
        tvActualPrice = itemView.findViewById(R.id.tv_actualprice_myorder);
            tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
