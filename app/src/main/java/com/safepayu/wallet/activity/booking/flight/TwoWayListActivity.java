package com.safepayu.wallet.activity.booking.flight;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.TwoWayFlightListAdapter;
import com.safepayu.wallet.adapter.fight.TwoWayFlightTopAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TwoWayListActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearTwoWay;
    private RecyclerView recyclerViewPriceList,recyclerViewFlightListgGo,recyclerViewFlightListReturn;
    private TwoWayFlightTopAdapter twoWayFlightTopAdapter;
    private TwoWayFlightListAdapter twoWayFlightListAdapter;
    private Button backBtn;
    public static BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_way_list);
        findId();
    }

    private void findId() {

        linearTwoWay = findViewById(R.id.linear_two_way);
        recyclerViewPriceList = findViewById(R.id.flight_price_list);
        recyclerViewFlightListgGo = findViewById(R.id.flight_list1);
        recyclerViewFlightListReturn = findViewById(R.id.flight_list2);

        backBtn = findViewById(R.id.backbtn_flight_list);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        backBtn.setOnClickListener(this);


        recyclerViewPriceList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        twoWayFlightTopAdapter = new TwoWayFlightTopAdapter(getApplicationContext());
        recyclerViewPriceList.setAdapter(twoWayFlightTopAdapter);

        recyclerViewFlightListgGo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        twoWayFlightListAdapter = new TwoWayFlightListAdapter(getApplicationContext());
        recyclerViewFlightListgGo.setAdapter(twoWayFlightListAdapter);

        recyclerViewFlightListReturn.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        twoWayFlightListAdapter = new TwoWayFlightListAdapter(getApplicationContext());
        recyclerViewFlightListReturn.setAdapter(twoWayFlightListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_flight_list:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
        }
    }
}
