package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {

    // SQLiteDatabase db;

    // Firebase initialization :))))
    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseApp.initializeApp(SplashScreen.this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();




        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (currentUser!= null) {
                // User is already signed in, do something here
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
            else {
                // User is not signed in, show login/signup screen
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        },2000);
    }


}
