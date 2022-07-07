package com.example.food_at_home.RecipeDetails;

import java.util.List;

public interface InstructionsListener {

    void fetch(List<InstructionsResponse> response, String message);

    void error(String message);
}
