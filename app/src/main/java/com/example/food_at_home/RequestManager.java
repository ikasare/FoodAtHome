package com.example.food_at_home;

import android.content.Context;

import com.example.food_at_home.Listeners.RandomRecipeResponseListener;
import com.example.food_at_home.Models.RandomRecipeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    Context mContext;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context mContext) {
        this.mContext = mContext;
    }

    // method to get random recipes for home feed
    public void getRandomRecipes(RandomRecipeResponseListener listener){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeResponse> call = callRandomRecipes.callRandomRecipe(mContext.getString(R.string.api_key),"30" );
        // enqueue to make call asynchronously
        call.enqueue(new Callback<RandomRecipeResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeResponse> call, Response<RandomRecipeResponse> response) {
                if (!response.isSuccessful()){
                    listener.error(response.message());
                    return;
                }
                listener.fetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeResponse> call, Throwable t) {
                listener.error(t.getMessage());

            }
        });
    }

    private interface CallRandomRecipes{

        // this is a get call
        @GET("recipes/random")
        Call<RandomRecipeResponse> callRandomRecipe(@Query("apiKey") String apiKey, @Query("number") String number);
    }
}
