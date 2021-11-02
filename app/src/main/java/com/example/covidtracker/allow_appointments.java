package com.example.covidtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class allow_appointments extends AppCompatActivity {


    private Spinner SpinnerVaccine;
    private Spinner SpinnerTime;
    private Spinner spinnerCounty;
    private TextView Choose_date;
    private FirebaseAuth auth;
    private FirebaseFirestore userData;
    private Button btn_book;
    private DatePickerDialog.OnDateSetListener Date_listener;
    private String numeric_date;
    private String month_booked;
    private String day_booked;

    public String allowments;

    Button save;
    Spinner dropdown;
    String dropdown_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_appointments);

        allowments = "0";

        dropdown = findViewById(R.id.spinner_age);
        save = findViewById(R.id.save_age_date);
        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();

        List<String> list = new ArrayList<>();
        for(int i = 99; i > 17; i--){
            if(i == 99){
                list.add("99+");
            }
            else {
                list.add(String.valueOf(i));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);

        dropdown.setAdapter(adapter);

        Choose_date = (TextView) findViewById(R.id.choose_date_age);
        Choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                DatePickerDialog date_dialog = new DatePickerDialog(allow_appointments.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,Date_listener, year,month,day);
                date_dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                date_dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                date_dialog.show();
            }
        });

        Date_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date_choosen = year + "" + month + "" + day;
                Choose_date.setText(date_choosen);
                if(month < 10 && day < 10) numeric_date= year+"0"+month+"0"+ day;
                else if(month < 10) numeric_date= year+"0"+month+day;
                else if(day < 10) numeric_date = year+""+month+"0"+day;
                else numeric_date = ""+year+month+day;

                month_booked = ""+month;
                day_booked = ""+day;
            }
        };

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = userData.collection("Admin").document("allowage");
                Map<String, Object> user = new HashMap<>();

                check_number_of_allowments();

                user.put("Number of allowments", allowments);
                user.put(allowments, Choose_date.getText().toString() + " " + dropdown.getSelectedItem().toString());
                documentReference.update(user);

                Toast.makeText(allow_appointments.this, "Age group updated", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void check_number_of_allowments(){
        DocumentReference docRef = userData.collection("Admin").document("allowage");
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                allowments = documentSnapshot.getString("Number of allowments");
                assert allowments != null;

                int temp = Integer.parseInt(allowments);
                temp = temp + 1;
                allowments = Integer.toString(temp);


            }
        });
    }
}