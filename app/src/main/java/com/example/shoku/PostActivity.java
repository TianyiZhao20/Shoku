package com.example.shoku;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


//目前不会用到这个Activity
/*
设想: 通过HTTP POST 的 JsonObject 包括
Image: base64String
Author: String
Title: String
Ingredients: String
Instruction: String
 */

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }
    /*
    public void addChatt(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://mobapp.eecs.umich.edu/addchatt/";
        TextView usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        TextView messageTextView = (TextView) findViewById(R.id.messageTextView);
        String username = usernameTextView.getText().toString();
        String message = messageTextView.getText().toString();
        String image = "";
        String videoString = "";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("message", message);
        params.put("image", image);
        params.put("video", videoString);
        params.put("audio", "");
        params.put("geodata", "");

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params),
                response -> Log.d("addChatt", "chatt posted!"),
                error -> Log.e("addChatt", "post error"));

        queue.add(postRequest);
        finish();
    }
    */

}