package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnotification = (Button) findViewById(R.id.btnNotifi);
        btnotification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

    }
}