package com.example.shoku;

/*
Defines bottom navigator and the corresponding fragments here
(The previous version of MainActivity is moved to RecipesFragment)
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        if (this.getIntent().getIntExtra("from", 0) == 0) {
            //came from launch
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new RecipesFragment()).commit();
            bnv.setSelectedItemId(R.id.bottomHome);

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new CartFragment()).commit();
            bnv.setSelectedItemId(R.id.bottomCart);
        }
        bnv.setOnNavigationItemSelectedListener(btnList);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener btnList = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFrag = null;
            switch (item.getItemId()) {
                case R.id.bottomHome:
                    selectedFrag = new RecipesFragment();
                    break;
                case R.id.bottomPost:
                    selectedFrag = new PostFragment();
                    break;

                case R.id.bottomCart:
                    selectedFrag = new CartFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, selectedFrag).commit();
            return true;
        }

    };
}

