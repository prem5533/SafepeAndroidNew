package com.safepayu.wallet.activity.booking.hotel;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.hotel.HotelSourcesAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.DatePickerHidePreviousDate;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.booking.hotel.HotelSourcesResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class HotelActivity extends AppCompatActivity implements View.OnClickListener,HotelSourcesAdapter.LocationListListener {

    private Button searhHotelBtn, backBtn;
    private Dialog dialog;
    private TextView HotelCityTV,AdultNumberTV,ChildNumberTV,RoomsTV,CheckInTV,CheckOutTV;
    private RecyclerView recyclerViewLocationList ;
    private LoadingDialog loadingDialog;
    private AutoCompleteTextView locationName;
    private ArrayList<String> HotelSourcesList,HotelSourcesIdList;
    private HotelSourcesAdapter busSourcesAdapter;
    private String SourceName="",SourceNameId="";
    private int TotalGuest=0;
    private ImageView MinusAdultOrange,PlusAdultOrange,MinusChildOrange,PlusChildOrange,MinusRoomOrange,PlusRoomOrange;

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

        backBtn = findViewById(R.id.recharge_back_btn);
        searhHotelBtn = findViewById(R.id.search_hotel_btn);
        AdultNumberTV=findViewById(R.id.tvAdultsCount_HotelSources);
        ChildNumberTV=findViewById(R.id.tvChildrenCount_HotelSources);
        HotelCityTV=findViewById(R.id.tv_HotelCity);
        RoomsTV=findViewById(R.id.tv_number_Hotel);
        CheckInTV=findViewById(R.id.tv_CheckIn_date);
        CheckOutTV=findViewById(R.id.tv_CheckOut_date);

        MinusAdultOrange=findViewById(R.id.imageAdultsMinus_HotelSources);
        PlusAdultOrange=findViewById(R.id.imageAdultsPlus_HotelSources);
        MinusChildOrange=findViewById(R.id.imageChildrenMinus_HotelSources);
        PlusChildOrange=findViewById(R.id.imageChildrenPlus_HotelSources);
        MinusRoomOrange=findViewById(R.id.roomsMinus_HotelSources);
        PlusRoomOrange=findViewById(R.id.roomsPlus_HotelSources);

        //set listener
        backBtn.setOnClickListener(this);
        searhHotelBtn.setOnClickListener(this);
        HotelCityTV.setOnClickListener(this);
        MinusAdultOrange.setOnClickListener(this);
        PlusAdultOrange.setOnClickListener(this);
        MinusChildOrange.setOnClickListener(this);
        PlusChildOrange.setOnClickListener(this);
        CheckInTV.setOnClickListener(this);
        CheckOutTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;
                
            case R.id.search_hotel_btn:
                
                break;

            case R.id.tv_HotelCity:
                showDialogHotelSources();
                break;

            case R.id.tv_CheckIn_date:
                DatePickerHidePreviousDate datePicker = DatePickerHidePreviousDate.newInstance(null, CheckInTV);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.tv_CheckOut_date:
                DatePickerHidePreviousDate datePicker1 = DatePickerHidePreviousDate.newInstance(null, CheckOutTV);
                datePicker1.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.imageAdultsMinus_HotelSources:

                if (Integer.parseInt(AdultNumberTV.getText().toString())==1){
                    MinusAdultOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus_gray));
                }else {
                    TotalGuest=TotalGuest-1;
                    AdultNumberTV.setText(String.valueOf(Integer.parseInt(AdultNumberTV.getText().toString())-1));
                    MinusAdultOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus));
                    if (Integer.parseInt(AdultNumberTV.getText().toString())==1) {
                        MinusAdultOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus_gray));
                    }

                    AdultChildRooms();

//                    if (Integer.parseInt(AdultNumberTV.getText().toString())==12 || Integer.parseInt(AdultNumberTV.getText().toString())==8
//                            || Integer.parseInt(AdultNumberTV.getText().toString())==4) {
//                        if (Integer.parseInt(RoomsTV.getText().toString())==1){
//
//                        }else {
//                            RoomsTV.setText(String.valueOf(Integer.parseInt(RoomsTV.getText().toString())-1));
//                        }
//
//                    }
                }

                break;

            case R.id.imageAdultsPlus_HotelSources:
                //ic_plus_gray
                if (Integer.parseInt(AdultNumberTV.getText().toString())==16){
                    PlusAdultOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_gray));
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel),"Maximum Number Of Adults Exceeded",true);
                }else {
                    TotalGuest=TotalGuest+1;
                    AdultNumberTV.setText(String.valueOf(Integer.parseInt(AdultNumberTV.getText().toString())+1));
                    MinusAdultOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus));
                    PlusAdultOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));

                    AdultChildRooms();

