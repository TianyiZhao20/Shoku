package com.example.shoku;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChattRecViewAdapter extends RecyclerView.Adapter<ChattRecViewAdapter.ViewHolder> {
    private static final String TAG = "ChattRecViewAdapter";
    private ArrayList<Recipe> recipes;
    private Context mContext;

    public ChattRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_chatt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.imageView.setImageBitmap(recipes.get(position).getImage());
        holder.nameTextView.setText(recipes.get(position).getName());
        holder.tagsRecView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ChattTagListAdapter tagAdapter = new ChattTagListAdapter(mContext);
        holder.tagsRecView.setAdapter(tagAdapter);
        tagAdapter.setTags(recipes.get(position).getTags());

        holder.parentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DemoActivity.class);
//                intent.putExtra("name", recipes.get(position).getName());
//                intent.putExtra("image", recipes.get(position).imageToString());
//                intent.putExtra("ingredients", recipes.get(position).IngreToString());
//                intent.putExtra("instructions", recipes.get(position).getInstructions());
//                intent.putExtra("tags", recipes.get(position).tagToString());
//                intent.putExtra("servingSize", recipes.get(position).getServingSize());
//                intent.putExtra("cookTime", recipes.get(position).getCookTime());
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private CardView parentCard;
        private ImageView imageView;
        private RecyclerView tagsRecView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCard = itemView.findViewById(R.id.recipeParentCard);
            nameTextView = itemView.findViewById(R.id.messageListTextView);
            imageView = itemView.findViewById(R.id.demoImageView);
            tagsRecView = itemView.findViewById(R.id.chattRecView);
        }
    }
}
