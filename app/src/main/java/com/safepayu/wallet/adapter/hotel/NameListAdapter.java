package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.HotelDetailResponse;

import java.util.List;

public class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private int size=0;
    private String Gender="";
    private NameListAdapter.LocationListListener locationListListener;
    private List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList;
    String[] users = { "Select Gender", "Male", "Female", "Other" };

    public  interface  LocationListListener {
        void onLocationClickTo (int position,List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList);
    }

    public NameListAdapter(Context context,int size1) {
        this.context = context;
        this.size=size1;
    }

    @NonNull
    @Override
    public NameListAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.name_list_adapter,parent,false);
        return new NameListAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameListAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener{
        private TextView tvHotelName,tvHotelPrice,tvHotelLocality,tvHotelDescription,tvMore ;
        private Spinner spinnerGender;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvHotelName = itemView.findViewById(R.id.roomType_roomListAdapter);
            //tvHotelPrice = itemView.findViewById(R.id.roomPrice_roomListAdapter);
//            tvHotelStar = itemView.findViewById(R.id.ratingBar_availableHotelAdapter);
//            tvHotelLocality = itemView.findViewById(R.id.hotelLocation_availableHotelAdapter);
//            tvHotelDescription = itemView.findViewById(R.id.services_availableHotelAdapter);
//            tvMore = itemView.findViewById(R.id.moreBtn_availableHotelAdapter);
//            ivHotelImage = itemView.findViewById(R.id.imageHotel_availableHotelAdapter);

            spinnerGender = itemView.findViewById(R.id.spinnerGender);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, users);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGender.setAdapter(adapter);
            spinnerGender.setOnItemSelectedListener(this);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
//            tvHotelName.setText(RoomDetailsList.get(position).getRoomType());
//            tvHotelPrice.setText(context.getResources().getString(R.string.rupees)+" "+RoomDetailsList.get(position).getRoomTotal());
        }

        @Override
        public void onClick(View v) {

            if (locationListListener != null) {
               // locationListListener.onLocationClickTo(getLayoutPosition(),RoomDetailsList);

            }

        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i!=0){
                if (TextUtils.isEmpty(Gender)){
                    Toast.makeText(context, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
