package com.safepayu.wallet.fragment.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.MyOrdersActivity;
import com.safepayu.wallet.activity.booking.flight.FlightHistoryActivity;

public class FlightHistory  extends Fragment implements View.OnClickListener {

    LinearLayout layoutFlightHistory;
    public FlightHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.flight_history_fragment, container, false);

        layoutFlightHistory=rootView.findViewById(R.id.layout_flight_history);
        layoutFlightHistory.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_flight_history:
                Intent intent = new Intent( getActivity(), FlightHistoryActivity.class);
                startActivity(intent);
                break;

        }
    }
}
