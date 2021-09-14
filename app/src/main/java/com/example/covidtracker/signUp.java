package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUp extends AppCompatActivity {
    //register stuff
    EditText EditTextFirstname, EditTextLastname, EditTextEmail, EditTextSSN, EditTextPassword, EditTextPasswordConfirm;
    Button BtnSignUp;
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
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SignUpCheck();
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

    public void SignUpCheck() {

        if (isEmpty(EditTextFirstname)) {
            EditTextFirstname.setError("Field is required");
        } else if (noNumber(EditTextFirstname)) {
            EditTextFirstname.setError("Only letters");
        }
        if (isEmpty(EditTextLastname)) {
            EditTextLastname.setError("Field is required");
        } else if (noNumber(EditTextLastname)) {
            EditTextLastname.setError("Only letters");
        }
        if (isEmpty(EditTextEmail)) {
            EditTextEmail.setError("Field is required");
        } else if (realEmail(EditTextEmail)) {
            EditTextEmail.setError("Enter a valid email");
        }

        if (isEmpty(EditTextSSN)) {
            EditTextSSN.setError("Field is required");
        }
        if (isEmpty(EditTextPassword)) {
            EditTextPassword.setError("Field is required");
        }
        if (isEmpty(EditTextPasswordConfirm)) {
            EditTextPasswordConfirm.setError("Field is required");
        } else if (EditTextPassword.getText().toString() == EditTextPasswordConfirm.getText().toString()) {
            EditTextPasswordConfirm.setError("Password doesn't match");
        }


    }
    }
