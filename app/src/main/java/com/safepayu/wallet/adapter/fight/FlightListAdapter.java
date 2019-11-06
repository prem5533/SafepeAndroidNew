package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safepayu.wallet.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FlightListAdapter  extends RecyclerView.Adapter<FlightListAdapter.FlightViewHolder> {
    private Context context;

    public FlightListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.flight_adapter,parent,false);
        return new FlightListAdapter.FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class FlightViewHolder extends RecyclerView.ViewHolder {
        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
