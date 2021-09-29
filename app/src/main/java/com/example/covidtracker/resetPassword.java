package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {
    EditText editTextEmail;
    Button btnSend;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        btnSend = (Button) findViewById(R.id.btnSend);
        auth = FirebaseAuth.getInstance();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                if(!(TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(resetPassword.this, "Password sent to your email", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(resetPassword.this, login.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(resetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            }
                        });
                    }
                }
            });

    }
}