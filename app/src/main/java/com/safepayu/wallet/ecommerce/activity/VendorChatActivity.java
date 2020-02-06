package com.safepayu.wallet.ecommerce.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.VendorChatAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.SendMessageToVendorRequest;
import com.safepayu.wallet.ecommerce.model.response.SendChatToVendorResponse;
import com.safepayu.wallet.ecommerce.model.response.VendorChatResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.ecommerce.adapter.VendorChatAdapter.statusWaitingImageVendor;

public class VendorChatActivity extends AppCompatActivity {

    private Button BackBtn;
    private TextView tvVenueName,tvVenueAddress,tvVenueContact;
    private RecyclerView chatRecyclerView;
    private ImageView SendBtn;
    private EditText edMessage;
    private String Message="",venueId="",orderId="",venueName="",contactNumber="";
    private LoadingDialog loadingDialog;
    private VendorChatAdapter chatAdapter;
    private VendorChatResponse vendorChatResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_chat_activity);
        findId();
    }

    private void findId() {
        loadingDialog=new LoadingDialog(this);

        BackBtn=findViewById(R.id.backBtn_chat);
        SendBtn=findViewById(R.id.sendMessageBtn_chat);
        edMessage=findViewById(R.id.message_chat);
        chatRecyclerView = findViewById(R.id.recycleChatList_chat);
        tvVenueName=findViewById(R.id.venueName_vendorChat);
        tvVenueAddress=findViewById(R.id.venueAddress_vendorChat);
        tvVenueContact=findViewById(R.id.venueContact_vendorChat);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        try {
            venueId=getIntent().getStringExtra("venueId");
            orderId=getIntent().getStringExtra("orderId");
            venueName=getIntent().getStringExtra("venueName");
            contactNumber=getIntent().getStringExtra("contactNumber");

            tvVenueName.setText("Seller Name - "+venueName);
            tvVenueContact.setText("Seller Contact - "+contactNumber);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (isNetworkAvailable()){
            getChatMessage(0);
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"No Internet Connection!",true);
        }

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message = edMessage.getText().toString().trim();
                if (isNetworkAvailable()){
                    if (!TextUtils.isEmpty(Message)){
                        sendMessageApi();
                    }
                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"No Internet Connection!",true);
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {
                if (isNetworkAvailable()){
                    getChatMessage(1);
                }
                handler.postDelayed( this, 30 * 1000 );
            }
        }, 60 * 1000 );


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getChatMessage(final int type) {
        if (type==0){
            loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        }

        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiServiceEcom.getMessageDetails(venueId,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<VendorChatResponse>(){
                    @Override
                    public void onSuccess(VendorChatResponse response) {
                        if (type==0){
                            loadingDialog.hideDialog();
                        }

                        if (response.isStatus()) {
                            try {
                                if (response.getData()==null){
                                    tvVenueAddress.setVisibility(View.GONE);
                                }else {
                                    vendorChatResponse=response;
                                    chatAdapter=new VendorChatAdapter(VendorChatActivity.this,vendorChatResponse.getData());
                                    chatRecyclerView.setAdapter(chatAdapter);
                                    if (response.getData().size()>0){
                                        if (TextUtils.isEmpty(response.getData().get(0).getAddress_1())){
                                            tvVenueAddress.setVisibility(View.GONE);
                                        }else {
                                            tvVenueAddress.setText("Seller Address - "+response.getData().get(0).getAddress_1());
                                        }
                                    }else {
                                        tvVenueAddress.setVisibility(View.GONE);
                                    }
                                }
                            }catch (Exception e){
                                Toast.makeText(VendorChatActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }else {
                            tvVenueAddress.setVisibility(View.GONE);
                            Toast.makeText(VendorChatActivity.this, response.getMessages(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (type==0){
                            loadingDialog.hideDialog();
                        }
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.chatLayout), false, e.getCause());
                    }
                }));
    }

    private void sendMessageApi() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        VendorChatResponse.DataBean dataBean=new VendorChatResponse.DataBean();
        List<VendorChatResponse.DataBean> dataBeanList=new ArrayList<>();
        VendorChatResponse vendorChatResponse1=new VendorChatResponse();


        dataBean.setId(0);
        dataBean.setFrom("cust");
        dataBean.setTo("venue");
        dataBean.setStatus(0);
        dataBean.setCreated_at(sdf.format(new Date()));
        dataBean.setUpdated_at(sdf.format(new Date()));
        dataBean.setMessage(Message);

        try {
            if (vendorChatResponse.getData()!=null){
                if (vendorChatResponse.getData().size()>0){
                    vendorChatResponse.getData().add(dataBean);
                }else {
                    dataBeanList.add(dataBean);
                    vendorChatResponse.setData(dataBeanList);
                }
            }else {
                dataBeanList.add(dataBean);
                vendorChatResponse.setData(dataBeanList);
            }
        }catch (Exception e){
            dataBeanList.add(dataBean);
            vendorChatResponse1.setData(dataBeanList);
            vendorChatResponse=vendorChatResponse1;
            chatAdapter=new VendorChatAdapter(VendorChatActivity.this,vendorChatResponse.getData());
            chatRecyclerView.setAdapter(chatAdapter);
            e.printStackTrace();
        }


        try {
            chatAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

        SendMessageToVendorRequest sendMessageToVendorRequest=new SendMessageToVendorRequest();
        sendMessageToVendorRequest.setCusttype("app");
        sendMessageToVendorRequest.setOrderid(orderId);
        sendMessageToVendorRequest.setVenueid(venueId);
        sendMessageToVendorRequest.setFrom("cust");
        sendMessageToVendorRequest.setTo("venue");
        sendMessageToVendorRequest.setMessage(Message);

        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.sendMessageToVendor(sendMessageToVendorRequest )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SendChatToVendorResponse>() {
                    @Override
                    public void onSuccess(SendChatToVendorResponse response) {
                        if (response.isStatus()) {

                            try {
                                edMessage.setText("");
                                edMessage.setHint("Enter Message");
                                statusWaitingImageVendor.setImageDrawable(getResources().getDrawable(R.drawable.tick_chat_new));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),response.getSuccess(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.chatLayout), true, e);
                    }
                })
        );
    }
}
