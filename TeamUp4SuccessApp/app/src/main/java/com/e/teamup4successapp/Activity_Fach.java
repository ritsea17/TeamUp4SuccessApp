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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_Fach extends AppCompatActivity {

        private Spinner spinnerA, spinnerF, spinnerP;
        private TextView auswahl;
        private Button submit;
        FirebaseAuth fAuth;
        FirebaseFirestore fStore;
        int x = 0;
        int y = 0;
    private static final String TAG = "Activity_Fach";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fach_activity_main);

            spinnerA =  findViewById(R.id.spinner1);
            spinnerF = findViewById(R.id.spinner2);
            spinnerP = findViewById(R.id.spinner3);
            submit = findViewById(R.id.btnSubmit);


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Activity_Fach.this, "OnCLick Submit", Toast.LENGTH_SHORT).show();
                    String Abteilung = spinnerA.getSelectedItem().toString();
                    String Fach = spinnerF.getSelectedItem().toString();
                    String Person = spinnerP.getSelectedItem().toString().trim();
                    if(Person.equals("Lehrer")) {
                       // Intent intent = new Intent(getApplicationContext(), Lehrer_Activity.class);
                        //intent.putExtra("abt",Abteilung);
                        //intent.putExtra("fach",Fach);
                        //startActivity(intent);
                        Toast.makeText(Activity_Fach.this, "Lehrer", Toast.LENGTH_SHORT).show();
                        String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("fachanbieten").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("username",userID);
                        user.put("fach",Fach);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: Fach wurde hinzugefügt"+Fach);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: fach konnte nicht hinzugefügt werden"+e.getMessage());
                            }
                        });
                    }else{
                       // Intent intent = new Intent(getApplicationContext(), Schueler_Activity.class);
                        //intent.putExtra("abt",Abteilung);
                        //intent.putExtra("fach",Fach);
                        //startActivity(intent);
                        String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("fachsuchen").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("username",userID);
                        user.put("fach"+y,Fach);
                        y++;
                    }

                }

            });
        }

}
