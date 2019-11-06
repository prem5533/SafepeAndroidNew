package com.safepayu.wallet.fragment.bus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.safepayu.wallet.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardingDropFragment extends Fragment implements View.OnClickListener {

    private LinearLayout liBusBoarding,liBusDrop,linearBoarding,linearDrop;

    public BoardingDropFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boarding_drop, container, false);
        findId(view);
        return view;
    }

    private void findId(View view) {
        liBusBoarding = view.findViewById(R.id.li_bus_boarding);
        liBusDrop = view.findViewById(R.id.li_bus_drop);
        linearBoarding = view.findViewById(R.id.linear_boarding);
        linearDrop = view.findViewById(R.id.linear_drop);

        //set listener
        liBusBoarding.setOnClickListener(this);
        liBusDrop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_bus_boarding:
                liBusBoarding.setBackgroundColor(getResources().getColor(R.color.white));
                liBusDrop.setBackgroundColor(getResources().getColor(R.color.tab_bg));
                linearBoarding.setVisibility(View.VISIBLE);
                linearDrop.setVisibility(View.GONE);
                break;
            case R.id.li_bus_drop:
                liBusDrop.setBackgroundColor(getResources().getColor(R.color.white));
                liBusBoarding.setBackgroundColor(getResources().getColor(R.color.tab_bg));
                linearBoarding.setVisibility(View.GONE);
                linearDrop.setVisibility(View.VISIBLE);
                break;
        }
    }
}
