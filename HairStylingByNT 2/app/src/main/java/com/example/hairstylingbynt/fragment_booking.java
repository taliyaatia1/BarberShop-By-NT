package com.example.hairstylingbynt;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hairstylingbynt.databinding.FragmentBookingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class fragment_booking extends Fragment implements ITimeSlotLoadListener {

    private FragmentBookingBinding binding;
    private AlertDialog dialog;
    private LocalBroadcastManager localBroadcastManager;
    private SimpleDateFormat simpleDateFormat;
    private Calendar selected_date;
    private Barber currentBarber; // Example declaration
    private DocumentReference barberDoc; // Correct spelling from 'DocumtentReference'

    private final BroadcastReceiver displayTimeSlot = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 0);
            loadAvailableTimeSlotOfBarber(Common.currentBarber.getBarberID(), simpleDateFormat.format(date.getTime()));
        }
    };
    private RecyclerView recycler_time_slot;


    public fragment_booking() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
        selected_date = Calendar.getInstance();
        selected_date.add(Calendar.DATE, 0);
        localBroadcastManager = LocalBroadcastManager.getInstance(requireContext());
        localBroadcastManager.registerReceiver(displayTimeSlot, new IntentFilter("KEY_DISPLAY_TIME_SLOT"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.recyclerTimeSlot.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        binding.recyclerTimeSlot.setLayoutManager(gridLayoutManager);

    }

    public void onSelected(Calendar date, int position) {
        if (selected_date.getTimeInMillis() != date.getTimeInMillis()) {
            selected_date = date;
            loadAvailableTimeSlotOfBarber(Common.currentBarber.getBarberID(), simpleDateFormat.format(date.getTime()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(displayTimeSlot);
        super.onDestroy();
    }

    @Override
    public void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotList) {
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getContext(), timeSlotList);
        recycler_time_slot.setAdapter(adapter);
        dialog.dismiss();
        // Handle success
    }

    @Override
    public void onTimeSlotLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }
    // Handle failure


    @Override
    public void onTimeSlotLoadEmpty() {
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getContext());
        recycler_time_slot.setAdapter(adapter);
        dialog.dismiss();
        // Handle empty
    }

    private void loadAvailableTimeSlotOfBarber(String barberId, final String bookDate) {
        // Corrected dialog showing and fetching logic here

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference barberDocRef = db.collection("BarberShop").document("BarberShop")
                .collection("Branch").document(Common.currentBarber.getBarberId());

        barberDocRef.collection(bookDate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<TimeSlot> timeSlots = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        timeSlots.add(document.toObject(TimeSlot.class));
                    }
                    if (timeSlots.size() > 0) {
                        fragment_booking.this.onTimeSlotLoadSuccess(timeSlots);
                    } else {
                        fragment_booking.this.onTimeSlotLoadEmpty();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fragment_booking.this.onTimeSlotLoadFailed(e.getMessage());
            }
        });
    }
}
