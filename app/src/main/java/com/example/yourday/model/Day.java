package com.example.yourday.model;

import android.media.Image;
import android.widget.SeekBar;

import com.orm.SugarRecord;

import java.util.Date;

public class Day extends SugarRecord {
    String date;
    String ort;
    int wieWarTag;
    String erlebnis;
    String bild;

    public Day(){
    }

    public Day(String  date, String ort, int wieWarTag, String erlebnis, String bild){
        this.date = date;
        this.erlebnis = erlebnis;
        this.ort = ort;
        this.wieWarTag = wieWarTag;
        this.bild = bild;
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

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }
}
