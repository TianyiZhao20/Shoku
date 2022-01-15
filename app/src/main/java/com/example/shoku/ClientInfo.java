package com.example.shoku;

/*
Will request information from Kroger API when searching for products
To use: call
String results = ClientInfo.getAPIInfo(KEYWORD);
 */

import android.widget.ArrayAdapter;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RequiresApi(api = Build.VERSION_CODES.O)
public class ClientInfo {
    private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
    private static final String clientId = "shokumotsu-ea16dedd4faccd75c6084899f1e732fe3431355481724990144";//clientId
    private static final String clientSecret = "l8a5aIeFOFIJjfXnsQAqGFn4gDCbLH1zaIcUE97J";//client secret
    private static final String tokenUrl = "https://api.kroger.com/v1/connect/oauth2/token";
//    private static final String auth = clientId + ":" + clientSecret;
//    private static final String authentication = Base64.getEncoder().encodeToString(auth.getBytes());
    private static final String encodedAuthorization = "c2hva3Vtb3RzdS1lYTE2ZGVkZDRmYWNjZDc1YzYwODQ4OTlmMWU3MzJmZTM0MzEzNTU0ODE3MjQ5OTAxNDQ6bDhhNWFJZUZPRklKamZYbnNRQXFHRm40Z0RDYkxIMXphSWNVRTk3Sg==";//base64 encoded
    private static final CartSingleton localCart = CartSingleton.CartSingleton();

    public static String getAPIInfo(String query) {
        String locationId = localCart.locationId;
        String cred = getClientCredentials("?scope=product.compact");
        return useBearerToken(cred, "https://api.kroger.com/v1/products", "?filter.term=" +
                query.toLowerCase() + "&filter.locationId=" + locationId);
    }

    public static String getLocationsFromZip(String zipCode) {
        String cred = getClientCredentials("?scope=product.compact");
        return useBearerToken(cred, "https://api.kroger.com/v1/locations",
                "?filter.zipCode.near=" + zipCode + "&filter.chain=KROGER");
    }


    //?scope=product.compact
    private static String getClientCredentials(String scope) {
        String content = "grant_type=client_credentials";
        BufferedReader reader = null;
        HttpsURLConnection connection = null;
        String returnValue = "";
        try {
            URL url = new URL(tokenUrl + scope);
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
            Matcher matcher = pat.matcher(response);
            if (matcher.matches() && matcher.groupCount() > 0) {
                returnValue = matcher.group(1);
            }
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
        return returnValue;
    }
    private static String useBearerToken(String bearerToken, String queryUrl, String pid) {
        String response = "";
        BufferedReader reader = null;
        int responseCode = -1;
        try {
            URL url = new URL(queryUrl + pid);
//            URL url = new URL("https://api.kroger.com/v1/products?filter.term=milk&locationId=01400376");
            System.out.println(queryUrl + pid);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            connection.setRequestProperty("Accept", "application/json");
//            connection.connect();
            responseCode = connection.getResponseCode();
//            System.out.println(connection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            StringWriter out = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            response = out.toString();
            //System.out.println(response);
        } catch (Exception e) {
            System.out.println(e);
            return Integer.toString(responseCode);
        }
        return response;
    }



//    public static void main(String[] args){
//        String testing = "hello world";
//        // testing = getClientCredentials("?scope=product.compact");
//        // testing = getClientCredentials("?scope=profile.compact");
//        testing = getClientCredentials("?scope=product.compact");
//        //System.out.println(testing);
//        // System.out.println(useBearerToken(testing, "https://api.kroger.com/v1/products/" ,"0004732320350"));
//        // System.out.println(useBearerToken(testing, "https://api.kroger.com/v1/identity/profile" ,""));
//        System.out.println(useBearerToken(testing, "https://api.kroger.com/v1/products" ,"?filter.term=milk"));
//        // extract the upc for further information
//    }
}