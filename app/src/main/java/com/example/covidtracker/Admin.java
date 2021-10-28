package com.example.covidtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin extends AppCompatActivity {

    Button btbLogout;
    Button btbTrack_manage;
    Button btbSave;
    Button btbFilter;
    Button btbAgegroup;
    Button btnresp;

    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;
    Spinner dropdown;
    String dropdown_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btbLogout = findViewById(R.id.Logout_admin);
        btbTrack_manage = findViewById(R.id.Tracking_admin);
        btbSave = findViewById(R.id.save_admin);
        btbFilter = findViewById(R.id.filterCounty);
        btbAgegroup = findViewById(R.id.btb_agegroup);
        btnresp = findViewById(R.id.resp);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();


        btbTrack_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_manage = new Intent(Admin.this, bookedTimesCounty.class);
                startActivity(intent_manage);
            }
        });

        btbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropdown_value = (String) dropdown.getSelectedItem();
                DocumentReference documentReference = userData.collection("Admin").document("admin");
                Map<String, Object> user = new HashMap<>();
                user.put("agegroup", dropdown_value);
                documentReference.update(user);


            }
        });

        btbAgegroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_agegroup = new Intent(Admin.this, allow_appointments.class);
                startActivity(intent_agegroup);
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

        btbFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_filter = new Intent(Admin.this, filterCounty.class);
                startActivity(intent_filter);
            }
        });

        btnresp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_filter = new Intent(Admin.this, Admin_boxes.class);
                startActivity(intent_filter);
            }
        });

    }
}