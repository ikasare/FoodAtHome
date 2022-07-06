package com.example.food_at_home.Listeners;

import com.example.food_at_home.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {

    void fetch(List<InstructionsResponse> response, String message);

    void error(String message);
}
