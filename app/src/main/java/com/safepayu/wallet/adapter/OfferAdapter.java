package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.recharge.MobileRecharge;
import com.safepayu.wallet.models.response.Offer;
import com.safepayu.wallet.models.response.PackageListData;

import java.util.ArrayList;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.RechargeOfferViewHolder> {

    private List<Offer> offers;
    private List<Offer> allOffers;
    private String offerType = "Topup";
    Context context;
    private OnOfferSelectListener callback;

    public interface OnOfferSelectListener {
        void onOfferSelect(int position, Offer offer);
    }

    public OfferAdapter(Context context, ArrayList<Offer> reports,OnOfferSelectListener callback) {
        this.context = context;
        this.allOffers = reports;
        this.offers = getOffers();
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void setOfferType(String offerType){

        this.offerType = offerType;
        this.offers = getOffers();
        this.notifyDataSetChanged();
    }

    public List<Offer> getOffers(){//Toast.makeText(context,"50",Toast.LENGTH_SHORT).show();
        List<Offer> list = new ArrayList<>();
        for (Offer o : allOffers){
            if (o.getCategory().equals(offerType)){
                list.add(o);

            }
        }
        return list;
    }

    public String getAmount(int pos) {
        try {
            return offers.get(pos).getAmount();
        }catch (Exception e){
            return "";
        }
    }

    @Override
    public OfferAdapter.RechargeOfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        // For First Item with Big ImageView , Other are small
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_offers, parent, false);

        return new OfferAdapter.RechargeOfferViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OfferAdapter.RechargeOfferViewHolder holder, final int position) {
        //Toast.makeText(context,"82",Toast.LENGTH_SHORT).show();

        final Offer data = offers.get(position);

        holder.amount.setText(data.getAmount());
        holder.validity.setText(data.getValidity());
        holder.description.setText(data.getShortdesc());
        holder.type.setText(data.getTalktime());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callback.onOfferSelect(position,data);
            }
        });
    }

    public class RechargeOfferViewHolder extends RecyclerView.ViewHolder {

        public TextView amount,validity,description,type;
        CardView cardView;


        public RechargeOfferViewHolder(View view) {
            super(view);
            amount = (TextView) view.findViewById(R.id.amount);
            validity = (TextView) view.findViewById(R.id.validity);
            description = (TextView) view.findViewById(R.id.description);
            type = (TextView) view.findViewById(R.id.type);
            cardView=view.findViewById(R.id.card_viewOffers);
        }
    }
}
