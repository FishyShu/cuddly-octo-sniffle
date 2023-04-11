package com.example.cuddly_octo_sniffle;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn_bMain, btn_bShop, btn_bSports_hall, btn_bMagal, btn_bGoren,btn_bMorag, btn_bKatzir, btn_bAlomot,btn_bKama, btn_bOmarim;

    Button btn_login_test01;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Write a message to the database



        askingButton();

        buttonGotClicked();


        btn_login_test01 =  findViewById(R.id.btn_login_test01);

        // the following code is to check if the user is logged in, if true; show name.

        /*fireStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult());
                        }
                        else {
                            Log.w(TAG, "Error getting document.", task.getException());
                        }
                    }
                });

        if()*/
        btn_login_test01.setText("Test: " +
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
        btn_login_test01.setOnClickListener(v -> {
            // signOut the user from the Google account or the general account
            firebaseAuth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void buttonGotClicked() {

        btn_bMain.setOnClickListener(v -> {
            // TODO document why this method is empty
        });
        btn_bShop.setOnClickListener(v -> {

        });
        btn_bSports_hall.setOnClickListener(v -> {
            // TODO document why this method is empty
        });

        btn_bMagal.setOnClickListener(v -> {

        });
        btn_bGoren.setOnClickListener(v -> {

        });
        btn_bMorag.setOnClickListener(v -> {

        });

        btn_bKatzir.setOnClickListener(v -> {

        });
        btn_bAlomot.setOnClickListener(v -> {

        });

        btn_bKama.setOnClickListener(v -> {

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


    // --- THE FOLLOWING IS A MISTAKE, USING REALTIME DATABASE AND NOT FIRESTORE! ----
    /*private void realTimeDatabaseInitialization() {
        // Get a reference to the buildings node
        DatabaseReference buildingRef = database.getReference("building");

        // Retrieve the buildings data and display it in a RecyclerView or other view
        buildingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Building_Information> buildingInformationArrayList = new ArrayList<>();
                for ( DataSnapshot buildingSnapshot : snapshot.getChildren() ) {
                    Building_Information building = buildingSnapshot.getValue(Building_Information.class);
                    buildingInformationArrayList.add(building);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d(TAG, "Error retrieving buildings data: " + error.getMessage());

            }
        });


    }*/
}