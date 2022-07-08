package com.example.food_at_home.RecipeDetails;

import com.example.food_at_home.RecipeDetails.ExtendedIngredient;

import java.util.ArrayList;

public class RecipeDetailsResponse {
    public int id;
    public String title;
    public String image;
    public String sourceName;
    public ArrayList<ExtendedIngredient> extendedIngredients;
    public String summary;

}
