package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.model.response.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {


     private Context context;
    private List<CategoryModel> categoryList;
    private OnCategoryItemListener onCategoryItemListener;
    public interface OnCategoryItemListener{
        void onCtategory(int position);
    }

   /* public CategoryAdapter(Context context, OnCategoryItemListener onCategoryItemListener) {
        this.context = context;
        this.onCategoryItemListener = onCategoryItemListener;
    }*/

    public CategoryAdapter(Context context, List<CategoryModel> categoryList, OnCategoryItemListener onCategoryItemListener) {
        this.context = context;
        this.categoryList = categoryList;
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
        CategoryModel categoryModel = categoryList.get(position);
        holder.tvCatName.setText(categoryModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCatName;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatName = itemView.findViewById(R.id.tv_category_name);
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
