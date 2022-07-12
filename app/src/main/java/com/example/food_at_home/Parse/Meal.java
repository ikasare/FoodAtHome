package com.example.food_at_home.Parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Meal")

public class Meal  extends ParseObject {

    private static final String MEAL_NAME = "mealName" ;
    public static final String MEAL_ID = "mealID";
    //  default constructor
    public Meal() {
        super();
    }

    public void setMealID(String mealID){
        put(MEAL_ID, mealID);
    }

    public String getMealID(){
        return getString(MEAL_ID);
    }

    public void setMealName(String mealName){
        put(MEAL_NAME, mealName);
    }

    public String getMealName(){
        return getString(MEAL_NAME);
    }
}
