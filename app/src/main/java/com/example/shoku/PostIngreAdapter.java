package com.example.shoku;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostIngreAdapter extends RecyclerView.Adapter<PostIngreAdapter.ViewHolder> {
    private static final String TAG = "PostIngreAdapter";
    private ArrayList<Ingredient> ingredients;
    private Context mContext;

    public PostIngreAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_post_ingre, parent, false);
        ViewHolder vh = new PostIngreAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.postIngreNameTextView.setText(ingredients.get(position).name);
        holder.postIngreUnitTextView.setText(ingredients.get(position).unit);
        holder.postIngreQtTextView.setText(ingredients.get(position).qt);

//        holder.postDeleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ingredients.remove(position);
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postIngreNameTextView;
        CardView postIngreCardParent;
        TextView postIngreQtTextView;
        TextView postIngreUnitTextView;
//        Button postDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postIngreCardParent = itemView.findViewById(R.id.postIngreRecParent);
            postIngreNameTextView = itemView.findViewById(R.id.postIngreNameText);
            postIngreQtTextView = itemView.findViewById(R.id.postIngreQtText);
            postIngreUnitTextView = itemView.findViewById(R.id.postIngreUnitText);
//            postDeleteBtn = itemView.findViewById(R.id.postIngreDeleteBtn);
        }
    }
}
