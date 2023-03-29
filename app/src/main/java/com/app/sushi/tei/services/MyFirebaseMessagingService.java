package com.app.sushi.tei.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.SubCategoryActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Date;
import java.util.Map;

import static android.app.PendingIntent.getActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

/*    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

//        GlobalValues.DeviceToken=refreshedToken;
        //prefs.setString("DEVICE_TOKEN",refreshedToken);

    }*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 1) {

            sendNotification(remoteMessage.getData());

        } else if(remoteMessage.getNotification()!=null) {
            //sendNotification(remoteMessage.getNotification());
          /*  Intent intent=new Intent(this,HomeActivity.class);
            startActivity(intent);*/
            String CHANNEL_ID = "01";
            String textTitle = remoteMessage.getNotification().getTitle();
            String textContent = remoteMessage.getNotification().getBody();
            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            NotificationCompat.Builder builder;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(textTitle)
                        .setContentText(textContent)
                        .setAutoCancel(true)
                        .setChannelId(CHANNEL_ID)
                        .setPriority(NotificationCompat.PRIORITY_MAX);
            }else{
                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(textTitle)
                        .setContentText(textContent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX);
            }

            /*Intent notificationIntent = new Intent(this, NotificationView.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //notification message will get at NotificationView
            notificationIntent.putExtra("message", "This is a notification message");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);*/

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
           /* Intent intent = new Intent(this, SubCategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(GlobalValues.FROM_NOTIFICATION_POSITION,4 );
            PendingIntent pendingIntent = getActivity(this, m, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.FLAG_SHOW_LIGHTS)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setAutoCancel(true)
                    .setLights(Color.WHITE,3000,1000)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(m, notificationBuilder.build());*/

        }
    }

    private void sendNotification(Map<String, String> data) {

        try {

            int position=0;

            if(data.get("notify_type").equalsIgnoreCase("Order"))
            {
                position=1;
            }else if(data.get("notify_type").equalsIgnoreCase("Reward")){
                position=2;
            }else if(data.get("notify_type").equalsIgnoreCase("Promotion"))
            {
                position=3;
            }
            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

            Intent intent = new Intent(this, SubCategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(GlobalValues.FROM_NOTIFICATION_POSITION,position );
            PendingIntent pendingIntent = getActivity(this, m, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(data.get("notify_type"))
                    .setContentText(data.get("message"))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.FLAG_SHOW_LIGHTS)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setAutoCancel(true)
                    .setLights(Color.WHITE,3000,1000)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);



            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(m, notificationBuilder.build());

            String channelId = "Default";
            NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(data.get("notify_type"))
                    .setContentText(data.get("message"))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
