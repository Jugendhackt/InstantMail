package com.tomaskostadinov.instantmail.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.tomaskostadinov.instantmail.activity.MainActivity;
import com.tomaskostadinov.instantmail.R;

/**
 * Created by Tomas on 11.06.2016
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("message"));
    }

    private boolean checkMuted(){
        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("",Context.MODE_PRIVATE);
        return sharedPref.getBoolean("muted", false);
    }
    private void showNotification(String message) {
        if(checkMuted()){
            return;
        }
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Sie haben Post!")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_mail)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://"
                                        + getApplication().getPackageName() + "/"
                                        + R.raw.alert))

                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }


}