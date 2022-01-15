package com.example.shoku;

/*
Activity for a single Recipe page
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoku.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.util.ArrayList;

public class DemoActivity extends MenuBaseActivity {
    private RecyclerView recyclerView;
    private IngredListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        invalidateOptionsMenu();

        recyclerView = (RecyclerView) findViewById(R.id.ingreRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //set up values
        Intent intent=this.getIntent();

        ImageView demoImageView = (ImageView) findViewById(R.id.demoImageView);
        TextView demoTitle = (TextView) findViewById(R.id.demoTitle);
        RecyclerView demoTagRecView = findViewById(R.id.demoTagRecView);
        TextView demoInstructions = (TextView) findViewById(R.id.demoInstructions);
        ChattTagListAdapter adapter = new ChattTagListAdapter(this);
        TextView demoCookTime = findViewById(R.id.demoCookTime);
        TextView demoServingSize = findViewById(R.id.demoServingSize);

        //use first line in actual app, second for testing
//        Data myData = getDataFromPreviousView();
//        Data myData = getTestData();

//        try {
//            FileInputStream is = this.openFileInput(myData.filename);
//            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//            demoImageView.setImageBitmap(bitmap);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        demoTitle.setText(myData.title);
//        demoAuthor.setText(myData.author);
//        demoInstructions.setText(myData.instr);
//        parseIngre(myData.preIngre);

//        Recipe recipe = new Recipe(this.getIntent().getStringExtra("name"),
//                getIntent().getStringExtra("image"), getIntent().getStringExtra("ingredients"),
//                getIntent().getStringExtra("instructions"), getIntent().getStringExtra("tags"),
//                getIntent().getStringExtra("cookTime"), getIntent().getStringExtra("servingSize"));
        RecipeListSingleton recipeListSingleton = RecipeListSingleton.RecipeListSingleton();
        Recipe recipe = recipeListSingleton.recipes.get(getIntent().getIntExtra("position", 0));

        demoTitle.setText(recipe.getName());
        demoImageView.setImageBitmap(recipe.getImage());
        ingredients = recipe.getIngredients();
        demoInstructions.setText(recipe.getInstructions());
        demoCookTime.setText(recipe.getCookTime());
        demoServingSize.setText("Serving Size: " + recipe.getServingSize());
        demoTagRecView.setAdapter(adapter);
        demoTagRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter.setTags(recipe.getTags());

        mAdapter = new IngredListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setIngredients(ingredients);


    }



//    private class Data {
//        private String title;
//        private String author;
//        private String instr;
//        private String filename;
//        private String preIngre;
//
//        public Data(String title, String author, String instr, String filename, String preIngre) {
//            this.title = title;
//            this.author = author;
//            this.instr = instr;
//            this.filename = filename;
//            this.preIngre = preIngre;
//        }
//    }
//
//    private Data getDataFromPreviousView() {
//
//        return new Data(this.getIntent().getStringExtra("title"),
//                this.getIntent().getStringExtra("author"),
//                this.getIntent().getStringExtra("instr"),
//                this.getIntent().getStringExtra("img"),
//                this.getIntent().getStringExtra("ingre"));
//    }

//    private Data getTestData() {
////        ArrayList<Data> dataset = new ArrayList<>();
////        dataset.add(new Data("Pasta", "Mary", "here are\n the instructions\n",
////                BitmapFactory.decodeResource(getResources(),R.drawable.pasta),
////                "Pasta&&1&&serving#//#Tomatoes&&2&&qt"));
////        dataset.add(new Data("Salad", "John", "another\n Instruction for salad",
////                BitmapFactory.decodeResource(getResources(), R.drawable.salad),
////                "Lettuce&&1&&head#//#Crouton&&1&&package"));
//        return new Data("Pasta", "Mary", "here are\n the instructions\n",
//                BitmapFactory.decodeResource(getResources(),R.drawable.pasta),
//                "Pasta&&1&&serving#//#Tomatoes&&2&&qt");
//    }

//    private void parseIngre(String input){
//        String[] preprocessed=input.split("#//#");
//        this.ingredients = new ArrayList<>();
//        for(int i = 0; i < preprocessed.length; i++){
//            String[] tmp = preprocessed[i].split("&&");
//            String unit = "";
//            if (tmp.length > 2) {
//                unit = tmp[2];
//            }
//            this.ingredients.add(new Ingredient(tmp[0],tmp[1],unit));
//        }
//    }
}