package com.safepayu.wallet.activity.booking.flight;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class FlightDetailActivity extends AppCompatActivity {

    private String Source,Destination,JourneyDate,TripType,User,UserType,Adults,Infants,Children,FlightType,ReturnDate,TravelClass,TrvaellersCount,ClassType;
    private TextView tvFlightdetailsDateTravellersClass,tvFlightdetailsTo,tvFlightdetailsFrom,tvFlightdetailName,tvFlightDetailDepTime,tvFlightDetailArrivalTime
            ,tvFlightDetailSourceName,tvFlightDetailDepDate,tvFlightDetailSourceAirpot,tvFlightDetailDestiName,tvFlightDetailDestiDate,tvFlightDetailDestiAirport,tvFlightDetailDuration
            ,tvFlightHandBaggage,tvFlightCheckInBaggage,tvTotalFlightFare,tvTotalNavellersNumber;
    private ImageView imageFlightDetail;
    private CharSequence s;
    private Date date;
    int totalTravellers;

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
        tvFlightDetailDepDate = findViewById(R.id.tv_flight_detail_dep_date);
        tvFlightDetailSourceAirpot = findViewById(R.id.tv_flight_detail_source_airpot);
        tvFlightDetailDestiName = findViewById(R.id.tv_flight_detail_desti_name);
        tvFlightDetailDestiDate = findViewById(R.id.tv_flight_detail_desti_date);
        tvFlightDetailDestiAirport = findViewById(R.id.tv_flight_detail_destin_airport);
        tvFlightDetailDuration = findViewById(R.id.tv_flight_detail_duration);
        imageFlightDetail = findViewById(R.id.image_flight_detail);
        tvFlightHandBaggage = findViewById(R.id.tv_flight_hand_baggage);
        tvFlightCheckInBaggage = findViewById(R.id.tv_flight_checkin_baggage);
        tvTotalFlightFare = findViewById(R.id.total_flight_fare);
        tvTotalNavellersNumber = findViewById(R.id.tv_total_travellers_no);


        //***************get data****************
        Source =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
      //  TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
     //   User =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER);
    //    UserType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER_TYPE);
        Adults =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
        Infants =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
        Children =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);
      //  FlightType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TYPE);
      //  ReturnDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_DATE);
        TravelClass =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        TrvaellersCount =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);


            if (Infants.equals("")&&Children.equals("")){
                Infants = "0";
                Children = "0";
                totalTravellers = Integer.parseInt(Adults)+Integer.parseInt(Children)+Integer.parseInt(Infants);
            }
           else if (Infants.equals("")){
                Infants = "0";
                totalTravellers = Integer.parseInt(Adults)+Integer.parseInt(Children)+Integer.parseInt(Infants);
            }
            else if (Children.equals("")){
                Children = "0";
                totalTravellers = Integer.parseInt(Adults)+Integer.parseInt(Children)+Integer.parseInt(Infants);
            }

           else{
                totalTravellers = Integer.parseInt(Adults)+Integer.parseInt(Children)+Integer.parseInt(Infants);
            }


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

        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_HAND_BAGGAGE).equals(""))
        {
            tvFlightHandBaggage.setText("Not allowed");
        }
        else {
            tvFlightHandBaggage.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_HAND_BAGGAGE));
        }

        tvFlightCheckInBaggage.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHECKIN_BAGGAGE));
        tvTotalNavellersNumber.setText("For "+ totalTravellers +" Traveller");
        tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));

        String Date, Date1, Time, h, m;
        String depTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME);
        String[] separated = depTime.split(" ");
        Date = separated[0];
        Time = separated[1];
        String[] separatedTime = Time.split(":");
        h = separatedTime[0];
        m = separatedTime[1];
        tvFlightDetailDepTime.setText(h + ":" + m);

        date = new Date(Date);
        s  = DateFormat.format("dd-MMM-yyyy ", date.getTime());
        String daymonthYear = (String) s;
        String[] sep = daymonthYear.split("-");
        String d = sep[0];
        String mo = sep[1];
        String y = sep[2];

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
        String dayOfWeek = simpledateformat.format(date);
        tvFlightDetailDepDate.setText(dayOfWeek+", "+d+mo+" "+y);


        String arrTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME);
        String[] arrTimeseparated = arrTime.split(" ");
        Date1 = arrTimeseparated[0];
        Time = arrTimeseparated[1];
        String[] separatedArrTime = Time.split(":");
        h = separatedArrTime[0];
        m = separatedArrTime[1];
        tvFlightDetailArrivalTime.setText(h + ":" + m);

        date = new Date(Date1);
        s  = DateFormat.format("dd-MMM-yyyy ", date.getTime());
        String daymonthYear1 = (String) s;
        String[] sep1 = daymonthYear1.split("-");
        String da = sep1[0];
        String mon = sep1[1];
        String ye = sep1[2];

        SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
        String day_Week = sdateformat.format(date);
        tvFlightDetailDestiDate.setText(day_Week+", "+da+mon+" "+ye);

    }
}
