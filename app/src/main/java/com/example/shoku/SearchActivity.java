package com.example.shoku;

/*
Defines the activity for showing the products related to the ingredient indicated
Search from using Kroger API
TODO: fix 404 bug
 */

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends MenuBaseActivity {

    private RecyclerView searchRecView;
    private SearchRecViewAdapter adapter;
    private String searchWord;
    private TextView searchLabel;
    private ArrayList<Product> products;
    private TextView warningMsg;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        invalidateOptionsMenu();

        searchLabel = findViewById(R.id.searchLabel);
        searchRecView = findViewById(R.id.searchRecView);
        warningMsg = findViewById(R.id.noResultsMsg);

        searchWord = this.getIntent().getStringExtra("ingreName");
//        searchWord = "milk";//in testing
        searchLabel.setText("Showing Results for " + searchWord);

        //comment this line when using kroger api
//        products = getTestProducts();

        //comment this line when using hard coded products and not api
//        new Thread(runnable).start();
        new SearchProductAPITask(searchRecView, warningMsg, this).execute(searchWord);


//        if (products != null && products.size() > 0) {
//            adapter = new SearchRecViewAdapter(this);
//            searchRecView = findViewById(R.id.searchRecView);
//
//            searchRecView.setLayoutManager(new GridLayoutManager(this, 2));
//            searchRecView.setAdapter(adapter);
//
//
//            adapter.setProducts(products);
//        }

    }

    /*
    runs in a different thread for HTTP connections to work
    calls getProductsFromAPI();
     */

//    Runnable runnable = new Runnable() {
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        public void run() {
//            // TODO: http request fix.
//
//            try {
//                products = getProductsFromAPI();
//                adapter = new SearchRecViewAdapter(SearchActivity.this);
//                searchRecView = findViewById(R.id.searchRecView);
//
//                searchRecView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
//                searchRecView.setAdapter(adapter);
//
//
//                adapter.setProducts(products);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    };


    private ArrayList<Product> getTestProducts () {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Kroger Round Top White Bread",
                "https://www.kroger.com/product/images/medium/front/0001111000120",
                1.25, "0001111000120"));
        products.add(new Product("Private Selection 100% Whole Wheat Sliced Wide Pan Bread",
                "https://www.kroger.com/product/images/medium/front/0001111000197",
                2.19, "0001111000197"));
        return products;
    }



}