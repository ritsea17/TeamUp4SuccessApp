package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Activity_Fach extends AppCompatActivity {

        private Spinner spinnerA, spinnerF, spinnerP;
        private TextView auswahl;
        private Button submit, z;
        FirebaseAuth fAuth;
        FirebaseFirestore fStore;
        String name;
        DatabaseReference subject;

    private static final String TAG = "Activity_Fach";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fach_activity_main);

            spinnerA =  findViewById(R.id.spinner1);
            spinnerF = findViewById(R.id.spinner2);
            spinnerP = findViewById(R.id.spinner3);
            submit = findViewById(R.id.btnSubmit);
            z = findViewById(R.id.btn_zF);
            subject = FirebaseDatabase.getInstance().getReference().child("Test");


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //insertSubjectData();

                   }

            });

            z.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });
        }



}




