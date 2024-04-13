package com.example.hairstylingbynt;

public class TimeSlot {
    private String date;
    private int hour;
    private String min;
    private boolean isAvailable = true;


    public TimeSlot(){

    }

    public TimeSlot(String date, int hour, String min) {
        this.date = date;
        this.hour = hour;
        this.min = min;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
