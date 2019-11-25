package com.safepayu.wallet.activity.booking.flight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.FlighPassengerBookingDialog;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.safepayu.wallet.activity.booking.flight.FlightListActivity.MY_PREFS_NAME;

public class FlightPassengerDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvFlightPassengerDateTravellersClass,tvFlightPassengerTo,tvFlightPassengerFrom,tvTotalFlightFare,tvTotalNavellersNumber,tvChild,tvInfant;
    private String Source,Destination,JourneyDate,TrvaellersCount,ClassType,Adults,Infants,Children;
    private Button backbtnFlightPassenger,continue_btn;
    private EditText etFlightpMobileNumber,etFlightpEmailNumber;
    public   Dialog dialog;
    private RecyclerView recyclerTravellerInfo;
    private ImageView imageCancel;
    String json;
    AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mdata;
    private FlighPassengerBookingDialog flighPassengerBookingDialog;
    private LinearLayout itemAdult,itemChild,itemInfant;
    TextView  tv_infant_btn,tv_adult_btn,tv_child_btn;
    private LinearLayout  linear_infant_traveller_info,linear_adult_traveller_info,linear_child_traveller_info;
    private EditText etFlightpAdultFname,FlightpAdultFname;
    private String flightAdultFNme,flightAdultLName,flightAdultDob,flightChildFNme,flightChildLNme,flightChildDob,flightInfantFNme,flightInfantLNme,flightInfantDob;
    final List<View> li = new ArrayList<>();
    final List<View> liChild = new ArrayList<>();
    final List<View> liInfant = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_passenger_detail);
        findId();
    }

    private void findId() {
        tvFlightPassengerDateTravellersClass = findViewById(R.id.tv_flightpassenger_date_travellers_class);
        tvFlightPassengerTo = findViewById(R.id.tvflightpassenger_to_where);
        tvFlightPassengerFrom = findViewById(R.id.tvflightpassenger_from_where);
        backbtnFlightPassenger = findViewById(R.id.backbtn_flight_passenger);
        tvTotalFlightFare = findViewById(R.id.total_flight_fare);
        tvTotalNavellersNumber = findViewById(R.id.tv_total_travellers_no);
        etFlightpMobileNumber = findViewById(R.id.et_flightp_mobile_number);
        etFlightpEmailNumber = findViewById(R.id.et_flightp_email_number);
        continue_btn = findViewById(R.id.continue_btn);
        tvChild = findViewById(R.id.tv_child);
        tvInfant = findViewById(R.id.tv_infant);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        json = prefs.getString("MyObject", "");
        mdata = gson.fromJson(json, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);

        //***************get data****************
        Source =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
        TrvaellersCount =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        Adults = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
        Infants = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
        Children = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);


        //***********set listener*******************
        backbtnFlightPassenger.setOnClickListener(this);
        continue_btn.setOnClickListener(this);

        //*************set data***********

        int totalTravellers = getIntent().getExtras().getInt("total_travellers");
        tvFlightPassengerDateTravellersClass.setText(JourneyDate+" | "+TrvaellersCount+" | "+ClassType);
        tvFlightPassengerFrom.setText(Source);
        tvFlightPassengerTo.setText(Destination);
        tvTotalNavellersNumber.setText("For " + totalTravellers + " Traveller");
        tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))));
        etFlightpMobileNumber.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        etFlightpEmailNumber.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));

        //****************set adult, child, infant layout**************

        itemAdult = findViewById(R.id.relflight_adult);

        for(int i=1; i<=Integer.parseInt(Adults); i++){
        View view = getLayoutInflater().inflate(R.layout.flight_traveller_adult_info,null);
       TextView t=  view.findViewById(R.id.tv_adult_btn);
       final LinearLayout linearLayout = view.findViewById(R.id.linear_adult_traveller_info);

       FlightpAdultFname = view.findViewById(R.id.et_flightp_adult_fname);

            linearLayout.setVisibility(View.GONE);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            });
            itemAdult.addView(view);
            li.add(view);

        }
        int a;
        for ( a =0;a<li.size();a++){
            tv_adult_btn =   li.get(a).findViewById(R.id.tv_adult_btn);
            linear_adult_traveller_info = li.get(a).findViewById(R.id.linear_adult_traveller_info);
            etFlightpAdultFname = li.get(a).findViewById(R.id.et_flightp_adult_fname);

        }


        //*********** child *****************
        if (Children.equals("")) {
            Children = "0";
            if (Integer.parseInt(Children)>0){
                itemChild = findViewById(R.id.relflight_child);
                for(int i=1; i<=Integer.parseInt(Children); i++){
                    View view = getLayoutInflater().inflate(R.layout.flight_traveller_child_info,null);
                    itemChild.addView(view); } }
            tvChild.setVisibility(View.GONE); }

        else {
            itemChild = findViewById(R.id.relflight_child);
            for (int i = 1; i <= Integer.parseInt(Children); i++) {
                View view = getLayoutInflater().inflate(R.layout.flight_traveller_child_info, null);
                TextView t = view.findViewById(R.id.tv_child_btn);
                final LinearLayout linearLayout = view.findViewById(R.id.linear_child_traveller_info);
                linearLayout.setVisibility(View.GONE);
                final int pos = i;
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                });
                itemChild.addView(view);
                liChild.add(view);
            }
            int c;
            for (c = 0; c < liChild.size(); c++) {
                tv_child_btn = liChild.get(c).findViewById(R.id.tv_child_btn);
                linear_child_traveller_info = liChild.get(c).findViewById(R.id.linear_child_traveller_info);
            }
        }

        //********** infant **********//
        if (Infants.equals("")) {
            Infants = "0";
            if (Integer.parseInt(Infants)>0){

                itemInfant = findViewById(R.id.relflight_infant);
                for(int i=1; i<=Integer.parseInt(Infants); i++){
                    View view = getLayoutInflater().inflate(R.layout.flight_traveller_infant_info,null);
                    itemInfant.addView(view);
                } }
            tvInfant.setVisibility(View.GONE); }
        else {
            itemInfant = findViewById(R.id.relflight_infant);

            for(int i=1; i<=Integer.parseInt(Infants); i++){
                View view = getLayoutInflater().inflate(R.layout.flight_traveller_infant_info,null);
                TextView t = view.findViewById(R.id.tv_infant_btn);
                final LinearLayout linearLayout = view.findViewById(R.id.linear_infant_traveller_info);
                linearLayout.setVisibility(View.GONE);
                final int pos = i;
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                });
                itemInfant.addView(view);
                liInfant.add(view);}
            int f;
            for (f = 0; f < liInfant.size(); f++) {
                tv_infant_btn = liInfant.get(f).findViewById(R.id.tv_infant_btn);
                linear_infant_traveller_info = liInfant.get(f).findViewById(R.id.linear_infant_traveller_info);
            }
         } }

    private void adult(int parseInt) {
        Toast.makeText(getApplicationContext(),parseInt,Toast.LENGTH_LONG).show();
     /*   linear_adult_traveller_info = view.findViewById(R.id.linear_adult_traveller_info);
        tv_adult_btn = view.findViewById(R.id.tv_adult_btn);
        etFlightpAdultFname = view.findViewById(R.id.et_flightp_adult_fname);*/
    }


    private void infant(final int i) {
        tv_infant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_infant_traveller_info.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_flight_passenger:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();

                break;
            case R.id.continue_btn:
               // showDialog(FlightPassengerDetailActivity.this);
                if (validate()) {

                }

                break;
        }
    }

    private boolean validate() {

        EditText fnameAdult, lnameAdult, dobAdult,fnameChild,lnameChild,dobChild,fnameInfant,lnameInfant,dobInfant;
        int a;
        for (a = 0; a < li.size(); a++) {

            fnameAdult = ((EditText) li.get(a).findViewById(R.id.et_flightp_adult_fname));
            lnameAdult = ((EditText) li.get(a).findViewById(R.id.et_flightp_adult_lname));
            dobAdult = ((EditText) li.get(a).findViewById(R.id.et_flightp_adult_dob));
            flightAdultFNme = fnameAdult.getText().toString().trim();
            flightAdultLName = lnameAdult.getText().toString().trim();
            flightAdultDob = dobAdult.getText().toString().trim();
            if (flightAdultFNme.equals("") && flightAdultFNme.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter adult first name", Toast.LENGTH_LONG).show();
            } else if (flightAdultLName.equals("") && flightAdultLName.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter adult last name", Toast.LENGTH_LONG).show();
            } else if (flightAdultDob.equals("") && flightAdultDob.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter adult dob", Toast.LENGTH_LONG).show();
            }
        }
        int c;
        for (c = 0; c < liChild.size(); c++){
            fnameChild = ((EditText) liChild.get(c).findViewById(R.id.et_flightp_child_fname));
            lnameChild = ((EditText) liChild.get(c).findViewById(R.id.et_flightp_child_lname));
            dobChild = ((EditText) liChild.get(c).findViewById(R.id.et_flightp_child_dob));
            flightChildFNme = fnameChild.getText().toString().trim();
            flightChildLNme = lnameChild.getText().toString().trim();
            flightChildDob = dobChild.getText().toString().trim();
            if (flightChildFNme.equals("") && flightChildFNme.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter child first name", Toast.LENGTH_LONG).show();
            } else if (flightChildLNme.equals("") && flightChildLNme.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter child last name", Toast.LENGTH_LONG).show();
            } else if (flightChildDob.equals("") && flightChildDob.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter child adult dob", Toast.LENGTH_LONG).show();
            }
        }
        int f;
        for (f = 0; f < liInfant.size(); f++) {

            fnameInfant = ((EditText) liInfant.get(f).findViewById(R.id.et_flightp_infant_fname));
            lnameInfant = ((EditText) liInfant.get(f).findViewById(R.id.et_flightp_infant_lname));
            dobInfant = ((EditText) liInfant.get(f).findViewById(R.id.et_flightp_infant_dob));
            flightInfantFNme = fnameInfant.getText().toString().trim();
            flightInfantLNme = lnameInfant.getText().toString().trim();
            flightInfantDob= dobInfant.getText().toString().trim();
            if (flightInfantFNme.equals("") && flightInfantFNme.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter infant first name", Toast.LENGTH_LONG).show();
            } else if (flightInfantLNme.equals("") && flightInfantLNme.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter infant last name", Toast.LENGTH_LONG).show();
            } else if (flightInfantDob.equals("") && flightInfantDob.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter inant adult dob", Toast.LENGTH_LONG).show();
            }
        }

            return true;
        }

   /* private void checkBtn() {
        int a ;
        for (a =0;a<li.size();a++){
            flightAdultFNme =    ((EditText)li.get(a).findViewById(R.id.et_flightp_adult_fname)).getText().toString();
            flightAdultLName =    ((EditText)li.get(a).findViewById(R.id.et_flightp_adult_lname)).getText().toString();
            flightAdultDob =    ((EditText)li.get(a).findViewById(R.id.et_flightp_adult_dob)).getText().toString();
            flightChildFNme =    ((EditText)li.get(a).findViewById(R.id.et_flightp_child_fname)).getText().toString();
            flightChildLNme =    ((EditText)li.get(a).findViewById(R.id.et_flightp_child_lname)).getText().toString();
            flightChildDob =    ((EditText)li.get(a).findViewById(R.id.et_flightp_child_dob)).getText().toString();
            flightInfantFNme =    ((EditText)li.get(a).findViewById(R.id.et_flightp_infant_fname)).getText().toString();
            flightInfantLNme =    ((EditText)li.get(a).findViewById(R.id.et_flightp_infant_lname)).getText().toString();
            flightInfantDob =    ((EditText)li.get(a).findViewById(R.id.et_flightp_infant_dob)).getText().toString();

            Toast.makeText(getApplicationContext(),flightAdultFNme,Toast.LENGTH_LONG).show();
           Log.e("flightfname",flightAdultFNme);
           Log.e("flightfname",flightAdultLName);
           Log.e("flightfname",flightAdultDob);
        }*/

 //   }


    private void showDialog(FlightPassengerDetailActivity flightPassengerDetailActivity) {

        dialog = new Dialog(flightPassengerDetailActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.flight_final_booking_details_dialog);
        recyclerTravellerInfo = dialog.findViewById(R.id.recyler_traveller_info);
        imageCancel = dialog.findViewById(R.id.image_cancel);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();

        imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        recyclerTravellerInfo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        flighPassengerBookingDialog = new FlighPassengerBookingDialog(getApplicationContext());
        recyclerTravellerInfo.setAdapter(flighPassengerBookingDialog);
    }
}

