package com.safepayu.wallet.fragment.bus;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.bus.BoardingPointAdapter;
import com.safepayu.wallet.adapter.bus.DroppingPointAdapter;
import com.safepayu.wallet.adapter.bus.FillDetailAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking_bus.BusTripDetailsRequest;
import com.safepayu.wallet.models.response.booking.bus.BusTripDetailsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.booking.bus.BusListActivity.SelectedBusList;
import static com.safepayu.wallet.activity.booking.bus.BusListActivity.busTripDetailsRequest;
import static com.safepayu.wallet.activity.booking.bus.BusListActivity.response1;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectSeatFragment extends Fragment implements View.OnClickListener, BoardingPointAdapter.BoardingListListener,
        DroppingPointAdapter.DroppingListListener, FillDetailAdapter.FillDetailListener
 {
     private LoadingDialog loadingDialog;
     private ViewGroup layout;
     private String seats ="";
     private ArrayList<String> RowList,ColumnList,SeatNoList,ZIndex;
     List<BusTripDetailsResponse.DataBean.SeatsBean> SeatLists;
     private Button DoneBtn;

//     String seats = "UU_AA/"
//             + "UU_AA/"
//             + "UU_UU/"
//             + "AA_AA/"
//             + "AA_AA/"
//             + "UU_UU/"
//             + "AA_AA/"
//             + "AA_AA/"
//             + "AA_UU/"
//             + "UU_AA/"
//             + "RR_AA/"
//             + "AA_UU/"
//             + "AA_AA/"
//             + "_____/";


     List<TextView> seatViewList = new ArrayList<>();
     private int seatSize = 80;
     private int seatGaping = 10;

     private int STATUS_AVAILABLE = 1;
     private int STATUS_BOOKED = 2;
     private int STATUS_RESERVED = 3;
     private String selectedIds,SeatsSelected="";
     private Dialog dialog,fillDetailDialog;
     public static Button proceedBtnFillDetailDialog;

     {
         selectedIds = "";
     }


     public SelectSeatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_select_seat1, container, false);

        loadingDialog=new LoadingDialog(getActivity());
        RowList=new ArrayList<>();
        ColumnList=new ArrayList<>();
        SeatNoList=new ArrayList<>();
        ZIndex=new ArrayList<>();

        DoneBtn=rootView.findViewById(R.id.doneBtn_busSeatLayout);

        DoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBusSources( );
            }
        });

        getBusSeat(busTripDetailsRequest,rootView);

        return rootView;
    }

     private void showDialogBusSources( ) {

         BoardingPointAdapter boardingPointAdapter;
         DroppingPointAdapter droppingPointAdapter;

         dialog = new Dialog(getActivity());
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setContentView(R.layout.dialog_show_seat_selection);

         WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
         Window window = dialog.getWindow();
         dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
         lp.copyFrom(window.getAttributes());
         //This makes the dialog take up the full width
         lp.width = WindowManager.LayoutParams.MATCH_PARENT;
         lp.height = WindowManager.LayoutParams.MATCH_PARENT;
         window.setAttributes(lp);

         TextView tvTravelDate,tvSource,tvDestination,tvTravellerName,tvBusType,tvNoOfSeats,tvSeatNumbers;
         TextView tvBaseFare,tvGst,tvConvenience,tvTotalFare;

         RecyclerView recyclerViewBoarding,recyclerViewDropping;

         tvTravelDate = dialog.findViewById(R.id.date_seatSelectionDialog);
         tvSource = dialog.findViewById(R.id.sourceName_seatSelectionDialog);
         tvDestination = dialog.findViewById(R.id.destinationName_seatSelectionDialog);
         tvTravellerName = dialog.findViewById(R.id.travellerName_seatSelectionDialog);
         tvBusType = dialog.findViewById(R.id.busTypeName_seatSelectionDialog);
         tvNoOfSeats = dialog.findViewById(R.id.numberOfSeats_seatSelectionDialog);
         tvSeatNumbers = dialog.findViewById(R.id.seatNumber_seatSelectionDialog);
         tvBaseFare = dialog.findViewById(R.id.baseFare_seatSelectionDialog);
         tvGst = dialog.findViewById(R.id.gst_seatSelectionDialog);
         tvConvenience = dialog.findViewById(R.id.convenience_seatSelectionDialog);
         tvTotalFare = dialog.findViewById(R.id.totalFare_seatSelectionDialog);

         tvTravelDate.setText(busTripDetailsRequest.getJourneyDate());
         tvSource.setText(busTripDetailsRequest.getSource());
         tvDestination.setText(busTripDetailsRequest.getDestination());
         tvTravellerName.setText(busTripDetailsRequest.getTravelOperator());
         tvBusType.setText(busTripDetailsRequest.getBusType());

         tvNoOfSeats.setText(""+SeatNoList.size());

         int baseFare=0,test=0;
         double totalFare = 0;
         String seatss="";
         for (int seatNo=0;seatNo<SeatNoList.size();seatNo++){
             if (SeatNoList.size()==1){
                 tvSeatNumbers.setText(SeatNoList.get(seatNo));
                 seatss=seatss+SeatNoList.get(seatNo);
             }else {
                 if (SeatNoList.size()-1==seatNo){
                     seatss = seatss + SeatNoList.get(seatNo);
                 }else {
                     seatss = seatss + SeatNoList.get(seatNo) + ", ";
                 }
             }
             tvSeatNumbers.setText(seatss);
             test=Integer.parseInt(SeatLists.get(seatNo+1).getFare());
             baseFare=baseFare+test;
         }

         try {
             totalFare=baseFare+GetGstAmount(baseFare)+(double)20;
         }catch (Exception e){
             e.printStackTrace();
         }

         tvBaseFare.setText(getActivity().getResources().getString(R.string.rupees)+" "+(double)baseFare);
         tvGst.setText(getActivity().getResources().getString(R.string.rupees)+" "+GetGstAmount(baseFare));
         tvConvenience.setText(getActivity().getResources().getString(R.string.rupees)+" "+(double)20);
         tvTotalFare.setText(getActivity().getResources().getString(R.string.rupees)+" "+totalFare);


         recyclerViewBoarding = dialog.findViewById(R.id.boardingPoints_seatSelectionDialog);
         recyclerViewBoarding.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
         recyclerViewBoarding.setNestedScrollingEnabled(false);
         boardingPointAdapter=new BoardingPointAdapter(getActivity(),
                 response1.getData().getAvailableTrips().get(SelectedBusList).getBoardingTimes(),this);

         recyclerViewBoarding.setAdapter(boardingPointAdapter);

         recyclerViewDropping = dialog.findViewById(R.id.dropingPoints_seatSelectionDialog);
         recyclerViewDropping.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
         recyclerViewDropping.setNestedScrollingEnabled(false);
         droppingPointAdapter=new DroppingPointAdapter(getActivity(),
                 response1.getData().getAvailableTrips().get(SelectedBusList).getDroppingTimes(),this);
         recyclerViewDropping.setAdapter(droppingPointAdapter);

         Button back_btn_locationNameBus=dialog.findViewById(R.id.backBtn_seatSelectionDialog);
         Button proceedBtn=dialog.findViewById(R.id.proceedBtn_seatSelectionDialog);

         back_btn_locationNameBus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.dismiss();
             }
         });

         proceedBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.dismiss();
                 showDialogFillDetail( );
             }
         });

         dialog.show();

     }

     @Override
     public void onLocationClickTo(int position, String Buslist) {

     }

     @Override
     public void onDroppingClickTo(int position, String Buslist) {

     }


     private double GetGstAmount(int BaseFase){
         double amount = Double.parseDouble(String.valueOf(BaseFase));
         double res = (amount / 100.0f) * 5;

         return res;
     }


     private void showDialogFillDetail( ) {

         FillDetailAdapter fillDetailAdapter;

         fillDetailDialog = new Dialog(getActivity());
         fillDetailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         fillDetailDialog.setContentView(R.layout.dialog_fill_detail);

         WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
         Window window = fillDetailDialog.getWindow();
         fillDetailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
         lp.copyFrom(window.getAttributes());
         //This makes the dialog take up the full width
         lp.width = WindowManager.LayoutParams.MATCH_PARENT;
         lp.height = WindowManager.LayoutParams.MATCH_PARENT;
         window.setAttributes(lp);

         TextView tvTravelDate,tvSource,tvDestination;

         RecyclerView recyclerViewFillDetail;


         recyclerViewFillDetail = fillDetailDialog.findViewById(R.id.bus_passengers_fillDetailDialog);
         recyclerViewFillDetail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
         recyclerViewFillDetail.setNestedScrollingEnabled(false);
         fillDetailAdapter=new FillDetailAdapter(getActivity(),SeatNoList.size(),this);

         recyclerViewFillDetail.setAdapter(fillDetailAdapter);

         Button backBtn_fillDetailDialog=fillDetailDialog.findViewById(R.id.backBtn_fillDetailDialog);

         backBtn_fillDetailDialog.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 fillDetailDialog.dismiss();
             }
         });

         proceedBtnFillDetailDialog=fillDetailDialog.findViewById(R.id.proceedBtn_fillDetailDialog);

         proceedBtnFillDetailDialog.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 fillDetailDialog.dismiss();
             }
         });


         fillDetailDialog.show();

     }

     @Override
     public void onLocationClickTo(int position) {

     }

     private String SeatsCounts(int NoOfSeats){
         String SeatsArrangement="";
         int length=0,rowLength=0,seatNumber=0;

         if (SeatLists.get(seatNumber).getIsAvailableSeat().equalsIgnoreCase("True")){
             SeatsArrangement=SeatsArrangement+"A";
         }else {
             SeatsArrangement=SeatsArrangement+"U";
         }

         for (int i=1;i<NoOfSeats;i++){

             length=SeatsArrangement.length();

             try {
                 if (length%6==0){
                     rowLength=0;
                     NoOfSeats=NoOfSeats+2;
                 }else {
                     try {
                         rowLength=length%6;
                     }catch (Exception e){
                         rowLength=SeatsArrangement.length();
                         e.printStackTrace();
                     }
                 }
             }catch (Exception e){
                 rowLength=SeatsArrangement.length();
                 e.printStackTrace();
             }

             if (rowLength%6==0){
                 seatNumber=seatNumber+1;
                 if (CheckSeatAvailable(SeatLists.get(seatNumber).getIsAvailableSeat())){
                     SeatsArrangement=SeatsArrangement+"A";
                 }else {
                     SeatsArrangement=SeatsArrangement+"U";
                 }

             }else if (rowLength%5==0){
                 SeatsArrangement=SeatsArrangement+"/";
             }else if (rowLength%4==0){

                 seatNumber=seatNumber+1;
                 if (CheckSeatAvailable(SeatLists.get(seatNumber).getIsAvailableSeat())){
                     SeatsArrangement=SeatsArrangement+"A";
                 }else {
                     SeatsArrangement=SeatsArrangement+"U";
                 }

             }else if (rowLength%2==0){
                 SeatsArrangement=SeatsArrangement+"_";
             }else {
                 seatNumber=seatNumber+1;
                 if (CheckSeatAvailable(SeatLists.get(seatNumber).getIsAvailableSeat())){
                     SeatsArrangement=SeatsArrangement+"A";
                 }else {
                     SeatsArrangement=SeatsArrangement+"U";
                 }

             }
         }

         try {
             try {
                 length=SeatsArrangement.length();
                 try {
                     rowLength=length%6;
                     if (rowLength==5){
                         SeatsArrangement=SeatsArrangement+"/";
                     }else if (rowLength==2){
                         SeatsArrangement=SeatsArrangement+"_";
                     }
                 }catch (Exception e){
                     e.printStackTrace();
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }

             if (SeatLists.get(seatNumber+1).getIsAvailableSeat().equalsIgnoreCase("True")){
                 SeatsArrangement=SeatsArrangement+"A";
             }else {
                 SeatsArrangement=SeatsArrangement+"U";
             }
             if (SeatLists.get(seatNumber+2).getIsAvailableSeat().equalsIgnoreCase("True")){
                 SeatsArrangement=SeatsArrangement+"A";
             }else {
                 SeatsArrangement=SeatsArrangement+"U";
             }
         }catch (Exception e){
             e.printStackTrace();
         }

         return SeatsArrangement+"/";
     }

     private boolean CheckSeatAvailable(String Case){

         if (Case.equalsIgnoreCase("True")){
             return true;
         }else {
             return false;
         }
     }

     private void MakeSeat(View rootView){
         layout = rootView.findViewById(R.id.layoutSeat);

         seats = "/" + seats;

         LinearLayout layoutSeat = new LinearLayout(getActivity());
         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         layoutSeat.setOrientation(LinearLayout.VERTICAL);
         layoutSeat.setLayoutParams(params);
         layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
         layout.addView(layoutSeat);

         LinearLayout layout = null;

         int count = 0;

         for (int index = 0; index < seats.length(); index++) {
             if (seats.charAt(index) == '/') {
                 layout = new LinearLayout(getActivity());
                 layout.setOrientation(LinearLayout.HORIZONTAL);
                 layoutSeat.addView(layout);
             } else if (seats.charAt(index) == 'U') {
                 count++;
                 TextView view = new TextView(getActivity());
                 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                 layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                 view.setLayoutParams(layoutParams);
                 view.setPadding(0, 0, 0, 2 * seatGaping);
                 view.setId(count);
                 view.setGravity(Gravity.CENTER);
                 view.setBackgroundResource(R.drawable.ic_seats_booked);
                 view.setTextColor(Color.WHITE);
                 view.setTag(STATUS_BOOKED);
                 view.setText(count + "");
                 view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                 layout.addView(view);
                 seatViewList.add(view);
                 view.setOnClickListener(this);
             } else if (seats.charAt(index) == 'A') {
                 count++;
                 TextView view = new TextView(getActivity());
                 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                 layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                 view.setLayoutParams(layoutParams);
                 view.setPadding(0, 0, 0, 2 * seatGaping);
                 view.setId(count);
                 view.setGravity(Gravity.CENTER);
                 view.setBackgroundResource(R.drawable.ic_seats_book);
                 view.setText(count + "");
                 view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                 view.setTextColor(Color.BLACK);
                 view.setTag(STATUS_AVAILABLE);
                 layout.addView(view);
                 seatViewList.add(view);
                 view.setOnClickListener(this);
             } else if (seats.charAt(index) == 'R') {
                 count++;
                 TextView view = new TextView(getActivity());
                 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                 layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                 view.setLayoutParams(layoutParams);
                 view.setPadding(0, 0, 0, 2 * seatGaping);
                 view.setId(count);
                 view.setGravity(Gravity.CENTER);
                 view.setBackgroundResource(R.drawable.ic_seats_reserved);
                 view.setText(count + "");
                 view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                 view.setTextColor(Color.WHITE);
                 view.setTag(STATUS_RESERVED);
                 layout.addView(view);
                 seatViewList.add(view);
                 view.setOnClickListener(this);
             } else if (seats.charAt(index) == '_') {
                 TextView view = new TextView(getActivity());
                 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                 layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                 view.setLayoutParams(layoutParams);
                 view.setBackgroundColor(Color.TRANSPARENT);
                 view.setText("");
                 layout.addView(view);
             }
         }
     }

     @Override
     public void onClick(View view) {
         int pos=0;
         String ZIndexText="";
         if ((int) view.getTag() == STATUS_AVAILABLE) {
             if (selectedIds.contains(view.getId() + ",")) {
                 selectedIds = selectedIds.replace(+view.getId() + ",", "");
                 SeatsSelected = SeatsSelected.replace(+view.getId() + "~", "");
                 view.setBackgroundResource(R.drawable.ic_seats_book);

                 pos=(int) view.getId()-1;
                 RowList.remove(String.valueOf(SeatLists.get(pos).getRow()));
                 ColumnList.remove(String.valueOf(SeatLists.get(pos).getColumn()));
                 SeatNoList.remove(String.valueOf( view.getId()));
                 Toast.makeText(getActivity(), "Unselected - "+view.getId(), Toast.LENGTH_SHORT).show();
             } else {

                 selectedIds = selectedIds + view.getId() + ",";
                 SeatsSelected = SeatsSelected + view.getId() + "~";
                 view.setBackgroundResource(R.drawable.ic_seats_selected);

                 //ZIndexText=ZIndex.get(view.getId());

                 try {
                     if (ZIndex.get((view.getId())-1).equalsIgnoreCase("0")){
                         ZIndexText="Lower Berth";
                     }else {
                         ZIndexText="Upper Berth";
                     }
                 }catch (Exception e){
                     e.printStackTrace();
                 }
                 pos=(int) view.getId()-1;
                 RowList.add(String.valueOf(SeatLists.get(pos).getRow()));
                 ColumnList.add(String.valueOf(SeatLists.get(pos).getColumn()));
                 SeatNoList.add(String.valueOf( view.getId()));

                 Toast.makeText(getActivity(), "Selected - "+view.getId()+" "+ZIndexText, Toast.LENGTH_SHORT).show();
             }
         } else if ((int) view.getTag() == STATUS_BOOKED) {
             Toast.makeText(getActivity(), "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
         } else if ((int) view.getTag() == STATUS_RESERVED) {
             Toast.makeText(getActivity(), "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
         }
     }

     private void getBusSeat(final BusTripDetailsRequest busTripDetailsRequest,final View rootView) {

         loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
         ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);

         BaseApp.getInstance().getDisposable().add(apiService.getBusTripDetails(busTripDetailsRequest)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeWith(new DisposableSingleObserver<BusTripDetailsResponse>() {
                     @Override
                     public void onSuccess(BusTripDetailsResponse response) {
                         loadingDialog.hideDialog();
                         if (response.isStatus()) {
                             try {
                                 if (response.getData().getSeats().size()>0){

                                 }else {
                                     BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.busSeatLayout), "No Data Found", false);
                                 }
                                 for (int index=0;index<response.getData().getSeats().size();index++){
                                     ZIndex.add(String.valueOf(response.getData().getSeats().get(index).getZindex()));
                                 }
                                 SeatLists=response.getData().getSeats();
                                 seats=SeatsCounts(response.getData().getSeats().size());

                                 MakeSeat(rootView);
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         } else {
                             BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.busSeatLayout), response.getMesage(), false);
                         }
                     }

                     @Override
                     public void onError(Throwable e) {
                         loadingDialog.hideDialog();
                         Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                         BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.busSeatLayout), false, e.getCause());
                     }
                 }));

     }
 }
