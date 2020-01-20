package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.TotalCartResponse;
import com.safepayu.wallet.ecommerce.model.response.WishListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private List<WishListResponse.LikesBean> likesBeans;

    public WishlistAdapter(Context context, List<WishListResponse.LikesBean> likesBeans) {
        this.context = context;
        this.likesBeans = likesBeans;
    }

    @NonNull
    @Override
    public WishlistAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_adapter,parent,false);
        return new WishlistAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return likesBeans.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDayName,tvName ,tvActualprice,tvSavers,tvOffprice;
        private ImageView im_wishlist;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);

            im_wishlist =itemView.findViewById(R.id.im_wishlist);
            tvName =itemView.findViewById(R.id.tv_product_name_wishlist);
            tvActualprice =itemView.findViewById(R.id.tv_actualprice_wishlist);
            tvSavers =itemView.findViewById(R.id.tv_savers_wishlist);
            tvOffprice =itemView.findViewById(R.id.tv_offprice_wishlist);


        }

        public void bindData(int position) {
            try {
                if (TextUtils.isEmpty(ApiClientEcom.ImagePath+likesBeans.get(position).getProduct_image())){
                }else {
                    Picasso.get()
                            .load(ApiClientEcom.ImagePath+likesBeans.get(position).getProduct_image())
                            .error(context.getResources().getDrawable(R.drawable.image_not_available))
                            .into(im_wishlist); }
            }catch (Exception er){
                er.printStackTrace(); }
            tvName.setText(likesBeans.get(position).getProduct_name());
            tvActualprice.setText("₹ "+ likesBeans.get(position).getSelling_price());
            tvActualprice.setPaintFlags(tvActualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            String discountPercent = likesBeans.get(position).getOffer_title();
            String percent[] = discountPercent.split(" ");
            String percent1 = percent[0];
            tvSavers.setText(percent1+" Off");

            if (likesBeans.get(position).getOffer_type().equals("discper")){

                Double b = ((Double.parseDouble(likesBeans.get(position).getSelling_price())-((Double.parseDouble(likesBeans.get(position).getSelling_price()))*(Double.parseDouble(likesBeans.get(position).getDisc_per()))/100)));
                tvOffprice.setText("₹ " +String.format("%.3f", b)); }

            else if (likesBeans.get(position).getOffer_type().equals("discamt")){
                tvOffprice.setText("₹ "+String.format("%.2f",(Double.parseDouble(likesBeans.get(position).getSelling_price())- Double.parseDouble(likesBeans.get(position).getDisc_amt())))); }


        }
    }
}
