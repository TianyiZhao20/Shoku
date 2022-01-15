package com.example.shoku;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuBaseActivity extends AppCompatActivity {
//    private static final CartSingleton cart = CartSingleton.CartSingleton();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_number_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartNumMenu:
                //go to cart fragment
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("from", 1);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        CartSingleton cartSingleton = CartSingleton.CartSingleton();
        //set title to number of items in cart
        String str = "Cart (" + Integer.toString(cartSingleton.getNumItems()) + ")";
        menu.findItem(R.id.cartNumMenu).setTitle(str);
        return super.onPrepareOptionsMenu(menu);
    }
}
