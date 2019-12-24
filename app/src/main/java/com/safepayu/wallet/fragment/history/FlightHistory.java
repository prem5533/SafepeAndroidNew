package com.safepayu.wallet.fragment.history;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.MemberBankAddPackages;
import com.safepayu.wallet.activity.MyOrdersActivity;
import com.safepayu.wallet.activity.booking.flight.FlightHistoryActivity;
import com.safepayu.wallet.adapter.fight.FlighHistoryPassengerBookingDialog;
import com.safepayu.wallet.adapter.fight.FlightHistroyAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking.flight.FlightBookingDetailRequest;
import com.safepayu.wallet.models.request.booking.flight.FlightHistoryResponse;
import com.safepayu.wallet.models.response.booking.flight.FlighPdfResponse;
import com.safepayu.wallet.utils.pdf.DownloadTask;
import com.safepayu.wallet.utils.pdf.MarshMallowPermission;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;

public class FlightHistory  extends Fragment implements FlightHistroyAdapter.FlighHistoryClick, View.OnClickListener {

    private RecyclerView flightOrderHistoryList;
    private LoadingDialog loadingDialog;
    private FlightHistroyAdapter flightHistroyAdapter;
    public Dialog dialog;
    FlightHistoryResponse FHistoryResponse;
    private String ReferanceNo ="",Source,Destination,JourneyDate,DepTime,ArrivalTime,DurationTime,AirLineCode,AirLineNumber,FlightImage,TotalTravellers,
            TripType,FlightImageReturn,DurationTimeReturn,FlightDepTimeReturn,FlightArrivalTimeReturn,FlightJourneyReturn;
    private TextView tvBookingStatus, tvSafeJourney,tvFlightRefrenceNo,tvFlightTimeDate,tvFlightSourceDestination,tvFlightEticketNo,tvFlightCancelTicket,tvTicketTimeDateTraveller,
            tvTicketSource,tvTicketDestination,tvTicketSourceTime,tvTicketTotalTime,tvTicketDestinationTime,tvTicketSourceAirportname,tvTicketDetinationAirportname,tvFlightNumber,tvFlightCode,
            tvFlightSourceDestinationReturn,tvFlightCodeReturn,tvFlightNumberReturn,tvTicketTimeDateTravellerReturn,tvTicketSourceReturn,tvTicketDestinationReturn,
            tvTicketTotalTimeReturn,tvTicketSourceTimeReturn,tvTicketDestinationTimeReturn,tvTicketSourceAirportnameReturn,tvTicketDetinationAirportnameReturn,tvFlightDownloadTicket;
    private ImageView imFlightLogo,imFlightLogoReturn;
    private GifImageView statusImage;
    private FlighHistoryPassengerBookingDialog flighPassengerBookingDialog;
    private Button backBtn;
    private LinearLayout liFlightName,lretunrticket,liFlightNameReturn;
    FlightBookingDetailRequest flightBookingDetailRequest;

