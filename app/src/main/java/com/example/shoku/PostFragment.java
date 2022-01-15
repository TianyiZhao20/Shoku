package com.example.shoku;

/*
Use this instead of PostActivity
Displayed as a fragment for one of the bottom navigators
TODO: For MPV2, fill this out
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class PostFragment extends Fragment {

    private EditText postNameEditText;
    private ImageView postImg;
    private Button postImgBtn;
    private Button postSubmit;
    private TextView postSubmitWarning;
    private PostIngreAdapter postIngreAdapter;
    private RecyclerView postIngreRecView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText postAddIngreName;
    private EditText postAddIngreQt;
    private EditText postAddIngreUnit;
    private Button postAddIngreBtn;
    private EditText postInstrText;
    private Button postTagVegan;
    private Button postTagSpicy;
    private Button postTagMild;
    private Button postTagFast;
    private EditText postCookingTimeEditText;
    private EditText postServingSizeEditText;
    private ArrayList<Ingredient> ingredients;
    private boolean[] tags = {false, false, false, false};
    private Bitmap imageSelected;


    private Recipe recipe;



    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE=1001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View view = inflater.inflate(R.layout.post_fragment, container, false);
        //name
        postNameEditText = view.findViewById(R.id.postRecipeNameEditText);
        //image
        postImg = view.findViewById(R.id.postImgView);
        postImgBtn = view.findViewById(R.id.postChooseImgBtn);
        postImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                        //permission not granted. request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show runtime popup for permissions
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //version lower than marshmellow
                    pickImageFromGallery();
                }
            }
        });
        //ingredient list already put in
        postIngreRecView = (RecyclerView) view.findViewById(R.id.postIngreRecView);
        layoutManager = new LinearLayoutManager(getContext());
        postIngreRecView.setLayoutManager(layoutManager);
        postIngreAdapter = new PostIngreAdapter(getContext());
        postIngreRecView.setAdapter(postIngreAdapter);
        postIngreAdapter.setIngredients(ingredients);
        //add another ingredient
        postAddIngreName = view.findViewById(R.id.postAddIngreName);
        postAddIngreQt = view.findViewById(R.id.postAddIngreQt);
        postAddIngreUnit = view.findViewById(R.id.postAddIngreUnit);
        postAddIngreBtn = view.findViewById(R.id.postAddIngreBtn);
        postAddIngreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingreName = postAddIngreName.getText().toString();
                String ingreQt = postAddIngreQt.getText().toString();
                String ingreUnit = postAddIngreUnit.getText().toString();
                if (ingreName != null && ingreName != "" && ingreQt != null && ingreQt != "" && ingreUnit != null && ingreUnit != "") {
                    //add ingredient to ingredients list and update
                    //if ingredients list is null, create one
                    if (ingredients == null) {
                        ingredients = new ArrayList<>();
                    }
                    ingredients.add(new Ingredient(ingreName, ingreQt, ingreUnit));
                    postIngreAdapter.setIngredients(ingredients);
                    //empty fields
                    postAddIngreName.setText("");
                    postAddIngreQt.setText("");
                    postAddIngreUnit.setText("");
                } else {
                    Toast.makeText(getContext(), "Fill in all fields first", Toast.LENGTH_LONG).show();
                }
            }
        });

        //instruction
        postInstrText = view.findViewById(R.id.postInstrEditText);
        //tags
        postTagVegan = view.findViewById(R.id.veganBtn);
        postTagSpicy = view.findViewById(R.id.spicyBtn);
        postTagMild = view.findViewById(R.id.mildBtn);
        postTagFast = view.findViewById(R.id.fastBtn);
        postTagVegan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (tags[0]) {
                    //already selected, then deselect
                    tags[0] = false;
                    postTagVegan.setBackgroundColor(postTagVegan.getContext().getResources().getColor(R.color.colorAccent));
                    postTagVegan.setTextColor(postTagVegan.getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    //not selected, now select
                    tags[0] = true;
                    postTagVegan.setBackgroundColor(postTagVegan.getContext().getResources().getColor(R.color.colorPrimaryDark));
                    postTagVegan.setTextColor(postTagVegan.getContext().getResources().getColor(R.color.colorAccent));
                }
            }

        });
        postTagSpicy.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                if (tags[1]) {
                    //already selected, then deselect
                    tags[1] = false;
                    postTagSpicy.setBackgroundColor(postTagSpicy.getContext().getResources().getColor(R.color.colorAccent));
                    postTagSpicy.setTextColor(postTagSpicy.getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    //not selected, now select
                    tags[1] = true;
                    postTagSpicy.setBackgroundColor(postTagSpicy.getContext().getResources().getColor(R.color.colorPrimaryDark));
                    postTagSpicy.setTextColor(postTagSpicy.getContext().getResources().getColor(R.color.colorAccent));
                }
            }
        });
        postTagMild.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                if (tags[2]) {
                    //already selected, then deselect
                    tags[2] = false;
                    postTagMild.setBackgroundColor(postTagMild.getContext().getResources().getColor(R.color.colorAccent));
                    postTagMild.setTextColor(postTagMild.getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    //not selected, now select
                    tags[2] = true;
                    postTagMild.setBackgroundColor(postTagMild.getContext().getResources().getColor(R.color.colorPrimaryDark));
                    postTagMild.setTextColor(postTagMild.getContext().getResources().getColor(R.color.colorAccent));
                }
            }
        });
        postTagFast.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                if (tags[3]) {
                    //already selected, then deselect
                    tags[3] = false;
                    postTagFast.setBackgroundColor(postTagFast.getContext().getResources().getColor(R.color.colorAccent));
                    postTagFast.setTextColor(postTagFast.getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    //not selected, now select
                    tags[3] = true;
                    postTagFast.setBackgroundColor(postTagFast.getContext().getResources().getColor(R.color.colorPrimaryDark));
                    postTagFast.setTextColor(postTagFast.getContext().getResources().getColor(R.color.colorAccent));
                }
            }
        });

        //cooking time
        postCookingTimeEditText = view.findViewById(R.id.postCookTimeEdit);
        //serving size
        postServingSizeEditText = view.findViewById(R.id.postServingSizeEdit);



        postSubmit = view.findViewById(R.id.submitButton);
        postSubmitWarning = view.findViewById(R.id.postWarning);
        postSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //check that every field has been filled, otherwise display message
                /*
                1. name edit text filled
                2. image selected
                3. at least one ingredient
                4. instruction edit
                5. select at least one tag
                6. cook time edit text
                7. serving size edit text
                */
                String recipeName;
                Bitmap recipeImage;
                ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
                String recipeInstructions;
                ArrayList<Integer> recipeTags;
                String recipeCookTime;
                String recipeServingSize;
                //recipe
                if (postNameEditText.getText().toString() == null && postNameEditText.getText().toString() == "") {
                    postSubmitWarning.setVisibility(View.VISIBLE);
                    return;
                } else {
                    recipeName = postNameEditText.getText().toString();
                }
                //image
                if (imageSelected == null) {
                    postSubmitWarning.setVisibility(View.VISIBLE);
                    return;
                } else {
                    recipeImage = imageSelected;
                }
                //ingredients
                if (ingredients == null || ingredients.size() == 0) {
                    postSubmitWarning.setVisibility(View.VISIBLE);
                    return;
                } else  {
                    recipeIngredients = ingredients;
                }
                //instructions
                if (postInstrText.getText().toString() == null && postInstrText.getText().toString() == "") {
                    postSubmitWarning.setVisibility(View.VISIBLE);
                    return;
                } else {
                    recipeInstructions = (postInstrText.getText().toString());
                }
                //tags
                if (tags[0] || tags [1] || tags[2] || tags[3]) {
                    ArrayList<Integer> tagsList = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        if (tags[i]) {
                            tagsList.add(i);
                        }
                    }
                    recipeTags = (tagsList);
                } else {
                    postSubmitWarning.setVisibility(View.VISIBLE);
                    return;
                }
                //cooktime
                if (postCookingTimeEditText.getText().toString() == null || postCookingTimeEditText.getText().toString() == "") {
                    postSubmitWarning.setVisibility(View.VISIBLE);
                    return;
                } else {
                    recipeCookTime = (postCookingTimeEditText.getText().toString());
                }
                //serving size
                if (postServingSizeEditText.getText().toString() == null || postServingSizeEditText.getText().toString() == "") {
                    postSubmitWarning.setVisibility(View.VISIBLE);
                    return;
                } else {
                    recipeServingSize = (postServingSizeEditText.getText().toString());
                }

                recipe = new Recipe(recipeName, recipeImage, recipeIngredients, recipeInstructions, recipeTags, recipeCookTime, recipeServingSize);
