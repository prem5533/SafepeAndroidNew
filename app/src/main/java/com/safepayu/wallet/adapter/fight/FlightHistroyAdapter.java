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
import com.safepayu.wallet.models.request.booking.flight.FlightHistoryResponse;

import java.util.List;

public class FlightHistroyAdapter  extends RecyclerView.Adapter<FlightHistroyAdapter.FlightViewHolder>{

    private Context context;
    private List<FlightHistoryResponse.DataBean> flightHistoryList;
    private FlightHistroyAdapter.FlighHistoryClick flighHistoryClick ;


    public  interface  FlighHistoryClick {
        void onHistoryClick(int position,FlightHistoryResponse.DataBean mOrderId);
    }

    public FlightHistroyAdapter(Context context, List<FlightHistoryResponse.DataBean> flightHistoryList, FlighHistoryClick flighHistoryClick) {
        this.context = context;
        this.flightHistoryList = flightHistoryList;
        this.flighHistoryClick = flighHistoryClick;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flight_histroy_adapter,parent,false);
        return new FlightHistroyAdapter.FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return flightHistoryList.size();
    }

    public class FlightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFlightReferenceNo,tvFlightBookingStatus,tvFlightPnr,tvFlightAmount,tvFlightTimeDate,tvFlightStatus;
        private ImageView imageViewStatus;
        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightReferenceNo = itemView.findViewById(R.id.tv_flight_ticket_ref_no);
            tvFlightBookingStatus = itemView.findViewById(R.id.tv_flight_booking_status);
            tvFlightPnr = itemView.findViewById(R.id.tv_flightpnr);
            tvFlightAmount = itemView.findViewById(R.id.tv_flight_amount);
            tvFlightTimeDate = itemView.findViewById(R.id.tv_flight_time_date);
            tvFlightStatus = itemView.findViewById(R.id.tv_flight_status);
            imageViewStatus = itemView.findViewById(R.id.image_status);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            tvFlightReferenceNo.setText("Order ID: "+flightHistoryList.get(position).getBookingRefNo());
            tvFlightPnr.setText("PNR: "+flightHistoryList.get(position).getAPIRefNo());
            tvFlightAmount.setText(context.getResources().getString(R.string.rupees)+" "+(flightHistoryList.get(position).getOnward_fare()+flightHistoryList.get(position).getReturn_fare()));

            String DateTime =flightHistoryList.get(position).getBookingDate();
            String DT[] = DateTime.split("T");
            String D = DT[0];
            String T = DT[1];
            tvFlightTimeDate.setText(D+" , "+T);

            if (flightHistoryList.get(position).getBookingStatus()==1){
                tvFlightStatus.setText("Payment received, Ticket booking is under process");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.green_500));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success)); }
            else if (flightHistoryList.get(position).getBookingStatus()==2){
                tvFlightStatus.setText("Payment received, Due to some problems We cannot book the ticket. So, cancelled. Payment will be reverted");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.clay_yellow));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pending)); }
            else  if (flightHistoryList.get(position).getBookingStatus()==3){
                tvFlightStatus.setText("Indicates Payment received, Ticket booked successfully");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.green_500));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success)); }
            else if (flightHistoryList.get(position).getBookingStatus()==4){
                tvFlightStatus.setText("After successful confirmation of the ticket, if it is cancelled then this is the status, due to flight delay");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.clay_yellow));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pending)); }
            else if (flightHistoryList.get(position).getBookingStatus()==5){
                tvFlightStatus.setText("Cancellation is in process");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==6){
                tvFlightStatus.setText("Ticket Cancellation is successfully");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==7){
                tvFlightStatus.setText("Ticket cancellation is rejected");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==8){
                tvFlightStatus.setText("Flight ticket is blocked for booking");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success)); }
            else if (flightHistoryList.get(position).getBookingStatus()==9){
                tvFlightStatus.setText("Something went wrong in Payment");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==10){
                tvFlightStatus.setText("Ticket Booking Failed");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==11){
                tvFlightStatus.setText("Ticket not Found");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==12){
                tvFlightStatus.setText("Ticket Partially Cancelled");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==13){
                tvFlightStatus.setText("Partial Cancellation Failed");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==14){
                tvFlightStatus.setText("Request Processed");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==15){
                tvFlightStatus.setText("Request Rejected");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail)); }
            else if (flightHistoryList.get(position).getBookingStatus()==16){
                tvFlightStatus.setText("Ticket Blocking Failed");
                tvFlightStatus.setTextColor(context.getResources().getColor(R.color.red_400));
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail));
            }
        }

        @Override
        public void onClick(View v) {
            if (flighHistoryClick != null) {
                flighHistoryClick.onHistoryClick(getLayoutPosition(),flightHistoryList.get(getLayoutPosition()) );

            }
        }
    }
}
