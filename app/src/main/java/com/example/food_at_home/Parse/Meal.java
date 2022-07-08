package com.example.food_at_home.Parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Meal")

public class Meal  extends ParseObject {

    //  default constructor
    public Meal() {
        super();
    }

    public static final String MEAL_NAME = "mealName";

    public void setMealName(String mealName){
        put(MEAL_NAME, mealName);
    }

    public String getMealName(){
        return getString(MEAL_NAME);
    }
}
