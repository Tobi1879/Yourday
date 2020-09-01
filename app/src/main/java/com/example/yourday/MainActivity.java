package com.example.yourday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageButton buttonKalender;
    DatePickerDialog picker;
    TextView textViewDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonKalender = (ImageButton) findViewById(R.id.imageButtonKalender);
        textViewDatum = (TextView) findViewById(R.id.textViewDatum);

        buttonKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Kalender Klick");
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textViewDatum.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

        });
    }
}