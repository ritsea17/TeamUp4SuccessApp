package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    TextView name, email, department, klasse, tv_kurs;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String vklasse, vabteilung, vemail, vusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        department = findViewById(R.id.tv_department);
        klasse = findViewById(R.id.tv_class);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        tv_kurs = findViewById(R.id.tv_switch);
        userID = fAuth.getCurrentUser().getUid();
        FirebaseUser fuser = fAuth.getCurrentUser();

        if (fuser.isEmailVerified()) {
            Toast.makeText(MainActivity.this, "Sucsess: Email wurde erfolgreich verifiziert ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "ERROR ! Bitte Verifiziere deine Email Adresse" +
                    "\nDu kannst sonst nicht in die Kursansicht wechseln", Toast.LENGTH_SHORT).show();
            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "Verification Mail wurde gesendet", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error ! Verification Mail konnte nicht gesendet werden" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                vusername = value.getString("fname");
                vemail = value.getString("email");
                vklasse = value.getString("klasse");
                vabteilung = value.getString("department");
                department.setText("Abteilung: " + vabteilung);
                klasse.setText("Klasse: " + vklasse);
                name.setText("Benutzername:" + vusername);
                email.setText("Email-Adresse: " + vemail);
            }
        });

        tv_kurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fuser.isEmailVerified()) {
                    Toast.makeText(MainActivity.this, "Hier wird zum Kurs gewechselt", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Activity_Fach.class);
                    intent.putExtra("username", vusername);
                    intent.putExtra("email", vemail);
                    intent.putExtra("abteilung", vabteilung);
                    intent.putExtra("klasse", vklasse);
                    startActivity(intent);

                }
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void getTeacherForSubject(View view) {
        Intent intent = new Intent(getApplicationContext(), mySubjects_Activity.class);
        intent.putExtra("username", vusername);
        intent.putExtra("abteilung", vabteilung);
        startActivity(intent);
    }
}

