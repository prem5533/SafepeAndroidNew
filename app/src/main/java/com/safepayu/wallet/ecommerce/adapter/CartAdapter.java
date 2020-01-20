package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.TotalCartResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.FlightLocationListViewHolder> {

    int quantity = 1 ,avalQuantity;
    private Context context ;
    private CartSizeListener cartSizeListener;
    private List<TotalCartResponse.CartsBean> cartsBeans;


    public interface CartSizeListener{
        void cartSizeItem(int position );
    }

    public CartAdapter(Context context, CartSizeListener cartSizeListener, List<TotalCartResponse.CartsBean> cartsBeans) {
        this.context = context;
        this.cartSizeListener = cartSizeListener;
        this.cartsBeans = cartsBeans;
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
        return cartsBeans.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvDayName,tvDayHours ,productQuantity,cartAdapter,tvActualprice,ProductName,tvSellingprice,tvBuyQuantity,cartRemove;
        private ImageView imMinus ,imPlus,ivCartImage;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);

            imMinus = itemView.findViewById(R.id.im_minus);
            imPlus = itemView.findViewById(R.id.im_plus);
            productQuantity = itemView.findViewById(R.id.productQuantity_cartAdapter);
            tvSellingprice = itemView.findViewById(R.id.tv_offprice_myorder);
            ProductName = itemView.findViewById(R.id.tv_product_name_myorder);
            cartAdapter = itemView.findViewById(R.id.productChanegSize_cartAdapter);
            tvActualprice = itemView.findViewById(R.id.tv_actualprice_myorder);
            ivCartImage = itemView.findViewById(R.id.iv_cart_image);
            tvBuyQuantity = itemView.findViewById(R.id.tv_quantity);
            cartRemove = itemView.findViewById(R.id.cart_remove);
            imMinus.setOnClickListener(this);
            imPlus.setOnClickListener(this);
            cartAdapter.setOnClickListener(this);
            cartRemove.setOnClickListener(this);
            tvActualprice.setPaintFlags(tvActualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        public void bindData(int position) {

            try {
                if (TextUtils.isEmpty(ApiClientEcom.ImagePath+cartsBeans.get(position).getProduct_image())){
                }else {
                    Picasso.get()
                            .load(ApiClientEcom.ImagePath+cartsBeans.get(position).getProduct_image())
                            .error(context.getResources().getDrawable(R.drawable.image_not_available))
                            .into(ivCartImage); }
            }catch (Exception er){
                er.printStackTrace(); }

            ProductName.setText(cartsBeans.get(position).getProduct_name());
            tvSellingprice.setText(cartsBeans.get(position).getSelling_price());
            tvBuyQuantity.setText(String.valueOf(cartsBeans.get(position).getQuantities()));
            avalQuantity=     cartsBeans.get(position).getAvl_quantity();
        /*    if ((quantity == cartsBeans.get(position).getAvl_quantity()) && (quantity < cartsBeans.get(position).getAvl_quantity())){
                imPlus.setClickable(false);
            }
            else {
                imPlus.setClickable(true);
            }*/
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.im_minus:

                    display(quantity);
                    quantity = quantity - 1;

                    break;
                case R.id.im_plus:

                    quantity = quantity + 1;
                    display(quantity);
                    break;
                case R.id.productChanegSize_cartAdapter:

                    if (cartSizeListener != null) {
                        cartSizeListener.cartSizeItem(getLayoutPosition()); }
                    break;

                case  R.id.cart_remove:

                    break;

            }
        }
        private void display(int quantity) {
            productQuantity.setText("" + quantity);
            if (quantity== avalQuantity){
                imPlus.setClickable(false); }
            else {
                imPlus.setClickable(true); }
            if (quantity==1){
                imMinus.setClickable(false); }
            else {
                imMinus.setClickable(true); }
        }
    }


}
