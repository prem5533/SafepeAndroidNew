package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class TwoWayFlightListAdapter extends RecyclerView.Adapter<TwoWayFlightListAdapter.FlightTwoWayViewHodler> {

    private Context context;
    private List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> mItem;
    private OnFlightItemListener onFlightItemListener;
    int row_index = -1,   selectedPosition;
    private View selectedPackageView;
    private int selectedPackagePosition = -1;
    private View selectedOnwardView = null;

    public interface OnFlightItemListener{
        void onFlightItemListerne(int position, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mFlightItemListenre,LinearLayout liOnward);
    }

    public TwoWayFlightListAdapter(Context context, List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> mItem, OnFlightItemListener onFlightItemListener) {
        this.context = context;
        this.mItem = mItem;
        this.onFlightItemListener = onFlightItemListener;
    }

   /* public AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean getSelectedData() {
        if (selectedPackagePosition != -1)
            return mItem.get(selectedPackagePosition);
        else
            return null;
    }*/

    @NonNull
    @Override
    public FlightTwoWayViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.two_way_flight_adapter,parent,false);
        return new TwoWayFlightListAdapter.FlightTwoWayViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightTwoWayViewHodler holder, int position) {
        holder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class FlightTwoWayViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvflight_name1,tvflight_name2,tvflight_rs,tvflight_departure_time,tvtake_flight_time,tvflight_arrival_time;
        private ImageView imageFlight;
        private FrameLayout frameTwoStopLine,frameOneStop;
        private View non_stop;
        private LinearLayout liOnwards;

        public FlightTwoWayViewHodler(@NonNull View itemView) {
            super(itemView);
            tvflight_name1 = itemView.findViewById(R.id.flight_name1);
            tvflight_name2 = itemView.findViewById(R.id.flight_name2);
            tvflight_rs = itemView.findViewById(R.id.flight_rs);
            tvflight_departure_time = itemView.findViewById(R.id.tv_flight_departure_time);
            tvtake_flight_time = itemView.findViewById(R.id.tv_take_flight_time);
            tvflight_arrival_time = itemView.findViewById(R.id.tv_flight_arrival_time);
            imageFlight = itemView.findViewById(R.id.image_flight_icon);
            frameTwoStopLine = itemView.findViewById(R.id.frame_two_stop);
            frameOneStop = itemView.findViewById(R.id.frame_one_stop);
            non_stop = itemView.findViewById(R.id.non_stop);
            liOnwards = itemView.findViewById(R.id.li_onwards);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {

          //  selectedPosition = getAdapterPosition();

        /*    try{
                if (selectedPackageView == null) {
                    selectedPackageView = itemView;
                    itemView.setBackgroundColor(context.getResources().getColor(R.color.green_100));
                } else {

                    selectedPackageView.setBackgroundColor(context.getResources().getColor(R.color.white));
                    selectedPackageView = itemView;
                    itemView.setBackgroundColor(context.getResources().getColor(R.color.green_100));

                }
            }catch (Exception e){
                e.printStackTrace();
            }*/

            liOnwards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onFlightItemListener != null) {
                        onFlightItemListener.onFlightItemListerne(getLayoutPosition(),mItem.get(getLayoutPosition()),liOnwards);
                        // notifyDataSetChanged();
                    }
                 /*   row_index=position;
                    notifyDataSetChanged();*/
                }
            });

           /* if(row_index==position){
                liOnwards.setBackgroundColor(Color.parseColor("#C8E6C9"));
            } else
            { liOnwards.setBackgroundColor(Color.parseColor("#ffffff")); }*/
            int flightStop =   mItem.get(position).getFlightSegments().size();
            tvflight_name1.setText(mItem.get(position).getFlightSegments().get(0).getAirLineName());
            tvflight_name2.setText(mItem.get(position).getFlightSegments().get(0).getOperatingAirlineCode()+" "+mItem.get(position).getFlightSegments().get(0).getOperatingAirlineFlightNumber());

            int passengercount = mItem.get(position).getFareDetails().getFareBreakUp().getFareAry().get(0).getIntPassengerCount();
            int intAmount = mItem.get(position).getFareDetails().getFareBreakUp().getFareAry().get(0).getIntTaxDataArray().get(0).getIntAmount();
            int totalAmt =  intAmount/passengercount;
            tvflight_rs.setText(context.getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(totalAmt));

            //*************set image****************
            Picasso.get().load("http://webapi.i2space.co.in/" + mItem.get(position).getFlightSegments().get(0).getImagePath()).into(imageFlight);


            //************set flight time***********
            String Date, Time, h, m;
            String depTime = mItem.get(position).getFlightSegments().get(0).getDepartureDateTime();
            String[] separated = depTime.split("T");
            Date = separated[0];
            Time = separated[1];
            String[] separatedTime = Time.split(":");
            h = separatedTime[0];
            m = separatedTime[1];
            tvflight_departure_time.setText(h + ":" + m);


            if (flightStop>1){
                String arrTime = mItem.get(position).getFlightSegments().get(flightStop-1).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tvflight_arrival_time.setText(h + ":" + m);

            }
            else {
                String arrTime = mItem.get(position).getFlightSegments().get(0).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tvflight_arrival_time.setText(h + ":" + m);
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


                tvtake_flight_time.setText(hours+"h "+minutes+"m");
            }



            //*********set flight count*****************

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
           /* if (onFlightItemListener != null) {
                onFlightItemListener.onFlightItemListerne(getLayoutPosition(),mItem.get(getLayoutPosition()),liOnwards);
               // notifyDataSetChanged();
            }*/
          /*  if (selectedOnwardView == null) {
                selectedPackagePosition = getLayoutPosition();
                selectedOnwardView = liOnwards;
                liOnwards.setBackground(context.getResources().getDrawable(R.drawable.package_selected));
            } else {
                selectedOnwardView.setBackground(context.getResources().getDrawable(R.drawable.package_normal));
                selectedPackagePosition = getLayoutPosition();
                selectedOnwardView = liOnwards;
                liOnwards.setBackground(context.getResources().getDrawable(R.drawable.package_selected));

            }*/

        }
    }
}
