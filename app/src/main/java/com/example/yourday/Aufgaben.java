package com.example.yourday;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourday.model.Auftrag;
import com.example.yourday.model.Day;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class Aufgaben extends AppCompatActivity {

    List<Auftrag> auftraege;

    Button buttonAuftraege;
    ImageButton buttonHilfe;
    Button buttonErstellen;

    private ArrayList<String> items;
    private ArrayAdapter<Auftrag> itemsAdapter;
    private ListView listView;
    private Button button;
    private int instances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgaben);

        SugarContext.init(this);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.buttonHinzufuegen);
        buttonHilfe = (ImageButton) findViewById(R.id.imageButtonHilfe);
        buttonErstellen = (Button) findViewById(R.id.buttonErstellen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        auftraege = Auftrag.listAll(Auftrag.class);

        itemsAdapter = new ArrayAdapter<Auftrag>(this, android.R.layout.simple_list_item_1, auftraege);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        buttonAuftraege = (Button) findViewById(R.id.buttonAuftraege);

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

        buttonHilfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hilfe Klick");
                doOpenHilfe();
            }

        });
    }

    // Aufgabe erledigt -> entfernen
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Aufgabe entfernt", Toast.LENGTH_LONG).show();

                // Datensatz löschen, und Aufgabe/Item entfernen
                itemsAdapter.getItem(i).delete();
                itemsAdapter.remove(itemsAdapter.getItem(i));
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    // add Aufgabe
    private void addItem(View view) {
        EditText input = findViewById(R.id.aufgabenHinzu);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))){
            Auftrag auftrag = new Auftrag(itemText);
            auftrag.save();
            itemsAdapter.add(auftrag);
            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Schreibe eine Aufgabe.", Toast.LENGTH_LONG).show();
        }
    }

    // Button für andere Screens / Activitys
    private void doOpenAufgaben() {
        Intent intent = new Intent(this, Aufgaben.class);
        startActivity(intent);
    }
    private void doOpenHilfe() {
        Intent intent = new Intent(this, Hilfe.class);
        startActivity(intent);
    }
    private void doOpenErstellen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}