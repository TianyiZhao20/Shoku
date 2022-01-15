package com.example.shoku;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetLocationsTask extends AsyncTask<String, Void, ArrayList<krogerLocation>> {
    private final WeakReference<Spinner> spinnerWeakReference;
    private final WeakReference<TextView> invalidTextViewReference;
    private String zip;
    private boolean zipValid;
    private ArrayAdapter<String> adapter;
    private Context mContext;
    private static final CartSingleton localCart = CartSingleton.CartSingleton();

    public GetLocationsTask(Spinner spinner, TextView textView, Context mContext) {
        spinnerWeakReference = new WeakReference<Spinner>(spinner);
        this.mContext = mContext;
        invalidTextViewReference = new WeakReference<TextView>(textView);
    }

    @Override
    protected ArrayList<krogerLocation> doInBackground(String... strings) {
        zip = strings[0];
        int zipNum = 0;
        try {
            zipNum = Integer.parseInt(zip);
        } catch (NumberFormatException nfe) {
            zipValid = false;
            return null;
        }
        if (zipNum < 10000 || zipNum > 99999) {
            zipValid = false;
            return null;
        }
        try {
            return getLocations();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<krogerLocation> getLocations() throws JSONException {
        String krogerResult = ClientInfo.getLocationsFromZip(zip);
        if (krogerResult == "-1" || krogerResult == "400") {
            zipValid = false;
            return null;
        }
            //otherwise fill spinner
            //parse through krogerResult in JSON, get locationId and name
        JSONObject locObj = new JSONObject(krogerResult);
        JSONArray locArray = locObj.getJSONArray("data");
        ArrayList<krogerLocation> locations = new ArrayList<>();
        for (int i = 0; i < locArray.length(); i++) {
            locations.add(new krogerLocation(locArray.getJSONObject(i).getString("locationId"),
                    locArray.getJSONObject(i).getString("name")));
//            locationIds.add(locArray.getJSONObject(i).getString("locationId"));
//            locationNames.add(locArray.getJSONObject(i).getString("name"));
        }

        if (locations.size() == 0) {
                //no locations found
            zipValid = false;
            return null;
        }
        zipValid = true;
        return locations;
    }

    @Override
    protected void onPostExecute(final ArrayList<krogerLocation> locations) {
        if (invalidTextViewReference != null) {
            if (!zipValid) {
                TextView textView = invalidTextViewReference.get();
                textView.setVisibility(View.VISIBLE);
                return;
            }
        }
        if (spinnerWeakReference!= null && locations != null) {
            Spinner spinner = spinnerWeakReference.get();
            if (spinner != null) {
                ArrayList<String> locationNames = new ArrayList<>();
                for (int i = 0; i < locations.size(); i++) {
                    locationNames.add(locations.get(i).getLocationName());
                }
                adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, locationNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                if (invalidTextViewReference != null) {
                    TextView textView = invalidTextViewReference.get();
                    textView.setVisibility(View.INVISIBLE);
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //store the id in the singleton
                        if (position > 0) {
                            localCart.locationId = locations.get(position).getLocationId();
                            localCart.locationInitiated = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //cannot proceed
                    }
                });
            }

        }

    }
}
