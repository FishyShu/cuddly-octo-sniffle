package com.example.cuddly_octo_sniffle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

public class ScheduleActivity extends AppCompatActivity {


    CalendarView cvSchedulePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        cvSchedulePicker = (CalendarView) findViewById(R.id.cv_schedule_picker);

        cvSchedulePicker.setOnDateChangeListener((view, year, month, dayOfMonth) -> {


        });

    }
}