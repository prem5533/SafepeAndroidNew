package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.FlightLocationListViewHolder> {

    int quantity = 1;
    private Context context ;
    private CartSizeListener cartSizeListener;

    public interface CartSizeListener{
        void cartSizeItem(int position );
    }

    public CartAdapter(Context context, CartSizeListener cartSizeListener) {
        this.context = context;
        this.cartSizeListener = cartSizeListener;
    }

    @NonNull
    @Override
    public CartAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_adapter,parent,false);
        return new CartAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvDayName,tvDayHours ,productQuantity,cartAdapter,tvActualprice;
        private ImageView imMinus ,imPlus;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);

            imMinus = itemView.findViewById(R.id.im_minus);
            imPlus = itemView.findViewById(R.id.im_plus);
            productQuantity = itemView.findViewById(R.id.productQuantity_cartAdapter);
            cartAdapter = itemView.findViewById(R.id.productChanegSize_cartAdapter);
            tvActualprice = itemView.findViewById(R.id.tv_actualprice_myorder);
            imMinus.setOnClickListener(this);
            imPlus.setOnClickListener(this);
            cartAdapter.setOnClickListener(this);
            tvActualprice.setPaintFlags(tvActualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        public void bindData(int position) {

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.im_minus:

                    quantity = quantity - 1;
                    display(quantity);

                    if(quantity==1){
                        imMinus.setClickable(false);}
                    else {
                        imMinus.setClickable(true);
                    }
                    break;
                case R.id.im_plus:

                    quantity = quantity + 1;
                    display(quantity);
                    break;
                case R.id.productChanegSize_cartAdapter:

                    if (cartSizeListener != null) {
                        cartSizeListener.cartSizeItem(getLayoutPosition());

                    }
                    break;
            }
        }
        private void display(int quantity) {
            productQuantity.setText("" + quantity);
        }
    }


}
