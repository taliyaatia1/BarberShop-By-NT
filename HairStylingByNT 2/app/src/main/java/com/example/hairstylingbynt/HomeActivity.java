package com.example.hairstylingbynt;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView email = findViewById(R.id.txt_email);
        TextView type = findViewById(R.id.txt_member_type);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("barberstaff@gmail.com")){
            type.setText(R.string.staff);
        } else {
            type.setText(R.string.user);
        }
        Button galleryBtn = findViewById(R.id.gallery_btn);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        Button priceListBtn = findViewById(R.id.PriceList_btn);
        priceListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PriceListActivity.class);
                startActivity(intent);
            }
        });

        Button bookingBtn = findViewById(R.id.booking_btn);
        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });

        Button infoBT = findViewById(R.id.information_btn);
        infoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }
}