package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuddly_octo_sniffle.adapters.MyRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();


    CalendarView cvSchedulePicker;
    TextView tvScheduleTitle;

    MyRecyclerViewAdapter adapter;


    // get the current date in yyyy,mm,dd
    Calendar calendar = Calendar.getInstance(); // \-o-/ thank you Jesus for this import
    int selectedYear = calendar.get(Calendar.YEAR);
    int selectedMonth = calendar.get(Calendar.MONTH);
    int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        String building = getIntent().getStringExtra("building");
        String room = getIntent().getStringExtra("room");
        String username = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        cvSchedulePicker = (CalendarView) findViewById(R.id.cv_schedule_picker);
        tvScheduleTitle = (TextView) findViewById(R.id.tv_schedule_title);


        cvSchedulePicker.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            Toast.makeText(this, "DATE CHANGED!", Toast.LENGTH_SHORT).show();


            // change selected values when user changes selected date
            selectedYear = year;
            selectedMonth = month;
            selectedDayOfMonth = dayOfMonth;


        });


        //TODO: Get FireStore information about the current date selected.

        ArrayList<String> numbers = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            numbers.add(String.valueOf(i));

        // set up the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rv_hour_picker);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, numbers);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(this, numbers);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void onItemClick(View view, int position) {

        //TODO: When user clicks on a Negative hour, show alert with user information from FireStore
        // If user clicks on a positive hour, add it to a list and change its color to bright green.

    }
}