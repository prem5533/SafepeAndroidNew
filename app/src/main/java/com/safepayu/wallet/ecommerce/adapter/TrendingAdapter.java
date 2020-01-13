package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ProductViewHolder> {

    private ArrayList<String> ProductNameList,ProductImageList;
     private Context context;

    public TrendingAdapter(Context context, ArrayList<String> ProductNameList, ArrayList<String>ProductImageList) {
        this.context = context;
        this.ProductNameList = ProductNameList;
        this.ProductImageList = ProductImageList;
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
            if (TextUtils.isEmpty(ProductImageList.get(position))){

            }else {
                Picasso.get()
                        .load(ProductImageList.get(position))
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.imageView);
            }
        }catch (Exception er){
            er.printStackTrace();
        }

        holder.tvProductName.setText(ProductNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return ProductNameList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvProductName;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName=itemView.findViewById(R.id.tv_productName_TrendingAdapter);
            imageView=itemView.findViewById(R.id.image_TrendingAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
