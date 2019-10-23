package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.fragment.BottomSheetFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

public class FlightsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvOneWay, tvTwoWay;
    private ImageView imageOneWay, imageTwoWay;
    private Button searchFlightBtn,backBtn;
    private LinearLayout liayoutClassAdult;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        findId();
    }

    private void findId() {
        backBtn = findViewById(R.id.recharge_back_btn);
        searchFlightBtn = findViewById( R.id.search_flight_btn);
        tvOneWay = findViewById(R.id.tv_oneway);
        tvTwoWay = findViewById(R.id.tv_twoway);
        imageOneWay = findViewById(R.id.image_one_way);
        imageTwoWay = findViewById(R.id.image_two_way);
        liayoutClassAdult = findViewById(R.id.layout_class_traveller_tab);

        //set listener
        tvOneWay.setOnClickListener(this);
        tvTwoWay.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        searchFlightBtn.setOnClickListener(this);
        liayoutClassAdult.setOnClickListener(this);

        tvOneWay.setBackgroundDrawable(getDrawable(R.drawable.green_rounded));
        tvOneWay.setTextColor(getResources().getColor(R.color.white));
        imageOneWay.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case
                R.id.tv_oneway:
                tvOneWay.setBackgroundDrawable(getDrawable(R.drawable.green_rounded));
                tvOneWay.setTextColor(getResources().getColor(R.color.white));
                tvTwoWay.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvTwoWay.setTextColor(getResources().getColor(R.color.darker_gray));
                imageOneWay.setVisibility(View.VISIBLE);
                imageTwoWay.setVisibility(View.GONE);
                break;
            case  R.id.tv_twoway:
                tvTwoWay.setBackgroundDrawable(getDrawable(R.drawable.green_rounded));
                tvTwoWay.setTextColor(getResources().getColor(R.color.white));
                tvOneWay.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvOneWay.setTextColor(getResources().getColor(R.color.darker_gray));
                imageOneWay.setVisibility(View.GONE);
                imageTwoWay.setVisibility(View.VISIBLE);
                break;
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

            case R. id.search_flight_btn:

                CheckValidate();
                break;
            case R.id.layout_class_traveller_tab:

                    BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                    bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                break;
        }
    }

    private void CheckValidate() {
        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_flight),"Coming Soon",false);

    }
}
