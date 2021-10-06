package com.example.covidtracker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class channels extends Application {
        public static final String CHANNEL_1_ID = "channel1";
    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationsChannels();
    }
    private void createNotificationsChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH);

            channel1.setDescription("This is channel 1");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }


}
