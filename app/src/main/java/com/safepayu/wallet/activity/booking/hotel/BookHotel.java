package com.safepayu.wallet.activity.booking.hotel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.DetailListAdapter;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking_hotel.BookHotelRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookHotel extends AppCompatActivity implements View.OnClickListener{

    private LoadingDialog loadingDialog;
    private TextView HotelCityTV,tvHotelName,tvHotelStar,tvHotelAddress,tvHotelFacility,tvHotelDescription;
    private ImageView ivHotelImage;
    private Button BackBtn;
    private String HotelId="",SourceName="",HotelName="",Provider1="",WebService="",ImgUrl="",Description="",Facility="";
    private String NameDialog="",AgeDialog="",EmailDialog="",GenderDialog="",MobileDialog="";
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
    private Dialog fillDetailDialog;
    private List<List<String>> NamesPList,AgesPList,GendersPList;
    private List<String> NamesChList,AgesChList,GendersChList;
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

        NamesPList=new ArrayList<>();
        AgesPList=new ArrayList<>();
        GendersPList=new ArrayList<>();

        NamesChList=new ArrayList<>();
        AgesChList=new ArrayList<>();
        GendersChList=new ArrayList<>();

        BackBtn=findViewById(R.id.backBtn_hotelBookLayout);
        HotelCityTV=findViewById(R.id.cityHotel_hotelBookLayout);

        recyclerViewDetailList = findViewById(R.id.detailList_hotelBookLayout);
        recyclerViewDetailList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDetailList.setNestedScrollingEnabled(false);

        detailListAdapter=new DetailListAdapter(BookHotel.this,Integer.parseInt(HotelActivity.availableHotelRequest.getRooms()));
        //recyclerViewDetailList.setAdapter(detailListAdapter);

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
        showDialogFillDetail();
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

    private void showDialogFillDetail( ) {

        fillDetailDialog = new Dialog(this);
        fillDetailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fillDetailDialog.setContentView(R.layout.dialog_fill_hotel_detail);
        fillDetailDialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = fillDetailDialog.getWindow();
        fillDetailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        final EditText edName,edMobile,edEmail,edAge;
        RadioGroup radioGroupGender;

        edName=fillDetailDialog.findViewById(R.id.name_fillDetailHotelDialog);
        edMobile=fillDetailDialog.findViewById(R.id.phone_fillDetailHotelDialog);
        edEmail=fillDetailDialog.findViewById(R.id.email_fillDetailHotelDialog);
        edAge=fillDetailDialog.findViewById(R.id.age_fillDetailHotelDialog);
        radioGroupGender=fillDetailDialog.findViewById(R.id.radioGrp_fillDetailHotelDialog);

        if (NamesChList.size()>0){
            edName.setText(NamesChList.get(0));
            edAge.setText(AgesChList.get(0));
            edEmail.setText(EmailId);
            edMobile.setText(MobileNo);

            try {
                if (GendersChList.get(0).equalsIgnoreCase("Male")){
                    ((RadioButton)radioGroupGender.getChildAt(0)).setChecked(true);
                }else if (GendersChList.get(0).equalsIgnoreCase("Female")){
                    ((RadioButton)radioGroupGender.getChildAt(1)).setChecked(true);
                }else {
                    ((RadioButton)radioGroupGender.getChildAt(2)).setChecked(true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb=fillDetailDialog.findViewById(checkedId);
                GenderDialog=rb.getText().toString();
                Toast.makeText(BookHotel.this, rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        Button backBtn_fillDetailDialog=fillDetailDialog.findViewById(R.id.backBtn_fillDetailHotelDialog);

        backBtn_fillDetailDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillDetailDialog.dismiss();
            }
        });

        Button proceedBtn_fillDetailDialog=fillDetailDialog.findViewById(R.id.proceedBtn_fillDetailHotelDialog);

        proceedBtn_fillDetailDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    GetGuestDetail(edName.getText().toString().trim(),edAge.getText().toString().trim(),edEmail.getText().toString().trim(),
                            edMobile.getText().toString().trim(),GenderDialog);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        fillDetailDialog.show();

    }

    private void GetGuestDetail(String NameDialog,String AgeDialog,String EmailDialog,String MobileDialog,String GenderDialog){

        if (TextUtils.isEmpty(NameDialog)){
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
        }else {
            if (TextUtils.isEmpty(AgeDialog)){
                Toast.makeText(this, "Please Enter Your Age", Toast.LENGTH_SHORT).show();
            }else {
                if (Integer.parseInt(AgeDialog)>100){
                    Toast.makeText(this, "Please Enter Correct Your Age", Toast.LENGTH_SHORT).show();
                }else {
                    if (TextUtils.isEmpty(EmailDialog)){
                        Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    }else {
                        if (!BaseApp.getInstance().commonUtils().isValidEmail(EmailDialog)){
                            Toast.makeText(this, "Please Enter Correct Your Email", Toast.LENGTH_SHORT).show();
                        }else {
                            if (TextUtils.isEmpty(MobileDialog)){
                                Toast.makeText(this, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                            }else {
                                if (MobileDialog.length()<10){
                                    Toast.makeText(this, "Please Enter Your Correct Mobile Number", Toast.LENGTH_SHORT).show();
                                }else {
                                    if (TextUtils.isEmpty(GenderDialog)){
                                        Toast.makeText(this, "Please Select Your Gender", Toast.LENGTH_SHORT).show();
                                    }else {
                                        MobileNo=MobileDialog;
                                        EmailId=EmailDialog;

                                        if (NamesChList.size()>0){
                                            NamesChList.set(0,NameDialog);
                                            AgesChList.set(0,AgeDialog);
                                            GendersChList.set(0,GenderDialog);

                                            NamesPList.set(0,NamesChList);
                                            AgesPList.set(0,AgesChList);
                                            GendersPList.set(0,GendersChList);
                                        }else {
                                            NamesChList.add(NameDialog);
                                            AgesChList.add(AgeDialog);
                                            GendersChList.add(GenderDialog);

                                            NamesPList.add(NamesChList);
                                            AgesPList.add(AgesChList);
                                            GendersPList.add(GendersChList);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
