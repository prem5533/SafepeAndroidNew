package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.WalletHistoryResponse;

import java.util.ArrayList;
import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.PackagesListingViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<WalletHistoryResponse.DataBean> mItem;

    private int selectedHistoryPosition = -1;
    private View selectedHistoryView = null;
    private WalletHistoryAdapter.OnPackageSelectListener callback;

    public WalletHistoryAdapter(Context context, WalletHistoryAdapter.OnPackageSelectListener callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
        this.callback = callback;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();

    }

    public void addItem(List<WalletHistoryResponse.DataBean> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(WalletHistoryResponse.DataBean mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());

    }

    public WalletHistoryResponse.DataBean getData(int position) {
        return mItem.get(position);
    }

    public WalletHistoryResponse.DataBean getSelectedData() {
        if (selectedHistoryPosition != -1)
            return mItem.get(selectedHistoryPosition);
        else
            return null;
    }

    @NonNull
    @Override
    public WalletHistoryAdapter.PackagesListingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new WalletHistoryAdapter.PackagesListingViewHolder(inflater.inflate(R.layout.wallet_history_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final WalletHistoryAdapter.PackagesListingViewHolder packagesListingViewHolder, int position) {
        packagesListingViewHolder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


    public interface OnPackageSelectListener {
        void onPackageSelect(int position, WalletHistoryResponse.DataBean selectedPackage);
    }

    class PackagesListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tv_walletDescription, AmountTV,tv_opertionType,TimeTV,StatusTV;
        private final ImageView imageViewStatus;


        public PackagesListingViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_walletDescription = itemView.findViewById(R.id.tv_wallet_description);
            AmountTV = itemView.findViewById(R.id.tv_wallet_amount);
            tv_opertionType = itemView.findViewById(R.id.tv_opertion_type);
            TimeTV = itemView.findViewById(R.id.tv_wallet_time_date);
            StatusTV = itemView.findViewById(R.id.tv_wallet_status);
            imageViewStatus = itemView.findViewById(R.id.image_status);

            itemView.setOnClickListener(this);
        }

        private void bindData(final int position) {
            String status="failed";
            try{
                if (mItem.get(position).getStatus()==1){
                    status="success";
                    imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success));
                }else if (mItem.get(position).getStatus()==0){
                    imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pending));
                    status="pending";
                }else {
                    status="failed";
                }
            }catch (Exception e){
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail));
                status="failed";
                e.printStackTrace();
            }

            tv_opertionType.setText(mItem.get(position).getOperation());
            tv_walletDescription.setText(mItem.get(position).getDescription());
            AmountTV.setText(String.valueOf(mItem.get(position).getAmount()));
            TimeTV.setText(mItem.get(position).getCreated_at());
            StatusTV.setText(status);

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