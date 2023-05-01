package com.example.cuddly_octo_sniffle.alarms;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cuddly_octo_sniffle.DeleteLater;
//import com.example.cuddly_octo_sniffle.LoginActivity;
import com.example.cuddly_octo_sniffle.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();


        Intent i = new Intent(context, DeleteLater.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivities(context, 0, new Intent[]{i}, 0);
        // Use NotificationCompat.Builder to build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Nav") //Replace placeholder with name of application
                .setSmallIcon(R.drawable.baseline_notifications)
                .setContentTitle("DING DING DING! The Day of Occupation Has Come!")
                .setContentText("You have an hour within this day that you have occupied in the past!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationMangerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationMangerCompat.notify(123, builder.build());

    }
}