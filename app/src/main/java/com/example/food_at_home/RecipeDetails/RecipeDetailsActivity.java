package com.example.food_at_home.RecipeDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_at_home.R;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeAdapter;
import com.example.food_at_home.Common.RequestManager;
import com.example.food_at_home.Common.RecipeClickListener;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeListener;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView tvMealName;
    TextView tvMealSource;
    ImageView ivMealImage;
    TextView tvMealSummary;
    RecyclerView rvIngredients;
    RecyclerView rvSimilarMeals;
    RecyclerView rvInstructions;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter adapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        initializeViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(listener, id);
        manager.getSimilarRecipe(similarRecipeListener, id);
        manager.getInstructions(instructionsListener, id);
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
        rvInstructions = findViewById(R.id.rvInstructions);
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
            tvMealSummary.setText(Jsoup.parse(summary).text());
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

    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void fetch(List<SimilarRecipeResponse> response, String message) {
            rvSimilarMeals.setHasFixedSize(true);
            rvSimilarMeals.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
            rvSimilarMeals.setAdapter(similarRecipeAdapter);

        }

        @Override
        public void error(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClickListener(String id) {
            Intent intent = new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);

        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void fetch(List<InstructionsResponse> response, String message) {
            rvInstructions.setHasFixedSize(true);
            rvInstructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
            rvInstructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void error(String message) {
        }
    };
}