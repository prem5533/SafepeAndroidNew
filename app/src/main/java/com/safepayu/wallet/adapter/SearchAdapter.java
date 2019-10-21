package com.safepayu.wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.BuyMemberShip;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context mContext;
    private SearchAdapter.OnListSelectListener callback;
    private ArrayList<String> SearchList;
    private int selectedPackagePosition = -1;
    private View selectedPackageView = null;

    public interface OnListSelectListener {
        void onListSelect(int position, String SearchItem);
    }

    public SearchAdapter(Context context, ArrayList<String> PackageNameList1, SearchAdapter.OnListSelectListener callback1) {

        this.mContext = context;
        this.callback =callback1;
        this.SearchList =PackageNameList1;
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.packages_item_view, parent, false);

        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, final int position) {

        holder.SearchName.setText(SearchList.get(position));
    }

    @Override
    public int getItemCount() {
        return SearchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView SearchName, packageAmount;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            SearchName = itemView.findViewById(R.id.tv_packageName);
            packageAmount = itemView.findViewById(R.id.tv_packageAmount);
            cardView = itemView.findViewById(R.id.packageItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try{
                if (selectedPackageView == null) {
                    selectedPackagePosition = getLayoutPosition();
                    selectedPackageView = itemView;
                    itemView.setBackground(mContext.getResources().getDrawable(R.drawable.package_selected));
                } else {
                    selectedPackageView.setBackground(mContext.getResources().getDrawable(R.drawable.package_normal));
                    selectedPackagePosition = getLayoutPosition();
                    selectedPackageView = itemView;
                    itemView.setBackground(mContext.getResources().getDrawable(R.drawable.package_selected));

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (callback != null) {

                if (SearchList.get(getLayoutPosition()).equalsIgnoreCase("more")){
                    mContext.startActivity(new Intent(mContext, BuyMemberShip.class));
                    ((Activity)mContext).finish();
                }else {
                    callback.onListSelect(getLayoutPosition(), SearchList.get(getLayoutPosition()));
                }

            }
        }
    }
}
