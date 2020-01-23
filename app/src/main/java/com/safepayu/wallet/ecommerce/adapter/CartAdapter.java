package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
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
    String  PlusMinus;



    public interface CartSizeListener{
        void cartSizeItem(int position );
        void cartRemoveItem(int position );
        void onCartList(int position, TotalCartResponse.CartsBean cartsBean);
        void cartMoveItem(int position );
        void cartQuantityItem(int position ,TextView productQuantity,TotalCartResponse.CartsBean cartsBean ,int quantity);
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
        private  TextView tvDayName,tvDayHours ,productQuantity,cartAdapter,tvBuyQuantity, tvVenueName,tvActualprice,ProductName,tvSellingprice,cartRemove,cartMove,tvDiscount;
        private ImageView imMinus ,imPlus,ivCartImage;
        private LinearLayout liCartProduct;
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
            cartMove = itemView.findViewById(R.id.cart_move);
            tvDiscount = itemView.findViewById(R.id.tv_savers_myorder);
            tvVenueName = itemView.findViewById(R.id.tvVenueName);
            liCartProduct = itemView.findViewById(R.id.li_cart_product);


            imMinus.setOnClickListener(this);
            imPlus.setOnClickListener(this);
            cartAdapter.setOnClickListener(this);
            cartRemove.setOnClickListener(this);
            cartMove.setOnClickListener(this);
            tvActualprice.setPaintFlags(tvActualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            liCartProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartSizeListener != null) {
                        cartSizeListener.onCartList(getLayoutPosition(),cartsBeans.get(getLayoutPosition())); }
                }
            });
        }

        public void bindData(final int position) {

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

            tvBuyQuantity.setText(String.valueOf(cartsBeans.get(position).getQuantities()));
            productQuantity.setText(String.valueOf(cartsBeans.get(position).getQuantities()));

            tvVenueName.setText(cartsBeans.get(position).getVenue_name());



            if (cartsBeans.get(position).getOffer_id()!=0)
            {
                tvActualprice.setText("₹ "+cartsBeans.get(position).getSelling_price());
                String discountPercent = cartsBeans.get(position).getOffer_title();
                String percent[] = discountPercent.split(" ");
                String percent1 = percent[0];
                tvDiscount.setText(percent1 + "Off");
                if (cartsBeans.get(position).getOffer_type().equals("discper")){

                    Double b = ((Double.parseDouble(cartsBeans.get(position).getSelling_price())-((Double.parseDouble(cartsBeans.get(position).getSelling_price()))*(Double.parseDouble(cartsBeans.get(position).getDisc_per()))/100)));
                    tvSellingprice.setText("₹ " +String.format("%.3f", b)); }
                else if (cartsBeans.get(position).getOffer_type().equals("discamt")){
                    tvSellingprice.setText("₹ "+String.format("%.2f",(Double.parseDouble(cartsBeans.get(position).getSelling_price())- Double.parseDouble(cartsBeans.get(position).getDisc_amt())))); }
            }
            else {
                Double totalAmount = Double.parseDouble(cartsBeans.get(position).getSelling_price())* Double.parseDouble(productQuantity.getText().toString());
                tvSellingprice.setText("₹ "+String.valueOf(totalAmount));
                tvActualprice.setVisibility(View.GONE);
                tvDiscount.setVisibility(View.GONE);




                imMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PlusMinus = "plus";
                        avalQuantity= cartsBeans.get(position).getAvl_quantity();
                        quantity= Integer.parseInt(productQuantity.getText().toString());
                        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PLUS_MINUS,"minus");
                        quantity = quantity - 1;
                        display(quantity);

                    }
                });

                imPlus.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       PlusMinus = "minus";
                       avalQuantity= cartsBeans.get(position).getAvl_quantity();
                       quantity= Integer.parseInt(productQuantity.getText().toString());
                       if (avalQuantity>quantity){
                           BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().PLUS_MINUS,"plus");
                           quantity = quantity + 1;
                           display(quantity);

                       } else {
                           Toast.makeText(context,"Can not exceed more item", Toast.LENGTH_LONG).show();
                       }


                   }
               });
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.productChanegSize_cartAdapter:

                    if (cartSizeListener != null) {
                        cartSizeListener.cartSizeItem(getLayoutPosition()); }
                    break;

                case  R.id.cart_remove:
                    if (cartSizeListener != null) {
                        cartSizeListener.cartRemoveItem(getLayoutPosition()); }
                    break;
                case R.id.cart_move:
                    if (cartSizeListener != null) {
                        cartSizeListener.cartMoveItem(getLayoutPosition()); }
                    break;

            }
        }

          private void display(int quantity) {

            if (quantity==0){
                productQuantity.setText("" + 1);
                tvBuyQuantity.setText("" + 1);
                imMinus.setClickable(false);
            }
            else {

                productQuantity.setText("" + quantity);
                tvBuyQuantity.setText("" + quantity);


               if (PlusMinus.equals("plus")){
                    if (quantity== avalQuantity){
                        imPlus.setClickable(false); }
                    else {
                        imPlus.setClickable(true);
                        if (cartSizeListener != null) {
                            cartSizeListener.cartQuantityItem(getLayoutPosition(),productQuantity,cartsBeans.get(getLayoutPosition()), quantity); }
                    }
               }

                else  if (PlusMinus.equals("minus")){
                    if (quantity==1){
                        imMinus.setClickable(false); }
                    else {
                        imMinus.setClickable(true);
                        if (cartSizeListener != null) {
                            cartSizeListener.cartQuantityItem(getLayoutPosition(),productQuantity,cartsBeans.get(getLayoutPosition()), quantity); }
                    }
               }


             }
            }

    }
    }



