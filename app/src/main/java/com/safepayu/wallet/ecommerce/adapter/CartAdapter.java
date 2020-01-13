package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.FlightLocationListViewHolder> {

    private Context context ;

    public CartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_adapter,parent,false);
        return new CartAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return 1;
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
