package com.example.shoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.net.*;
import java.io.*;
public class Bridge {
    public Bridge() {}

    public void uploadPost(Recipe recipe, File dir){
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
            String filename = recipe.getName() + time;
            File output=File.createTempFile(filename,".txt",dir);
//            output.setWritable(true);
            output.setWritable(true);
            output.setReadable(true);
            FileWriter newFile = new FileWriter(output);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("name",recipe.getName());
            params.put("instr",recipe.getInstructions());
            params.put("cookTime",recipe.getCookTime());
            params.put("servingSize",recipe.getServingSize());
            params.put("ingre",recipe.IngreToString());
            params.put("tags",recipe.tagToString());
            params.put("image",recipe.imageToString());

            JSONObject Jobj=new JSONObject(params);
            String toWrite=Jobj.toString();
            newFile.write(toWrite);
            newFile.close();
            Backend.uploadFile(output);
            System.out.println("Recipe Uploaded in my Bridge");
        }catch (Exception e){
            System.out.println("Recipe Upload Failed in my Bridge");
        }
    }
    public ArrayList<Recipe> retrievePost(String filePath){
        ArrayList<String> paths= Backend.listBlobs();
        ArrayList<Recipe> results = new ArrayList<>();
        for(int i=0;i<paths.size();i++){
            //download
            String uri=paths.get(i);
            try{
                URL oracle = new URL(uri);
                BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
                String inputLine;
                String recipe="";
                while((inputLine=in.readLine())!=null){
                    recipe+=inputLine;
                }
                in.close();
                JSONObject Jobj=new JSONObject(recipe);
                //Recipe(String name, Bitmap image, ArrayList<Ingredient> ingredients, String instructions, ArrayList<Integer> tags, String cookTime, String servingSize) {
                String rname=Jobj.getString("name");
                String rimage=Jobj.getString("image");
                String ringre=Jobj.getString("ingre");
                String rinstr=Jobj.getString("instr");
                String rtags=Jobj.getString("tags");
                String rcookTime=Jobj.getString("cookTime");
                String rservingSize=Jobj.getString("servingSize");


                results.add(new Recipe(rname,rimage,ringre,rinstr,rtags,rcookTime,rservingSize));
            }catch (IOException e){
                System.out.println("Reading recipe failed in my Bridge.");
            }catch (JSONException e){
                System.out.println("Converting to Json failed in my Bridge.");
            }
        }


        return results;
    }
}