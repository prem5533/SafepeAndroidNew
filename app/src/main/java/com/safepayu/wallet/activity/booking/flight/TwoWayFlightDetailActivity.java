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
import com.safepayu.wallet.adapter.fight.FlightReturnDetailAdapter;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Date;

import static com.safepayu.wallet.activity.booking.flight.FlightListActivity.MY_PREFS_NAME;

public class TwoWayFlightDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String Source,Destination,JourneyDate,TripType,User,UserType,Adults,Infants,Children,FlightType,ReturnDate,TravelClass,TrvaellersCount,ClassType;
    private TextView tvFlightdetailsDateTravellersClass,tvFlightdetailsTo,tvFlightdetailsFrom,tvFlightHandBaggage,tvFlightCheckInBaggage,tvTotalFlightFare,
            tvTotalNavellersNumber,tvFlightFareRules, tvOnwardName,tv_return_name,tvFlightHandBaggageReturn,tvFlightCheckinBaggageReturn,tvFlightdetailNameDialog,
            tvFlightdetailNameDialog2,tvCanelTimeDateDialog,tvCanelTimeDateDialog2;
    private ImageView onwardImage,return_image,imageFlightDetailDilog,imageFlightDetailDilog2;
    private Button backbtnDialog;

    private CharSequence s;
    private Date date;
    int totalTravellers,onwardsAmount,ReturnAmout ,TotalAmount;
    private LinearLayout lreturn;
    String json;
    Gson gson;
    AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mdataOnwards;
    AvailableFlightResponse.DataBean.DomesticReturnFlightsBean mdataReturn;
    private RecyclerView recyle_flight_detail,recycle_flight_return_detail;
    private FlightDetailAdapter flightDetailAdapter;
    private FlightReturnDetailAdapter flightReturnDetailAdapter;
    private Button continueBtn,backbtnFlightList,backBtn;
    public  Dialog dialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_way_flight_detail);

        findId();

         SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         gson = new Gson();
         json = prefs.getString("MyObjectOnward", "");
        mdataOnwards = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);

        json = prefs.getString("MyObjectReturn", "");
        mdataReturn = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticReturnFlightsBean.class);

        recyle_flight_detail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        flightDetailAdapter = new FlightDetailAdapter(getApplicationContext(), mdataOnwards.getFlightSegments());
        recyle_flight_detail.setAdapter(flightDetailAdapter);

        recycle_flight_return_detail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        flightReturnDetailAdapter = new FlightReturnDetailAdapter(getApplicationContext(), mdataReturn.getFlightSegments());
        recycle_flight_return_detail.setAdapter(flightReturnDetailAdapter);

        tvFlightCheckinBaggageReturn.setText(mdataReturn.getFlightSegments().get(0).getBaggageAllowed().getCheckInBaggage());
           if (mdataOnwards.getFlightSegments().get(0).getBaggageAllowed().getHandBaggage().equals("")){
            tvFlightHandBaggage.setText("Not allowed");
        } else {
            tvFlightHandBaggage.setText(mdataOnwards.getFlightSegments().get(0).getBaggageAllowed().getHandBaggage());
        }
        if (mdataReturn.getFlightSegments().get(0).getBaggageAllowed().getHandBaggage().equals("")){
          tvFlightHandBaggageReturn.setText("Not allowed");
        } else {
          tvFlightHandBaggageReturn.setText(mdataReturn.getFlightSegments().get(0).getBaggageAllowed().getHandBaggage());
        }
        Picasso.get().load("http://webapi.i2space.co.in/"+mdataOnwards.getFlightSegments().get(0).getImagePath()).into(onwardImage);
        Picasso.get().load("http://webapi.i2space.co.in/"+mdataReturn.getFlightSegments().get(0).getImagePath()).into(return_image);
        onwardsAmount = mdataOnwards.getFareDetails().getTotalFare();
        ReturnAmout = mdataReturn.getFareDetails().getTotalFare();
        TotalAmount =onwardsAmount+ReturnAmout;
        tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(TotalAmount));
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().TOTALFARE_RETURN_ONWARDS,String.valueOf(TotalAmount));
    }


    private void findId() {
        tvFlightdetailsDateTravellersClass = findViewById(R.id.tv_flightdetails_date_travellers_class);
        backBtn = findViewById(R.id.backbtn_flight_list);
        tvFlightdetailsTo = findViewById(R.id.tvflightdetails_to_where);
        tvFlightdetailsFrom = findViewById(R.id.tvflightdetails_from_where);
        continueBtn = findViewById(R.id.continue_btn);
        backbtnFlightList = findViewById(R.id.backbtn_flight_list);
        tvFlightFareRules = findViewById(R.id.tvflight_fare_rules);
        lreturn = findViewById(R.id.lreturn);


        tvFlightHandBaggage = findViewById(R.id.tv_flight_hand_baggage);
        tvFlightCheckInBaggage = findViewById(R.id.tv_flight_checkin_baggage);
        tvTotalFlightFare = findViewById(R.id.total_flight_fare);
        tvTotalNavellersNumber = findViewById(R.id.tv_total_travellers_no);
        recyle_flight_detail = findViewById(R.id.recycle_flight_detail);
        recycle_flight_return_detail = findViewById(R.id.recycle_flight_return_detail);
        tvOnwardName = findViewById(R.id.tv_onward_name);
        tv_return_name = findViewById(R.id.tv_return_name);
        tvFlightHandBaggageReturn = findViewById(R.id.tv_flight_hand_baggage_return);
        tvFlightCheckinBaggageReturn = findViewById(R.id.tv_flight_checkin_baggage_return);
        onwardImage = findViewById(R.id.onward_image);
        return_image = findViewById(R.id.return_image);


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
        TravelClass = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        TrvaellersCount = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        ClassType = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        ClassType = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);

        if (TripType.equals("1")){
            recycle_flight_return_detail.setVisibility(View.GONE);
            lreturn.setVisibility(View.GONE);

        }else if (TripType.equals("2")){
            recycle_flight_return_detail.setVisibility(View.VISIBLE);
            lreturn.setVisibility(View.VISIBLE);
        }

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
        tvOnwardName.setText(Source +"-"+ Destination);
        tv_return_name.setText(Destination +"-"+ Source);


        //*************set data***********

        tvFlightdetailsDateTravellersClass.setText(JourneyDate + " | " + TrvaellersCount + " | " + ClassType);
        tvFlightdetailsFrom.setText(Source);
        tvFlightdetailsTo.setText(Destination);




        tvFlightCheckInBaggage.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHECKIN_BAGGAGE));
        tvTotalNavellersNumber.setText("For " + totalTravellers + " Traveller");
      //  tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));
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

                showDialogFareRule(TwoWayFlightDetailActivity.this);
                break;
        }
    }

    private void showDialogFareRule(TwoWayFlightDetailActivity twoWayFlightDetailActivity) {

        dialog = new Dialog(twoWayFlightDetailActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.flight_fare_rule_dialog);
        String DateTime =mdataOnwards.getFlightSegments().get(0).getDepartureDateTime();
        String DT[] = DateTime.split("T");
        String D = DT[0];
        String T = DT[1];


        backbtnDialog = dialog.findViewById(R.id.backbtn_dialog);
        tvFlightdetailNameDialog = dialog.findViewById(R.id.tv_flightdetail_name);
        tvCanelTimeDateDialog = dialog.findViewById(R.id.tv_canel_time_Date);
        imageFlightDetailDilog = dialog.findViewById(R.id.image_flight_detail);
        tvFlightdetailNameDialog.setText(Source +" - "+ Destination);
        tvCanelTimeDateDialog.setText("This airline allows cancellation only before 2 hrs from departure time(Upto "+ D+", "+T+" )");
        Picasso.get().load("http://webapi.i2space.co.in/"+mdataOnwards.getFlightSegments().get(0).getImagePath()).into(imageFlightDetailDilog);

        String DateTime2 =mdataReturn.getFlightSegments().get(0).getDepartureDateTime();
        String DT2[] = DateTime2.split("T");
        String D2 = DT2[0];
        String T2 = DT2[1];
        tvFlightdetailNameDialog2 = dialog.findViewById(R.id.tv_flightdetail_name2);
        tvCanelTimeDateDialog2 = dialog.findViewById(R.id.tv_canel_time_Date2);
        imageFlightDetailDilog2 = dialog.findViewById(R.id.image_flight_detail2);
        tvFlightdetailNameDialog2.setText( Destination+" - "+ Source);
        tvCanelTimeDateDialog2.setText("This airline allows cancellation only before 2 hrs from departure time(Upto "+ D2+", "+T2+" )");
        Picasso.get().load("http://webapi.i2space.co.in/"+mdataReturn.getFlightSegments().get(0).getImagePath()).into(imageFlightDetailDilog2);

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
