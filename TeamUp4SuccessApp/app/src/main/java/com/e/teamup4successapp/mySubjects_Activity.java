package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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

    ListView listViewStudentSubjects;
    ListView listViewTeacherSubjects;
    String username, subject, role;
    String vabteilung;
    String[] subjectsAbt;
    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysubjects);
        listViewStudentSubjects = findViewById(R.id.mysubjects_Student_listView);
        listViewTeacherSubjects = findViewById(R.id.mysubjects_Teacher_listView);
        listViewTeacherSubjects.setSelector(android.R.color.transparent);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        vabteilung = intent.getStringExtra("abteilung");

        ArrayList<String> namesStudentSubjects = new ArrayList<>();
        ArrayList<String> namesTeacherSubjects = new ArrayList<>();
        ArrayAdapter adapterStudent = new ArrayAdapter<String>(this, R.layout.mysubjectsstudent_listitem, namesStudentSubjects);
        ArrayAdapter adapterTeacher = new ArrayAdapter<String>(this, R.layout.mysubjectsteacher_listitem, namesTeacherSubjects);
        listViewStudentSubjects.setAdapter(adapterStudent);
        listViewTeacherSubjects.setAdapter(adapterTeacher);

        String[] alldepartments = getResources().getStringArray(R.array.departments);
        String[] subjectsInformatics = getResources().getStringArray(R.array.subjects_informatics);
        String[] subjectsAutomation = getResources().getStringArray(R.array.subjects_automation);
        String[] subjectsMechatronics = getResources().getStringArray(R.array.subjects_mechatronics);
        String[] subjectsRobotics = getResources().getStringArray(R.array.subjects_robotics);

        String[] departments = getResources().getStringArray(R.array.departments);
        String[] subjects = getResources().getStringArray(R.array.subjects);
        String[] roles = getResources().getStringArray(R.array.roles);
        if (vabteilung.equals("Informatik")) {
            departments = getResources().getStringArray(R.array.departmentsInformatics);
            subjectsAbt = getResources().getStringArray(R.array.subjects_informatics);
            onCallGetMySubjects(departments, subjectsAbt, subjects, roles, namesStudentSubjects, namesTeacherSubjects, adapterStudent, adapterTeacher);
        } else if (vabteilung.equals("Automatisierung")) {
            departments = getResources().getStringArray(R.array.departmentsAutomation);
            subjectsAbt = getResources().getStringArray(R.array.subjects_automation);
            onCallGetMySubjects(departments, subjectsAbt, subjects, roles, namesStudentSubjects, namesTeacherSubjects, adapterStudent, adapterTeacher);
        } else if (vabteilung.equals("Mechatronik")) {
            departments = getResources().getStringArray(R.array.departmentsMechatronics);
            subjectsAbt = getResources().getStringArray(R.array.subjects_mechatronics);
            onCallGetMySubjects(departments, subjectsAbt, subjects, roles, namesStudentSubjects, namesTeacherSubjects, adapterStudent, adapterTeacher);
        } else if (vabteilung.equals("Robotik")) {
            departments = getResources().getStringArray(R.array.departmentsRobotics);
            subjectsAbt = getResources().getStringArray(R.array.subjects_robotics);
            onCallGetMySubjects(departments, subjectsAbt, subjects, roles, namesStudentSubjects, namesTeacherSubjects, adapterStudent, adapterTeacher);
        }

        listViewStudentSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = (String) adapterStudent.getItem(i);
                String department = alldepartments[0];
                if (Arrays.asList(subjectsInformatics).contains(subject)) {
                    department = alldepartments[0];
                } else if (Arrays.asList(subjectsAutomation).contains(subject)) {
                    department = alldepartments[1];
                } else if (Arrays.asList(subjectsMechatronics).contains(subject)) {
                    department = alldepartments[2];
                } else if (Arrays.asList(subjectsRobotics).contains(subject)) {
                    department = alldepartments[3];
                } else if (Arrays.asList(subjects).contains(subject)) {
                    department = alldepartments[4];
                }
                Intent intent = new Intent(getApplicationContext(), TeacherList.class);
                intent.putExtra("subject", subject);
                intent.putExtra("department", department);
                startActivity(intent);
            }
        });
    }

    public void onCallGetMySubjects(String[] departments, String[] subjectsAbt, String[] subjects, String[] roles, ArrayList namesStudentSubjects, ArrayList namesTeacherSubjects, ArrayAdapter roleStudentAdapter, ArrayAdapter roleTeacherAdapter) {
        for (int j = 0; j < subjects.length; j++) {
            subject = subjects[j];
            for (int k = 0; k < roles.length; k++) {
                role = roles[k];
                if (role.equals("Schüler")) {
                    onGetMySubjects(departments[1], subject, role, namesStudentSubjects, roleStudentAdapter);
                } else if (role.equals("Lehrer")) {
                    onGetMySubjects(departments[1], subject, role, namesTeacherSubjects, roleTeacherAdapter);
                }
            }
        }
        for (int j = 0; j < subjectsAbt.length; j++) {
            subject = subjectsAbt[j];
            for (int k = 0; k < roles.length; k++) {
                role = roles[k];
                if (roles[k].equals("Schüler")) {
                    onGetMySubjects(departments[0], subject, role, namesStudentSubjects, roleStudentAdapter);
                } else if (roles[k].equals("Lehrer")) {
                    onGetMySubjects(departments[0], subject, role, namesTeacherSubjects, roleTeacherAdapter);
                }
            }
        }
    }

    public void onGetMySubjects(String department, String subject, String role, ArrayList names, ArrayAdapter adapter) {
        Log.i("Abteilung: ", department);
        Log.i("Fach: ", subject);
        Log.i("Rolle: ", role);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(department).child(subject).child(role);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.getValue().toString();
                    String[] separated = name.split("=");
                    String[] splitted = separated[3].split(",");
                    String finalname = splitted[0];
                    if (finalname.equals(username)) {
                        names.add(subject);
                    }
                    //names.add(finalname);
                    /*for (int i = 0; i < separated.length; i++) {
                        if (separated[i].substring(0, 1).equals("{")) {

                        } else {
                            if (((i % 2) == 0)) {
                                int v1 = separated[i].length() - 1;
                                int v2 = separated[i].length();
                                if (separated[i].substring(v1, v2).equals("}")) {
                                    vemail = separated[i].substring(0, separated[i].indexOf('}'));
                                } else {
                                    String[] finalclass = separated[i].split(",");
                                    vklasse = finalclass[0];
                                }
                            } else {
                                String departmentorname = separated[i].substring(0, separated[i].indexOf(','));
                                if (state == 0) {
                                    vabteilung = departmentorname;
                                    state = 1;
                                } else if (state == 1) {
                                    vusername = departmentorname;
                                    state = 0;
                                    if (vusername.equals(username)) {
                                        names.add(subject);
                                    }
                                }
                            }
                        }
                    }*/
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
