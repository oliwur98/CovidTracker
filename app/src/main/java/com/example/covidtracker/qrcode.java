package com.example.covidtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class qrcode extends AppCompatActivity {
    //variables
    ImageView ivOutput;
    Button btGenerate;
    FirebaseAuth auth;
    FirebaseFirestore userData;
    String userID;
    String vaccineDoseTwoDate;
    public String qrCodeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ivOutput = findViewById(R.id.iv_output);
        btGenerate = findViewById(R.id.bt_generate);
        auth = FirebaseAuth.getInstance();
        userData = FirebaseFirestore.getInstance();

        userID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = userData.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                String qrCodeStringtest = documentSnapshot.getString("Doses");
                String doseTwotest = documentSnapshot.getString("dose2_date");
                qrCodeString = qrCodeStringtest;
                vaccineDoseTwoDate = doseTwotest;
            }
        });


        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String sdfDate = sdf.format(date);

                System.out.println("djwiajdiwajdwaijdwaidjaw" + " " + qrCodeString);
                if(qrCodeString.equals("2") && (Integer.parseInt(vaccineDoseTwoDate) - Integer.parseInt(sdfDate)) <= -14) {
                    //Multi format writer
                    MultiFormatWriter writer = new MultiFormatWriter();

                    try {

                        //Initialize bit matrix
                        BitMatrix matrix = writer.encode("Total doses: " + qrCodeString, BarcodeFormat.QR_CODE
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
                else{
                    Toast.makeText(qrcode.this,"Cant generate passport, you do not have 2 doses, or have not waited two weeks after the 2:nd dose", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}