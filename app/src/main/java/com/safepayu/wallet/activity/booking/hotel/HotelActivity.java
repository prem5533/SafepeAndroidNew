package com.safepayu.wallet.activity.booking.hotel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.hotel.GuestSelectAdapter;
import com.safepayu.wallet.adapter.hotel.HotelSourcesAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.DatePickerHidePreviousDate;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking_hotel.AvailableHotelRequest;
import com.safepayu.wallet.models.response.booking.hotel.HotelSourcesResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.adapter.hotel.ChildGuestAdapter.ChildAgeList;
import static com.safepayu.wallet.adapter.hotel.GuestSelectAdapter.AdultNoList;
import static com.safepayu.wallet.adapter.hotel.GuestSelectAdapter.ChildNoList;
import static com.safepayu.wallet.adapter.hotel.GuestSelectAdapter.RoomList;


public class HotelActivity extends AppCompatActivity implements View.OnClickListener,HotelSourcesAdapter.LocationListListener {

    private Button searhHotelBtn, backBtn;
    private Dialog dialog,GuestDialog;
    private LinearLayout TravellerLAyout;
    private TextView HotelCityTV,CheckInTV,CheckOutTV,TravellerTV;
    private RecyclerView recyclerViewLocationList,GuestListSelect ;
    private LoadingDialog loadingDialog;
    private AutoCompleteTextView locationName;
    private ArrayList<String> HotelSourcesList,HotelSourcesIdList,ChildNoStringList,AdultNoStringList,ChildAgeStringList;
    private List<List<String>> childrenAges;
    private ArrayList<Integer> ChildNoList1,AdultNoList1,GuestNoList1,RoomNoList1;
    private HotelSourcesAdapter busSourcesAdapter;
    private String SourceName="",SourceNameId="";
    private int TotalGuest=1,AdultTotal=1,ChildTotal=0,RoomTotal=1;
    public static AvailableHotelRequest availableHotelRequest;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        findId();
    }

    private void findId() {
        loadingDialog=new LoadingDialog(this);
        HotelSourcesList=new ArrayList<>();
        HotelSourcesIdList=new ArrayList<>();
        ChildNoStringList=new ArrayList<>();
        AdultNoStringList=new ArrayList<>();
        childrenAges=new ArrayList<>();
        ChildAgeStringList=new ArrayList<>();

        backBtn = findViewById(R.id.recharge_back_btn);
        searhHotelBtn = findViewById(R.id.search_hotel_btn);
        HotelCityTV=findViewById(R.id.tv_HotelCity);
        CheckInTV=findViewById(R.id.tv_CheckIn_date);
        CheckOutTV=findViewById(R.id.tv_CheckOut_date);
        TravellerTV=findViewById(R.id.travellers_hotel);
        TravellerLAyout=findViewById(R.id.travellersLayout_hotel);

        //set listener
        backBtn.setOnClickListener(this);
        searhHotelBtn.setOnClickListener(this);
        HotelCityTV.setOnClickListener(this);
        CheckInTV.setOnClickListener(this);
        CheckOutTV.setOnClickListener(this);
        TravellerLAyout.setOnClickListener(this);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        CheckInTV.setText(df.format(Calendar.getInstance().getTime()));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        CheckOutTV.setText(df.format(calendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;
                
            case R.id.search_hotel_btn:

                if (TextUtils.isEmpty(SourceNameId)){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel),"Please Select Location",true);
                }else {
                    String days = String.valueOf(getDaysBetweenDates(CheckInTV.getText().toString(),CheckOutTV.getText().toString()));

                    if (AdultNoStringList.size()>0){

                    }else {
                        AdultNoStringList.add("1");
                    }
                    availableHotelRequest=new AvailableHotelRequest();

                    availableHotelRequest.setAdults(AdultNoStringList);
                    availableHotelRequest.setChildren(ChildNoStringList);
                    availableHotelRequest.setArrivalDate("30-03-2020");
                    availableHotelRequest.setDepartureDate("31-03-2020");
                    availableHotelRequest.setNoOfDays(days);
                    availableHotelRequest.setRooms(String.valueOf(RoomTotal));
                    availableHotelRequest.setHoteltype("1");
                    availableHotelRequest.setDestinationId(SourceNameId);
                    availableHotelRequest.setChildrenAges(ChildAgeList);

                    Intent intent=new Intent(HotelActivity.this,BookHotel.class);
                    intent.putExtra("SourceName",SourceName);
                    startActivity(intent);
                }

                /*

                for (int j=0;j<ChildNoStringList.size();j++){

                    childrenAges.add(ChildAgeStringList);
                }

                for (int ag=0;ag<ChildAgeList.size();ag++){
                    String age=ChildAgeList.get(ag);
                    for (int k=0;k<age.length();k++){
                        char c=age.charAt(k);
                        if (c=='-' || c=='~'){

                        }else {
                            if (age.charAt(k+1)=='-' || age.charAt(k+1)=='~'){
                                ChildAgeStringList.add(String.valueOf(c));
                            }else {
                                ChildAgeStringList.add(age.substring(k,k+2));
                                k++;
                            }
                        }
                    }

                    if (!TextUtils.isEmpty(age)){
                        childrenAges.set(ag,ChildAgeStringList);
                        ChildAgeStringList.clear();
                    }
                }

                availableHotelRequest.setChildrenAges(childrenAges);

                */

                break;

            case R.id.tv_HotelCity:
                showDialogHotelSources();
                break;

            case R.id.travellersLayout_hotel:

                showDialogGuest();
                break;

            case R.id.tv_CheckIn_date:
                DatePickerHidePreviousDate datePicker = DatePickerHidePreviousDate.newInstance(null, CheckInTV);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.tv_CheckOut_date:
                DatePickerHidePreviousDate datePicker1 = DatePickerHidePreviousDate.newInstance(null, CheckOutTV);
                datePicker1.show(getSupportFragmentManager(), "datePicker");
                break;

        }

    }

    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    private void showDialogHotelSources() {

        dialog = new Dialog(HotelActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bus_location_list);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        recyclerViewLocationList = dialog.findViewById(R.id.bus_location_list);
        recyclerViewLocationList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        locationName=dialog.findViewById(R.id.locationNameBus);

        locationName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                dialog.dismiss();
                String city=locationName.getText().toString();
                int index=HotelSourcesList.indexOf(city);
                
                SourceName=city;
                SourceNameId=HotelSourcesIdList.get(index);
                locationName.setText("");
                HotelCityTV.setText(SourceName);
            }
        });

        Button back_btn_locationNameBus=dialog.findViewById(R.id.back_btn_locationNameBus);

        back_btn_locationNameBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();

        if (HotelSourcesList.size()>0){
            busSourcesAdapter=null;
            busSourcesAdapter = new HotelSourcesAdapter(getApplicationContext(),HotelSourcesList,HotelActivity.this);
            recyclerViewLocationList.setAdapter(busSourcesAdapter);
            busSourcesAdapter.notifyDataSetChanged();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(HotelActivity.this, android.R.layout.simple_dropdown_item_1line, HotelSourcesList);
            locationName.setAdapter(adapter);
            locationName.setThreshold(3);
        }else {
            getHotelLocation();
        }
    }

    private void getHotelLocation() {

        HotelSourcesList.clear();
        HotelSourcesIdList.clear();

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getHotelSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HotelSourcesResponse>() {
                    @Override
                    public void onSuccess(HotelSourcesResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                if (response.getData().size()>0){
                                    for (int i=0;i<response.getData().size();i++){
                                        HotelSourcesList.add(response.getData().get(i).getCityName());
                                        HotelSourcesIdList.add(String.valueOf(response.getData().get(i).getCityId()));
                                    }
                                    busSourcesAdapter = new HotelSourcesAdapter(getApplicationContext(),HotelSourcesList,HotelActivity.this);
                                    recyclerViewLocationList.setAdapter(busSourcesAdapter);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HotelActivity.this, android.R.layout.simple_dropdown_item_1line, HotelSourcesList);
                                    locationName.setAdapter(adapter);
                                    locationName.setThreshold(3);
                                }else {
                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel), "No Data Found", false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel), response.getMesage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_hotel), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onLocationClickTo(int position) {
        dialog.dismiss();
        SourceName=HotelSourcesList.get(position);
        SourceNameId=HotelSourcesIdList.get(position);
        HotelCityTV.setText(SourceName);
    }

    private void showDialogGuest() {

        final GuestSelectAdapter[] guestSelectAdapter = new GuestSelectAdapter[1];
        GuestDialog = new Dialog(HotelActivity.this);
        GuestDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        GuestDialog.setContentView(R.layout.dialog_guest_number_hotel);

        ChildNoList1=new ArrayList<>();
        AdultNoList1=new ArrayList<>();
        GuestNoList1=new ArrayList<>();
        RoomNoList1=new ArrayList<>();

        ChildNoList1.add(0);
        AdultNoList1.add(1);
        GuestNoList1.add(1);
        RoomNoList1.add(1);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = GuestDialog.getWindow();
        GuestDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        Button back_btn_locationNameBus=GuestDialog.findViewById(R.id.back_btn_hotelGuestDialog);
        Button AddRoomBtn=GuestDialog.findViewById(R.id.add_roomBtn);

        Button SubmitBtn=GuestDialog.findViewById(R.id.submit_roomBtn);


        back_btn_locationNameBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TravellerSelectMethod();
            }
        });

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TravellerSelectMethod();
            }
        });

        GuestListSelect = GuestDialog.findViewById(R.id.guest_select_list);
        GuestListSelect.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        GuestListSelect.setNestedScrollingEnabled(false);


        guestSelectAdapter[0] =new GuestSelectAdapter(HotelActivity.this,RoomNoList1,ChildNoList1,AdultNoList1,GuestNoList1);
        GuestListSelect.setAdapter(guestSelectAdapter[0]);

        AddRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RoomNoList1.size()<4){

                    ChildNoList1.add(0);
                    AdultNoList1.add(1);
                    GuestNoList1.add(1);
                    RoomNoList1.add(RoomNoList1.size()+1);

                    guestSelectAdapter[0].notifyDataSetChanged();
                }else {
                    Toast.makeText(HotelActivity.this, "Max Number Of Hotel Rooms Exceeded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        GuestDialog.show();
    }

    private void TravellerSelectMethod(){
        boolean checkAge=false;

        try {
            for (int ag=0;ag<ChildAgeList.size();ag++){
                String age=ChildAgeList.get(ag);
                if (age.contains("0")){
                    checkAge=false;
                    Toast.makeText(HotelActivity.this, "Please Enter Age Of Every Child", Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    checkAge=true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (checkAge){
            RoomTotal=RoomList.size();
            if (ChildNoList.size()>0){
                ChildTotal=0;
                for (int ch=0;ch<ChildNoList.size();ch++){
                    ChildTotal=ChildTotal+ChildNoList.get(ch);
                    ChildNoStringList.add(""+ChildNoList.get(ch));
                }
            }else {
                ChildTotal=0;
            }

            if (ChildNoList.size()>0){
                AdultTotal=0;
                for (int ad=0;ad<AdultNoList.size();ad++){
                    AdultTotal=AdultTotal+AdultNoList.get(ad);
                    AdultNoStringList.add(""+AdultNoList.get(ad));
                }
            }else {
                AdultTotal=1;
                RoomTotal=1;
                TotalGuest=1;
            }
            GuestDialog.dismiss();
            TravellerTV.setText("Rooms - "+RoomTotal+", Adult - "+AdultTotal+", Child - "+ChildTotal);
        }

    }
}
