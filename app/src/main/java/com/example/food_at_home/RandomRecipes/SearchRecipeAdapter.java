package com.example.food_at_home.RandomRecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_at_home.Common.RecipeClickListener;
import com.example.food_at_home.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder> {

    Context mContext;
    List<Result> resultList;
    RecipeClickListener listener;

    public SearchRecipeAdapter(Context mContext, List<Result> resultList, RecipeClickListener listener) {
        this.mContext = mContext;
        this.resultList = resultList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = resultList.get(position).title;
        holder.tvTitle.setText(title);
        holder.tvTitle.setSelected(true);
        holder.tvLikes.setText("20 likes");
        holder.tvServings.setText("2 servings");
        holder.tvTime.setText("45 minutes");
        String imgUri = resultList.get(position).image;
        Picasso.get().load(imgUri).into(holder.ivFoodPhoto);

        holder.cvRandomRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClickListener(String.valueOf(resultList.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
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
            cvRandomRecipe = itemView.findViewById(R.id.cvRandomRecipe);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivFoodPhoto = itemView.findViewById(R.id.ivFoodPhoto);
            tvServings = itemView.findViewById(R.id.tvServings);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
