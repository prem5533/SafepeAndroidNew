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
import com.safepayu.wallet.models.response.InvestmentWalletLogResponse;

import java.util.List;

public class InvestmentWalletAdapter  extends RecyclerView.Adapter<InvestmentWalletAdapter.InvestmentWalletListViewHolder> {

    private Context context ;
    private InvestmentWalletListener investmentWalletListener;
    private List<InvestmentWalletLogResponse.DataBean.LogListBean> logListBeanList;

    public  interface InvestmentWalletListener {
        void onInvestmentWalletClick (int position,InvestmentWalletLogResponse.DataBean.LogListBean logListBeanList);
    }

    public InvestmentWalletAdapter(Context context, List<InvestmentWalletLogResponse.DataBean.LogListBean> logListBeanList1,
                                   InvestmentWalletListener investmentWalletListener) {
        this.context = context;
        this.logListBeanList = logListBeanList1;
        this.investmentWalletListener=investmentWalletListener;
    }

    @NonNull
    @Override
    public InvestmentWalletAdapter.InvestmentWalletListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.investment_wallet_adapter,parent,false);
        return new InvestmentWalletAdapter.InvestmentWalletListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvestmentWalletAdapter.InvestmentWalletListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return logListBeanList.size();
    }

    public class InvestmentWalletListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTxnId,tvAmount,tvDate,tvStatus ;
        private ImageView ivStatusImage;
        public InvestmentWalletListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTxnId = itemView.findViewById(R.id.txnId_investmentWalletAdapter);
            tvAmount = itemView.findViewById(R.id.amount_investmentWalletAdapter);
            tvDate = itemView.findViewById(R.id.time_investmentWalletAdapter);
            tvStatus = itemView.findViewById(R.id.status_investmentWalletAdapter);
            ivStatusImage = itemView.findViewById(R.id.statusImage_investmentWalletAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvTxnId.setText(logListBeanList.get(position).getTransaction_no());
            tvAmount.setText(context.getResources().getString(R.string.rupees)+" "+logListBeanList.get(position).getAmount());
            tvDate.setText(logListBeanList.get(position).getCreated_at());


            try{
                if (logListBeanList.get(position).getStatus()==1){
                    tvStatus.setText("Success");
                    tvStatus.setTextColor(Color.parseColor("#84DE02"));
                    ivStatusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success));
                }else if (logListBeanList.get(position).getStatus()==2){
                    ivStatusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pending));
                    tvStatus.setText("Pending");
                    tvStatus.setTextColor(Color.parseColor("#FFBF00"));
                }else {
                    ivStatusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail));
                    tvStatus.setText("Failed");
                    tvStatus.setTextColor(Color.parseColor("#E32636"));
                }
            }catch (Exception e){
                ivStatusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail));
                tvStatus.setText("Failed");
                e.printStackTrace();
            }

        }

        @Override
        public void onClick(View v) {

            if (investmentWalletListener != null) {
                investmentWalletListener.onInvestmentWalletClick(getLayoutPosition(),logListBeanList.get(getLayoutPosition()));

            }

        }
    }
}


