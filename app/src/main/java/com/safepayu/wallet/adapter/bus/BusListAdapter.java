package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.bus.BusSeat_DetailActivity;
import com.safepayu.wallet.models.response.booking.bus.BusListResponse;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.BusViewHolder> {
    private Context context;
    private OnBusItemClickListener onItemClickListener;
    private BusListResponse.DataBean dataBean;

    public BusListAdapter(Context context,BusListResponse.DataBean dataBean1,OnBusItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.dataBean=dataBean1;
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

        holder.TravelerNameTV.setText(dataBean.getAvailableTrips().get(position).getDisplayName());
        holder.BusTypeTv.setText(dataBean.getAvailableTrips().get(position).getBusType());
        holder.DepartureTimeTV.setText(dataBean.getAvailableTrips().get(position).getDepartureTime());
        holder.ArrivalTimeTV.setText(dataBean.getAvailableTrips().get(position).getArrivalTime());
        holder.AvailableSeatsTV.setText(dataBean.getAvailableTrips().get(position).getAvailableSeats());
        holder.DurationTV.setText(dataBean.getAvailableTrips().get(position).getDuration());
        holder.BoardingLocationTV.setText(dataBean.getAvailableTrips().get(position).getBoardingTimes().get(0).getLocation());
        holder.CancellationPolicyTV.setText(dataBean.getAvailableTrips().get(position).getCancellationPolicy());
        holder.PriceTV.setText(context.getResources().getString(R.string.rupees)+" "+dataBean.getAvailableTrips().get(position).getFares());
    }

    @Override
    public int getItemCount() {
        return dataBean.getAvailableTrips().size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView TravelerNameTV,PriceTV,BusTypeTv,DepartureTimeTV,ArrivalTimeTV,AvailableSeatsTV,DurationTV;
        private TextView BoardingLocationTV,CancellationPolicyTV;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);

            TravelerNameTV=itemView.findViewById(R.id.travelName_buslist_adapter);
            BusTypeTv=itemView.findViewById(R.id.busType_buslist_adapter);
            PriceTV=itemView.findViewById(R.id.price_buslist_adapter);
            DepartureTimeTV=itemView.findViewById(R.id.departureTime_buslist_adapter);
            ArrivalTimeTV=itemView.findViewById(R.id.arrival_buslist_adapter);
            AvailableSeatsTV=itemView.findViewById(R.id.seats_buslist_adapter);
            DurationTV=itemView.findViewById(R.id.duration_buslist_adapter);
            CancellationPolicyTV=itemView.findViewById(R.id.cancellation_buslist_adapter);
            BoardingLocationTV=itemView.findViewById(R.id.boardingLocation_buslist_adapter);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context, BusSeat_DetailActivity.class);
            context.startActivity(intent);
        }
    }
}
