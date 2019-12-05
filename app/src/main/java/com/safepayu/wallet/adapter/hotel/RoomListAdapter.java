package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.HotelDetailResponse;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private RoomListAdapter.LocationListListener locationListListener;
    private List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList;

    public  interface  LocationListListener {
        void onLocationClickTo (int position,List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList);
    }

    public RoomListAdapter(Context context, List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList1,
                                 RoomListAdapter.LocationListListener locationListListener) {
        this.context = context;
        this.RoomDetailsList = RoomDetailsList1;
        this.locationListListener=locationListListener;
    }

    @NonNull
    @Override
    public RoomListAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_list_adapter,parent,false);
        return new RoomListAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return RoomDetailsList.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvHotelName,tvHotelPrice,tvHotelLocality,tvHotelDescription,tvMore ;
        private ImageView ivHotelImage;
        private RatingBar tvHotelStar;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.roomType_roomListAdapter);
            tvHotelPrice = itemView.findViewById(R.id.roomPrice_roomListAdapter);
//            tvHotelStar = itemView.findViewById(R.id.ratingBar_availableHotelAdapter);
//            tvHotelLocality = itemView.findViewById(R.id.hotelLocation_availableHotelAdapter);
//            tvHotelDescription = itemView.findViewById(R.id.services_availableHotelAdapter);
//            tvMore = itemView.findViewById(R.id.moreBtn_availableHotelAdapter);
//            ivHotelImage = itemView.findViewById(R.id.imageHotel_availableHotelAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvHotelName.setText(RoomDetailsList.get(position).getRoomType());
            tvHotelPrice.setText(context.getResources().getString(R.string.rupees)+" "+RoomDetailsList.get(position).getRoomTotal());
        }

        @Override
        public void onClick(View v) {

            if (locationListListener != null) {
                locationListListener.onLocationClickTo(getLayoutPosition(),RoomDetailsList);

            }

        }
    }
}

