package com.example.food_at_home.Listeners;

import com.example.food_at_home.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {

    void fetch(RecipeDetailsResponse response, String message);

    void error(String message);
}
