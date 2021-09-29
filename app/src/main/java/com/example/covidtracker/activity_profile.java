package com.example.covidtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class activity_profile extends AppCompatActivity {

    TextView name;
    EditText number, confirmNumber, email;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;


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


        DocumentReference documentReference = userData.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                String fName = documentSnapshot.getString("firstName");
                String lName = documentSnapshot.getString("lastName");
                String Pnumber = documentSnapshot.getString("number");
                String mail = documentSnapshot.getString("email");
                String space = " ";
                String fullName = fName + space + lName;

                name.setText(fullName);
                number.setHint(Pnumber);
                confirmNumber.setHint(Pnumber);
                email.setHint(mail);
            }
        });
    }
}