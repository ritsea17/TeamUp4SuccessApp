package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

            String[] department =getResources().getStringArray(R.array.abteilung);
            ArrayAdapter<String> subjectAdapterA = new ArrayAdapter<String>(Activity_Fach.this,android.R.layout.simple_spinner_item, department);
            subjectAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerA.setAdapter(subjectAdapterA);

            spinnerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(spinnerA.getSelectedItem().toString().equals("Informatik")) {
                        String[] subjects =getResources().getStringArray(R.array.subjects_informatics);
                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Activity_Fach.this,android.R.layout.simple_spinner_item, subjects);
                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerF.setAdapter(subjectAdapter);
                    } else if(spinnerA.getSelectedItem().toString().equals("Automatisierung")) {
                        String[] subjects =getResources().getStringArray(R.array.subjects_automation);
                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Activity_Fach.this,android.R.layout.simple_spinner_item, subjects);
                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerF.setAdapter(subjectAdapter);
                    } else if(spinnerA.getSelectedItem().toString().equals("Mechatronik")) {
                        String[] subjects =getResources().getStringArray(R.array.subjects_mechatronics);
                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Activity_Fach.this,android.R.layout.simple_spinner_item, subjects);
                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerF.setAdapter(subjectAdapter);
                    } else if(spinnerA.getSelectedItem().toString().equals("Allgemein")) {
                        String[] subjects =getResources().getStringArray(R.array.subjects);
                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Activity_Fach.this,android.R.layout.simple_spinner_item, subjects);
                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerF.setAdapter(subjectAdapter);
                    } else if(spinnerA.getSelectedItem().toString().equals("Robotik")) {
                        String[] subjects =getResources().getStringArray(R.array.subjects_robotics);
                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Activity_Fach.this,android.R.layout.simple_spinner_item, subjects);
                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerF.setAdapter(subjectAdapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Activity_Fach.this, "OnCLick Submit", Toast.LENGTH_SHORT).show();
                    String Abteilung = spinnerA.getSelectedItem().toString();
                    String Fach = spinnerF.getSelectedItem().toString();
                    String Person = spinnerP.getSelectedItem().toString().trim();
                    
                    if(Person.equals("Lehrer")) {
                        Intent intent = new Intent(getApplicationContext(), Lehrer_Activity.class);
                        intent.putExtra("abt", Abteilung);
                        intent.putExtra("fach", Fach);
                        startActivity(intent);

              //Einfügen eines Faches in die Collection "fachanbieten" wenn der User dieses Fach unterrichten möchte

                        //String userID = fAuth.getCurrentUser().getUid();
                        //DocumentReference documentReference = fStore.collection("fachanbieten").document(userID);
                        //Toast.makeText(Activity_Fach.this, "Lehrer", Toast.LENGTH_SHORT).show();
                        //Map<String,Object> user = new HashMap<>();
                        //user.put("name",userID);
                        //user.put("fach",Fach);
                        //documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                           // @Override
                            //public void onSuccess(Void aVoid) {
                              //  Log.d(TAG, "onSuccess: Fach wurde Hinzugefügt"+userID);
                            //}
                        //});
                    }else{
                        Intent intent = new Intent(getApplicationContext(), Schueler_Activity.class);
                        intent.putExtra("abt", Abteilung);
                        intent.putExtra("fach", Fach);
                        startActivity(intent);

            //Einfügen eines Neuen Faches wenn der User dafür Nachhilfe benötigt in die "fachsuchen" Collection

                        //String userID = fAuth.getCurrentUser().getUid();
                        //DocumentReference documentReference = fStore.collection("fachsuchen").document(userID);
                       // Map<String,Object> user = new HashMap<>();
                        //user.put("username",userID);
                        //user.put("fach",Fach);
                        //documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        // @Override
                        //public void onSuccess(Void aVoid) {
                        //  Log.d(TAG, "onSuccess: Fach wurde Hinzugefügt"+userID);
                        //}
                        //});
                    }}

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
