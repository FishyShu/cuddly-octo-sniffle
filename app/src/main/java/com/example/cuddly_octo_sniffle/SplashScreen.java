package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    SQLiteDatabase db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
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


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(
                        SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        },2000);
    }

}

//* new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent i = new Intent(
//                        SplashScreen.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        },2000);
//    }