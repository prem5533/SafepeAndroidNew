package com.safepayu.wallet.activity.booking.flight;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.fight.FlightLocationAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.FlightDatePicker;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.booking.flight.AirportLocationResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class FlightsActivity extends AppCompatActivity implements View.OnClickListener , FlightLocationAdapter.LocationListListener {
     private TextView tvOneWay, tvTwoWay,SourceDescTV,DestinationDescTV,tvDepartureDate ,tvReturnDate,tvDepartureMonthYear,tvDepartureDay ,tvReturnMonthYear,
             tvReturnDay;
     public static TextView tvFlightTraveller,tvFlightClassType;
    private ImageView imageOneWay, imageTwoWay;
    private Button searchFlightBtn,backBtn;
    private LinearLayout liayoutClassAdult;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private LoadingDialog loadingDialog;
    private ArrayList<String> AirportCodeList,CityList,AirportDescList;
    private TextView SourceACTV,DestinationACTV;
    private CheckBox checkboxFlight;
    private FlightDatePicker datePicker;
    private LinearLayout linearFlightSource,linearFlightDestination,liDate ,liDepartureTab,liReturnTab;
    private FlightLocationAdapter flightLocationAdapter;
    private RecyclerView recyclerViewLocationList ;
    public   Dialog dialog;
    private TextView   tvSourceFlightDescName,tvSourceFlightAirportCode,tvDestinationFlightAirportCode,tvRound;
    private  SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private CharSequence s;
    Date date,tomorrow,date1;
    private String daymonthYear,d,m,y,dayOfWeek;
    SimpleDateFormat simpledateformat;
    public static TextView tvAdultsCount,tvChildrenCount,tvInfantCount,tv_done;


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        loadingDialog = new LoadingDialog(this);

        findId();

    }

    private void findId() {
        backBtn = findViewById(R.id.recharge_back_btn);
        searchFlightBtn = findViewById( R.id.search_flight_btn);
        tvOneWay = findViewById(R.id.tv_oneway);
        tvTwoWay = findViewById(R.id.tv_twoway);
        tvFlightTraveller = findViewById(R.id.tv_flight_traveller);
        imageOneWay = findViewById(R.id.image_one_way);
        imageTwoWay = findViewById(R.id.image_two_way);
        liayoutClassAdult = findViewById(R.id.layout_class_traveller_tab);
        SourceACTV= findViewById(R.id.sourceFlight);
        DestinationACTV= findViewById(R.id.destinationFlight);
        DestinationDescTV= findViewById(R.id.destinationFlightDesc);
        checkboxFlight= findViewById(R.id.checkbox_flight);
        tvDepartureDate= findViewById(R.id.tv_departure_date);
        tvReturnDate= findViewById(R.id.tv_return_date);
        linearFlightSource= findViewById(R.id.linear_flight_source);
        linearFlightDestination= findViewById(R.id.linear_flight_destination);
        tvSourceFlightDescName = findViewById(R.id.sourceFlightDesc);
        tvSourceFlightAirportCode = findViewById(R.id.source_flight_airport_code);
        tvDestinationFlightAirportCode = findViewById(R.id.destination_flight_airport_code);
        tvDepartureMonthYear = findViewById(R.id.tv_departure_month_year);
        tvDepartureDay = findViewById(R.id.tv_departure_day);
        tvRound = findViewById(R.id.tv_round);
        liDate = findViewById(R.id.lidate);
        liDepartureTab = findViewById(R.id.li_departure_tab);
        liReturnTab = findViewById(R.id.li_return_tab);
        tvReturnMonthYear = findViewById(R.id.tv_return_month_year);
        tvReturnDay = findViewById(R.id.tv_return_day);
        tvFlightClassType = findViewById(R.id.tv_flight_class_type);


        //set listener
        tvOneWay.setOnClickListener(this);
        tvTwoWay.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        searchFlightBtn.setOnClickListener(this);
        liayoutClassAdult.setOnClickListener(this);
        tvDepartureDate.setOnClickListener(this);
        tvReturnDate.setOnClickListener(this);
        linearFlightDestination.setOnClickListener(this);
        linearFlightSource.setOnClickListener(this);
        linearFlightSource.setOnClickListener(this);

        tvOneWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_rounded));
        tvOneWay.setTextColor(getResources().getColor(R.color.white));
        imageOneWay.setVisibility(View.VISIBLE);


        liDepartureTab.setOnClickListener(this);
        liDate.setOnClickListener(this);




        AirportCodeList=new ArrayList<>();
        CityList=new ArrayList<>();
        AirportDescList=new ArrayList<>();

        AirportCodeList.clear();
        CityList.clear();
        AirportDescList.clear();

        //getFlightSources("1");
     //   new GetSoureces().execute();

       /* checkboxFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(FlightsActivity.this,FlightListActivity.class);
                startActivity(intent);
            }
        });*/


        date = new Date();
        s  = DateFormat.format("dd-MMM-yyyy ", date.getTime());
        String daymonthYear = (String) s;
        String[] separated = daymonthYear.split("-");
        String d = separated[0];
        String m = separated[1];
        String y = separated[2];
        tvDepartureDate.setText(d);
        tvDepartureMonthYear.setText(m +" "+y);

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        String dayOfWeek = simpledateformat.format(date);

        tvDepartureDay.setText(dayOfWeek);


        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE,tvSourceFlightAirportCode.getText().toString());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case
                R.id.tv_oneway:
                tvOneWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_rounded));
                tvOneWay.setTextColor(getResources().getColor(R.color.white));
                tvTwoWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded));
                tvTwoWay.setTextColor(getResources().getColor(R.color.darker_gray));
                imageOneWay.setVisibility(View.VISIBLE);
                imageTwoWay.setVisibility(View.GONE);
                tvRound.setVisibility(View.VISIBLE);
                liDate.setVisibility(View.GONE);


                break;
            case  R.id.tv_twoway:
                tvTwoWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_rounded));
                tvTwoWay.setTextColor(getResources().getColor(R.color.white));
                tvOneWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded));
                tvOneWay.setTextColor(getResources().getColor(R.color.darker_gray));
                imageOneWay.setVisibility(View.GONE);
                imageTwoWay.setVisibility(View.VISIBLE);
                tvRound.setVisibility(View.GONE);
                liDate.setVisibility(View.VISIBLE);



                date = new Date();
                tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
                s  = DateFormat.format("dd-MMM-yyyy ", tomorrow.getTime());
                 daymonthYear = (String) s;
                String[] separated = daymonthYear.split("-");
                 d = separated[0];
                 m = separated[1];
                 y = separated[2];
                tvReturnDate.setText(d);
                tvReturnMonthYear.setText(m +" "+y);

                 simpledateformat = new SimpleDateFormat("EEEE");
                String dayOfWeek = simpledateformat.format(tomorrow);
                tvReturnDay.setText(dayOfWeek);

                String monthYear = tvDepartureMonthYear.getText().toString();
                String[] separate = monthYear.split(" ");
                String  month = separate[0];
                String year = separate[1];

                String s1 = tvDepartureDate.getText().toString() + "-"+month+ "-"+ year ;

                try {
                    date1=new SimpleDateFormat("dd-MMM-yyyy").parse(s1);
                    Log.e("ds", String.valueOf(date1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //convert tomorrow to string
                String strDate = (String) DateFormat.format("dd-MMM-yyyy", tomorrow);

                if ( s1.equals(strDate)){
                   tomorrowDate();
                }
                else if (date1.compareTo(tomorrow)>0){
                    tomorrowDate();
                 }

                break;
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

            case R. id.search_flight_btn:

               /* Intent intent = new Intent(FlightsActivity.this,TwoWayListActivity.class);
                startActivity(intent);*/
               CheckValidate();
                break;
            case R.id.layout_class_traveller_tab:

            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            break;
            case R.id.li_departure_tab:
                sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.putString("date", "dDate");
                editor.apply();
                 datePicker = FlightDatePicker.newInstance(null, tvDepartureDate,tvDepartureMonthYear,tvDepartureDay);
                datePicker.show(getSupportFragmentManager(), "datePicker");


                break;
            case R.id.lidate:
                sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                 editor = sharedPref.edit();
                editor.putString("date", "rDate");
                editor.apply();
                datePicker = FlightDatePicker.newInstance(null, tvReturnDate,tvReturnMonthYear,tvReturnDay);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.linear_flight_destination:

               sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
               editor = sharedPref.edit();
                editor.putString("value", "To");
                editor.apply();
                showDialog(FlightsActivity.this);
                break;
            case R.id.linear_flight_source:
                sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.putString("value", "From");
                editor.apply();
                showDialog(FlightsActivity.this);
                break;
        }
    }


    private void CheckValidate() {

        //*********split travellers count

        String tc = "";
        String travellersClass = tvFlightClassType.getText().toString();
        if (travellersClass.equals("Economy")){
            tc = "E";
        }
        else  if (travellersClass.equals("Preminum Economy")){
            tc = "PE";
        }
        else  if (travellersClass.equals("Business")){
            tc = "B";
        }else  if (travellersClass.equals("First Class")){
            tc = "F";
        }
        
        //*************************************************************
      //  BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_flight),"Coming Soon",false);
        Intent intent = new Intent(FlightsActivity.this,FlightListActivity.class);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE,tvSourceFlightAirportCode.getText().toString());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION,tvDestinationFlightAirportCode.getText().toString());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_JOURNEY_DATE,"01-01-2020");
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE,"1");
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_USER,"");
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_USER_TYPE,"5");
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_ADULTS,tvAdultsCount.getText().toString());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_INFANTS,tvInfantCount.getText().toString());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_CHILDREN,tvChildrenCount.getText().toString());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_TYPE,"1");
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_DATE,"");
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS,tc);
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_COUNT,tvFlightTraveller.getText().toString());
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().FLIGHT_TRAVELLERS_CLASS_TYPE,travellersClass);

        startActivity(intent);
    }


    @Override
    public void onLocationClickFrom(int position, AirportLocationResponse.DataBean mLocationResponse) {
        dialog.dismiss();
        tvSourceFlightDescName.setText(mLocationResponse.getAirportDesc());
        tvSourceFlightAirportCode.setText(mLocationResponse.getAirportCode());
    }

    @Override
    public void onLocationClickTo(int position, AirportLocationResponse.DataBean mLocationResponse) {
        dialog.dismiss();
        DestinationDescTV.setText(mLocationResponse.getAirportDesc());
        tvDestinationFlightAirportCode.setText(mLocationResponse.getAirportCode());
    }


    //****************bottom sheet dialog fragment**********************


    public static class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

        private ImageView imageAdultsMinus,imageAdultsPlus,imageAdultsMinusgray,imageChildrenMinus,imageChildrenMinusgray,imageChildrenPlus,imageInfantMinus,imageInfantMinusgray
                ,imageInfantPlus,imageAdultsPlusgray,imageChildrenPlusgray,imageInfantPlusgray;
        private RadioGroup radioGroup;

        int minteger = 1;
        int mintegerChild = 0;
        int mintegerInfant = 0;
        int mtotalTravellers;

        public BottomSheetFragment() {
        }

        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view =  inflater.inflate(R.layout.flight_bottom_popup, container, false);
            setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);

            findId(view);
            return view;
        }

        private void findId(View view) {

            tvAdultsCount =view. findViewById(R.id.tvAdultsCount);
            tvChildrenCount = view.findViewById(R.id.tvChildrenCount);
            tvInfantCount = view.findViewById(R.id.tvInfantCount);
            tv_done = view.findViewById(R.id.tv_done);
            imageAdultsMinus = view.findViewById(R.id.imageAdultsMinus);
            imageAdultsMinusgray = view.findViewById(R.id.imageAdultsMinusgray);
            imageAdultsPlus = view.findViewById(R.id.imageAdultsPlus);
            imageChildrenMinus = view.findViewById(R.id.imageChildrenMinus);
            imageChildrenMinusgray = view.findViewById(R.id.imageChildrenMinusgray);
            imageChildrenPlus = view.findViewById(R.id.imageChildrenPlus);
            imageInfantMinus = view.findViewById(R.id.imageInfantMinus);
            imageInfantMinusgray = view.findViewById(R.id.imageInfantMinusgray);
            imageInfantPlus = view.findViewById(R.id.imageInfantPlus);
            imageAdultsPlusgray = view.findViewById(R.id.imageAdultsPlusgray);
            imageChildrenPlusgray = view.findViewById(R.id.imageChildrenPlusgray);
            imageInfantPlusgray = view.findViewById(R.id.imageInfantPlusgray);
            radioGroup = view.findViewById(R.id.radio_group_class);


            imageAdultsMinus.setOnClickListener(this);
            imageAdultsPlus.setOnClickListener(this);
            imageChildrenMinus.setOnClickListener(this);
            imageChildrenPlus.setOnClickListener(this);
            imageInfantPlus.setOnClickListener(this);
            imageInfantMinus.setOnClickListener(this);
            tv_done.setOnClickListener(this);

            tvInfantCount.setText("" +mintegerInfant);
            tvChildrenCount.setText("" + mintegerChild);



        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.imageAdultsMinus:
                    if (minteger==1)
                    {
                        tvAdultsCount.setText("" + 0);
                        imageAdultsMinusgray.setVisibility(View.VISIBLE);
                        imageAdultsMinus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        minteger = minteger - 1;
                        display(minteger);
                        if(minteger==1){
                            imageAdultsMinusgray.setVisibility(View.VISIBLE);
                            imageAdultsMinus.setVisibility(View.INVISIBLE); }
                        else {
                            imageAdultsMinusgray.setVisibility(View.INVISIBLE);
                            imageAdultsMinus.setVisibility(View.VISIBLE);
                        }
                    }
                    break;

                case R.id.imageAdultsPlus:
                    minteger = minteger + 1;
                    display(minteger);
                    tvAdultsCount.setText("" + minteger);
                    imageAdultsMinusgray.setVisibility(View.INVISIBLE);
                    imageAdultsMinus.setVisibility(View.VISIBLE);
                    break;

                case R.id.imageChildrenMinus:
                    if (mintegerChild==0)
                    {
                        tvChildrenCount.setText("" + 0);
                        imageChildrenMinusgray.setVisibility(View.VISIBLE);
                        imageChildrenMinus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mintegerChild = mintegerChild - 1;
                        displayChildren(mintegerChild);


                        if(mintegerChild==0){
                            imageChildrenMinusgray.setVisibility(View.VISIBLE);
                            imageChildrenMinus.setVisibility(View.INVISIBLE); }
                        else {
                            imageChildrenMinusgray.setVisibility(View.INVISIBLE);
                            imageChildrenMinus.setVisibility(View.VISIBLE);
                        }
                    }
                    break;

                case R.id.imageChildrenPlus:
                    mintegerChild = mintegerChild + 1;
                    displayChildren(mintegerChild);
                    tvChildrenCount.setText("" + mintegerChild);
                    imageChildrenMinusgray.setVisibility(View.INVISIBLE);
                    imageChildrenMinus.setVisibility(View.VISIBLE);
                    break;
                case R.id.imageInfantMinus:
                    if (mintegerInfant==0)
                    {
                        tvInfantCount.setText("" + 0);
                        imageInfantMinusgray.setVisibility(View.VISIBLE);
                        imageInfantMinus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mintegerInfant = mintegerInfant - 1;
                        displayInfant(mintegerInfant);
                        if(mintegerInfant==0){
                            imageInfantMinusgray.setVisibility(View.VISIBLE);
                            imageInfantMinus.setVisibility(View.INVISIBLE); }
                        else {
                            imageInfantMinusgray.setVisibility(View.INVISIBLE);
                            imageInfantMinus.setVisibility(View.VISIBLE);
                        }
                    }
                    break;

                case R.id.imageInfantPlus:
                    mintegerInfant = mintegerInfant + 1;
                    displayInfant(mintegerInfant);
                    tvInfantCount.setText("" + mintegerInfant);
                    imageInfantMinusgray.setVisibility(View.INVISIBLE);
                    imageInfantMinus.setVisibility(View.VISIBLE);
                    break;

                case R.id.tv_done:

                    //**********travellers count **************
                    String adults = tvAdultsCount.getText().toString();
                    String infant = tvInfantCount.getText().toString();
                    String child = tvChildrenCount.getText().toString();

                    if (mintegerInfant==0 && mintegerChild==0){
                     tvInfantCount.setText("");
                     tvChildrenCount.setText("");
                        if (adults.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+tvChildrenCount.getText().toString()+ tvInfantCount.getText().toString()); }
                        else {
                            tvFlightTraveller.setText(adults+" "+"Adults"+tvChildrenCount.getText().toString()+ tvInfantCount.getText().toString()); }
                    }

                    else if (mintegerChild==0 && mintegerInfant!=0 ){
                        tvChildrenCount.setText("");
                        if (adults.equals("1") && infant.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+", "+tvChildrenCount.getText().toString()+ tvInfantCount.getText().toString()+" "+"Infant");
                        }
                        else if (infant.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+ tvInfantCount.getText().toString()+" "+"Infant");
                        }
                        else if (adults.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+", "+tvChildrenCount.getText().toString()+ tvInfantCount.getText().toString()+" "+"Infants");
                        }
                        else {
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+ tvInfantCount.getText().toString()+" "+"Infants");
                        }
                    }

                    else if (mintegerChild!=0 && mintegerInfant==0 ){
                        tvInfantCount.setText("");
                        if (adults.equals("1") && child.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+", "+tvChildrenCount.getText().toString()+" "+"Child"+" "+tvInfantCount.getText().toString()); }

                        else if (child.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+" "+"Child"+" "+tvInfantCount.getText().toString()); }

                        else if (adults.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+", "+tvChildrenCount.getText().toString()+" "+"Children"+" "+tvInfantCount.getText().toString()); }
                        else {
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+" "+"Children"+ tvInfantCount.getText().toString());
                        }
                    }

                    else if (mintegerChild!=0 && mintegerInfant!=0 ){
                        if (adults.equals("1") && child.equals("1") && infant.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+", "+tvChildrenCount.getText().toString()+" "+"Child"+", "+tvInfantCount.getText().toString()+" "+"Infant"); }

                        else if (child.equals("1")&&infant.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+" "+"Child"+", "+tvInfantCount.getText().toString()+" "+"Infant"); }

                        else if (adults.equals("1")&&infant.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+", "+tvChildrenCount.getText().toString()+" "+"Children"+", "+tvInfantCount.getText().toString()+" "+"Infant"); }

                       else if (adults.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adult"+", "+tvChildrenCount.getText().toString()+" "+"Children"+", "+tvInfantCount.getText().toString()+" "+"Infants"); }

                        else if (child.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+" "+"Child"+", "+tvInfantCount.getText().toString()+" "+"Infants"); }

                        else if (infant.equals("1")){
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+" "+"Children"+", "+tvInfantCount.getText().toString()+" "+"Infant"); }

                        else {
                            tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+" "+"Children"+", "+tvInfantCount.getText().toString()+" "+"Infants"); }

                    }
                    else {
                        tvFlightTraveller.setText(adults+" "+"Adults"+", "+tvChildrenCount.getText().toString()+" "+"Children"+ tvInfantCount.getText().toString()+" "+"Infants");
                    }




                  //************cabin class*************************
                    int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
                    // If nothing is selected from Radio Group, then it return -1
                    if (selectedRadioButtonID != -1) {
                        RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonID);
                        String selectedRadioButtonText = selectedRadioButton.getText().toString();
                        tvFlightClassType.setText(selectedRadioButtonText );
                    } else{
                        Toast.makeText(getActivity(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();

                    }



                    dismiss();
                    break;
            }
        }




        private void display(int intAdult) {

            mtotalTravellers = minteger+mintegerChild+mintegerInfant;
            if (mtotalTravellers>8)
            {
                Toast.makeText(getActivity(),"Travellers can not more than 9 ",Toast.LENGTH_LONG).show();
                imageInfantPlus.setVisibility(View.INVISIBLE);
                imageChildrenPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlusgray.setVisibility(View.VISIBLE);
                imageChildrenPlusgray.setVisibility(View.VISIBLE);
                imageInfantPlusgray.setVisibility(View.VISIBLE);
            }
            else if (mtotalTravellers<=9){
                tvAdultsCount.setText("" + intAdult);
                imageInfantPlus.setVisibility(View.VISIBLE);
                imageChildrenPlus.setVisibility(View.VISIBLE);
                imageAdultsPlus.setVisibility(View.VISIBLE);
                imageAdultsPlusgray.setVisibility(View.INVISIBLE);
                imageChildrenPlusgray.setVisibility(View.INVISIBLE);
                imageInfantPlusgray.setVisibility(View.INVISIBLE);


                if (intAdult==1)
                {
                    imageAdultsMinusgray.setVisibility(View.VISIBLE);
                    imageAdultsMinus.setVisibility(View.INVISIBLE);

                }
            }

        }

        private void displayChildren(int intChild) {

            mtotalTravellers = minteger+mintegerChild+mintegerInfant;

            if (mtotalTravellers>8)
            {
                Toast.makeText(getActivity(),"Travellers can not more than 9 ",Toast.LENGTH_SHORT).show();
                imageInfantPlus.setVisibility(View.INVISIBLE);
                imageChildrenPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlusgray.setVisibility(View.VISIBLE);
                imageChildrenPlusgray.setVisibility(View.VISIBLE);
                imageInfantPlusgray.setVisibility(View.VISIBLE);
            }
            else if (mtotalTravellers<=9){
                tvChildrenCount.setText("" + intChild);
                imageInfantPlus.setVisibility(View.VISIBLE);
                imageChildrenPlus.setVisibility(View.VISIBLE);
                imageAdultsPlus.setVisibility(View.VISIBLE);
                imageAdultsPlusgray.setVisibility(View.INVISIBLE);
                imageChildrenPlusgray.setVisibility(View.INVISIBLE);
                imageInfantPlusgray.setVisibility(View.INVISIBLE);
                if (intChild == 0) {
                    imageChildrenMinusgray.setVisibility(View.VISIBLE);
                    imageChildrenMinus.setVisibility(View.INVISIBLE); }
            }
        }

        private void displayInfant(int intInfant) {

            mtotalTravellers = minteger+mintegerChild+mintegerInfant;
            if (mtotalTravellers>8)
            {
                Toast.makeText(getActivity(),"Travellers can not more than 9 ",Toast.LENGTH_SHORT).show();
                imageInfantPlus.setVisibility(View.INVISIBLE);
                imageChildrenPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlusgray.setVisibility(View.VISIBLE);
                imageChildrenPlusgray.setVisibility(View.VISIBLE);
                imageInfantPlusgray.setVisibility(View.VISIBLE);
            }
            else if (mtotalTravellers<=9){
                tvInfantCount.setText("" + intInfant);
                imageInfantPlus.setVisibility(View.VISIBLE);
                imageChildrenPlus.setVisibility(View.VISIBLE);
                imageAdultsPlus.setVisibility(View.VISIBLE);
                imageAdultsPlusgray.setVisibility(View.INVISIBLE);
                imageChildrenPlusgray.setVisibility(View.INVISIBLE);
                imageInfantPlusgray.setVisibility(View.INVISIBLE);


                if (intInfant == 0) {
                    imageInfantMinusgray.setVisibility(View.VISIBLE);
                    imageInfantMinus.setVisibility(View.INVISIBLE);

                }
            }
        }

    }

    public class GetSoureces extends AsyncTask<Object, Void, Object> {
        public ProgressDialog dialog;
        int status_code = 0;
        private String response = "", actualString = "";
        private  String ConsumerKey="CF1AA97C156A138796A39CA8E7CFDEB3D17C8D60";
        private String ConsumerSecret="7FAEC265BC38BA83F21D23C88A1C416F2820D0B1";

        @Override
        protected Object doInBackground(Object... arg0) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                String URL = "http://webapi.i2space.co.in/Flights/Airports?flightType=1";
                //HttpPost httpost = new HttpPost(URL);
                HttpGet httpGet = new HttpGet(URL);

                httpGet.addHeader("ConsumerKey",ConsumerKey);
                httpGet.addHeader("ConsumerSecret",ConsumerSecret);

                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters,
                        timeoutConnection);
                int timeoutSocket = 3000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                HttpContext localContext = new BasicHttpContext();
                HttpResponse httpresponse = httpClient.execute(httpGet, localContext);
                status_code = httpresponse.getStatusLine().getStatusCode();
                HttpEntity entity = httpresponse.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(
                        stream));

                String s = "";

                while ((s = buffer.readLine()) != null) {
                    response += s;
                }


                actualString = response;

                if (response.startsWith("null")) {
                    actualString = response;
                }


            } catch (Exception ex) {
                String msg = ex.getMessage();
            }
            return actualString;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(FlightsActivity.this, "Getting Sources...",
                    "Please wait...", true);
        }

        @Override
        protected void onPostExecute(Object result) {

            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            dialog.dismiss();
            if (status_code == 200) {
                try {
                    jsonArray = new JSONArray(response);
                    Log.v("response",response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonArray.length()>0){
                              for (int i=0;i<jsonArray.length();i++){
                                  try {
                                      jsonObject=jsonArray.getJSONObject(i);
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                                  try {
                                      AirportCodeList.add(jsonObject.getString("AirportCode"));
                                      CityList.add(jsonObject.getString("City"));
                                      AirportDescList.add(jsonObject.getString("AirportDesc"));
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }

                              }

                 /*   ArrayAdapter<String> adapter = new ArrayAdapter<String>(FlightsActivity.this, android.R.layout.simple_dropdown_item_1line, CityList);
                    SourceACTV.setAdapter(adapter);
                    SourceACTV.setThreshold(3);
                    DestinationACTV.setAdapter(adapter);
                    DestinationACTV.setThreshold(3);*/

                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_flight),"No Data Found",true);
                }

            }

        }
    }


    private void showDialog(FlightsActivity flightsActivity) {

        dialog = new Dialog(flightsActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_flight_location_list);

        recyclerViewLocationList = dialog.findViewById(R.id.airport_locaiton_list);




        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();



        getAirportLocation();
    }

    private void getAirportLocation() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getAirportLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AirportLocationResponse>() {
                    @Override
                    public void onSuccess(AirportLocationResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                recyclerViewLocationList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                flightLocationAdapter = new FlightLocationAdapter(getApplicationContext(), response.getData(),FlightsActivity.this);
                                recyclerViewLocationList.setAdapter(flightLocationAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.flight_location_layout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_location_layout), false, e.getCause());
                    }
                }));

    }

    //**************Tomorrow Date******************//
    private void tomorrowDate() {
        tomorrow = new Date(date1.getTime() + (1000 * 60 * 60 * 24));
        s  = DateFormat.format("dd-MMM-yyyy ", tomorrow.getTime());
        daymonthYear = (String) s;
        String[] separated1 = daymonthYear.split("-");
        d = separated1[0];
        m = separated1[1];
        y = separated1[2];
        tvReturnDate.setText(d);
        tvReturnMonthYear.setText(m +" "+y);

        simpledateformat = new SimpleDateFormat("EEEE");
        dayOfWeek = simpledateformat.format(tomorrow);
        tvReturnDay.setText(dayOfWeek);
    }


}
