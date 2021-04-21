package com.e.teamup4successapp;

import android.os.Bundle;
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

public class TeacherList extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getteacher_activity);
        listView = findViewById(R.id.ListView);

        ArrayList<String> names = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);
        listView.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Subjects").child("Deutsch");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                names.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    names.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
