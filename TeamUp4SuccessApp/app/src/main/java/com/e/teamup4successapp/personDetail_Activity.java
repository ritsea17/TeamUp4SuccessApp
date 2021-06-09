package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class personDetail_Activity extends AppCompatActivity {

    String[] sendTo;
    String name, abteilung, klasse, email, fach;
    TextView name_textView, class_textView, department_textView, email_textView;
    Button onSendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persondetail_activity);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        abteilung = intent.getStringExtra("abteilung");
        klasse = intent.getStringExtra("klasse");
        email = intent.getStringExtra("email");
        fach = intent.getStringExtra("fach");

        name_textView = findViewById(R.id.name_textView);
        class_textView = findViewById(R.id.class_textView);
        department_textView = findViewById(R.id.department_textView);
        email_textView = findViewById(R.id.email_textView);
        onSendMail = findViewById(R.id.btnMail);

        name_textView.setText(name);
        email_textView.setText(email);
        department_textView.setText(abteilung);
        class_textView.setText(klasse);

        onSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        String header = "Anfrage Nachhilfe " + fach;
        Intent intent2 = new Intent(Intent.ACTION_SEND);
        intent2.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        intent2.putExtra(Intent.EXTRA_SUBJECT, header);
        intent2.setType("message/rfc822");
        startActivity(Intent.createChooser(intent2, "Wähle einen Mail Client:"));
    }
}
