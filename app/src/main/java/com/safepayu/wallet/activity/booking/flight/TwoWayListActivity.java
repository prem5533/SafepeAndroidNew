package com.safepayu.wallet.activity.booking.flight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.TwoWayFlightListAdapter;
import com.safepayu.wallet.adapter.fight.TwoWayFlightTopAdapter;
import com.safepayu.wallet.adapter.fight.TwoWayReturnFlightListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking.flight.AvailableFlightRequest;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;

import java.text.NumberFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TwoWayListActivity extends AppCompatActivity implements View.OnClickListener,TwoWayFlightListAdapter.OnFlightItemListener,TwoWayReturnFlightListAdapter.OnFlightItemReturnListener {

    private LinearLayout linearTwoWay,lFarebreakup;
    private RecyclerView recyclerViewPriceList,recyclerViewFlightListgGo,recyclerViewFlightListReturn;
    private TwoWayFlightTopAdapter twoWayFlightTopAdapter;
    private TwoWayFlightListAdapter twoWayFlightListAdapter;
    private TwoWayReturnFlightListAdapter twoWayReturnFlightListAdapter;
    private Button backBtn,btnContinue;
    public static BottomNavigationView bottomNavigation;
    private TextView tvFrom, tvTo,tvFlightDateTravellersClass,tvOnwardFlightName,tvReturnFlightName,tvOnwardDate,tvReturnDate,totalFlightFare;
    private String Source,Destination,JourneyDate,TripType,User,UserType,Adults,Infants,Children,FlightType,ReturnDate,TravelClass,TrvaellersCount,ClassType,
            FlightSourceDate,FlightDestinationDate,Depp,FlightReturnDate;
    private AvailableFlightRequest availableFlightRequest;
    AvailableFlightResponse FlightResponse;
    private LoadingDialog loadingDialog;
    int finalIndex =0,ReturnAmout,onwardsAmount,TotalAmount,totalTravellers;
    boolean returnFlight=false,onwardFlight=false;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_way_list);
        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);
        linearTwoWay = findViewById(R.id.linear_two_way);
        recyclerViewPriceList = findViewById(R.id.flight_price_list);
        recyclerViewFlightListgGo = findViewById(R.id.flight_list1);
        recyclerViewFlightListReturn = findViewById(R.id.flight_list2);
        tvFrom = findViewById(R.id.tv_two_way_from);
        tvTo = findViewById(R.id.tv_two_way_to);
        tvFlightDateTravellersClass = findViewById(R.id.tv_twoway_date_travellers_class);
        tvOnwardFlightName = findViewById(R.id.tv_onwards_flight_name);
        tvReturnFlightName = findViewById(R.id.tv_return_flight_name);
        tvOnwardDate = findViewById(R.id.tv_onwards_flight_date);
        tvReturnDate = findViewById(R.id.tv_return_flight_date);
        lFarebreakup = findViewById(R.id.l_farebreakup);
        btnContinue = findViewById(R.id.continue_btn);
        totalFlightFare = findViewById(R.id.total_flight_fare);

        backBtn = findViewById(R.id.backbtn_flight_list);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        backBtn.setOnClickListener(this);
        btnContinue.setOnClickListener(this);



        recyclerViewPriceList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        twoWayFlightTopAdapter = new TwoWayFlightTopAdapter(getApplicationContext());
        recyclerViewPriceList.setAdapter(twoWayFlightTopAdapter);






        //*************get string*********************
        Intent intent= getIntent();
        Depp = intent.getExtras().getString("dep_date");
        FlightReturnDate = intent.getExtras().getString("arrival_date");
        Source =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
        TrvaellersCount =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        FlightSourceDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE_DATE);
        FlightDestinationDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TWOWAY_RETURN_DATE);
        ReturnDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_DATE);
        TravelClass =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS);
        Adults =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
        Infants =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
        Children =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);
        TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
        User =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER);
        UserType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_USER_TYPE);
        FlightType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TYPE);

        tvFrom.setText(Source);
        tvTo.setText(Destination);
        tvFlightDateTravellersClass.setText(JourneyDate+" | "+TrvaellersCount+" | "+ClassType);
        tvOnwardFlightName.setText(Source+" - "+Destination);
        tvReturnFlightName.setText(Destination+" - "+Source);
        tvOnwardDate.setText(Depp);
        tvReturnDate.setText(FlightReturnDate);


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

        getTwaoWayFlightAvailable(availableFlightRequest);

        onwardReturnCheck();

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

    }

    private void onwardReturnCheck() {
        if (returnFlight && onwardFlight){
            TotalAmount =onwardsAmount+ReturnAmout;
            totalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(TotalAmount));
            btnContinue.setText("Continue");
            btnContinue.setEnabled(true);
            TotalAmount =onwardsAmount+ReturnAmout;


        }else {
            if (!returnFlight){
                Toast.makeText(getApplicationContext(),"Please select Return Flight",Toast.LENGTH_LONG).show();
                btnContinue.setText("Next");
                btnContinue.setEnabled(false);
            }

            if (!onwardFlight){
                Toast.makeText(getApplicationContext(),"Please select Onward Flight",Toast.LENGTH_LONG).show();
                btnContinue.setText("Next");
                btnContinue.setEnabled(false);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_flight_list:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.continue_btn:
             Intent intent = new Intent(getApplicationContext(),TwoWayFlightDetailActivity.class);

             startActivity(intent);
                break;
        }
    }

    private void getTwaoWayFlightAvailable(AvailableFlightRequest availableFlightRequest) {

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

                             /*   recyclerViewFlightList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                oneWayFlightListAdapter = new OneWayFlightListAdapter(getApplicationContext(), availableFlightResponse.getData().getDomesticOnwardFlights(),FlightListActivity.this,FlightListActivity.this);
                                recyclerViewFlightList.setAdapter(oneWayFlightListAdapter);
*/

                                recyclerViewFlightListgGo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                twoWayFlightListAdapter = new TwoWayFlightListAdapter(getApplicationContext(),availableFlightResponse.getData().getDomesticOnwardFlights(),TwoWayListActivity.this);
                                recyclerViewFlightListgGo.setAdapter(twoWayFlightListAdapter);

                                recyclerViewFlightListReturn.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                twoWayReturnFlightListAdapter = new TwoWayReturnFlightListAdapter(getApplicationContext(),availableFlightResponse.getData().getDomesticReturnFlights(),TwoWayListActivity.this);
                                recyclerViewFlightListReturn.setAdapter(twoWayReturnFlightListAdapter);


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

    /*@Override
    public void onFlightItemListerne(int position, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mFlightItemListenre) {
        Intent intent = new Intent(TwoWayListActivity.this,FlightDetailActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onFlightItemReturnListerne(int position, AvailableFlightResponse.DataBean.DomesticReturnFlightsBean mFlightItemListenreReturn) {
        Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
        ReturnAmout = mFlightItemListenreReturn.getFareDetails().getTotalFare();
        returnFlight=true;
        onwardReturnCheck();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        json = gson.toJson(mFlightItemListenreReturn);
        editor.putString("MyObjectReturn", json);
        editor.commit();

    }

    @Override
    public void onFlightItemListerne(final int position, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mFlightItemListenre, final LinearLayout liOnward) {

        onwardsAmount = mFlightItemListenre.getFareDetails().getTotalFare();
       // BaseApp.getInstance().sharedPref().setObject(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_ONWARD_FARE,String.valueOf(mFlightItemListenre.getFareDetails().getTotalFare()));
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        json = gson.toJson(mFlightItemListenre);
        editor.putString("MyObjectOnward", json);
        editor.commit();
        onwardFlight=true;
        onwardReturnCheck();



//        finalIndex = position;
//        twoWayFlightListAdapter.notifyDataSetChanged();
//
//        if(finalIndex ==position){
//            liOnward.setBackgroundColor(getResources().getColor(R.color.green_100));
//            Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
//
//        }else{
//            liOnward.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        }
    }


}
