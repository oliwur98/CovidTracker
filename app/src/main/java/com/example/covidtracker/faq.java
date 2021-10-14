package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

public class faq extends AppCompatActivity {
    ExpandableListView expandableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        expandableTextView=findViewById(R.id.eTV);
        ExpandableTextViewAdapter adapter = new ExpandableTextViewAdapter(faq.this);
        expandableTextView.setAdapter(adapter);
    }
}