package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class personDetail_Activity extends AppCompatActivity {

    String name, abteilung, klasse, email;
    TextView name_textView, class_textView, department_textView, email_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persondetail_activity);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        abteilung = intent.getStringExtra("abteilung");
        klasse = intent.getStringExtra("klasse");
        email = intent.getStringExtra("email");

        name_textView = findViewById(R.id.name_textView);
        class_textView = findViewById(R.id.class_textView);
        department_textView = findViewById(R.id.department_textView);
        email_textView = findViewById(R.id.email_textView);

        name_textView.setText(name);
        email_textView.setText(email);
        department_textView.setText(abteilung);
        class_textView.setText(klasse);
    }

}
