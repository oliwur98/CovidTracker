package com.example.covidtracker;

import static java.sql.DriverManager.println;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class login extends AppCompatActivity {
    EditText EditTextEmail, EditTextPassword;
    TextView textViewRegister, textViewForgotPassword;
    Button btnLogin;
    FirebaseAuth auth;
    String userID;
    FirebaseFirestore userData;
    String Admin_check;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // if user already logged in


        EditTextEmail = (EditText) findViewById(R.id.Email);
        EditTextPassword = (EditText) findViewById(R.id.Password);
        textViewRegister = (TextView) findViewById(R.id.txtRegister);
        textViewForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        auth = FirebaseAuth.getInstance();



        if (auth.getCurrentUser() != null) {
            /*userID = auth.getCurrentUser().getUid();
            System.out.println(userID);
            userData = FirebaseFirestore.getInstance();
            DocumentReference documentReference = userData.collection("Users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error)
                {
                    Admin_check = documentSnapshot.getString("email");
                    System.out.println(Admin_check);
                    System.out.println("_-----d-d-d-dd--d-d.-f.d-f.d-fd.f-df.-df.d-f.d-fd-f.d-f.d-fd-fd-fd-f-d.f-d.f-df.d-f.d-f-df-df-dfd-f.d-f.d-f.d-f.d-.f-d.fd-");
                }
            });

            if(Admin_check.equals("bjort@gmail.com")){
                Intent intent_admin = new Intent(login.this, Admin.class);
                startActivity(intent_admin);
            }*/
            //else{
                Intent intent = new Intent(login.this, dashboard.class);
                startActivity(intent);
            //}
        }


        textViewForgotPassword.setOnClickListener(View -> {
            Intent intent = new Intent(login.this, resetPassword.class);
            startActivity(intent);
        });
        textViewRegister.setOnClickListener(view -> {
            Intent intent = new Intent(login.this, signUp.class);
            startActivity(intent);
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditTextEmail.getText().toString().equals("bjort@gmail.com")){
                    auth.signInWithEmailAndPassword(EditTextEmail.getText().toString(), EditTextPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(login.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    //If sign in successful, sign in to the admin page.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(login.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(login.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                                        Intent intent_admin = new Intent(login.this, Admin.class);
                                        startActivity(intent_admin);
                                    }
                                }
                            });
                }
                else if(LoginCheck()) {
                    auth.signInWithEmailAndPassword(EditTextEmail.getText().toString(), EditTextPassword.getText().toString())
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
                                        Intent intent = new Intent(login.this, dashboard.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }

    boolean isEmpty(EditText text) {
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
