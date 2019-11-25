package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safepayu.wallet.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FlighPassengerBookingDialog  extends RecyclerView.Adapter<FlighPassengerBookingDialog.BookingViewHolder> {
    private Context context;

    public FlighPassengerBookingDialog(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.flight_booking_dialog_adapter,parent,false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
