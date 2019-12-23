package com.safepayu.wallet.activity.booking.flight;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.FlightDetailAdapter;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Date;

import static com.safepayu.wallet.activity.booking.flight.FlightListActivity.MY_PREFS_NAME;

public class FlightDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String Source,Destination,JourneyDate,TripType,User,UserType,Adults,Infants,Children,FlightType,ReturnDate,TravelClass,TrvaellersCount,ClassType,FlightImage;
    private TextView tvFlightdetailsDateTravellersClass,tvFlightdetailsTo,tvFlightdetailsFrom,tvFlightHandBaggage,tvFlightCheckInBaggage,tvTotalFlightFare,
            tvTotalNavellersNumber,tvFlightFareRules,tvFlightdetailNameDialog,tvCanelTimeDateDialog;
    private Button backbtnDialog;
    private ImageView imageFlightDetailDilog;
    private LinearLayout lTrip2;
    private CharSequence s;
    private Date date;
    int totalTravellers;
    String json;
    Gson gson;
    AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mdata;
    private RecyclerView recyle_flight_detail,recycle_flight_return_detail;
    private FlightDetailAdapter flightDetailAdapter;
    private Button continueBtn,backbtnFlightList,backBtn;
    public  Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);

        findId();

         SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         gson = new Gson();
         json = prefs.getString("MyObject", "");
         mdata = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);

        recyle_flight_detail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        flightDetailAdapter = new FlightDetailAdapter(getApplicationContext(), mdata.getFlightSegments());
        recyle_flight_detail.setAdapter(flightDetailAdapter);
    }

    private void findId() {
        tvFlightdetailsDateTravellersClass = findViewById(R.id.tv_flightdetails_date_travellers_class);
        backBtn = findViewById(R.id.backbtn_flight_list);
        tvFlightdetailsTo = findViewById(R.id.tvflightdetails_to_where);
        tvFlightdetailsFrom = findViewById(R.id.tvflightdetails_from_where);
        continueBtn = findViewById(R.id.continue_btn);
        backbtnFlightList = findViewById(R.id.backbtn_flight_list);
        tvFlightFareRules = findViewById(R.id.tvflight_fare_rules);


        tvFlightHandBaggage = findViewById(R.id.tv_flight_hand_baggage);
        tvFlightCheckInBaggage = findViewById(R.id.tv_flight_checkin_baggage);
        tvTotalFlightFare = findViewById(R.id.total_flight_fare);
        tvTotalNavellersNumber = findViewById(R.id.tv_total_travellers_no);
        recyle_flight_detail = findViewById(R.id.recycle_flight_detail);
        recycle_flight_return_detail = findViewById(R.id.recycle_flight_return_detail);


        //************set listener*************
        continueBtn.setOnClickListener(this);
        backbtnFlightList.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        tvFlightFareRules.setOnClickListener(this);


        //***************get data****************
        Source = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
        TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
        //   User =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER);
        //    UserType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER_TYPE);
        Adults = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
        Infants = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
        Children = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);
        //  FlightType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TYPE);
        //  ReturnDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_DATE);
        FlightImage = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        TrvaellersCount = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        ClassType = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        ClassType = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);



        if (Infants.equals("") && Children.equals("")) {
            Infants = "0";
            Children = "0";
            totalTravellers = Integer.parseInt(Adults) + Integer.parseInt(Children) + Integer.parseInt(Infants);
        } else if (Infants.equals("")) {
            Infants = "0";
            totalTravellers = Integer.parseInt(Adults) + Integer.parseInt(Children) + Integer.parseInt(Infants);
        } else if (Children.equals("")) {
            Children = "0";
            totalTravellers = Integer.parseInt(Adults) + Integer.parseInt(Children) + Integer.parseInt(Infants);
        } else {
            totalTravellers = Integer.parseInt(Adults) + Integer.parseInt(Children) + Integer.parseInt(Infants);
        }


        //*************set data***********

        tvFlightdetailsDateTravellersClass.setText(JourneyDate + " | " + TrvaellersCount + " | " + ClassType);
        tvFlightdetailsFrom.setText(Source);
        tvFlightdetailsTo.setText(Destination);


        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_HAND_BAGGAGE).equals("")) {
            tvFlightHandBaggage.setText("Not allowed");
        } else {
            tvFlightHandBaggage.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_HAND_BAGGAGE));
        }

        tvFlightCheckInBaggage.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHECKIN_BAGGAGE));
        tvTotalNavellersNumber.setText("For " + totalTravellers + " Traveller");
        tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_flight_list:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.continue_btn:
                Intent intent = new Intent(getApplicationContext(),FlightPassengerDetailActivity.class);
                intent.putExtra("total_travellers",totalTravellers);
                startActivity(intent);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().TotalTravellers,String.valueOf(totalTravellers));
                break;
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

            case R.id.tvflight_fare_rules:

                showDialogFareRule(FlightDetailActivity.this);
                break;
        }
    }

    private void showDialogFareRule(FlightDetailActivity flightDetailActivity) {
        dialog = new Dialog(flightDetailActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.flight_fare_rule_dialog);
        String DateTime =mdata.getFlightSegments().get(0).getDepartureDateTime();
        String DT[] = DateTime.split("T");
        String D = DT[0];
        String T = DT[1];


        tvFlightdetailNameDialog = dialog.findViewById(R.id.tv_flightdetail_name);
        backbtnDialog = dialog.findViewById(R.id.backbtn_dialog);
        lTrip2 = dialog.findViewById(R.id.l_trip2);
        tvCanelTimeDateDialog = dialog.findViewById(R.id.tv_canel_time_Date);
        imageFlightDetailDilog = dialog.findViewById(R.id.image_flight_detail);
        if ( TripType.equals("1")){
            lTrip2.setVisibility(View.GONE);
        }
        tvFlightdetailNameDialog.setText(Source +" - "+ Destination);
        tvCanelTimeDateDialog.setText("This airline allows cancellation only before 2 hrs from departure time(Upto "+ D+", "+T+" )");
        Picasso.get().load("http://webapi.i2space.co.in/"+mdata.getFlightSegments().get(0).getImagePath()).into(imageFlightDetailDilog);
        backbtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();

    }
}
