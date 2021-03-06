package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    String vname, vemail, vabteilung, vklasse;
    private static final String TAG = "Activity_Fach";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fach_activity_main);

        Intent intent = getIntent();
        vname = intent.getStringExtra("username");
        vemail = intent.getStringExtra("email");
        vabteilung = intent.getStringExtra("abteilung");
        vklasse = intent.getStringExtra("klasse");

        spinnerA = findViewById(R.id.spinner1);
        spinnerF = findViewById(R.id.spinner2);
        spinnerP = findViewById(R.id.spinner3);
        submit = findViewById(R.id.btnSubmit);
        z = findViewById(R.id.btn_zF);

        String[] department = getResources().getStringArray(R.array.departments);
        if(vabteilung.equals("Informatik")) {
            department =getResources().getStringArray(R.array.departmentsInformatics);
        } else if (vabteilung.equals("Automatisierung")) {
            department =getResources().getStringArray(R.array.departmentsAutomation);
        } else if (vabteilung.equals("Mechatronik")) {
            department =getResources().getStringArray(R.array.departmentsMechatronics);
        } else if (vabteilung.equals("Robotik")) {
            department =getResources().getStringArray(R.array.departmentsRobotics);
        }
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Activity_Fach.this,android.R.layout.simple_spinner_item, department);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerA.setAdapter(subjectAdapter);

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
        FachObject o1 = new FachObject(vname, vemail, vabteilung, vklasse);
        data.push().setValue(o1);
        Toast.makeText(Activity_Fach.this, "Data inserted", Toast.LENGTH_SHORT).show();
    }
}
