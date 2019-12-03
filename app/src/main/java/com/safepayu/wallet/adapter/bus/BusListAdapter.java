package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
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
        void onBusItemSelect(BusSelectModel busSelectModel);
    }


    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.buslist_adapter,parent,false);
        return new BusListAdapter.BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BusViewHolder holder, final int position) {

        holder.TravelerNameTV.setText(dataBean.getAvailableTrips().get(position).getDisplayName());
        holder.BusTypeTv.setText(dataBean.getAvailableTrips().get(position).getBusType());
        holder.DepartureTimeTV.setText(dataBean.getAvailableTrips().get(position).getDepartureTime());
        holder.ArrivalTimeTV.setText(dataBean.getAvailableTrips().get(position).getArrivalTime());
        holder.AvailableSeatsTV.setText(dataBean.getAvailableTrips().get(position).getAvailableSeats());
        holder.DurationTV.setText(dataBean.getAvailableTrips().get(position).getDuration());
        holder.BoardingLocationTV.setText(dataBean.getAvailableTrips().get(position).getBoardingTimes().get(0).getLocation());
        holder.CancellationPolicyTV.setText(dataBean.getAvailableTrips().get(position).getCancellationPolicy());
        holder.PriceTV.setText(context.getResources().getString(R.string.rupees)+" "+dataBean.getAvailableTrips().get(position).getFares());

        holder.BusSingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusSelectModel busSelectModel=new BusSelectModel();
                busSelectModel.setAvailableSeats(dataBean.getAvailableTrips().get(position).getAvailableSeats());
                busSelectModel.setBusType(dataBean.getAvailableTrips().get(position).getBusType());
                busSelectModel.setProvider(dataBean.getAvailableTrips().get(position).getProvider());
                busSelectModel.setTravelOperator(dataBean.getAvailableTrips().get(position).getTravels());
                busSelectModel.setTripId(dataBean.getAvailableTrips().get(position).getId());
                busSelectModel.setProvider(dataBean.getAvailableTrips().get(position).getProvider());

                //seatListener.seatsAvailable(busSelectModel);


                onItemClickListener.onBusItemSelect(busSelectModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBean.getAvailableTrips().size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder{

        private TextView TravelerNameTV,PriceTV,BusTypeTv,DepartureTimeTV,ArrivalTimeTV,AvailableSeatsTV,DurationTV;
        private TextView BoardingLocationTV,CancellationPolicyTV;
        private LinearLayout BusSingleItem;

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
            BusSingleItem=itemView.findViewById(R.id.busSingleItem);


        }
    }

    public class BusSelectModel{


        /**
         * TravelerName :
         * BusType :
         * AvailableSeatsTV :
         *
         */
        /**
         * tripType : 1
         * tripId : 86
         * provider : DZ/GmxpuN0rTfFNIdYpjWA==
         * travelOperator : SANGAMITRA
         * returnDate :
         */

        private String TravelerName;
        private String BusType;
        private String AvailableSeats;
        private String tripType;
        private String tripId;
        private String provider;
        private String travelOperator;
        private String returnDate;


        public String getTravelerName() {
            return TravelerName;
        }

        public void setTravelerName(String TravelerName) {
            this.TravelerName = TravelerName;
        }

        public String getBusType() {
            return BusType;
        }

        public void setBusType(String BusType) {
            this.BusType = BusType;
        }

        public String getAvailableSeats() {
            return AvailableSeats;
        }

        public void setAvailableSeats(String AvailableSeats) {
            this.AvailableSeats = AvailableSeats;
        }

        public String getTripType() {
            return tripType;
        }

        public void setTripType(String tripType) {
            this.tripType = tripType;
        }

        public String getTripId() {
            return tripId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getTravelOperator() {
            return travelOperator;
        }

        public void setTravelOperator(String travelOperator) {
            this.travelOperator = travelOperator;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(String returnDate) {
            this.returnDate = returnDate;
        }
    }
}
