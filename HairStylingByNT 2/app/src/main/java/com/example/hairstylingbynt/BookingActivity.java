package com.example.hairstylingbynt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class BookingActivity extends AppCompatActivity {

    private Calendar calender = Calendar.getInstance();
    private ArrayList<TimeSlot> appointments = new ArrayList<>();
    private AppointmentSlotAdapter adapter;
    private long lastTime;
    Barber barber;
    private long currentTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        ImageView close = findViewById(R.id.close);
        close.setOnClickListener(v->finish());
        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new AppointmentSlotAdapter(appointments, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClicked(i);
            }
        });
        list.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("BarberShop ")
                .document("BarberShop")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            barber = task.getResult().toObject(Barber.class);
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.clear();
                            calendar2.set(Calendar.YEAR, calender.get(Calendar.YEAR));
                            calendar2.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH));
                            calendar2.set(Calendar.MONTH, calender.get(Calendar.MONTH));
                            currentTS = calendar2.getTimeInMillis();
                            fetchHours(calendar2.getTimeInMillis(), calender.get(Calendar.HOUR_OF_DAY));
                        } else {
                            int x = 0;
                        }
                    }
                });

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.clear();
                selectedCalendar.set(year, month, dayOfMonth);

                if (selectedCalendar.getTimeInMillis() < currentTS || selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    Toast.makeText(getApplicationContext(), "The date is no longer available", Toast.LENGTH_SHORT).show();
                    calendarView.setDate(lastTime);
                } else {
                    if (selectedCalendar.getTimeInMillis() == currentTS) {
                        fetchHours(selectedCalendar.getTimeInMillis(), Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    } else {
                        fetchHours(selectedCalendar.getTimeInMillis(), -1);
                    }
                }
            }
        });
    }

    private void onItemClicked(int position) {
        String message ="";
        if ("barberstaff@gmail.com".equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            message = getString(R.string.are_you_sure_barber);
        } else {
            message = getString(R.string.are_you_sure_client);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.alarm)
                .setMessage(message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TimeSlot appointment = appointments.get(position);
                        appointment.setAvailable(false);
                        if (barber.getAppointments().get(appointment.getDate()) == null) {
                            barber.getAppointments().put(appointment.getDate(), new ArrayList<>());
                        }

                        barber.getAppointments().get(appointment.getDate()).add(appointment);
                        FirebaseFirestore.getInstance().collection("BarberShop ")
                                .document("BarberShop")
                                .set(barber)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(BookingActivity.this, getString(R.string.time_slot_successfull), Toast.LENGTH_LONG).show();
                                        } else {
                                            new AlertDialog.Builder(BookingActivity.this)
                                                    .setTitle(R.string.error)
                                                    .setMessage(task.getException().getMessage())
                                                    .show();
                                        }
                                    }
                                });

                        adapter.notifyItemChanged(position);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();



    }

    private void fetchHours(long timestamp, int hour) {
        appointments.clear();
        calender.setTimeInMillis(timestamp);
        String stringTS = String.valueOf(timestamp);

        int relevantHour = hour != -1 ? hour : 8;
        for (int i = relevantHour + 1 ; i <= 19 ; i++) {
            appointments.add(new TimeSlot(String.valueOf(timestamp), i, "00"));
            if (i != 19) {
                appointments.add(new TimeSlot(String.valueOf(timestamp), i, "30"));
            }
        }
        if (barber.getAppointments().get(stringTS) != null) {
            barber.getAppointments().get(stringTS).forEach(appointment -> {
                for (int i =0; i < appointments.size(); i++) {
                    if (appointment.getHour() == appointments.get(i).getHour()
                            && appointment.getMin().equals(appointments.get(i).getMin())) {
                        appointments.get(i).setAvailable(false);
                    }
                }
            });
        }
        lastTime = timestamp;
        adapter.notifyDataSetChanged();
    }
}
