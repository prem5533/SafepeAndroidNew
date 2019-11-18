package com.safepayu.wallet.activity.booking.bus;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.bus.BusSourcesAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.DatePickerHidePreviousDate;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.booking.bus.BusSourcesResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BusActivity extends AppCompatActivity implements View.OnClickListener,BusSourcesAdapter.LocationListListener {

    private Button searchBusBtn,backBtn;
    private TextView tvFromBus,tvToBus,BusDate,DateTomorrow,DateToday;
    private Dialog dialog;
    private RecyclerView recyclerViewLocationList ;
    private LoadingDialog loadingDialog;
    private AutoCompleteTextView locationName;
    private ArrayList<String> BusSourcesList,BusSourcesIdList;
    private BusSourcesAdapter busSourcesAdapter;
    private ImageView UpDownBtn;
    private String SourceName="",SourceNameId="",DestinationName="",DestinationNameId="",SourceTypeeee="",Today="",Tomorrow="";

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);

        backBtn = findViewById(R.id.recharge_back_btn);
        searchBusBtn = findViewById( R.id.search_bus_btn);
        tvFromBus = findViewById( R.id.tv_from_bus);
        tvToBus = findViewById( R.id.tv_to_bus);
        UpDownBtn =findViewById(R.id.image_up_downBus);
        BusDate =findViewById(R.id.tv_bus_date);
        DateTomorrow= findViewById(R.id.tv_bus_tomorrow);
        DateToday=findViewById(R.id.tv_bus_today);

        //set listener
        backBtn.setOnClickListener(this);
        searchBusBtn.setOnClickListener(this);
        tvFromBus.setOnClickListener(this);
        tvToBus.setOnClickListener(this);
        BusDate.setOnClickListener(this);
        DateTomorrow.setOnClickListener(this);
        DateToday.setOnClickListener(this);

        BusSourcesList=new ArrayList<>();
        BusSourcesIdList=new ArrayList<>();

        UpDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Namev="",Idv="";

                Namev=SourceName;
                SourceName=DestinationName;
                DestinationName=Namev;

                Idv=SourceNameId;
                SourceNameId=DestinationNameId;
                DestinationNameId=Idv;

                tvFromBus.setText(SourceName);
                tvToBus.setText(DestinationName);
            }
        });

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Today = df.format(Calendar.getInstance().getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Tomorrow = df.format(calendar.getTime());

        BusDate.setText(Today);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R.id.search_bus_btn:

                startActivity(new Intent(BusActivity.this, BusListActivity.class));
                break;

            case R.id.tv_bus_date:

                DatePickerHidePreviousDate datePicker = DatePickerHidePreviousDate.newInstance(null, BusDate);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.tv_bus_tomorrow:

                BusDate.setText(Tomorrow);
                DateTomorrow.setTextColor(getResources().getColor(R.color.yellow_theme));
                DateToday.setTextColor(getResources().getColor(R.color.darker_gray));

            break;

            case R.id.tv_bus_today:

                BusDate.setText(Today);
                DateTomorrow.setTextColor(getResources().getColor(R.color.darker_gray));
                DateToday.setTextColor(getResources().getColor(R.color.yellow_theme));

                break;

            case R.id.tv_from_bus:

                SourceTypeeee="from";
                if (TextUtils.isEmpty(SourceName)){
                    showDialogBusSources(SourceTypeeee);
                }else {
                    busSourcesAdapter = new BusSourcesAdapter(getApplicationContext(),BusSourcesList,BusActivity.this,SourceTypeeee);
                    recyclerViewLocationList.setAdapter(busSourcesAdapter);
                    busSourcesAdapter.notifyDataSetChanged();

                    dialog.show();
                }
                break;

            case R.id.tv_to_bus:
                SourceTypeeee="to";
                if (TextUtils.isEmpty(SourceName)){
                    showDialogBusSources(SourceTypeeee);
                }else {
                    busSourcesAdapter = new BusSourcesAdapter(getApplicationContext(),BusSourcesList,BusActivity.this,SourceTypeeee);
                    recyclerViewLocationList.setAdapter(busSourcesAdapter);
                    busSourcesAdapter.notifyDataSetChanged();

                    dialog.show();
                }
                break;
        }

    }

    private void CheckValidate() {

        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_bus),"Coming Soon",false);
    }

    private void showDialogBusSources(final String SourceType ) {

        dialog = new Dialog(BusActivity.this);
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
                int index=BusSourcesList.indexOf(city);
                //locationName.setText(BusSourcesList.get(index));
                //locationName.setSelection(locationName.getText().toString().length());

                if (SourceType.equalsIgnoreCase(SourceTypeeee)){
                    SourceName=city;
                    SourceNameId=BusSourcesIdList.get(index);
                    tvFromBus.setText(SourceName);
                }else {
                    DestinationName=city;
                    DestinationNameId=BusSourcesIdList.get(index);
                    tvToBus.setText(DestinationName);
                }
                locationName.setText("");
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

        getBusLocation(SourceType);
    }

    private void getBusLocation(final String SourceType) {

        BusSourcesList.clear();
        BusSourcesIdList.clear();

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getBusSourcres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BusSourcesResponse>() {
                    @Override
                    public void onSuccess(BusSourcesResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {

                                if (response.getData().size()>0){
                                    for (int i=0;i<response.getData().size();i++){
                                        BusSourcesList.add(response.getData().get(i).getName());
                                        BusSourcesIdList.add(String.valueOf(response.getData().get(i).getId()));
                                    }
                                    busSourcesAdapter = new BusSourcesAdapter(getApplicationContext(),BusSourcesList,BusActivity.this,SourceType);
                                    recyclerViewLocationList.setAdapter(busSourcesAdapter);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BusActivity.this, android.R.layout.simple_dropdown_item_1line, BusSourcesList);
                                    locationName.setAdapter(adapter);
                                    locationName.setThreshold(3);
                                }else {
                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.flight_location_layout), "No Data Found", false);
                                }
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

    @Override
    public void onLocationClickFrom(int position, ArrayList<String> Buslist) {
        dialog.dismiss();
        SourceName=Buslist.get(position);
        SourceNameId=BusSourcesIdList.get(position);
        tvFromBus.setText(SourceName);
    }

    @Override
    public void onLocationClickTo(int position, ArrayList<String> Buslist) {
        dialog.dismiss();
        DestinationName=Buslist.get(position);
        DestinationNameId=BusSourcesIdList.get(position);
        tvToBus.setText(DestinationName);
    }
}
