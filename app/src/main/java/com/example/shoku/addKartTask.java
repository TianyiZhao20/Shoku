package com.example.shoku;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class addKartTask extends AsyncTask<String, Void, Integer> {

    private WeakReference<TextView> msgTextViewReference;
    private Context mContext;
    private String authCode;
    private String accessToken;
    private String refreshToken;
    private static final String encodedAuthorization = "c2hva3Vtb3RzdS1lYTE2ZGVkZDRmYWNjZDc1YzYwODQ4OTlmMWU3MzJmZTM0MzEzNTU0ODE3MjQ5OTAxNDQ6bDhhNWFJZUZPRklKamZYbnNRQXFHRm40Z0RDYkxIMXphSWNVRTk3Sg==";//base64 encoded
    private static final CartSingleton localCart = CartSingleton.CartSingleton();
    private static final String tokenUrl = "https://api.kroger.com/v1/connect/oauth2/token";
    private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");



    public addKartTask(TextView textView, Context mContext) {
        msgTextViewReference = new WeakReference<TextView>(textView);
        this.mContext = mContext;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        authCode = strings[0];
        /*
         * USE THE STRING CONTAINING AUTHORIZATION TOKEN
         * GET ACCESS TOKEN AND REFRESH TOKEN
         * USE THE TOKEN TO ADD TO CART
         */
        //First build string of all items in cart
        String cartData = "{\"items\":[";
        for (int i = 0; i < localCart.cartItems.size() - 1; i++) {
            String cartItemData = "{\"upc\":\"" + localCart.cartItems.get(i).getProductId() +
                    "\",\"quantity\":" + Integer.toString(localCart.cartItems.get(i).getQuantity())
                    + "},";
            cartData = cartData + cartItemData;
        }
        cartData = cartData + "{\"upc\":\"" + localCart.cartItems.get(localCart.cartItems.size() - 1).getProductId() +
                "\",\"quantity\":" + Integer.toString(localCart.cartItems.get(localCart.cartItems.size() - 1).getQuantity())
                + "}]}";
        String urlPrefix = "https://api.kroger.com/v1/cart/add";
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(urlPrefix);
            int responseCode = -1;
            getAccessTokenFromAuthCode();
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            PrintStream os = new PrintStream(connection.getOutputStream());
            os.print(cartData);
            os.close();
            responseCode = connection.getResponseCode();
            connection.disconnect();
            if (connection.getResponseCode() == 401) {
                getAccessTokenFromRefreshToken();
            }
            return connection.getResponseCode();
//            while (responseCode != 204) {
//                if (responseCode == -1) {
//                    getAccessTokenFromAuthCode();
//                } else if (responseCode == 401) {
//                    getAccessTokenFromRefreshToken();
//                } else {
//                    return false;
//                }
//                connection = (HttpsURLConnection) url.openConnection();
//                connection.setRequestMethod("PUT");
//                connection.setRequestProperty("Accept", "application/json");
//                connection.setRequestProperty("Authorization", "Bearer " + accessToken);
//                PrintStream os = new PrintStream(connection.getOutputStream());
//                os.print(cartData);
//                os.close();
//                responseCode = connection.getResponseCode();
//                connection.disconnect();
//                if (responseCode == 204) {
//                    return true;
//                }
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer responseCode) {
        /*
         * CHANGE TEXT VIEW TO SUCCESS
         * TODO: empty local cart
         */
        if (msgTextViewReference!= null) {
            TextView msgTextView = msgTextViewReference.get();
//            if (responseCode == 204) {
//                msgTextView.setText("Added Successfully!");
//            } else {
//                msgTextView.setText("Error code" + Integer.toString(responseCode));
//            }
            msgTextView.setText("Added Successfully!");
            localCart.added = true;
        }
    }

    private void getAccessTokenFromRefreshToken() throws JSONException {
        /*
         * use refresh token to update access token
         * check that refresh token is not null before calling this
         */
        String content = "grant_type=refresh_token&refresh_token=" + refreshToken + "&redirect_uri=shokumotsu://callback";
        BufferedReader reader = null;
        HttpsURLConnection connection = null;
        try {


            URL url = new URL(tokenUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "application/json");
            PrintStream os = new PrintStream(connection.getOutputStream());
            os.print(content);
            os.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            StringWriter out = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String response = out.toString();
            JSONObject responseJsonObj = new JSONObject(response);
            accessToken = responseJsonObj.getString("access_token");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            connection.disconnect();
        }

    }

    private void getAccessTokenFromAuthCode() throws JSONException {
        /*
        use authCode to update access token and refresh token
         */
        String content = "grant_type=authorization_code&code=" + authCode + "&redirect_uri=shokumotsu://callback";
        BufferedReader reader = null;
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(tokenUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "application/json");
            PrintStream os = new PrintStream(connection.getOutputStream());
            os.print(content);
            os.close();
            if (connection.getResponseCode() == 400) {
                Toast.makeText(mContext, connection.getErrorStream().toString(), Toast.LENGTH_LONG).show();
            }
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            StringWriter out = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String response = out.toString();
//            Matcher matcher = pat.matcher(response);
//            if (matcher.matches() && matcher.groupCount() > 0) {
//                returnValue = matcher.group(1);
//            }
            JSONObject returnJSONObj = new JSONObject(response);
            accessToken = returnJSONObj.getString("access_token");
            refreshToken = returnJSONObj.getString("refresh_token");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            connection.disconnect();
        }

    }
}
