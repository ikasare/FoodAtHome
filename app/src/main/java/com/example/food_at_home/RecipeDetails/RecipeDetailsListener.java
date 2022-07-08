package com.example.food_at_home.RecipeDetails;

public interface RecipeDetailsListener {

    void fetch(RecipeDetailsResponse response, String message);

    void error(String message);
}
