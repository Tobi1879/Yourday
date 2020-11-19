package com.example.yourday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yourday.R;

public class Hilfe extends AppCompatActivity {
    Button buttonAuftraege;
    Button buttonErstellen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilfe);

        buttonAuftraege = (Button) findViewById(R.id.buttonAuftraege);
        buttonErstellen = (Button) findViewById(R.id.buttonErstellen);

        // Klick Listeners
        buttonAuftraege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Aufträge Klick");
                doOpenAufgaben();
            }

        });

        buttonErstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Erstellen Klick");
                doOpenErstellen();
            }

        });
    }

    // Button für andere Activities
    private void doOpenAufgaben() {
        Intent intent = new Intent(this, Aufgaben.class);
        startActivity(intent);
    }
    private void doOpenErstellen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}