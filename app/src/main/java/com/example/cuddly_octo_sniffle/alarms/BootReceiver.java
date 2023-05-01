package com.example.cuddly_octo_sniffle.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//import com.example.cuddly_octo_sniffle.ScheduleActivity;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {


    // TODO: CHECK

    private void recreateAlarms(Context context) {
        //TODO: CHECK IF THIS WORKS!
        // Create an Intent to start the AlarmReceiver class
        Intent intent = new Intent(context, AlarmReceiver.class);
        Calendar calendar = Calendar.getInstance();

        // Use PendingIntent.getBroadcast() to create a pending intent
        //PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Create an instance of AlarmManager and set the alarm to trigger at the specified time every day
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("BootReceiver","BootReceiver was called!");
        // Check if the device has just booted
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // Recreate the alarms using AlarmManager and PendingIntent
            recreateAlarms(context);
        }

    }
}

