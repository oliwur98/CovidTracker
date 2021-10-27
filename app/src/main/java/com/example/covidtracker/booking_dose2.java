package com.example.covidtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class booking_dose2 extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;
    String day_dose1;
    String month_dose1;
    private Spinner SpinnerTime;
    private Button btn_book;
    private String numeric_date;
    private Spinner spinnerCounty;




    private TextView Choose_date;
    private DatePickerDialog.OnDateSetListener Date_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_dose2);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();
        btn_book = (Button) findViewById(R.id.button);
        spinnerCounty = (Spinner) findViewById(R.id.spinner_county);

        DocumentReference documentReference = userData.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                month_dose1 = documentSnapshot.getString("month_first_vaccination");
                day_dose1 = documentSnapshot.getString("day_first_vaccination");
            }
        });
        DocumentReference documentRef = userData.collection("Users").document(userID);
        documentRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                String county = documentSnapshot.getString("county");

                DocumentReference doc = userData.collection("County").document(county);
                hmm(county);
            }
        });

        Date_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date_choosen = "Your date: " + month + "/"+  day + "/" + year;
                Choose_date.setText(date_choosen);

                if(month < 10 && day < 10) numeric_date= year+"0"+month+"0"+ day;
                else if(month < 10) numeric_date= year+"0"+month+day;
                else if(day < 10) numeric_date = year+""+month+"0"+day;
                else numeric_date = ""+year+month+day;

                //month_booked = ""+month;
                //day_booked = ""+day;


            }
        };

        SpinnerTime = (Spinner) findViewById(R.id.spinner_time);
        String[] times = new String[]{"Choose time", "10.00", "10.15", "10.30", "10.45" , "11.00", "11.15", "11.30", "11.45" , "12.00", "12.15", "12.30", "12.45", "13.00", "13.15",
                "13.30", "13.45", "14.00", "14.15", "14.30", "14.45", "15.00", "15.15", "15.30", "15.45", "16.00", "16.15", "16.30", "16.45", "17.00"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times);
        SpinnerTime.setAdapter(adapter2);




        Choose_date = (TextView) findViewById(R.id.choose_date2);
        Choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //int dd1 = Integer.parseInt(day_dose1);
                 //int dm1 = Integer.parseInt(month_dose1);

                Calendar date = Calendar.getInstance();

                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);
                //date.add(Calendar.MONTH, 1 ); unslash if you want it to work properly


                DatePickerDialog date_dialog = new DatePickerDialog(booking_dose2.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, Date_listener, year, month, day  ); // year, dm1, dd1 if you want it to work properly

                date_dialog.getDatePicker().setMinDate(date.getTimeInMillis());
                //date.add(Calendar.DAY_OF_MONTH, 14); unslash if you want it to work properly
                //date_dialog.getDatePicker().setMaxDate(date.getTimeInMillis()); unslash if you want it to work properly

                date_dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                date_dialog.show();
            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(booking_check()){
                    already_booked();
                }
                else Toast.makeText(booking_dose2.this, "Booking failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void already_booked(){
        CollectionReference ref = userData.collection("Users");
        String day_time = Choose_date.getText().toString() + SpinnerTime.getSelectedItem().toString() + spinnerCounty.getSelectedItem().toString();;
        Query q=ref.whereEqualTo("booked_day_time", day_time);

        q.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean isExisting = false;
                for(DocumentSnapshot document : queryDocumentSnapshots){
                    String already_booked;
                    already_booked = document.getString("booked_day_time");
                    if(already_booked.equals(day_time)) isExisting = true;
                }
                if(!isExisting) {
                    userID = auth.getCurrentUser().getUid();
                    DocumentReference documentReference = userData.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("booked_day_time", Choose_date.getText().toString() +" at "+ SpinnerTime.getSelectedItem().toString() + " at clinic " + spinnerCounty.getSelectedItem().toString());
                    user.put("numeric_date", numeric_date);
                    user.put("center", spinnerCounty.getSelectedItem().toString());
                    documentReference.update(user);

                    Intent intent = new Intent(booking_dose2.this, dashboard.class);
                    startActivity(intent);
                }
                else Toast.makeText(booking_dose2.this, "Time already booked", Toast.LENGTH_SHORT).show();
            }
        });
    }


    boolean booking_check(){
        int counter = 0;
        if (Choose_date.getText().toString().equals("Choose date of appointment")) {
            Choose_date.setError("Field is required");
        }
        else counter++;

        if(SpinnerTime.getSelectedItem().equals("Choose time")){
            TextView errorText = (TextView)SpinnerTime.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Choose time");//changes the selected item text to this
        }
        else counter++;


        if(counter == 2){
            return true;
        }
        else return false;
    }
    public void hmm(String county) {
        DocumentReference docref = userData.collection("County").document(county);
        docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(booking_dose2.this, android.R.layout.simple_spinner_dropdown_item, list);
                spinnerCounty.setAdapter(adapter);
            }
        });

    }
}