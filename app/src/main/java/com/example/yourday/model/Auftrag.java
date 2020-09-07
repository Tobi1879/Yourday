package com.example.yourday.model;

import android.media.Image;
import android.widget.SeekBar;

import com.orm.SugarRecord;

import java.util.Date;

public class Auftrag extends SugarRecord {
    String text;

    public Auftrag() {
    }
    public Auftrag(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
