package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class FilterListSizeAdapter extends RecyclerView.Adapter<FilterListSizeAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<String> ProductSizeList,selectedList;
    private OnFilterListSizeListener onFilterListSizeListener;

    public FilterListSizeAdapter(Context context, ArrayList<String> productSizeList, FilterListSizeAdapter.OnFilterListSizeListener onFilterListSizeListener) {
        this.context = context;
        ProductSizeList = productSizeList;
        this.onFilterListSizeListener=onFilterListSizeListener;

        selectedList=new ArrayList<>();
    }

    public interface OnFilterListSizeListener{
        void onFilterListSize(ArrayList<String> ProductSizeList);
    }

    @NonNull
    @Override
    public FilterListSizeAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filterlistadapter,parent,false);
        return new FilterListSizeAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterListSizeAdapter.ProductViewHolder holder, int position) {
        holder.checkBox.setText(ProductSizeList.get(position));
    }

    @Override
    public int getItemCount() {
        return ProductSizeList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.cb_prductsize);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectedList.contains(ProductSizeList.get(getAdapterPosition()))){
                        selectedList.remove(ProductSizeList.get(getAdapterPosition()));
                    }else {
                        selectedList.add(ProductSizeList.get(getAdapterPosition()));
                    }

                    if (onFilterListSizeListener!=null){
                        onFilterListSizeListener.onFilterListSize(selectedList);
                    }

                }
            });
        }
    }
}
