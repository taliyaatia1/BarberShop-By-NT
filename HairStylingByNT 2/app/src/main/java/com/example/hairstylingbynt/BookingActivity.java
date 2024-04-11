package com.example.hairstylingbynt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        CalendarView calendarView = findViewById(R.id.calendarView);

        // Set any listener or customization for the CalendarView as needed
        // For example:
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date change event
                // For example, display the selected date
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(BookingActivity.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
