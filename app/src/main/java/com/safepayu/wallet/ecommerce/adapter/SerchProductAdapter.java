package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class SerchProductAdapter extends RecyclerView.Adapter<SerchProductAdapter.ProductViewHolder> {


     private Context context;
    private OnProductDetailItemListener onProductItemDetailListener;

    public interface OnProductDetailItemListener{
        void onProductItemDetail(int position);
    }

    public SerchProductAdapter(Context context, OnProductDetailItemListener onProductItemDetailListener) {
        this.context = context;
        this.onProductItemDetailListener = onProductItemDetailListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_product_adapter,parent,false);
        return new SerchProductAdapter.ProductViewHolder(view);
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
                   // Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();
                    if (onProductItemDetailListener != null) {
                        onProductItemDetailListener.onProductItemDetail(getLayoutPosition());

                    }
                }
            });
        }
    }
}
