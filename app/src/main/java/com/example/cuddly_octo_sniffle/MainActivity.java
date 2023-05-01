package com.example.cuddly_octo_sniffle;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;

import android.content.DialogInterface;
import android.content.Intent;
//import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn_bMain, btn_bShop, btn_bSports_hall, btn_bMagal, btn_bGoren, btn_bMorag, btn_bKatzir,
            btn_bAlomot, btn_bKama, btn_bOmarim, btn_occupy;

    Button btn_login_test01;

    Spinner spinner_building, spinner_room;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    //final static String COLLECTION_DATA = "settings";

    CollectionReference settingsRef = fireStore.collection("settings");
    DocumentReference buildingsRef = settingsRef.document("Buildings");


    //CollectionReference roomsRef = settingsRef.document("Buildings").collection("");
    List<String> list = new ArrayList<>();

    // create a different way to save the current selected spinner item, if possible!
    private String selectedBuilding;
    private String selectedRoom;

    private boolean isTeacher = true; // if couldn't find user, shall be set to false

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


        //TODO: organize the damn methods..
        askingButton();

        buttonGotClicked();

        checkIfUserIsTeacher(); // checks if current user is teacher or not


        spinnerThings("עומרים");
        spinnerThings("קמה");
        spinnerThings("אלומות");
        spinnerThings("מורג");
        spinnerThings("מגל");
        spinnerThings("גורן");
        spinnerThings("קציר");
        spinnerThings("בניין מרכזי");

        spinnerThingsBuilding();


        btn_login_test01 = findViewById(R.id.btn_login_test01);

        // TODO: Change UI to match sign out, in addition to tthat, add an Alert to confirm choice!

        btn_login_test01.setText(getString(R.string.testUsername) + " " +
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).
                        getDisplayName());
        btn_login_test01.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to log out?")
                    .setTitle("!")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        // signOut the user from the Google account or the general account
                        firebaseAuth.signOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        // User clicked No button, dismiss the dialog
                        dialog.dismiss();
                    });

            AlertDialog alert = builder.create();
            alert.show();


        });
    }

    private void spinnerThingsBuilding() {

        //TODO: show rooms of building item selected within the spinner_building
        spinner_building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Changed item", Toast.LENGTH_SHORT).
                        show();
                int buildingPosition = spinner_building.getSelectedItemPosition();

                // TODO: Main building doesn't work, fix!
                switch (buildingPosition) {
                    case 0:
                        getRoomsForBuilding("עומרים");
                        break;
                    case 1:
                        getRoomsForBuilding("קמה");
                        break;
                    case 2:
                        getRoomsForBuilding("אלומות");
                        break;
                    case 3:
                        getRoomsForBuilding("מורג");
                        break;
                    case 4:
                        getRoomsForBuilding("מגל");
                        break;
                    case 5:
                        getRoomsForBuilding("גורן");
                        break;
                    case 6:
                        getRoomsForBuilding("קציר");
                        break;
                    case 7:
                        getRoomsForBuilding("בניין מרכזי");
                        break;
                    //case default: need to update something to use this, uhh nope..
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //ERROR
                Toast.makeText(MainActivity.this,
                        "Please select a building", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getRoomsForBuilding(String buildingName) {
        selectedBuilding = buildingName; // saves the building name
        buildingsRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> buildingData = documentSnapshot.getData();
                if (buildingData != null && buildingData.containsKey(buildingName)) {
                    Map<String, Object> buildingMap = (Map<String, Object>) buildingData.get(buildingName);
                    if (buildingMap != null && buildingMap.containsKey("חדרים")) {
                        Map<String, Object> roomsMap = (Map<String, Object>) buildingMap.get("חדרים");
                        if (roomsMap != null && !roomsMap.isEmpty()) {
                            List<String> roomsList = new ArrayList<>();
                            for (Map.Entry<String, Object> entry : roomsMap.entrySet()) {
                                String maxRooms = entry.getKey();
                                String roomValue = (String) entry.getValue();
                                if (roomValue != null && !roomValue.isEmpty())
                                    roomsList.add(roomValue);
                            }
                            ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, roomsList);
                            roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_room.setAdapter(roomAdapter);
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error getting document", e);
            }
        });
    }

    private void spinnerThings(String buildingName) {

        buildingsRef.get().addOnSuccessListener(documentSnapshot -> {
            String grade = documentSnapshot.getString(buildingName + ".שכבה");
            Long id = documentSnapshot.getLong(buildingName + ".מזהה");

            int intID = 0;
            if (id != null) {
                intID = id.intValue();
            }

            if (intID != 0) {
                if (grade != null) {
                    list.add(grade + " - " + intID);
                } else {
                    list.add(String.valueOf(intID));
                }
            } else if (grade != null) {
                list.add(grade);
            }

            // Create an ArrayAdapter using the list and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, list);

            // Set the adapter to the spinner
            spinner_building.setAdapter(adapter);
        });
    } // spinnerthings end


    private void populateSpinner(List<String> buildingsList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_building.setAdapter(adapter);
    }


    private void buttonGotClicked() {

        // TODO: when click, set item within spinner to match the building.


        btn_bMain.setOnClickListener(v -> spinner_building.setSelection(7));
        btn_bOmarim.setOnClickListener(v -> spinner_building.setSelection(6));
        btn_bKatzir.setOnClickListener(v -> spinner_building.setSelection(5));
        btn_bGoren.setOnClickListener(v -> spinner_building.setSelection(4));
        btn_bMagal.setOnClickListener(v -> spinner_building.setSelection(3));
        btn_bMorag.setOnClickListener(v -> spinner_building.setSelection(2));
        btn_bAlomot.setOnClickListener(v -> spinner_building.setSelection(1));
        btn_bKama.setOnClickListener(v -> spinner_building.setSelection(0));


        btn_occupy.setOnClickListener(v -> {

            //TODO: check if current user isTeacher, if not, set this button alpha to 0 and don't
            Toast.makeText(this, " " + this.isTeacher, Toast.LENGTH_SHORT).show();
            // allow current user to click this button.
            getCurrentSelection();
            // when pressed go to the activity of the schedule view to pick a date.
            startActivity(new Intent(this, ScheduleActivity.class).putExtra("building", selectedBuilding).putExtra("room", selectedRoom));
        });

    }

    private void getCurrentSelection() {
        // Get the selected room
        // maybe make it less carrying...
        Object selectedRoomObj = spinner_room.getSelectedItem();
        if (selectedRoomObj != null) {
            selectedRoom = selectedRoomObj.toString().trim().substring(1);
            int len = selectedRoom.length();
            int i = 0;
            while (i < len && selectedRoom.charAt(i) == '0') {
                i++;
            }
            if (i > 0) {
                selectedRoom = selectedRoom.substring(i);
            }
        }
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
        btn_occupy = findViewById(R.id.btn_occupy);


        spinner_building = findViewById(R.id.spinner_building);
        spinner_room = findViewById(R.id.spinner_room);
    }

    private void checkIfUserIsTeacher() {
        String userEmail = Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.
                getInstance().getCurrentUser()).getEmail()).replace(".", "_");

        DocumentReference docRef = fireStore.collection("users").document("known-users");

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> userData = documentSnapshot.getData();
                assert userData != null;
                if (userData.containsKey("users")) {
                    Map<String, Object> users = (Map<String, Object>) userData.get("users");
                    assert users != null;
                    if (users.containsKey(userEmail)) {
                        Map<String, Object> emailData = (Map<String, Object>) users.get(userEmail);
                        assert emailData != null;
                        if (emailData.containsKey("isTeacher")) {
                            boolean userIsTeacher = (boolean) emailData.get("isTeacher");
                            // do something with the isTeacher value

                            if (userIsTeacher) {
                                btn_occupy.setEnabled(true);
                            } else { // if user is student
                                btn_occupy.setEnabled(false);
                                btn_occupy.setAlpha(0);
                            }

                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // handle failure
            }
        });
    }

    /*private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_notifications)
                .setContentTitle("My Notification")
                .setContentText("This is a test notification.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
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
        notificationManager.notify(0, builder.build());
    }
*/

}

