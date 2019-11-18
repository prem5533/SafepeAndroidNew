package com.safepayu.wallet.activity.booking.flight;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.squareup.picasso.Picasso;

public class FlightDetailActivity extends AppCompatActivity {

    private String Source,Destination,JourneyDate,TripType,User,UserType,Adults,Infants,Children,FlightType,ReturnDate,TravelClass,TrvaellersCount,ClassType;
    private TextView tvFlightdetailsDateTravellersClass,tvFlightdetailsTo,tvFlightdetailsFrom,tvFlightdetailName,tvFlightDetailDepTime,tvFlightDetailArrivalTime
            ,tvFlightDetailSourceName,tvFlightDetailArrivalDate,tvFlightDetailSourceAirpot,tvFlightDetailDestiName,tvFlightDetailDestiDate,tvFlightDetailDestiAirport,tvFlightDetailDuration;
    private ImageView imageFlightDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);

        findId();


    }

    private void findId() {
        tvFlightdetailsDateTravellersClass = findViewById(R.id.tv_flightdetails_date_travellers_class);
        tvFlightdetailsTo = findViewById(R.id.tvflightdetails_to_where);
        tvFlightdetailsFrom = findViewById(R.id.tvflightdetails_from_where);
        tvFlightdetailName = findViewById(R.id.tv_flightdetail_name);
        tvFlightDetailDepTime = findViewById(R.id.tv_flight_detail_dep_time);
        tvFlightDetailArrivalTime = findViewById(R.id.tv_flight_detail_arrival_time);
        tvFlightDetailSourceName = findViewById(R.id.tv_flight_detail_source_name);
        tvFlightDetailArrivalDate = findViewById(R.id.tv_flight_detail_arrival_date);
        tvFlightDetailSourceAirpot = findViewById(R.id.tv_flight_detail_source_airpot);
        tvFlightDetailDestiName = findViewById(R.id.tv_flight_detail_desti_name);
        tvFlightDetailDestiDate = findViewById(R.id.tv_flight_detail_desti_date);
        tvFlightDetailDestiAirport = findViewById(R.id.tv_flight_detail_destin_airport);
        tvFlightDetailDuration = findViewById(R.id.tv_flight_detail_duration);
        imageFlightDetail = findViewById(R.id.image_flight_detail);


        //***************get data****************
        Source =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
      //  TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
     //   User =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER);
    //    UserType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER_TYPE);
      //  Adults =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
      //  Infants =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
      //  Children =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);
      //  FlightType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TYPE);
      //  ReturnDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_DATE);
        TravelClass =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        TrvaellersCount =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);

        //*************set data***********

        tvFlightdetailsDateTravellersClass.setText(JourneyDate+" | "+TrvaellersCount+" | "+ClassType);
        tvFlightdetailsFrom.setText(Source);
        tvFlightdetailsTo.setText(Destination);
        tvFlightDetailSourceName.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE_FULL_NAME));
        tvFlightDetailDestiName.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION_FULL_NAME));
        Picasso.get().load(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE)).into(imageFlightDetail);
        tvFlightdetailName.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_NAME)+ "  "+
                BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_CODE)+"-"+
                BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_FLIGHT_NUMBER));
        tvFlightDetailSourceAirpot.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE_FULL_NAME)+" Airport");
        tvFlightDetailDestiAirport.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION_FULL_NAME)+" Airport");
        tvFlightDetailDuration.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION));

    }
}
