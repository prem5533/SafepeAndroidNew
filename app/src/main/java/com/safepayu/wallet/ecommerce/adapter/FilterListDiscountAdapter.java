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

public class FilterListDiscountAdapter extends RecyclerView.Adapter<FilterListDiscountAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<String> ProductSizeList,selectedList;
    private OnFilterListDiscountListener onFilterListDiscountListener;

    public FilterListDiscountAdapter(Context context, ArrayList<String> productSizeList, FilterListDiscountAdapter.OnFilterListDiscountListener onFilterListDiscountListener) {
        this.context = context;
        ProductSizeList = productSizeList;
        this.onFilterListDiscountListener=onFilterListDiscountListener;

        selectedList=new ArrayList<>();
    }

    public interface OnFilterListDiscountListener{
        void onFilterListDiscount(ArrayList<String> ProductSizeList);
    }

    @NonNull
    @Override
    public FilterListDiscountAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filterlistadapter,parent,false);
        return new FilterListDiscountAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterListDiscountAdapter.ProductViewHolder holder, int position) {
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

                    if (onFilterListDiscountListener!=null){
                        onFilterListDiscountListener.onFilterListDiscount(selectedList);
                    }
                }
            });
        }
    }
}

