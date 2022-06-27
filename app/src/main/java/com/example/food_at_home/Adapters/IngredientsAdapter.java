package com.example.food_at_home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_at_home.Models.ExtendedIngredient;
import com.example.food_at_home.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    Context mContext;

    public IngredientsAdapter(Context mContext, List<ExtendedIngredient> list) {
        this.mContext = mContext;
        this.list = list;
    }

    List<ExtendedIngredient> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.meal_ingredients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = list.get(position).name;
        holder.tvIngredientsName.setText(name);
        String amount = list.get(position).original;
        holder.tvIngredientsAmount.setText(amount);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+ list.get(position).image).into(holder.ivIngredients);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIngredientsAmount;
        TextView tvIngredientsName;
        ImageView ivIngredients;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientsAmount = itemView.findViewById(R.id.tvIngredientsAmount);
            tvIngredientsName = itemView.findViewById(R.id.tvIngredientsName);
            ivIngredients = itemView.findViewById(R.id.ivIngredients);
        }
    }
}
