package com.example.hairstylingbynt;

import java.util.List;

public interface ITimeSlotLoadListener {
    void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotList);
    void onTimeSlotLoadFailed(String message);

    void onTimeSlotLoadEmpty();
}


