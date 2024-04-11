package com.example.hairstylingbynt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;



public class HomeActivity extends AppCompatActivity {



    CollectionReference userRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userRef = FirebaseFirestore.getInstance().collection("User");
    }
}