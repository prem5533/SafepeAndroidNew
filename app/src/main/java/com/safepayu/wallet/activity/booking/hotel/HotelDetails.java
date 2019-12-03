package com.safepayu.wallet.activity.booking.hotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.RechargeHistory;
import com.safepayu.wallet.adapter.hotel.RoomListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.MySpannable;
import com.safepayu.wallet.models.request.booking_hotel.HotelDetailsRequest;
import com.safepayu.wallet.models.response.booking.HotelDetailResponse;
import com.squareup.picasso.Picasso;

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
    private String HotelId="",SourceName="",HotelName="",Provider="",WebService="",ImgUrl="",Description="",Facility="";
    private HotelDetailsRequest hotelDetailsRequest;
    private RecyclerView recyclerViewRoomList;

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
                        Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
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

        Toast.makeText(this, RoomDetailsList.get(position).getRoomType(), Toast.LENGTH_SHORT).show();

    }
}
