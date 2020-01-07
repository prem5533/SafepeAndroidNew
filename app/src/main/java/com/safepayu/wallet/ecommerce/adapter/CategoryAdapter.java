package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.activity.SearchProductActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {


     private Context context;
    private OnCategoryItemListener onCategoryItemListener;
    public interface OnCategoryItemListener{
        void onCtategory(int position);
    }

    public CategoryAdapter(Context context, OnCategoryItemListener onCategoryItemListener) {
        this.context = context;
        this.onCategoryItemListener = onCategoryItemListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categroy_adaper,parent,false);
        return new CategoryAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCategoryItemListener != null) {
                        onCategoryItemListener.onCtategory(getLayoutPosition());

                    }
                }
            });
        }
    }
}
