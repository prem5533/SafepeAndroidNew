package com.safepayu.wallet.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.BellNotifictionActivity;
import com.safepayu.wallet.activity.LoginActivity;

import static com.safepayu.wallet.activity.Navigation.BadgeCount;
import static com.safepayu.wallet.activity.Navigation.BadgeCountTV;

public class MyFirebaseMsgService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        BadgeCount=BadgeCount+1;

        try{
            BadgeCountTV.post(new Runnable() {
                public void run() {
                    BadgeCountTV.setVisibility(View.VISIBLE);
                    BadgeCountTV.setText(""+BadgeCount);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //create notification
        createNotification(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //Log.d("token",token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // sending gcm token to server
      //  Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void createNotification(RemoteMessage remoteMessage) {
        BadgeCount=BadgeCount+1;
        Intent intent;
        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ACCESS_TOKEN) != null) {
            intent = new Intent( this , BellNotifictionActivity.class );
        }else {
            intent = new Intent( this , LoginActivity.class );
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.drawable.safepe1)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setStyle( new NotificationCompat.BigTextStyle().bigText("SafePe"))

                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        //                .setStyle(new NotificationCompat.BigPictureStyle()
        //                        .bigPicture(myBitmap))

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }

}
