package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class FilterListPriceAdapter extends RecyclerView.Adapter<FilterListPriceAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<String> ProductSizeList,selectedList;
    private OnFilterListPriceListener onFilterListPriceListener;

    public FilterListPriceAdapter(Context context, ArrayList<String> productSizeList,OnFilterListPriceListener onFilterListPriceListener) {
        this.context = context;
        ProductSizeList = productSizeList;
        this.onFilterListPriceListener=onFilterListPriceListener;

        selectedList=new ArrayList<>();
    }

    public interface OnFilterListPriceListener{
        void onFilterListPrice(ArrayList<String> ProductSizeList);
    }

    @NonNull
    @Override
    public FilterListPriceAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filterlistadapter,parent,false);
        return new FilterListPriceAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterListPriceAdapter.ProductViewHolder holder, int position) {
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

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (selectedList.contains(ProductSizeList.get(getAdapterPosition()))){
                        selectedList.remove(ProductSizeList.get(getAdapterPosition()));
                    }else {
                        selectedList.add(ProductSizeList.get(getAdapterPosition()));
                    }

                    if (onFilterListPriceListener!=null){
                        onFilterListPriceListener.onFilterListPrice(selectedList);
                    }
                }
            });
        }
    }
}
