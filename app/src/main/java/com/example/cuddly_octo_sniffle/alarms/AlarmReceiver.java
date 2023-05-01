package com.example.cuddly_octo_sniffle.alarms;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cuddly_octo_sniffle.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        if (intent == null) {
            return;
        }
        // Method to create and show the notification
        createNotification(context);
    }

    private void createNotification(Context context) {
        int notificationId = 1; // Assign a value to notificationId

        Log.d("Alarm", "Notification has been created");

        // Use NotificationCompat.Builder to build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelId")
                .setSmallIcon(R.drawable.baseline_notifications)
                .setContentTitle("Notification Title")
                .setContentText("Notification Text")
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT);

        Log.d("Alarm", "Notification has been created");

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
            }
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }
}