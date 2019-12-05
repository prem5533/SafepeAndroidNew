package com.safepayu.wallet.adapter.hotel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.hotel.AvailableHotelsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AvailableHotelAdapter extends RecyclerView.Adapter<AvailableHotelAdapter.FlightLocationListViewHolder> {

    private Context context ;
    ArrayList<String> Hotellist;
    private ArrayList<String> LocalityList;
    private AvailableHotelAdapter.LocationListListener locationListListener;
    List<AvailableHotelsResponse.DataBean.AvailableHotelsBean> AvailableHotels;


    public  interface  LocationListListener {
        void onLocationClickTo (int position,String HotelId,String HotelName,String WebService,String Provider,String ImgUrl);
    }

    public AvailableHotelAdapter(Context context, List<AvailableHotelsResponse.DataBean.AvailableHotelsBean> AvailableHotels1,
                                  ArrayList<String> LocalityList1,AvailableHotelAdapter.LocationListListener locationListListener) {
        this.context = context;
        this.AvailableHotels = AvailableHotels1;
        this.LocalityList=LocalityList1;
        this.locationListListener=locationListListener;
    }

    @NonNull
    @Override
    public AvailableHotelAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_hotels_adapter,parent,false);
        return new AvailableHotelAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableHotelAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return AvailableHotels.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvHotelName,tvHotelPrice,tvHotelLocality,tvHotelDescription,tvMore ;
        private ImageView ivHotelImage;
        private RatingBar tvHotelStar;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.hotelName_availableHotelAdapter);
            tvHotelPrice = itemView.findViewById(R.id.hotelPrice_availableHotelAdapter);
            tvHotelStar = itemView.findViewById(R.id.ratingBar_availableHotelAdapter);
            tvHotelLocality = itemView.findViewById(R.id.hotelLocation_availableHotelAdapter);
            tvHotelDescription = itemView.findViewById(R.id.services_availableHotelAdapter);
            tvMore = itemView.findViewById(R.id.moreBtn_availableHotelAdapter);
            ivHotelImage = itemView.findViewById(R.id.imageHotel_availableHotelAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvHotelName.setText(AvailableHotels.get(position).getHotelName());
            tvHotelPrice.setText(context.getResources().getString(R.string.rupees)+" "+AvailableHotels.get(position).getRoomDetails().get(0).getRoomTotal());
            if (TextUtils.isEmpty(LocalityList.get(position))){
                tvHotelLocality.setText(" ");
            }else {
                LocalityList.get(position);
            }

            tvHotelStar.setNumStars(Integer.parseInt(AvailableHotels.get(position).getStarRating()));

            try {
                Picasso.get().load(AvailableHotels.get(position).getHotelImages().get(0).getImagepath()).into(ivHotelImage);
            }catch (Exception er){
                er.printStackTrace();
            }

            if (AvailableHotels.get(position).getFacilities().length()>30){
                String facility=AvailableHotels.get(position).getFacilities();
                facility=facility.substring(0,25);
                tvHotelDescription.setText("Facilities - "+facility+"... ");
                tvMore.setVisibility(View.VISIBLE);
            }else {
                tvHotelDescription.setText("Facilities - "+AvailableHotels.get(position).getFacilities());
                tvMore.setVisibility(View.GONE);
            }

            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMessage(AvailableHotels.get(position).getFacilities());
                }
            });

        }

        @Override
        public void onClick(View v) {

            if (locationListListener != null) {
                locationListListener.onLocationClickTo(getLayoutPosition(),AvailableHotels.get(getLayoutPosition()).getHotelId(),
                        AvailableHotels.get(getLayoutPosition()).getHotelName(),AvailableHotels.get(getLayoutPosition()).getWebService(),
                        AvailableHotels.get(getLayoutPosition()).getProvider(),
                        AvailableHotels.get(getLayoutPosition()).getHotelImages().get(0).getImagepath());

            }

        }
    }

    public void showMessage(String Message) {
        new AlertDialog.Builder(context)
                .setTitle("Hotel Facilities")
                .setMessage(Message)
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        dialog.dismiss();

                    }
                })
                .setIcon(context.getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }
}

