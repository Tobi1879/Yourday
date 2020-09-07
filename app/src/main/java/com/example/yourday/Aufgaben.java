package com.example.yourday;

import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        auftraege = Auftrag.listAll(Auftrag.class);

        for(int i = 0;i<auftraege.size();i++){
            if(auftraege.get(i).getText() != ""){
                itemsAdapter.add(auftraege.get(i).getText());
            }
        }
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Aufgabe entfernt", Toast.LENGTH_LONG).show();

                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.aufgabenHinzu);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))){
            Auftrag auftrag = new Auftrag(itemText);
            auftrag.save();
            auftraege.add(auftrag);
            itemsAdapter.add(auftrag.getText());
            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Schreibe eine Aufgabe.", Toast.LENGTH_LONG).show();
        }
    }
}