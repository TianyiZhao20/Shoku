package com.example.shoku;

/*
Defines the recycler view for search activity
with add, remove buttons and add to local cart
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchRecViewAdapter extends RecyclerView.Adapter<SearchRecViewAdapter.ViewHolder> {
    private static final String TAG = "SearchRecViewAdapter";

    private ArrayList<Product> products;
    private Context mContext;
    private int lastClicked;
    private int lastOperation;

    public SearchRecViewAdapter(Context mContext) {
        this.mContext = mContext;
        lastClicked=-1;
        lastOperation=-1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_search,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.nameProduct.setText(products.get(position).getName());
        holder.priceProduct.setText(String.format("%.2f", products.get(position).getPrice()));
//        Glide.with(mContext)
//                .asBitmap()
//                .load(products.get(position).getImgUrl())
//                .into(holder.imageProduct);

        new BitmapWorkerTask(holder.imageProduct).execute(products.get(position).getImgUrl());

        //if (lastOperation == 0 && lastClicked==position){
            //products.get(position).plusQuantity();
            holder.quantityProduct.setText(Integer.toString(products.get(holder.getAdapterPosition()).getQuantity()));
            //lastClicked=-1;
            //lastOperation=-1;
        //}
        //if (lastOperation == 1 && lastClicked==position){
            //products.get(position).minusQuantity();
        //    holder.quantityProduct.setText(Integer.toString(products.get(holder.getAdapterPosition()).getQuantity()));
        //    lastClicked=-1;
        //    lastOperation=-1;
        //}

        //when click on add btn, quantity + 1
        //when click on minus btn, quantity -1, unless already 0
        //when click on add btn and quantity > 0, add to cart; clear quantity

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lastClicked=position;
                lastOperation=0;
                products.get(position).plusQuantity();
                //holder.quantityProduct.setText(Integer.toString(products.get(position).getQuantity()));
                notifyItemChanged(position);
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lastClicked=position;
                lastOperation=1;
                products.get(position).minusQuantity();
                //holder.quantityProduct.setText(Integer.toString(products.get(position).getQuantity()));
                notifyItemChanged(position);
            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (products.get(position).getQuantity() > 0) {
                    Toast.makeText(mContext, Integer.toString(products.get(position).getQuantity()) +
                            " " + products.get(position).getName() + " Added", Toast.LENGTH_SHORT).show();

                    CartSingleton localCart = CartSingleton.CartSingleton();

                    //if this product isn't in the cart, add it
                    //otherwise, increase the quantity
                    boolean exists = false;
                    for (int i = 0; i < localCart.cartItems.size(); i++) {
                        if (localCart.cartItems.get(i).getProductId() == products.get(position).getProductId()) {
                            localCart.cartItems.get(i).setQuantity(localCart.cartItems.get(i).getQuantity()
                            + products.get(position).getQuantity());
                            notifyItemChanged(position);
                            exists = true;
                        }
                    }
                    if (!exists) {
                        Product temp = new Product(products.get(position).getName(),
                                products.get(position).getImgUrl(), products.get(position).getPrice(),
                                products.get(position).getProductId());
                        temp.setQuantity(products.get(position).getQuantity());
                        localCart.cartItems.add(temp);
                        notifyItemChanged(position);
                    }

                    products.get(position).setQuantity(0);
                    //holder.quantityProduct.setText(Integer.toString(products.get(position).getQuantity()));
                    notifyItemChanged(position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardParent;
        private ImageView imageProduct;
        private TextView nameProduct;
        private TextView quantityProduct;
        private ImageButton btnPlus;
        private ImageButton btnMinus;
        private Button btnAdd;
        private TextView priceProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardParent = itemView.findViewById(R.id.searchCardParent);
            imageProduct = itemView.findViewById(R.id.searchImg);
            nameProduct = itemView.findViewById(R.id.searchName);
            quantityProduct = itemView.findViewById(R.id.quantityNum);
            btnAdd = itemView.findViewById(R.id.btnAddCart);
            btnPlus = itemView.findViewById(R.id.quantityPlus);
            btnMinus = itemView.findViewById(R.id.quantityMinus);
            priceProduct = itemView.findViewById(R.id.searchPrice);
        }
    }
}
