package com.safepayu.wallet.activity.booking.flight;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.FlighPassengerBookingDialog;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking.flight.FlightBookingDetailRequest;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.safepayu.wallet.models.response.booking.flight.CancelBookTicketResponse;
import com.safepayu.wallet.models.response.booking.flight.FlighPdfResponse;
import com.safepayu.wallet.models.response.booking.flight.FlightBookingDetailResponse;
import com.safepayu.wallet.utils.pdf.DownloadTask;
import com.safepayu.wallet.utils.pdf.MarshMallowPermission;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;

public class FlightBookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBookingStatus, tvSafeJourney,tvFlightRefrenceNo,tvFlightTimeDate,tvFlightSourceDestination,tvFlightEticketNo,tvFlightCancelTicket,tvTicketTimeDateTraveller,
    tvTicketSource,tvTicketDestination,tvTicketSourceTime,tvTicketTotalTime,tvTicketDestinationTime,tvTicketSourceAirportname,tvTicketDetinationAirportname,tvFlightNumber,tvFlightCode,
            tvFlightSourceDestinationReturn,tvFlightCodeReturn,tvFlightNumberReturn,tvTicketTimeDateTravellerReturn,tvTicketSourceReturn,tvTicketDestinationReturn,tvTicketTotalTimeReturn,
            tvTicketSourceAirportnameReturn,tvTicketDetinationAirportnameReturn,tvTicketSourceTimeReturn,tvTicketDestinationTimeReturn,tvDownloadTicket;
    private LoadingDialog loadingDialog;
    FlightBookingDetailRequest flightBookingDetailRequest;
    private String ReferanceNo ="",Source,Destination,JourneyDate,DepTime,ArrivalTime,DurationTime,AirLineCode,AirLineNumber,FlightImage,TotalTravellers,TripType,
            DurationTimeReturn,FlightDepTimeReturn,FlightArrivalTimeReturn,FlightImageReturn,FlightImageOnward,FlightJourneyReturn;
    private LinearLayout liFlightName,lretunrticket,liFlightNameReturn;
    private ImageView imFlightLogo,imFlightLogoReturn,imShare;
    private GifImageView statusImage;
    private RecyclerView travellerListName;
    private FlighPassengerBookingDialog flighPassengerBookingDialog;
    private Button backBtn;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String json;
    Gson gson;

    public  static AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean mdata;
    public  static AvailableFlightResponse.DataBean.DomesticReturnFlightsBean mdataReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_book_detail);
        findId();
        if (isNetworkAvailable()){

            getFlightBookingDetails();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
        }

    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);
        backBtn = findViewById(R.id._back_btn);
        tvBookingStatus = findViewById(R.id.tv_booking_status);
        tvSafeJourney = findViewById(R.id.tv_safe_journey);
        tvFlightRefrenceNo = findViewById(R.id.tv_flight_refrence_no);
        tvFlightTimeDate = findViewById(R.id.tv_flight_time_date);
        tvFlightSourceDestination = findViewById(R.id.tv_flight_source_destination);
        tvFlightEticketNo = findViewById(R.id.tv_flight_eticket_no);
        tvFlightCancelTicket = findViewById(R.id.tv_flight_cancel_ticket);
        tvTicketTimeDateTraveller = findViewById(R.id.tv_ticket_time_date_traveller);
        tvTicketSource = findViewById(R.id.tv_ticket_source);
        tvTicketDestination = findViewById(R.id.tv_ticket_destination);
        tvTicketSourceTime = findViewById(R.id.tv_ticket_source_time);
        tvTicketTotalTime = findViewById(R.id.tv_ticket_total_time);
        tvTicketDestinationTime = findViewById(R.id.tv_ticket_destination_time);
        tvTicketSourceAirportname = findViewById(R.id.tv_ticket_source_airportname);
        tvTicketDetinationAirportname = findViewById(R.id.tv_ticket_detination_airportname);
        tvTicketSourceAirportnameReturn = findViewById(R.id.tv_ticket_source_airportname_return);
        tvTicketDetinationAirportnameReturn = findViewById(R.id.tv_ticket_detination_airportname_return);
        tvDownloadTicket = findViewById(R.id.tv_flight_download_ticket);
        imShare = findViewById(R.id.im_share);
        backBtn.setOnClickListener(this);

        //RETURN
        tvFlightSourceDestinationReturn = findViewById(R.id.tv_flight_source_destination_return);
        tvFlightCodeReturn = findViewById(R.id.tv_flight_code_return);
        tvFlightNumberReturn = findViewById(R.id.tv_flight_number_return);
        tvTicketTimeDateTravellerReturn = findViewById(R.id.tv_ticket_time_date_traveller_return);
        tvTicketSourceReturn = findViewById(R.id.tv_ticket_source_return);
        tvTicketDestinationReturn = findViewById(R.id.tv_ticket_destination_return);
        tvTicketTotalTimeReturn = findViewById(R.id.tv_ticket_total_time_return);
        tvTicketSourceTimeReturn = findViewById(R.id.tv_ticket_source_time_return);
        tvTicketDestinationTimeReturn = findViewById(R.id.tv_ticket_destination_time_return);


        lretunrticket = findViewById(R.id.lretunrticket);
        travellerListName = findViewById(R.id.list_traveller_details);
        tvFlightCode = findViewById(R.id.tv_flight_code);
        tvFlightNumber = findViewById(R.id.tv_flight_number);
        imFlightLogo = findViewById(R.id.im_flight_logo);
        imFlightLogoReturn = findViewById(R.id.im_flight_logo_return);
        liFlightName = findViewById(R.id.li_flight_name);
        liFlightNameReturn = findViewById(R.id.li_flight_name_return);
        statusImage = findViewById(R.id.statusImage);
        tvFlightCancelTicket.setOnClickListener(this);
        imShare.setOnClickListener(this);
        tvDownloadTicket.setOnClickListener(this);
     //   backBtn.setOnClickListener(this);

        //***************get data****************
        Source =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        ReferanceNo = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_REFERENCE_NO);
        JourneyDate = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_START_JOURNEY);
        DepTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME);
        ArrivalTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME);
        DurationTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION_TIME);
        DurationTimeReturn = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION_TIME_RETURN);
        AirLineCode =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_CODE);
        AirLineNumber =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_FLIGHT_NUMBER);
        FlightImage = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        FlightImageReturn = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_IMAGE);
        FlightImageOnward = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ONWARD_IMAGE);
        TotalTravellers = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().TotalTravellers);
        TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
        FlightDepTimeReturn =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME_RETURN);
        FlightArrivalTimeReturn =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME_RETURN);
        FlightJourneyReturn =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_START_JOURNEY_RETURN);


       if (TripType.equals("2")){
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            gson = new Gson();
            json = prefs.getString("MyObjectOnward", "");
            mdata = gson.fromJson(json, AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.class);

            json = prefs.getString("MyObjectReturn", "");
            mdataReturn = gson.fromJson(json,AvailableFlightResponse.DataBean.DomesticReturnFlightsBean.class);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getFlightBookingDetails() {

        flightBookingDetailRequest = new FlightBookingDetailRequest();
      //  flightBookingDetailRequest.setReferenceNo("300905016582");
       flightBookingDetailRequest.setReferenceNo("300344016590");
       // flightBookingDetailRequest.setReferenceNo(ReferanceNo);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightBookingDetails(flightBookingDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlightBookingDetailResponse>(){
                    @Override
                    public void onSuccess(FlightBookingDetailResponse flightBookingDetailResponse) {
                        loadingDialog.hideDialog();
                        int bs = flightBookingDetailResponse.getData().getBookingStatus();
                        tvFlightEticketNo.setText("PNR - " + flightBookingDetailResponse.getData().getAPIRefNo());
                        tvTicketSourceAirportname.setText(flightBookingDetailResponse.getData().getSourceName());
                        tvTicketDetinationAirportname.setText(flightBookingDetailResponse.getData().getDestinationName());
                        tvTicketTotalTime.setText(DurationTime);
                        if (flightBookingDetailResponse.getData().getBookingStatus() == 3) {
                            tvBookingStatus.setText("Booking Successful");
                            tvSafeJourney.setText("Have a safe journey!");
                            tvFlightRefrenceNo.setText(flightBookingDetailResponse.getData().getBookingRefNo());

                            String DateTime =flightBookingDetailResponse.getData().getBookingDate();
                            String DT[] = DateTime.split("T");
                            String D = DT[0];
                            String T = DT[1];
                            tvFlightTimeDate.setText(D+" , "+T);

                        } else if (bs == 5) {
                            statusImage.setVisibility(View.GONE);
                            tvBookingStatus.setText("Booking Cancel");
                            tvSafeJourney.setText("Ticket cancelled successfully!");
                            tvFlightCancelTicket.setVisibility(View.GONE);

                        }
                        if (TripType.equals("1")) {
                            lretunrticket.setVisibility(View.GONE);


                            tvFlightSourceDestination.setText(Source + " - " + Destination);
                            tvTicketTimeDateTraveller.setText(JourneyDate + " | " + TotalTravellers + " Traveller");
                            // tvTicketTimeDateTraveller.setText(JourneyDate+" | "+"" + " Traveller");
                            tvTicketSource.setText(Source);
                            tvTicketDestination.setText(Destination);
                            tvTicketSourceTime.setText(DepTime);
                            tvTicketDestinationTime.setText(ArrivalTime);

                            tvFlightCode.setText(AirLineCode);
                            tvFlightNumber.setText(AirLineNumber);
                            Picasso.get().load(FlightImage).into(imFlightLogo);

                            if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("Indigo")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.indigo));
                            }

                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("Air India")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.airindia));
                            }
                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("SpiceJet")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.spicejet));
                            }
                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("GoAir")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.goair));
                            }
                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("Vistara")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.vistara));
                            }
                            else {
                                liFlightName.setBackgroundColor(Color.parseColor("#ffffff"));
                            }

                        }
                        else if (TripType.equals("2")){

                            lretunrticket.setVisibility(View.VISIBLE);
                            tvFlightSourceDestination.setText(flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getDepartureAirportCode() + " - " +flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getArrivalAirportCode());
                            tvFlightSourceDestinationReturn.setText(flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getDepartureAirportCode() + " - " +flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getArrivalAirportCode());
                            Picasso.get().load(FlightImageOnward).into(imFlightLogo);
                            Picasso.get().load(FlightImageReturn).into(imFlightLogoReturn);
                            tvFlightCode.setText(flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getOperatingAirlineCode());
                            tvFlightCodeReturn.setText(flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getImageFileName());
                            tvFlightNumber.setText(flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getOperatingAirlineFlightNumber());
                            tvFlightNumberReturn.setText(flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getOperatingAirlineFlightNumber());
                            tvTicketSource.setText(flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getDepartureAirportCode());
                            tvTicketDestination.setText(  flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getArrivalAirportCode());
                            tvTicketSourceReturn.setText(  flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getDepartureAirportCode());
                            tvTicketDestinationReturn.setText(flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getArrivalAirportCode());
                            tvTicketDetinationAirportnameReturn.setText(flightBookingDetailResponse.getData().getSourceName());
                            tvTicketSourceAirportnameReturn.setText(flightBookingDetailResponse.getData().getDestinationName());
                            tvTicketTotalTimeReturn.setText(DurationTimeReturn);
                            tvTicketSourceTimeReturn.setText(FlightDepTimeReturn);
                            tvTicketDestinationTimeReturn.setText(FlightArrivalTimeReturn);
                            tvTicketSourceTime.setText(DepTime);
                            tvTicketDestinationTime.setText(ArrivalTime);
                            tvTicketTimeDateTraveller.setText(JourneyDate + " | " + TotalTravellers + " Traveller");
                            tvTicketTimeDateTravellerReturn.setText(FlightJourneyReturn + " | " + TotalTravellers + " Traveller");

                            if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("Indigo")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.indigo));
                            }

                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("Air India")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.airindia));
                            }
                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("SpiceJet")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.spicejet));
                            }
                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("GoAir")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.goair));
                            }
                            else  if (flightBookingDetailResponse.getData().getOnwardFlightSegments().get(0).getAirLineName().equals("Vistara")){
                                liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.vistara));
                            }
                            else {
                                liFlightName.setBackgroundColor(Color.parseColor("#ffffff"));
                            }

                            if (flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getAirLineName().equals("Indigo")){
                                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.indigo));
                            }

                            else  if (flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getAirLineName().equals("Air India")){
                                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.airindia));
                            }
                            else  if (flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getAirLineName().equals("SpiceJet")){
                                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.spicejet));
                            }
                            else  if (flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getAirLineName().equals("GoAir")){
                                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.goair));

                            }
                            else  if (flightBookingDetailResponse.getData().getReturnFlightSegments().get(0).getAirLineName().equals("Vistara")){
                                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.vistara));
                            }
                            else {
                                liFlightNameReturn.setBackgroundColor(Color.parseColor("#ffffff"));
                            }

                        }
                        travellerListName.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        flighPassengerBookingDialog = new FlighPassengerBookingDialog(getApplicationContext(), flightBookingDetailResponse.getData().getTickets());
                        travellerListName.setAdapter(flighPassengerBookingDialog);
                    /*   */
                    /*    if (flightBookingDetailResponse.getAirlineName().equals("Indigo")){
                            liFlightName.setBackgroundColor(Color.parseColor("#011b94"));
                        }
                        else  if (flightBookingDetailResponse.getAirlineName().equals("Air India")){
                            liFlightName.setBackgroundColor(Color.parseColor("#c0062e"));
                        }
                        else  if (flightBookingDetailResponse.getAirlineName().equals("SpiceJet")){
                            liFlightName.setBackgroundColor(Color.parseColor("#bf112f"));
                        }
                        else  if (flightBookingDetailResponse.getAirlineName().equals("GoAir")){
                            liFlightName.setBackgroundColor(Color.parseColor("#253881"));
                        }
                        else  if (flightBookingDetailResponse.getAirlineName().equals("Vistara")){
                            liFlightName.setBackgroundColor(Color.parseColor("#47143d"));
                        }
                        else {
                            liFlightName.setBackgroundColor(Color.parseColor("#ffffff"));
                        }*/

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_book_detail), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_flight_cancel_ticket:
                showDialogCancelTicket(FlightBookDetailActivity.this);

                break;
            case R.id._back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.tv_flight_download_ticket:

                if (ContextCompat.checkSelfPermission(FlightBookDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                   MarshMallowPermission.requestStoragePermission(getApplicationContext());
                        getFlightPdf();


                }
                else {
                    ActivityCompat.requestPermissions(FlightBookDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 100);
                    getFlightPdf();
                }
                break;
        }
    }

    public void showDialogCancelTicket(Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        dialog.setTitle("SafePe Alert")
                .setCancelable(false)
                .setMessage("\nAre you sure you want to cancel this ticket\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        if (isNetworkAvailable()){
                            getCancelBookTicket();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
                        }

                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }

    private void getCancelBookTicket() {
        flightBookingDetailRequest = new FlightBookingDetailRequest();
        flightBookingDetailRequest.setReferenceNo("300356016556");
      //  flightBookingDetailRequest.setReferenceNo(ReferanceNo);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightCancelTicket(flightBookingDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CancelBookTicketResponse>(){
            @Override
            public void onSuccess(CancelBookTicketResponse response) {
                loadingDialog.hideDialog();
                if (response.isStatus()) {
                    statusImage.setVisibility(View.GONE);
                    tvBookingStatus.setText("Cancellation is in progress");
                 //   tvSafeJourney.setText("Ticket cancelled successfully!");

                    String DateTime =response.getData().getCancelTime();
                    String DT[] = DateTime.split("T");
                    String D = DT[0];
                    String T = DT[1];
                    tvFlightTimeDate.setText(D+" , "+T);

                    tvFlightEticketNo.setText("PNR - "+response.getData().getAPIReferenceNo());

                    if (response.getData().getCancellations().get(0).getCancelStatus()==5){
                        tvFlightCancelTicket.setVisibility(View.GONE);
                    }
                   else if (response.getData().getCancellations().get(0).getCancelStatus()==6){
                        tvFlightCancelTicket.setVisibility(View.GONE);
                    } else if (response.getData().getCancellations().get(0).getCancelStatus()==12){
                        tvFlightCancelTicket.setVisibility(View.GONE);
                    }
                   else {
                        tvFlightCancelTicket.setVisibility(View.VISIBLE);
                    }


                }


            }

            @Override
            public void onError(Throwable e) {
                loadingDialog.hideDialog();
                BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_book_detail), false, e.getCause());
            }
        }));

    }
    //**********************************for pdf download************************
    private void getFlightPdf() {

        flightBookingDetailRequest = new FlightBookingDetailRequest();
        //  flightBookingDetailRequest.setReferenceNo("300905016582");
        flightBookingDetailRequest.setReferenceNo("300270016738");
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(FlightBookDetailActivity.this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightPdf(flightBookingDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlighPdfResponse>(){
                    @Override
                    public void onSuccess(FlighPdfResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            new DownloadTask(FlightBookDetailActivity.this, response.getData());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.flight_book_detail), false, e.getCause());
                    }
                }));

    }
}


