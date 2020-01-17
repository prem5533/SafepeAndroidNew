package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
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
import com.safepayu.wallet.ecommerce.model.response.HomeCatResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ProductViewHolder> {


     private Context context;
     private List<HomeCatResponse.DataBean.ProductsTrendingBean> trendingItem;
    private OnTrendinItemListener onTrendinItemListener;

    public interface OnTrendinItemListener{
        void onTrending(int position,HomeCatResponse.DataBean.ProductsTrendingBean productsTrendingBean);
    }

    public TrendingAdapter(Context context, List<HomeCatResponse.DataBean.ProductsTrendingBean> trendingItem, OnTrendinItemListener onTrendinItemListener) {
        this.context = context;
        this.trendingItem = trendingItem;
        this.onTrendinItemListener = onTrendinItemListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trending_adapter,parent,false);
        return new TrendingAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        try {
            if (TextUtils.isEmpty(ApiClientEcom.ImagePath+trendingItem.get(position).getImages())){

            }else {
                Picasso.get()
                        .load(ApiClientEcom.ImagePath+trendingItem.get(position).getImages())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.imageView);
            }
        }catch (Exception er){
            er.printStackTrace();
        }

        holder.tvProductName.setText(trendingItem.get(position).getProduct_name());
        holder.tvSellingPrice.setText("₹ "+trendingItem.get(position).getFinal_sell_price());
        holder.listItemRating.setRating(trendingItem.get(position).getStars());
        holder.tvProductDetail.setText(trendingItem.get(position).getProduct_description());
        holder.tvProductStorenme.setText(trendingItem.get(position).getVenue_name());
        holder.tv_pstore_km.setText(trendingItem.get(position).getDistance()+" km");


    }

    @Override
    public int getItemCount() {
        return trendingItem.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvProductName,tvDiscount,tvSellingPrice,tvActualRs,tvProductDetail,tvProductStorenme,tv_pstore_km;
        private RatingBar listItemRating;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName=itemView.findViewById(R.id.tv_productName_TrendingAdapter);
            imageView=itemView.findViewById(R.id.image_TrendingAdapter);
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
                    if (onTrendinItemListener != null) {
                        onTrendinItemListener.onTrending(getLayoutPosition(),trendingItem.get(getLayoutPosition())); }
                }
            });
        }
    }
}
