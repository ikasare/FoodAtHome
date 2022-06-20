package com.example.food_at_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.food_at_home.Adapters.RandomRecipeAdapter;
import com.example.food_at_home.Listeners.RandomRecipeResponseListener;
import com.example.food_at_home.Models.RandomRecipeResponse;

public class MainActivity extends AppCompatActivity {


    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Getting Meals...");

        manager = new RequestManager(this);
        manager.getRandomRecipes(randomRecipeResponseListener);
        dialog.show();

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
            adapter = new RandomRecipeAdapter(MainActivity.this, response.recipes);
            // set adapter onto rv
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void error(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}