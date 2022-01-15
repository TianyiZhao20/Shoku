package com.example.shoku;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RecipesLoadAsyncTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {

    private final WeakReference<RecyclerView> recyclerViewWeakReference;
    private Context mContext;
    private ChattRecViewAdapter adapter;

    public RecipesLoadAsyncTask(RecyclerView recyclerView, Context mContext) {
        this.recyclerViewWeakReference = new WeakReference<RecyclerView>(recyclerView);
        this.mContext = mContext;
    }

    @Override
    protected ArrayList<Recipe> doInBackground(Void... voids) {
        return new Bridge().retrievePost(Uri.fromFile(mContext.getFilesDir()).toString());
    }

    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        if (recyclerViewWeakReference != null && recipes != null) {
            RecyclerView recyclerView = recyclerViewWeakReference.get();
            if (recyclerView != null) {
                adapter = new ChattRecViewAdapter(mContext);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(adapter);
                RecipeListSingleton recipeListSingleton = RecipeListSingleton.RecipeListSingleton();
                recipeListSingleton.recipes = recipes;
                adapter.setRecipes(recipes);
            }
        }
    }
}
