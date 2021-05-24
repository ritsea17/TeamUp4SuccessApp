package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_emailR;
    EditText et_passwortR;
    Spinner spinner_abteilung;
    Spinner spinner_klasse;
    EditText et_passwort1R;
    Button btn_register;
    TextView  btnlog;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "RegisterActivity";


private List<String> Listedaten = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        et_name = (EditText)  findViewById(R.id.et_name);
        et_emailR = (EditText) findViewById(R.id.et_emailR);
        et_passwortR = (EditText) findViewById((R.id.et_passwordR1));
        et_passwort1R = (EditText) findViewById((R.id.et_repasswordR2));
        btn_register = (Button) findViewById(R.id.btn_register);
        spinner_abteilung = (Spinner) findViewById(R.id.spinner_abteilung);
        spinner_klasse = (Spinner) findViewById(R.id.spinner_klasse);
       
        btnlog = findViewById(R.id.log);

        fAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(RegisterActivity.this, "Register onCLick", Toast.LENGTH_SHORT).show();

                String name = et_name.getText().toString().trim();
                String mail = et_emailR.getText().toString().trim();
                String psw = et_passwortR.getText().toString().trim();
                String psw1 = et_passwort1R.getText().toString().trim();
                String department = spinner_abteilung.getSelectedItem().toString();
                String klasse = spinner_klasse.getSelectedItem().toString();

                int unicode1 =0x1F605;
                int unicode2 = 0x1F600;
                int unicode3 = 0x1F615;

                String emoji1 = getEmojiByUnicode(unicode1);
                String emoji2= getEmojiByUnicode(unicode2);
                String emoji3 = getEmojiByUnicode(unicode3);

                if (TextUtils.isEmpty(mail)){
                    et_emailR.setError("Bitte eine Email eingeben"+emoji1);
                    return;
                }
                if(TextUtils.isEmpty(psw)){
                    et_passwortR.setError("Bitte ein Passwort eingeben"+emoji1);
                    return;
                }
                if (psw.equals(psw1)){
                    fAuth.createUserWithEmailAndPassword(mail,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this,"Verification Mail wurde gesendet",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this,"Error ! Verification Mail konnte nicht gesendet werden"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Toast.makeText(RegisterActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("fname",name);
                                user.put("email",mail);
                                user.put("department",department);
                                user.put("klasse",klasse);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created"+userID);
                                    }
                                });
                               startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }else{
                                Toast.makeText(RegisterActivity.this,"Error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    et_passwortR.setError("Bitte zwei mal das selbe Passwort eingeben"+emoji1);
                    return;
                }



            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }



    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
