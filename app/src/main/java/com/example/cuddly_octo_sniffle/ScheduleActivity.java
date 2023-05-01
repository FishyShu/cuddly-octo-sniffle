package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.graphics.Color;
//import android.os.Build;
import android.os.Build;
import android.os.Bundle;

import android.service.autofill.CharSequenceTransformation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuddly_octo_sniffle.adapters.MyRecyclerViewAdapter;
import com.example.cuddly_octo_sniffle.alarms.AlarmReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();


    //private SharedPreferences prefs;
    CalendarView cvSchedulePicker;
    TextView tvScheduleTitle;

    // change it to floating action button if needed
    Button btnScheduleFinish;
    MyRecyclerViewAdapter adapter;


    // get the current date in yyyy,mm,dd
    Calendar calendar = Calendar.getInstance(); // \-o-/ thank you Jesus for this import
    int selectedYear = calendar.get(Calendar.YEAR);
    int selectedMonth = calendar.get(Calendar.MONTH);
    int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


    List<Integer> occupiedHoursWithinDay = new ArrayList<>();
    List<Integer> selectedHoursWithinDay = new ArrayList<>();
    List<String> selectedOccupiersUsernames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        String building = getIntent().getStringExtra("building");
        String room = getIntent().getStringExtra("room");
        String username = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        cvSchedulePicker = findViewById(R.id.cv_schedule_picker);
        tvScheduleTitle = findViewById(R.id.tv_schedule_title);

        createNotificationChannel();
        ChangeRecyclerViewItems(building, room);

        //MakeTheWorldRedAgain();

        cvSchedulePicker.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            Toast.makeText(this, "DATE CHANGED!", Toast.LENGTH_SHORT).show();


            // change selected values when user changes selected date
            selectedYear = year;
            selectedMonth = month;
            selectedDayOfMonth = dayOfMonth;

            selectedHoursWithinDay.clear();

            ChangeRecyclerViewItems(building, room);


        });


        ArrayList<String> numbers = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            numbers.add(String.valueOf(i));

        // set up the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rv_hour_picker);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, numbers, occupiedHoursWithinDay);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);

        //adapter = new MyRecyclerViewAdapter(this, numbers);
        //adapter.setClickListener(this);
        //recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        //adapter.setOccupiedHoursWithinDay(occupiedHoursWithinDay);
        /*/ ---------------------------------------------------------------- //
                                    LINE BREAK
         /------------------------------------------------------------------/*/


        btnScheduleFinish = findViewById(R.id.btn_schedule_finish);
        btnScheduleFinish.setOnClickListener(v -> {

            // check if user clicked on an hour.
            //selectedYear, selectedMonth, selectedDayOfMonth
            // building, room, username, email
            //  String TAG = "SABA";

            // TODO: make an arraylist that loops through all selected hours
            //  also make sure that the date is similar, and if user changes dates, it will clear the hour arraylist
            //int hour = -1; // placeholder
            String reason = "Reason"; // placeholder

            // Check if the room exists in the building document
            String TAG = "Schedule activity build occupation";
            DocumentReference buildingRef = fireStore.collection("dates").document(building);
            buildingRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot buildingDoc = task.getResult();
                    if (!buildingDoc.exists()) {
                        // create a new document for the building
                        Map<String, Object> buildingData = new HashMap<>();
                        fireStore.collection("dates").document(building).set(buildingData);
                    }
                    // check if the room exists in the database
                    DocumentReference roomRef = buildingRef.collection("room").document(room);
                    roomRef.get().addOnCompleteListener(roomTask -> {
                        if (roomTask.isSuccessful()) {
                            DocumentSnapshot roomDoc = roomTask.getResult();
                            if (!roomDoc.exists()) {
                                // create a new document for the room
                                Map<String, Object> roomData = new HashMap<>();
                                buildingRef.collection("room").document(room).set(roomData);
                            }
                            // check if the selected year exists in the database
                            DocumentReference yearRef = roomRef.collection("selectedYear").document(Integer.toString(selectedYear));
                            yearRef.get().addOnCompleteListener(yearTask -> {
                                if (yearTask.isSuccessful()) {
                                    DocumentSnapshot yearDoc = yearTask.getResult();
                                    if (!yearDoc.exists()) {
                                        // create a new document for the selected year
                                        Map<String, Object> yearData = new HashMap<>();
                                        roomRef.collection("selectedYear").document(Integer.toString(selectedYear)).set(yearData);
                                    }
                                    // check if the selected month exists in the database
                                    DocumentReference monthRef = yearRef.collection("selectedMonth").document(Integer.toString(selectedMonth));
                                    monthRef.get().addOnCompleteListener(monthTask -> {
                                        if (monthTask.isSuccessful()) {
                                            DocumentSnapshot monthDoc = monthTask.getResult();
                                            if (!monthDoc.exists()) {
                                                // create a new document for the selected month
                                                Map<String, Object> monthData = new HashMap<>();
                                                yearRef.collection("selectedMonth").document(Integer.toString(selectedMonth)).set(monthData);
                                            }
                                            // check if the selected day exists in the database
                                            DocumentReference dayRef = monthRef.collection("selectedDayOfMonth").document(Integer.toString(selectedDayOfMonth));
                                            dayRef.get().addOnCompleteListener(dayTask -> {
                                                if (dayTask.isSuccessful()) {
                                                    DocumentSnapshot dayDoc = dayTask.getResult();
                                                    if (!dayDoc.exists()) {
                                                        // create a new document for the selected day
                                                        Map<String, Object> dayData = new HashMap<>();
                                                        monthRef.collection("selectedDayOfMonth").document(Integer.toString(selectedDayOfMonth)).set(dayData);
                                                    }
                                                    // loop through all selected hours and add user data to the database
                                                    if (selectedHoursWithinDay.isEmpty()) {
                                                        Toast.makeText(this, "Please select at least one hour", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        for (int hour : selectedHoursWithinDay) {
                                                            // check if the hour exists in the database
                                                            DocumentReference hourRef = dayRef.collection("hour").document(Integer.toString(hour));
                                                            hourRef.get().addOnCompleteListener(hourTask -> {
                                                                if (hourTask.isSuccessful()) {
                                                                    DocumentSnapshot hourDoc = hourTask.getResult();
                                                                    if (!hourDoc.exists()) {
                                                                        // create a new document for the hour
                                                                        Map<String, Object> hourData = new HashMap<>();
                                                                        dayRef.collection("hour").document(Integer.toString(hour)).set(hourData);
                                                                    }
                                                                    // add the user data to the database
                                                                    Map<String, Object> userData = new HashMap<>();
                                                                    userData.put("email", email);
                                                                    userData.put("username", username);
                                                                    userData.put("reason", reason);
                                                                    assert email != null;
                                                                    hourRef.set(userData);
                                                                    //hourRef.collection("userDate").document(email).set(userData); <-- old user save data way


                                                                } else {
                                                                    Log.d(TAG, "Error getting hour document: ", hourTask.getException());
                                                                }
                                                            });
                                                        }

                                                        //TODO: CHECK IF THIS WORKS!

                                                        // create a calendar object with the specified date and time
                                                        Calendar calendar = Calendar.getInstance();
                                                        calendar.set(Calendar.YEAR, selectedYear);
                                                        calendar.set(Calendar.MONTH, selectedMonth - 1); // month starts from 0
                                                        calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);
                                                        calendar.set(Calendar.HOUR_OF_DAY, 8); //  this thing works in a 24 hours type, so 8 == 8 am,and 16 == 4pm
                                                        calendar.set(Calendar.MINUTE, 0);
                                                        calendar.set(Calendar.SECOND, 0);


                                                        Log.d("Notification", calendar.getCalendarType() + ":" + calendar.getTimeInMillis());


                                                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                                        // Create an Intent to start the AlarmReceiver class
                                                        Intent intent = new Intent(ScheduleActivity.this, AlarmReceiver.class);

                                                        // Use PendingIntent.getBroadcast() to create a pending intent
                                                        PendingIntent pendingIntent;
                                                        //pendingIntent = PendingIntent.getBroadcast(ScheduleActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                                        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT); // or 0


                                                        // Create an instance of AlarmManager and set the alarm to trigger at the specified time once
                                                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                                        Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();


                                                        //closed the activity and sends the user back to main activity.
                                                        startActivity(new Intent(ScheduleActivity.this, MainActivity.class));
                                                        finish();
                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting day document: ", dayTask.getException());
                                                }
                                            });
                                        } else {
                                            Log.d(TAG, "Error getting month document: ", monthTask.getException());
                                        }
                                    });
                                } else {
                                    Log.d(TAG, "Error getting year document: ", yearTask.getException());
                                }
                            });
                        } else {
                            Log.d(TAG, "Error getting room document: ", roomTask.getException());
                        }
                    });
                } else {
                    Log.d(TAG, "Error getting building document: ", task.getException());
                }
            });
            //finish();
        });
    }

    private void createNotificationChannel() {
        CharSequence name = "Nav";
        String description = "Know your way through Nahahal's ground!";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("Nav", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    /*private void scheduleNotification(long timeInMillis) {
        //Intent intent = new Intent(this, MyNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }*/

    private void ChangeRecyclerViewItems(String building, String room) {

        DocumentReference roomDocRef = fireStore.collection("dates")
                .document(building)
                .collection("room")
                .document(room);

        roomDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot roomSnapshot = task.getResult();
                if (roomSnapshot.exists()) {
                    CollectionReference hourCollectionRef = roomDocRef.collection("selectedYear")
                            .document(String.valueOf(selectedYear))
                            .collection("selectedMonth")
                            .document(String.valueOf(selectedMonth))
                            .collection("selectedDayOfMonth")
                            .document(String.valueOf(selectedDayOfMonth))
                            .collection("hour");

                    hourCollectionRef.get().addOnCompleteListener(task1 -> {

                        if (task1.isSuccessful()) {
                            List<String> usernames = new ArrayList<>();
                            List<Integer> hourIds = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task1.getResult()) {
                                hourIds.add(Integer.valueOf(document.getId()));

                                usernames.add(document.getString("username"));

                                // String username = document.getString("username"); // get the username field
                                //  usernames.add(username); // add username to the list
                            }
                            //TODO: use the hourIds arrayList to block the selection possibility
                            //  within the recycler view list + set them to color 'RED'.
                            occupiedHoursWithinDay = hourIds;
                            adapter.setmOccupied((occupiedHoursWithinDay));
                            adapter.notifyDataSetChanged();
                            selectedHoursWithinDay.clear();
                            selectedOccupiersUsernames = usernames;

                            //TODO: change occupied hours to red, and other to white
                            // + clear selectedHoursWithinDay


                            // Do something with the hourIds list
                            Log.d("Firestore", " Username is: " + usernames);
                            Log.d("Firestore", "Hour IDs: " + hourIds);
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task1.getException());
                        }
                    });
                } else {
                    Log.d("Firestore", "Room does not exist");
                }
            } else {
                Log.d("Firestore", "Error getting document: ", task.getException());
            }
        });


    }


    @Override
    public void onItemClick(View view, int position) {
        // int truePos = position + 1;
        if (!occupiedHoursWithinDay.contains(position)) {
            if (!selectedHoursWithinDay.contains(position)) {
                selectedHoursWithinDay.add(position);
                //Toast.makeText(this, "Clicked on " + position + "!" +
                // " Item Changed to cyan", Toast.LENGTH_SHORT).show();
                Log.d("HoursWithinDay A", "Hour: " + selectedHoursWithinDay);
                view.setBackgroundColor(Color.CYAN);
                //TODO: change item color to cyan

            } else {
                //Toast.makeText(this, "Clicked on " + position + "!" +
                // " Item Changed to default", Toast.LENGTH_SHORT).show();
                view.setBackgroundColor(Color.WHITE);
                //TODO: change item color to from cyan to default
                selectedHoursWithinDay.remove(Integer.valueOf(position));
                Log.d("HoursWithinDay R", "Hour : " + selectedHoursWithinDay);
            }
            Log.d("Occupied Hour has been clicked", "Hour occupied : " +
                    occupiedHoursWithinDay);
        } else {
            //Toast.makeText(this, "Current Occupier" + selectedOccupiersUsernames.
            // get(occupiedHoursWithinDay.indexOf(position)), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Current Occupier: " + selectedOccupiersUsernames.
                    get(occupiedHoursWithinDay.indexOf(position)), Toast.LENGTH_LONG).show();
            //view.setBackgroundColor(Color.RED);


            Log.d("Hour already occupied", "Hour:" + position);


        }
        //Toast.makeText(this, "Clicked on " + position + "!", Toast.LENGTH_SHORT).show();
    }

}