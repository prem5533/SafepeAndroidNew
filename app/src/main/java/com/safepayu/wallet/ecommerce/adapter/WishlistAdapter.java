package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.FlightLocationListViewHolder> {

    private Context context ;

    public WishlistAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WishlistAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_adapter,parent,false);
        return new WishlistAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDayName,tvDayHours ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);


        }

        public void bindData(int position) {

        }
    }
}
