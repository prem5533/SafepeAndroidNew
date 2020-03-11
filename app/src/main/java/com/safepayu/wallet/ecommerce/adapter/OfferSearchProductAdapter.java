package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferSearchProductAdapter extends RecyclerView.Adapter<OfferSearchProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductsByCategoryIdResponse.ProductsOffersBean> offerItem;
    private OnProductDetailItemListener onProductDetailItemListener;

    public interface OnProductDetailItemListener{
        void onProductItemDetail(int position,ProductsByCategoryIdResponse.ProductsOffersBean productsBean);
    }

    public OfferSearchProductAdapter(Context context, List<ProductsByCategoryIdResponse.ProductsOffersBean> offerItem,OnProductDetailItemListener onProductDetailItemListener) {
        this.context = context;
        this.offerItem = offerItem;
        this.onProductDetailItemListener=onProductDetailItemListener;
    }

    @NonNull
    @Override
    public OfferSearchProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_adapter,parent,false);
        return new OfferSearchProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferSearchProductAdapter.ProductViewHolder holder, int position) {

        try {
            if (TextUtils.isEmpty(ApiClientEcom.ImagePath+offerItem.get(position).getProduct_images())){
            }else {
                Picasso.get()
                        .load(ApiClientEcom.ImagePath+offerItem.get(position).getProduct_images())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.imageView); }
        }catch (Exception er){
            er.printStackTrace(); }

        holder.tvProductName.setText(offerItem.get(position).getProduct_name());
        holder.tvActualRs.setText("₹ "+offerItem.get(position).getSelling_price());
        holder.tvActualRs.setPaintFlags(holder.tvActualRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.listItemRating.setRating((int)offerItem.get(position).getStars());
        holder.tvProductDetail.setText(offerItem.get(position).getProduct_description());
        holder.tvProductStorenme.setText(offerItem.get(position).getVenue_name());
        holder.tv_pstore_km.setText(offerItem.get(position).getDistance()+" km");

        String discountPercent = offerItem.get(position).getOffer_title();
        String percent[] = discountPercent.split(" ");
        String percent1 = percent[0];
        holder.tvDiscount.setText(percent1);

        if (offerItem.get(position).getOffer_type().equals("discper")){
          /*  Double b = ((Double.parseDouble(offerItem.get(position).getSelling_price())-(((Double.parseDouble(offerItem.get(position).getSelling_price()))*(Double.parseDouble(offerItem.get(position).getDisc_per())))/100)));
            holder.tvSellingPrice.setText("₹ "+String.valueOf(b));*/
            Double b = ((Double.parseDouble(offerItem.get(position).getSelling_price())-((Double.parseDouble(offerItem.get(position).getSelling_price()))*(Double.parseDouble(offerItem.get(position).getDisc_per()))/100)));
            holder.tvSellingPrice.setText("₹ " +String.format("%.3f", b));

        } else if (offerItem.get(position).getOffer_type().equals("discamt")){
           // holder.tvSellingPrice.setText("₹ "+String.valueOf(Double.parseDouble(offerItem.get(position).getSelling_price())- Double.parseDouble(offerItem.get(position).getDisc_amt())));
            holder.tvSellingPrice.setText("₹ "+String.format("%.2f",(Double.parseDouble(offerItem.get(position).getSelling_price())- Double.parseDouble(offerItem.get(position).getDisc_amt()))));
        }
    }

    @Override
    public int getItemCount() {
        return offerItem.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView tvProductName,tvDiscount,tvSellingPrice,tvActualRs,tvProductDetail,tvProductStorenme,tv_pstore_km;
        private RatingBar listItemRating;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName=itemView.findViewById(R.id.tv_productName_offerAdapter);
            imageView=itemView.findViewById(R.id.image_OfferAdapter);
            tvDiscount=itemView.findViewById(R.id.tv_discount);
            tvSellingPrice=itemView.findViewById(R.id.tv_offer_rs);
            tvActualRs=itemView.findViewById(R.id.tv_actual_rs);
            listItemRating=itemView.findViewById(R.id.listitemrating);
            tvProductDetail=itemView.findViewById(R.id.tv_product_detail);
            tvProductStorenme=itemView.findViewById(R.id.tv_product_storenme);
            tv_pstore_km=itemView.findViewById(R.id.tv_pstore_km);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductDetailItemListener!=null){
                        onProductDetailItemListener.onProductItemDetail(getLayoutPosition(),offerItem.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
}
