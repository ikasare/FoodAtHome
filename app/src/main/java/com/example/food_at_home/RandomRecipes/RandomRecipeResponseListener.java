package com.example.food_at_home.RandomRecipes;

// listener of api response
public interface RandomRecipeResponseListener {

    void fetch(RandomRecipeResponse response, String message);

    void error(String message);
}
