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

public class ProdouctOfferRelatedAdapter extends RecyclerView.Adapter<ProdouctOfferRelatedAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private List<ProductsDetailsResponse.RelatedproductBean>relatedItem;
    private OnProductRelatedItemListener reltedItemListener;

    public interface OnProductRelatedItemListener{
        void onReltedItem(int position ,ProductsDetailsResponse.RelatedproductBean productOfersBean);
    }

    public ProdouctOfferRelatedAdapter(Context context, List<ProductsDetailsResponse.RelatedproductBean> relatedItem, OnProductRelatedItemListener reltedItemListener) {
        this.context = context;
        this.relatedItem = relatedItem;
        this.reltedItemListener = reltedItemListener;
    }

    @NonNull
    @Override
    public ProdouctOfferRelatedAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_offer_adapter,parent,false);
        return new ProdouctOfferRelatedAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdouctOfferRelatedAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return relatedItem.size();
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
                    if (reltedItemListener != null) {
                        reltedItemListener.onReltedItem(getLayoutPosition(),relatedItem.get(getLayoutPosition()));

                    }
                }
            });

        }

        public void bindData(int position) {
            tvProductName.setText(relatedItem.get(position).getProduct_name());
            tvOffPrice.setText("₹ "+relatedItem.get(position).getSelling_price());

            Picasso.get().load(ApiClientEcom.BASE_URL +relatedItem.get(position).getImages()).into(offerImage);
            tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvActualPrice.setVisibility(View.GONE);
            tvSaveOffer.setVisibility(View.GONE);


        }
    }
}
