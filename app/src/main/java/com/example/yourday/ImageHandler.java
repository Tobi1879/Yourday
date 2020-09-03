package com.example.yourday;

public class ImageHandler {

    private static String imageFile = null;

    public static String getImageFile() {
        return imageFile;
    }

    public static void setImageFile(String imageFile) {
        ImageHandler.imageFile = imageFile;
    }
}
