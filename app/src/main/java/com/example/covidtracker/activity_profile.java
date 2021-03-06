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

    TextView name, county, booked, Doses;
    TextView number, email;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;
    Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.name_profile);
        Doses = (TextView) findViewById(R.id.Doses);
        number = (TextView) findViewById(R.id.number_profile);
        email = (TextView) findViewById(R.id.mail_profile);
        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();
        county = findViewById(R.id.Adress_profile);
        booked = findViewById(R.id.bookings);
        btn_cancel = findViewById(R.id.cancel_appointment);


        DocumentReference documentReference = userData.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String show = value.getString("booked_day_time");
                if(show != null) {
                btn_cancel.setVisibility(View.VISIBLE);
                }
                else;

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = userData.collection("Users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("booked_day_time", FieldValue.delete());
                user.put("Vaccine", FieldValue.delete());
                user.put("numeric_date", FieldValue.delete());
                user.put("center", FieldValue.delete());
                user.put("numeric_date", "0".toString());
                btn_cancel.setVisibility(View.INVISIBLE);
                documentReference.update(user);
            }
        });


        DocumentReference documentReference2 = userData.collection("Users").document(userID);
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
                number.setText(Pnumber);
                email.setText(mail);
                county.setText(adress);
            }
        });
        DocumentReference documentReference3 = userData.collection("Users").document(userID);
        documentReference3.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Doses.setText("You have taken "+value.getString("Doses")+ " doses");

            }
        });
    }
}