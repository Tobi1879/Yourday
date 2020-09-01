package com.example.yourday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.DirectAction;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.yourday.model.Day;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonUebersicht;
    Button buttonAuftraege;
    Button buttonSpeichern;
    ImageButton buttonKalender;
    DatePickerDialog picker;
    TextView textViewDatum;

    List<Day> days;

    int id;
    TextView date;
    TextView ort;
    SeekBar wieWarTag;
    TextView erlebnis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SugarContext.init(this);

        buttonSpeichern = (Button) findViewById(R.id.buttonSpeichern);
        buttonAuftraege = (Button) findViewById(R.id.buttonAuftraege);
        buttonUebersicht = (Button) findViewById(R.id.buttonUebersicht);
        buttonKalender = (ImageButton) findViewById(R.id.imageButtonKalender);
        textViewDatum = (TextView) findViewById(R.id.textViewDatum);

        date = (TextView) findViewById(R.id.textViewDatum);
        ort = (EditText) findViewById(R.id.editTextOrt);
        wieWarTag = (SeekBar) findViewById(R.id.seekBarWieWarTag);
        erlebnis = (EditText) findViewById(R.id.editTextTextMultiLineErlebnis);

        final Calendar cldrStart = Calendar.getInstance();
        int day = cldrStart.get(Calendar.DAY_OF_MONTH);
        int month = cldrStart.get(Calendar.MONTH);
        int year = cldrStart.get(Calendar.YEAR);
        textViewDatum.setText(day + "/" + (month + 1) + "/" + year);

        days = Day.listAll(Day.class);
        for(int i = 0;i<days.size();i++){
            String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
            int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

            if(days.get(i).getDate() == dateJustNumbersInteger){
                ort.setText(days.get(i).getOrt());
                erlebnis.setText(days.get(i).getErlebnis());
                wieWarTag.setProgress(days.get(i).getWieWarTag());
            }
        }

        buttonKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ort.setText("");
                erlebnis.setText("");
                wieWarTag.setProgress(0);
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
                for(int i = 0;i<days.size();i++){
                    String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
                    int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

                    if(days.get(i).getDate() == dateJustNumbersInteger){
                        ort.setText(days.get(i).getOrt());
                        erlebnis.setText(days.get(i).getErlebnis());
                        wieWarTag.setProgress(days.get(i).getWieWarTag());
                    }
                }
            }

        });

        buttonAuftraege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Aufträge Klick");
                setContentView(R.layout.activity_aufgaben);
            }

        });

        buttonSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean alreadyExists = false;
                int indexOfCurrentDay = 0;
                for(int i = 0;i<days.size();i++){
                    String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
                    int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

                    if(days.get(i).getDate() == dateJustNumbersInteger){
                        alreadyExists = true;
                        indexOfCurrentDay = i;
                    }
                }
                if(alreadyExists == false){
                    String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
                    int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

                    Day day = new Day(dateJustNumbersInteger,ort.getText().toString(),wieWarTag.getProgress(),
                            erlebnis.getText().toString());
                    day.save();
                } else {
                    days.get(indexOfCurrentDay).setOrt(ort.getText().toString());
                    days.get(indexOfCurrentDay).setErlebnis(erlebnis.getText().toString());
                    days.get(indexOfCurrentDay).setWieWarTag(wieWarTag.getProgress());
                    days.get(indexOfCurrentDay).save();
                }
            }

        });

        date.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                for(int i = 0;i<days.size();i++){
                    String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
                    int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

                    if(days.get(i).getDate() == dateJustNumbersInteger){
                        ort.setText(days.get(i).getOrt());
                        erlebnis.setText(days.get(i).getErlebnis());
                        wieWarTag.setProgress(days.get(i).getWieWarTag());
                    }
                }
            }
        });
    }
}