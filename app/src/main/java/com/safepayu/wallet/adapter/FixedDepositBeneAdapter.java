package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.interfaces.FixedDepositInterface;
import com.safepayu.wallet.models.response.InvestmentResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FixedDepositBeneAdapter extends RecyclerView.Adapter<FixedDepositBeneAdapter.ViewHolder> {

    private Context context;
    private List<InvestmentResponse.DataBean.InvestmentBean> modelArrayList;
    private FixedDepositInterface fixedDepositInterface;

    public FixedDepositBeneAdapter(Context context, List<InvestmentResponse.DataBean.InvestmentBean> modelArrayList, FixedDepositInterface fixedDepositInterface) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.fixedDepositInterface = fixedDepositInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fixed_deposit_bene_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2019-11-19 10:15:07
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, h:mm a");
        Date time_in;

        holder.tv_bonus_amount.setText(String.valueOf(modelArrayList.get(position).getPackage_amount()));
        holder.tv_interest_rate.setText(String.valueOf(modelArrayList.get(position).getRateOfInterest()) + "%");
        holder.tv_no_day.setText(modelArrayList.get(position).getDays());
        holder.tv_total_amount_to_withdraw.setText(String.valueOf(modelArrayList.get(position).getFinalAmount()));
        holder.tv_current_withdrawal_amount.setText(modelArrayList.get(position).getCurrentWithdrawalAmount());
        holder.tv_total_days.setText(modelArrayList.get(position).getTotalDays());
        holder.tv_roi_applied.setText(modelArrayList.get(position).getRoiApplied() + "%");
        holder.tv_penalty.setText(modelArrayList.get(position).getPenalty());
        try {
            time_in = inputFormat.parse(modelArrayList.get(position).getBuy_date());
            holder.tv_buy_date.setText(outputFormat.format(time_in));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.tv_buy_date.setText(modelArrayList.get(position).getBuy_date());
        }

        holder.ch_check_in.setOnClickListener(v -> fixedDepositInterface.onClickInterestRate(String.valueOf(modelArrayList.get(position).getId()),
                String.valueOf(modelArrayList.get(position).getPackage_amount())));
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_bonus_amount, tv_current_withdrawal_amount, tv_total_days, tv_roi_applied, tv_penalty, tv_buy_date, tv_interest_rate, tv_no_day, tv_total_amount_to_withdraw;
        private RadioButton ch_check_in;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_interest_rate = itemView.findViewById(R.id.tv_interest_rate);
            tv_buy_date = itemView.findViewById(R.id.tv_buy_date);
            tv_bonus_amount = itemView.findViewById(R.id.tv_bonus_amount);
            ch_check_in = itemView.findViewById(R.id.ch_check_in);
            tv_no_day = itemView.findViewById(R.id.tv_no_day);
            tv_total_amount_to_withdraw = itemView.findViewById(R.id.tv_total_amount_to_withdraw);
            tv_current_withdrawal_amount = itemView.findViewById(R.id.tv_current_withdrawal_amount);
            tv_total_days = itemView.findViewById(R.id.tv_total_days);
            tv_roi_applied = itemView.findViewById(R.id.tv_roi_applied);
            tv_penalty = itemView.findViewById(R.id.tv_penalty);
        }
    }
}