package com.example.hairstylingbynt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class AppointmentSlotAdapter extends RecyclerView.Adapter<AppointmentSlotAdapter.AppointmentSlotViewHolder>{

    private final AdapterView.OnItemClickListener listener;
    private List<TimeSlot> appointments;

    public AppointmentSlotAdapter(List<TimeSlot> appointments, AdapterView.OnItemClickListener listener) {
        this.appointments = appointments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentSlotViewHolder((FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentSlotViewHolder holder, int position) {
        TimeSlot appointment = appointments.get(position);
        ((TextView)holder.textView).setText(String.valueOf(appointment.getHour()) + ":" + appointment.getMin());
        holder.textView.setBackgroundColor(appointment.isAvailable() ? holder.textView.getContext().getResources().getColor(R.color.black)
                : holder.textView.getContext().getResources().getColor(R.color.colorAccent));
        holder.itemView.setEnabled(appointment.isAvailable());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(null, null , position, -1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class AppointmentSlotViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public AppointmentSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}