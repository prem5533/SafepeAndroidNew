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
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ProductViewHolder> {

    private List<ProductsByCategoryIdResponse.ProductsBean> products;
    private Context context;
    private OnProductDetailItemListener onProductItemDetailListener;

    public interface OnProductDetailItemListener{
        void onProductItemDetail(int position,ProductsByCategoryIdResponse.ProductsBean productsBean);
    }

    public SearchProductAdapter(Context context, List<ProductsByCategoryIdResponse.ProductsBean> products, OnProductDetailItemListener onProductItemDetailListener) {
        this.context = context;
        this.onProductItemDetailListener = onProductItemDetailListener;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_product_adapter,parent,false);
        return new SearchProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        try {
            if (TextUtils.isEmpty(products.get(position).getImages())){

            }else {
                Picasso.get()
                        .load(products.get(position).getImages())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.imageView);
            }
        }catch (Exception er){
            er.printStackTrace();
        }

        holder.tvProductName.setText(products.get(position).getProduct_name());
        holder.tvProductPrice.setText(context.getResources().getString(R.string.rupees)+" "+products.get(position).getSelling_price());
        holder.tvProductOfferPrice.setText(context.getResources().getString(R.string.rupees)+" "+products.get(position).getBuy_price());
        holder.tvProductDetail.setText(products.get(position).getProduct_description());
        holder.tvProductStoreName.setText(products.get(position).getVenue_name());
        holder.tvProductStoreDis.setText(products.get(position).getDistance()+" KM");
        //holder.tvProductOfferPer.setText(products.get(position).getSelling_price());
        holder.productRating.setNumStars((int)products.get(position).getStars());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvProductName,tvProductPrice,tvProductOfferPrice,tvProductDetail,tvProductStoreName,tvProductStoreDis,tvProductOfferPer;
        private RatingBar productRating;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName=itemView.findViewById(R.id.tvProductName_SearchProductAdapter);
            tvProductPrice=itemView.findViewById(R.id.productPrice_SearchProductAdapter);
            tvProductOfferPrice=itemView.findViewById(R.id.productOfferPrice_SearchProductAdapter);
            tvProductDetail=itemView.findViewById(R.id.productDetail_SearchProductAdapter);
            tvProductStoreName=itemView.findViewById(R.id.productStoreName_SearchProductAdapter);
            tvProductStoreDis=itemView.findViewById(R.id.productStoreDistance_SearchProductAdapter);
            tvProductOfferPer=itemView.findViewById(R.id.productOfferPercent_SearchProductAdapter);
            productRating=itemView.findViewById(R.id.productRating_SearchProductAdapter);
            imageView=itemView.findViewById(R.id.image_SearchProductAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();

                    if (onProductItemDetailListener != null) {
                        onProductItemDetailListener.onProductItemDetail(getLayoutPosition(),products.get(getLayoutPosition()));

                    }
                }
            });
        }
    }
}
