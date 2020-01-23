package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductsByCategoryIdResponse.VenuesBean> venues;
    private ShopItemListListener shopItemListListener;

    public StoreListAdapter(Context context,List<ProductsByCategoryIdResponse.VenuesBean> venues,ShopItemListListener shopItemListListener1) {
        this.context = context;
        this.venues = venues;
        this.shopItemListListener=shopItemListListener1;
    }

    public  interface  ShopItemListListener {
        void onShopItemClick (int position,String VenueId);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_adapter,parent,false);
        return new StoreListAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.tvStoreName.setText(venues.get(position).getVenue_name());
        holder.tvStoreTime.setText(venues.get(position).getOpening_time()+" - "+venues.get(position).getClosing_time());
        holder.tvStoreType.setText(venues.get(position).getVenue_name());
        holder.tvStoreDistance.setText(venues.get(position).getDistance()+" KM");
        holder.tvStoreOffers.setText(""+venues.get(position).getTotal_offers());

        //holder.tvStoreFreeDelivery.setText(venues.get(position).getDelivery());
        holder.StoreRating.setNumStars((int)venues.get(position).getStars());
        holder.tvStoreFreeDelivery.setVisibility(View.GONE);

        try {
            if (TextUtils.isEmpty(venues.get(position).getVenue_images())) {

            } else {
                Picasso.get()
                        .load(ApiClientEcom.ImagePath + venues.get(position).getVenue_images())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.storeImage);
            }
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView storeImage;
        private TextView tvStoreName,tvStoreTime,tvStoreType,tvStoreFreeDelivery,tvStoreOffers,tvStoreDistance;
        private RatingBar StoreRating;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            storeImage=itemView.findViewById(R.id.storeImage_storeAdapter);
            tvStoreName=itemView.findViewById(R.id.storeName_storeAdapter);
            tvStoreTime=itemView.findViewById(R.id.storeOpenTime_storeAdapter);
            tvStoreType=itemView.findViewById(R.id.storeType_storeAdapter);
            tvStoreFreeDelivery=itemView.findViewById(R.id.storeFreeDelivery_storeAdapter);
            tvStoreOffers=itemView.findViewById(R.id.storeOffer_storeAdapter);
            tvStoreDistance=itemView.findViewById(R.id.storeDistance_storeAdapter);
            StoreRating=itemView.findViewById(R.id.storeRating_storeAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();

                    if (shopItemListListener!=null){
                        shopItemListListener.onShopItemClick(getLayoutPosition(),venues.get(getLayoutPosition()).getVenue_id());
                    }
                }
            });
        }
    }
}
