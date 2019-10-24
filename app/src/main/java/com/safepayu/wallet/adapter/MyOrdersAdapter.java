package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safepayu.wallet.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrderViewHolder> {

    private Context context;

    public MyOrdersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        // For First Item with Big ImageView , Other are small
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history_adapter, parent, false);

        return new MyOrdersAdapter.MyOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder {
        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
