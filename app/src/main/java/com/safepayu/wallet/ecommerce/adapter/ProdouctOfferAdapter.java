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
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdouctOfferAdapter extends RecyclerView.Adapter<ProdouctOfferAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private List<ProductsDetailsResponse.ProductOfersBean>ofersItem;
    private OnProductOfferItemListener offerItemListener;

    public interface OnProductOfferItemListener{
        void onProductOfferItem(int position ,ProductsDetailsResponse.ProductOfersBean productOfersBean);
    }

    public ProdouctOfferAdapter(Context context, List<ProductsDetailsResponse.ProductOfersBean> ofersItem, OnProductOfferItemListener offerItemListener) {
        this.context = context;
        this.ofersItem = ofersItem;
        this.offerItemListener = offerItemListener;
    }

    @NonNull
    @Override
    public ProdouctOfferAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_offer_adapter,parent,false);
        return new ProdouctOfferAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdouctOfferAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return ofersItem.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName,tvOffPrice , tvActualPrice,tvSaveOffer;
        private ImageView offerImage;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name_offer);
            tvOffPrice = itemView.findViewById(R.id.tv_offprice_offer);
            tvActualPrice = itemView.findViewById(R.id.tv_actualprice_offer);
            tvSaveOffer = itemView.findViewById(R.id.tv_savers_offer);
            offerImage = itemView.findViewById(R.id.im_product_offer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (offerItemListener != null) {
                        offerItemListener.onProductOfferItem(getLayoutPosition(),ofersItem.get(getLayoutPosition()));

                    }
                }
            });
        }

        public void bindData(int position) {
            tvProductName.setText(ofersItem.get(position).getProduct_name());
            tvActualPrice.setText("₹ "+ofersItem.get(position).getSelling_price());

            Picasso.get().load(ApiClientEcom.BASE_URL +ofersItem.get(position).getProduct_images()).into(offerImage);
            tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (ofersItem.get(position).getDiscount_amount().equals(" ")){
                tvSaveOffer.setVisibility(View.GONE); }
            else {

                if (ofersItem.get(position).getOffer_type().equals("discper")){
                    String discountPercent = ofersItem.get(position).getOffer_title();
                    String percent[] = discountPercent.split(" ");
                    String percent1 = percent[0];
                    tvSaveOffer.setText(percent1+" Off");
                    Double b = ((Double.parseDouble(ofersItem.get(position).getSelling_price())-((Double.parseDouble(ofersItem.get(position).getSelling_price()))*(Double.parseDouble(ofersItem.get(position).getDisc_per()))/100)));
                    tvOffPrice.setText("₹ " +String.format("%.3f", b)); }

                else if (ofersItem.get(position).getOffer_type().equals("discamt")){
                    tvSaveOffer.setText("₹ "+ofersItem.get(position).getDiscount_amount()+" Off");
                    tvOffPrice.setText("₹ "+String.format("%.2f",(Double.parseDouble(ofersItem.get(position).getSelling_price())- Double.parseDouble(ofersItem.get(position).getDisc_amt())))); }
            }
         //   tvOffPrice.setText("₹ "+String.valueOf(Float.parseFloat(ofersItem.get(position).getSelling_price()) - Float.parseFloat(ofersItem.get(position).getDiscount_amount())));




        }
    }
}
