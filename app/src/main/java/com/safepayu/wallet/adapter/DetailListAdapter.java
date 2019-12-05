package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.hotel.HotelActivity;
import com.safepayu.wallet.adapter.hotel.NameListAdapter;
import com.safepayu.wallet.models.response.booking.HotelDetailResponse;

import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private int size=0,childSize=0,adultSize=0;
    private DetailListAdapter.LocationListListener locationListListener;
    private List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList;
    private NameListAdapter nameListAdultAdapter,nameListChildAdapter;

    public  interface  LocationListListener {
        void onLocationClickTo (int position,List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList);
    }

    public DetailListAdapter(Context context,int size1) {
        this.context = context;
        this.size=size1;
    }

    @NonNull
    @Override
    public DetailListAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_list_adapter,parent,false);
        return new DetailListAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailListAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvHotelName,tvHotelPrice,tvHotelLocality,tvHotelDescription,tvMore ;
        private ImageView ivHotelImage;
        private RatingBar tvHotelStar;
        private RecyclerView recyclerViewAdultList,recyclerViewChildList;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.roomNumber_detailListAdapter);

            recyclerViewAdultList = itemView.findViewById(R.id.detailAdultList_detailListAdapter);
            //recyclerViewAdultList.setNestedScrollingEnabled(false);

            recyclerViewChildList = itemView.findViewById(R.id.detailChildList_detailListAdapter);
           // recyclerViewChildList.setNestedScrollingEnabled(false);

            // tvHotelPrice = itemView.findViewById(R.id.roomPrice_roomListAdapter);
//            tvHotelStar = itemView.findViewById(R.id.ratingBar_availableHotelAdapter);
//            tvHotelLocality = itemView.findViewById(R.id.hotelLocation_availableHotelAdapter);
//            tvHotelDescription = itemView.findViewById(R.id.services_availableHotelAdapter);
//            tvMore = itemView.findViewById(R.id.moreBtn_availableHotelAdapter);
//            ivHotelImage = itemView.findViewById(R.id.imageHotel_availableHotelAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvHotelName.setText("In Room "+position);
            recyclerViewAdultList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            adultSize=Integer.parseInt(HotelActivity.availableHotelRequest.getAdults().get(position));
            nameListAdultAdapter=new NameListAdapter(context,adultSize);
            recyclerViewAdultList.setAdapter(nameListAdultAdapter);


            childSize=Integer.parseInt(HotelActivity.availableHotelRequest.getChildren().get(position));

            recyclerViewChildList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            nameListChildAdapter=new NameListAdapter(context,childSize);
            recyclerViewChildList.setAdapter(nameListChildAdapter);
        }

        @Override
        public void onClick(View v) {

            if (locationListListener != null) {
                locationListListener.onLocationClickTo(getLayoutPosition(),RoomDetailsList);

            }

        }
    }
}


