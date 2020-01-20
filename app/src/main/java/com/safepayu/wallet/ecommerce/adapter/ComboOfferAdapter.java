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

public class ComboOfferAdapter extends RecyclerView.Adapter<ComboOfferAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private List<ProductsDetailsResponse.ProductOfersBean.ComboProductIdBean>comboItem;
   // private OnProductOfferItemListener offerItemListener;

    public interface OnProductOfferItemListener{
        void onProductOfferItem(int position, ProductsDetailsResponse.ProductOfersBean productOfersBean);
    }

    public ComboOfferAdapter(Context context, List<ProductsDetailsResponse.ProductOfersBean.ComboProductIdBean> comboItem) {
        this.context = context;
        this.comboItem = comboItem;
    }

    @NonNull
    @Override
    public ComboOfferAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.combo_adapter,parent,false);
        return new ComboOfferAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboOfferAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return comboItem.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvcomboName ;
        private ImageView comboImage;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvcomboName = itemView.findViewById(R.id.tvComboName);
            comboImage = itemView.findViewById(R.id.comboImage);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (offerItemListener != null) {
                        offerItemListener.onProductOfferItem(getLayoutPosition(),ofersItem.get(getLayoutPosition()));

                    }
                }
            });*/
        }

        public void bindData(int position) {
            tvcomboName.setText(comboItem.get(position).getName());
        //    tvActualPrice.setText("₹ "+ofersItem.get(position).getSelling_price());

            Picasso.get().load(ApiClientEcom.BASE_URL +comboItem.get(position).getImages()).into(comboImage);
            /*tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

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

                else if (ofersItem.get(position).getOffer_type().equals("combo")){
                //    tvActualPrice.setVisibility(View.GONE);
                    tvActualPrice.setText("");
                    tvOffPrice.setText("₹ " + ofersItem.get(position).getSelling_price());
                    tvSaveOffer.setText(ofersItem.get(position).getOffer_title());
                }*/
            }
         //   tvOffPrice.setText("₹ "+String.valueOf(Float.parseFloat(ofersItem.get(position).getSelling_price()) - Float.parseFloat(ofersItem.get(position).getDiscount_amount())));




        }
    }

