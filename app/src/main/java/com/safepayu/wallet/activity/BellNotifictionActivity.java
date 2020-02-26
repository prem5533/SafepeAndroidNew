package com.safepayu.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.NotifictionAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.NotificationResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.activity.Navigation.BadgeCount;

public class BellNotifictionActivity extends AppCompatActivity {

    private RecyclerView recylerNotifications;
    private NotifictionAdapter notifictionAdapter;
    private LoadingDialog loadingDialog;
    private Button backBtn;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bell_notifiction);
        BadgeCount=0;
        BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().BADGE_COUNT,String.valueOf(BadgeCount));

        findId();
        getNotificationData();
    }


    private void findId() {
        loadingDialog = new LoadingDialog(this);
        recylerNotifications = findViewById(R.id.recyler_notifications);
        backBtn=findViewById(R.id.notification_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BellNotifictionActivity.this, Navigation.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(BellNotifictionActivity.this, Navigation.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

    private void getNotificationData() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getNotificationData()
                  .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<NotificationResponse>() {
                    @Override
                    public void onSuccess(NotificationResponse notificationResponse) {
                        loadingDialog.hideDialog();
                        if (notificationResponse.isStatus()) {

                            recylerNotifications.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            notifictionAdapter = new NotifictionAdapter(getApplicationContext(), notificationResponse.getData());
                            recylerNotifications.setAdapter(notifictionAdapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(BellNotifictionActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.notificationLayout), false, e.getCause());
                    }
                }));
    }
}
