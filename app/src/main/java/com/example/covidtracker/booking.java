package com.example.covidtracker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
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


import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class booking extends AppCompatActivity {
    private Spinner SpinnerVaccine;
    private Spinner SpinnerTime;
    private TextView Choose_date;
    private FirebaseAuth auth;
    private FirebaseFirestore userData;
    private Button btn_book;
    private DatePickerDialog.OnDateSetListener Date_listener;
    private String numeric_date;
    private String month_booked;
    private String day_booked;
    String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        btn_book = (Button) findViewById(R.id.button);

        SpinnerVaccine = (Spinner) findViewById(R.id.spinner_vaccine);
        String[] items = new String[]{" ", "Pfizer", "Moderna"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        SpinnerVaccine.setAdapter(adapter);



        Choose_date = (TextView) findViewById(R.id.choose_date);
        Choose_date.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view){

               Calendar date = Calendar.getInstance();
               int day = date.get(Calendar.DAY_OF_MONTH);
               int month = date.get(Calendar.MONTH);
               int year = date.get(Calendar.YEAR);

               DatePickerDialog date_dialog = new DatePickerDialog(booking.this,
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
                String date_choosen = "Your date: " + month + "/"+  day + "/" + year;
                Choose_date.setText(date_choosen);

                if(month < 10 && day < 10) numeric_date= year+"0"+month+"0"+ day;
                else if(month < 10) numeric_date= year+"0"+month+day;
                else if(day < 10) numeric_date = year+""+month+"0"+day;
                else numeric_date = ""+year+month+day;

                month_booked = ""+month;
                day_booked = ""+day;


            }
        };

        SpinnerTime = (Spinner) findViewById(R.id.spinner_time);
        String[] times = new String[]{"Choose time", "10.00", "10.15", "10.30", "10.45" , "11.00", "11.15", "11.30", "11.45" , "12.00", "12.15", "12.30", "12.45", "13.00", "13.15",
                "13.30", "13.45", "14.00", "14.15", "14.30", "14.45", "15.00", "15.15", "15.30", "15.45", "16.00", "16.15", "16.30", "16.45", "17.00"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times);
        SpinnerTime.setAdapter(adapter2);

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(booking_check()){
                    already_booked();
                }
                else Toast.makeText(booking.this, "Booking failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void already_booked(){
        CollectionReference ref = userData.collection("Users");
        String day_time = Choose_date.getText().toString() + SpinnerTime.getSelectedItem().toString();
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
                    UserID = auth.getCurrentUser().getUid();
                    DocumentReference documentReference = userData.collection("Users").document(UserID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("booked_day_time", Choose_date.getText().toString() +" at "+ SpinnerTime.getSelectedItem().toString());
                    user.put("Vaccine", SpinnerVaccine.getSelectedItem().toString());
                    user.put("numeric_date", numeric_date);
                    user.put("month_first_vaccination",month_booked.toString());
                    user.put("day_first_vaccination",day_booked.toString());
                    documentReference.update(user);

                    Intent intent = new Intent(booking.this, dashboard.class);
                    startActivity(intent);
                }
                else Toast.makeText(booking.this, "Time already booked", Toast.LENGTH_SHORT).show();
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

        if(SpinnerVaccine.getSelectedItem().equals(" ")){
            TextView errorText = (TextView)SpinnerVaccine.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Choose Vaccine");//changes the selected item text to this
        }
        else counter++;

        if(counter == 3){
            return true;
        }
        else return false;
    }


}