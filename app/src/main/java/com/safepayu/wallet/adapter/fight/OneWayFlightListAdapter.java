package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OneWayFlightListAdapter extends RecyclerView.Adapter<OneWayFlightListAdapter.OneWayListViewHolder> {

    private Context context;
    private List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> mItem;
    private OnFlightItemListener onFlightItemListener;

    public interface OnFlightItemListener{
        void onFlightItemListerne(int position, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mFlightItemListenre);
        void onFlight(int position);
    }

    public OneWayFlightListAdapter(Context context, List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> mItem,OnFlightItemListener onFlightItemListener,OnFlightItemListener onFlight) {
        this.context = context;
        this.mItem = mItem;
        this.onFlightItemListener = onFlightItemListener;
        this.onFlightItemListener = onFlight;
    }

    @NonNull
    @Override
    public OneWayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_way_flight_list_adapter,parent,false);

        return new OneWayFlightListAdapter.OneWayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneWayListViewHolder holder, int position) {
        holder.bindData(position);


    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class  OneWayListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvflightName, tvDepartureCode, tvArrivalCode, tvTakeFlightTime, tvFlightTotalFare, tvDepartureFlightTime, tvArrivalFlightTime,
                tvFlightNumStops,tvOperatingAirlineCodeNumber;
        private ImageView imageFlight;
        private FrameLayout frameTwoStopLine,frameOneStop;
        private View non_stop;

        public OneWayListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvflightName = itemView.findViewById(R.id.tv_flight_name);
            tvDepartureCode = itemView.findViewById(R.id.tv_departure_flight_code);
            tvArrivalCode = itemView.findViewById(R.id.tv_arrival_flight_code);
            tvTakeFlightTime = itemView.findViewById(R.id.tv_take_flight_time);
            tvFlightTotalFare = itemView.findViewById(R.id.tv_fight_rupee);
            tvDepartureFlightTime = itemView.findViewById(R.id.tv_departure_flight_time);
            tvArrivalFlightTime = itemView.findViewById(R.id.tv_arrival_flight_time);
            tvFlightNumStops = itemView.findViewById(R.id.tv_flight_num_stops);
            imageFlight = itemView.findViewById(R.id.image_flight_icon);
            tvOperatingAirlineCodeNumber = itemView.findViewById(R.id.tv_operatingAirlineCode_number);
            frameTwoStopLine = itemView.findViewById(R.id.frame_two_stop);
            frameOneStop = itemView.findViewById(R.id.frame_one_stop);
            non_stop = itemView.findViewById(R.id.non_stop);

            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {




            int flightStop =   mItem.get(position).getFlightSegments().size();
            tvflightName.setText(mItem.get(position).getFlightSegments().get(0).getAirLineName());
            tvDepartureCode.setText(mItem.get(position).getFlightSegments().get(0).getDepartureAirportCode());
            if (flightStop>1){
                tvArrivalCode.setText(mItem.get(position).getFlightSegments().get(flightStop-1).getArrivalAirportCode());
            }
            else {
                tvArrivalCode.setText(mItem.get(position).getFlightSegments().get(0).getArrivalAirportCode());
            }

            //*****************calculate total hour & minute**************
            int     sum = 0;
            int mintemp = 0;
            int hourtemp = 0;
            if (flightStop>0){
                for (int i = 0; i<mItem.get(position).getFlightSegments().size();i++){
                    String duration [] = mItem.get(position).getFlightSegments().get(i).getDuration().split(":");
                    String hour = duration[0];
                    String min = duration[1];
                    String durat [] = min.split(" ");
                    String a = durat[0];
                    String b = durat[1];


                    hourtemp = Integer.parseInt(hour)*60;
                    mintemp = Integer.parseInt(a)+hourtemp;
                    sum = sum+mintemp;
                }

                int hours = sum / 60; //since both are ints, you get an int
                int minutes = sum % 60;


                 tvTakeFlightTime.setText(hours+"h "+minutes+"m");
            }



        /*    String totalFare = String.valueOf(mItem.get(position).getFareDetails().getChargeableFares().getActualBaseFare() +
                    mItem.get(position).getFareDetails().getChargeableFares().getTax() + mItem.get(position).getFareDetails().getChargeableFares().getSTax() +
                    mItem.get(position).getFareDetails().getChargeableFares().getSCharge() + mItem.get(position).getFareDetails().getChargeableFares().getTDiscount() +
                    mItem.get(position).getFareDetails().getChargeableFares().getTPartnerCommission());*/
            int passengercount = mItem.get(position).getFareDetails().getFareBreakUp().getFareAry().get(0).getIntPassengerCount();
            int intAmount = mItem.get(position).getFareDetails().getFareBreakUp().getFareAry().get(0).getIntTaxDataArray().get(0).getIntAmount();
            int totalAmt =  intAmount/passengercount;

            tvFlightTotalFare.setText(context.getResources().getString(R.string.rupees) + " " +NumberFormat.getIntegerInstance().format(totalAmt));

            tvOperatingAirlineCodeNumber.setText(mItem.get(position).getFlightSegments().get(0).getOperatingAirlineCode()+ " "+ mItem.get(position).getFlightSegments().get(0).getOperatingAirlineFlightNumber());

            //************set flight time***********
            String Date, Time, h, m;
            String depTime = mItem.get(position).getFlightSegments().get(0).getDepartureDateTime();
            String[] separated = depTime.split("T");
            Date = separated[0];
            Time = separated[1];
            String[] separatedTime = Time.split(":");
            h = separatedTime[0];
            m = separatedTime[1];
            tvDepartureFlightTime.setText(h + ":" + m);

            if (flightStop>1){
                String arrTime = mItem.get(position).getFlightSegments().get(flightStop-1).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tvArrivalFlightTime.setText(h + ":" + m);

            }
            else {
                String arrTime = mItem.get(position).getFlightSegments().get(0).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tvArrivalFlightTime.setText(h + ":" + m);
            }


            //*************set image****************
            Picasso.get().load("http://webapi.i2space.co.in/" + mItem.get(position).getFlightSegments().get(0).getImagePath()).into(imageFlight);


            //*********set flight count*****************
            if ( mItem.get(position).getFlightSegments().size()>1){
                tvFlightNumStops.setText(mItem.get(position).getFlightSegments().size()-1 +" Stop");

            }
          else  if (String.valueOf(mItem.get(position).getFlightSegments().get(0).getIntNumStops()).equals("null")){
                tvFlightNumStops.setText("Non Stop");
            }
            else {
                tvFlightNumStops.setText(String.valueOf(mItem.get(position).getFlightSegments().get(0).getIntNumStops())+"Stop");
            }

            if (flightStop-1==2){
                frameTwoStopLine.setVisibility(View.VISIBLE);
                frameOneStop.setVisibility(View.GONE);
                non_stop.setVisibility(View.GONE);
            }
            else    if (flightStop-1==1){
                frameTwoStopLine.setVisibility(View.GONE);
                frameOneStop.setVisibility(View.VISIBLE);
                non_stop.setVisibility(View.GONE);
            }
            else {
                frameTwoStopLine.setVisibility(View.GONE);
                frameOneStop.setVisibility(View.GONE);
                non_stop.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onClick(View v) {
            if (onFlightItemListener != null) {
                onFlightItemListener.onFlightItemListerne(getLayoutPosition(),mItem.get(getLayoutPosition()) );
                onFlightItemListener.onFlight(getLayoutPosition());

            }
        }
    }
}
