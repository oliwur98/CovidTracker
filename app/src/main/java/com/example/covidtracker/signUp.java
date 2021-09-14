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
                String Firstname, Lastname, Email, SSN, Password, PasswordConfirm;
                int finalSSN;
                Firstname = String.valueOf(EditTextFirstname.getText());
                Lastname = String.valueOf(EditTextLastname.getText());
                Email = String.valueOf(EditTextEmail.getText());
                SSN = String.valueOf(EditTextSSN.getText());
                finalSSN = Integer.parseInt(SSN);
                Password = String.valueOf(EditTextPassword.getText());
                PasswordConfirm = String.valueOf(EditTextPasswordConfirm.getText());
                Toast.makeText(getApplicationContext(), "missing stuff",Toast.LENGTH_LONG);
             /*   if(!Firstname.equals("") && !Lastname.equals("") && !Email.equals("") && !SSN.equals("") && !Password.equals("") && !PasswordConfirm.equals("")) {

                }
                else{
                    Toast.makeText(getApplicationContext(), "missing stuff",Toast.LENGTH_LONG);
                }*/
            }
        });
    }

}