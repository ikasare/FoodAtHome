package com.example.food_at_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_at_home.Adapters.IngredientsAdapter;
import com.example.food_at_home.Listeners.RecipeDetailsListener;
import com.example.food_at_home.Models.RecipeDetailsResponse;
import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView tvMealName;
    TextView tvMealSource;
    ImageView ivMealImage;
    TextView tvMealSummary;
    RecyclerView rvIngredients;
    RecyclerView rvSimilarMeals;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        initializeViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(listener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Getting Details...");
        dialog.show();


    }

    private void initializeViews() {
        tvMealName = findViewById(R.id.tvMealName);
        tvMealSource = findViewById(R.id.tvMealSource);
        ivMealImage = findViewById(R.id.ivMealImage);
        tvMealSummary = findViewById(R.id.tvMealSummary);
        rvIngredients = findViewById(R.id.rvIngredients);
        rvSimilarMeals = findViewById(R.id.rvSimilarMeals);
    }

    private final RecipeDetailsListener listener = new RecipeDetailsListener() {
        @Override
        public void fetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            String mealName = response.title;
            tvMealName.setText(mealName);
            String sourceName = response.sourceName;
            tvMealSource.setText(sourceName);
            String summary = response.summary;
            tvMealSummary.setText(summary);
            Picasso.get().load(response.image).into(ivMealImage);

            rvIngredients.setHasFixedSize(true);
            rvIngredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            adapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            rvIngredients.setAdapter(adapter);


        }

        @Override
        public void error(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}