package com.safepayu.wallet.ecommerce.adapter;

import android.content.ClipData;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.HomeCatResponse;
import com.safepayu.wallet.ecommerce.model.response.ParentCategoriesResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParentCategoryAdapter extends RecyclerView.Adapter<ParentCategoryAdapter.ProductViewHolder> {


    private Context context;
    private String Tag="";
    private List<HomeCatResponse.DataBean.CategoriesBean> categories;
    private ParentCategoryAdapter.OnCategoryItemListener onCategoryItemListener;

    public interface OnCategoryItemListener{
        void onCategory(int position,HomeCatResponse.DataBean.CategoriesBean categoriesBean);
    }

   public ParentCategoryAdapter(Context context, List<HomeCatResponse.DataBean.CategoriesBean> categoryList,
                                 ParentCategoryAdapter.OnCategoryItemListener onCategoryItemListener,String Tag) {
        this.context = context;
        this.Tag = Tag;
        this.categories = categoryList;
        this.onCategoryItemListener = onCategoryItemListener;
    }



    @NonNull
    @Override
    public ParentCategoryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
       if (Tag.equals("Home")){
           view = LayoutInflater.from(context).inflate(R.layout.homecategroy_adapter,parent,false);
       } else {
            view = LayoutInflater.from(context).inflate(R.layout.parent_category_adaper,parent,false); }

        return new ParentCategoryAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentCategoryAdapter.ProductViewHolder holder, int position) {

        holder.tvCatName.setText(categories.get(position).getCat_name());

        try {
            if (TextUtils.isEmpty(categories.get(position).getImage())) {

            } else {
                Picasso.get()
                        .load(ApiClientEcom.ImagePath+ categories.get(position).getImage())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.imageCat);
            }
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (Tag.equalsIgnoreCase("Home")){
            return 6;
        }else {
            return categories.size();
        }

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCatName;
        private ImageView imageCat;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatName = itemView.findViewById(R.id.tvCatName_parentCatAdapter);
            imageCat = itemView.findViewById(R.id.catImage_parentCatAdapter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCategoryItemListener != null) {
                        onCategoryItemListener.onCategory(getLayoutPosition(),categories.get(getLayoutPosition()));

                    }
                }
            });
        }
    }
}

