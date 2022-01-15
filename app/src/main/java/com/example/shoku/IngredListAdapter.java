package com.example.shoku;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class IngredListAdapter extends RecyclerView.Adapter<IngredListAdapter.MyViewHolder> {
    private static final String TAG = "IngredListAdapter";
    private ArrayList<Ingredient> ingredients;
    private Context mContext;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private CardView parent;
        private TextView ingreName;
        private TextView ingreQt;
        private TextView ingreUnit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.ingreParent);
            ingreName = itemView.findViewById(R.id.ingreName);
            ingreQt = itemView.findViewById(R.id.ingreQt);
            ingreUnit = itemView.findViewById(R.id.ingreUnit);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public IngredListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        //View v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_ingre, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.myView.setText(mDataset.get(position));


        holder.ingreName.setText(ingredients.get(position).name);
        holder.ingreQt.setText(ingredients.get(position).qt);
        holder.ingreUnit.setText(ingredients.get(position).unit);


        //onclick parent card view, start intent for search page, passing string "ingreName"
        final Intent intent = new Intent(mContext, SearchActivity.class);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("ingreName", ingredients.get(position).name);
                mContext.startActivity(intent);
            }
        });

    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private CardView parent;
//        private TextView ingreName;
//        private TextView ingreQt;
//        private TextView ingreUnit;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            parent = itemView.findViewById(R.id.ingreParent);
//            ingreName = itemView.findViewById(R.id.ingreName);
//            ingreQt = itemView.findViewById(R.id.ingreQt);
//            ingreUnit = itemView.findViewById(R.id.ingreUnit);
//        }
//    }
}