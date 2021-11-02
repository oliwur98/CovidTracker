package com.example.covidtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
import java.util.Arrays;
import java.util.List;

public class bookedTimesCounty extends AppCompatActivity {

    Spinner chooseCounty;
    Spinner chooseCenter;
    Button btbChoose;
    Button btbSearch;

    FirebaseAuth auth;
    FirebaseFirestore userData;
    ArrayList<Users> userArrayList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_times_county);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        chooseCounty = findViewById(R.id.choose_county_spinner);
        chooseCenter = findViewById(R.id.choose_center_spinner);
        btbChoose = findViewById(R.id.btb_choose_bookedtimes);
        btbSearch = findViewById(R.id.search_times);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String[] items = new String[]{"County", "Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland", "Jämntland", "Jönköping", "kalmar", "Kronoberg", "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala", "Värmland", "Västerbotten", "Västernorrland", "Västmanland", "Västra Götaland", "Örebro", "Östergötland"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        chooseCounty.setAdapter(adapter);

        btbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String county = chooseCounty.getSelectedItem().toString();
                updateCenter(county);
            }
        });

        btbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bajs();
            }
        });


    }

    public void bajs(){
        MyAdapter myAdapter;
        userArrayList = new ArrayList<Users>();
        myAdapter = new MyAdapter(bookedTimesCounty.this, userArrayList);
        recyclerView.setAdapter(myAdapter);
        CollectionReference ref = userData.collection("Users");
        Query q=ref.whereEqualTo("center", chooseCenter.getSelectedItem());

        q.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean sameCenter = false;
                String fullyVaccinated;
                for(DocumentSnapshot document : queryDocumentSnapshots){
                    String center;
                    center = document.getString("center");
                    fullyVaccinated = document.getString("booked_day_time");
                    if(center.equals(chooseCenter.getSelectedItem())) sameCenter = true;
                    if(sameCenter && fullyVaccinated != null){
                        userArrayList.add(document.toObject(Users.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }

            }
        });


    }

    public void updateCenter(String county){
        DocumentReference documentReference = userData.collection("County").document(county);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                int hej = 1;
                int teste = 1;
                int size = 0;
                List<String> list = new ArrayList<>();
                list.add("");
                while(hej == 1){
                    assert value != null;
                    String temp= value.getString(String.valueOf(teste));
                    if(temp != null){
                        list.add(String.valueOf(temp));
                        size++;
                        teste++;
                    }
                    else{
                        hej = 0;
                    }
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(bookedTimesCounty.this, android.R.layout.simple_spinner_dropdown_item, list);
                chooseCenter.setAdapter(adapter2);
            }
        });

    }
}