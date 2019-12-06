package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.flight.FlightBlockTicketResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlighPassengerBookingDialog  extends RecyclerView.Adapter<FlighPassengerBookingDialog.BookingViewHolder> {
    private Context context;
    private List<FlightBlockTicketResponse.DataBean.TicketsBean >flightBlockTicketResponse;

    public FlighPassengerBookingDialog(Context context, List<FlightBlockTicketResponse.DataBean.TicketsBean> flightBlockTicketResponse) {
        this.context = context;
        this.flightBlockTicketResponse = flightBlockTicketResponse;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.flight_booking_dialog_adapter,parent,false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return flightBlockTicketResponse.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlightPassengeFullName,tvPassengerTitle;
        private ImageView image;
        String gender ,firstName;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightPassengeFullName = itemView.findViewById(R.id.tv_flight_passenge_full_name);
            image = itemView.findViewById(R.id.flight_passenger_image);
            tvPassengerTitle = itemView.findViewById(R.id.tv_passenger_type);
        }

        public void bindData(int position) {
            tvFlightPassengeFullName.setText(flightBlockTicketResponse.get(position).getNameReference());
            tvPassengerTitle.setText(flightBlockTicketResponse.get(position).getTitle());

            String name = flightBlockTicketResponse.get(position).getNameReference();

            String[] separated = name.split(" ");
            gender = separated[0];
            firstName = separated[1];
            if (gender.equals("Mr.")){
                image.setImageResource(R.drawable.ic_flight_man);
                tvPassengerTitle.setText("Adult");
            }
            else if (gender.equals("Ms.")){
                image.setImageResource(R.drawable.ic_flight_women);
                tvPassengerTitle.setText("Adult");

            }else if (gender.equals("Mrs.")){
                image.setImageResource(R.drawable.ic_flight_mrs);
                tvPassengerTitle.setText("Adult");
            }

            else if (flightBlockTicketResponse.get(position).getTitle().equals("chd")){
                image.setImageResource(R.drawable.ic_flight_child_boy);
                tvPassengerTitle.setText("Child");
            }
            else if (flightBlockTicketResponse.get(position).getTitle().equals("inft")){
                image.setImageResource(R.drawable.ic_flight_infant);
                tvPassengerTitle.setText("Infant");
            }
        }
    }
}
