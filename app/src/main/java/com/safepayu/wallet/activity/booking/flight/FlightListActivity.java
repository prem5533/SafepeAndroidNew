package com.safepayu.wallet.activity.booking.flight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.FlightListAdapter;
import com.safepayu.wallet.adapter.fight.OneWayFlightListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking.flight.AvailableFlightRequest;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class FlightListActivity extends AppCompatActivity implements View.OnClickListener, OneWayFlightListAdapter.OnFlightItemListener {

    private FlightListAdapter flightListAdapter;
    private OneWayFlightListAdapter oneWayFlightListAdapter;
    private RecyclerView recyclerViewFlight,recyclerViewFlightList;
    private Button backBtn;
    public static BottomNavigationView bottomNavigation;
    private LinearLayout linearOneWay ;
    private LoadingDialog loadingDialog;
    private AvailableFlightRequest availableFlightRequest;
    private TextView tvFlightDateTravellersClass,tvFlightFromWhere,tvFlightToWhere;
    private String Source,Destination,JourneyDate,TripType,User,UserType,Adults,Infants,Children,FlightType,ReturnDate,TravelClass,TrvaellersCount,ClassType;
    AvailableFlightResponse FlightResponse;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        findId();
    }



    private void findId() {
        loadingDialog = new LoadingDialog(this);
        recyclerViewFlight = findViewById(R.id.flight_date_list);
        recyclerViewFlightList = findViewById(R.id.one_way_flight_list);
        tvFlightDateTravellersClass = findViewById(R.id.tv_flight_date_travellers_class);
        tvFlightFromWhere = findViewById(R.id.tvflight_from_where);
        tvFlightToWhere = findViewById(R.id.tvflight_to_where);


        linearOneWay = findViewById(R.id.linear_one_way);
        backBtn = findViewById(R.id.backbtn_flight_list);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        backBtn.setOnClickListener(this);

            recyclerViewFlight.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            flightListAdapter = new FlightListAdapter(getApplicationContext());
            recyclerViewFlight.setAdapter(flightListAdapter);


        Source =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
        TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
        User =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER);
        UserType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER_TYPE);
        Adults =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
        Infants =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
        Children =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);
        FlightType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TYPE);
        ReturnDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_DATE);
        TravelClass =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS);
        TrvaellersCount =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);

        availableFlightRequest=new AvailableFlightRequest();
        availableFlightRequest.setSource(Source);
        availableFlightRequest.setDestination(Destination);
        availableFlightRequest.setJourneyDate(JourneyDate);
        availableFlightRequest.setTripType(TripType);
        availableFlightRequest.setUser(User);
        availableFlightRequest.setUserType(UserType);
        availableFlightRequest.setAdults(Adults);
        availableFlightRequest.setInfants(Infants);
        availableFlightRequest.setChildren(Children);
        availableFlightRequest.setFlightType(FlightType);
        availableFlightRequest.setReturnDate(ReturnDate);
        availableFlightRequest.setTravelClass(TravelClass);
        //availableFlightRequest.setCount("10");

        getFlightAvailable(availableFlightRequest);

        tvFlightDateTravellersClass.setText(JourneyDate+" | "+TrvaellersCount+" | "+ClassType);
        tvFlightFromWhere.setText(Source);
        tvFlightToWhere.setText(Destination);
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

    private void getFlightAvailable(final AvailableFlightRequest availableFlightRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getAvailableFlight(availableFlightRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AvailableFlightResponse>(){

                    @Override
                    public void onSuccess(AvailableFlightResponse availableFlightResponse) {
                        loadingDialog.hideDialog();
                        if (availableFlightResponse.isStatus()) {
                            FlightResponse = availableFlightResponse;
                            if (availableFlightResponse.getData().getDomesticOnwardFlights().size()>0){

                                recyclerViewFlightList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                oneWayFlightListAdapter = new OneWayFlightListAdapter(getApplicationContext(), availableFlightResponse.getData().getDomesticOnwardFlights(),FlightListActivity.this,FlightListActivity.this);
                                recyclerViewFlightList.setAdapter(oneWayFlightListAdapter);


                            } }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(FlightListActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_list), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onFlightItemListerne(int position, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mFlightItemListenre) {


        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        Intent intent = new Intent(FlightListActivity.this,FlightDetailActivity.class);
       // intent.putExtra("serializable_extra", String.valueOf(mFlightItemListenre) );

       // BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE_FULL_NAME,mFlightItemListenre.getFlightSegments().get(0).getIntDepartureAirportName());
      //  BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION_FULL_NAME,mFlightItemListenre.getFlightSegments().get(0).getIntArrivalAirportName());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE,"http://webapi.i2space.co.in/"+mFlightItemListenre.getFlightSegments().get(0).getImagePath());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_NAME,mFlightItemListenre.getFlightSegments().get(0).getAirLineName());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_CODE,mFlightItemListenre.getFlightSegments().get(0).getOperatingAirlineCode());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_FLIGHT_NUMBER,mFlightItemListenre.getFlightSegments().get(0).getOperatingAirlineFlightNumber());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION,mFlightItemListenre.getFlightSegments().get(0).getDuration());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_HAND_BAGGAGE,mFlightItemListenre.getFlightSegments().get(0).getBaggageAllowed().getHandBaggage());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_CHECKIN_BAGGAGE,mFlightItemListenre.getFlightSegments().get(0).getBaggageAllowed().getCheckInBaggage());

     //   BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME,mFlightItemListenre.getFlightSegments().get(0).getDepartureDateTimeZone());
      //  BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME,mFlightItemListenre.getFlightSegments().get(0).getArrivalDateTimeZone());
        BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE,String.valueOf(mFlightItemListenre.getFareDetails().getTotalFare()));
      //  BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE,mFlightItemListenre.getFlightSegments());


        Gson gson = new Gson();
        String json = gson.toJson(mFlightItemListenre);
        editor.putString("MyObject", json);
        editor.commit();

        try {
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onFlight(int position) {

     //   AvailableFlightResponse mFlightItemListenre = new AvailableFlightResponse();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(FlightResponse.getData().getDomesticOnwardFlights().get(position));
        editor.putString("MyObject", json);
        editor.commit();
    }
}
