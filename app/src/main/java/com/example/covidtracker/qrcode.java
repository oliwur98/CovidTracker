package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


import java.lang.Object;

public class qrcode extends AppCompatActivity {
    //variables
    ImageView ivOutput;
    Button btGenerate;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ivOutput = findViewById(R.id.iv_output);
        btGenerate = findViewById(R.id.bt_generate);
        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();
        //the value the QR code will output
        userID = auth.getCurrentUser().getUid();

        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Multi format writer
                MultiFormatWriter writer = new MultiFormatWriter();

                try {
                    //Initialize bit matrix
                    BitMatrix matrix = writer.encode("Vaccinated, 2 doses", BarcodeFormat.QR_CODE
                            , 350, 350);
                    //Initialize barcode encoder
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    //Initialize bitmap
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    //set bitmap on image view
                    ivOutput.setImageBitmap(bitmap);
                    //initialize input manager
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}