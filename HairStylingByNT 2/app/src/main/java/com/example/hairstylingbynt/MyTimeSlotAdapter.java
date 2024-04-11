package com.example.hairstylingbynt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {
    private List<TimeSlot> timeSlots; // Correctly named field
    private Context context;

    // Corrected constructor to match the field name
    public MyTimeSlotAdapter(Context context, List<TimeSlot> timeSlots) {
        this.context = context;
        this.timeSlots = timeSlots;
    }

    public MyTimeSlotAdapter(Context context) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_time_slot, parent, false); // Updated to not use null as the parent
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_time_slot.setText(new StringBuilder(Common.convertTimeSlotToString(position)).toString());
        if (timeSlots.size() == 0) { // Corrected variable name
            holder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.txt_time_slot_description.setText("Available");
            holder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.black));
        } else {
            // Assuming you have a method to check if a slot is booked
            boolean isBooked = false;
            for (TimeSlot slotValue : timeSlots) {
                // Assuming TimeSlot class has a method getSlot() that returns an int
                if (slotValue.getSlot().intValue() == position) {
                    isBooked = true;
                    break;
                }
            }

            if (isBooked) {
                holder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                holder.txt_time_slot_description.setText("Full");
                holder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.white));
            } else {
                holder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
                holder.txt_time_slot_description.setText("Available");
                holder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return 20; // Returns fixed number of time slots as 20
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_time_slot, txt_time_slot_description;
        CardView card_time_slot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_time_slot = itemView.findViewById(R.id.card_time_slot);
            txt_time_slot = itemView.findViewById(R.id.txt_time_slot);
            txt_time_slot_description = itemView.findViewById(R.id.txt_time_slot_description);
        }
    }
}
