package com.example.cuddly_octo_sniffle.alarms;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//import com.example.cuddly_octo_sniffle.DeleteLater;
//import com.example.cuddly_octo_sniffle.LoginActivity;
import com.example.cuddly_octo_sniffle.MainActivity;
import com.example.cuddly_octo_sniffle.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String NOTIFICATION_CHANNEL_ID = "my_channel_id";
    private static int notificationId = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();

        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications)
                .setContentTitle("DING DING DING! The Day of Occupation Has Come!")
                .setContentText("You have an hour within this day that you have occupied in the past!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        String longText = "You have an hour within this day that you have occupied in the past!";
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(longText);

        builder.setStyle(bigTextStyle);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

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
        notificationManager.notify(getNextNotificationId(), builder.build());
    }

    private synchronized static int getNextNotificationId() {
        return ++notificationId; // this will allow the users to have more than one notification at a time.
    }
}
