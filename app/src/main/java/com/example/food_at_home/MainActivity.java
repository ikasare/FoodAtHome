package com.example.food_at_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.food_at_home.Adapters.RandomRecipeAdapter;
import com.example.food_at_home.Listeners.RandomRecipeResponseListener;
import com.example.food_at_home.Listeners.RecipeClickListener;
import com.example.food_at_home.Models.RandomRecipeResponse;
import com.example.food_at_home.Models.Recipe;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter adapter;
    RecyclerView recyclerView;
    Button btnLogout;
    SearchView searchView;
    List<String> tags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Getting Meals...");

        manager = new RequestManager(this);
        manager.getRandomRecipes(randomRecipeResponseListener, tags);
        dialog.show();

        btnLogout = findViewById(R.id.btnLogout);
        searchView = findViewById(R.id.svSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void fetch(RandomRecipeResponse response, String message) {
            // dismiss dialog
            dialog.dismiss();
            // initialize recycler view
            recyclerView = findViewById(R.id.rvRandom);
            // set recycler view layout
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
            // set adapter onto rv
            recyclerView.setAdapter(adapter);
            // populate meal database with new meals fetched
            saveMeals(response.recipes);
        }

        @Override
        public void error(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void saveMeals(ArrayList<Recipe> recipes) {
        for (Recipe i : recipes) {
            String mealName = i.title;
            Meal meal = new Meal();
            meal.setMealName(mealName);
            if (meal.has(mealName)) {
                continue;
            } else {
                meal.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }


                });
            }
        }
    }

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClickListener(String id) {
            Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    };

}