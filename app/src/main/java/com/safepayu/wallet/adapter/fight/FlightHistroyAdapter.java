package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView tvFlightReferenceNo,tvFlightBookingStatus;
        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightReferenceNo = itemView.findViewById(R.id.tv_flight_ticket_ref_no);
            tvFlightBookingStatus = itemView.findViewById(R.id.tv_flight_booking_status);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            tvFlightReferenceNo.setText("Order ID: "+flightHistoryList.get(position).getBookingRefNo());
            tvFlightReferenceNo.setText("Booking: "+flightHistoryList.get(position).getBookingRefNo());
        }

        @Override
        public void onClick(View v) {
            if (flighHistoryClick != null) {
                flighHistoryClick.onHistoryClick(getLayoutPosition(),flightHistoryList.get(getLayoutPosition()) );

            }
        }
    }
}
