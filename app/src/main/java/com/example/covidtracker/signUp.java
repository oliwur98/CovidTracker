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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class signUp extends AppCompatActivity {
    //register stuff
    private EditText EditTextFirstname, EditTextLastname, EditTextEmail, EditTextSSN, EditTextPassword, EditTextPasswordConfirm;
    private Button BtnSignUp;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //register stuff
        EditTextFirstname =(EditText) findViewById(R.id.Firstname);
        EditTextLastname =(EditText) findViewById(R.id.Lastname);
        EditTextEmail = (EditText) findViewById(R.id.Email_signup);
        EditTextSSN = (EditText) findViewById(R.id.SSN_signup);
        EditTextPassword =(EditText) findViewById(R.id.Password_signup);
        EditTextPasswordConfirm =(EditText) findViewById(R.id.VerifyPassword_signup);
        BtnSignUp =(Button) findViewById(R.id.BtnRegister);
        auth = FirebaseAuth.getInstance();
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String email = EditTextEmail.getText().toString();
            String password =EditTextPassword.getText().toString();


            if(SignUpCheck()){

                auth.createUserWithEmailAndPassword(email,password )
                        .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(signUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(signUp.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(signUp.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signUp.this,login.class);
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
    boolean noNumber(EditText text) {
        String str = text.getText().toString();

            for(int i = 0; i < str.length(); i++) {
                if (!Character.isLetter(str.charAt(i))) return true;
            }
            return false;
    }
    boolean realEmail(EditText text){
        CharSequence seq = text.getText().toString();
        return (!Patterns.EMAIL_ADDRESS.matcher(seq).matches());
    }

    boolean SignUpCheck() {
        int counter =0;
        if (isEmpty(EditTextFirstname)) {
            EditTextFirstname.setError("Field is required");
        }
        else if (noNumber(EditTextFirstname)) {
            EditTextFirstname.setError("Only letters");
        }
        else counter++;

        if (isEmpty(EditTextLastname)) {
            EditTextLastname.setError("Field is required");
        }
        else if (noNumber(EditTextLastname)) {
            EditTextLastname.setError("Only letters");
        }
        else counter++;

        if (isEmpty(EditTextEmail)) {
            EditTextEmail.setError("Field is required");
        }
        else if (realEmail(EditTextEmail)) {
            EditTextEmail.setError("Enter a valid email");
        }
        else counter++;

        if (isEmpty(EditTextSSN)) {
            EditTextSSN.setError("Field is required");
        }
        else counter++;

        if (isEmpty(EditTextPassword)) {
            EditTextPassword.setError("Field is required");
        }
        else if(EditTextPassword.getText().toString().length() < 6){
            EditTextPassword.setError("password must be atleast 6 characters long");
        }
        else counter++;

        if (isEmpty(EditTextPasswordConfirm)) {
            EditTextPasswordConfirm.setError("Field is required");
        }
        else if (!(EditTextPassword.getText().toString().equals(EditTextPasswordConfirm.getText().toString()))) {
            EditTextPasswordConfirm.setError("Password doesn't match");
        }
        else counter++;
        if(counter == 6) return true;
        else return false;

    }
    }
