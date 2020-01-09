package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class OpeningHoursAdapter extends RecyclerView.Adapter<OpeningHoursAdapter.FlightLocationListViewHolder> {

    private Context context ;
    ArrayList<String> Hourslist,DayList;

    public OpeningHoursAdapter(Context context, ArrayList<String> DayList1, ArrayList<String> Hourslist1) {
        this.context = context;
        this.Hourslist = Hourslist1;
        this.DayList = DayList1;
    }

    @NonNull
    @Override
    public OpeningHoursAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.opening_hour_adapter,parent,false);
        return new OpeningHoursAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpeningHoursAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return Hourslist.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDayName,tvDayHours ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.dayName_hourAdapter);
            tvDayHours = itemView.findViewById(R.id.dayTime_hourAdapter);
            
        }

        public void bindData(int position) {
            tvDayName.setText(Hourslist.get(position));
            tvDayHours.setText(DayList.get(position));
        }
    }
}
