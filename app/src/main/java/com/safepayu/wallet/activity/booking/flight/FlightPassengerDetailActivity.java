package com.safepayu.wallet.activity.booking.flight;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.Navigation;
import com.safepayu.wallet.activity.NewAccount;
import com.safepayu.wallet.activity.TermsAndCondition;
import com.safepayu.wallet.adapter.fight.FlighPassengerBookingDialog;
import com.safepayu.wallet.adapter.fight.FlightTravellersList;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.DatePicker;
import com.safepayu.wallet.dialogs.DatePickerChild;
import com.safepayu.wallet.dialogs.DatePickerInfant;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.Config;
import com.safepayu.wallet.models.request.booking.flight.ConvieneceFeeRequest;
import com.safepayu.wallet.models.request.booking.flight.FlightBlockTicketRequest;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.safepayu.wallet.models.response.booking.flight.ConvieneceFeeResponse;
import com.safepayu.wallet.models.response.booking.flight.FlightBlockTicketResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.booking.flight.FlightListActivity.MY_PREFS_NAME;

public class FlightPassengerDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvFlightPassengerDateTravellersClass,tvFlightPassengerTo,tvFlightPassengerFrom,tvTotalNavellersNumber,tvChild,tvInfant;
    private String Source,Destination,JourneyDate,TrvaellersCount,ClassType,Adults,Infants,Children,FlightImage,TravelClass,AirLineCode,AirLineNumber,TotalFareReturnOnward;
    private Button backbtnFlightPassenger,continue_btn;
    private EditText etFlightpMobileNumber,etFlightpEmailNumber;
    public  Dialog dialog;
    public  static LoadingDialog loadingDialog;
    private RecyclerView recyclerTravellerInfo;
    private ImageView imageCancel,image_flight_detail_pop_up,image_flight_detail_return;
    private TextView tv_flightbooking_name_popup,tv_flightbooking_classname_popup,tvFlightBookingSourceName_popup,tvFlightBookingDestiName_popup,tvTermCond,
            tvFlightBookingDepDate,tvFlightDetailDestiDate,tvFlightBookingDuration,tv_flight_booking_stop,tv_flight_booking_dep_time,tv_flight_booking_arrival_time,tv_gender,
            tv_flightbooking_name_return,tv_flightbooking_classname_return,tv_flight_booking_source_name_return,tv_flight_detail_desti_name_return,tv_flight_booking_dep_date_return,
            tv_flight_detail_desti_date_return,tv_flight_booking_duration_return,tv_flight_booking_stop_return,tv_flight_booking_dep_time_return,tv_flight_booking_arrival_time_return;
    String json;
    private Button flightBookBtn;
    private static String TripType;
    private CheckBox checkboxPassengerTC;


  public  static AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mdata;
  public  static AvailableFlightResponse.DataBean.DomesticReturnFlightsBean mdataReturn;
    private FlighPassengerBookingDialog flighPassengerBookingDialog;

    private LinearLayout itemAdult,itemChild,itemInfant,lFarebreakup;
    TextView  tv_infant_btn,tv_adult_btn,tv_child_btn;
    private LinearLayout  linear_infant_traveller_info,linear_adult_traveller_info,linear_child_traveller_info;
    private EditText etFlightpAdultFname,FlightpAdultFname,dobAdult;
    private String flightAdultFNme,flightAdultLName,flightAdultDob,flightChildFNme,flightChildLNme,flightChildDob,flightInfantFNme,flightInfantLNme,flightInfantDob,
            Fullname,radioGender,childFullNme,infantFullNme;
    private LinearLayout lreturn_detail_popup;
    ImageView flight_adult_dob;
    final List<View> li = new ArrayList<>();
    final List<View> liChild = new ArrayList<>();
    final List<View> liInfant = new ArrayList<>();
    ArrayList<String>adultList = new ArrayList<>();
    ArrayList<String>DobList = new ArrayList<>();
    FlightBlockTicketResponse FlightResponse;
    FlightTravellersList flightTravellersList;
    public  static ConvieneceFeeRequest convieneceFeeRequest;
    ConvieneceFeeResponse ConvieneceResponse;
    public  static int ConvieneceFee,totalTravellers ,totalPaid,total2,total1;
    public  static TextView tvTotalFlightFare;

    Gson gson;

    private FlightBlockTicketRequest flightBlockTicketRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_passenger_detail);
        findId();
        getConvieneceFee();
    }



    private void findId() {
        loadingDialog = new LoadingDialog(this);
        tvFlightPassengerDateTravellersClass = findViewById(R.id.tv_flightpassenger_date_travellers_class);
        tvFlightPassengerTo = findViewById(R.id.tvflightpassenger_to_where);
        tvFlightPassengerFrom = findViewById(R.id.tvflightpassenger_from_where);
        backbtnFlightPassenger = findViewById(R.id.backbtn_flight_passenger);
        tvTotalFlightFare = findViewById(R.id.total_flight_fare);
        tvTotalNavellersNumber = findViewById(R.id.tv_total_travellers_no);
        etFlightpMobileNumber = findViewById(R.id.et_flightp_mobile_number);
        etFlightpEmailNumber = findViewById(R.id.et_flightp_email_number);
        lFarebreakup = findViewById(R.id.l_farebreakup);
        continue_btn = findViewById(R.id.continue_btn);
        tvChild = findViewById(R.id.tv_child);
        tvInfant = findViewById(R.id.tv_infant);
        checkboxPassengerTC = findViewById(R.id.checkbox_passenger_tc);
        tvTermCond = findViewById(R.id.tv_term_cond);



        //***************get data****************
        Source =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        JourneyDate =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE);
        TrvaellersCount =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT);
        ClassType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE);
        Adults = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS);
        Infants = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS);
        Children = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN);
        FlightImage = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        TravelClass =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS);
        AirLineCode =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_CODE);
        AirLineNumber =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_FLIGHT_NUMBER);
        TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
        TotalFareReturnOnward =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().TOTALFARE_RETURN_ONWARDS);

        SpannableString ss = new SpannableString("I understand & agree with the all Terms and Conditions of SafePe");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(FlightPassengerDetailActivity.this, TermsAndCondition.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 34, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

       // TextView textView = (TextView) findViewById(R.id.hello);
        tvTermCond.setText(ss);
        tvTermCond.setMovementMethod(LinkMovementMethod.getInstance());
        tvTermCond.setHighlightColor(Color.TRANSPARENT);



        checkboxPassengerTC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkboxPassengerTC.isChecked()){
                    startActivity(new Intent(FlightPassengerDetailActivity.this, TermsAndCondition.class));
                }
            }
        });

        if (TripType.equals("1")){
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
             gson = new Gson();
            json = prefs.getString("MyObject", "");
            mdata = gson.fromJson(json, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);
        }
        else if (TripType.equals("2")){
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            gson = new Gson();
            json = prefs.getString("MyObjectOnward", "");
            mdata = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);

            json = prefs.getString("MyObjectReturn", "");
            mdataReturn = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticReturnFlightsBean.class);
        }

        if (Infants.equals("")){
            Infants="0";
        }
        if (Children.equals("")){
            Children = "0";
        }

        if (TravelClass.equals("E")){
            TravelClass = "Economy";
        }
        else  if (TravelClass.equals("PE")){
            TravelClass = "Preminum Economy";
        }
        else  if (TravelClass.equals("B")){
            TravelClass = "Business";
        }else  if (TravelClass.equals("F")){
            TravelClass = "First Class";
        }

        //***********set listener*******************
        backbtnFlightPassenger.setOnClickListener(this);
        continue_btn.setOnClickListener(this);
        lFarebreakup.setOnClickListener(this);

        //*************set data***********

         totalTravellers = getIntent().getExtras().getInt("total_travellers");
        tvFlightPassengerDateTravellersClass.setText(JourneyDate+" | "+TrvaellersCount+" | "+ClassType);
        tvFlightPassengerFrom.setText(Source);
        tvFlightPassengerTo.setText(Destination);
        tvTotalNavellersNumber.setText("For " + totalTravellers + " Traveller");
        if (TripType.equals("1")){
            int total = ((Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE)))+(ConvieneceFee*totalTravellers));
           // tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(total));
        }
        else if (TripType.equals("2")){
            tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(Integer.parseInt(TotalFareReturnOnward)));

        }
        etFlightpMobileNumber.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        etFlightpEmailNumber.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_EMAIL));


     /*   FlightBlockTicketRequest.DataBean.DomesticOnwardFlightsBean domesticOnwardFlightsBean;
        domesticOnwardFlightsBean=dataBean.getDomesticOnwardFlights();

        List<FlightBlockTicketRequest.DataBean.DomesticOnwardFlightsBean> DomesticOnwardFlights;
        List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean> DomesticOnwardFlightsRe = null;
        DomesticOnwardFlights=DomesticOnwardFlightsRe;*/


        //****************set adult, child, infant layout**************

        itemAdult = findViewById(R.id.relflight_adult);

        for(int i=1; i<=Integer.parseInt(Adults); i++){
        View view = getLayoutInflater().inflate(R.layout.flight_traveller_adult_info,null);
       TextView t=  view.findViewById(R.id.tv_adult_btn);
       final LinearLayout linearLayout = view.findViewById(R.id.linear_adult_traveller_info);
       final EditText etFlightAdultDOB = view.findViewById(R.id.et_flightp_adult_dob);


       etFlightAdultDOB.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatePicker datePicker = DatePicker.newInstance(etFlightAdultDOB, null);
               datePicker.show(getSupportFragmentManager(), "datePicker");
           }
       });

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
                final EditText etFlightChildDOB = view.findViewById(R.id.et_flightp_child_dob);
                etFlightChildDOB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerChild datePicker = DatePickerChild.newInstance(etFlightChildDOB, null);
                        datePicker.show(getSupportFragmentManager(), "datePicker");

                    }
                });
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
                final EditText etFlightInfantDOB = view.findViewById(R.id.et_flightp_infant_dob);
                etFlightInfantDOB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerInfant datePicker = DatePickerInfant.newInstance(etFlightInfantDOB, null);
                        datePicker.show(getSupportFragmentManager(), "datePicker");
                    }
                });
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

                   // if (validateChkBox()) {
                        showDialog(FlightPassengerDetailActivity.this);
                  //  }
                 //   getflightBlockTicket(flightBlockTicketRequest);

                }

                break;

            case R.id.l_farebreakup:

                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                break;
        }
    }

    private boolean validateChkBox() {
     if (!checkboxPassengerTC.isChecked()){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.flight_passenger),"Please Accept Term & Conditions", true);
            return false;
        }

        return true;
    }


    private boolean validate() {

        EditText fnameAdult, lnameAdult,fnameChild,lnameChild,dobChild,fnameInfant,lnameInfant,dobInfant;
        String selectedRadioButtonText = " ";
        int a;
        adultList.clear();
        DobList.clear();
        for (a = 0; a < li.size(); a++) {

            fnameAdult = ((EditText) li.get(a).findViewById(R.id.et_flightp_adult_fname));
            lnameAdult = ((EditText) li.get(a).findViewById(R.id.et_flightp_adult_lname));
            dobAdult = ((EditText) li.get(a).findViewById(R.id.et_flightp_adult_dob));
            tv_gender = ((TextView) li.get(a).findViewById(R.id.tv_gender));
             RadioGroup  radioGen = ((RadioGroup) li.get(a).findViewById(R.id.radio_gender));

            flightAdultFNme = fnameAdult.getText().toString().trim();
            flightAdultLName = lnameAdult.getText().toString().trim();
            flightAdultDob = dobAdult.getText().toString().trim();
            radioGender = tv_gender.getText().toString();

            if (fnameAdult.getText().toString().trim().length() == 0)  {

                Toast.makeText(getApplicationContext(), "Please enter adult first name", Toast.LENGTH_LONG).show();
                return  false;
            } else if (flightAdultLName.equals("") && flightAdultLName.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter adult last name", Toast.LENGTH_LONG).show();
                return  false;
            } else if (flightAdultDob.equals("") && flightAdultDob.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter adult dob", Toast.LENGTH_LONG).show();
                return  false;
            }

            //************Gender Radio *************************
            int selectedRadioButtonID = radioGen.getCheckedRadioButtonId();
            // If nothing is selected from Radio Group, then it return -1
            if (selectedRadioButtonID != -1) {
                RadioButton selectedRadioButton = radioGen.findViewById(selectedRadioButtonID);
                 selectedRadioButtonText = selectedRadioButton.getText().toString();
             //   Toast.makeText(getApplicationContext(),selectedRadioButtonText,Toast.LENGTH_LONG).show();
                tv_gender.setText(selectedRadioButtonText);

            } else{
                Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();

            }

            Fullname = selectedRadioButtonText+"~"+flightAdultFNme+"~"+flightAdultLName+"~"+"adt";
             adultList.add(Fullname);
             DobList.add(flightAdultDob);

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
                return  false;
            } else if (flightChildLNme.equals("") && flightChildLNme.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter child last name", Toast.LENGTH_LONG).show();
                return  false;
            } else if (flightChildDob.equals("") && flightChildDob.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter child dob", Toast.LENGTH_LONG).show();
                return  false;
            }
            childFullNme = "Mstr."+"~"+flightChildFNme+"~"+flightChildLNme+"~"+"chd";
            adultList.add(childFullNme);
            DobList.add(flightChildDob);
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
                return  false;
            } else if (flightInfantLNme.equals("") && flightInfantLNme.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter infant last name", Toast.LENGTH_LONG).show();
                return  false;
            } else if (flightInfantDob.equals("") && flightInfantDob.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter infant  dob", Toast.LENGTH_LONG).show();
                return  false;
            }
            infantFullNme = "Mstr."+"~"+flightInfantFNme+"~"+flightInfantLNme+"~"+"inf";
            adultList.add(infantFullNme);
            DobList.add(flightInfantDob);
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
           //Log.e("flightfname",flightAdultFNme);
           //Log.e("flightfname",flightAdultLName);
           //Log.e("flightfname",flightAdultDob);
        }*/

 //   }


    private void getConvieneceFee() {

        convieneceFeeRequest = new ConvieneceFeeRequest();

        convieneceFeeRequest.setType("1");
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(FlightPassengerDetailActivity.this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getConvieneceFee(convieneceFeeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ConvieneceFeeResponse>(){
                    @Override
                    public void onSuccess(ConvieneceFeeResponse response) {
                        loadingDialog.hideDialog();
                        ConvieneceResponse = response;
                        if (response.isStatus()) {

                            ConvieneceFee = response.getData().getFee();
                            totalPaid = ConvieneceFee*totalTravellers;
                           // tvConvenienceFee.setText(String.valueOf(totalPaid));

                            if (TripType.equals("1")){
                                int total = ((Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE)))+(totalPaid));
                                tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(total));
                            }
                            else if (TripType.equals("2")){
                                int total = (Integer.parseInt(TotalFareReturnOnward)+totalPaid);
                                tvTotalFlightFare.setText(getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(total));
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //  BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_book_detail), false, e.getCause());
                    }
                }));

    }

    private void showDialog(FlightPassengerDetailActivity flightPassengerDetailActivity) {
         CharSequence s;
        dialog = new Dialog(flightPassengerDetailActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.flight_final_booking_details_dialog);
        recyclerTravellerInfo = dialog.findViewById(R.id.recyler_traveller_info);
        imageCancel = dialog.findViewById(R.id.image_cancel);
        image_flight_detail_pop_up = dialog.findViewById(R.id.image_flight_detail);
        tv_flightbooking_name_popup = dialog.findViewById(R.id.tv_flightbooking_name);
        tv_flightbooking_classname_popup = dialog.findViewById(R.id.tv_flightbooking_classname);
        tvFlightBookingSourceName_popup = dialog.findViewById(R.id.tv_flight_booking_source_name);
        tvFlightBookingDestiName_popup = dialog.findViewById(R.id.tv_flight_detail_desti_name);
        tvFlightBookingDepDate = dialog.findViewById(R.id.tv_flight_booking_dep_date);
        tvFlightDetailDestiDate = dialog.findViewById(R.id.tv_flight_detail_desti_date);
        tvFlightBookingDuration = dialog.findViewById(R.id.tv_flight_booking_duration);
        tv_flight_booking_stop = dialog.findViewById(R.id.tv_flight_booking_stop);
        tv_flight_booking_dep_time = dialog.findViewById(R.id.tv_flight_booking_dep_time);
        tv_flight_booking_arrival_time = dialog.findViewById(R.id.tv_flight_booking_arrival_time);
        flightBookBtn = dialog.findViewById(R.id.flight_book_btn);

        // return flight detail
        image_flight_detail_return = dialog.findViewById(R.id.image_flight_detail_return);
        tv_flightbooking_name_return = dialog.findViewById(R.id.tv_flightbooking_name_return);
        tv_flightbooking_classname_return = dialog.findViewById(R.id.tv_flightbooking_classname_return);
        tv_flight_booking_source_name_return = dialog.findViewById(R.id.tv_flight_booking_source_name_return);
        tv_flight_detail_desti_name_return = dialog.findViewById(R.id.tv_flight_detail_desti_name_return);
        tv_flight_booking_dep_date_return = dialog.findViewById(R.id.tv_flight_booking_dep_date_return);
        tv_flight_detail_desti_date_return = dialog.findViewById(R.id.tv_flight_detail_desti_date_return);
        tv_flight_booking_duration_return = dialog.findViewById(R.id.tv_flight_booking_duration_return);
        tv_flight_booking_stop_return = dialog.findViewById(R.id.tv_flight_booking_stop_return);
        tv_flight_booking_dep_time_return = dialog.findViewById(R.id.tv_flight_booking_dep_time_return);
        tv_flight_booking_arrival_time_return = dialog.findViewById(R.id.tv_flight_booking_arrival_time_return);
        lreturn_detail_popup = dialog.findViewById(R.id.lreturn_detail_popup);

        //**************Go to payment activity*************
        flightBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getApplicationContext(), BookingPaymentActivity.class);
                intent.putExtra("adult_list", adultList);
                intent.putExtra("dob_list", DobList);
                intent.putExtra("totalPaid",totalPaid);
            /*    if (TripType.equals("2")){
                    intent.putExtra("payamount2", String.valueOf(total2));
                }
                else if (TripType.equals("1")){
                    intent.putExtra("payamount", String.valueOf(total1));
                }*/

                startActivity(intent);
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
        tv_flightbooking_classname_popup.setText(TravelClass);
        imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //********************for return flight detail  ************************Trip Type 2*************************************
        if (TripType.equals("2")){

            Picasso.get().load("http://webapi.i2space.co.in/"+mdata.getFlightSegments().get(0).getImagePath()).into(image_flight_detail_pop_up);
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ONWARD_IMAGE,"http://webapi.i2space.co.in/"+mdata.getFlightSegments().get(0).getImagePath());
            tv_flightbooking_name_popup.setText(mdata.getFlightSegments().get(0).getAirLineName()+" " +mdata.getFlightSegments().get(0).getOperatingAirlineCode()+" "+mdata.getFlightSegments().get(0).getOperatingAirlineFlightNumber());
             total2 = (totalPaid+Integer.parseInt(TotalFareReturnOnward));
            flightBookBtn.setText("Pay "+getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(total2));

            tvFlightBookingSourceName_popup.setText(Source);
            tvFlightBookingDestiName_popup.setText(Destination);


            //************set flight time onward***********
            int flightStop =   mdata.getFlightSegments().size();
            String Date, Time, h, m;
            String depTime = mdata.getFlightSegments().get(0).getDepartureDateTime();
            String[] separated = depTime.split("T");
            Date = separated[0];
            Time = separated[1];
            String[] separatedTime = Time.split(":");
            h = separatedTime[0];
            m = separatedTime[1];
            tv_flight_booking_dep_time.setText(h + ":" + m);
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME,h + ":" + m);
//date departure
            String[] sept = Date.split("-");
            String yd = sept[0];
            String mod = sept[1];
            String dd = sept[2];

            String dateZone= dd+"/"+mod+"/"+yd;
            Date  date = new Date(dateZone);
            s  = DateFormat.format("dd-MMM-yyyy", date.getTime());
            String daymonthYear = (String) s;
            String[] sep = daymonthYear.split("-");
            String d = sep[0];
            String mo = sep[1];
            String y = sep[2];
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
            String dayOfWeek = simpledateformat.format(date);
            tvFlightBookingDepDate.setText(dayOfWeek+", "+d+mo+" "+y);
            String dayofWeek = dayOfWeek+", "+d+mo+" "+y;
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_START_JOURNEY,dayofWeek);

            if (flightStop>1){
                String arrTime = mdata.getFlightSegments().get(flightStop-1).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tv_flight_booking_arrival_time.setText(h + ":" + m);
              //  BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME,h + ":" + m);

                String[] septt = Date.split("-");
                String ydd = septt[0];
                String modd = septt[1];
                String ddd = septt[2];

                String dateZonee= modd+"/"+ddd+"/"+ydd;
                Date  datee = new Date(dateZonee);
                s  = DateFormat.format("dd-MMM-yyyy", datee.getTime());
                String daymonthYear1 = (String) s;
                String[] sep1 = daymonthYear1.split("-");
                String da = sep1[0];
                String mon = sep1[1];
                String ye = sep1[2];

                SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
                String day_Week = sdateformat.format(datee);
                tvFlightDetailDestiDate.setText(day_Week+", "+da+mon+" "+ye);

            }
            else {
                String arrTime = mdata.getFlightSegments().get(0).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tv_flight_booking_arrival_time.setText(h + ":" + m);
              //  BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME,h + ":" + m);

                String[] septt = Date.split("-");
                String ydd = septt[0];
                String modd = septt[1];
                String ddd = septt[2];

                String dateZonee= modd+"/"+ddd+"/"+ydd;
                Date  datee = new Date(dateZonee);
                s  = DateFormat.format("dd-MMM-yyyy", datee.getTime());
                String daymonthYear1 = (String) s;
                String[] sep1 = daymonthYear1.split("-");
                String da = sep1[0];
                String mon = sep1[1];
                String ye = sep1[2];

                SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
                String day_Week = sdateformat.format(datee);
                tvFlightDetailDestiDate.setText(day_Week+", "+da+mon+" "+ye);
            }
            //*****************calculate total hour & minute onward**************

            int     sum = 0;
            int mintemp = 0;
            int hourtemp = 0;
            if (flightStop>0){
                for (int i = 0; i<mdata.getFlightSegments().size();i++){
                    String duration [] = mdata.getFlightSegments().get(i).getDuration().split(":");
                    String hour = duration[0];
                    String min = duration[1];
                    String durat [] = min.split(" ");
                    String a = durat[0];
                    String b = durat[1];


                    hourtemp = Integer.parseInt(hour)*60;
                    mintemp = Integer.parseInt(a)+hourtemp;
                    sum = sum+mintemp;
                }

                int hours = sum / 60; //since both are ints, you get an int
                int minutes = sum % 60;


                tvFlightBookingDuration.setText(hours+"h "+minutes+"m");
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION_TIME,hours+"h "+minutes+"m");
            }
            //*********set flight count onward*****************
            if ( mdata.getFlightSegments().size()>1){
                tv_flight_booking_stop.setText(mdata.getFlightSegments().size()-1 +" Stop");

            }
            else  if (String.valueOf(mdata.getFlightSegments().get(0).getIntNumStops()).equals("null")){
                tv_flight_booking_stop.setText("Non Stop");
            }
            else {
                tv_flight_booking_stop.setText(String.valueOf(mdata.getFlightSegments().get(0).getIntNumStops())+"Stop");
            }


            //////////////****************************RETURN**************************

            lreturn_detail_popup.setVisibility(View.VISIBLE);
            Picasso.get().load("http://webapi.i2space.co.in/"+mdataReturn.getFlightSegments().get(0).getImagePath()).into(image_flight_detail_return);
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_IMAGE,"http://webapi.i2space.co.in/"+mdataReturn.getFlightSegments().get(0).getImagePath());

            tv_flightbooking_name_return.setText(mdataReturn.getFlightSegments().get(0).getAirLineName()+" " +mdataReturn.getFlightSegments().get(0).getOperatingAirlineCode()+" "+mdataReturn.getFlightSegments().get(0).getOperatingAirlineFlightNumber());
            tv_flightbooking_classname_return.setText(TravelClass);
            tv_flight_booking_source_name_return.setText(mdataReturn.getFlightSegments().get(0).getDepartureAirportCode());
            tv_flight_detail_desti_name_return.setText(Source);

            //************set flight time***********
            int flightStopReturn =   mdataReturn.getFlightSegments().size();
            String DateReturn, TimeReturn, hr, mr;
            String depTimeReturn = mdataReturn.getFlightSegments().get(0).getDepartureDateTime();
            String[] separatedReturn = depTimeReturn.split("T");
            DateReturn = separatedReturn[0];
            TimeReturn = separatedReturn[1];
            String[] separatedTimeReturn = TimeReturn.split(":");
            hr = separatedTimeReturn[0];
            mr = separatedTimeReturn[1];
            tv_flight_booking_dep_time_return.setText(hr + ":" + mr);
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME_RETURN,h + ":" + m);
//date departure
            String[] septt = DateReturn.split("-");
            String ydd = septt[0];
            String ddd = septt[1];
            String modd = septt[2];

            String dateZonee= ddd+"/"+modd+"/"+ydd;
            Date  datee = new Date(dateZonee);
            s  = DateFormat.format("dd-MMM-yyyy", datee.getTime());
            String daymonthYearReturn = (String) s;
            String[] sepp = daymonthYearReturn.split("-");
            String dr = sepp[0];
            String moo = sepp[1];
            String yy = sepp[2];
            SimpleDateFormat simpledateformatReturn = new SimpleDateFormat("EEE");
            String dayOfWeekReturn = simpledateformatReturn.format(datee);
            tv_flight_booking_dep_date_return.setText(dayOfWeekReturn+", "+dr+moo+" "+yy);
            String dayofWeekr = dayOfWeekReturn+", "+dr+moo+" "+yy;
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_START_JOURNEY_RETURN,dayofWeekr);

            if (flightStopReturn>1){
                String arrTime = mdataReturn.getFlightSegments().get(flightStopReturn-1).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                DateReturn = arrTimeseparated[0];
                TimeReturn = arrTimeseparated[1];
                String[] separatedArrTime = TimeReturn.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tv_flight_booking_arrival_time_return.setText(h + ":" + m);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME_RETURN,h + ":" + m);

                String[] septtt = DateReturn.split("-");
                String yddd = septtt[0];
                String moddd = septtt[1];
                String dddd = septtt[2];

                String dateZoneee= moddd+"/"+dddd+"/"+yddd;
                Date  dateee = new Date(dateZoneee);
                s  = DateFormat.format("dd-MMM-yyyy", dateee.getTime());
                String daymonthYear1 = (String) s;
                String[] sep1 = daymonthYear1.split("-");
                String da = sep1[0];
                String mon = sep1[1];
                String ye = sep1[2];

                SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
                String day_Week = sdateformat.format(datee);
                tv_flight_detail_desti_date_return.setText(day_Week+", "+da+mon+" "+ye);

            }
            else {
                String arrTime = mdataReturn.getFlightSegments().get(0).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                DateReturn = arrTimeseparated[0];
                TimeReturn = arrTimeseparated[1];
                String[] separatedArrTime = TimeReturn.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tv_flight_booking_arrival_time_return.setText(h + ":" + m);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME_RETURN,h + ":" + m);

                String[] septtt = DateReturn.split("-");
                String yddd = septtt[0];
                String moddd = septtt[1];
                String dddd = septtt[2];

                String dateZoneee= moddd+"/"+dddd+"/"+yddd;
                Date  dateee = new Date(dateZoneee);
                s  = DateFormat.format("dd-MMM-yyyy", dateee.getTime());
                String daymonthYear1 = (String) s;
                String[] sep1 = daymonthYear1.split("-");
                String da = sep1[0];
                String mon = sep1[1];
                String ye = sep1[2];

                SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
                String day_Week = sdateformat.format(datee);
                tv_flight_detail_desti_date_return.setText(day_Week+", "+da+mon+" "+ye);
            }

            //*****************calculate total hour & minute**************

              /* sum = 0;
             mintemp = 0;
            hourtemp = 0;*/
            if (flightStopReturn>0){
                for (int i = 0; i<mdataReturn.getFlightSegments().size();i++){
                    String duration [] = mdataReturn.getFlightSegments().get(i).getDuration().split(":");
                    String hour = duration[0];
                    String min = duration[1];
                    String durat [] = min.split(" ");
                    String a = durat[0];
                    String b = durat[1];


                    hourtemp = Integer.parseInt(hour)*60;
                    mintemp = Integer.parseInt(a)+hourtemp;
                    sum = sum+mintemp;
                }

                int hours = sum / 60; //since both are ints, you get an int
                int minutes = sum % 60;


                tv_flight_booking_duration_return.setText(hours+"h "+minutes+"m");
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION_TIME_RETURN,hours+"h "+minutes+"m");
            }
            //*********set flight count*****************
            if ( mdataReturn.getFlightSegments().size()>1){
                tv_flight_booking_stop_return.setText(mdataReturn.getFlightSegments().size()-1 +" Stop");

            }
            else  if (String.valueOf(mdataReturn.getFlightSegments().get(0).getIntNumStops()).equals("null")){
                tv_flight_booking_stop_return.setText("Non Stop");
            }
            else {
                tv_flight_booking_stop_return.setText(String.valueOf(mdataReturn.getFlightSegments().get(0).getIntNumStops())+"Stop");
            }
        } //*******************************************************Trip Type 1***************
        else if (TripType.equals("1")) {

            Picasso.get().load(FlightImage).into(image_flight_detail_pop_up);
            tv_flightbooking_name_popup.setText(mdata.getFlightSegments().get(0).getAirLineName()+" " +AirLineCode+" "+AirLineNumber);
            tvFlightBookingSourceName_popup.setText(Source);
            tvFlightBookingDestiName_popup.setText(Destination);

             total1  = (Integer.parseInt(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TOTAL_FARE))+(totalPaid));
            flightBookBtn.setText("Pay "+getResources().getString(R.string.rupees) + " " + NumberFormat.getIntegerInstance().format(total1));

            //************set flight time onward***********
            int flightStop =   mdata.getFlightSegments().size();
            String Date, Time, h, m;
            String depTime = mdata.getFlightSegments().get(0).getDepartureDateTime();
            String[] separated = depTime.split("T");
            Date = separated[0];
            Time = separated[1];
            String[] separatedTime = Time.split(":");
            h = separatedTime[0];
            m = separatedTime[1];
            tv_flight_booking_dep_time.setText(h + ":" + m);
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME,h + ":" + m);
//date departure
            String[] sept = Date.split("-");
            String yd = sept[0];
            String mod = sept[1];
            String dd = sept[2];

            String dateZone= dd+"/"+mod+"/"+yd;
            Date  date = new Date(dateZone);
            s  = DateFormat.format("dd-MMM-yyyy", date.getTime());
            String daymonthYear = (String) s;
            String[] sep = daymonthYear.split("-");
            String d = sep[0];
            String mo = sep[1];
            String y = sep[2];
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
            String dayOfWeek = simpledateformat.format(date);
            tvFlightBookingDepDate.setText(dayOfWeek+", "+d+mo+" "+y);
            String dayofWeek = dayOfWeek+", "+d+mo+" "+y;
            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_START_JOURNEY,dayofWeek);

            if (flightStop>1){
                String arrTime = mdata.getFlightSegments().get(flightStop-1).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tv_flight_booking_arrival_time.setText(h + ":" + m);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME,h + ":" + m);

                String[] septt = Date.split("-");
                String ydd = septt[0];
                String modd = septt[1];
                String ddd = septt[2];

                String dateZonee= modd+"/"+ddd+"/"+ydd;
                Date  datee = new Date(dateZonee);
                s  = DateFormat.format("dd-MMM-yyyy", datee.getTime());
                String daymonthYear1 = (String) s;
                String[] sep1 = daymonthYear1.split("-");
                String da = sep1[0];
                String mon = sep1[1];
                String ye = sep1[2];

                SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
                String day_Week = sdateformat.format(datee);
                tvFlightDetailDestiDate.setText(day_Week+", "+da+mon+" "+ye);

            }
            else {
                String arrTime = mdata.getFlightSegments().get(0).getArrivalDateTime();
                String[] arrTimeseparated = arrTime.split("T");
                Date = arrTimeseparated[0];
                Time = arrTimeseparated[1];
                String[] separatedArrTime = Time.split(":");
                h = separatedArrTime[0];
                m = separatedArrTime[1];
                tv_flight_booking_arrival_time.setText(h + ":" + m);
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME,h + ":" + m);

                String[] septt = Date.split("-");
                String ydd = septt[0];
                String modd = septt[1];
                String ddd = septt[2];

                String dateZonee= modd+"/"+ddd+"/"+ydd;
                Date  datee = new Date(dateZonee);
                s  = DateFormat.format("dd-MMM-yyyy", datee.getTime());
                String daymonthYear1 = (String) s;
                String[] sep1 = daymonthYear1.split("-");
                String da = sep1[0];
                String mon = sep1[1];
                String ye = sep1[2];

                SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
                String day_Week = sdateformat.format(datee);
                tvFlightDetailDestiDate.setText(day_Week+", "+da+mon+" "+ye);
            }
            //*****************calculate total hour & minute onward**************

            int     sum = 0;
            int mintemp = 0;
            int hourtemp = 0;
            if (flightStop>0){
                for (int i = 0; i<mdata.getFlightSegments().size();i++){
                    String duration [] = mdata.getFlightSegments().get(i).getDuration().split(":");
                    String hour = duration[0];
                    String min = duration[1];
                    String durat [] = min.split(" ");
                    String a = durat[0];
                    String b = durat[1];


                    hourtemp = Integer.parseInt(hour)*60;
                    mintemp = Integer.parseInt(a)+hourtemp;
                    sum = sum+mintemp;
                }

                int hours = sum / 60; //since both are ints, you get an int
                int minutes = sum % 60;


                tvFlightBookingDuration.setText(hours+"h "+minutes+"m");
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION_TIME,hours+"h "+minutes+"m");
            }
            //*********set flight count onward*****************
            if ( mdata.getFlightSegments().size()>1){
                tv_flight_booking_stop.setText(mdata.getFlightSegments().size()-1 +" Stop");

            }
            else  if (String.valueOf(mdata.getFlightSegments().get(0).getIntNumStops()).equals("null")){
                tv_flight_booking_stop.setText("Non Stop");
            }
            else {
                tv_flight_booking_stop.setText(String.valueOf(mdata.getFlightSegments().get(0).getIntNumStops())+"Stop");
            }

        }


        recyclerTravellerInfo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
       /* flighPassengerBookingDialog = new FlighPassengerBookingDialog(getApplicationContext(),FlightResponse.getData().getTickets());
        recyclerTravellerInfo.setAdapter(flighPassengerBookingDialog);*/
        flightTravellersList = new FlightTravellersList(getApplicationContext(),adultList);
        recyclerTravellerInfo.setAdapter(flightTravellersList);

    }

    //****************bottom sheet dialog fragment**********************


    public static class BottomSheetFragment extends BottomSheetDialogFragment{
        TextView tvTotalBaseFare,tvTotalTaxFee,tvAirfare,tvConvenienceFee,tvTotalPaid;
        private ImageView imageCancel;

        public BottomSheetFragment() {
        }

        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view =  inflater.inflate(R.layout.flight_fare_breakup_dialog, container, false);
            setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);
            findId(view);
            return view;
        }


        private void findId(View view) {
            tvTotalBaseFare = view.findViewById(R.id.tv_total_base_fare);
            tvTotalTaxFee = view.findViewById(R.id.tv_total_tax_fee);
            tvAirfare = view.findViewById(R.id.tv_airfare);
            tvConvenienceFee = view.findViewById(R.id.tv_convenience_fee);
            tvTotalPaid = view.findViewById(R.id.tv_total_paid);
            imageCancel = view.findViewById(R.id.image_cancel);
            tvConvenienceFee.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(totalPaid))));
            if (TripType.equals("1")){
                tvTotalBaseFare.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(mdata.getFareDetails().getChargeableFares().getActualBaseFare()))));
                tvTotalTaxFee.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(mdata.getFareDetails().getChargeableFares().getTax()))));
                tvAirfare.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(mdata.getFareDetails().getTotalFare()))));
            //    tvConvenienceFee.setText(String.valueOf(mdata.getFareDetails().getChargeableFares().getConveniencefee()));
                int total = (mdata.getFareDetails().getTotalFare()+totalPaid);
                tvTotalPaid.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(total))));

            }
            else if (TripType.equals("2")){
                tvTotalBaseFare.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(mdata.getFareDetails().getChargeableFares().getActualBaseFare()+mdataReturn.getFareDetails().getChargeableFares().getActualBaseFare()))));
                tvTotalTaxFee.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(mdata.getFareDetails().getChargeableFares().getTax()+mdataReturn.getFareDetails().getChargeableFares().getTax()))));
                tvAirfare.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(mdata.getFareDetails().getTotalFare()+mdataReturn.getFareDetails().getTotalFare()))));
                int a = mdata.getFareDetails().getTotalFare();
                int b = mdataReturn.getFareDetails().getTotalFare();
                int total = (a+b+totalPaid);
                tvTotalPaid.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(String.valueOf(total))));

            }

            imageCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

    }
    private void getflightBlockTicket(final FlightBlockTicketRequest flightBlockTicketRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightBlockTicket(flightBlockTicketRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlightBlockTicketResponse>() {
                    @Override
                    public void onSuccess(FlightBlockTicketResponse flightBlockTicketResponse) {
                        loadingDialog.hideDialog();
                        FlightResponse = flightBlockTicketResponse;
                        if (flightBlockTicketResponse.isStatus()) {
                            Toast.makeText(getApplicationContext(),flightBlockTicketResponse.getMessage(),Toast.LENGTH_LONG).show();
                            showDialog(FlightPassengerDetailActivity.this);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(FlightPassengerDetailActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_passenger), false, e.getCause());
                    }
                }));
    }



}

