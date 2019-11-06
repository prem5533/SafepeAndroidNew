package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safepayu.wallet.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TwoWayFlightListAdapter extends RecyclerView.Adapter<TwoWayFlightListAdapter.FlightTwoWayViewHodler> {

    private Context context;

    public TwoWayFlightListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FlightTwoWayViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.two_way_flight_adapter,parent,false);
        return new TwoWayFlightListAdapter.FlightTwoWayViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightTwoWayViewHodler holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class FlightTwoWayViewHodler extends RecyclerView.ViewHolder {
        public FlightTwoWayViewHodler(@NonNull View itemView) {
            super(itemView);
        }
    }
}
