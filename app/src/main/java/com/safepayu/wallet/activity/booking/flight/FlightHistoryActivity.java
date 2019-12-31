package com.safepayu.wallet.activity.booking.flight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.fight.FlighHistoryPassengerBookingDialog;
import com.safepayu.wallet.adapter.fight.FlightHistroyAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking.flight.FlightHistoryResponse;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;

public class FlightHistoryActivity extends AppCompatActivity implements FlightHistroyAdapter.FlighHistoryClick, View.OnClickListener {

    private RecyclerView flightOrderHistoryList;
    private LoadingDialog loadingDialog;
    private FlightHistroyAdapter flightHistroyAdapter;
    public  Dialog dialog;
    FlightHistoryResponse FHistoryResponse;
    private String ReferanceNo ="",Source,Destination,JourneyDate,DepTime,ArrivalTime,DurationTime,AirLineCode,AirLineNumber,FlightImage,TotalTravellers,
            TripType,FlightImageReturn,DurationTimeReturn,FlightDepTimeReturn,FlightArrivalTimeReturn,FlightJourneyReturn;
    private TextView tvBookingStatus, tvSafeJourney,tvFlightRefrenceNo,tvFlightTimeDate,tvFlightSourceDestination,tvFlightEticketNo,tvFlightCancelTicket,tvTicketTimeDateTraveller,
            tvTicketSource,tvTicketDestination,tvTicketSourceTime,tvTicketTotalTime,tvTicketDestinationTime,tvTicketSourceAirportname,tvTicketDetinationAirportname,tvFlightNumber,tvFlightCode,
            tvFlightSourceDestinationReturn,tvFlightCodeReturn,tvFlightNumberReturn,tvTicketTimeDateTravellerReturn,tvTicketSourceReturn,tvTicketDestinationReturn,
            tvTicketTotalTimeReturn,tvTicketSourceTimeReturn,tvTicketDestinationTimeReturn,tvTicketSourceAirportnameReturn,tvTicketDetinationAirportnameReturn;
    private ImageView imFlightLogo,imFlightLogoReturn;
    private GifImageView statusImage;
    private FlighHistoryPassengerBookingDialog flighPassengerBookingDialog;
    private Button backBtn;
    private LinearLayout liFlightName,lretunrticket,liFlightNameReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_history);
        findId();
        if (isNetworkAvailable()){
            getFlightHistory();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
        }
    }


    private void findId() {
        loadingDialog = new LoadingDialog(this);
        flightOrderHistoryList = findViewById(R.id.flight_order_history_list);
        backBtn = findViewById(R.id.recharge_back_btn);
        backBtn.setOnClickListener(this);

        //***************get data****************
        Source =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_SOURCE);
        Destination =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DESTINATION);
        ReferanceNo = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_REFERENCE_NO);
        JourneyDate = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_START_JOURNEY);
        DepTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME);
        ArrivalTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME);
        DurationTime = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION_TIME);
        AirLineCode =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_CODE);
        AirLineNumber =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_OPERATING_AIRLINE_FLIGHT_NUMBER);
        FlightImage = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_IMAGE);
        TotalTravellers = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().TotalTravellers);
        TripType =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_TRIP_TYPE);
        FlightImageReturn = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_RETURN_IMAGE);
        DurationTimeReturn = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DURATION_TIME_RETURN);
        FlightDepTimeReturn =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_DEP_TIME_RETURN);
        FlightArrivalTimeReturn =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_ARRIVAL_TIME_RETURN);
        FlightJourneyReturn =  BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().FLIGHT_START_JOURNEY_RETURN);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getFlightHistory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlightHistoryResponse>() {
                    @Override
                    public void onSuccess(FlightHistoryResponse flightHistoryResponse) {
                        FHistoryResponse = flightHistoryResponse;
                        if (flightHistoryResponse.isStatus()){

                            loadingDialog.hideDialog(); flightOrderHistoryList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            flightHistroyAdapter = new FlightHistroyAdapter(getApplicationContext(),flightHistoryResponse.getData(),FlightHistoryActivity.this);
                            flightOrderHistoryList.setAdapter(flightHistroyAdapter);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_flight_histroy), false, e.getCause());
                    }
                }));

    }

    @Override
    public void onHistoryClick(int position, FlightHistoryResponse.DataBean mOrderId) {

        showDialogHistoryDetail(FlightHistoryActivity.this,position,mOrderId);
    }

    private void showDialogHistoryDetail(FlightHistoryActivity flightHistoryActivity, int position, FlightHistoryResponse.DataBean mOrderId) {
         RecyclerView travellerListName;

        dialog = new Dialog(flightHistoryActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_flight_book_detail);

        statusImage = dialog.findViewById(R.id.statusImage);
        tvBookingStatus = dialog.findViewById(R.id.tv_booking_status);
        tvSafeJourney = dialog.findViewById(R.id.tv_safe_journey);
        tvFlightRefrenceNo = dialog.findViewById(R.id.tv_flight_refrence_no);
        tvFlightTimeDate = dialog.findViewById(R.id.tv_flight_time_date);
        tvFlightSourceDestination = dialog.findViewById(R.id.tv_flight_source_destination);
        tvFlightEticketNo = dialog.findViewById(R.id.tv_flight_eticket_no);
        tvFlightCancelTicket = dialog.findViewById(R.id.tv_flight_cancel_ticket);
        tvTicketTimeDateTraveller = dialog.findViewById(R.id.tv_ticket_time_date_traveller);
        tvTicketSource = dialog.findViewById(R.id.tv_ticket_source);
        tvTicketDestination = dialog.findViewById(R.id.tv_ticket_destination);
        tvTicketSourceTime = dialog.findViewById(R.id.tv_ticket_source_time);
        tvTicketTotalTime = dialog.findViewById(R.id.tv_ticket_total_time);
        tvTicketDestinationTime = dialog.findViewById(R.id.tv_ticket_destination_time);
        tvTicketSourceAirportname = dialog.findViewById(R.id.tv_ticket_source_airportname);
        tvTicketDetinationAirportname = dialog.findViewById(R.id.tv_ticket_detination_airportname);

        //RETURN
        tvFlightSourceDestinationReturn = dialog.findViewById(R.id.tv_flight_source_destination_return);
        tvFlightCodeReturn = dialog.findViewById(R.id.tv_flight_code_return);
        tvFlightNumberReturn = dialog.findViewById(R.id.tv_flight_number_return);
        tvTicketTimeDateTravellerReturn = dialog.findViewById(R.id.tv_ticket_time_date_traveller_return);
        tvTicketSourceReturn = dialog.findViewById(R.id.tv_ticket_source_return);
        tvTicketDestinationReturn = dialog.findViewById(R.id.tv_ticket_destination_return);
        tvTicketTotalTimeReturn = dialog.findViewById(R.id.tv_ticket_total_time_return);
        tvTicketSourceTimeReturn = dialog.findViewById(R.id.tv_ticket_source_time_return);
        tvTicketDestinationTimeReturn = dialog.findViewById(R.id.tv_ticket_destination_time_return);
        tvTicketSourceAirportnameReturn = dialog.findViewById(R.id.tv_ticket_source_airportname_return);
        tvTicketDetinationAirportnameReturn = dialog.findViewById(R.id.tv_ticket_detination_airportname_return);
        imFlightLogoReturn = dialog.findViewById(R.id.im_flight_logo_return);
        liFlightNameReturn = dialog.findViewById(R.id.li_flight_name_return);

        travellerListName = dialog.findViewById(R.id.list_traveller_details);
        tvFlightCode = dialog.findViewById(R.id.tv_flight_code);
        tvFlightNumber = dialog.findViewById(R.id.tv_flight_number);
        imFlightLogo = dialog.findViewById(R.id.im_flight_logo);
        liFlightName = dialog.findViewById(R.id.li_flight_name);
        lretunrticket = dialog.findViewById(R.id.lretunrticket);
        tvSafeJourney.setVisibility(View.GONE);
        statusImage.setVisibility(View.GONE);
        tvFlightCancelTicket.setVisibility(View.GONE);

        tvFlightEticketNo.setText("PNR - " + mOrderId.getAPIRefNo());
        if (mOrderId.getOnwardFlightSegments().get(0).getAirLineName().equals("Indigo")){
            liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.indigo));
        }

        else  if (mOrderId.getOnwardFlightSegments().get(0).getAirLineName().equals("Air India")){
            liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.airindia));
        }
        else  if (mOrderId.getOnwardFlightSegments().get(0).getAirLineName().equals("SpiceJet")){
            liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.spicejet));
        }
        else  if (mOrderId.getOnwardFlightSegments().get(0).getAirLineName().equals("GoAir")){
            liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.goair));
        }
        else  if (mOrderId.getOnwardFlightSegments().get(0).getAirLineName().equals("Vistara")){
            liFlightName.setBackgroundDrawable(getResources().getDrawable(R.drawable.vistara));
        }
        else {
            liFlightName.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        if (FHistoryResponse.getData().get(position).getBookingStatus()==1){
            tvBookingStatus.setText("Payment received, Ticket booking is under process");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.green_500)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==2){
            tvBookingStatus.setText("Payment received, Due to some problems We cannot book the ticket. So, cancelled. Payment will be reverted");
              tvBookingStatus.setTextColor(getResources().getColor(R.color.clay_yellow)); }
        else  if (FHistoryResponse.getData().get(position).getBookingStatus()==3){
            tvBookingStatus.setText("Indicates Payment received, Ticket booked successfully");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.green_500)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==4){
            tvBookingStatus.setText("After successful confirmation of the ticket, if it is cancelled then this is the status, due to flight delay");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.clay_yellow)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==5){
            tvBookingStatus.setText("Cancellation is in process");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==6){
            tvBookingStatus.setText("Ticket Cancellation is successfully");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==7){
            tvBookingStatus.setText("Ticket cancellation is rejected");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==8){
            tvBookingStatus.setText("Flight ticket is blocked for booking");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==9){
            tvBookingStatus.setText("Something went wrong in Payment");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==10){
            tvBookingStatus.setText("Ticket Booking Failed");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==11){
            tvBookingStatus.setText("Ticket not Found");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==12){
            tvBookingStatus.setText("Ticket Partially Cancelled");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==13){
            tvBookingStatus.setText("Partial Cancellation Failed");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==14){
            tvBookingStatus.setText("Request Processed");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==15){
            tvBookingStatus.setText("Request Rejected");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==16){
            tvBookingStatus.setText("Ticket Blocking Failed");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400)); }
      /*  if (FHistoryResponse.getData().get(position).getBookingStatus()==3){
            tvBookingStatus.setText("Booking Successful");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.green_500));
        }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==5){
            tvBookingStatus.setText("Cancellation is in progress");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.red_400));
        }
        else if (FHistoryResponse.getData().get(position).getBookingStatus()==2){
            tvBookingStatus.setText("Payment received, Due to some problems We cannot book the ticket. So, cancelled. Payment will be reverted");
            tvBookingStatus.setTextColor(getResources().getColor(R.color.clay_yellow));
        }*/
        tvFlightRefrenceNo.setText(FHistoryResponse.getData().get(position).getBookingRefNo());


        String DateTime =FHistoryResponse.getData().get(position).getBookingDate();
        String DT[] = DateTime.split("T");
        String D = DT[0];
        String T = DT[1];
        tvFlightTimeDate.setText(D+" , "+T);


        tvFlightSourceDestination.setText(Source+" - "+Destination);
        tvTicketTimeDateTraveller.setText(JourneyDate+" | "+TotalTravellers + " Traveller");
        tvTicketSource.setText(Source);
        tvTicketDestination.setText(Destination);
        tvTicketSourceTime.setText(DepTime);
        tvTicketDestinationTime.setText(ArrivalTime);
        tvTicketTotalTime.setText(DurationTime);
        tvTicketSourceAirportname.setText(FHistoryResponse.getData().get(position).getSourceName());
        tvTicketDetinationAirportname.setText(FHistoryResponse.getData().get(position).getDestinationName());
        tvFlightCode.setText(AirLineCode);
        tvFlightNumber.setText(AirLineNumber);
        Picasso.get().load(FlightImage).into(imFlightLogo);


        if(mOrderId.getTripType()==2){
            lretunrticket.setVisibility(View.VISIBLE);
            tvFlightSourceDestinationReturn.setText(mOrderId.getReturnFlightSegments().get(0).getDepartureAirportCode() + " - " +mOrderId.getReturnFlightSegments().get(0).getArrivalAirportCode());
            Picasso.get().load(FlightImageReturn).into(imFlightLogoReturn);
            tvFlightCodeReturn.setText(mOrderId.getReturnFlightSegments().get(0).getImageFileName());
            tvFlightNumberReturn.setText(mOrderId.getReturnFlightSegments().get(0).getOperatingAirlineFlightNumber());
            tvTicketSourceReturn.setText(  mOrderId.getReturnFlightSegments().get(0).getDepartureAirportCode());
            tvTicketDestinationReturn.setText(mOrderId.getReturnFlightSegments().get(0).getArrivalAirportCode());
            tvTicketDetinationAirportnameReturn.setText(mOrderId.getSourceName());
            tvTicketSourceAirportnameReturn.setText(mOrderId.getDestinationName());
            tvTicketTotalTimeReturn.setText(DurationTimeReturn);
            tvTicketSourceTimeReturn.setText(FlightDepTimeReturn);
            tvTicketDestinationTimeReturn.setText(FlightArrivalTimeReturn);
            tvTicketTimeDateTraveller.setText(JourneyDate + " | " + TotalTravellers + " Traveller");
            tvTicketTimeDateTravellerReturn.setText(FlightJourneyReturn + " | " + TotalTravellers + " Traveller");
            if (mOrderId.getReturnFlightSegments().get(0).getAirLineName().equals("Indigo")){
                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.indigo));
            }

            else  if (mOrderId.getReturnFlightSegments().get(0).getAirLineName().equals("Air India")){
                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.airindia));
            }
            else  if (mOrderId.getReturnFlightSegments().get(0).getAirLineName().equals("SpiceJet")){
                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.spicejet));
            }
            else  if (mOrderId.getReturnFlightSegments().get(0).getAirLineName().equals("GoAir")){
                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.goair));
            }
            else  if (mOrderId.getReturnFlightSegments().get(0).getAirLineName().equals("Vistara")){
                liFlightNameReturn.setBackgroundDrawable(getResources().getDrawable(R.drawable.vistara));
            }
            else {
                liFlightNameReturn.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
        travellerListName.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        flighPassengerBookingDialog = new FlighHistoryPassengerBookingDialog(getApplicationContext(),mOrderId.getTickets());
        travellerListName.setAdapter(flighPassengerBookingDialog);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id._back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
        }
    }
}
