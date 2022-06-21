package com.example.food_at_home;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("voXwwiXmpsbP9QynlWa2vWdARGcSYJtl8NG9HotE")
                .clientKey("gdbIkudoUdyGNJ3oRj6F32AaRfj3Y4OiVsWoS4zW")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
