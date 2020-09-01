package com.example.yourday.model;

import android.widget.SeekBar;

import java.util.Date;

public class Day {
    String date;
    String ort;
    int wieWarTag;
    String erlebnis;

    public Day(){
    }

    public Day(String date, String ort, int wieWarTag, String erlebnis){
        this.date = date;
        this.erlebnis = erlebnis;
        this.ort = ort;
        this.wieWarTag = wieWarTag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
