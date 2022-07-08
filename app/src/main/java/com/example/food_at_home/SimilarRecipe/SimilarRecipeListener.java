package com.example.food_at_home.SimilarRecipe;

import java.util.List;

public interface SimilarRecipeListener {

    void fetch(List<SimilarRecipeResponse> response, String message);

    void error(String message);
}
