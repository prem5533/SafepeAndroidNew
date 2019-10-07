package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.RechargeHistoryResponse;

import java.util.ArrayList;
import java.util.List;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.PackagesListingViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<RechargeHistoryResponse.DataBean> mItem;

    private int selectedHistoryPosition = -1;
    private View selectedHistoryView = null;
    private RechargeHistoryAdapter.OnPackageSelectListener callback;

    public RechargeHistoryAdapter(Context context, RechargeHistoryAdapter.OnPackageSelectListener callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
        this.callback = callback;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();

    }

    public void addItem(List<RechargeHistoryResponse.DataBean> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(RechargeHistoryResponse.DataBean mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());

    }

    public RechargeHistoryResponse.DataBean getData(int position) {
        return mItem.get(position);
    }

    public RechargeHistoryResponse.DataBean getSelectedData() {
        if (selectedHistoryPosition != -1)
            return mItem.get(selectedHistoryPosition);
        else
            return null;
    }

    @NonNull
    @Override
    public RechargeHistoryAdapter.PackagesListingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new RechargeHistoryAdapter.PackagesListingViewHolder(inflater.inflate(R.layout.packages_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RechargeHistoryAdapter.PackagesListingViewHolder packagesListingViewHolder, int position) {
        packagesListingViewHolder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


    public interface OnPackageSelectListener {
        void onPackageSelect(int position, RechargeHistoryResponse.DataBean selectedPackage);
    }

    class PackagesListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView packageName, packageAmount;


        public PackagesListingViewHolder(@NonNull View itemView) {
            super(itemView);

            packageName = itemView.findViewById(R.id.tv_packageName);
            packageAmount = itemView.findViewById(R.id.tv_packageAmount);

            itemView.setOnClickListener(this);
        }

        private void bindData(final int position) {

            packageName.setText(mItem.get(position).getTransactionID());
            //packageAmount.setText());

        }

        @Override
        public void onClick(View v) {
            if (selectedHistoryView == null) {
                selectedHistoryPosition = getLayoutPosition();
                selectedHistoryView = itemView;
                itemView.setBackground(context.getResources().getDrawable(R.drawable.package_selected));
            } else {
                selectedHistoryView.setBackground(context.getResources().getDrawable(R.drawable.package_normal));
                selectedHistoryPosition = getLayoutPosition();
                selectedHistoryView = itemView;
                itemView.setBackground(context.getResources().getDrawable(R.drawable.package_selected));

            }
            if (callback != null) {
                callback.onPackageSelect(getLayoutPosition(), mItem.get(getLayoutPosition()));
            }
        }
    }
}