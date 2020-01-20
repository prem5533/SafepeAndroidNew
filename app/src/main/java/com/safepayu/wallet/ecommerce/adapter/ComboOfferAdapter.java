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
        private TextView tvcomboName ,tvPlus;
        private ImageView comboImage;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvcomboName = itemView.findViewById(R.id.tvComboName);
            comboImage = itemView.findViewById(R.id.comboImage);
            tvPlus = itemView.findViewById(R.id.tvplus);
        }

        public void bindData(int position) {
            tvcomboName.setText(comboItem.get(position).getName());
        //    tvActualPrice.setText("₹ "+ofersItem.get(position).getSelling_price());

            Picasso.get().load(ApiClientEcom.BASE_URL +comboItem.get(position).getImages()).into(comboImage);

            if(position==(getItemCount()-1)){
                tvPlus.setText(" "); }
            else {
                tvPlus.setText("+");
            } }





        }
    }

