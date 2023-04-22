package com.example.cuddly_octo_sniffle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class ScheduleActivity extends AppCompatActivity {


    CalendarView cvSchedulePicker;
    TextView tvScheduleTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        String building = getIntent().getStringExtra("building");
        String room = getIntent().getStringExtra("room");

        cvSchedulePicker = (CalendarView) findViewById(R.id.cv_schedule_picker);
        tvScheduleTitle = (TextView) findViewById(R.id.tv_schedule_title);


        cvSchedulePicker.setOnDateChangeListener((view, year, month, dayOfMonth) -> {



        });

    }
}