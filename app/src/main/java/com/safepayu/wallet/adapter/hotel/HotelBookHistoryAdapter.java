package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.hotel.HotelHistoryResponse;

import java.util.List;

public class HotelBookHistoryAdapter extends RecyclerView.Adapter<HotelBookHistoryAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private HotelBookHistoryAdapter.HotelBookListListener bookListListener;
    private List<HotelHistoryResponse.DataBean> data;

    public  interface  HotelBookListListener {
        void onLocationClickTo (int position,String BookingRefNo, HotelHistoryResponse.DataBean.HotelPolicyBean HotelPolicy);
    }

    public HotelBookHistoryAdapter(Context context, List<HotelHistoryResponse.DataBean> data1,
                           HotelBookHistoryAdapter.HotelBookListListener bookListListener) {
        this.context = context;
        this.data = data1;
        this.bookListListener=bookListListener;
    }

    @NonNull
    @Override
    public HotelBookHistoryAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_history_adapter,parent,false);
        return new HotelBookHistoryAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelBookHistoryAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvHotelName,tvHotelPrice,tvHotelPlace,tvHotelChickIn, tvHotelChickOut,tvStatus;
        private Button CancelBtn;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.hotelName_hotelHistoryAdapter);
            tvHotelPlace = itemView.findViewById(R.id.hotelPlace_hotelHistoryAdapter);
            tvHotelChickIn = itemView.findViewById(R.id.hotelCheckIn_hotelHistoryAdapter);
            tvHotelChickOut = itemView.findViewById(R.id.hotelCheckOut_hotelHistoryAdapter);
            tvHotelPrice = itemView.findViewById(R.id.hotelFare_hotelHistoryAdapter);
            tvStatus = itemView.findViewById(R.id.hotelCancel_hotelHistoryAdapter);
            CancelBtn = itemView.findViewById(R.id.hotelCancelBtn_hotelHistoryAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvHotelName.setText(data.get(position).getHotelDetail().getHotelName());
            tvHotelPlace.setText(data.get(position).getCityName());
            tvHotelChickIn.setText(data.get(position).getArrivalDate());
            tvHotelChickOut.setText(data.get(position).getDepartureDate());
            tvHotelPrice.setText(context.getResources().getString(R.string.rupees)+" "+data.get(position).getFare());

            if (data.get(position).getStatus()==3){
                CancelBtn.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.GONE);
            }else {
                CancelBtn.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
            }

            CancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookListListener != null) {
                        bookListListener.onLocationClickTo(getLayoutPosition(),data.get(getLayoutPosition()).getBookingRefNo(),
                                data.get(getLayoutPosition()).getHotelPolicy());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {



        }
    }
}

