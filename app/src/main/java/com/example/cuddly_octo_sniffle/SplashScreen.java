package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    SQLiteDatabase db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        settingUpDatabase();
        
        
        
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

    private void settingUpDatabase() {
        
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