//                    if (Integer.parseInt(AdultNumberTV.getText().toString())==5 || Integer.parseInt(AdultNumberTV.getText().toString())==9
//                            || Integer.parseInt(AdultNumberTV.getText().toString())==13) {
//                        if (Integer.parseInt(RoomsTV.getText().toString())==4) {
//                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel),"Maximum Number Of Booked Rooms Can Only Be 4",true);
//                        }else {
//                            RoomsTV.setText(String.valueOf(Integer.parseInt(RoomsTV.getText().toString())+1));
//                        }
//                    }
                }
                break;

            case R.id.imageChildrenMinus_HotelSources:
                if (Integer.parseInt(ChildNumberTV.getText().toString())==0){
                    MinusChildOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus_gray));
                }else {
                    TotalGuest=TotalGuest-1;
                    ChildNumberTV.setText(String.valueOf(Integer.parseInt(ChildNumberTV.getText().toString())-1));
                    MinusChildOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus));
                    if (Integer.parseInt(ChildNumberTV.getText().toString())==0) {
                        MinusChildOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus_gray));
                    }

                    AdultChildRooms();

//                    if (Integer.parseInt(ChildNumberTV.getText().toString())==6 || Integer.parseInt(ChildNumberTV.getText().toString())==4
//                            || Integer.parseInt(ChildNumberTV.getText().toString())==2) {
//                        if (Integer.parseInt(RoomsTV.getText().toString())==1){
//
//                        }else {
//                            RoomsTV.setText(String.valueOf(Integer.parseInt(RoomsTV.getText().toString())-1));
//                        }
//                    }
                }
                break;

            case R.id.imageChildrenPlus_HotelSources:
                if (Integer.parseInt(ChildNumberTV.getText().toString())==8){
                    PlusChildOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_gray));
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel),"Maximum Number Of Adults Exceeded",true);
                }else {
                    TotalGuest=TotalGuest+1;
                    ChildNumberTV.setText(String.valueOf(Integer.parseInt(ChildNumberTV.getText().toString())+1));
                    MinusChildOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus));
                    PlusChildOrange.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));

                    AdultChildRooms();

//                    if (Integer.parseInt(ChildNumberTV.getText().toString())==3 || Integer.parseInt(ChildNumberTV.getText().toString())==5
//                            || Integer.parseInt(ChildNumberTV.getText().toString())==7) {
//                        if (Integer.parseInt(RoomsTV.getText().toString())==4) {
//                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel),"Maximum Number Of Booked Rooms Can Only Be 4",true);
//                        }else {
//                            RoomsTV.setText(String.valueOf(Integer.parseInt(RoomsTV.getText().toString())+1));
//                        }
//                    }
                }
                break;

            case R.id.roomsMinus_HotelSources:

                break;

            case R.id.roomsPlus_HotelSources:

                break;

        }

    }

    private void AdultChildRooms(){
        if (Integer.parseInt(AdultNumberTV.getText().toString())>1 && Integer.parseInt(AdultNumberTV.getText().toString())<5){
            if (TotalGuest>0 && TotalGuest<7){
                RoomsTV.setText(""+1);
            }
        }else if (Integer.parseInt(AdultNumberTV.getText().toString())>4 && Integer.parseInt(AdultNumberTV.getText().toString())<9){
            if (TotalGuest>0 && TotalGuest<13){
                RoomsTV.setText(""+2);
            }
        }else if (Integer.parseInt(AdultNumberTV.getText().toString())>8 && Integer.parseInt(AdultNumberTV.getText().toString())<13){
            if (TotalGuest>0 && TotalGuest<19){
                RoomsTV.setText(""+3);
            }
        }else if (Integer.parseInt(AdultNumberTV.getText().toString())>12 && Integer.parseInt(AdultNumberTV.getText().toString())<17){
            if (TotalGuest>0 && TotalGuest<24){
                RoomsTV.setText(""+4);
            }
        }else {
           // BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_hotel),"Maximum Number Of Adults Exceeded",true);
        }
    }

    private void showDialogHotelSources() {

        dialog = new Dialog(HotelActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bus_location_list);

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
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
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
}
