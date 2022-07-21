package com.example.food_at_home.RecipeDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_at_home.Comments.CommentsActivity;
import com.example.food_at_home.Comments.CommentsFeed;
import com.example.food_at_home.Parse.Bookmarks;
import com.example.food_at_home.R;
import com.example.food_at_home.RandomRecipes.Recipe;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeAdapter;
import com.example.food_at_home.Common.RequestManager;
import com.example.food_at_home.Common.RecipeClickListener;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeListener;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeResponse;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView tvMealName;
    TextView tvMealSource;
    ImageView ivMealImage;
    TextView tvMealSummary;
    ImageButton ibReview;
    ImageButton ibBookmark;
    RecyclerView rvIngredients;
    RecyclerView rvSimilarMeals;
    RecyclerView rvInstructions;
    RequestManager manager;
    ProgressDialog dialog;
    ImageButton btnSeeReviews;
    IngredientsAdapter adapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;
    boolean isBookmarked = false;
    List<RecipeDetailsResponse> list = new ArrayList<>();


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
        checkBookmarked();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Getting Details...");
        dialog.show();

        ibReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailsActivity.this, CommentsActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        btnSeeReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailsActivity.this, CommentsFeed.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        ibBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBookmarked){
                    Drawable newImage1 = RecipeDetailsActivity.this.getDrawable(android.R.drawable.btn_star_big_on);
                    ibBookmark.setImageDrawable(newImage1);
                    saveBookmark(list);
                }else{
                    Drawable newImage2 = RecipeDetailsActivity.this.getDrawable(android.R.drawable.btn_star_big_off);
                    ibBookmark.setImageDrawable(newImage2);
                }

            }
        });

    }

    private void saveBookmark(List<RecipeDetailsResponse> list) {
        RecipeDetailsResponse item = list.get(0);
        Bookmarks bookmarks = new Bookmarks();
        bookmarks.setBookmarkTitle(item.title);
        bookmarks.setBookmarkId(String.valueOf(id));
        bookmarks.setUser(ParseUser.getCurrentUser());
        bookmarks.setBookmarkPhoto(item.image);
        bookmarks.saveInBackground();
    }

    private void checkBookmarked() {
        ParseQuery<Bookmarks> query = ParseQuery.getQuery(Bookmarks.class);
        query.include(Bookmarks.BOOKMARK_TITLE);
        query.include(Bookmarks.BOOKMARK_ID);
        query.include(Bookmarks.BOOKMARK_USER);
        query.whereEqualTo(Bookmarks.BOOKMARK_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Bookmarks>() {
            @Override
            public void done(List<Bookmarks> bookmarksList, ParseException e) {
                if (e != null){
                    Toast.makeText(RecipeDetailsActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
                    return;

                }
                for (Bookmarks bookmark : bookmarksList){
                    if (Objects.equals(bookmark.getBookmarkId(), String.valueOf(id))){
                        isBookmarked = true;
                        break;
                    }
                }
                if(isBookmarked){
                    Drawable newImage = RecipeDetailsActivity.this.getDrawable(android.R.drawable.btn_star_big_on);
                    ibBookmark.setImageDrawable(newImage);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private void initializeViews() {
        tvMealName = findViewById(R.id.tvMealName);
        tvMealSource = findViewById(R.id.tvMealSource);
        ivMealImage = findViewById(R.id.ivMealImage);
        tvMealSummary = findViewById(R.id.tvMealSummary);
        rvIngredients = findViewById(R.id.rvIngredients);
        rvSimilarMeals = findViewById(R.id.rvSimilarMeals);
        rvInstructions = findViewById(R.id.rvInstructions);
        ibReview = findViewById(R.id.ibReview);
        btnSeeReviews = findViewById(R.id.btnSeeReviews);
        ibBookmark = findViewById(R.id.ibBookmark);
    }

    private final RecipeDetailsListener listener = new RecipeDetailsListener() {
        @Override
        public void fetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            list.add(response);
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