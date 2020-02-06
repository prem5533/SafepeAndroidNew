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

public class FilterLisCategoryAdapter extends RecyclerView.Adapter<FilterLisCategoryAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<String> CatNameList,CatIdList,selectedList;
    private OnFilterListCategoryListener onFilterListCategoryListener;

    public FilterLisCategoryAdapter(Context context, ArrayList<String> CatNameList,ArrayList<String>CatIdList,OnFilterListCategoryListener onFilterListCategoryListener) {
        this.context = context;
        this.CatNameList = CatNameList;
        this.CatIdList=CatIdList;
        this.onFilterListCategoryListener=onFilterListCategoryListener;

        selectedList=new ArrayList<>();
    }

    public interface OnFilterListCategoryListener{
        void onFilterListCategory(ArrayList<String> ProductSizeList);
    }

    @NonNull
    @Override
    public FilterLisCategoryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filterlistadapter,parent,false);
        return new FilterLisCategoryAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterLisCategoryAdapter.ProductViewHolder holder, int position) {
        holder.checkBox.setText(CatNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return CatIdList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkBox;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.cb_prductsize);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (selectedList.contains(CatIdList.get(getAdapterPosition()))){
                        selectedList.remove(CatIdList.get(getAdapterPosition()));
                    }else {
                        selectedList.add(CatIdList.get(getAdapterPosition()));
                    }

                    if (onFilterListCategoryListener!=null){
                        onFilterListCategoryListener.onFilterListCategory(selectedList);
                    }
                }
            });
        }
    }
}
