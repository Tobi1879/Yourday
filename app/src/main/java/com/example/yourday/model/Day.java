package com.example.yourday.model;

import android.widget.SeekBar;

import com.orm.SugarRecord;

import java.util.Date;

public class Day extends SugarRecord {
    int date;
    String ort;
    int wieWarTag;
    String erlebnis;

    public Day(){
    }

    public Day(int date, String ort, int wieWarTag, String erlebnis){
        this.date = date;
        this.erlebnis = erlebnis;
        this.ort = ort;
        this.wieWarTag = wieWarTag;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getWieWarTag() {
        return wieWarTag;
    }

    public void setWieWarTag(int wieWarTag) {
        this.wieWarTag = wieWarTag;
    }

    public String getErlebnis() {
        return erlebnis;
    }

    public void setErlebnis(String erlebnis) {
        this.erlebnis = erlebnis;
    }
}
