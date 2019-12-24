package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.bus.BusHistoryResponse;

import java.util.ArrayList;
import java.util.List;

public class BusHistoryListAdapter extends RecyclerView.Adapter<BusHistoryListAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private BusHistoryListAdapter.BusBookListListener bookListListener;
    private BusHistoryListAdapter.DownloadListListener downloadListListener;
    private List<BusHistoryResponse.DataBean> data;

    public  interface  BusBookListListener {
        void onLocationClickTo (int position,String BookingRefNo, String SeatNos);
    }

    public  interface  DownloadListListener {
        void onLocationClickTo (String BookingRefNo);
    }

    public BusHistoryListAdapter(Context context, List<BusHistoryResponse.DataBean> data1,
                                   BusHistoryListAdapter.BusBookListListener bookListListener,BusHistoryListAdapter.DownloadListListener downloadListListener) {
        this.context = context;
        this.data = data1;
        this.bookListListener=bookListListener;
        this.downloadListListener=downloadListListener;
    }

    @NonNull
    @Override
    public BusHistoryListAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_history_adapter,parent,false);
        return new BusHistoryListAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusHistoryListAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTrip,tvTripPrice,tvTripBusName,tvTripDate, tvTripSeats,tvStatus;
        private Button CancelBtn,DownloadBtn;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTrip = itemView.findViewById(R.id.trip_busHistoryAdapter);
            tvTripBusName = itemView.findViewById(R.id.tripBusName_busHistoryAdapter);
            tvTripDate = itemView.findViewById(R.id.tripDate_busHistoryAdapter);
            tvTripSeats = itemView.findViewById(R.id.tripSeats_busHistoryAdapter);
            tvTripPrice = itemView.findViewById(R.id.tripFare_busHistoryAdapter);
            tvStatus = itemView.findViewById(R.id.cancel_busHistoryAdapter);
            CancelBtn = itemView.findViewById(R.id.cancelBtn_busHistoryAdapter);
            DownloadBtn = itemView.findViewById(R.id.downloadBtn_busHistoryAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvTrip.setText(data.get(position).getSourceName()+" To "+data.get(position).getDestinationName());
            tvTripBusName.setText(data.get(position).getDisplayName());
            tvTripDate.setText(data.get(position).getJourneyDate());

            tvTripPrice.setText(context.getResources().getString(R.string.rupees)+" "+data.get(position).getActualFare());

            try {
                String seatNo=data.get(position).getSeatNos();
                seatNo=seatNo+"~";
                ArrayList<String> seat=new ArrayList<>();
                int start=0; String seatShow="";
                for (int j=0;j<seatNo.length();j++){
                    char c=seatNo.charAt(j);
                    if (c=='~'){
                        seat.add(seatNo.substring(start,j));
                        start=j+1;
                    }
                }

                for (int k=0;k<seat.size();k++){
                    if (k==seat.size()-1){
                        seatShow=seatShow+seat.get(k);
                    }else {
                        seatShow=seatShow+seat.get(k)+",";
                    }
                }
                tvTripSeats.setText(seatShow);
            }catch (Exception e){
                tvTripSeats.setText("0");
                e.printStackTrace();
            }

            if (data.get(position).getBookingStatus().equalsIgnoreCase("Booked")){
                CancelBtn.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.GONE);
            }else if (data.get(position).getBookingStatus().equalsIgnoreCase("Cancelled")){
                CancelBtn.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
            }else {
                CancelBtn.setText("Pending");
                CancelBtn.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
            }

            CancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookListListener != null) {
                        bookListListener.onLocationClickTo(getLayoutPosition(),data.get(getLayoutPosition()).getBookingRefNo(),
                                data.get(getLayoutPosition()).getSeatNos());
                    }
                }
            });

            DownloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (downloadListListener != null) {
                        downloadListListener.onLocationClickTo(data.get(getLayoutPosition()).getBookingRefNo());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}

