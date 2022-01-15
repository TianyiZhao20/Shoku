package com.example.shoku;

/*
This activity will prompt oauth2 log in and add to cart
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class KrogerActivity extends AppCompatActivity {

    private TextView krogerPageMsg;
    private static final String clientId = "shokumotsu-ea16dedd4faccd75c6084899f1e732fe3431355481724990144";//clientId
    private static final String clientSecret = "l8a5aIeFOFIJjfXnsQAqGFn4gDCbLH1zaIcUE97J";//client secret
    private static final String redirectUri = "shokumotsu://callback";
    private static final String encodedAuthorization = "c2hva3Vtb3RzdS1lYTE2ZGVkZDRmYWNjZDc1YzYwODQ4OTlmMWU3MzJmZTM0MzEzNTU0ODE3MjQ5OTAxNDQ6bDhhNWFJZUZPRklKamZYbnNRQXFHRm40Z0RDYkxIMXphSWNVRTk3Sg==";//base64 encoded


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kroger);

        krogerPageMsg = findViewById(R.id.krogerTextView);

        Intent loginIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.kroger.com/v1/connect/oauth2/authorize"
                + "?scope=product.compact+cart.basic:write&response_type=code&client_id=" + clientId
                + "&redirect_uri=" + redirectUri));
//        onNewIntent(loginIntent);
        startActivity(loginIntent);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            String authCode = uri.getQueryParameter("code");
            // add to cart
            new addKartTask(krogerPageMsg, KrogerActivity.this).execute(authCode);

        }
    }
}