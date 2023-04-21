package com.example.cuddly_octo_sniffle;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn_bMain, btn_bShop, btn_bSports_hall, btn_bMagal, btn_bGoren,btn_bMorag, btn_bKatzir, btn_bAlomot,btn_bKama, btn_bOmarim;

    Button btn_login_test01;

    Spinner spinner_building, spinner_room;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    final static String COLLECTION_DATA = "settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Write a message to the database



        // TODO: when someone clicks the building spinner show the list of buildings,
        //  " 600 (Katzir) ", in addition when the user clicks on the building it will select the
        //  building number which the user clicked!
        //  When the user clicks / chooses a building, set the list of rooms to include only those within
        //  the building!


        // TODO: Make the bottom navigation bar, with all the fragments of List, Favorites, and settings!

        // TODO: make a teacher / student system, show the occupation system only when the user is a teacher!


        askingButton();

        buttonGotClicked();


        spinnerThings("אלומות");
        spinnerThingsBuilding();


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

    private void spinnerThingsBuilding() {


    }

    private void spinnerThings(String buildingName) {


        CollectionReference settingsRef = fireStore.collection("settings");
        DocumentReference buildingsRef = settingsRef.document("Buildings");

        buildingsRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String שכבה = documentSnapshot.getString(buildingName +".שכבה");
                Long מזההLong = documentSnapshot.getLong(buildingName +".מזהה");

                int מזהה = 0;
                if (מזההLong != null) {
                    מזהה = מזההLong.intValue();
                }

                // Add the information to a list
                List<String> list = new ArrayList<>();
                list.add(שכבה + " - " + מזהה);
                list.add("This is a test" + " " + "test");

                // Create an ArrayAdapter using the list and a default spinner layout
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, list);

                // Set the adapter to the spinner
                spinner_building.setAdapter(adapter);
            }
        });


        //Make the spinner system!
        // Take information from Firebase Firestore and set the information from the building collection
        // Get all the buildings names and put them into an the building spinner


        // TODO: Change getting from settings collection, and then getting buildings document!

       // CollectionReference buildingRef = fireStore.collection("settings");

    /*    buildingRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> buildingsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Get the name field value of each document and add it to the buildingsList
                        String buildingName = document.getString("name");
                        buildingsList.add(buildingName);

                    }
                    populateSpinner(buildingsList);
                } else
                    Log.d("MainActivity.java -> spinnerThings", "Error getting the buildings list from Firebase Firestore", task.getException());

            }
        });
*/
    } // spinnerthings end


    private void populateSpinner(List<String> buildingsList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_building.setAdapter(adapter);
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

        spinner_building = findViewById(R.id.spinner_building);
        spinner_room = findViewById(R.id.spinner_room);
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