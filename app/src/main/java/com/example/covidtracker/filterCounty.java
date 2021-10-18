package com.example.covidtracker;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class filterCounty extends AppCompatActivity {

    Spinner spiCounty;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    TextView WdoseOneText;
    TextView WdoseTwoText;
    TextView fullyVaccinatedText;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_county);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();

        spiCounty = findViewById(R.id.county_spinner);
        WdoseOneText = (TextView) findViewById(R.id.total_waiting_dose1);
        WdoseTwoText = (TextView) findViewById(R.id.total_waiting_dose2);
        fullyVaccinatedText = (TextView) findViewById(R.id.total_vaccinated);
        update = findViewById(R.id.update);



        String[] items = new String[]{"County", "Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland", "Jämtland", "Jönköping", "kalmar", "Kronoberg", "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala", "Värmland", "Västerbotten", "Västernorrland", "Västmanland", "Västra Götaland", "Örebro", "Östergötland"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spiCounty.setAdapter(adapter);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNumbers();
            }
        });



    }

    void updateNumbers(){
        CollectionReference ref = userData.collection("Users");
        String county = spiCounty.getSelectedItem().toString();
        Query q=ref.whereEqualTo("county", county);

        q.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean isExisting = false;
                int WdoseOne = 0;
                int WdoseTwo = 0;
                int fullyVaccinated = 0;
                for(DocumentSnapshot document : queryDocumentSnapshots){
                    String sameCounty;
                    sameCounty = document.getString("county");
                    if(sameCounty.equals(county)) {
                        isExisting = true;
                        String doses = document.getString("Doses");
                        if(doses.equals("0")){
                            WdoseOne++;
                        }
                        if(doses.equals("1")){
                            WdoseTwo++;
                        }
                        if(doses.equals("2")){
                            fullyVaccinated++;
                        }
                    }
                }
                WdoseOneText.setText(valueOf(WdoseOne));
                WdoseTwoText.setText(valueOf(WdoseTwo));
                fullyVaccinatedText.setText(valueOf(fullyVaccinated));


            }
        });
    }
}