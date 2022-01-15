package com.example.shoku;

/*
This is the Cart List Adapter class
Defines the adapter activity for items in the cart
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CartListAdapter extends ArrayAdapter<Product> {
    public CartListAdapter(Context context, ArrayList<Product> cartItems) {
        super(context, 0, cartItems);
    }


    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {
        if (listItemView == null) {  // no recycled listItemView
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_cart, parent, false);
        }

        Product cartItem = getItem(position);
        TextView cartItemNameView = (TextView) listItemView.findViewById(R.id.cartItemNameTextView);
        TextView cartItemQtView = (TextView) listItemView.findViewById(R.id.cartItemQtTextView);
        ImageView cartItemImageView = (ImageView) listItemView.findViewById(R.id.cartItemImageView);
        cartItemNameView.setText(cartItem.getName());
        cartItemQtView.setText(Integer.toString(cartItem.getQuantity()));
        new BitmapWorkerTask(cartItemImageView).execute(cartItem.getImgUrl());
        return listItemView;
    }
}
