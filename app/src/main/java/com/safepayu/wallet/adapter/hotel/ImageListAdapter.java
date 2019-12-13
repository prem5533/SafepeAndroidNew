package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private int Size=0;
    private String StarSize="";
    private List<String> ImageList;

    public ImageListAdapter(Context context,List<String> ImageList1) {
        this.context = context;
        this.ImageList=ImageList1;
    }

    @NonNull
    @Override
    public ImageListAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_hotel_images_adapter,parent,false);
        return new ImageListAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return ImageList.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivHotelImage;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHotelImage = itemView.findViewById(R.id.images_hotelImagesAdapter);
        }

        public void bindData(final int position) {
            try {
                if (TextUtils.isEmpty(ImageList.get(position))){
                    ivHotelImage.setImageDrawable(context.getResources().getDrawable(R.drawable.image_not_available));
                }else {
                    Picasso.get()
                            .load(ImageList.get(position))
                            .error(context.getResources().getDrawable(R.drawable.image_not_available))
                            .into(ivHotelImage);
                }
            }catch (Exception er){
                er.printStackTrace();
            }
        }
    }
}