//                RecipeListSingleton recipeListSingleton = RecipeListSingleton.RecipeListSingleton();
//                if (recipeListSingleton.recipes == null) {
//                    recipeListSingleton.recipes = new ArrayList<>();
//                }
//                recipeListSingleton.recipes.add(recipe);
//                new Bridge().uploadPost(recipe,getContext().getCacheDir());
                new uploadTask(getContext()).execute(recipe);
                //display submitted, empty all fields
                Toast.makeText(getContext(), "Recipe Submitted!", Toast.LENGTH_LONG).show();
                postNameEditText.setText("");
                postImg.setImageResource(R.mipmap.ic_launcher);
                ingredients = new ArrayList<>();
                postIngreAdapter.setIngredients(ingredients);
                postAddIngreName.setText("");
                postAddIngreQt.setText("");
                postAddIngreUnit.setText("");
                postInstrText.setText("");
                for (int i = 0; i < 4; i++) {
                    tags[i] = false;
                }
                postTagVegan.setBackgroundColor(postTagVegan.getContext().getResources().getColor(R.color.colorAccent));
                postTagSpicy.setBackgroundColor(postTagSpicy.getContext().getResources().getColor(R.color.colorAccent));
                postTagMild.setBackgroundColor(postTagMild.getContext().getResources().getColor(R.color.colorAccent));
                postTagFast.setBackgroundColor(postTagFast.getContext().getResources().getColor(R.color.colorAccent));
                postTagVegan.setTextColor(postTagVegan.getContext().getResources().getColor(R.color.colorPrimaryDark));
                postTagSpicy.setTextColor(postTagSpicy.getContext().getResources().getColor(R.color.colorPrimaryDark));
                postTagMild.setTextColor(postTagMild.getContext().getResources().getColor(R.color.colorPrimaryDark));
                postTagFast.setTextColor(postTagFast.getContext().getResources().getColor(R.color.colorPrimaryDark));
                postCookingTimeEditText.setText("");
                postServingSizeEditText.setText("");
            }
        });


        return view;
    }

    //import image
    private void pickImageFromGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image*/");
//        startActivityForResult(intent, IMAGE_PICK_CODE);
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");


        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, IMAGE_PICK_CODE);
    }

    //after picking image from gallery...
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Uri imageUri = data.getData();
            try {
                imageSelected = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                postImg.setImageBitmap(imageSelected);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //get permission for picking image
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
