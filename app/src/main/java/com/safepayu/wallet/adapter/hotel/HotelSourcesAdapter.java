package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class HotelSourcesAdapter extends RecyclerView.Adapter<HotelSourcesAdapter.FlightLocationListViewHolder> {

    private Context context ;
    ArrayList<String> Hotellist;
    private HotelSourcesAdapter.LocationListListener locationListListener;


    public  interface  LocationListListener {
        void onLocationClickTo (int position);
    }

    public HotelSourcesAdapter(Context context, ArrayList<String> Buslist1, HotelSourcesAdapter.LocationListListener locationListListener) {
        this.context = context;
        this.Hotellist = Buslist1;
        this.locationListListener=locationListListener;
    }

    @NonNull
    @Override
    public HotelSourcesAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_location_adapter,parent,false);
        return new HotelSourcesAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelSourcesAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return Hotellist.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvHotelCityName ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelCityName = itemView.findViewById(R.id.busSourceName_adapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            tvHotelCityName.setText(Hotellist.get(position));
        }

        @Override
        public void onClick(View v) {

            if (locationListListener != null) {
                locationListListener.onLocationClickTo(getLayoutPosition());

            }

        }
    }
}
