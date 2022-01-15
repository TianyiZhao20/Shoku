package com.example.shoku;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChattTagListAdapter extends RecyclerView.Adapter<ChattTagListAdapter.ViewHolder> {
    private static final String TAG = "ChattTagListAdapter";
    private ArrayList<Integer> tags;
    private Context mContext;
    private static final String[] tagNames = {"Vegan", "Spicy", "Mild", "Fast"};

    public ChattTagListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setTags(ArrayList<Integer> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_chatt_tag, parent, false);
        ChattTagListAdapter.ViewHolder vh = new ChattTagListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.tagTextView.setText(tagNames[tags.get(position)]);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tagTextView;
        CardView tagCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagTextView = itemView.findViewById(R.id.tagTextView);
            tagCard = itemView.findViewById(R.id.chattTagParent);
        }
    }
}
