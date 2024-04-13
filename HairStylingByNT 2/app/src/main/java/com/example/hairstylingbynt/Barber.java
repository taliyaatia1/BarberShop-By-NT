package com.example.hairstylingbynt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Barber implements Serializable {
    private String name;
    private HashMap<String, ArrayList<TimeSlot>> appointments = new HashMap<>();

    public HashMap<String, ArrayList<TimeSlot>> getAppointments() {
        return appointments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppointments(HashMap<String, ArrayList<TimeSlot>> appointments) {
        this.appointments = appointments;
    }

    public Barber() {
    }
}


