package com.example.shoku;

/*
This is the Fragment Cart for the cart page associated with the cart bottom navigator
Displays a text: viewing cart
Then an array of cart items, images and quantity
Will update when products are added
Button add to kroger
Once clicked, go to another tab and come back, your cart will empty.

TODO: implement add to kroger with kroger api
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";

    //    private RecyclerView cartRecView;
//    private CartItemRecViewAdapter adapter;
    private Button addKroger;
//    private SwipeRefreshLayout swipe;
    private final CartSingleton localCart = CartSingleton.CartSingleton();


    private CartListAdapter cartAdapter;
    private ListView cartListView;
    private SwipeRefreshLayout refreshContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
         View view =  inflater.inflate(R.layout.cart_fragment, container, false);

         if (localCart.added) {
             //should empty cart
             localCart.cartItems.clear();
             localCart.added = false;
         }

         cartAdapter = new CartListAdapter(getContext(), new ArrayList<Product>());
         cartListView = (ListView) view.findViewById(R.id.cartListView);
         cartListView.setAdapter(cartAdapter);

         fillCart();

        refreshContainer = (SwipeRefreshLayout) view.findViewById(R.id.cartRefresh);

//
        //add to kroger cart button
        addKroger = view.findViewById(R.id.cartAddBtn);
        addKroger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localCart.cartItems.size() == 0) {
                    Toast.makeText(getContext(), "Please put items in your cart first",
                            Toast.LENGTH_LONG).show();
                } else {
                    //TODO: login and add to cart; after adding, clear cart
                    Intent intent = new Intent(getContext(), KrogerActivity.class);
                    startActivity(intent);
//                Toast.makeText(getContext(), "Added To your Kroger Cart", Toast.LENGTH_SHORT).show();

                }

            }
        });
//
        return view;
    }



    private void fillCart() {
        for (int i = 0; i < localCart.cartItems.size(); i++) {
            if (localCart.cartItems.get(i).getProductId() != "title") {
                cartAdapter.add(localCart.cartItems.get(i));
            }
        }
    }

}
