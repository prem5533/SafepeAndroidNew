package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.interfaces.FixedDepositInterface;
import com.safepayu.wallet.models.response.AllListData;

import java.text.DecimalFormat;
import java.util.List;

public class FixedDepositRateAdapter extends RecyclerView.Adapter<FixedDepositRateAdapter.ViewHolder> {

    private Context context;
    private List<AllListData> modelArrayList;
    private FixedDepositInterface fixedDepositInterface;

    public FixedDepositRateAdapter(Context context, List<AllListData> modelArrayList, FixedDepositInterface fixedDepositInterface) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.fixedDepositInterface = fixedDepositInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deposit_interest_rate_table_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DecimalFormat df = new DecimalFormat("######.##");
        holder.tv_period.setText(modelArrayList.get(position).from);
        holder.tv_interest_rate.setText("Interest @ " + modelArrayList.get(position).interestrate + "% pa");
        holder.tv_amount.setText(String.valueOf(df.format(Double.valueOf(modelArrayList.get(position).amount))));

        holder.ll_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixedDepositInterface.onClickInterestRate(modelArrayList.get(position).interestRateId,modelArrayList.get(position).interestrate);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_period, tv_interest_rate, tv_amount;
        private CardView ll_interest;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_period = itemView.findViewById(R.id.tv_period);
            tv_interest_rate = itemView.findViewById(R.id.tv_interest_rate);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            ll_interest = itemView.findViewById(R.id.ll_interest);
        }
    }
}