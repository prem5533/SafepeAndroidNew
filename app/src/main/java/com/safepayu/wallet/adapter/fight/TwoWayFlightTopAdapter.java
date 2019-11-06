package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safepayu.wallet.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TwoWayFlightTopAdapter  extends RecyclerView.Adapter<TwoWayFlightTopAdapter.TwoWayFlightTopViewHolder> {
    private Context context ;

    public TwoWayFlightTopAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TwoWayFlightTopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.two_way_top_adapter,parent,false);
        return new TwoWayFlightTopAdapter.TwoWayFlightTopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TwoWayFlightTopViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TwoWayFlightTopViewHolder extends RecyclerView.ViewHolder {
        public TwoWayFlightTopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
