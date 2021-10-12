package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class filterCounty extends AppCompatActivity {

    Spinner spiCounty;
    private FirebaseAuth auth;
    private FirebaseFirestore userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_county);


        spiCounty = findViewById(R.id.county_spinner);


        String[] items = new String[]{"County", "Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland", "Jämtland Härjedalen", "Jönköpings län", "Kalmar län", "Kronoberg", "Norrbotten", "Skåne", "Stockholms län", "Sörmland", "Uppsala län", "Värmland", "Västerbotten", "VästerNorrland", "Västmanland", "Västra Götaland", "Örebro län", "Östergötland"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spiCounty.setAdapter(adapter);



    }
}