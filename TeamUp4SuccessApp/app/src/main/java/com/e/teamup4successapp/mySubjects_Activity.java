package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class mySubjects_Activity extends AppCompatActivity {

    ListView listView;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysubjects);
        listView = findViewById(R.id.mysubjects_listView);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        ArrayList<String> names = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.mysubjects_listitem, names);
        listView.setAdapter(adapter);

        String[] departments = getResources().getStringArray(R.array.departments);
        String[] subjectsInformatics = getResources().getStringArray(R.array.subjects_informatics);
        String[] subjectsAutomation = getResources().getStringArray(R.array.subjects_automation);
        String[] subjectsMechatronics = getResources().getStringArray(R.array.subjects_mechatronics);
        String[] subjectsRobotics = getResources().getStringArray(R.array.subjects_robotics);
        String[] subjects = getResources().getStringArray(R.array.subjects);

        for(int i = 0; i < departments.length; i++) {
            String department = departments[i];
            String subject;
            if(department.equals(departments[0])) {
                for(int j = 0; j < subjectsInformatics.length; j++) {
                    subject = subjectsInformatics[j];
                    onGetMySubjects(department, subject, names, adapter);
                }
            } else if(department.equals(departments[1])) {
                for(int j = 0; j < subjectsAutomation.length; j++) {
                    subject = subjectsAutomation[j];
                    onGetMySubjects(department, subject, names, adapter);
                }
            } else if(department.equals(departments[2])) {
                for(int j = 0; j < subjectsMechatronics.length; j++) {
                    subject = subjectsMechatronics[j];
                    onGetMySubjects(department, subject, names, adapter);
                }
            } else if(department.equals(departments[3])) {
                for(int j = 0; j < subjectsRobotics.length; j++) {
                    subject = subjectsRobotics[j];
                    onGetMySubjects(department, subject, names, adapter);
                }
            } else if(department.equals(departments[4])) {
                for(int j = 0; j < subjects.length; j++) {
                    subject = subjects[j];
                    onGetMySubjects(department, subject, names, adapter);
                }
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = (String) adapter.getItem(i);
                String department = departments[0];
                if(Arrays.asList(subjectsInformatics).contains(subject)) {
                    department = departments[0];
                } else if (Arrays.asList(subjectsAutomation).contains(subject)) {
                    department = departments[1];
                } else if (Arrays.asList(subjectsMechatronics).contains(subject)) {
                    department = departments[2];
                } else if (Arrays.asList(subjectsRobotics).contains(subject)) {
                    department = departments[3];
                } else if (Arrays.asList(subjects).contains(subject)) {
                    department = departments[4];
                }
                Intent intent = new Intent(getApplicationContext(), TeacherList.class);
                intent.putExtra("subject", subject);
                intent.putExtra("department", department);
                startActivity(intent);
            }
        });
    }

    public void onGetMySubjects(String department, String subject, ArrayList names, ArrayAdapter adapter) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(department).child(subject);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.getValue().toString();
                    String[] separated = name.split("=");
                    for(int i = 0; i < separated.length; i++) {
                        if(separated[i].substring(0,1).equals("{")) {

                        } else {
                            String namesToCompare = separated[i].substring(0, separated[i].indexOf("}"));
                            if(namesToCompare.equals(username)) {
                                names.add(subject);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
