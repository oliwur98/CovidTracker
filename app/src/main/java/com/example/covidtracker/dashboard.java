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


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar myToolbar;
    TextView name, email;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;

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

        DocumentReference documentReference = userData.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error)
            {
                String fName = documentSnapshot.getString("firstName");
                String lName = documentSnapshot.getString("lastName");
                String mail = documentSnapshot.getString("email");
                String space = " ";
                String fullName = fName + space + lName;
                name.setText(fullName);
                email.setText(mail);
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
                        Intent intent_faq = new Intent(dashboard.this, FAQ.class);
                        startActivity(intent_faq);
                        break;
                    case R.id.nav_covidProof:
                        Intent intent_covidProof = new Intent(dashboard.this, qrcode.class);
                        startActivity(intent_covidProof);
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