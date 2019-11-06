package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safepayu.wallet.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OneWayFlightListAdapter extends RecyclerView.Adapter<OneWayFlightListAdapter.OneWayListViewHolder> {

    private Context context;

    public OneWayFlightListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OneWayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_way_flight_list_adapter,parent,false);
        return new OneWayFlightListAdapter.OneWayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneWayListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class  OneWayListViewHolder extends RecyclerView.ViewHolder {
        public OneWayListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
