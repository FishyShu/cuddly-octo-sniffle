package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
//import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {
    private final String CHANNEL_ID = "Notification ";


    // SQLiteDatabase db

    // Firebase initialization :))))
    // FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    // Shared preferences
    SharedPreferences sharedPreferences;
    boolean isNotificationShown = false;

    // ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        FirebaseApp.initializeApp(SplashScreen.this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Get shared preferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        isNotificationShown = sharedPreferences.getBoolean("isNotificationShown", false);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (currentUser != null) {
                // User is already signed in, do something here
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            } else {
                // User is not signed in, show login/signup screen
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }

            // Show notification if it hasn't been shown before
            if (!isNotificationShown) {
                showNotification();
            }
        }, 2000);
    }

    private void showNotification() {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), (CHANNEL_ID))
                .setSmallIcon(R.drawable.ic_launcher_background) // TODO: Change Icon
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round)) //TODO: Change icon
                .setContentTitle("Welcome to the application!")
                .setContentText("Thank you for downloading  Nahahal Navigate!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        //notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // Save the notification flag to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isNotificationShown", true);
        editor.apply();
    }

    //create notification channel for android 8.0 or higher to deliver notification
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification";
            String description = "Simple Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
