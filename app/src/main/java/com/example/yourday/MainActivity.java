package com.example.yourday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourday.model.Day;
import com.orm.SugarContext;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonUebersicht;
    Button buttonAuftraege;
    Button buttonSpeichern;
    ImageButton buttonKalender;
    ImageButton buttonHilfe;
    DatePickerDialog picker;
    TextView textViewDatum;
    ImageButton btnCapture;
    private static final int Image_Capture_Code = 1;


    List<Day> days;

    int id;
    TextView date;
    TextView ort;
    SeekBar wieWarTag;
    TextView erlebnis;
    ImageView imgCapture;
    String imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SugarContext.init(this);

        buttonSpeichern = (Button) findViewById(R.id.buttonSpeichern);
        buttonAuftraege = (Button) findViewById(R.id.buttonAuftraege);
        buttonUebersicht = (Button) findViewById(R.id.buttonErstellen);
        buttonKalender = (ImageButton) findViewById(R.id.imageButtonKalender);
        buttonHilfe = (ImageButton) findViewById(R.id.imageButtonHilfe) ;
        textViewDatum = (TextView) findViewById(R.id.textViewDatum);

        date = (TextView) findViewById(R.id.textViewDatum);
        ort = (EditText) findViewById(R.id.editTextOrt);
        wieWarTag = (SeekBar) findViewById(R.id.seekBarWieWarTag);
        erlebnis = (EditText) findViewById(R.id.editTextTextMultiLineErlebnis);
        //image = (Image) findViewById(R.id.imageView).
        Intent intent = getIntent();
        if( intent != null )  {
            intent.getAction();
            imageFile = ImageHandler.getImageFile();
            Log.d("IMAGE FILE ADD", "HAS INTENT");
            if(imageFile != null) {
                Log.d("IMAGE FILE ADD", imageFile);
                File imgFile = new  File(imageFile);

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) findViewById(R.id.imageView);
                    myImage.setImageBitmap(myBitmap);
                }
            }
        }

        final Calendar cldrStart = Calendar.getInstance();
        int day = cldrStart.get(Calendar.DAY_OF_MONTH);
        int month = cldrStart.get(Calendar.MONTH);
        int year = cldrStart.get(Calendar.YEAR);
        textViewDatum.setText(day + "/" + (month + 1) + "/" + year);
        if(ImageHandler.getDay() != null) {
            date.setText(ImageHandler.getDay().getDate());
        }

        days = Day.listAll(Day.class);
        for(int i = 0;i<days.size();i++){
            String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
            int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

            if(days.get(i).getDate().equals(date.getText().toString())){
                ort.setText(days.get(i).getOrt());
                erlebnis.setText(days.get(i).getErlebnis());
                wieWarTag.setProgress(days.get(i).getWieWarTag());
                /*imageFile = ImageHandler.getImageFile();
                if(days.get(i).getBild() != null) {
                    File imgFile = new  File(days.get(i).getBild());

                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgCapture.setImageBitmap(myBitmap);
                    }
                }*/
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

                    if(days.get(i).getDate().equals(date.getText().toString())){
                        ort.setText(days.get(i).getOrt());
                        erlebnis.setText(days.get(i).getErlebnis());
                        wieWarTag.setProgress(days.get(i).getWieWarTag());
                        //File file = new File(imageFile)
                    }
                }
            }

        });

        buttonAuftraege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Aufträge Klick");
                doOpenAufgaben();
            }

        });

        buttonHilfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hilfe Klick");
                doOpenHilfe();
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

                    if(days.get(i).getDate().equals(date.getText().toString())){
                        alreadyExists = true;
                        indexOfCurrentDay = i;
                    }
                }
                if(alreadyExists == false){
                    String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
                    int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

                    Day day = new Day(date.getText().toString(),ort.getText().toString(),wieWarTag.getProgress(),
                            erlebnis.getText().toString(),imageFile);
                    days.add(day);
                    day.save();
                } else {
                    days.get(indexOfCurrentDay).setOrt(ort.getText().toString());
                    days.get(indexOfCurrentDay).setErlebnis(erlebnis.getText().toString());
                    days.get(indexOfCurrentDay).setWieWarTag(wieWarTag.getProgress());
                    days.get(indexOfCurrentDay).setBild(imageFile);
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
                // zuerst Formular leeren
                ort.setText("");
                erlebnis.setText("");
                wieWarTag.setProgress(0);
                imgCapture.setImageBitmap(null);
                for(int i = 0;i<days.size();i++){
                    String dateJustNumbers = date.getText().toString().replaceAll("[^\\d.]", "");
                    int dateJustNumbersInteger = Integer.parseInt(dateJustNumbers);

                    if(days.get(i).getDate().equals(date.getText().toString())){
                        ort.setText(days.get(i).getOrt());
                        erlebnis.setText(days.get(i).getErlebnis());
                        wieWarTag.setProgress(days.get(i).getWieWarTag());
                        imageFile = ImageHandler.getImageFile();
                        if(days.get(i).getBild() != null) {
                            File imgFile = new  File(days.get(i).getBild());

                            if(imgFile.exists()){
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imgCapture.setImageBitmap(myBitmap);
                            }
                        }
                    }
                }
            }
        });

        // Kamera
        btnCapture =(ImageButton)findViewById(R.id.buttonImage);
        imgCapture = (ImageView) findViewById(R.id.imageView);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakePicClick();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imgCapture.setImageBitmap(bp);
                //MediaStore.Images.Media.insertImage(getContentResolver(), bp, "Aus App" , "Im Emu geschossen");
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void doTakePicClick() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    private void doOpenAufgaben() {
        Intent intent = new Intent(this, Aufgaben.class);
        startActivity(intent);
    }
    private void doOpenHilfe() {
        Intent intent = new Intent(this, Hilfe.class);
        startActivity(intent);
    }
}