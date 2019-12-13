package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.flight.FlightBlockTicketResponse;

import java.util.ArrayList;
import java.util.List;

public class FlightTravellersList extends RecyclerView.Adapter<FlightTravellersList.TravellerViewHolder> {
    private Context context;
    ArrayList<String>adultList = new ArrayList<>();

    public FlightTravellersList(Context context, ArrayList<String> adultList) {
        this.context = context;
        this.adultList = adultList;
    }

    @NonNull
    @Override
    public TravellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flight_booking_dialog_adapter,parent,false);
        return new FlightTravellersList.TravellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravellerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return adultList.size();
    }

    public class TravellerViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlightPassengeFullName,tvPassengerTitle;
        private ImageView image;
        String gender ,firstName,secondName,travellersType;
        public TravellerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightPassengeFullName = itemView.findViewById(R.id.tv_flight_passenge_full_name);
            image = itemView.findViewById(R.id.flight_passenger_image);
            tvPassengerTitle = itemView.findViewById(R.id.tv_passenger_type);
        }

        public void bindData(int position) {
            String name =   adultList.get(position);
            String[] separated = name.split("~");
            gender = separated[0];
            firstName = separated[1];
            secondName = separated[2];
            travellersType = separated[3];
            tvFlightPassengeFullName.setText(gender+" "+firstName+" "+secondName);
            if (gender.equals("Mr.")){
                image.setImageResource(R.drawable.ic_flight_man);
                tvPassengerTitle.setText("Adult");
            }
            else if (gender.equals("Ms.")){
                image.setImageResource(R.drawable.ic_flight_women);
                tvPassengerTitle.setText("Adult");

            }else if (gender.equals("Mrs.")){
                image.setImageResource(R.drawable.ic_flight_mrs);
                tvPassengerTitle.setText("Adult");
            }

            else if (travellersType.equals("chd")){
                image.setImageResource(R.drawable.ic_flight_child_boy);
                tvPassengerTitle.setText("Child");
            }
            else if (travellersType.equals("inft")){
                image.setImageResource(R.drawable.ic_flight_infant);
                tvPassengerTitle.setText("Infant");
            }
        }
    }
}
