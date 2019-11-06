package com.safepayu.wallet.activity.booking.flight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.FlightListAdapter;
import com.safepayu.wallet.adapter.fight.OneWayFlightListAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FlightListActivity extends AppCompatActivity implements View.OnClickListener {

    private FlightListAdapter flightListAdapter;
    private OneWayFlightListAdapter oneWayFlightListAdapter;
    private RecyclerView recyclerViewFlight,recyclerViewFlightList,recyclerViewFlightListMore;
    private Button backBtn;
    public static BottomNavigationView bottomNavigation;
    private LinearLayout linearOneWay ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        findId();

    }

    private void findId() {

        recyclerViewFlight = findViewById(R.id.flight_date_list);
        recyclerViewFlightList = findViewById(R.id.one_way_flight_list);
        recyclerViewFlightListMore = findViewById(R.id.one_way_flight_list_more);

        linearOneWay = findViewById(R.id.linear_one_way);

        backBtn = findViewById(R.id.backbtn_flight_list);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);


            backBtn.setOnClickListener(this);







        Intent intent = getIntent();
        String one = intent.getStringExtra("oneway");
        String two = intent.getStringExtra("twoway");
     //   if (one.equals("1")){
         /*   linearOneWay.setVisibility(View.VISIBLE);
            linearTwoWay.setVisibility(View.GONE);*/

            recyclerViewFlight.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            flightListAdapter = new FlightListAdapter(getApplicationContext());
            recyclerViewFlight.setAdapter(flightListAdapter);

            recyclerViewFlightList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            oneWayFlightListAdapter = new OneWayFlightListAdapter(getApplicationContext());
            recyclerViewFlightList.setAdapter(oneWayFlightListAdapter);

            recyclerViewFlightListMore.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            oneWayFlightListAdapter = new OneWayFlightListAdapter(getApplicationContext());
            recyclerViewFlightListMore.setAdapter(oneWayFlightListAdapter);


     /*
           /* linearOneWay.setVisibility(View.GONE);
            linearTwoWay.setVisibility(View.VISIBLE);*/



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
