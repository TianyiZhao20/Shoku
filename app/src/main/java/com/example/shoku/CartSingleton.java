package com.example.shoku;

/*
Global variable as cartsingleton.
To use:
CartSingleton localCart = CartSingleton.CartSingleton();
And access using
localCart.cartItems
as a public ArrayList of Products
 */

import java.util.ArrayList;


public class CartSingleton {
    private static CartSingleton cart = null;
    public ArrayList<Product> cartItems;
    public String locationId;
    public boolean locationInitiated;
    public boolean added;

    private CartSingleton() {
        this.cartItems = new ArrayList<>();
        this.locationId = "";
        this.locationInitiated = false;
        this.added = false;
        //you can ignore this line below...
//        cartItems.add(new Product("Name", "https://image.flaticon.com/icons/png/512/263/263142.png", 0, "title"));
    }

    public static CartSingleton CartSingleton() {
        if (cart == null) {
            cart = new CartSingleton();
        }
        return cart;
    }

    public int getNumItems() {
        if (cart == null) return 0;
        int count = 0;
        for (int i = 0; i < this.cartItems.size(); i++) {
            count += this.cartItems.get(i).getQuantity();
        }
        return count;
    }
}
