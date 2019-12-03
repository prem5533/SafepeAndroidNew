package com.safepayu.wallet.activity.booking.hotel;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.hotel.AvailableHotelAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.booking.hotel.AvailableHotelsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.booking.hotel.HotelActivity.availableHotelRequest;

public class AvailableHotels extends AppCompatActivity  implements View.OnClickListener,AvailableHotelAdapter.LocationListListener{

    private LoadingDialog loadingDialog;
    private TextView HotelCityTV;
    private Button BackBtn;
    private RecyclerView recyclerViewHotelList;
    private Geocoder geocoder;
    private List<AvailableHotelsResponse.DataBean.AvailableHotelsBean> AvailableHotelList;
    private AvailableHotelAdapter availableHotelAdapter;
    private ArrayList<String> LocalityList;
    private String locality="",SourceName="";

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_hotels);
        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);
        LocalityList=new ArrayList<>();
        AvailableHotelList=new ArrayList<>();

        HotelCityTV=findViewById(R.id.cityHotel);
        BackBtn=findViewById(R.id.backBtn_available_hotelsLayout);
        recyclerViewHotelList = findViewById(R.id.available_hotels_list);
        recyclerViewHotelList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

//        availableHotelAdapter=new AvailableHotelAdapter(AvailableHotelList.this,AvailableHotelList,LocalityList,AvailableHotelList.this);
//        recyclerViewHotelList.setAdapter(availableHotelAdapter);

        BackBtn.setOnClickListener(this);
        SourceName=getIntent().getStringExtra("SourceName");
        HotelCityTV.setText(SourceName);

        getHotelLocation();
    }

    private void getHotelLocation() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getHotelAvailable(availableHotelRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AvailableHotelsResponse>() {
                    @Override
                    public void onSuccess(AvailableHotelsResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                for (int i=0;i<response.getData().getAvailableHotels().size();i++){
                                    try {
                                        locality= GetLocality(Double.parseDouble(response.getData().getAvailableHotels().get(i).getLatitude()),Double.parseDouble(response.getData().getAvailableHotels().get(i).getLatitude()));
                                    }catch (Exception e){
                                        locality="";
                                        e.printStackTrace();
                                    }
                                    LocalityList.add(locality);
                                }
                                AvailableHotelList=response.getData().getAvailableHotels();
                                availableHotelAdapter=new AvailableHotelAdapter(AvailableHotels.this,response.getData().getAvailableHotels(),LocalityList,AvailableHotels.this);
                                recyclerViewHotelList.setAdapter(availableHotelAdapter);
                                availableHotelAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.available_hotelsLayout), response.getMesage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.available_hotelsLayout), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn_available_hotelsLayout:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;
        }
    }

    private String GetLocality( double latitude,double longitude){
        String subLocality="";
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
//                address = listAddresses.get(0).getAddressLine(0);
                  subLocality = listAddresses.get(0).getAdminArea();
//                city = listAddresses.get(0).getLocality();
//                state = listAddresses.get(0).getAdminArea();
//                country = listAddresses.get(0).getCountryName();
//                postalCode = listAddresses.get(0).getPostalCode();
//                knownName = listAddresses.get(0).getFeatureName();
//                subAdmin = listAddresses.get(0).getSubAdminArea();
//                subAdmin1 = listAddresses.get(0).getSubThoroughfare();
//                subAdmin2 = listAddresses.get(0).getPremises();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subLocality;
    }

    @Override
    public void onLocationClickTo(int position,String HotelId,String HotelName,String WebService,String Provider,String ImgUrl) {

        Intent intent=new Intent(new Intent(AvailableHotels.this,HotelDetails.class));
        intent.putExtra("HotelId",HotelId);
        intent.putExtra("SourceName",SourceName);
        intent.putExtra("HotelName",HotelName);
        intent.putExtra("WebService",WebService);
        intent.putExtra("Provider",Provider);
        intent.putExtra("ImgUrl",ImgUrl);
        startActivity(intent);

    }
}
