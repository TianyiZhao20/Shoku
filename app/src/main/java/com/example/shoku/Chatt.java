package com.example.shoku;

/*
Chatt class, defines a class used in the recipes fragment
 */

import android.graphics.Bitmap;

public class Chatt {
    public String username;
    public String title;
    public String ingredients;
    public String instructions;
    public String timestamp;
    public Bitmap image;

    public Chatt(String username, String title, String ingredients, String instructions, String timestamp, Bitmap image) {
        this.username = username;
        this.title = title;
        this.ingredients=ingredients;
        this.instructions=instructions;
        this.timestamp = timestamp;
        this.image = image;
    }
}
