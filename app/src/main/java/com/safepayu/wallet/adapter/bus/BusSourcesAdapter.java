package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class BusSourcesAdapter extends RecyclerView.Adapter<BusSourcesAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private String FromType;
    ArrayList<String> Buslist;
    private BusSourcesAdapter.LocationListListener locationListListener;


    public  interface  LocationListListener {
        void onLocationClickFrom (int position, ArrayList<String> Buslist);
        void onLocationClickTo (int position, ArrayList<String> Buslist);
    }

    public BusSourcesAdapter(Context context, ArrayList<String> Buslist1, BusSourcesAdapter.LocationListListener locationListListener,String FromType1) {
        this.context = context;
        this.Buslist = Buslist1;
        this.locationListListener=locationListListener;
        this.FromType=FromType1;
    }

    @NonNull
    @Override
    public BusSourcesAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_location_adapter,parent,false);
        return new BusSourcesAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusSourcesAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return Buslist.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvFlightCityName ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightCityName = itemView.findViewById(R.id.busSourceName_adapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            tvFlightCityName.setText(Buslist.get(position));
        }

        @Override
        public void onClick(View v) {

            if (FromType.equalsIgnoreCase("From")){
                if (locationListListener != null) {
                    locationListListener.onLocationClickFrom(getLayoutPosition(),Buslist );

                }
            }else {
                if (locationListListener != null) {
                    locationListListener.onLocationClickTo(getLayoutPosition(),Buslist);

                }
            }

        }
    }
}
