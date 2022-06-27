package com.example.food_at_home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_at_home.Listeners.RecipeClickListener;
import com.example.food_at_home.Models.Recipe;
import com.example.food_at_home.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeAdapter.ViewHolder> {
    Context mContext;
    List<Recipe> recipeList;
    RecipeClickListener listener;

    public RandomRecipeAdapter(Context mContext, List<Recipe> recipeList, RecipeClickListener listener) {
        this.mContext = mContext;
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the view
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get recipe title and set it as the title in the view
        String title = recipeList.get(position).title;
        holder.tvTitle.setText(title);
        holder.tvTitle.setSelected(true);
        // get total likes and set it on the appropriate view
        int likes = recipeList.get(position).aggregateLikes;
        holder.tvLikes.setText(likes + " likes");
        // get number of servings and set it on appropriate view
        int servings = recipeList.get(position).servings;
        holder.tvServings.setText(servings + " servings");
        // get time for meal and set it
        int time = recipeList.get(position).readyInMinutes;
        holder.tvTime.setText(time + " minutes");
        // get the image
        String imgUri = recipeList.get(position).image;
        Picasso.get().load(imgUri).into(holder.ivFoodPhoto);

        holder.cvRandomRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClickListener(String.valueOf(recipeList.get(holder.getAdapterPosition()).id));
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvRandomRecipe;
        TextView tvTitle;
        ImageView ivFoodPhoto;
        TextView tvServings;
        TextView tvLikes;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // assign ids to widgets
            cvRandomRecipe = itemView.findViewById(R.id.cvRandomRecipe);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivFoodPhoto = itemView.findViewById(R.id.ivFoodPhoto);
            tvServings = itemView.findViewById(R.id.tvServings);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
