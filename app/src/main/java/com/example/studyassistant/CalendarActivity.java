package com.example.studyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CalendarActivity extends AppCompatActivity {
    CustomCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CustomCalendarView) findViewById(R.id.custom_calendar_view);
    }
}
