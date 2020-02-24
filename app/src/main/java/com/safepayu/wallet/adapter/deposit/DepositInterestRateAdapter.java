package com.safepayu.wallet.adapter.deposit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.AllListData;

import java.util.List;

public class DepositInterestRateAdapter extends RecyclerView.Adapter<DepositInterestRateAdapter.ViewHolder> {

    private Context context;
    private List<AllListData> modelArrayList;

    public DepositInterestRateAdapter(Context context, List<AllListData> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deposit_interest_rate_table_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tv_period.setText(modelArrayList.get(position).period + " Days");
        holder.tv_interest_rate.setText("Interest @ " + modelArrayList.get(position).r_rate + " %");
        holder.tv_amount.setText(modelArrayList.get(position).amount);

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_period, tv_interest_rate, tv_amount;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_period = itemView.findViewById(R.id.tv_period);
            tv_interest_rate = itemView.findViewById(R.id.tv_interest_rate);
            tv_amount = itemView.findViewById(R.id.tv_amount);
        }
    }
}