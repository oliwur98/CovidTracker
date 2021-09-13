package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signUp extends AppCompatActivity {
    //register stuff
    EditText firstname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //register stuff
        firstname=(EditText) findViewById(R.id.Firstname);

    }

    public void register(View view){
        //just a test to see if button works
        Toast.makeText(this, "hejhej", Toast.LENGTH_SHORT).show();

    }
}