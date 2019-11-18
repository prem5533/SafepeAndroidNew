package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.BusViewHolder> {
    private Context context;
    private OnBusItemClickListener onItemClickListener;

    public BusListAdapter(Context context,OnBusItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnBusItemClickListener {
        void onBusItemSelect();
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.buslist_adapter,parent,false);
        return new BusListAdapter.BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class BusViewHolder extends RecyclerView.ViewHolder {
        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 onItemClickListener.onBusItemSelect();

                }
            });
        }
    }
}
