package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.PackageListData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class  PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.PackagesListingViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<PackageListData.Packages> mItem;

    private int selectedPackagePosition = -1;
    private View selectedPackageView = null;
    private OnPackageSelectListener callback;

    public interface OnPackageSelectListener {
        void onPackageSelect(int position, PackageListData.Packages selectedPackage);
    }

    public PackageListAdapter(Context context, OnPackageSelectListener callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
        this.callback = callback;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();

    }

    public void addItem(List<PackageListData.Packages> mItem) {
        this.mItem.addAll(mItem);
        Collections.reverse( this.mItem);
        notifyItemChanged(this.mItem.size());
    }

    public PackageListData.Packages getData(int position) {
        return mItem.get(position);
    }

    public PackageListData.Packages getSelectedData() {
        if (selectedPackagePosition != -1)
            return mItem.get(selectedPackagePosition);
        else
            return null;
    }

    @NonNull
    @Override
    public PackagesListingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new PackagesListingViewHolder(inflater.inflate(R.layout.packages_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PackagesListingViewHolder packagesListingViewHolder, int position) {
        packagesListingViewHolder.bindData(position);

    }


    @Override
    public int getItemCount() {
        return mItem.size();
    }


    class PackagesListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView packageName, packageAmount;
        private LinearLayout linear_buy_package;


        public PackagesListingViewHolder(@NonNull View itemView) {
            super(itemView);

            packageName = itemView.findViewById(R.id.tv_packageName);
            packageAmount = itemView.findViewById(R.id.tv_packageAmount);
            linear_buy_package = itemView.findViewById(R.id.linear_buy_package);

            itemView.setOnClickListener(this);
        }

        private void bindData(final int position) {

            packageName.setText(mItem.get(position).getPackageName());
            packageAmount.setText(context.getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(mItem.get(position).getPackageAmount()));

        }

        @Override
        public void onClick(View v) {
            if (selectedPackageView == null) {
                selectedPackagePosition = getLayoutPosition();
                selectedPackageView = linear_buy_package;
                linear_buy_package.setBackground(context.getResources().getDrawable(R.drawable.package_selected));
            } else {
                selectedPackageView.setBackground(context.getResources().getDrawable(R.drawable.package_normal));
                selectedPackagePosition = getLayoutPosition();
                selectedPackageView = linear_buy_package;
                linear_buy_package.setBackground(context.getResources().getDrawable(R.drawable.package_selected));

            }
            if (callback != null) {
                callback.onPackageSelect(getLayoutPosition(), mItem.get(getLayoutPosition()));
            }
        }
    }
}