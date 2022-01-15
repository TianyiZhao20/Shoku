package com.example.shoku;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SearchProductAPITask extends AsyncTask<String, Void, ArrayList<Product>> {

    private final WeakReference<RecyclerView> searchRecViewReference;
    private final WeakReference<TextView> textViewWeakReference;
    private Context mContext;
    private String searchWord;
    private SearchRecViewAdapter adapter;

    public SearchProductAPITask(RecyclerView recView,TextView textView, Context mContext) {
        searchRecViewReference = new WeakReference<RecyclerView>(recView);
        textViewWeakReference = new WeakReference<TextView>(textView);
        this.mContext = mContext;
    }

    /*
    get product list in background
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        searchWord = strings[0];
        try {
            return getProductsFromAPI();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        if (searchRecViewReference!= null && products!= null) {
            RecyclerView searchRecView = searchRecViewReference.get();
            if (searchRecView != null) {
                adapter = new SearchRecViewAdapter(mContext);

//                searchRecView.setLayoutManager(new GridLayoutManager(mContext, 2));
                searchRecView.setLayoutManager(new LinearLayoutManager(mContext));
                searchRecView.setAdapter(adapter);


                adapter.setProducts(products);
                if (products.size() == 0) {
                    if (textViewWeakReference!= null) {
                        textViewWeakReference.get().setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        if (searchRecViewReference != null && textViewWeakReference != null && (products == null || products.size() == 0)) {
            textViewWeakReference.get().setVisibility(View.VISIBLE);
        }


    }

    /*
   This function gets the result from ClientInfo.getAPIInfo when searching for a product name
   then parses the string result and stores all related products in an array of products

   TODO: I have yet tested whether or not the parsing works, because it always fails in the first step
    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Product> getProductsFromAPI() throws IOException, JSONException {
        String nameSearchStr = ClientInfo.getAPIInfo(searchWord);

        JSONObject productsSearchObj = new JSONObject(nameSearchStr);
        JSONArray productsJsonArray = productsSearchObj.getJSONArray("data");
        ArrayList<Product> products = new ArrayList<>();
        for (int productIdx = 0; productIdx < productsJsonArray.length(); productIdx++) {
            JSONObject temp = productsJsonArray.getJSONObject(productIdx);
            String productId = temp.getString("productId");
            String description = temp.getString("description");
            //price
            double price = 0.0;
            if (temp.getJSONArray("items").length() > 0) {
                price = temp.getJSONArray("items").getJSONObject(0).
                        getJSONObject("price").optDouble("regular", 0.0);
            }
            //url
            String imgUrl = "";
            JSONArray tempArray;
            if ((tempArray = temp.getJSONArray("images")).length() > 0) {
                JSONArray tempArray2;
                if ((tempArray2 = tempArray.getJSONObject(0).getJSONArray("sizes")).length() > 0) {
                    imgUrl = tempArray2.getJSONObject(0).getString("url");
                }
            }
            products.add(new Product(description, imgUrl, price, productId));
        }
        return products;
    }
}
