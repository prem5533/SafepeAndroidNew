package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ProductViewHolder> {


    private Context context;
    private ShopItemListListener shopItemListListener;

    public StoreListAdapter(Context context,ShopItemListListener shopItemListListener1) {
        this.context = context;
        this.shopItemListListener=shopItemListListener1;
    }

    public  interface  ShopItemListListener {
        void onShopItemClick (int position);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_adapter,parent,false);
        return new StoreListAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();

                    if (shopItemListListener!=null){
                        shopItemListListener.onShopItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
