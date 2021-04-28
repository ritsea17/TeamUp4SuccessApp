package com.e.teamup4successapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView name, email, phone, tv_kurs;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name= findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        phone = findViewById(R.id.tv_phone);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        tv_kurs = findViewById(R.id.tv_switch);
        userID = fAuth.getCurrentUser().getUid();
        FirebaseUser fuser = fAuth.getCurrentUser();

        if(fuser.isEmailVerified()) {
            Toast.makeText(MainActivity.this, "Sucsess: Email wurde erfolgreich verifiziert ", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "ERROR ! Bitte Verifiziere deine Email Adresse" +
                    "\nDu kannst sonst nicht in die Kursansicht wechseln", Toast.LENGTH_SHORT).show();
            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this,"Verification Mail wurde gesendet",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,"Error ! Verification Mail konnte nicht gesendet werden"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phone.setText("Telefon-Nummer: "+value.getString("Nummer"));
                name.setText("Benutzername:"+value.getString("fname"));
                email.setText("Email-Adresse: "+value.getString("email"));
                data = value.getString("fname");
            }});

        tv_kurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fuser.isEmailVerified()){
                    Toast.makeText(MainActivity.this, "Hier wird zum Kurs gewechselt", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this, Activity_Fach.class);
                    intent.putExtra("username",data);
                    startActivity(intent);

            }}
        });

    }



            public void logout(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }

        }

