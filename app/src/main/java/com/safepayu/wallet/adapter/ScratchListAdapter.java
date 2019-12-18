package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.safepayu.wallet.R;

public class ScratchListAdapter extends RecyclerView.Adapter<ScratchListAdapter.MyOrderViewHolder> {

    private Context context;
    private ScratchListAdapter.OnScratchSelectListener callback;

    public ScratchListAdapter(Context context,ScratchListAdapter.OnScratchSelectListener callback) {
        this.context = context;
        this.callback = callback;

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
        return 5;
    }

    public interface OnScratchSelectListener {
        void onScratchSelect(int position);
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ScratchView scratchView;
        private CardView ivSimple,ivScratch;
        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            //scratchView = itemView.findViewById(R.id.scratchView_scratchListAdapter);

            ivSimple = itemView.findViewById(R.id.simple_scratchListAdapter);
            ivScratch = itemView.findViewById(R.id.scratch_scratchListAdapter);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {

          /*  if (position==2 || position ==4){
                ivSimple.setVisibility(View.GONE);
            }else {
                ivScratch.setVisibility(View.GONE);
            }*/

        }

        @Override
        public void onClick(View v) {

        }
    }
}
