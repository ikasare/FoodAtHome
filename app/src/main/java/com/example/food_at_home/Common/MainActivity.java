package com.example.food_at_home.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.food_at_home.RandomRecipes.RandomRecipeAdapter;
import com.example.food_at_home.RandomRecipes.RandomRecipeResponseListener;
import com.example.food_at_home.Parse.Meal;
import com.example.food_at_home.RandomRecipes.RandomRecipeResponse;
import com.example.food_at_home.RandomRecipes.Recipe;
import com.example.food_at_home.R;
import com.example.food_at_home.RecipeDetails.RecipeDetailsActivity;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private ProgressDialog dialog;
    private RequestManager manager;
    private Spinner spinner;
    private List<String> tags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Getting Meals...");

        manager = new RequestManager(this);

        Button btnLogout = findViewById(R.id.btnLogout);
        SearchView searchView = findViewById(R.id.svSearch);

        spinner = findViewById(R.id.spnSpinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.tags, R.layout.spinner_text);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerListener);


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
            RecyclerView recyclerView = findViewById(R.id.rvRandom);
            // set recycler view layout
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            RandomRecipeAdapter adapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
            // set adapter onto rv
            recyclerView.setAdapter(adapter);
            // populate meal database with new meals fetched
            saveMeals(response.recipes);
        }

        @Override
        public void error(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void saveMeals(ArrayList<Recipe> recipes) {
        for (Recipe i : recipes) {
            String mealID = String.valueOf(i.id);
            String mealName = i.title;
            Meal meal = new Meal();
            meal.setMealID(mealID);
            meal.setMealName(mealName);
            if (meal.has(mealID)) {
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

    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tags.clear();
            tags.add(parent.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}
