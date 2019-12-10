package com.safepayu.wallet.activity.booking.hotel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
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
import com.safepayu.wallet.adapter.hotel.RoomListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.MySpannable;
import com.safepayu.wallet.models.request.booking_hotel.BookHotelRequest;
import com.safepayu.wallet.models.request.booking_hotel.HotelDetailsRequest;
import com.safepayu.wallet.models.response.booking.HotelDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.booking.hotel.HotelActivity.availableHotelRequest;

public class HotelDetails extends AppCompatActivity implements View.OnClickListener,RoomListAdapter.LocationListListener{

    private LoadingDialog loadingDialog;
    private TextView HotelCityTV,tvHotelName,tvHotelStar,tvHotelAddress,tvHotelFacility,tvHotelDescription;
    private ImageView ivHotelImage;
    private Button BackBtn;
    private String HotelId="",SourceName="",HotelName="",Provider="",WebService="",ImgUrl="",Description="",Facility="",DestinationId="";
    private String MobileNo="",EmailId="",GenderDialog="";
    private HotelDetailsRequest hotelDetailsRequest;
    private RecyclerView recyclerViewRoomList;
    private Dialog fillDetailDialog;
    private List<List<String>> NamesPList,AgesPList,GendersPList;
    private List<String> NamesChList,AgesChList,GendersChList;
    HotelDetailResponse hotelDetailResponse;
    BookHotelRequest bookHotelRequest=new BookHotelRequest();

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);
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
        hotelDetailResponse=new HotelDetailResponse();

        HotelCityTV=findViewById(R.id.cityHotel_hotelDetailLayout);
        BackBtn=findViewById(R.id.backBtn_hotelDetailLayout);
        tvHotelName=findViewById(R.id.hotelName_hotelDetailLayout);
        tvHotelStar=findViewById(R.id.star_hotelDetailLayout);
        tvHotelAddress=findViewById(R.id.hotelAdd_hotelDetailLayout);
        tvHotelDescription=findViewById(R.id.hotelDescription_hotelDetailLayout);
        tvHotelFacility=findViewById(R.id.hotelFacility_hotelDetailLayout);
        ivHotelImage=findViewById(R.id.hotelImage_hotelDetailLayout);
        recyclerViewRoomList = findViewById(R.id.rooms_list_hotelDetailLayout);
        recyclerViewRoomList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewRoomList.setNestedScrollingEnabled(false);

        BackBtn.setOnClickListener(this);

        try {
            Intent intent=getIntent();
            HotelId=intent.getStringExtra("HotelId");
            SourceName=intent.getStringExtra("SourceName");
            HotelName=intent.getStringExtra("HotelName");
            WebService=intent.getStringExtra("WebService");
            Provider=intent.getStringExtra("Provider");
            ImgUrl=intent.getStringExtra("ImgUrl");
            DestinationId=intent.getStringExtra("DestinationId");

            HotelCityTV.setText(HotelName+" - "+SourceName);

            Picasso.get().load(ImgUrl).into(ivHotelImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        hotelDetailsRequest=new HotelDetailsRequest();
        hotelDetailsRequest.setAdults(availableHotelRequest.getAdults());
        hotelDetailsRequest.setArrivalDate(availableHotelRequest.getArrivalDate());
        hotelDetailsRequest.setChildren(availableHotelRequest.getChildren());
        hotelDetailsRequest.setChildrenAges(availableHotelRequest.getChildrenAges());
        hotelDetailsRequest.setDepartureDate(availableHotelRequest.getDepartureDate());
        hotelDetailsRequest.setDestinationId(availableHotelRequest.getDestinationId());
        hotelDetailsRequest.setArrivalDate(availableHotelRequest.getArrivalDate());
        hotelDetailsRequest.setHoteltype(availableHotelRequest.getHoteltype());
        hotelDetailsRequest.setNoOfDays(availableHotelRequest.getNoOfDays());
        hotelDetailsRequest.setCityId(availableHotelRequest.getDestinationId());
        hotelDetailsRequest.setRooms(availableHotelRequest.getRooms());
        hotelDetailsRequest.setRoomscount(availableHotelRequest.getRooms());
        hotelDetailsRequest.setProvider(Provider);
        hotelDetailsRequest.setWebService(WebService);
        hotelDetailsRequest.setHotelId(HotelId);

        getHotelDetail();
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    private void getHotelDetail() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getHotelDetails(hotelDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HotelDetailResponse>() {
                    @Override
                    public void onSuccess(HotelDetailResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {

                                hotelDetailResponse=response;
                                tvHotelName.setText(response.getData().getHotelName());
                                tvHotelStar.setText(response.getData().getStarRating()+" Star Hotel");
                                tvHotelAddress.setText(response.getData().getHotelAddress());
                                tvHotelName.setText(response.getData().getHotelName());

                                Facility=response.getData().getFacilities();
                                tvHotelFacility.setText(Facility);
                                makeTextViewResizable(tvHotelFacility, 3, "See More", true);

                                Description=response.getData().getDescription();
                                tvHotelDescription.setText(Description);
                                makeTextViewResizable(tvHotelDescription, 3, "See More", true);

                                RoomListAdapter roomListAdapter=new RoomListAdapter(HotelDetails.this,response.getData().getRoomDetails(),HotelDetails.this);
                                recyclerViewRoomList.setAdapter(roomListAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.hotelDetailLayout), response.getMesage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.hotelDetailLayout), false, e.getCause());
                    }
                }));
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

    @Override
    public void onLocationClickTo(int position, List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList) {

        showMessage(RoomDetailsList.get(position).getRoomType(),RoomDetailsList);

        List<BookHotelRequest.RoomDetailsBean> roomDetails=new ArrayList<>();

        BookHotelRequest.RoomDetailsBean roomDetailsBean=new BookHotelRequest.RoomDetailsBean();
        roomDetailsBean.setSoldout(hotelDetailResponse.getData().getRoomDetails().get(position).isSoldout());
        roomDetailsBean.setRoomIndex(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomIndex());
        roomDetailsBean.setRateType(hotelDetailResponse.getData().getRoomDetails().get(position).getRateType());
        roomDetailsBean.setHotelPackage(hotelDetailResponse.getData().getRoomDetails().get(position).getHotelPackage());
        roomDetailsBean.setRoomType(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomType());
        roomDetailsBean.setRoomBasis(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomBasis());
        roomDetailsBean.setRoomTypeCode(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomTypeCode());
        roomDetailsBean.setRatePlanCode(hotelDetailResponse.getData().getRoomDetails().get(position).getRatePlanCode());
        roomDetailsBean.setValiddays(hotelDetailResponse.getData().getRoomDetails().get(position).getValiddays());
        roomDetailsBean.setWsKey(hotelDetailResponse.getData().getRoomDetails().get(position).getWsKey());
        roomDetailsBean.setExtGuestTotal(hotelDetailResponse.getData().getRoomDetails().get(position).getExtGuestTotal());
        roomDetailsBean.setRoomTotal(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomTotal());
        roomDetailsBean.setRoomNetTotal(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomNetTotal());
        roomDetailsBean.setServicetaxTotal(hotelDetailResponse.getData().getRoomDetails().get(position).getServicetaxTotal());
        roomDetailsBean.setDiscount(hotelDetailResponse.getData().getRoomDetails().get(position).getDiscount());
        roomDetailsBean.setCommission(hotelDetailResponse.getData().getRoomDetails().get(position).getCommission());
        roomDetailsBean.setExpediaPropertyId(hotelDetailResponse.getData().getRoomDetails().get(position).getExpediaPropertyId());
        roomDetailsBean.setRoomCancellationPolicy(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomCancellationPolicy());
        roomDetailsBean.setNoOfPax(hotelDetailResponse.getData().getRoomDetails().get(position).getNoOfPax());
        roomDetailsBean.setRefundRule(hotelDetailResponse.getData().getRoomDetails().get(position).getRefundRule());
        roomDetailsBean.setInclusions(hotelDetailResponse.getData().getRoomDetails().get(position).getInclusions());
        roomDetailsBean.setExclusions(hotelDetailResponse.getData().getRoomDetails().get(position).getExclusions());
        roomDetailsBean.setRoomCount(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomCount());
        roomDetailsBean.setMaxAdultOccupancy(hotelDetailResponse.getData().getRoomDetails().get(position).getMaxAdultOccupancy());
        roomDetailsBean.setMaxChildOccupancy(hotelDetailResponse.getData().getRoomDetails().get(position).getMaxChildOccupancy());
        roomDetailsBean.setBedType(hotelDetailResponse.getData().getRoomDetails().get(position).getBedType());
        roomDetailsBean.setRoomView(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomView());
        roomDetailsBean.setRoomDescription(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomDescription());
        roomDetailsBean.setRoomAmenities(hotelDetailResponse.getData().getRoomDetails().get(position).getRoomAmenities());
        roomDetailsBean.setIncExcCondition(hotelDetailResponse.getData().getRoomDetails().get(position).getIncExcCondition());
        roomDetailsBean.setIsInclusion(false);
        roomDetailsBean.setImages(hotelDetailResponse.getData().getRoomDetails().get(position).getImages());

        BookHotelRequest.RoomDetailsBean.PartnerFareDatailsBean partnerFareDatailsBean=new BookHotelRequest.RoomDetailsBean.PartnerFareDatailsBean();
        partnerFareDatailsBean.setCommission(hotelDetailResponse.getData().getRoomDetails().get(position).getPartnerFareDatails().getCommission());
        partnerFareDatailsBean.setCommissionType(hotelDetailResponse.getData().getRoomDetails().get(position).getPartnerFareDatails().getCommissionType());
        partnerFareDatailsBean.setNetFares(hotelDetailResponse.getData().getRoomDetails().get(position).getPartnerFareDatails().getNetFares());

        roomDetailsBean.setPartnerFareDatails(partnerFareDatailsBean);
        roomDetails.add(roomDetailsBean);

        bookHotelRequest.setRoomDetails(roomDetails);
    }

    public void showMessage(String Message,List<HotelDetailResponse.DataBean.RoomDetailsBean> RoomDetailsList) {
        new AlertDialog.Builder(HotelDetails.this)
                .setTitle("Room Detail")
                .setMessage(Message)
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Intent intent=new Intent(HotelDetails.this,BookHotel.class);
                        intent.putExtra("HotelId",HotelId);
                        intent.putExtra("SourceName",SourceName);
                        intent.putExtra("HotelName",HotelName);
                        intent.putExtra("WebService",WebService);
                        intent.putExtra("Provider",Provider);
                        intent.putExtra("ImgUrl",ImgUrl);
                        intent.putExtra("CityId",hotelDetailsRequest.getCityId());
                        //startActivity(intent);
                        showDialogFillDetail();
                        dialog.dismiss();

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                    }
                })

                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
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
                Toast.makeText(HotelDetails.this, rb.getText(), Toast.LENGTH_SHORT).show();
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

                                        bookHotelRequest.setNationality("IN");
                                        bookHotelRequest.setNames(NamesPList);
                                        bookHotelRequest.setAges(AgesPList);
                                        bookHotelRequest.setGenders(GendersPList);
                                        bookHotelRequest.setAddress(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ADDRESS));
                                        bookHotelRequest.setEmailId(EmailId);
                                        bookHotelRequest.setMobileNo(MobileNo);
                                        bookHotelRequest.setCountry("India");
                                        bookHotelRequest.setProvider(Provider);
                                        bookHotelRequest.setStatus(1);
                                        bookHotelRequest.setDestinationId(DestinationId);
                                        bookHotelRequest.setCityName(SourceName);
                                        bookHotelRequest.setArrivalDate(availableHotelRequest.getArrivalDate());
                                        bookHotelRequest.setDepartureDate(availableHotelRequest.getDepartureDate());
                                        bookHotelRequest.setRooms(Integer.parseInt(availableHotelRequest.getRooms()));
                                        bookHotelRequest.setAdults(availableHotelRequest.getAdults());
                                        bookHotelRequest.setChildren(availableHotelRequest.getChildren());
                                        //bookHotelRequest.setChildrenAges(availableHotelRequest.getChildrenAges());
                                        bookHotelRequest.setHotelType(Integer.parseInt(availableHotelRequest.getHoteltype()));





                                        fillDetailDialog.dismiss();
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
