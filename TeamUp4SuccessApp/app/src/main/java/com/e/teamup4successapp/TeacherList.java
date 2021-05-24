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

public class TeacherList extends AppCompatActivity {

    ListView listView;
    String subject;
    String department;
    String vusername, vemail, vabteilung, vklasse;
    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getteacher_activity);
        listView = findViewById(R.id.ListView);

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        department = intent.getStringExtra("department");

        ArrayList<String> names = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);
        listView.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(department).child(subject).child("Lehrer");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.getValue().toString();
                    String[] separated = name.split("=");
                    for (int i = 0; i < separated.length; i++) {
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
                                    names.add(departmentorname);
                                    state = 0;
                                }
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), personDetail_Activity.class);
                String name = (String) adapter.getItem(i);
                intent.putExtra("name", name);
                intent.putExtra("abteilung", vabteilung);
                intent.putExtra("klasse", vklasse);
                intent.putExtra("email", vemail);
                startActivity(intent);
            }
        });
    }
}
