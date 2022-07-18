package com.example.food_at_home.RandomRecipes;

public interface SearchRecipeListener {
    void fetch(SearchRecipeResponse response, String message);

    void error(String message);
}
