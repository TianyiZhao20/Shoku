package com.example.shoku;

/*
used only for local post testing. won't actually use this once the backend is implemented
 */

import java.util.ArrayList;

public class RecipeListSingleton {
    private static RecipeListSingleton recipesSingleton = null;
    public ArrayList<Recipe> recipes;

    private RecipeListSingleton() {
        this.recipes = new ArrayList<>();
    }

    public static RecipeListSingleton RecipeListSingleton() {
        if (recipesSingleton == null) {
            recipesSingleton = new RecipeListSingleton();
        }
        return recipesSingleton;
    }
}
