package com.safepayu.wallet.activity.booking.bus;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.safepayu.wallet.R;

import androidx.appcompat.app.AppCompatActivity;

public class BusLocationActivity extends AppCompatActivity implements View.OnClickListener {


     private LinearLayout liBusFromWhere,liBusToWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_location);

        findId();
    }

    private void findId() {
        liBusFromWhere = findViewById(R.id.li_bus_from_where);
        liBusToWhere = findViewById(R.id.li_bus_to_where);

        liBusFromWhere.setOnClickListener(this);
        liBusToWhere.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_bus_from_where:
                liBusFromWhere.setBackgroundColor(Color.parseColor("#f5f7fc"));
                liBusToWhere.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case R.id.li_bus_to_where:
                liBusFromWhere.setBackgroundColor(Color.parseColor("#ffffff"));
                liBusToWhere.setBackgroundColor(Color.parseColor("#f5f7fc"));
                break;
        }
    }
}
