package com.example.food_at_home.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.food_at_home.RandomRecipes.RandomRecipeAdapter;
import com.example.food_at_home.RandomRecipes.RandomRecipeResponseListener;
import com.example.food_at_home.Parse.Meal;
import com.example.food_at_home.RandomRecipes.RandomRecipeResponse;
import com.example.food_at_home.RandomRecipes.Recipe;
import com.example.food_at_home.R;
import com.example.food_at_home.RandomRecipes.SearchRecipeAdapter;
import com.example.food_at_home.RandomRecipes.SearchRecipeListener;
import com.example.food_at_home.RandomRecipes.SearchRecipeResponse;
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
    private List<Recipe> recipeList = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manager.getSearchRecipes(searchRecipeListener, query);
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
            recipeList.clear();
            recipeList.addAll(response.recipes);
            // initialize recycler view
            RecyclerView recyclerView = findViewById(R.id.rvRandom);
            // set recycler view layout
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            RandomRecipeAdapter adapter = new RandomRecipeAdapter(MainActivity.this, recipeList, recipeClickListener);
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

    private final SearchRecipeListener searchRecipeListener = new SearchRecipeListener() {
        @Override
        public void fetch(SearchRecipeResponse response, String message) {
            dialog.dismiss();
            RecyclerView recyclerView = findViewById(R.id.rvRandom);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            Log.i("Search Results", response.results.toString());
            SearchRecipeAdapter adapter = new SearchRecipeAdapter(MainActivity.this, response.results, recipeClickListener);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void error(String message) {

        }
    };

    private void saveMeals(ArrayList<Recipe> recipes) {
        for (Recipe i : recipes) {
            String mealID = String.valueOf(i.id);
            String mealName = i.title;
            Meal meal = new Meal();
            meal.setMealID(mealID);
            meal.setMealName(mealName);
            if (meal.containsKey(mealID)) {
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
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (View) findViewById(R.id.ivFoodPhoto), "food");
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
