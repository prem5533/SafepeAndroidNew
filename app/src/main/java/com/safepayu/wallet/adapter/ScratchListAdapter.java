package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.CoinLogResponse;

import java.util.List;

public class ScratchListAdapter extends RecyclerView.Adapter<ScratchListAdapter.MyOrderViewHolder> {

    private Context context;
    private List<CoinLogResponse.DataBean.LogBean> mCoinItem;
    private ScratchListAdapter.OnScratchSelectListener callback;

    public ScratchListAdapter(Context context, List<CoinLogResponse.DataBean.LogBean> mCoinItem, OnScratchSelectListener callback) {
        this.context = context;
        this.mCoinItem = mCoinItem;
        this.callback = callback;
    }

    public interface OnScratchSelectListener {
        void onScratchSelect(int position,CoinLogResponse.DataBean.LogBean mLogCoin);
    }
    @NonNull
    @Override
    public ScratchListAdapter.MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        // For First Item with Big ImageView , Other are small
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.scratch_list_adapter, parent, false);

        return new ScratchListAdapter.MyOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScratchListAdapter.MyOrderViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mCoinItem.size();
    }



    public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ScratchView scratchView;
        private CardView ivSimple,ivScratch;
        private TextView tvNumberCoin,tvCreatedTime;
        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            //scratchView = itemView.findViewById(R.id.scratchView_scratchListAdapter);

            ivSimple = itemView.findViewById(R.id.simple_scratchListAdapter);
            ivScratch = itemView.findViewById(R.id.scratch_scratchListAdapter);
            tvNumberCoin = itemView.findViewById(R.id.tv_number_of_coin);
            tvCreatedTime = itemView.findViewById(R.id.tv_reward_created);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {

          if (mCoinItem.get(position).getStatus()==0){
              ivSimple.setVisibility(View.GONE);
              ivScratch.setVisibility(View.VISIBLE);
              if (mCoinItem.get(position).getAmount()==1) {
                  tvNumberCoin.setText(String.valueOf(mCoinItem.get(position).getAmount()) + " Coin");
              }
              else {
                  tvNumberCoin.setText(String.valueOf(mCoinItem.get(position).getAmount())+" Coins"); }
              tvCreatedTime.setText(mCoinItem.get(position).getCreated_at());
          }
          else if (mCoinItem.get(position).getStatus()==1){
              ivSimple.setVisibility(View.VISIBLE);
              ivScratch.setVisibility(View.GONE);
              if (mCoinItem.get(position).getAmount()==1) {
                  tvNumberCoin.setText(String.valueOf(mCoinItem.get(position).getAmount()) + " Coin");
              }
              else {
                  tvNumberCoin.setText(String.valueOf(mCoinItem.get(position).getAmount())+" Coins"); }

              tvCreatedTime.setText(mCoinItem.get(position).getCreated_at());
          }


           /* if (position==2 || position ==4){
                ivSimple.setVisibility(View.GONE);
            }else {
                ivScratch.setVisibility(View.GONE);
            }*/

        }

        @Override
        public void onClick(View v) {

            if (callback != null) {
                callback.onScratchSelect(getLayoutPosition(),mCoinItem.get(getLayoutPosition()) );

            }
        }
    }
}
