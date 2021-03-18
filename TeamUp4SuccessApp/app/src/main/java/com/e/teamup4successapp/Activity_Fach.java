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
                        String userID = fAuth.getCurrentUser().getUid();
                        Map<String,Object> user = new HashMap<>();
                        user.put("name",userID);
                        user.put("fach",Fach);
                        fStore.collection("Lehrer").document(userID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: Fach wurde Hinzugefügt"+userID);
                                Toast.makeText(Activity_Fach.this, "Success: Fach eingefügt",Toast.LENGTH_SHORT).show();
                            }
                        }
                        ).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                Toast.makeText(Activity_Fach.this, "Fail: Fach nicht eingefügt",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        Intent intent = new Intent(getApplicationContext(), Schueler_Activity.class);
                        intent.putExtra("abt", Abteilung);
                        intent.putExtra("fach", Fach);
                        startActivity(intent);

                        //Einfügen eines Neuen Faches wenn der User dafür Nachhilfe benötigt in die "fachsuchen" Collection
                        String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("Schueler").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("username",userID);
                        user.put("fach",Fach);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: Fach wurde Hinzugefügt"+userID);
                            }
                        });
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




