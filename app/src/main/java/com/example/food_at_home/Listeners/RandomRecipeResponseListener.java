package com.example.food_at_home.Listeners;

import com.example.food_at_home.Models.RandomRecipeResponse;

// listener of api response
public interface RandomRecipeResponseListener {

    void fetch(RandomRecipeResponse response, String message);

    void error(String message);
}
