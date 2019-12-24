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

public class BoardingPointAdapter extends RecyclerView.Adapter<BoardingPointAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private List<BusListResponse.DataBean.AvailableTripsBean.BoardingTimesBean> BoardingTimes;
    private BoardingPointAdapter.BoardingListListener boardingListListener;
    private int lastSelectedPosition=0;
    String BoardId="",BoardName="";

    public  interface  BoardingListListener {
        void onLocationClickTo (int position,List<BusListResponse.DataBean.AvailableTripsBean.BoardingTimesBean> BoardingTimes,
                                String BoardId,String BoardName);
    }

    public BoardingPointAdapter(Context context,  List<BusListResponse.DataBean.AvailableTripsBean.BoardingTimesBean> BoardingTimes1, BoardingPointAdapter.BoardingListListener boardingListListener) {
        this.context = context;
        this.BoardingTimes=BoardingTimes1;
        this.boardingListListener=boardingListListener;
    }

    @NonNull
    @Override
    public BoardingPointAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.boarding_point_adapter,parent,false);
        return new BoardingPointAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardingPointAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return BoardingTimes.size();
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
            tvPlace.setText(BoardingTimes.get(position).getLocation());
            tvTime.setText(BoardingTimes.get(position).getTime());

            try {
                radioButton.setChecked(lastSelectedPosition == position);
                if (boardingListListener != null) {
                    boardingListListener.onLocationClickTo(getLayoutPosition(),BoardingTimes,BoardingTimes.get(getLayoutPosition()).getPointId(),
                            BoardingTimes.get(getLayoutPosition()).getLocation());
                    Toast.makeText(context, tvTime.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }catch (Exception e){
                e.printStackTrace();
            }

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = position;
                    notifyDataSetChanged();

                    BoardId=BoardingTimes.get(getLayoutPosition()).getPointId();
                    BoardName=BoardingTimes.get(getLayoutPosition()).getLocation();
                    if (boardingListListener != null) {
                        boardingListListener.onLocationClickTo(getLayoutPosition(),BoardingTimes,BoardId,BoardName);
                        Toast.makeText(context, tvTime.getText().toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

        @Override
        public void onClick(View v) {



        }
    }
}