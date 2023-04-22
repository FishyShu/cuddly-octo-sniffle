package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity {

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();


    CalendarView cvSchedulePicker;
    TextView tvScheduleTitle;


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

        //tvScheduleTitle.setText(String.format("%s %s", building, room));
        //tvScheduleTitle.setOnClickListener(v -> finish());


        cvSchedulePicker.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            Toast.makeText(this, "DATE CHANGED!", Toast.LENGTH_SHORT).show();



        });

    }
}