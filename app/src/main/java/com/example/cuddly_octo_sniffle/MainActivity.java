package com.example.cuddly_octo_sniffle;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn_bMain, btn_bShop, btn_bSports_hall, btn_bMagal, btn_bGoren,btn_bMorag, btn_bKatzir, btn_bAlomot,btn_bKama, btn_bOmarim;

    Button btn_login_test01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");


        // Read from the database



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
// Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }



        });

        askingButton();

        buttonGotClicked();


        btn_login_test01 =  findViewById(R.id.btn_login_test01);

        // the following code is to check if the user is logged in, if true; show name.
        btn_login_test01.setText("Test: " +
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
        btn_login_test01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // signOut the user from the Google account or the general account
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void buttonGotClicked() {

        btn_bMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_bShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_bSports_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_bMagal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_bGoren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_bMorag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_bKatzir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        btn_bAlomot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        btn_bKama.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    private void askingButton() {
        btn_bMain = (Button) findViewById(R.id.btn_bMain);
        btn_bShop = (Button) findViewById(R.id.btn_bShop);
        btn_bSports_hall = (Button) findViewById(R.id.btn_bSports_hall);
        btn_bMagal = (Button) findViewById(R.id.btn_bMagal);
        btn_bGoren = (Button) findViewById(R.id.btn_bGoren);
        btn_bMorag = (Button) findViewById(R.id.btn_bMorag);
        btn_bKatzir = (Button) findViewById(R.id.btn_bKatzir);
        btn_bAlomot = (Button) findViewById(R.id.btn_bAlomot);
        btn_bKama = (Button) findViewById(R.id.btn_bKama);
        btn_bOmarim = (Button) findViewById(R.id.btn_bOmarim);
    }
}