package com.e.teamup4successapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {


    private Button btn_login;
    TextView btnreg, fpsw;
    private EditText et_email;
    private EditText et_passwort;
    private TextView tv_message;
    private static final String TAG = "LoginFragment";
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_email= (EditText) findViewById(R.id.et_emailL);
        et_passwort = (EditText) findViewById(R.id.et_passwordL);
       btnreg = findViewById(R.id.reg);
       fpsw = findViewById(R.id.psw_v);

        Log.d(TAG, "onCreateView: started");

        fAuth = FirebaseAuth.getInstance();



        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login onCLick", Toast.LENGTH_SHORT).show();
                String mail = et_email.getText().toString().trim();
                String psw = et_passwort.getText().toString().trim();
                int unicode = 0x1F600;
                int unicode1 = 0x1F615;
                String emoji1 = getEmojiByUnicode(unicode1);


                if (TextUtils.isEmpty(mail)){
                    et_email.setError("Bitte eine Email eingeben"+emoji1);
                    return;
                }
                if(TextUtils.isEmpty(psw)){
                    et_passwort.setError("Bitte ein Passwort eingeben"+emoji1);
                    return;
                }
                fAuth.signInWithEmailAndPassword(mail,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"Error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        fpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());

                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Gib eine Email-Adresse ein um den Reset-Link zu erhalten");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString().trim();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this,"Reset-Link wurde gesendet",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset-Link wurde nicht gesendet"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
               startActivity(intent);
            }
        });





    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
