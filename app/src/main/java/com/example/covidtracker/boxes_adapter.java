package com.example.covidtracker;

import static com.google.firebase.firestore.Source.*;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class boxes_adapter extends RecyclerView.Adapter<boxes_adapter.MyViewHolder> {

    Context context;
    ArrayList<boxes_user> usersArrayList;
    FirebaseFirestore userData;
    String ids;
    String Vaccine;
    String booked_day_time1;
    String day_first_vaccination;
    String email1;
    String month_first_vaccination;
    String center;
    String numeric_date1;






    public boxes_adapter(Context context, ArrayList<boxes_user> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public boxes_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.boxes_item,parent,false);





        userData = FirebaseFirestore.getInstance();
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull boxes_adapter.MyViewHolder holder, int position) {

        boxes_user user = usersArrayList.get(position);

        holder.email.setText(user.email);
        holder.numeric_date.setText(String.valueOf(user.numeric_date));
        holder.sick.setText(user.sick);
        holder.user = user;


        Vaccine = user.Vaccine;
        booked_day_time1 = user.booked_day_time;
        email1 = user.email;
        month_first_vaccination = user.month_first_vaccination;
        day_first_vaccination = user.day_first_vaccination;
        numeric_date1 = user.numeric_date;
        center = user.center;


    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView email, numeric_date, sick;
        Button btn;
        boxes_user user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.box_email);
            numeric_date = itemView.findViewById(R.id.booked);
            sick = itemView.findViewById(R.id.sick);
            btn = itemView.findViewById(R.id.btn_acpt);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(user.id);
                    ids = user.id;
                    DocumentReference documentReference = userData.collection("Boxes").document(user.id);

                    Map<String, Object> user = new HashMap<>();
                    user.put("box","no");
                    user.put("booked_day_time",FieldValue.delete());
                    user.put("Vaccine",FieldValue.delete());
                    user.put("day_first_vaccination",FieldValue.delete());
                    user.put("month_first_vaccination",FieldValue.delete());
                    user.put("email",FieldValue.delete());
                    user.put("id",FieldValue.delete());
                    user.put("numeric_date",FieldValue.delete());
                    user.put("center",FieldValue.delete());
                    documentReference.update(user);


                    DocumentReference documentReference1 = userData.collection("Users").document(ids);
                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("Vaccine",Vaccine);
                    user1.put("booked_day_time", booked_day_time1);
                    user1.put("day_first_vaccination",day_first_vaccination);
                    user1.put("month_first_vaccination",month_first_vaccination);
                    user1.put("numeric_date",numeric_date1);
                    user1.put("center", center);
                    documentReference1.update(user1);

                    btn.setVisibility(View.INVISIBLE);

                }
            });
        }
    }
}
