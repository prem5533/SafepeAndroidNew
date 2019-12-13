package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.bus.BusListResponse;

import java.util.List;

public class DroppingPointAdapter  extends RecyclerView.Adapter<DroppingPointAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private List<BusListResponse.DataBean.AvailableTripsBean.DroppingTimesBean> DroppingTimes;
    private DroppingPointAdapter.DroppingListListener boardingListListener;
    private int lastSelectedPosition=0;

    public  interface  DroppingListListener {
        void onDroppingClickTo (int position,List<BusListResponse.DataBean.AvailableTripsBean.DroppingTimesBean> DroppingTimes);
    }

    public DroppingPointAdapter(Context context, List<BusListResponse.DataBean.AvailableTripsBean.DroppingTimesBean> DroppingTimes1, DroppingPointAdapter.DroppingListListener boardingListListener) {
        this.context = context;
        this.DroppingTimes=DroppingTimes1;
        this.boardingListListener=boardingListListener;
    }

    @NonNull
    @Override
    public DroppingPointAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.boarding_point_adapter,parent,false);
        return new DroppingPointAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DroppingPointAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return DroppingTimes.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTime,tvPlace ;
        private RadioButton radioButton;
        public FlightLocationListViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.time_boardingDroppingAdapter);
            tvPlace = itemView.findViewById(R.id.place_boardingDroppingAdapter);
            radioButton = itemView.findViewById(R.id.radioBtn_boardingDroppingAdapter);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    Toast.makeText(context,"selected offer is " + tvTime.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            tvPlace.setText(DroppingTimes.get(position).getLocation());
            tvTime.setText(DroppingTimes.get(position).getTime());

            try {
                radioButton.setChecked(lastSelectedPosition == position);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {

            if (boardingListListener != null) {
                boardingListListener.onDroppingClickTo(getLayoutPosition(),DroppingTimes);

            }

        }
    }
}
