package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.ktx.Firebase;

public class Activity_Fach extends AppCompatActivity {

    private Spinner spinnerA, spinnerF, spinnerP;
    private TextView auswahl;
    private Button submit, z;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String name;
    private static final String TAG = "Activity_Fach";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fach_activity_main);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        spinnerA = findViewById(R.id.spinner1);
        spinnerF = findViewById(R.id.spinner2);
        spinnerP = findViewById(R.id.spinner3);
        submit = findViewById(R.id.btnSubmit);
        z = findViewById(R.id.btn_zF);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_Fach.this, "OnCLick Submit", Toast.LENGTH_SHORT).show();
                insertFachData();
            }


        });

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertFachData() {
        String fach = spinnerF.getSelectedItem().toString();
        String abteilung = spinnerA.getSelectedItem().toString();
        String person = spinnerP.getSelectedItem().toString().trim();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(abteilung).child(fach).child(person);
        FachObject o1 = new FachObject(name);
        data.push().setValue(o1);
        Toast.makeText(Activity_Fach.this, "Data inserted", Toast.LENGTH_SHORT).show();


    }
}
