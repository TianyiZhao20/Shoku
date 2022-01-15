package com.example.shoku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class ChattListAdapter extends ArrayAdapter<Recipe> {
    public ChattListAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
    }


    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {
        if (listItemView == null) {  // no recycled listItemView
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_chatt, parent, false);
        }

        Recipe recipe = getItem(position);
        TextView messageTextView = (TextView) listItemView.findViewById(R.id.messageListTextView);
        ImageView chattImageView = (ImageView) listItemView.findViewById(R.id.demoImageView);
        RecyclerView chattTagRecView = listItemView.findViewById(R.id.chattRecView);
        chattTagRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ChattTagListAdapter adapter = new ChattTagListAdapter(getContext());
        chattTagRecView.setAdapter(adapter);
        adapter.setTags(recipe.getTags());

        messageTextView.setText(recipe.getName());
        chattImageView.setImageBitmap(recipe.getImage());
        return listItemView;
    }
}
