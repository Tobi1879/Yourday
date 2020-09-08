package com.example.yourday;

import com.example.yourday.model.Auftrag;
import com.example.yourday.model.Day;

public class ImageHandler {

    private static String imageFile = null;
    private static Day day = null;

    public static String getImageFile() {
        return imageFile;
    }

    public static void setImageFile(String imageFile) {
        ImageHandler.imageFile = imageFile;
    }

    public static Day getDay() {
        return day;
    }

    public static void setDay(Day day) {
        ImageHandler.day = day;
    }
}
