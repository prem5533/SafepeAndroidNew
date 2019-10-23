package com.safepayu.wallet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.safepayu.wallet.R;


public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private ImageView imageAdultsMinus,imageAdultsPlus,imageAdultsMinusgray;
    private TextView tvAdultsCount;
    int minteger = 0;
    public BottomSheetFragment() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.flight_bottom_popup, container, false);
        setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);

        findId(view);
        return view;
    }

    private void findId(View view) {

        tvAdultsCount = view.findViewById(R.id.tvAdultsCount);
        imageAdultsMinus = view.findViewById(R.id.imageAdultsMinus);
        imageAdultsMinusgray = view.findViewById(R.id.imageAdultsMinusgray);
        imageAdultsPlus = view.findViewById(R.id.imageAdultsPlus);

        imageAdultsMinus.setOnClickListener(this);
        imageAdultsPlus.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageAdultsMinus:

                if (minteger==0)
                {
                    tvAdultsCount.setText("" + 0);
                    imageAdultsMinusgray.setVisibility(View.VISIBLE);
                    imageAdultsMinus.setVisibility(View.INVISIBLE);

                }
                else {
                    minteger = minteger - 1;
                    display(minteger);
                    imageAdultsMinusgray.setVisibility(View.INVISIBLE);
                    imageAdultsMinus.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.imageAdultsPlus:

                minteger = minteger + 1;
                display(minteger);
                imageAdultsMinusgray.setVisibility(View.INVISIBLE);
                imageAdultsMinus.setVisibility(View.VISIBLE);
                break;

        }
    }


    private void display(int minteger) {

        tvAdultsCount.setText("" + minteger);

        if (minteger==0)
        {
            imageAdultsMinusgray.setVisibility(View.VISIBLE);
            imageAdultsMinus.setVisibility(View.INVISIBLE);

        }
    }




}
