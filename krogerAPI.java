import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class krogerAPI{
    private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
    private static final String clientId = "shokumotsu-ea16dedd4faccd75c6084899f1e732fe3431355481724990144";//clientId
    private static final String clientSecret = "l8a5aIeFOFIJjfXnsQAqGFn4gDCbLH1zaIcUE97J";//client secret
    private static final String tokenUrl = "https://api.kroger.com/v1/connect/oauth2/token";
    private static final String auth = clientId + ":" + clientSecret;
    private static final String authentication = Base64.getEncoder().encodeToString(auth.getBytes());
    private static final String authCode = "Hbly3AdgMyD0BhfI021mJNs6bLlxFvALWG80Bz71";
//    private String accessToken;
//    private String refreshToken;
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
            connection.setRequestProperty("Authorization", "Basic " + authentication);
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
        try {
            URL url = new URL(queryUrl + pid);
            System.out.println(queryUrl + pid);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            connection.setRequestProperty("Accept", "application/json");
            //System.out.println(connection.getInputStream());
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
        }
        return response;
    }

    private static String getAccessTokenFromRefreshToken(String refreshToken) {
        /*
         * use refresh token to update access token
         * check that refresh token is not null before calling this
         */
        String content = "grant_type=refresh_token&refresh_token=" + refreshToken;
        BufferedReader reader = null;
        HttpsURLConnection connection = null;
        String accessToken = "";
        try {


            URL url = new URL(tokenUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authentication);
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
            if (matcher.matches() && matcher.groupCount() > 0)
            accessToken = matcher.group(1);

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
        return accessToken;
    }

    private static ArrayList<String> getAccessTokenFromAuthCode() {
        /*
        use authCode to update access token and refresh token
         */
        String content = "grant_type=authorization_code&code=" + authCode;
        BufferedReader reader = null;
        HttpsURLConnection connection = null;
        String accessToken = "";
        String refreshToken = "";
        try {
            URL url = new URL(tokenUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authentication);
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
//                returnValue = matcher.group(1);
                accessToken = matcher.group(1);
                refreshToken = matcher.group(3);
            }
//            JSONObject returnJSONObj = new JSONObject(response);
//            accessToken = returnJSONObj.getString("access_token");
//            refreshToken = returnJSONObj.getString("refresh_token");
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
        ArrayList<String> tokens =  new ArrayList<>();
        tokens.add(accessToken);
        tokens.add(refreshToken);
        return tokens;
    }

    public static void main(String[] args){

//        int i = Integer.parseInt("48104");

//        String credential_token = getClientCredentials("?scope=product.compact");
        //System.out.println(testing);
        // System.out.println(useBearerToken(testing, "https://api.kroger.com/v1/products/" ,"0004732320350"));
        // System.out.println(useBearerToken(testing, "https://api.kroger.com/v1/identity/profile" ,""));
//        String productSearchOutput = useBearerToken(credential_token, "https://api.kroger.com/v1/products" ,"?filter.term=milk");
//        System.out.println(productSearchOutput);
        // extract the upc for further information

        //location
//        String locations = useBearerToken(credential_token, "https://api.kroger.com/v1/locations", "?filter.zipCode.near=48104&filter.chain=Kroger");
//        System.out.println(locations);
//        String latLongLocations = useBearerToken(credential_token, "https://api.kroger.com/v1/locations", "?filter.lat.near=42.2808&filter.long.near=83.7430&filter.chain=Kroger");
//        System.out.println(latLongLocations);

        //oauth token
        ArrayList<String> tokens = getAccessTokenFromAuthCode();
        System.out.println(tokens.get(0));
        System.out.println(tokens.get(1));
        System.out.println(getAccessTokenFromRefreshToken(tokens.get(1)));
    }
}
