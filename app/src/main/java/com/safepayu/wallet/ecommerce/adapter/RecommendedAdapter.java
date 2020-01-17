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
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.ecommerce.model.response.HomeCatResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ProductViewHolder> {


     private Context context;
    private List<HomeCatResponse.DataBean.ProductsRecommendedBean> recommedItem;
    private OnRecommendedItemListener onRecommendedItemListener;

    public interface OnRecommendedItemListener{
        void onRecommended(int position,HomeCatResponse.DataBean.ProductsRecommendedBean productsRecommendedBean);
    }

    public RecommendedAdapter(Context context, List<HomeCatResponse.DataBean.ProductsRecommendedBean> recommedItem, OnRecommendedItemListener onRecommendedItemListener) {
        this.context = context;
        this.recommedItem = recommedItem;
        this.onRecommendedItemListener = onRecommendedItemListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended_adapter,parent,false);
        return new RecommendedAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        try {
            if (TextUtils.isEmpty(ApiClient.ImagePath+recommedItem.get(position).getImages())){

            }else {
                Picasso.get()
                        .load(ApiClient.ImagePath+recommedItem.get(position).getImages())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.imageView);
            }
        }catch (Exception er){
            er.printStackTrace();
        }

        holder.tvProductName.setText(recommedItem.get(position).getProduct_name());
        holder.tvSellingPrice.setText("₹ "+recommedItem.get(position).getFinal_sell_price());
        holder.listItemRating.setRating(recommedItem.get(position).getStars());
        holder.tvProductDetail.setText(recommedItem.get(position).getProduct_description());
        holder.tvProductStorenme.setText(recommedItem.get(position).getVenue_name());
        holder.tv_pstore_km.setText(recommedItem.get(position).getDistance()+" km");
    }

    @Override
    public int getItemCount() {
        return recommedItem.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView tvProductName,tvDiscount,tvSellingPrice,tvActualRs,tvProductDetail,tvProductStorenme,tv_pstore_km;
        private RatingBar listItemRating;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName=itemView.findViewById(R.id.tv_productName_RecommendAdapter);
            imageView=itemView.findViewById(R.id.image_RecommendAdapter);
            tvSellingPrice=itemView.findViewById(R.id.tv_offer_rs);
            tvActualRs=itemView.findViewById(R.id.tv_actual_rs);
            listItemRating=itemView.findViewById(R.id.listitemrating);
            tvProductDetail=itemView.findViewById(R.id.tv_product_detail);
            tvProductStorenme=itemView.findViewById(R.id.tv_product_storenme);
            tv_pstore_km=itemView.findViewById(R.id.tv_pstore_km);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecommendedItemListener != null) {
                        onRecommendedItemListener.onRecommended(getLayoutPosition(),recommedItem.get(getLayoutPosition()));

                    }
                }
            });
        }
    }
}
