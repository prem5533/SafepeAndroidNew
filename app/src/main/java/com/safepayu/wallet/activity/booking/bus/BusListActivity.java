package com.safepayu.wallet.activity.booking.bus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.bus.BusListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking_bus.BusListRequest;
import com.safepayu.wallet.models.response.booking.bus.BusListResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BusListActivity extends AppCompatActivity implements View.OnClickListener, BusListAdapter.OnBusItemClickListener {

    private BusListAdapter busListAdapter;
    private RecyclerView recyclerViewBus;
    private Button backBtn;
    private LoadingDialog loadingDialog;
    private TextView SourceDestinationTV;
    private String SourceName="",SourceNameId="",DestinationName="",DestinationNameId="",SourceTypeeee="",BusDate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);
        recyclerViewBus = findViewById(R.id.list_bus);
        backBtn = findViewById(R.id.backbtn_bus_list);
        SourceDestinationTV=findViewById(R.id.journeySourceDestination_busList);
        
        try{
            Intent intent=getIntent();
            SourceName=intent.getStringExtra("SourceName");
            SourceNameId=intent.getStringExtra("SourceNameId");
            DestinationName=intent.getStringExtra("DestinationName");
            DestinationNameId=intent.getStringExtra("DestinationNameId");
            BusDate=intent.getStringExtra("BusDate");

            BusListRequest busListRequest=new BusListRequest();
            busListRequest.setDestinationId(DestinationNameId);
            busListRequest.setSourceId(SourceNameId);
            busListRequest.setTripType("1");
            busListRequest.setJourneyDate(BusDate);
            busListRequest.setReturnDate("");

            SourceDestinationTV.setText(SourceName+" To "+DestinationName);

            getBusList(busListRequest);



        }catch (Exception e){
            e.printStackTrace();
        }
        
        backBtn.setOnClickListener(this);

        recyclerViewBus.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_bus_list:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
        }
    }

    @Override
    public void onBusItemSelect() {
        overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
        startActivity(new Intent(BusListActivity.this, BusSeat_DetailActivity.class));
    }

    private void getBusList(BusListRequest busListRequest) {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getBusAvailable(busListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BusListResponse>() {
                    @Override
                    public void onSuccess(BusListResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                if (response.getData().getAvailableTrips().size()>0){
                                    busListAdapter = new BusListAdapter(BusListActivity.this,response.getData(),BusListActivity.this);
                                    recyclerViewBus.setAdapter(busListAdapter);
                                }else {
                                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.busListLayout), "No Data Found", false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.busListLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.busListLayout), false, e.getCause());
                    }
                }));

    }
}
