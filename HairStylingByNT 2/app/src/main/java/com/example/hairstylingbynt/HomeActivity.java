package com.example.hairstylingbynt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;





public class HomeActivity extends AppCompatActivity {

    Button BookBtn;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       BookBtn = findViewById(R.id.booking_btn);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , BookingActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }


}