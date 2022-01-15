package com.example.shoku;

import android.graphics.Bitmap;

import java.util.ArrayList;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Recipe {
    private String name;
    private Bitmap image;
    private ArrayList<Ingredient> ingredients;
    private String instructions;
    private ArrayList<Integer> tags;
    private String cookTime;
    private String servingSize;

    /*
    [vegan, spicy, mild, fast]
    [0, 0, 1, 1]
    tags [2, 3]
     */

    public String tagToString(){
        String str = "";
        for(int i=0;i<tags.size();i++){
            str+=tags.get(i)+"##";
        }
        return str;
    }

    public String imageToString(){
        ByteArrayOutputStream byteStream=new ByteArrayOutputStream();
        this.image.compress(Bitmap.CompressFormat.PNG,100,byteStream);
        byte [] byteArray=byteStream.toByteArray();
        String encoded= Base64.encodeToString(byteArray,Base64.DEFAULT);
        return encoded;
    }

    public void stringToTags(String input){
        String[] preprocessed=input.split("##");
        this.tags=new ArrayList<>();
        for(int i=0;i<preprocessed.length;i++){
            tags.add(new Integer(preprocessed[i]));
        }
    }
    public void stringToImage(String input){
        String image64=input.substring(input.indexOf(",")+1);
        byte[] decode=Base64.decode(image64,Base64.DEFAULT);
        this.image= BitmapFactory.decodeByteArray(decode,0,decode.length);
    }

    //convert ingredient list to string, used to put to backend
    public String IngreToString() {
        String str = "";
        if (ingredients == null || ingredients.size() == 0) return str;
        for (int i = 0; i < ingredients.size(); i++) {
            str = str + ingredients.get(i).name + "&&" + ingredients.get(i).qt + "&&" + ingredients.get(i).unit + "#//#";
        }
        return str;
    }

    public void StringToIngre(String input) {
        String[] preprocessed=input.split("#//#");
        ingredients = new ArrayList<>();
        for(int i = 0; i < preprocessed.length; i++){
            String[] tmp = preprocessed[i].split("&&");
            String unit = "";
            if (tmp.length > 2) {
                unit = tmp[2];
            }
            this.ingredients.add(new Ingredient(tmp[0],tmp[1],unit));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public ArrayList<Integer> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Integer> tags) {
        this.tags = tags;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    //constructor, called in postFragment submit button onclickListener
    public Recipe(String name, Bitmap image, ArrayList<Ingredient> ingredients, String instructions, ArrayList<Integer> tags, String cookTime, String servingSize) {
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.tags = tags;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
    }
    public Recipe(String name, String image, String ingredients, String instructions, String tags, String cookTime, String servingSize) {
        this.name = name;
        stringToImage(image);
        StringToIngre(ingredients);
        this.instructions = instructions;
        stringToTags(tags);
        this.cookTime = cookTime;
        this.servingSize = servingSize;
    }


}
