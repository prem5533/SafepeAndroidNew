package com.safepayu.wallet.activity.booking.hotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.DetailListAdapter;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking_hotel.BookHotelRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookHotel extends AppCompatActivity implements View.OnClickListener{

    private LoadingDialog loadingDialog;
    private TextView HotelCityTV,tvHotelName,tvHotelStar,tvHotelAddress,tvHotelFacility,tvHotelDescription;
    private ImageView ivHotelImage;
    private Button BackBtn;
    private String HotelId="",SourceName="",HotelName="",Provider1="",WebService="",ImgUrl="",Description="",Facility="";
    private DetailListAdapter detailListAdapter;
    private RecyclerView recyclerViewDetailList;

    private String Nationality="",Address="",MobileNo="",EmailId="",Provider="",Country="",CityId="";
    private String CityName="",arrivalDate="",DestinationId="",departureDate="";
    private int Status=0,rooms=0,hotelType=0;
    private BookHotelRequest.HotelDetailsBean hotelDetails;
    private BookHotelRequest.HotelImagesBean hotelImages;
    private BookHotelRequest.HotelPolicyBeanX HotelPolicy;
    private BookHotelRequest.GSTDetailsBean GSTDetails;
    private List<List<String>> Names,Ages,Genders,childrenAges;
    private List<BookHotelRequest.RoomDetailsBean> roomDetails;
    private List<String> adults,children;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_book);
        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);

        BackBtn=findViewById(R.id.backBtn_hotelBookLayout);
        HotelCityTV=findViewById(R.id.cityHotel_hotelBookLayout);

        recyclerViewDetailList = findViewById(R.id.detailList_hotelBookLayout);
        recyclerViewDetailList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDetailList.setNestedScrollingEnabled(false);

        detailListAdapter=new DetailListAdapter(BookHotel.this,Integer.parseInt(HotelActivity.availableHotelRequest.getRooms()));
        recyclerViewDetailList.setAdapter(detailListAdapter);

        BackBtn.setOnClickListener(this);

        try {
            Intent intent=getIntent();
            HotelId=intent.getStringExtra("HotelId");
            SourceName=intent.getStringExtra("SourceName");
            HotelName=intent.getStringExtra("HotelName");
            WebService=intent.getStringExtra("WebService");
            Provider=intent.getStringExtra("Provider");
            ImgUrl=intent.getStringExtra("ImgUrl");
            CityId=intent.getStringExtra("CityId");

            HotelCityTV.setText(HotelName+" - "+SourceName);

            Picasso.get().load(ImgUrl).into(ivHotelImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        HotelCityTV.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn_hotelDetailLayout:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;
        }
    }
}
