package com.nic.civilsurgeonnic;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import utils.DbHandler;

/**
 * Created by RVerma on 11-01-2017.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private static final String TAG="MyFirebaseMsgService";
    public String data;
    public String head;
    DbHandler dbh;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: "+remoteMessage.getFrom());
        dbh=new DbHandler(MyFirebaseMessagingService.this);
        //check if the message contains data
        if(remoteMessage.getData().size()>0){
            Log.d(TAG,"Message data:"+remoteMessage.getData());
        }
        //check if the message contains notification

        if(remoteMessage.getNotification()!=null)
        {
            Log.d(TAG,"Message body:"+remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
            data=remoteMessage.getNotification().getBody();
            head=remoteMessage.getNotification().getTitle();
            dbh.insert_notification(head,data);
        }

    }

    private void sendNotification(String body) {

        Intent intent = new Intent(this,Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0/*request code*/, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri notificationSaved= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notifiBuilder= new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSaved)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifiBuilder.build());

    }
}
