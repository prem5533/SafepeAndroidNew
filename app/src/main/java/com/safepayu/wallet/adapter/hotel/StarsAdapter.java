package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

public class StarsAdapter extends RecyclerView.Adapter<StarsAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private int Size=0;
    private String StarSize="";

    public StarsAdapter(Context context,String StarSize1,int Size1) {
        this.context = context;
        this.StarSize=StarSize1;
        this.Size=Size1;
    }

    @NonNull
    @Override
    public StarsAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stars_hotels_adapter,parent,false);
        return new StarsAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarsAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return Size;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivStarImage;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStarImage = itemView.findViewById(R.id.stars_starAdapter);
        }

        public void bindData(final int position) {

            try {
                    if (StarSize.contains(".")){

                        if (position==Size-1){
                            ivStarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_half_star));
                        }else {
                            ivStarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full));
                        }
                    }else {
                        ivStarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full));
                    }
            }catch (Exception e){
                ivStarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_full));
                e.printStackTrace();
            }


        }
    }
}

