package com.safepayu.wallet.adapter.deposit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.safepayu.wallet.R;

import java.util.List;

public class DepositRateAdapter extends RecyclerView.Adapter<DepositRateAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    //private List<AllProductList> modelArrayList;

    public DepositRateAdapter(Context context) {
        this.context = context;
        this.activity = activity;
        //    this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_interest_rate_table_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}