package com.example.yourday;

import android.app.DatePickerDialog;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourday.model.Day;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button buttonUebersicht;
    Button buttonAuftraege;
    ImageButton buttonKalender;
    DatePickerDialog picker;
    TextView textViewDatum;

    ArrayList<Day> days = new ArrayList<Day>();

    TextView date;
    TextView ort;
    SeekBar wieWarTag;
    TextView erlebnis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SugarContext.init(this);

        buttonAuftraege = (Button) findViewById(R.id.buttonAuftraege);
        buttonUebersicht = (Button) findViewById(R.id.buttonUebersicht);
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

        buttonAuftraege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Aufträge Klick");
                setContentView(R.layout.activity_aufgaben);
            }

        });

        date = (TextView) findViewById(R.id.textViewDatum);
        ort = (EditText) findViewById(R.id.editTextOrt);
        wieWarTag = (SeekBar) findViewById(R.id.seekBarWieWarTag);
        erlebnis = (EditText) findViewById(R.id.editTextTextMultiLineErlebnis);

        ort.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Boolean alreadyExists = false;
                int indexOfCurrentDay = 0;
                for(int i = 0;i<days.size();i++){
                    if(days.get(i).getDate() == date.getText().toString()){
                        alreadyExists = true;
                        indexOfCurrentDay = i;
                    }
                }
                if(alreadyExists == false){
                    Day day = new Day(date.getText().toString(),ort.getText().toString(),wieWarTag.getProgress(),
                            erlebnis.getText().toString());
                    days.add(day);
                } else {
                    days.get(indexOfCurrentDay).setOrt(ort.getText().toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        wieWarTag.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                Boolean alreadyExists = false;
                int indexOfCurrentDay = 0;
                for(int i = 0;i<days.size();i++){
                    if(days.get(i).getDate() == date.getText().toString()){
                        alreadyExists = true;
                        indexOfCurrentDay = i;
                    }
                }
                if(alreadyExists == false){
                    Day day = new Day(date.getText().toString(),ort.getText().toString(),wieWarTag.getProgress(),
                            erlebnis.getText().toString());
                    days.add(day);
                } else {
                    days.get(indexOfCurrentDay).setWieWarTag(wieWarTag.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
            }
        });
    }
}