    public FlightHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.flight_history_fragment, container, false);
        loadingDialog = new LoadingDialog(getActivity());

        flightOrderHistoryList =rootView. findViewById(R.id.flight_order_history_list);
        backBtn = rootView.findViewById(R.id.recharge_back_btn);
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

        if (isNetworkAvailable()){
            getFlightHistory();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(rootView.findViewById(R.id.paymentLayout),"Please Check Your Internet Connection",false);
        }

        return rootView;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_back_btn:
               getActivity(). overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                getActivity().finish();
                break;

        }
    }

    private void getFlightHistory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlightHistoryResponse>() {
                    @Override
                    public void onSuccess(FlightHistoryResponse flightHistoryResponse) {
                        FHistoryResponse = flightHistoryResponse;
                        if (flightHistoryResponse.isStatus()){

                            loadingDialog.hideDialog(); flightOrderHistoryList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            flightHistroyAdapter = new FlightHistroyAdapter(getActivity(),flightHistoryResponse.getData(),FlightHistory.this);
                            flightOrderHistoryList.setAdapter(flightHistroyAdapter);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getView().findViewById(R.id.relative_flight_histroy), false, e.getCause());
                    }
                }));

    }

    @Override
    public void onHistoryClick(int position, FlightHistoryResponse.DataBean mOrderId) {
        showDialogHistoryDetail(FlightHistory.this,position,mOrderId);
    }

    private void showDialogHistoryDetail(FlightHistory flightHistory, int position, FlightHistoryResponse.DataBean mOrderId) {
        RecyclerView travellerListName;

        dialog = new Dialog(getActivity());
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
        tvFlightDownloadTicket = dialog.findViewById(R.id.tv_flight_download_ticket);

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

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            MarshMallowPermission.requestStoragePermission(getActivity());
        }
        else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 100);
        }


        tvFlightDownloadTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlightPdf();

            }
        });
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


        if (FHistoryResponse.getData().get(position).getBookingStatus()==3){
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
        }



        String Time [] =FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getDepartureDateTime().split("T");
        String date = Time[0];
        String t = Time[1];
        String tt [] =t.split(":");
        String h = tt[0];
        String m = tt[1];
        String ATime [] =FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getArrivalDateTime().split("T");
        String Adate = ATime[0];
        String At = ATime[1];
        String Att [] =At.split(":");
        String Ah = Att[0];
        String Am = Att[1];

        String DateTime =FHistoryResponse.getData().get(position).getBookingDate();
        String DT[] = DateTime.split("T");
        String D = DT[0];
        String T = DT[1];
        tvFlightTimeDate.setText(D+" "+T);

        tvFlightRefrenceNo.setText(FHistoryResponse.getData().get(position).getBookingRefNo());
        tvFlightTimeDate.setText(D+" "+T);
        tvFlightSourceDestination.setText(FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getDepartureAirportCode()+" - "+FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getArrivalAirportCode());
        tvTicketTimeDateTraveller.setText(date+" | "+TotalTravellers + " Traveller");
        tvTicketSource.setText(FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getDepartureAirportCode());
        tvTicketDestination.setText(FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getArrivalAirportCode());
        tvTicketSourceTime.setText(h+":"+m);
        tvTicketDestinationTime.setText(Ah +":"+Am);
        tvTicketTotalTime.setText(FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getDuration());
        tvTicketSourceAirportname.setText(FHistoryResponse.getData().get(position).getSourceName());
        tvTicketDetinationAirportname.setText(FHistoryResponse.getData().get(position).getDestinationName());
        tvFlightCode.setText(FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getOperatingAirlineCode());
        tvFlightNumber.setText(FHistoryResponse.getData().get(position).getOnwardFlightSegments().get(0).getOperatingAirlineFlightNumber());
       // Picasso.get().load(FlightImage).into(imFlightLogo);
        Picasso.get().load("http://webapi.i2space.co.in/"+"/Images/airline_logos/"+mOrderId.getOnwardFlightSegments().get(0).getImageFileName()+".png").into(imFlightLogo);



        if(mOrderId.getTripType()==2){

            String DTime [] =mOrderId.getOnwardFlightSegments().get(0).getDepartureDateTime().split("T");
            String Ddate = DTime[0];
            String rTime [] =mOrderId.getReturnFlightSegments().get(0).getDepartureDateTime().split("T");
            String rdate = rTime[0];
            String Dt = rTime[1];
            String Dtt [] =Dt.split(":");
            String Dh = Dtt[0];
            String Dm = Dtt[1];
            String RTime [] =mOrderId.getReturnFlightSegments().get(0).getArrivalDateTime().split("T");
            String Rdate = RTime[0];
            String Rt = RTime[1];
            String Rtt [] =Rt.split(":");
            String Rh = Rtt[0];
            String Rm = Rtt[1];

            lretunrticket.setVisibility(View.VISIBLE);
            tvFlightSourceDestinationReturn.setText(mOrderId.getReturnFlightSegments().get(0).getDepartureAirportCode() + " - " +mOrderId.getReturnFlightSegments().get(0).getArrivalAirportCode());
            Picasso.get().load("http://webapi.i2space.co.in/"+"/Images/airline_logos/"+mOrderId.getReturnFlightSegments().get(0).getImageFileName()+".png").into(imFlightLogoReturn);
            tvFlightCodeReturn.setText(mOrderId.getReturnFlightSegments().get(0).getImageFileName());
            tvFlightNumberReturn.setText(mOrderId.getReturnFlightSegments().get(0).getOperatingAirlineFlightNumber());
            tvTicketSourceReturn.setText(  mOrderId.getReturnFlightSegments().get(0).getDepartureAirportCode());
            tvTicketDestinationReturn.setText(mOrderId.getReturnFlightSegments().get(0).getArrivalAirportCode());
            tvTicketDetinationAirportnameReturn.setText(mOrderId.getSourceName());
            tvTicketSourceAirportnameReturn.setText(mOrderId.getDestinationName());
            tvTicketTotalTimeReturn.setText(mOrderId.getReturnFlightSegments().get(0).getDuration());
            tvTicketSourceTimeReturn.setText(Dh+":"+Dm);
            tvTicketDestinationTimeReturn.setText(Rh+":"+Rm);
            tvTicketTimeDateTraveller.setText(Ddate + " | " + TotalTravellers + " Traveller");
            tvTicketTimeDateTravellerReturn.setText(rdate + " | " + TotalTravellers + " Traveller");
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
        travellerListName.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        flighPassengerBookingDialog = new FlighHistoryPassengerBookingDialog(getActivity(),mOrderId.getTickets());
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

    //**********************************for pdf download************************
    private void getFlightPdf() {

        flightBookingDetailRequest = new FlightBookingDetailRequest();
        //  flightBookingDetailRequest.setReferenceNo("300905016582");
        flightBookingDetailRequest.setReferenceNo("300270016738");
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightPdf(flightBookingDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlighPdfResponse>(){
                    @Override
                    public void onSuccess(FlighPdfResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            new DownloadTask(getActivity(), response.getData());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getView().findViewById(R.id.flight_book_detail), false, e.getCause());
                    }
                }));

    }
}
