package com.safepayu.wallet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.InvestmentResponse;
import com.safepayu.wallet.models.response.MyOrderResponse;

import java.util.List;

public class FixedDepositAdapter extends RecyclerView.Adapter<FixedDepositAdapter.MyOrderViewHolder> {

    private Context context;
    private List<InvestmentResponse.DataBean.InvestmentBean> investmentBeanList;
    private FixedDepositAdapter.OnFDSelectListener callback;

    public FixedDepositAdapter(Context context, List<InvestmentResponse.DataBean.InvestmentBean> investmentBeanList, OnFDSelectListener callback) {
        this.context = context;
        this.investmentBeanList = investmentBeanList;
        this.callback = callback;
    }


    public interface OnFDSelectListener {
        void onFDItemSelect(int position, InvestmentResponse.DataBean.InvestmentBean investmentBean);
    }
    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        // For First Item with Big ImageView , Other are small
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixeddepositadapter, parent, false);

        return new FixedDepositAdapter.MyOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return investmentBeanList.size();
    }

    public interface OnPackageSelectListener {
        void onOrderItemSelect(int position, MyOrderResponse.BankToWalletBean selectOrderItem);
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  TextView tvTimeDate, tvStatus,tvDepositAmount;
        private  ImageView imageViewStatus, image;
        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTimeDate = itemView.findViewById(R.id.tv_fd_time_date);
            tvStatus = itemView.findViewById(R.id.tv_fd_status);
            tvDepositAmount = itemView.findViewById(R.id.tv_deposit_amount);


            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {

            tvTimeDate.setText(investmentBeanList.get(position).getCreated_at());
            tvDepositAmount.setText("₹ " +investmentBeanList.get(position).getPackage_amount());
            if (investmentBeanList.get(position).getStatus()==0){
                tvStatus.setText("Pending");
                tvStatus.setTextColor(Color.parseColor("#FFBF00"));
            }
            else if (investmentBeanList.get(position).getStatus()==1){
                tvStatus.setText("Approved");
                tvStatus.setTextColor(Color.parseColor("#84DE02"));
            }
            else if (investmentBeanList.get(position).getStatus()==2){
                tvStatus.setText("Failed");
                tvStatus.setTextColor(Color.parseColor("#E32636"));
            }

        }

        @Override
        public void onClick(View v) {

            if (callback != null) {
                callback.onFDItemSelect(getLayoutPosition(),investmentBeanList.get(getLayoutPosition()));
            }
        }
    }
}
