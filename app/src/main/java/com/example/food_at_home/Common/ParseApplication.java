package com.example.food_at_home.Common;

import android.app.Application;

import com.example.food_at_home.Parse.Bookmarks;
import com.example.food_at_home.Parse.Meal;
import com.example.food_at_home.Parse.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // register parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Meal.class);
        ParseObject.registerSubclass(Bookmarks.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("voXwwiXmpsbP9QynlWa2vWdARGcSYJtl8NG9HotE")
                .clientKey("gdbIkudoUdyGNJ3oRj6F32AaRfj3Y4OiVsWoS4zW")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
