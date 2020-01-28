package com.safepayu.wallet.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.ChatAdapter;
import com.safepayu.wallet.ecommerce.model.request.ChatSendRequest;
import com.safepayu.wallet.ecommerce.model.response.ChatListResponse;
import com.safepayu.wallet.models.response.BaseResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.ecommerce.adapter.ChatAdapter.statusWaitingImage;

public class ChatActivity extends AppCompatActivity {

    private Button BackBtn;
    private TextView NewIssueBtn;
    private RecyclerView chatRecyclerView;
    private ImageView SendBtn,AttachBtn;
    private EditText edMessage;
    private String Message="",Title="",textBase64="";
    private LoadingDialog loadingDialog;
    private ChatAdapter chatAdapter;
    private ChatListResponse chatListResponse;
    private Spinner titleSpinner;
    private List<String> TitleList;
    private static final int PICK_IMAGE_CAMERA = 223;
    private static final int PICK_IMAGE_GALLERY = 623;
    public static Bitmap bitmapChat;
    private File destination = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        findId();
    }

    private void findId() {
        loadingDialog=new LoadingDialog(this);
        TitleList=new ArrayList<>();

        BackBtn=findViewById(R.id.backBtn_chat);
        NewIssueBtn=findViewById(R.id.newIssue_chat);
        AttachBtn=findViewById(R.id.sendAttachmentBtn_chat);
        SendBtn=findViewById(R.id.sendMessageBtn_chat);
        edMessage=findViewById(R.id.message_chat);
        titleSpinner=findViewById(R.id.titleSpinner_chat);
        chatRecyclerView = findViewById(R.id.recycleChatList_chat);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        if (isNetworkAvailable()){
            getChatList(0);
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"No Internet Connection!",true);
        }

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        NewIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleSpinner.setVisibility(View.VISIBLE);
                Title="";
            }
        });

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message = edMessage.getText().toString().trim();

                if (TextUtils.isEmpty(Title)){
                    titleSpinner.setVisibility(View.VISIBLE);
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"Please Select Issue",true);
                }else {
                   if (TextUtils.isEmpty(textBase64) && TextUtils.isEmpty(Message)){
                       BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"Please Enter Any Message",true);
                   }else {
                       sendMessageApi(Title,Message);
                   }
                }
            }
        });

        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos!=0){
                    Title=titleSpinner.getItemAtPosition(pos).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AttachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {
                if (isNetworkAvailable()){
                    getChatList(1);
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

    private void selectImage() {
        try {
            PackageManager pm = ChatActivity.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, ChatActivity.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Pick From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Pick From Gallery")) {
                            dialog.dismiss();
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(ChatActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ChatActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                bitmapChat = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmapChat.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textBase64 = ConvertToBase64(bitmapChat);

                if (TextUtils.isEmpty(Title)){
                    titleSpinner.setVisibility(View.VISIBLE);
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"Please Select Issue",true);
                }else {
                    if (TextUtils.isEmpty(textBase64) && TextUtils.isEmpty(Message)){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"Please Enter Any Message",true);
                    }else {
                        sendMessageApi(Title,Message);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {

            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    try {
                        bitmapChat = MediaStore.Images.Media.getBitmap(ChatActivity.this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    textBase64 = ConvertToBase64(bitmapChat);
                    if (TextUtils.isEmpty(Title)){
                        titleSpinner.setVisibility(View.VISIBLE);
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"Please Select Issue",true);
                    }else {
                        if (TextUtils.isEmpty(textBase64) && TextUtils.isEmpty(Message)){
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),"Please Enter Any Message",true);
                        }else {
                            sendMessageApi(Title,Message);
                        }
                    }
                    //imageView.setImageDrawable(d);
                }
            }

        }
    }

    private String ConvertToBase64(Bitmap bitmapChat){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapChat.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }


    private void getChatList(final int type) {
        TitleList.clear();
        chatListResponse=null;

        TitleList.add("Choose Title");
        if (type==0){
            loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        }

        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getChatList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ChatListResponse>() {
                    @Override
                    public void onSuccess(ChatListResponse response) {
                        if (type==0){
                            loadingDialog.hideDialog();
                        }

                        if (response.isStatus()) {
                            try {

                                if (chatListResponse.getData()==null){

                                }else {
                                    chatListResponse=response;
                                    chatAdapter=new ChatAdapter(ChatActivity.this,chatListResponse.getData());
                                    chatRecyclerView.setAdapter(chatAdapter);
                                    chatAdapter.notifyDataSetChanged();
                                    //chatRecyclerView.smoothScrollToPosition(chatListResponse.getData().size());
                                }

                                if (chatListResponse.getTitle().size()>0){
                                    for (int i=0;chatListResponse.getTitle().size()>i;i++){
                                        TitleList.add(chatListResponse.getTitle().get(i).getTitle());
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter(ChatActivity.this,android.R.layout.simple_spinner_item,TitleList);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    titleSpinner.setAdapter(adapter);
                                }else {
                                    titleSpinner.setVisibility(View.GONE);
                                }

                            }catch (Exception e){
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),e.getMessage(),true);
                                e.printStackTrace();
                            }
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (type==0){
                            loadingDialog.hideDialog();
                        }
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.chatLayout), true, e);
                    }
                })
        );
    }

    private void sendMessageApi(final String Titles, final String Message) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        ChatListResponse.DataBean dataBean=new ChatListResponse.DataBean();
        ChatListResponse chatListResponse1=new ChatListResponse();
        List<ChatListResponse.DataBean> dataBeanList=new ArrayList<>();


        dataBean.setId(0);
        dataBean.setUserid(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID));
        dataBean.setTitle(Title);
        dataBean.setStatus(0);
        dataBean.setReply("");
        dataBean.setCreated_at(sdf.format(new Date()));
        dataBean.setUpdated_at(sdf.format(new Date()));
        dataBean.setFrom("1");

        if (TextUtils.isEmpty(Message)){
            dataBean.setMessage("Image");
        }else {
            dataBean.setMessage(Message);
        }



        try {
            chatListResponse.getData().add(dataBean);
            chatRecyclerView.setAdapter(chatAdapter);
        }catch (Exception e){
            dataBeanList.add(dataBean);
            chatListResponse1.setData(dataBeanList);
            chatListResponse=chatListResponse1;
            chatAdapter=new ChatAdapter(ChatActivity.this,chatListResponse.getData());
            chatRecyclerView.setAdapter(chatAdapter);
            e.printStackTrace();
        }

        try {
            chatAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
        //chatRecyclerView.scrollToPosition(chatListResponse.getData().size());

        ChatSendRequest chatSendRequest=new ChatSendRequest();
        chatSendRequest.setTitle(Title);
        chatSendRequest.setMessage(Message);
        if (TextUtils.isEmpty(textBase64)){
            chatSendRequest.setDocument_attached("");
        }else {
            chatSendRequest.setDocument_attached("data:image/png;base64,"+textBase64);
        }


        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.saveCustomerQueries(chatSendRequest )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response.getStatus()) {

                            statusWaitingImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_chat_new));
                            titleSpinner.setVisibility(View.GONE);
                            edMessage.setText("");
                            textBase64="";
                            edMessage.setHint("Enter Message");
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatLayout),response.getMessage(),true);
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
