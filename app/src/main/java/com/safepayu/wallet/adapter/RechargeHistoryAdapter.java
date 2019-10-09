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
        return new RechargeHistoryAdapter.PackagesListingViewHolder(inflater.inflate(R.layout.rechrge_histrory_adapter_layout, viewGroup, false));
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

        private final TextView RechargeTypeTV, AmountTV,CustomerIdTV,TimeTV,StatusTV;
        private final ImageView imageViewStatus;


        public PackagesListingViewHolder(@NonNull View itemView) {
            super(itemView);

            RechargeTypeTV = itemView.findViewById(R.id.tv_recharge_type);
            AmountTV = itemView.findViewById(R.id.tv_recharge_amount);
            CustomerIdTV = itemView.findViewById(R.id.tv_customerId);
            TimeTV = itemView.findViewById(R.id.tv_rechrge_time_date);
            StatusTV = itemView.findViewById(R.id.tv_rechrge_status);
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
                    imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail));
                    status="failed";
                }
            }catch (Exception e){
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail));
                status="failed";
                e.printStackTrace();
            }

            RechargeTypeTV.setText(mItem.get(position).getTransactionID());
            TimeTV.setText(mItem.get(position).getCreated_at());
            CustomerIdTV.setText(mItem.get(position).getNumber());
            AmountTV.setText(context.getResources().getString(R.string.rupees)+" "+mItem.get(position).getAmount());
            StatusTV.setText(status);

        }

        @Override
        public void onClick(View v) {
            if (selectedHistoryView == null) {
                selectedHistoryPosition = getLayoutPosition();
                selectedHistoryView = itemView;
            } else {
                selectedHistoryView.setBackground(context.getResources().getDrawable(R.drawable.package_normal));
                selectedHistoryPosition = getLayoutPosition();
                selectedHistoryView = itemView;
            }
            if (callback != null) {
                callback.onPackageSelect(getLayoutPosition(), mItem.get(getLayoutPosition()));
            }
        }
    }
}