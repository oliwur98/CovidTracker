package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    Button btbLogout;
    Button btbTrack_manage;

    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;
    String nine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btbLogout = findViewById(R.id.Logout_admin);
        btbTrack_manage = findViewById(R.id.Tracking_admin);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        Spinner dropdown = findViewById(R.id.allow_age_spinner);
        //nine = "99+";
        List<String> list = new ArrayList<>();
        for(int i = 5; i < 100; i++){
            if(i == 99){
                list.add("99+");
                break;
            }
            list.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);

        dropdown.setAdapter(adapter);

        btbTrack_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_manage = new Intent(Admin.this, Track_and_Manage.class);
                startActivity(intent_manage);
            }
        });

        btbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent_logout = new Intent(Admin.this, login.class);
                startActivity(intent_logout);
            }
        });


    }
}