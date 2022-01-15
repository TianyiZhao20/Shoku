package com.example.shoku;

/*
This is what used to be MainActivity
Defines the fragment view for list of recipes
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class RecipesFragment extends Fragment {
//    private ChattListAdapter chatts;
//    private ListView chattListView;
    private SwipeRefreshLayout refreshContainer;
//    private ChattRecViewAdapter recViewAdapter;
    private ArrayList<Recipe> recipes;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View view =  inflater.inflate(R.layout.recipes_fragment, container, false);



        final RecyclerView recyclerView = view.findViewById(R.id.recipeFragmentRecView);
        RecipeListSingleton recipeListSingleton = RecipeListSingleton.RecipeListSingleton();
        if (recipeListSingleton.recipes.size() == 0) {
            new RecipesLoadAsyncTask(recyclerView, getContext()).execute();
        } else {
            ChattRecViewAdapter recViewAdapter = new ChattRecViewAdapter(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(recViewAdapter);
            recViewAdapter.setRecipes(recipeListSingleton.recipes);
        }

        refreshContainer = (SwipeRefreshLayout) view.findViewById(R.id.refreshContainer);
        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RecipesLoadAsyncTask(recyclerView, getContext()).execute();
            }
        });
        return view;
    }

//    private void populateChattsPostTest() {
//        final RecipeListSingleton recipeSingleton = RecipeListSingleton.RecipeListSingleton();
//        for (int i = 0; i < recipeSingleton.recipes.size(); i++) {
//            chatts.add(recipeSingleton.recipes.get(i));
//        }
//    }

//    private void loadChattsFromBackend() {
//        recipes = new Bridge().retrievePost();
//    }



//    private void populateChatts(){
//
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.salad);
//        Chatt c2 = new Chatt("John", "Pasta Salad",
//                "Spiral pasta&&1&&lb#//#Italian salad dressing&&16&&oz#//#Cherry tomatoes&&2&&cup#//#Bell pepper&&1&&#//#Feta cheese&&1&&block#//#Pine nuts&&1/3&&cup#//#Spinach&&10&&oz",
//                "1. Cook the pasta\n2. Dice the tomatoes, bell pepper and feta cheese\n3. Mix with salad dressing, spinach and pine nuts",
//                "15 minutes ago", bitmap2);
//        chatts.add(c2);
//        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.pasta);
//        Chatt c3 = new Chatt("Andrew", "Spaghetti",
//                "Spaghetti pasta&&1&&lb#//#Ground beef&&1&&lb#//#Italian sausage&&0.5&&lb#//#Tomato paste&&6&&oz#//#Tomato sauce&&15&&oz#//#Canned tomatoes&&15&&oz#//#Onion&&1&&head#//#Garlic&&2&&cloves#//#Basil leaves&&1&&tsp#//#Parmesan cheese&&2&&tbsp",
//                "1. Cook the beaf and sausage, add onion and garlic\n2. Add salt, tomato paste, tomato sauce and canned crushed tomatoes and stir\n3. Cook the pasta al dente\n4. Pour meat sauce over pasta, garnish with dried basil leaves and parmesan cheese",
//                "Oct 29, 2020", bitmap3);
//        chatts.add(c3);
//        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.creamcheesecookies);
//        Chatt c1=new Chatt("Mary","Cream Cheese Cookies",
//                "Butter&&56&&g#//#Cream cheese&&56&&g#//#Sugar&&100&&g#//#Egg&&1&&#//#All purpose flour&&105&&g#//#Baking powder&&0.25&&tsp#//#Salt&&0.25&&tsp",
//                "1. Whip the butter and cream cheese to a creamy texture\n2. Mix in the sugar and egg\n3. Mix the flour, baking powder and salt together and add to your wet mixture\n4. Bake in a 375 degree oven for 10 minutes",
//                "13:30 today",bitmap1);
//        chatts.add(c1);
//
//        chatts.add(new Chatt("Kristina", "Chicken Katsu",
//                "Chicken Breast&&2&&cts#//#Egg&&1&&ct#//#All-purpose Flour&&2&&tablespoons#//#White Pepper&&1&&tsp#//#salt&&1&&tsp#//#Bread crumbs&&1&&cup#//#Oil&&1&&cup",
//                "1. Split the chicken breasts into halfs. Season the chicken breasts on both sides with salt and pepper.\n2. Beat the egg well. Place the flour, the beaten egg and bread crumbs into seperate shallow dishes.\n3. Coat the chicken breasts in flour, shaking off any excess. Dip them into egg, and them press into the bread crumbs until well coated on both sides.\n4. Heat 1/4 inch of oil in a large skillet over medium-high heat. Place chicken in the hot oil, and cook 3 to 4 minutes per side, or until golden brown.",
//                "1 hour ago", BitmapFactory.decodeResource(getResources(), R.drawable.crispychicken)));
//
//        chatts.add(new Chatt("Chung-Ae", "Kimbap",
//                "dried Seaweed Sheets&&4&&sheets#//#Spinach&&4.2&&ounces#//#Eggs&&2&&cts#//#imitation crab sticks&&3&&cts#//#Carrot&&1/2&&cts#//#BBQ Kimbap ham&&4&&sticks#//#yellow radish pickle&&4&&strips#//#cooked short grain rice&&2 1/2&&cups#//#sesame oil&&2&&tablespoons#//#fine sea salt&&3/8&&tsp",
//                "1. Put spinach in boiling water. After 30 seconds, drain the water away. Squeeze the spinach to remove excess water. Add 1/8 tsp salt and 1/2 tbsp sesame oil. Mix them well.\n2. Beat the eggs. Preheat a pan, add a small dash of cooking oil, and cook the beaten egg well on both sides over medium heat.\n3. slightly cook the shredded carrots, crab sticks and kimbap ham over medium heat\n4. Cut the egg omelette, the crab sticks and the kimbap ham into long strips\n5. Mix 1/2 Tbsp sesame oil and 1/4 tsp fine sea salt with cooked rice.\n6. Place one dried seaweed sheet on the bamboo mat. Put some rice on the seaweed sheet and spread it evenly and thinly to cover about 2/3 of the seaweed.\n7. Fill the seaweed with the filling ingredients. Roll the seaweed sheet up.",
//                "3 days ago", BitmapFactory.decodeResource(getResources(), R.drawable.sushi)));
//
//        chatts.add(new Chatt("Sohla El-Waylly", "Flank Steak with Peaches and Dandelian Greens",
//                "flank steak&&1&&lb#//#vegetable oil&&2&&tbsp#//#fresh thyme&&2&&sprigs#//#garlic&&3&&clove#//#butter&&2&&tbsp#//#peach&&1&&large#//#chili&&1&&tbsp#//#lemon juice&&2&&tbsp#//#dandelion greens&&1&&bunch#//#salt&&1&&tsp#//#black pepper&&1&&tsp",
//                "1. Put a cast iron skillet on high heat and put oil in until smoking hot.\n2. Season flank steak with kosher salt and pepper.\n3. Put flank steak on, flipping every 30 seconds for about 8 minutes, or until medium done.\n4. Add thyme, garlic and butter to the pan, lower the pan to medium heat. Use a spoon to baste the steak in butter for about 4 minutes.\n5. Remove the steak on an iron rack and pour butter and aromatics over steak.\n6. Return the pan to high heat and sear peach wedges until golden brown for about 1 minute.\n7. Remove the peaches. Add 1/2 cup water, lemon juice, chili and 2 tablespoons of steak sauce to the pan, scraping up the brown bits until the sauce is emulsified.\n8. Add dandelion greens until gently wilted for about 1 minute.\n9. Slice steak and serve right away with peach, dandelion greens and sauce.",
//                "July 24, 2018", BitmapFactory.decodeResource(getResources(),R.drawable.steak)));
//
//        chatts.add(new Chatt("Anonymous user", "Scallion Pancakes",
//                "All purpose flour&&200&&g#//#water&&140&&g#//#scallions&&1&&bunch#//#vegetable oil&&2&&tbsp#//#salt&&1&&tsp",
//                "1. Mix flour and water to form a wet dough. Rest for at least 30 minutes.\n2. Chop scallions into small bits.\n3. Knead the dough a few times until smooth. Rest for an additonal 5 minutes if needed. Roll it out to a thin layer at 1/4 inches thick.\n4. sprinkle salt and press into the dough.\n5. Pour the oil onto the dough and fold the dough to spread evenly.\n6. Sprinkle scallions generously.\n7. Roll the dough into a string and seal the ends. Twist and fold into a pancake shape.\n8. Rest for 20 minutes and flatten to be 1/3 inches thick.\n9. Heat a nonstick pan and put the pancake on. Cook without cover until the bottom side is golden before flipping to the other side.\n10. Cook the other side with cover on until the bottom is golden.\n11. Serve after cutting into wedges.",
//                "Oct 31, 2020", BitmapFactory.decodeResource(getResources(), R.drawable.scallion)));
//    }

    /*
    public void startPost(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }
    private void getChatts() {
        chatts.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://mobapp.eecs.umich.edu/getchatts/";

        JsonObjectRequest getRequest = new JsonObjectRequest( url, null,
                response -> {
                    try {
                        Log.d("getMessage","Will try Get");
                        JSONArray chattsReceived = response.getJSONArray("chatts");
                        for (int i = 0; i < chattsReceived.length(); i++) {
                            JSONArray chattEntry = chattsReceived.getJSONArray(i);
                            chatts.add(new Chatt(
                                    chattEntry.getString(0), // username
                                    chattEntry.getString(1), // message
                                    chattEntry.getString(2),  // timestamp

                            ));
                        }
                        Log.d("getMessage","Tried Get");
                    }
                    catch (JSONException e) {
                        Log.e("getChatts", String.valueOf(e.getLocalizedMessage()));
                    }
                    refreshContainer.setRefreshing(false);
                },
                error -> refreshContainer.setRefreshing(false)
        );

        queue.add(getRequest);
    }
    */
}
