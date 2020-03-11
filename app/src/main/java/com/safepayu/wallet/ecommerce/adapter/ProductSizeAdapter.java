package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;

import java.util.List;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.ProductViewHolder> {


     private Context context;
    // private List<String> sizeitem;
     private List<ProductsDetailsResponse.ProductsBean.ModifierListBean> sizeitem;
    private int row_index = -1;
    private OnProductItem productItem;

    public interface OnProductItem{
        void onProductItem(RecyclerView recyclerView,ProductsDetailsResponse.ProductsBean.ModifierListBean modifierListBean );}


    public ProductSizeAdapter(Context context, List<ProductsDetailsResponse.ProductsBean.ModifierListBean> sizeitem, OnProductItem productItem) {
        this.context = context;
        this.sizeitem = sizeitem;
        this.productItem = productItem;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productsize_adapter,parent,false);
        return new ProductSizeAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return sizeitem.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvname;

        private RecyclerView productValueList;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById( R.id.tvname);
            productValueList = itemView.findViewById( R.id.product_colorvalue_list);
          /*  cbSize = itemView.findViewById(R.id.cb_prductsize);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();
                }
            });*/
        }

        public void bindData(final int position) {
            tvname.setText(sizeitem.get(position).getName());
            if (productItem != null) {
                productItem.onProductItem(productValueList,sizeitem.get(position));
            }

        }
    }
}
