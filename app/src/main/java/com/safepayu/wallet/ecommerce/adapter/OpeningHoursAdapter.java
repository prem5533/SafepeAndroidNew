package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.List;

public class OpeningHoursAdapter extends RecyclerView.Adapter<OpeningHoursAdapter.FlightLocationListViewHolder> {

    private Context context ;
    List<String> ClosedHourList,OpenHourList,DayList;

    public OpeningHoursAdapter(Context context, List<String> OpenHourList1, List<String> ClosedHourList1,List<String> DayList1) {
        this.context = context;
        this.ClosedHourList = ClosedHourList1;
        this.OpenHourList = OpenHourList1;
        this.DayList=DayList1;
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
        return ClosedHourList.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDayName,tvDayHours ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.dayName_hourAdapter);
            tvDayHours = itemView.findViewById(R.id.dayTime_hourAdapter);
            
        }

        public void bindData(int position) {
            tvDayName.setText(DayList.get(position));
            tvDayHours.setText(OpenHourList.get(position)+" - "+ClosedHourList.get(position));
        }
    }
}
