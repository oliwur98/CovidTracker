package com.example.covidtracker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar myToolbar;
    TextView name, email;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;
    String SSN;
    String SSN_test;
    String ageGroup;
    Boolean ageCheck;

    String which_booking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,myToolbar, R.string.navDrawerOpen, R.string.navDrawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        name = headerView.findViewById(R.id.txtUser);
        email = headerView.findViewById(R.id.txtEmail);
        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        DocumentReference document = userData.collection("Users").document(userID);
        document.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                String vaccine_dose_date = documentSnapshot.getString("numeric_date");

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String sdfDate = sdf.format(date);
                if (Integer.parseInt(vaccine_dose_date) != 0) {
                    if (Integer.parseInt(vaccine_dose_date) - Integer.parseInt(sdfDate) <= 0) {
                        DocumentReference documents = userData.collection("Users").document(userID);
                        String doses = documentSnapshot.getString("Doses");
                        int dose = Integer.parseInt(doses);
                        if (dose == 0) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("Doses", "1".toString());
                            user.put("numeric_date", "0".toString());
                            user.put("booked_day_time", FieldValue.delete());
                            Toast.makeText(dashboard.this, "You should now book your second appointment", Toast.LENGTH_SHORT).show();
                            documents.update(user);

                        } else {
                            Map<String, Object> user = new HashMap<>();
                            user.put("Doses", "2".toString());
                            user.put("dose2_date", vaccine_dose_date);
                            user.put("numeric_date", "0".toString());
                            user.put("booked_day_time", FieldValue.delete());
                            documents.update(user);
                        }
                    } else ;
                } else ;
            }
        });

       DocumentReference documentReference = userData.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error)
            {
                which_booking = documentSnapshot.getString("Doses");
            }
        });


        DocumentReference documentReference2 = userData.collection("Users").document(userID);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error)
            {
                String fName = documentSnapshot.getString("firstName");
                String lName = documentSnapshot.getString("lastName");
                String mail = documentSnapshot.getString("email");
                String space = " ";
                String fullName = fName + space + lName;

                SSN_test = documentSnapshot.getString("SSN");
                System.out.println("-dw-dwa-adw-adwa-wddw-dwa-wadw-dawad-wda-wdawad-w-wdaw-adwd-a");
                SSN = SSN_test.substring(0, SSN_test.length() - 4);

                name.setText(fullName);
                email.setText(mail);


            }
        });

        DocumentReference documentReference3 = userData.collection("Admin").document("admin");
        documentReference3.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                ageGroup = documentSnapshot.getString("agegroup");
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String sdfDate = sdf.format(date);

                if(Integer.parseInt(sdfDate) - Integer.parseInt(SSN) < Integer.parseInt(ageGroup)*10000){
                    ageCheck = false;
                }
                else{
                    ageCheck = true;
                }
            }
        });




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_Logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent_logout = new Intent(dashboard.this, login.class);
                        startActivity(intent_logout);
                        break;
                    case R.id.nav_profile:
                        Intent intent_profile = new Intent(dashboard.this, activity_profile.class);
                        startActivity(intent_profile);
                        break;
                    case R.id.nav_faq:
                        Intent intent_faq = new Intent(dashboard.this, faq.class);
                        startActivity(intent_faq);
                        break;
                    case R.id.nav_covidProof:
                        Intent intent_covidProof = new Intent(dashboard.this, qrcode.class);
                        startActivity(intent_covidProof);
                        break;
                    case R.id.nav_booking:
                        if(ageCheck) {
                            if(which_booking.equals("0")) {
                                Intent intent_bookings = new Intent(dashboard.this, booking.class);
                                startActivity(intent_bookings);
                            }
                            else if(which_booking.equals("1")){
                                Intent intent_bookings2 = new Intent(dashboard.this, booking_dose2.class);
                                startActivity(intent_bookings2);
                            }
                            else Toast.makeText(dashboard.this, "You are fully vaccinated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(dashboard.this, "Cant make an appointment, not in the right age group", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }





    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

}