package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

public class activity_profile extends AppCompatActivity {

    TextView name, county, booked;
    EditText number, confirmNumber, email;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;
    Button btn_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.name_profile);
        number = (EditText) findViewById(R.id.number_profile);
        confirmNumber = (EditText) findViewById(R.id.confirmnumber_profile);
        email = (EditText) findViewById(R.id.mail_profile);
        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();
        county = findViewById(R.id.Adress_profile);
        booked = findViewById(R.id.bookings);
        btn_cancel = findViewById(R.id.cancel_appointment);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = userData.collection("Users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("booked_day_time", FieldValue.delete());
                user.put("Vaccine", FieldValue.delete());
                documentReference.update(user);
            }
        });


        DocumentReference documentReference = userData.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nonnull DocumentSnapshot documentSnapshot, @Nonnull FirebaseFirestoreException error) {
                String fName = documentSnapshot.getString("firstName");
                String lName = documentSnapshot.getString("lastName");
                String Pnumber = documentSnapshot.getString("number");
                String mail = documentSnapshot.getString("email");
                String adress = documentSnapshot.getString("county");
                String booked_time = documentSnapshot.getString("booked_day_time");
                String fullName = fName + " " + lName;
                if(booked_time != null) booked_time = "Booked time for vaccination:\n" + booked_time;
                else booked_time = "You have not\nbooked a vaccination";

                booked.setText(booked_time);
                name.setText(fullName);
                number.setHint(Pnumber);
                confirmNumber.setHint(Pnumber);
                email.setHint(mail);
                county.setText(adress);
            }
        });
    }
}