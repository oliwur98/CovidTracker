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

        dropdown = findViewById(R.id.allow_age_spinner);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();


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


    }
}