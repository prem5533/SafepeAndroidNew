package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.flight.AirportLocationResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class FlightLocationAdapter extends RecyclerView.Adapter<FlightLocationAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private List<AirportLocationResponse.DataBean> airportLocationResponse;
    private LocationListListener locationListListener;


    public  interface  LocationListListener {
        void onLocationClickFrom(int position, AirportLocationResponse.DataBean mLocationResponse);
        void onLocationClickTo(int position, AirportLocationResponse.DataBean mLocationResponse);
    }

    public FlightLocationAdapter(Context context, List<AirportLocationResponse.DataBean> airportLocationResponse, LocationListListener locationListListener) {
        this.context = context;
        this.airportLocationResponse = airportLocationResponse;
        this.locationListListener = locationListListener;
    }

    @NonNull
    @Override
    public FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flight_location_adapter,parent,false);
        return new FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return airportLocationResponse.size();
    }

    public void filterList( List<AirportLocationResponse.DataBean>filterdNames) {
       this.airportLocationResponse = filterdNames;
        notifyDataSetChanged();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvFlightCityName , tvFlightDescName , tvFlightCode ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightCityName = itemView.findViewById(R.id.tv_flight_city_name);
            tvFlightDescName = itemView.findViewById(R.id.tv_flight_desc_name);
            tvFlightCode = itemView.findViewById(R.id.tv_flight_code);

            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            tvFlightCityName.setText(airportLocationResponse.get(position).getCity());
            tvFlightDescName.setText(airportLocationResponse.get(position).getAirportDesc());
            tvFlightCode.setText(airportLocationResponse.get(position).getAirportCode());
        }

        @Override
        public void onClick(View v) {
            SharedPreferences sharedPreferences = context. getSharedPreferences("myKey", MODE_PRIVATE);
            String value = sharedPreferences.getString("value","To");
            if (value.equals("From")){
                if (locationListListener != null) {
                    locationListListener.onLocationClickFrom(getLayoutPosition(),airportLocationResponse.get(getLayoutPosition()) );

                }
            }else if (value.equals("To")){
                if (locationListListener != null) {
                    locationListListener.onLocationClickTo(getLayoutPosition(),airportLocationResponse.get(getLayoutPosition()) );

                }
            }

        }
    }
}
