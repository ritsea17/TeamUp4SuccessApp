package com.e.teamup4successapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Lehrer_Activity extends AppCompatActivity {

    TextView tv_auswahl;
    Button z;
    private static final String TAG = "Lehrer_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lehrer_activity);

        tv_auswahl = findViewById(R.id.tv_awl);
        Intent intent = getIntent();
        String Abteilung = intent.getStringExtra("abt");
        String Fach = intent.getStringExtra("fach");
        z = findViewById(R.id.btn_zL);
        Toast.makeText(Lehrer_Activity.this, "LehrerActivity", Toast.LENGTH_SHORT).show();
        tv_auswahl.setText("Abteilung: "+Abteilung+"\nFach: "+Fach);

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Activity_Fach.class));
            }
        });

    }

}
