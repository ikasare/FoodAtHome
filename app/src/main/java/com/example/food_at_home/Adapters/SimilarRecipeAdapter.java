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
import com.example.food_at_home.Models.SimilarRecipeResponse;
import com.example.food_at_home.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<SimilarRecipeAdapter.ViewHolder> {

    Context mContext;
    List<SimilarRecipeResponse> list;
    RecipeClickListener listener;

    public SimilarRecipeAdapter(Context mContext, List<SimilarRecipeResponse> list, RecipeClickListener listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.similar_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = list.get(position).title;
        holder.tvSimilarTitle.setText(title);
        holder.tvSimilarTitle.setSelected(true);
        String servings = String.valueOf(list.get(position).servings);
        holder.tvSimilarServing.setText(servings + " persons");
        Picasso.get().load("https://spoonacular.com/recipeImages/" + list.get(position).id + "-556x370." + list.get(position).imageType).into(holder.ivSimilarImage);

        holder.cvSimilarRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClickListener(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cvSimilarRecipe;
        TextView tvSimilarTitle;
        ImageView ivSimilarImage;
        TextView tvSimilarServing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvSimilarRecipe = itemView.findViewById(R.id.cvSimilarRecipe);
            tvSimilarTitle = itemView.findViewById(R.id.tvSimilarTitle);
            ivSimilarImage = itemView.findViewById(R.id.ivSimilarImage);
            tvSimilarServing = itemView.findViewById(R.id.tvSimilarServing);
        }
    }
}
