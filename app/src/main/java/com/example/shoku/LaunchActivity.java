package com.example.shoku;

/*
Shows launch screen
can change SPASH_DISPLAY_LENGTH in ms
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LaunchActivity extends AppCompatActivity {

    private ImageView launchLogo;
    private TextView launchWarning;
//    private Button launchBtn;
    private TextView invalidZipCodeText;
    private EditText zipCodeInput;
    private Spinner locationSpinner;
    private Button startBtn;
    private TextView spinnerWarnText;
    private Button findLocations;

    private static final CartSingleton localCart = CartSingleton.CartSingleton();
//    private ArrayList<krogerLocation> locations;
    private ArrayList<String> locationIds;
    private ArrayList<String> locationNames;
    private boolean proceed = false;


    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_launch);


//        /* New Handler to start the Menu-Activity
//         * and close this Splash-Screen after some seconds.*/
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
//                LaunchActivity.this.startActivity(mainIntent);
//                LaunchActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
        invalidZipCodeText = findViewById(R.id.zipCodePromptText);
        zipCodeInput = findViewById(R.id.editTextZipCode);
        locationSpinner = findViewById(R.id.locationsSpinner);
        startBtn = findViewById(R.id.startBtn);
        findLocations = findViewById(R.id.searchZipCodeBtn);
        spinnerWarnText = findViewById(R.id.spinnerWarnTxt);
        locationNames = new ArrayList<>();
        locationIds = new ArrayList<>();
        locationNames.add("Select a store");

//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(LaunchActivity.this,
//                android.R.layout.simple_spinner_item, locationNames);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        locationSpinner.setAdapter(spinnerAdapter);

        findLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zipInput = zipCodeInput.getText().toString();
//                Toast.makeText(getApplicationContext(), zipInput, Toast.LENGTH_LONG).show();
                new GetLocationsTask(locationSpinner, invalidZipCodeText, LaunchActivity.this).execute(zipInput);

            }
        });



        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if spinner not selected prompt spinner warn text
                if (!localCart.locationInitiated) {
                    spinnerWarnText.setVisibility(View.VISIBLE);
                } else {
                    //then jump to main page
                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                    intent.putExtra("from", 0);
                    startActivity(intent);
                }
            }
        });


    }

}