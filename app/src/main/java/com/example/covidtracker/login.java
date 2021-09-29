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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity {
    EditText EditTextEmail, EditTextPassword;
    TextView textView;
    Button btnLogin;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       /*if(auth.getCurrentUser() != null){   //If the user is already logged in
           Intent intent = new Intent(login.this,dashboard.class);
           startActivity(intent);
        }*/

        /* public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));        Logout, send them to login class
         */
        EditTextEmail = (EditText) findViewById(R.id.Email);
        EditTextPassword = (EditText) findViewById(R.id.Password);
        textView = (TextView) findViewById(R.id.txtRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        textView.setOnClickListener(view -> {
            Intent intent = new Intent(login.this,signUp.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginCheck()){
                    auth.signInWithEmailAndPassword(EditTextEmail.getText().toString(),EditTextPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(login.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(login.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(login.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login.this,dashboard.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }

    boolean isEmpty(EditText text){
        CharSequence seq = text.getText().toString();
        return TextUtils.isEmpty(seq);
    }

    boolean LoginCheck() {
        int counter = 0;

        if (isEmpty(EditTextEmail)) {
            EditTextEmail.setError("Field is required");
        } else counter++;

        if (isEmpty(EditTextPassword)) {
            EditTextPassword.setError("Field is required");
        } else counter++;


        if (counter == 2) return true;
        else return false;


    }
}
