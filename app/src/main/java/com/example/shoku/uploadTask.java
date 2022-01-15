package com.example.shoku;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

public class uploadTask extends AsyncTask<Recipe, Void, Void> {
    private Context mContext;


    public uploadTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Recipe... recipes) {
        Recipe recipe = recipes[0];
        new Bridge().uploadPost(recipe, mContext.getCacheDir());
        return null;
    }
}
