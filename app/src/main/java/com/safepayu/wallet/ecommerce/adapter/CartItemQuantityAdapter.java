package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
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
import com.safepayu.wallet.ecommerce.model.response.AddressUserResponse;
import com.safepayu.wallet.ecommerce.model.response.CartsQuantityResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemQuantityAdapter  extends  RecyclerView.Adapter<CartItemQuantityAdapter.QuantityListViewHolder> {
    private Context context;
    private List<CartsQuantityResponse.CartsBean> cartsBeans;

    public CartItemQuantityAdapter(Context context, List<CartsQuantityResponse.CartsBean> cartsBeans) {
        this.context = context;
        this.cartsBeans = cartsBeans;
    }

    @NonNull
    @Override
    public QuantityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartitemquantity,parent,false);
        return new CartItemQuantityAdapter.QuantityListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuantityListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return cartsBeans.size();
    }

    public class QuantityListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvProductName, tvquantity;
        private ImageView imageCart;
        public QuantityListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvquantity = itemView.findViewById(R.id.quantity);
            imageCart = itemView.findViewById(R.id.image_cart);
        }

        public void bindData(int position) {

            tvProductName.setText(cartsBeans.get(position).getProduct_name());
            tvquantity.setText(", "+String.valueOf(cartsBeans.get(position).getQuantities()));

            try {
                if (TextUtils.isEmpty(ApiClientEcom.ImagePath+cartsBeans.get(position).getProduct_image())){
                }else {
                    Picasso.get()
                            .load(ApiClientEcom.ImagePath+cartsBeans.get(position).getProduct_image())
                            .error(context.getResources().getDrawable(R.drawable.image_not_available))
                            .into(imageCart); }
            }catch (Exception er){
                er.printStackTrace(); }
        }
    }
}
