package com.example.food_at_home.Common;

import android.content.Context;

import com.example.food_at_home.RecipeDetails.InstructionsListener;
import com.example.food_at_home.RandomRecipes.RandomRecipeResponseListener;
import com.example.food_at_home.RecipeDetails.RecipeDetailsListener;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeListener;
import com.example.food_at_home.RecipeDetails.InstructionsResponse;
import com.example.food_at_home.RandomRecipes.RandomRecipeResponse;
import com.example.food_at_home.RecipeDetails.RecipeDetailsResponse;
import com.example.food_at_home.SimilarRecipe.SimilarRecipeResponse;
import com.example.food_at_home.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {

    Context mContext;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context mContext) {
        this.mContext = mContext;
    }

    // method to get random recipes for home feed
    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeResponse> call = callRandomRecipes.callRandomRecipe(mContext.getString(R.string.api_key),"15", tags);
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

    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, mContext.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.error(response.message());
                    return;
                }
                listener.fetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.error(t.getMessage());
            }
        });
    }

    public void getSimilarRecipe(SimilarRecipeListener listener, int id){
        CallSimilarRecipe callSimilarRecipe = retrofit.create(CallSimilarRecipe.class);
        Call<List<SimilarRecipeResponse>> call = callSimilarRecipe.callSimilarRecipe(id, "5", mContext.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
                if (!response.isSuccessful()){
                    listener.error(response.message());
                    return;
                }
                listener.fetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeResponse>> call, Throwable t) {
                listener.error(t.getMessage());
            }
        });
    }

    public void getInstructions(InstructionsListener listener, int id){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id, mContext.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {
                if (!response.isSuccessful()){
                    listener.error((response.message()));
                    return;
                }
                listener.fetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable t) {
                listener.error(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes{

        // this is a get call
        @GET("recipes/random")
        Call<RandomRecipeResponse> callRandomRecipe(@Query("apiKey") String apiKey, @Query("number") String number, @Query("tags")List<String> tags);
    }

    private interface CallRecipeDetails{

        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(@Path("id") int id, @Query("apiKey") String apiKey);
    }

    private interface CallSimilarRecipe{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipeResponse>> callSimilarRecipe(@Path("id") int id, @Query("number") String number, @Query("apiKey") String apiKey);
    }

    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(@Path("id") int id, @Query("apiKey") String apiKey);
    }
}
