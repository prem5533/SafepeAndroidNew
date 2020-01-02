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
    private String DropId="",DropName="";

    public  interface  DroppingListListener {
        void onDroppingClickTo (int position,List<BusListResponse.DataBean.AvailableTripsBean.DroppingTimesBean> DroppingTimes,
                                String DropId,String DropName);
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



            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvPlace.setText(DroppingTimes.get(position).getLocation());


            try {
                int bTime= Integer.parseInt(DroppingTimes.get(position).getTime().trim());
                int divisor=bTime/60;
                int remainder=bTime%60;
                int journeyDayNo=divisor/24;
                int hour=0,min=0;

                if (divisor>23){
                    hour=divisor%24;
                }else {
                    hour=divisor;
                }
                min=remainder;
                if (String.valueOf(min).length()==1){
                    tvTime.setText(hour+":"+min+"0");
                }else {
                    tvTime.setText(hour+":"+min);
                }



            }catch (Exception e){
                tvTime.setText(DroppingTimes.get(position).getTime());
                e.printStackTrace();
            }

            try {
                radioButton.setChecked(lastSelectedPosition == position);
                if (boardingListListener != null) {
                    boardingListListener.onDroppingClickTo(getLayoutPosition(),DroppingTimes,DroppingTimes.get(getLayoutPosition()).getPointId(),
                            DroppingTimes.get(getLayoutPosition()).getLocation());

                }
            }catch (Exception e){
                e.printStackTrace();
            }

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = position;
                    notifyDataSetChanged();

                    Toast.makeText(context,"selected offer is " + tvTime.getText().toString(), Toast.LENGTH_SHORT).show();

                    DropId=DroppingTimes.get(getLayoutPosition()).getPointId();
                    DropName=DroppingTimes.get(getLayoutPosition()).getLocation();
                    if (boardingListListener != null) {
                        boardingListListener.onDroppingClickTo(getLayoutPosition(),DroppingTimes,DropId,DropName);

                    }

                }
            });
        }

        @Override
        public void onClick(View v) {


        }
    }
}
